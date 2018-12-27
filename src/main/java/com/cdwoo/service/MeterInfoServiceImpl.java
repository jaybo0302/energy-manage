package com.cdwoo.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdwoo.common.CDPage;
import com.cdwoo.common.CDParam;
import com.cdwoo.dao.MeterInfoDao;
import com.cdwoo.entity.MeterInfoParam;
import com.cdwoo.entity.MeterInfoStatisticParam;

/**
 * @author cd
 *
 */
@Service
public class MeterInfoServiceImpl implements MeterInfoService {
	@Autowired
	private MeterInfoDao meterInfoDao;

	@Override
	public CDPage queryMeterInfoByPage(MeterInfoParam param) {
		CDPage page = new CDPage();
		page.setCount(param.getPageSize());
		page.setCurrentPage(param.getPageNo());
		page.setTotalCount(meterInfoDao.queryMeterInfoCount(param));
		List<Object> result= meterInfoDao.queryMeterInfoByPage(param);
		page.setData(result);
		return page;
	}

	@Override
	public Object queryStatisticMeterInfo(MeterInfoStatisticParam param) {
		return meterInfoDao.queryStatisticMeterInfo(param);
	}

	@Override
	public CDPage queryOfflineByPage(CDParam param) {
		CDPage page = new CDPage();
		page.setCount(param.getPageSize());
		page.setCurrentPage(param.getPageNo());
		page.setTotalCount(meterInfoDao.queryOfflineCount(param));
		List<Object> result= meterInfoDao.queryOfflineByPage(param);
		page.setData(result);
		return page;
	}

	@Override
	public List<Map<String, Object>> getExportData(CDParam param) {
		return meterInfoDao.getExportData(param);
	}
}
