package com.accential.trueone.interfaces;

import java.util.List;
import java.util.Map;

import com.accential.trueone.bean.CompaniesInvitationsUser;

/**
 * 
 * @author Matheus Odilon - accentialbrasil
 * 
 */
public interface ICompaniesInvitationsUser {

	@SuppressWarnings("rawtypes")
	List<CompaniesInvitationsUser> listCompaniesInvitationsUser(Map params);

	@SuppressWarnings("rawtypes")
	List<CompaniesInvitationsUser> listAllCompaniesInvitationsUser(Map params);

	List<CompaniesInvitationsUser> searchByUserId(int userId);

	public List<CompaniesInvitationsUser> returnObjsCompInviteUser(int userId);

	void activateInvite(int id, CompaniesInvitationsUser invite);

	void inactivateInvite(int id);

	public List<CompaniesInvitationsUser> searchInvitationsWaiting(int userId);
	
	public int countInvitationsWaiting(int userId);
}
