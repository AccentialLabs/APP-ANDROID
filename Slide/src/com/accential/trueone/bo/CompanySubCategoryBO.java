package com.accential.trueone.bo;

import java.util.List;
import java.util.Map;

import com.accential.trueone.bean.CompanyCategory;
import com.accential.trueone.bean.CompanySubCategory;
import com.accential.trueone.daofactory.DAOFactory;
import com.accential.trueone.interfaces.ICompanySubCategory;

/**
 * 
 * @author Matheus Odilon - accentialbrasil
 * 
 */
@SuppressWarnings("all")
public class CompanySubCategoryBO {

	private static ICompanySubCategory dao = DAOFactory.whichFactory(
			DAOFactory.JSON).JSONDAOCompanySubCategory();

	/**
	 * Retorna todas subCategorias em uma lista JSON
	 * 
	 * @param Map
	 *            params
	 * @return Lista JSON
	 */
	public static List<CompanySubCategory> listSubCategories(Map params) {
		return dao.listSubCategories(params);
	}

	public static int countSubCategories(Map params) {
		return dao.countSubCategories(params);
	}

	public static List<CompanySubCategory> listAllCategories(Map params) {
		return dao.listAllCategories(params);
	}

	public static List<CompanySubCategory> searchByCategoryId(int id, Map params) {
		return dao.searchByCategory(id, params);
	}

	public static List<CompanySubCategory> getByCategoryId(int categoryId) {
		return dao.getByCategoryId(categoryId);
	}

}
