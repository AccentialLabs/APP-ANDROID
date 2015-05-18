package com.accential.trueone.bo;

import java.util.List;
import java.util.Map;

import com.accential.trueone.bean.CompaniesInvitationsFilter;
import com.accential.trueone.daofactory.DAOFactory;
import com.accential.trueone.interfaces.ICompaniesInvitationsFilter;

/**
 * 
 * @author Matheus Odilon - accentialbrasil
 *
 */
@SuppressWarnings("all")
public class CompaniesInvitationsFilterBO {

	private static ICompaniesInvitationsFilter dao = DAOFactory.whichFactory(DAOFactory.JSON).JSONDAOCompaniesInvitationsFilter();


	public static List<CompaniesInvitationsFilter> listCompaniesInvitationsFilter(Map params) {
		return dao.listCompaniesInvitationsFilter(params);
	}

	public static List<CompaniesInvitationsFilter> listAllCompaniesInvitationsFilter(Map params) {
		return dao.listAllCompaniesInvitationsFilter(params);
	}
	
	public static CompaniesInvitationsFilter searchById(int invitationId) {
		return dao.searchById(invitationId);
	}

}
