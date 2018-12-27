package com.cdwoo.common;

import javax.swing.JOptionPane;

public class PortInUse extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PortInUse() {
		CDLogger.error("端口已被占用！打开串口操作失败！");
	}

	@Override
	public String toString() {
		return "端口已被占用！打开串口操作失败！";
	}
	
}
