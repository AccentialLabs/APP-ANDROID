package com.accential.trueone.bo;

import java.util.List;
import java.util.Map;

import com.accential.trueone.bean.CompanyCategory;
import com.accential.trueone.daofactory.DAOFactory;
import com.accential.trueone.interfaces.ICompanyCategory;

/**
 * 
 * @author Matheus Odilon - accentialbrasil
 * 
 */
@SuppressWarnings("all")
public class CompanyCategoryBO {

	private static ICompanyCategory dao = DAOFactory.whichFactory(
			DAOFactory.JSON).JSONDAOCompanyCategory();

	public static List<CompanyCategory> listCategories(Map params) {
		return dao.listCategories(params);
	}

	public static int countCategories(Map params) {
		return dao.countCategories(params);
	}

	public static List<CompanyCategory> listAllCategories(Map params) {
		return dao.listAllCategories(params);
	}

	public static CompanyCategory searchById(int id, Map params) {
		return dao.searchById(id, params);
	}

	public static List<CompanyCategory> getAllCompaniesCategory() {
		return dao.getAllCompaniesCategory();
	}

}
