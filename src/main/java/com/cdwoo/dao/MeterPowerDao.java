package com.cdwoo.dao;

import java.util.List;

import com.cdwoo.entity.MeterInfoParam;

public interface MeterPowerDao {

	long queryMeterpowerCount(MeterInfoParam param);
	List<Object> queryMeterpowerByPage(MeterInfoParam param);

}
