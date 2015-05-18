package com.accential.trueone.interfaces;

import java.util.List;
import java.util.Map;

import com.accential.trueone.bean.CompanyCategory;
/**
 * 
 * @author Matheus Odilon - accentialbrasil
 *
 */
@SuppressWarnings("all")
public interface ICompanyCategory {

	List<CompanyCategory> listCategories(Map params);
	int countCategories(Map params);
	public List<CompanyCategory> listAllCategories(Map params);
	public CompanyCategory searchById(int id, Map params);
	public List<CompanyCategory> getAllCompaniesCategory();

	
}
