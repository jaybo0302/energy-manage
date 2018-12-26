package com.cdwoo.common;

import java.net.InetSocketAddress;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

import com.cdwoo.entity.GasData;
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
    	int port = ((InetSocketAddress)session.getLocalAddress()).getPort();
    	String msg = message.toString().replaceAll("\r|\n", "");
    	CDLogger.info("收到一个 " + port +"    的数据 " + msg);
    	int companyId = DataReceiveServer.companyMap.get(port);
        if (msg.substring(0, 2).toLowerCase().equals("ff")) {
        	if (msg.split(" ").length != 29) {
        		return;
        	}
            GasData gd = DataUtil.dataHandle(msg.replaceAll(" ", ""));
            if (gd == null) {
            	return;
            }
        	//保存到数据库
        	Statement stmt = (Statement) DataReceiveServer.conn.createStatement();
//        	stmt.execute("insert into `gas-data` (dataStr,createTime, data2, data3, data4"
//        			+ ", data5, data6, data7, data8, data9, data10, data11, data12, data13, data14, data15"
//        			+ ", data16, data17, data18, data19, data20, data21, data22, data23, data24, data25"
//        			+ ", data26, data27, data28) value ('"+msg+"', now(), '"+msg.split(" ")[1]+ "','"+msg.split(" ")[2]+ "','"
//        			+msg.split(" ")[3]+ "','"+msg.split(" ")[4]+ "','"+msg.split(" ")[5]+ "','"+msg.split(" ")[6]+ "','"
//        			+msg.split(" ")[7]+ "','"+msg.split(" ")[8]+ "','"+msg.split(" ")[9]+ "','"+msg.split(" ")[10]+ "','"
//        			+msg.split(" ")[11]+ "','"+msg.split(" ")[12]+ "','"+msg.split(" ")[13]+ "','"+msg.split(" ")[14]+ "','"
//        			+msg.split(" ")[15]+ "','"+msg.split(" ")[16]+ "','"+msg.split(" ")[17]+ "','"+msg.split(" ")[18]+ "','"
//        			+msg.split(" ")[19]+ "','"+msg.split(" ")[20]+ "','"+msg.split(" ")[21]+ "','"+msg.split(" ")[22]+ "','"
//        			+msg.split(" ")[23]+ "','"+msg.split(" ")[24]+ "','"+msg.split(" ")[25]+ "','"+msg.split(" ")[26]+ "','"
//        			+msg.split(" ")[27]+ "')");
        	stmt.execute("replace into `gas-data` (deviceNo, companyId, state, gas, dataUnit, gasLevel, low, high, gas1, gasLevel1, low1, high1, dataUnit1,"
        			+ "temperature, deO2Voltage, deComVoltage, dePowerVoltage, deTemp, `check`, warningState, O2Warning, CH4Warning,createTime) values("
        			+gd.getLocalAddr()+"," + companyId + ",'"+gd.getState()+"','"+gd.getGas()+"','"+gd.getDataUnit()+"','"+gd.getGasLevel()+"','"+gd.getLow()+"','"+gd.getHigh()+"','"
        			+gd.getGas1()+"','"+gd.getGasLevel1()+"','"+gd.getLow1()+"','"+gd.getHigh1()+"','"+gd.getDataUnit1()+"','"+gd.getTemperature()+"','"+gd.getDeO2Voltage()+"','"
        			+gd.getDeComVoltage()+"','"+gd.getDePowerVoltage()+"','"+gd.getDeTemp()+"','"+gd.getCheck()+"',"+gd.getWarningState()+","+gd.getO2Warning()+","
        			+gd.getCH4Warning()+",now())");
        	if (gd.getWarningState() == 1 || gd.getO2Warning() ==1 || gd.getCH4Warning() ==1) {
        		stmt.execute("replace into `gas-warn-data` (deviceId,companyId, state, gas, dataUnit, gasLevel, low, high, gas1, gasLevel1, low1, high1, dataUnit1,"
            			+ "temperature, deO2Voltage, deComVoltage, dePowerVoltage, deTemp, `check`, warningState, O2Warning, CH4Warning,createTime) values("
            			+gd.getLocalAddr()+"," + companyId + ",'"+gd.getState()+"','"+gd.getGas()+"','"+gd.getDataUnit()+"','"+gd.getGasLevel()+"','"+gd.getLow()+"','"+gd.getHigh()+"','"
            			+gd.getGas1()+"','"+gd.getGasLevel1()+"','"+gd.getLow1()+"','"+gd.getHigh1()+"','"+gd.getDataUnit1()+"','"+gd.getTemperature()+"','"+gd.getDeO2Voltage()+"','"
            			+gd.getDeComVoltage()+"','"+gd.getDePowerVoltage()+"','"+gd.getDeTemp()+"','"+gd.getCheck()+"',"+gd.getWarningState()+","+gd.getO2Warning()+","
            			+gd.getCH4Warning()+",now())");
        	}
        }
        //如果是中转，将此代码放开。
        //session.close();
    }
    @Override
    public void messageSent(IoSession session, Object message) throws Exception {
    	CDLogger.info("服务端发送信息成功...");
//        session.close();
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