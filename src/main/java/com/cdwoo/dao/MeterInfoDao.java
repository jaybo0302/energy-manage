/**
 * 
 */
package com.cdwoo.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.cdwoo.common.CDParam;
import com.cdwoo.entity.MeterInfoParam;
import com.cdwoo.entity.MeterInfoStatisticParam;

/**
 * @author cd
 *
 */
public interface MeterInfoDao {

	long queryMeterInfoCount(MeterInfoParam param);
	List<Object> queryMeterInfoByPage(MeterInfoParam param);
	List<Object> queryStatisticMeterInfo(MeterInfoStatisticParam param);
	long queryOfflineCount(CDParam param);
	List<Object> queryOfflineByPage(CDParam param);
	List<Map<String, Object>> getExportData(CDParam param);
}
