package com.cdwoo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdwoo.common.CDPage;
import com.cdwoo.common.CDParam;
import com.cdwoo.dao.CompanyDao;
import com.cdwoo.entity.Company;

@Service
public class CompanyServiceImpl implements CompanyService {

	@Autowired
	private CompanyDao companyDao;
	
	@Override
	public CDPage queryCompanyByPage(CDParam param) {
		CDPage page = new CDPage();
		page.setCount(param.getPageSize());
		page.setCurrentPage(param.getPageNo());
		page.setTotalCount(companyDao.queryCompanyCount(param));
		List<Object> result= companyDao.queryCompanyByPage(param);
		page.setData(result);
		return page;
	}

	@Override
	public void addCompany(Company company) {
		this.companyDao.addCompany(company);
	}

	@Override
	public void updateCompanyStatus(String id, String status) {
		this.companyDao.updateCompanyStatus(id, status);
	}

	@Override
	public Company getCompanyById(String id) {
		return this.companyDao.getCompanyById(id);
	}

	@Override
	public void editCompany(Company company) {
		this.companyDao.editCompany(company);
	}

	@Override
	public List<Company> getCompanyList() {
		return this.companyDao.getCompanyList();
	}

}
