package com.accential.trueone.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

import com.accential.trueone.bean.Company;
import com.accential.trueone.bean.CompanyPreference;
import com.accential.trueone.bo.CompanyPreferenceBO;
import com.accential.trueone.bo.UtilityComponentBO;
import com.accential.trueone.interfaces.ICompanyPreference;
import com.accential.trueone.utils.JSONUtils;

@SuppressWarnings("all")
public class CompanyPreferenceDAO implements ICompanyPreference {

	public List<CompanyPreference> listAllSQL(Map params) {
		List<CompanyPreference> preferences = null;
		UtilityComponentBO bo = null;

		try {
			bo = new UtilityComponentBO();
			preferences = new ArrayList<CompanyPreference>();

			JSONArray array = bo.urlRequestToGetData("users", "query", params);

			if (array != null) {
				List<HashMap> objs = JSONUtils.toList(array);

				CompanyPreference preference = null;
				Company company = null;

				Map values = null;
				Map compValues = null;

				// para extrair o metodo alt+shift+M
				if (!objs.isEmpty()) {
					for (HashMap obj : objs) {
						values = JSONUtils.toMap(new JSONObject((Map) obj
								.get("company_preferences")));
						preference = new CompanyPreference();

						preference.setId(Integer.parseInt((String) values
								.get("id")));
						preference.setCpf(String.valueOf(values.get("cpf")));
						preference.setBank(String.valueOf(values.get("bank")));
						preference.setAgency(String.valueOf(values
								.get("agency")));
						preference.setAccount(String.valueOf(values
								.get("account")));
						preference.setAccountName(String.valueOf(values
								.get("account_name")));
						preference.setBackAccountStatus(String.valueOf(values
								.get("bank_account_status")));
						preference
								.setShippingValue(Float.valueOf((String) values
										.get("shipping_value")));
						preference
								.setDeliveryTime(Integer
										.parseInt((String) values
												.get("delivery_time")));
						int correios = Integer.parseInt((String) values
								.get("use_correios_api"));
						if (correios == 1) {
							preference.setUseCorreiosApi(true);
						} else {
							preference.setUseCorreiosApi(false);
						}

						/*
						 * Company
						 */
						compValues = JSONUtils.toMap(new JSONObject((Map) obj
								.get("companies")));

						company = new Company();
						company.setId(Integer.parseInt((String) compValues
								.get("id")));
						company.setCorporate_name(String.valueOf(compValues
								.get("corporate_name")));
						company.setFancy_name(String.valueOf(compValues
								.get("fancy_name")));
						company.setDescription(String.valueOf(compValues
								.get("description")));
						company.setSite_url(String.valueOf(compValues
								.get("site_url")));
						company.setCategory_id(Integer
								.parseInt((String) compValues
										.get("category_id")));
						company.setSub_category_id(Integer
								.parseInt((String) compValues
										.get("sub_category_id")));

						company.setCnpj(String.valueOf(compValues.get("cnpj")));
						company.setEmail(String.valueOf(compValues.get("email")));
						company.setPassword(String.valueOf(compValues
								.get("password")));
						company.setPhone(String.valueOf(compValues.get("phone")));
						company.setPhone_2(String.valueOf(compValues
								.get("phone_2")));
						company.setAddress(String.valueOf(compValues
								.get("address")));
						// company.setComplement(String.valueOf(values.get("complement")));
						company.setCity(String.valueOf(compValues.get("city")));
						company.setState(String.valueOf(compValues.get("state")));
						company.setDistrict(String.valueOf(compValues
								.get("district")));
						company.setNumber(String.valueOf(compValues
								.get("number")));
						company.setZip_code(String.valueOf(compValues
								.get("zip_code")));
						company.setResponsible_name(String.valueOf(compValues
								.get("responsible_name")));
						company.setResponsible_cpf(String.valueOf(compValues
								.get("responsible_cpf")));
						company.setResponsible_email(String.valueOf(compValues
								.get("responsible_email")));
						company.setResponsible_phone(String.valueOf(compValues
								.get("responsible_phone")));
						company.setResponsible_phone_2(String
								.valueOf(compValues.get("responsible_phone_2")));
						company.setResponsible_cel_phone(String
								.valueOf(compValues
										.get("responsible_cel_phone")));
						company.setLogo(String.valueOf(compValues.get("logo")));
						company.setStatus(String.valueOf(compValues
								.get("status")));
						company.setLogin_moip(String.valueOf(compValues
								.get("login_moip")));
						company.setRegister(String.valueOf(compValues
								.get("register")));

						preference.setCompany(company);
						preferences.add(preference);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return preferences;
	}

	public List<CompanyPreference> listCompaniesPreferences(Map params) {

		List<CompanyPreference> preferences = null;
		UtilityComponentBO bo = null;

		try {
			bo = new UtilityComponentBO();
			preferences = new ArrayList<CompanyPreference>();

			JSONArray array = bo
					.urlRequestToGetData("companies", "all", params);

			if (array != null) {
				List<HashMap> objs = JSONUtils.toList(array);

				CompanyPreference preference = null;
				Company company = null;
				Map values = null;
				Map compValues = null;

				Log.e("OBJS", "OBJS COMPANYPREFERENCE: " + objs.toString());

				// para extrair o metodo alt+shift+M
				if (!objs.isEmpty()) {
					for (HashMap obj : objs) {
						values = JSONUtils.toMap(new JSONObject((Map) obj
								.get("CompanyPreference")));
						preference = new CompanyPreference();

						preference.setId(Integer.parseInt((String) values
								.get("id")));
						preference.setCpf(String.valueOf(values.get("cpf")));
						preference.setBank(String.valueOf(values.get("bank")));
						preference.setAgency(String.valueOf(values
								.get("agency")));
						preference.setAccount(String.valueOf(values
								.get("account")));
						preference.setAccountName(String.valueOf(values
								.get("account_name")));
						preference.setBackAccountStatus(String.valueOf(values
								.get("bank_account_status")));
						preference
								.setShippingValue(Float.valueOf((String) values
										.get("shipping_value")));
						preference
								.setDeliveryTime(Integer
										.parseInt((String) values
												.get("delivery_time")));
						int correios = Integer.parseInt((String) values
								.get("use_correios_api"));
						if (correios == 1) {
							preference.setUseCorreiosApi(true);
						} else {
							preference.setUseCorreiosApi(false);
						}

						preferences.add(preference);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return preferences;
	}

	public List<CompanyPreference> listPreferencesByCompany(int companyId) {

		AsyncTask<Map, Void, List<CompanyPreference>> async = new AsyncTask<Map, Void, List<CompanyPreference>>() {
			@Override
			protected List<CompanyPreference> doInBackground(Map... params) {
				List<CompanyPreference> preferences = CompanyPreferenceBO
						.listCompaniesPreferences(params[0]);
				return preferences;
			}

		};

		try {

			Map<String, Map<String, Map<String, String>>> key = new HashMap<String, Map<String, Map<String, String>>>();
			// Map key = new HashMap();
			Map<String, Map<String, String>> params = new HashMap<String, Map<String, String>>();
			Map<String, String> conditions = new HashMap<String, String>();

			conditions.put("CompanyPreference.company_id",
					String.valueOf(companyId));
			params.put("conditions", conditions);
			key.put("CompanyPreference", params);

			return async.execute(key).get();

		} catch (Exception e) {
			e.printStackTrace();
			Log.e("Erro CompanyPreferenceDAO", "Erro no CompanyPreferenceDAO");
			return null;
		}
	}

	public List<CompanyPreference> searchByOfferId(int offerId) {

		AsyncTask<Map, Void, List<CompanyPreference>> async = new AsyncTask<Map, Void, List<CompanyPreference>>() {
			@Override
			protected List<CompanyPreference> doInBackground(Map... params) {
				CompanyPreferenceDAO dao = new CompanyPreferenceDAO();
				return dao.listAllSQL(params[0]);
			}
		};

		try {

			Map param = new HashMap();
			Map query = new HashMap();

			query.put(
					"query",
					"select * from companies INNER JOIN company_preferences ON company_preferences.company_id = companies.id INNER JOIN offers ON offers.company_id = companies.id where offers.id = "
							+ String.valueOf(offerId) + ";");
			param.put("User", query);

			List<CompanyPreference> preferences = async.execute(param).get();

			return preferences;

		} catch (Exception e) {
			Log.e("Error",
					"Erro em ao listar CompanyPreference. Verifique os paramentros e tente novamente! CompanyPreferenceDAO - searchByCompanyId");
			e.printStackTrace();
			return null;
		}

	}
}
