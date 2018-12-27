package com.cdwoo.entity;

import java.util.Calendar;

import com.cdwoo.utils.DateUtil;

public class MeterInfoStatisticParam {
	private int dateType;
	private String date;
	private int deviceNo;
	private int companyId;
	private String start;
	private String end;
	public int getDateType() {
		return dateType;
	}
	public void setDateType(int dateType) {
		this.dateType = dateType;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
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
	public String getStart() {
		if (dateType == 0) {
			return date;
		} else if (dateType == 1) {
			return date + "-01";
		} else {
			return "2100-01-01";
		}
	}
	public void setStart(String start) {
		this.start = start;
	}
	public String getEnd() {
		Calendar c = Calendar.getInstance();
		try {
			if (dateType == 0) {
				c.setTime(DateUtil.sdf_date_format.parse(date));
				c.add(Calendar.DAY_OF_MONTH, 1);
				return DateUtil.sdf_date_format.format(c.getTime());
			} else if (dateType == 1) {
				c.setTime(DateUtil.sdf_month_formart.parse(date));
				c.add(Calendar.MONTH, 1);
				return DateUtil.sdf_date_format.format(c.getTime());
			} else {
				return "1900-01-01";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "1900-01-01";
		}
	}
	public void setEnd(String end) {
		this.end = end;
	}
}
