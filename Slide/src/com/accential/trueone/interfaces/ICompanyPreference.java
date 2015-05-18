package com.accential.trueone.interfaces;

import java.util.List;
import java.util.Map;

import com.accential.trueone.bean.CompanyPreference;

@SuppressWarnings("all")
public interface ICompanyPreference {

	public List<CompanyPreference> listCompaniesPreferences(Map params);

	public List<CompanyPreference> listPreferencesByCompany(int companyId);

	public List<CompanyPreference> searchByOfferId(int offerId);

}
