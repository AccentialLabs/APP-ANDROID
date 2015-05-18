package com.accential.trueone.bo;

import java.util.List;
import java.util.Map;

import com.accential.trueone.bean.CompaniesUser;
import com.accential.trueone.daofactory.DAOFactory;
import com.accential.trueone.interfaces.ICompaniesUser;

@SuppressWarnings("all")
/**
 * 
 * @author Matheus Odilon - accentialbrasil
 * 
 */
public class CompaniesUserBO {

	private static ICompaniesUser dao = DAOFactory
			.whichFactory(DAOFactory.JSON).JSONDAOCompaniesUser();

	@SuppressWarnings("rawtypes")
	public static List<CompaniesUser> listCompaniesUsers(Map params) {
		return dao.listCompaniesUsers(params);
	}

	@SuppressWarnings("rawtypes")
	public static List<CompaniesUser> listAllCompaniesUsers(Map params) {
		return dao.listAllCompaniesUsers(params);
	}

	public static int countCompaniesUsers(int userId) {
		return dao.countCompaniesUsers(userId);
	}

	@SuppressWarnings("rawtypes")
	public static int returnAtributeId(Map params, String atribute) {
		return dao.returnAtributeId(params, atribute);
	}

	public static List<CompaniesUser> returnObjCompaniesUser(int userId) {
		return dao.returnObjCompaniesUser(userId);
	}

	public static List<CompaniesUser> searchByName(String compName) {
		return dao.searchByName(compName);
	}

	public static void inactiveCompsUser(int id) {
		dao.inactiveCompsUser(id);
	}

	public static void saveMyCompaniesUser(CompaniesUser compUser) {
		dao.saveMyCompaniesUser(compUser);
	}

	public static List<CompaniesUser> listAllCompaniesUsersNoFilters(Map params) {
		return dao.listAllCompaniesUsersNoFilters(params);
	}

	public static List<CompaniesUser> returnObjCompaniesUserNoFilter(int userId) {
		return dao.returnObjCompaniesUserNoFilter(userId);
	}

	public static void activateCompsUser(int id) {
		dao.activateCompsUser(id);
	}

	public static List<CompaniesUser> listCompaniesUsersNoFilter(Map params) {
		return dao.listCompaniesUsersNoFilter(params);
	}

	public static List<CompaniesUser> getInactivesSignatures(int userId) {
		return dao.getInactivesSignatures(userId);
	}

	/**
	 * Faz a pesquisa e insere ou altera o registro dependendo do retorno.
	 * 
	 * @param userId
	 * @param compId
	 */
	public static void checkSignature(int userId, int compId) {
		dao.checkSignature(userId, compId);
	}

	/**
	 * USADO ATUALMENTE - Carrega lista de assinaturas ativas do usuario!!!
	 * 
	 * @param userId
	 * @return
	 */
	public static List<CompaniesUser> listByUser(int userId) {
		return dao.listByUser(userId);
	}
}
