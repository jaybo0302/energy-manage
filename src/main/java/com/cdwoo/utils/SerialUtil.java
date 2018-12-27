package com.cdwoo.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.TooManyListenersException;

import com.cdwoo.common.NoSuchPort;
import com.cdwoo.common.NotASerialPort;
import com.cdwoo.common.PortInUse;
import com.cdwoo.common.ReadDataFromSerialPortFailure;
import com.cdwoo.common.SendDataToSerialPortFailure;
import com.cdwoo.common.SerialPortInputStreamCloseFailure;
import com.cdwoo.common.SerialPortOutputStreamCloseFailure;
import com.cdwoo.common.SerialPortParameterFailure;
import com.cdwoo.common.TooManyListeners;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.SerialPortEventListener;
import gnu.io.UnsupportedCommOperationException;

/**
 * 串口工具类
 * @author Tao Wenjian
 *
 */

public class SerialUtil {

	private volatile static SerialUtil serialUtil = null;
	
	private SerialUtil(){}
	
	//双重检查锁 单例模式
	public static SerialUtil getSerialUtil(){
		if(serialUtil == null){
			synchronized(SerialUtil.class){
				serialUtil = new SerialUtil();
			}
		}
		return serialUtil;
	}
	
	/**
	 * 寻找端口,返回端口名
	 */
	public static final ArrayList<String> findPort(){
		Enumeration<CommPortIdentifier> portList = CommPortIdentifier.getPortIdentifiers();
		ArrayList<String> portNameList = new ArrayList<String>();
		while(portList.hasMoreElements()){
			CommPortIdentifier port = portList.nextElement();
			//String portName = portList.nextElement().getName();
			String portName = port.getName();
			portNameList.add(portName);
			
		}
		return portNameList;
	}
	
	/**
	 * 打开端口
	 */
	public static final SerialPort openPort(String portName, int baudrate, int databits, int stopbits, String parity) throws SerialPortParameterFailure, NotASerialPort, NoSuchPort, PortInUse {
		try{
			CommPortIdentifier portIndentifier = CommPortIdentifier.getPortIdentifier(portName);
			CommPort commPort = portIndentifier.open(portName, 2000); //参数1：端口名，参数2：超时时间'
			int parityNum = 0;
			switch(parity){
				case "None": parityNum = SerialPort.PARITY_NONE; break;
				case "Odd":  parityNum = SerialPort.PARITY_ODD; break;
				case "Even": parityNum = SerialPort.PARITY_EVEN; break;
				case "Mark": parityNum = SerialPort.PARITY_MARK; break;
				case "Space":parityNum = SerialPort.PARITY_SPACE; break;
				
			}
			if(commPort instanceof SerialPort){
				SerialPort serialPort = (SerialPort)commPort;
				try{
					serialPort.setSerialPortParams(baudrate, databits, stopbits, parityNum);
				}catch (UnsupportedCommOperationException e){
					throw new SerialPortParameterFailure();
				}
				return serialPort;
			}else{
				throw new NotASerialPort();
			}
		}catch (NoSuchPortException e1){
			throw new NoSuchPort();
		}catch (PortInUseException e2){
			throw new PortInUse();
		}
		
	}
	
	/**
	 * 关闭串口
	 */
	public static void closePort(SerialPort serialPort){
		if(serialPort != null){
			serialPort.close();
			serialPort = null;
		}
	}
	
