package com.cdwoo.service;

import java.util.Map;

import com.cdwoo.common.CDPage;
import com.cdwoo.common.CDParam;
import com.cdwoo.entity.ElectricMeter;

public interface ElectricMeterService {

	CDPage queryElectricmeterByPage(CDParam param);
	void addElectricmeter(ElectricMeter electricmeter);
	void updateElectricmeterStatus(String deviceNo,int companyId, String status);
	Map<String, Object> getElectricMeterById(int deviceNo, int companyId);
	void editElectricmeter(ElectricMeter electricmeter);
	Object getDevicesByCompany(int companyId);
	int getMaxDeviceNo(int companyId);
	long getDeviceNoExists(int deviceNo, int companyId);
}
