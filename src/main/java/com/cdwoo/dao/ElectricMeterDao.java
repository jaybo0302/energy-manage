package com.cdwoo.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.cdwoo.common.CDParam;
import com.cdwoo.entity.ElectricMeter;

public interface ElectricMeterDao {
	long queryElectricMeterCount(CDParam param);
	List<Object> queryElectricMeterByPage(CDParam param);
	void addElectricmeter(ElectricMeter electricmeter);
	void updateElectricmeterStatus(@Param("deviceNo")String deviceNo,@Param("companyId")int companyId, @Param("status")String status);
	Map<String, Object> getElectricMeterById(@Param("deviceNo")int deviceNo, @Param("companyId")int companyId);
	void editElectricmeter(ElectricMeter electricmeter);
	List<Map<String, Object>> getDevicesByCompany(@Param("companyId") int companyId);
	int getMaxDeviceNo(@Param("companyId")int companyId);
	long getDeviceNoExists(@Param("deviceNo")int deviceNo, @Param("companyId")int companyId);
}
