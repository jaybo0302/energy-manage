package com.cdwoo.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdwoo.common.CDPage;
import com.cdwoo.common.CDParam;
import com.cdwoo.dao.ElectricMeterDao;
import com.cdwoo.entity.ElectricMeter;

@Service
public class ElectricMeterServiceImpl implements ElectricMeterService{
	@Autowired
	private ElectricMeterDao electricMeterDao;

	@Override
	public CDPage queryElectricmeterByPage(CDParam param) {
		CDPage page = new CDPage();
		page.setCount(param.getPageSize());
		page.setCurrentPage(param.getPageNo());
		page.setTotalCount(electricMeterDao.queryElectricMeterCount(param));
		List<Object> result= electricMeterDao.queryElectricMeterByPage(param);
		page.setData(result);
		return page;
	}

	@Override
	public void addElectricmeter(ElectricMeter electricmeter) {
		this.electricMeterDao.addElectricmeter(electricmeter);
	}

	@Override
	public void updateElectricmeterStatus(String deviceNo,int companyId, String status) {
		this.electricMeterDao.updateElectricmeterStatus(deviceNo, companyId, status);
	}

	@Override
	public Map<String, Object> getElectricMeterById(int deviceNo, int companyId) {
		return this.electricMeterDao.getElectricMeterById(deviceNo, companyId);
	}

	@Override
	public void editElectricmeter(ElectricMeter electricmeter) {
		this.electricMeterDao.editElectricmeter(electricmeter);
	}

	@Override
	public Object getDevicesByCompany(int companyId) {
		return this.electricMeterDao.getDevicesByCompany(companyId);
	}

	@Override
	public int getMaxDeviceNo(int companyId) {
		return this.electricMeterDao.getMaxDeviceNo(companyId);
	}

	@Override
	public long getDeviceNoExists(int deviceNo, int companyId) {
		return this.electricMeterDao.getDeviceNoExists(deviceNo, companyId);
	}
}
