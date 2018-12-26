/**
 * 
 */
package com.cdwoo.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cdwoo.common.CDLogger;
import com.cdwoo.common.CDPage;
import com.cdwoo.common.CDParam;
import com.cdwoo.common.CDResult;
import com.cdwoo.common.Constants;
import com.cdwoo.entity.Company;
import com.cdwoo.service.CompanyService;

/**
 * @author cd
 *
 */
@Controller
@RequestMapping("company")
public class CompanyController {
	@Autowired
	private CompanyService companyService;
	
	@ResponseBody
	@RequestMapping("queryCompanyByPage")
	public CDResult queryCompanyByPage (CDParam param, HttpServletRequest req) {
		if (req.getSession().getAttribute(Constants.USER_CONTEXT) == null) {
			return CDResult.fail("login time out");
		}
		return CDResult.success(companyService.queryCompanyByPage(param));
	}
	
	@ResponseBody
	@RequestMapping("addCompany")
	public CDResult addCompany(Company company) {
		try {
			this.companyService.addCompany(company);
			return CDResult.success();
		} catch (Exception e) {
			CDLogger.error(e.toString());
			return CDResult.fail("添加失败");
		}
	}
	
	@ResponseBody
	@RequestMapping("updateCompanyStatus")
	public CDResult updateCompanyStatus(@RequestParam("id") String id, @RequestParam("status")String status) {
		try {
			this.companyService.updateCompanyStatus(id, status);
			return CDResult.success();
		} catch (Exception e) {
			CDLogger.error(e.toString());
			return CDResult.fail("内部错误");
		}
	}
	
	@RequestMapping("getEditPage")
	public String getEditPage(@RequestParam("id") String id, HttpServletRequest req) {
		Company com = companyService.getCompanyById(id);
		req.setAttribute("company", com);
		return "jsp/company_edit";
	}
	
	@ResponseBody
	@RequestMapping("editCompany")
	public CDResult editCompany(Company company) {
		try {
			this.companyService.editCompany(company);
			return CDResult.success();
		} catch (Exception e) {
			CDLogger.error(e.toString());
			return CDResult.fail("内部错误");
		}
	}
	
	@ResponseBody
	@RequestMapping("getCompanyList")
	public CDResult getCompanyList() {
		return CDResult.success(this.companyService.getCompanyList());
	}
}
