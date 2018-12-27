package com.cdwoo.common;

import javax.swing.JOptionPane;

public class NotASerialPort extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NotASerialPort() {
		CDLogger.error("端口指向设备不是串口类型！打开串口操作失败！");
	}

	@Override
	public String toString() {
		return "端口指向设备不是串口类型！打开串口操作失败！";
	}
	
	
}
