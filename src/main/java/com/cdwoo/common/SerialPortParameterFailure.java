package com.cdwoo.common;
import javax.swing.JOptionPane;

public class SerialPortParameterFailure extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SerialPortParameterFailure() {
		CDLogger.error("设置串口参数失败！打开串口操作未完成！");
	}

	@Override
	public String toString() {
		return "设置串口参数失败！打开串口操作未完成！";
	}
	
}
