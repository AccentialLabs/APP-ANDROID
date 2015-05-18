package com.accential.trueone.bo;

import java.util.List;
import java.util.Map;

import com.accential.trueone.bean.CompanyPreference;
import com.accential.trueone.daofactory.DAOFactory;
import com.accential.trueone.interfaces.ICompanyPreference;

@SuppressWarnings("all")
public class CompanyPreferenceBO {

	private static ICompanyPreference dao = DAOFactory.whichFactory(
			DAOFactory.JSON).JSONDAOCompanyPreference();

	public static List<CompanyPreference> listCompaniesPreferences(Map params) {
		return dao.listCompaniesPreferences(params);
	}

	/**
	 * Lista as preferencias da companhia de acordo com o id enviado
	 * 
	 * @param companyId
	 * @return list
	 */
	public static List<CompanyPreference> listPreferencesByCompany(int companyId) {
		return dao.listPreferencesByCompany(companyId);
	}

	public static List<CompanyPreference> searchByOfferId(int offerId) {
		return dao.searchByOfferId(offerId);
	}

}
