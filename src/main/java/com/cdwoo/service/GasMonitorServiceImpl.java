package com.cdwoo.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdwoo.dao.GasMonitorDao;

@Service
public class GasMonitorServiceImpl implements GasMonitorService {

	@Autowired GasMonitorDao gasMonitorDao;
	@Override
	public Object getGasInfo(int companyId) {
		// TODO Auto-generated method stub
		List<Map<String, Object>> result = gasMonitorDao.getGasInfo(companyId);
		int totalCount = result.size();
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("list", result);
		resultMap.put("totalCount", totalCount);
		return resultMap;
	}
	@Override
	public Object getGasInfo4App(int no, int companyId) {
		// TODO Auto-generated method stub
		List<Map<String, Object>> result = gasMonitorDao.getGasInfo4App(companyId);
		List<Map<String, Object>> returnResult = new ArrayList<>();
		Map<Integer, Integer> warnMap = new HashMap<>();
		List<Map<String, Integer>> warnList = new ArrayList<>();
		int start = (no-1) * 10;
		int end = no * 10;
		for (Map<String, Object> map : result) {
			if ("1".equals(String.valueOf(map.get("O2_state"))) || "1".equals(String.valueOf(map.get("CH4_state")))) {
				if (warnMap.get((Integer.parseInt(String.valueOf(map.get("deviceNo")))-1)/10+1) == null) {
					warnMap.put((Integer.parseInt(String.valueOf(map.get("deviceNo")))-1)/10+1, 1);
				} else {
					warnMap.put((Integer.parseInt(String.valueOf(map.get("deviceNo")))-1)/10+1, warnMap.get((Integer.parseInt(String.valueOf(map.get("deviceNo")))-1)/10+1) +1);
				}
			}
			if (Integer.parseInt(String.valueOf(map.get("deviceNo"))) > start && Integer.parseInt(String.valueOf(map.get("deviceNo"))) <= end) {
				returnResult.add(map);
			}
		}
		for(int i : warnMap.keySet()) {
			Map<String, Integer> currentMap = new HashMap<>();
			currentMap.put("list_num", i);
			currentMap.put("warning_num", warnMap.get(i));
			warnList.add(currentMap);
		}
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("devices_warning", warnList);
		resultMap.put("devices_state", returnResult);
		return resultMap;
	}
	@Override
	public Object getGasWarn4App(String start, String end, String deviceId,String companyId) {
		return gasMonitorDao.getGasWarn4App(start,end,deviceId, companyId);
	}
	@Override
	public void setPosition(String deviceNo, String position, int companyId) {
		gasMonitorDao.setPosition(deviceNo, position, companyId);
	}
}
