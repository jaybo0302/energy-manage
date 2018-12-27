package com.cdwoo.entity;

public class ElectricMeter {
	private int deviceNo;
	private int companyId;
	private String deviceName;
	private String position;
	private int status;
	private String port;
	public int getDeviceNo() {
		return deviceNo;
	}
	public void setDeviceNo(int deviceNo) {
		this.deviceNo = deviceNo;
	}
	public int getCompanyId() {
		return companyId;
	}
	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}
	public String getDeviceName() {
		return deviceName;
	}
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
}
