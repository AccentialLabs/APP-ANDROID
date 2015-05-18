package com.accential.trueone.interfaces;

import java.util.List;
import java.util.Map;

import com.accential.trueone.bean.CompanySubCategory;
/**
 * 
 * @author Matheus Odilon - accentialbrasil
 *
 */
@SuppressWarnings("all")
public interface ICompanySubCategory {

	List<CompanySubCategory> listSubCategories(Map params);
	int countSubCategories(Map params);
	public List<CompanySubCategory> listAllCategories(Map params);
	public List<CompanySubCategory> searchByCategory(int id, Map params);
	public CompanySubCategory searchById(int id);
	List<CompanySubCategory> getByCategoryId(int categoryId);
	
}
