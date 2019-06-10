package com.cdwoo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdwoo.common.CDPage;
import com.cdwoo.dao.MeterPowerDao;
import com.cdwoo.entity.MeterInfoParam;

@Service
public class MeterPowerServiceImpl implements MeterPowerService{
	@Autowired
	private MeterPowerDao meterPowerDao;

	@Override
	public Object queryMeterpowerByPage(MeterInfoParam param) {
		CDPage page = new CDPage();
		page.setCount(param.getPageSize());
		page.setCurrentPage(param.getPageNo());
		page.setTotalCount(meterPowerDao.queryMeterpowerCount(param));
		List<Object> result= meterPowerDao.queryMeterpowerByPage(param);
		page.setData(result);
		return page;
	}
	
	
}
