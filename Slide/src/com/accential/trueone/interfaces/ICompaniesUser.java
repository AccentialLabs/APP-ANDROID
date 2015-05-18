package com.accential.trueone.interfaces;

import java.util.List;
import java.util.Map;

import com.accential.trueone.bean.CompaniesUser;

@SuppressWarnings("all")
/**
 * 
 * @author Matheus Odilon - accentialbrasil
 * 
 */
public interface ICompaniesUser {

	@SuppressWarnings("rawtypes")
	public List<CompaniesUser> listCompaniesUsers(Map params);

	@SuppressWarnings("rawtypes")
	public List<CompaniesUser> listAllCompaniesUsers(Map params);

	public int countCompaniesUsers(int userId);

	@SuppressWarnings("rawtypes")
	public int returnAtributeId(Map params, String atribute);

	public List<CompaniesUser> returnObjCompaniesUser(int userId);

	public List<CompaniesUser> searchByName(String compName);

	public void saveMyCompaniesUser(CompaniesUser compUser);

	public void inactiveCompsUser(int id);

	public List<CompaniesUser> listAllCompaniesUsersNoFilters(Map params);

	public List<CompaniesUser> returnObjCompaniesUserNoFilter(int userId);

	public void activateCompsUser(int id);

	public List<CompaniesUser> listCompaniesUsersNoFilter(Map params);

	public List<CompaniesUser> getInactivesSignatures(int userId);

	public void checkSignature(int userId, int compId);

	public List<CompaniesUser> listByUser(int userId);

}
