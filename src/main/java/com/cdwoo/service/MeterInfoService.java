package com.cdwoo.service;

import java.util.List;
import java.util.Map;

import com.cdwoo.common.CDPage;
import com.cdwoo.common.CDParam;
import com.cdwoo.entity.MeterInfoParam;
import com.cdwoo.entity.MeterInfoStatisticParam;

public interface MeterInfoService {

	CDPage queryMeterInfoByPage(MeterInfoParam param);

	Object queryStatisticMeterInfo(MeterInfoStatisticParam param);

	CDPage queryOfflineByPage(CDParam param);

	List<Map<String, Object>> getExportData(CDParam param);

}
