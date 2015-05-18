package com.accential.trueone.interfaces;

import java.util.List;
import java.util.Map;

import com.accential.trueone.bean.Company;
import com.accential.trueone.bean.User;

@SuppressWarnings("all")
public interface IOCompany {

	@SuppressWarnings("rawtypes")
	List<Company> listCompanies(Map params);

	int countCompany(Map params);

	List<Company> listAllCompanies(Map params);

	Company searchById(int id);

	public Company searchCompByCheckoutId(int id);

	public Company searchCompanyByCompaniesUserId(int id);

	public Company searchCompanyByOffer(int offerId);

	public List<Company> searchByFancyName(String name);

}
