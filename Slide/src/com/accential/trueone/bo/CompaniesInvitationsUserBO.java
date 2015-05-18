package com.accential.trueone.bo;

import java.util.List;
import java.util.Map;

import com.accential.trueone.bean.CompaniesInvitationsUser;
import com.accential.trueone.daofactory.DAOFactory;
import com.accential.trueone.interfaces.ICompaniesInvitationsUser;

/**
 * 
 * @author Matheus Odilon - accentialbrasil
 * 
 */
public class CompaniesInvitationsUserBO {

	private static ICompaniesInvitationsUser dao = DAOFactory.whichFactory(
			DAOFactory.JSON).JSONDAOCompaniesInvitationsUsers();

	public static List<CompaniesInvitationsUser> listCompaniesInvitationsUser(
			Map params) {
		return dao.listCompaniesInvitationsUser(params);
	}

	public static List<CompaniesInvitationsUser> listAllCompaniesInvitationsUser(
			Map params) {
		return dao.listAllCompaniesInvitationsUser(params);
	}

	public static List<CompaniesInvitationsUser> searchByUserId(int userId) {
		return dao.searchByUserId(userId);
	}

	public static List<CompaniesInvitationsUser> returnObjsCompInviteUser(
			int userId) {
		return dao.returnObjsCompInviteUser(userId);
	}

	public static void inactivateInvite(int id) {
		dao.inactivateInvite(id);
	}

	public static void activateInvite(int id, CompaniesInvitationsUser invite) {
		dao.activateInvite(id, invite);
	}

	/**
	 * ATUAL!!!! Pesquisa convites e empresa.
	 * 
	 * @param userId
	 * @return CompaniesInvitationsUser, Company
	 */
	public static List<CompaniesInvitationsUser> searchInvitationsWaiting(
			int userId) {
		return dao.searchInvitationsWaiting(userId);
	}

	/**
	 * Retorna número de convites em espera
	 * 
	 * @param userId
	 * @return int quantidade
	 */
	public static int countInvitationsWaiting(int userId) {
		return dao.countInvitationsWaiting(userId);
	}
}
