package com.cdwoo.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cdwoo.common.CDResult;
import com.cdwoo.common.Constants;
import com.cdwoo.entity.MeterInfoParam;
import com.cdwoo.entity.User;
import com.cdwoo.service.MeterPowerService;

@Controller
@RequestMapping("meterpower")
public class MeterPowerController {
	@Autowired
	private MeterPowerService meterPowerService;

	@ResponseBody
	@RequestMapping("queryMeterpowerByPage")
	public Object queryMeterpowerByPage (MeterInfoParam param, HttpServletRequest req) {
		if (req.getSession().getAttribute(Constants.USER_CONTEXT) == null) {
			return CDResult.fail("login time out");
		}
		String devices = req.getParameter("devices");
		param.setCompanyId(((User)req.getSession().getAttribute(Constants.USER_CONTEXT)).getCompanyId());
		param.setRoleId(((User)req.getSession().getAttribute(Constants.USER_CONTEXT)).getRoleId());
		return CDResult.success(meterPowerService.queryMeterpowerByPage(param));
	}
}
