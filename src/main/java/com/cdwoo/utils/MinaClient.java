/**
 * 
 */
package com.cdwoo.utils;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.log4j.Logger;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.LineDelimiter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

/**
 * @author cd
 *
 */
public class MinaClient{

    private static Logger logger = Logger.getLogger(MinaClient.class);
    private static String HOST = "127.0.0.1";
    private static int PORT = 3241;
    public static void main(String[] args) {
        // 创建一个非阻塞的客户端程序
        IoConnector connector = new NioSocketConnector();  // 创建连接
        // 设置链接超时时间
        connector.setConnectTimeout(30000);
        // 添加过滤器
        connector.getFilterChain().addLast(   //添加消息过滤器
                "codec",
                new ProtocolCodecFilter(new MyCodecFactory()));
        // 添加业务逻辑处理器类
        connector.setHandler(new ClientHandler());// 添加业务处理
        IoSession session = null;
        try {
            ConnectFuture future = connector.connect(new InetSocketAddress("127.0.0.1", 1234));// 创建连接
            future.awaitUninterruptibly();// 等待连接创建完成
            session= future.getSession();// 获得session
            session.write("我爱你mina");// 发送消息
        }catch (Exception e) {
            logger.error("客户端链接异常...", e);
        }
        session.getCloseFuture().awaitUninterruptibly();// 等待连接断开
        connector.dispose();
    }
}