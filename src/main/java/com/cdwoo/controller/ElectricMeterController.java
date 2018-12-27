package com.cdwoo.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cdwoo.common.CDParam;
import com.cdwoo.common.CDResult;
import com.cdwoo.common.Constants;
import com.cdwoo.entity.ElectricMeter;
import com.cdwoo.entity.User;
import com.cdwoo.service.ElectricMeterService;

@Controller
@RequestMapping("electricmeter")
public class ElectricMeterController {
	@Autowired
	private ElectricMeterService electricMeterService;
	
	@ResponseBody
	@RequestMapping("queryElectricmeterByPage")
	public CDResult queryElectricmeterByPage (CDParam param, HttpServletRequest req) {
		if (req.getSession().getAttribute(Constants.USER_CONTEXT) == null) {
			return CDResult.fail("login time out");
		}
		param.setCompanyId(((User)req.getSession().getAttribute(Constants.USER_CONTEXT)).getCompanyId());
		param.setRoleId(((User)req.getSession().getAttribute(Constants.USER_CONTEXT)).getRoleId());
		return CDResult.success(electricMeterService.queryElectricmeterByPage(param));
	}
	
	@ResponseBody
	@RequestMapping("addElectricMeter")
	public CDResult addElectricMeter(ElectricMeter electricmeter, HttpServletRequest req) {
		try {
			if (req.getSession().getAttribute(Constants.USER_CONTEXT) == null) {
				return CDResult.fail("login time out");
			}
			int companyId = ((User)req.getSession().getAttribute(Constants.USER_CONTEXT)).getCompanyId();
			long currentNo = this.electricMeterService.getDeviceNoExists(electricmeter.getDeviceNo(), companyId);
			if (currentNo > 0) {
				return CDResult.fail("该设备号已经存在");
			}
			electricmeter.setCompanyId(companyId);
			this.electricMeterService.addElectricmeter(electricmeter);
			return CDResult.success();
		} catch (Exception e) {
			e.printStackTrace();
			return CDResult.fail("添加失败");
		}
	}
	
	@ResponseBody
	@RequestMapping("updateElectricmeterStatus")
	public CDResult updateElectricmeterStatus(@RequestParam("deviceNo") String deviceNo, @RequestParam("status")String status, HttpServletRequest req) {
		try {
			if (req.getSession().getAttribute(Constants.USER_CONTEXT) == null) {
				return CDResult.fail("login time out");
			}
			this.electricMeterService.updateElectricmeterStatus(deviceNo,((User)req.getSession().getAttribute(Constants.USER_CONTEXT)).getCompanyId(), status);
			return CDResult.success();
		} catch (Exception e) {
			e.printStackTrace();
			return CDResult.fail("内部错误");
		}
	}
	
	@RequestMapping("getEditPage")
	public String getEditPage(@RequestParam("deviceNo") String deviceNo, HttpServletRequest req) {
		if (req.getSession().getAttribute(Constants.USER_CONTEXT) == null) {
			return "";
		}
		req.setAttribute("electricmeter", electricMeterService.getElectricMeterById(Integer.parseInt(deviceNo), ((User)req.getSession().getAttribute(Constants.USER_CONTEXT)).getCompanyId()));
		return "jsp/electric_meter_edit";
	}
	
	@ResponseBody
	@RequestMapping("editElectricMeter")
	public CDResult editElectricmeter(ElectricMeter electricmeter, HttpServletRequest req) {
		try {
			if (req.getSession().getAttribute(Constants.USER_CONTEXT) == null) {
				return CDResult.fail("login time out");
			}
			electricmeter.setCompanyId(((User)req.getSession().getAttribute(Constants.USER_CONTEXT)).getCompanyId());
			this.electricMeterService.editElectricmeter(electricmeter);
			return CDResult.success();
		} catch (Exception e) {
			e.printStackTrace();
			return CDResult.fail("内部错误");
		}
	}
	
	@ResponseBody
	@RequestMapping("getDevicesByCompany")
	public CDResult getDevicesByCompany(HttpServletRequest req) {
		try {
			if (req.getSession().getAttribute(Constants.USER_CONTEXT) == null) {
				return CDResult.fail("login time out");
			}
			return CDResult.success(this.electricMeterService.getDevicesByCompany(((User)req.getSession().getAttribute(Constants.USER_CONTEXT)).getCompanyId()));
		} catch (Exception e) {
			e.printStackTrace();
			return CDResult.fail("内部错误");
		}
	}
}
