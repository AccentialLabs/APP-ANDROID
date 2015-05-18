package com.accential.trueone.bo;

import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import com.accential.trueone.bean.Company;
import com.accential.trueone.daofactory.DAOFactory;
import com.accential.trueone.interfaces.IOCompany;

public class CompanyBO {

	// Criando a instancia do dao com AbstractDAOFactory design pattern
	private static IOCompany dao = DAOFactory.whichFactory(DAOFactory.JSON)
			.JSONDAOCompany();

	@SuppressLint("SimpleDateFormat")
	@SuppressWarnings("rawtypes")
	public static List<Company> listCompanies(Map params) {
		return dao.listCompanies(params);
	}

	@SuppressLint("SimpleDateFormat")
	@SuppressWarnings("rawtypes")
	public static int countCompany(Map params) {
		return dao.countCompany(params);
	}

	@SuppressWarnings("rawtypes")
	public static List<Company> listAllCompanies(Map params) {
		return dao.listAllCompanies(params);
	}

	public static Company searchById(int id) {
		return dao.searchById(id);
	}

	public static Company searchCompByCheckoutId(int id) {
		return dao.searchCompByCheckoutId(id);
	}

	public static Company searchCompanyByCompaniesUserId(int id) {
		return dao.searchCompanyByCompaniesUserId(id);
	}

	public static Company searchCompanyByOffer(int offerId) {
		return dao.searchCompanyByOffer(offerId);
	}

	public static List<Company> searchByFancyName(String name) {
		return dao.searchByFancyName(name);
	}

}
