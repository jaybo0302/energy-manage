package com.cdwoo.service;

import java.util.List;

import com.cdwoo.common.CDPage;
import com.cdwoo.common.CDParam;
import com.cdwoo.entity.Company;

public interface CompanyService {
	CDPage queryCompanyByPage(CDParam param);
	void addCompany(Company company);
	void updateCompanyStatus(String id, String status);
	Company getCompanyById(String id);
	void editCompany(Company company);
	List<Company> getCompanyList();
}
