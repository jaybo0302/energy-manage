/**
 * 
 */
package com.cdwoo.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cdwoo.common.CDPage;
import com.cdwoo.common.CDParam;
import com.cdwoo.common.CDResult;
import com.cdwoo.common.Constants;
import com.cdwoo.entity.User;
import com.cdwoo.service.MeterInfoService;

/**
 * @author cd
 *
 */
@Controller
@RequestMapping("offline")
public class OfflineController {
	@Autowired
	private MeterInfoService meterInfoService;
	
	@ResponseBody
	@RequestMapping("queryOfflineByPage")
	public CDResult queryOfflineByPage(CDParam param, HttpServletRequest req) {
		if (req.getSession().getAttribute(Constants.USER_CONTEXT) == null) {
			return CDResult.fail("login time out");
		}
		param.setCompanyId(((User)req.getSession().getAttribute(Constants.USER_CONTEXT)).getCompanyId());
		param.setRoleId(((User)req.getSession().getAttribute(Constants.USER_CONTEXT)).getRoleId());
		return CDResult.success(meterInfoService.queryOfflineByPage(param));
	}
}
