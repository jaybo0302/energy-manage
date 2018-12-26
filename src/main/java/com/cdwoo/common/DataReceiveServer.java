package com.cdwoo.common;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.executor.ExecutorFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.springframework.util.ResourceUtils;

import com.cdwoo.utils.PropertiesUtil;

public class DataReceiveServer {
	private static int PORT;
	private static String JDBCURL;
	private static String DBUSERNAME;
	private static String DBPASSWORD;
	public static Connection conn;
	private static final String companySql = "select * from company where id != 0 and status = 0";
	public static Map<Integer, Integer> companyMap;
	private static Set<Integer> portSet;
	//记录当前已经绑定的端口；
	private static Set<Integer> currentPortSet;
	private static IoAcceptor acceptor = null; // 创建连接
	static {
		try {
			companyMap = new HashMap<>();
			portSet = new HashSet<>();
			currentPortSet = new HashSet<>();
			JDBCURL = PropertiesUtil.get(ResourceUtils.getFile("classpath:com/cdwoo/conf/constants/constants.properties").getPath(), "receive.jdbc.url");
			PORT = Integer.parseInt(PropertiesUtil.get(ResourceUtils.getFile("classpath:com/cdwoo/conf/constants/constants.properties").getPath(), "ServerPort"));
			DBUSERNAME = PropertiesUtil.get(ResourceUtils.getFile("classpath:com/cdwoo/conf/constants/constants.properties").getPath(), "receive.jdbc.username");
			DBPASSWORD = PropertiesUtil.get(ResourceUtils.getFile("classpath:com/cdwoo/conf/constants/constants.properties").getPath(), "receive.jdbc.password");
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(JDBCURL, DBUSERNAME, DBPASSWORD);
		} catch (Exception e) {
			CDLogger.error(e.toString());
		}
	}
	public void start() {
		
		try {
			// 创建一个非阻塞的server端的Socket 可以不指定线程数量，MINA2里面默认是CPU数量+2
			acceptor = new NioSocketAcceptor();
			// 设置过滤器（使用Mina提供的文本换行符编解码器）
			Executor threadPool = Executors.newFixedThreadPool(100);
			//加入过滤器（Filter）到Acceptor
			acceptor.getFilterChain().addLast("exector", new ExecutorFilter(threadPool));
			if ("net".equals(PropertiesUtil.get(ResourceUtils.getFile("classpath:com/cdwoo/conf/constants/constants.properties").getPath(), "mode").toLowerCase())) {
				acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(new MyCodecFactory4Net()));
				// 绑定逻辑处理器
				acceptor.setHandler(new MyServerHandler4Net()); // 添加业务处理
			} else {
				acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(new MyCodecFactory4Android()));
				// 绑定逻辑处理器
				acceptor.setHandler(new MyServerHandler4Android()); // 添加业务处理
			}
			// 绑定逻辑处理器
			acceptor.setHandler(new MyServerHandler4Net()); // 添加业务处理
			// 设置读取数据的缓冲区大小
			acceptor.getSessionConfig().setReadBufferSize(2048);
			// 读写通道10秒内无操作进入空闲状态
			acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);
			
			// 绑定端口
			Timer timer = new Timer();
			timer.schedule(new TimerTask() {
				@Override
				public void run() {
					//扫描船厂表-----------------------------
					CDLogger.info("扫描船厂表-----------------------------");
					getCompanyList();
					for (int port : portSet) {
						try {
							if (!currentPortSet.contains(port)) {
								CDLogger.info("服务端启动成功...     端口号为：" + port);
								acceptor.bind(new InetSocketAddress(port));
								currentPortSet.add(port);
							}
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					for (SocketAddress ia : acceptor.getLocalAddresses()) {
						if (!portSet.contains(((InetSocketAddress)ia).getPort())) {
							CDLogger.info("服务端停用成功...     端口号为：" + ((InetSocketAddress)ia).getPort());
							acceptor.unbind(ia);
							currentPortSet.remove(((InetSocketAddress)ia).getPort());
						}
					}
				}
			}, 0, 60 * 1000);
			
		} catch (Exception e) {
			CDLogger.error(e.toString());
		}
	}
	
	public void getCompanyList() {
		try {
			portSet.clear();
			companyMap.clear();
			ResultSet rs =  conn.createStatement().executeQuery(companySql);
			while(rs.next()) {
				int port = rs.getInt("port");
				int id = rs.getInt("id");
				portSet.add(port);
				companyMap.put(port, id);
			}
			rs.close();
		} catch (SQLException e) {
			CDLogger.error(e.toString());
		}
	}
}