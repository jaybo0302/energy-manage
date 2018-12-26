package com.cdwoo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cdwoo.common.CDParam;
import com.cdwoo.entity.Company;

public interface CompanyDao {
	List<Object> queryCompanyByPage(CDParam param);
	void addCompany(Company company);
	long queryCompanyCount(CDParam param);
	void updateCompanyStatus(@Param("id")String id, @Param("status")String status);
	Company getCompanyById(@Param("id")String id);
	void editCompany(Company company);
	List<Company> getCompanyList();
}