	/**
	 * 发送数据,并接收返回的数据
	 */
	public static byte[] sendToPort(SerialPort serialPort, byte[] order) throws SendDataToSerialPortFailure, SerialPortOutputStreamCloseFailure{
		System.out.println("发送内容：" + DataUtil.hex2String(order));
		InputStream is = null;
		OutputStream out = null;
		byte [] bytes = null;
		int count = 0;
		try{
			is = serialPort.getInputStream();
			out = serialPort.getOutputStream();
			out.write(order);
			out.flush();
			//接收数据
			while(true) {
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if (is.available() > count) {
					count = is.available();
				} else {
					break;
				}
			}
			bytes= new byte[count];
			is.read(bytes);
		}catch(IOException e){
			throw new SendDataToSerialPortFailure();
		} finally {
			try{
				if(out != null){
					out.close();
					out = null;
				}
				if (is != null) {
					is.close();
					is = null;
				}
			}catch (IOException e){
				throw new SerialPortOutputStreamCloseFailure();
			}
		}
		return bytes;
	}
	
	/**
	 * 接收文本数据（终端通信）
	 */
	public static String readFromPortTerminal(SerialPort serialPort) throws ReadDataFromSerialPortFailure, SerialPortInputStreamCloseFailure{
		InputStream in = null;
		byte[] bytes = null;
		try {
			in = serialPort.getInputStream();
			int bufflenth = in.available();
			while(bufflenth != 0) {
				bytes = new byte[bufflenth];
				in.read(bytes);
				bufflenth = in.available();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new ReadDataFromSerialPortFailure();
		}finally {
			try {
				if (in != null) {
                    in.close();
                    in = null;
                }
			} catch (IOException e) {
				// TODO Auto-generated catch block
				throw new SerialPortInputStreamCloseFailure();
			}
		}
		String result = "";
		try {
			result = new String(bytes, "gb2312");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 接收数据
	 */
	public static byte[] readFromPort(SerialPort serialPort) throws ReadDataFromSerialPortFailure, SerialPortInputStreamCloseFailure{
    	InputStream in = null;
        byte[] bytes = null;//按照协议设置
        try {
        	in = serialPort.getInputStream();
        	int count = 0;
        	bytes = new byte[29];
        	count = in.available();
        	
        	while(count != 29){
        		count = in.available();
        	}
        	in.read(bytes);
        	int temp;
        	if(!Integer.toHexString(0xFF & bytes[0]).equals("ff")) {
        		temp = 1;
        		while(temp < 29 && !Integer.toHexString(0xFF & bytes[temp]).equals("ff")) {
        			temp ++;
        		}
        		if(temp == 29) {
        			bytes = new byte[29];
        		}else {
        			for(int i = 0; i < (29 - temp); i++) {
        				bytes[i] = bytes[temp + i];
        			}
        			count = in.available();
        			while(count != temp + 1) {
        				count = in.available();
        			}
        			byte[] bytesPlus = new byte[temp];
        			in.read(bytesPlus);
        			for(int i = 0; i < temp; i++) {
        				bytes[(29 - temp) + i] = bytesPlus[i];
        			}
        		}
        	}       	
        } catch (IOException e) {
        	throw new ReadDataFromSerialPortFailure();
        } finally {
        	try {
            	if (in != null) {
            		in.close();
            		in = null;
            	}
        	} catch(IOException e) {
        		throw new SerialPortInputStreamCloseFailure();
        	}
        }
        return bytes;
	}
	
	public static void readFromPort1(SerialPort serialPort) throws ReadDataFromSerialPortFailure, SerialPortInputStreamCloseFailure{
    	InputStream in = null;
        byte[] bytes = new byte[1024];//按照协议设置
        try {
        	in = serialPort.getInputStream();
        	while (in.read(bytes) != -1) {
        		String line = new String(bytes);
        		if (line.trim().replace(" ", "").length() >0) {
        			System.out.println(line);
        		}
        	}
        } catch (Exception e) {
        	e.printStackTrace();
		}
	}
	/**
	 * 添加监听
	 */
	public static void addListener(SerialPort port, SerialPortEventListener listener) throws TooManyListeners{
		try{
			port.addEventListener(listener);
			port.notifyOnDataAvailable(true);
			port.notifyOnBreakInterrupt(true);
		}catch(TooManyListenersException e){
			throw new TooManyListeners();
		}
	}
}
