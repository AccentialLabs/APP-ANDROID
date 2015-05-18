package com.accential.trueone.interfaces;

import java.util.List;
import java.util.Map;

import com.accential.trueone.bean.CompaniesInvitationsFilter;

public interface ICompaniesInvitationsFilter {

	@SuppressWarnings("rawtypes")
	List<CompaniesInvitationsFilter> listCompaniesInvitationsFilter(Map params);
	@SuppressWarnings("rawtypes")
	List<CompaniesInvitationsFilter> listAllCompaniesInvitationsFilter(Map paramas);
	CompaniesInvitationsFilter searchById(int invitationId);
	
}
