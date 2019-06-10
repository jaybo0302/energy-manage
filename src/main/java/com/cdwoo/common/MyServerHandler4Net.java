package com.cdwoo.common;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import com.cdwoo.utils.DataUtil;
import com.mysql.jdbc.Statement;
public class MyServerHandler4Net extends IoHandlerAdapter {
    @Override
    public void sessionCreated(IoSession session) throws Exception {
    	CDLogger.info("服务端与客户端创建连接...");
    }
    @Override
    public void sessionOpened(IoSession session) throws Exception {
    	CDLogger.info("服务端与客户端连接打开...");
    }
    @Override
    public void messageReceived(IoSession session, Object message)
            throws Exception {
    	CDLogger.info("收到数据**");
    	String msg = message.toString();
    	System.out.println(msg);
    	//CDLogger.info(msg);
    	String[] datas = msg.split(" ");
    	if (datas.length < 3 || !((datas.length - 8) == DataUtil.getLength(new String[] {datas[1],datas[2]}))) {
			return;
		}
    	//获取终端编号
    	String zcode = datas[8] + datas[7];
    	String terminalCode = String.valueOf(Integer.parseInt(datas[10] + datas[9], 16));
    	terminalCode = DataUtil.add02front(terminalCode, 4-terminalCode.length());
    	//判断是否是登录消息或者心跳包，并且回复消息
    	int da1 = Integer.parseInt(datas[14], 16);
    	int da2 = Integer.parseInt(datas[15], 16);
    	int dt1 = Integer.parseInt(datas[16], 16);
    	int dt2 = Integer.parseInt(datas[17], 16);
    	int pn = 0;
    	int fn = 0;
    	String seq = datas[13];
    	int pseq = Integer.parseInt(seq.substring(1,2), 16);
    	if (da1 !=0 || da2 != 0) {
    		pn = (int)(Math.log((double)da1)/Math.log((double)2)) + (da2-1)*8 + 1;
    	}
    	fn = (int)(Math.log((double)dt1)/Math.log((double)2)) + dt2*8 + 1;
    	if (datas.length == 20 || datas.length == 26) {
    		CDLogger.info("心跳===");
    		Map<String, Object> paramsHeart = new HashMap<>();
    		paramsHeart.put("orderNo", 1);
    		paramsHeart.put("msa", 121);
    		paramsHeart.put("zcode", zcode);
    		paramsHeart.put("terminalNo", Integer.parseInt(terminalCode));
    		paramsHeart.put("no", pn);
    		paramsHeart.put("length", 12);
    		paramsHeart.put("dir", 0);
    		paramsHeart.put("prm", 1);
    		paramsHeart.put("fcb", 0);
    		paramsHeart.put("fcv", 0);
    		paramsHeart.put("funcCode", 0);
    		paramsHeart.put("tpv", 0);
    		paramsHeart.put("fir", 1);
    		paramsHeart.put("fin", 1);
    		paramsHeart.put("con", 0);
    		paramsHeart.put("pseq", pseq);
    		paramsHeart.put("afn", "00");
    		session.write(DataUtil.getOrder(paramsHeart));
    	}
    	//如果是上传的电表信息，进行记录并进行回复
    	if (datas.length == 182) {
    		//f25和f33合并成一个帧上传的信息，进行解析并保存。
    		String []dataF25 = new String[67];
    		String []dataF33 = new String[91];
    		for (int i = 0; i< 67; i++) {
    			dataF25[i] = datas[i+18];
    		}
    		for (int i = 0; i< 91; i++) {
    			dataF33[i] = datas[i+89];
    		}
    		CDLogger.info("获取到数据帧==");
    		DataUtil.f33(dataF33, pn);
    		DataUtil.f25(dataF25, pn);
    		Map<String, Object> paramsHeart = new HashMap<>();
    		paramsHeart.put("orderNo", 1);
    		paramsHeart.put("msa", 121);
    		paramsHeart.put("zcode", zcode);
    		paramsHeart.put("terminalNo", Integer.parseInt(terminalCode));
    		paramsHeart.put("no", pn);
    		paramsHeart.put("length", 12);
    		paramsHeart.put("dir", 0);
    		paramsHeart.put("prm", 1);
    		paramsHeart.put("fcb", 0);
    		paramsHeart.put("fcv", 0);
    		paramsHeart.put("funcCode", 0);
    		paramsHeart.put("tpv", 0);
    		paramsHeart.put("fir", 1);
    		paramsHeart.put("fin", 1);
    		paramsHeart.put("con", 0);
    		paramsHeart.put("pseq", pseq);
    		paramsHeart.put("afn", "00");
    		session.write(DataUtil.getOrder(paramsHeart));
    	}
    }
    @Override
    public void messageSent(IoSession session, Object message) throws Exception {
    	CDLogger.info("服务端发送信息成功...");
    }
    @Override
    public void sessionClosed(IoSession session) throws Exception {}
    @Override
    public void sessionIdle(IoSession session, IdleStatus status)
            throws Exception {
    	CDLogger.info("服务端进入空闲状态...");
    }
    @Override
    public void exceptionCaught(IoSession session, Throwable cause)
            throws Exception {
    	CDLogger.info("服务端异常..." + cause);
    }
}