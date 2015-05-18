package com.accential.trueone.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;

import com.accential.trueone.bean.CompaniesUser;
import com.accential.trueone.bean.Company;
import com.accential.trueone.bean.Offer;
import com.accential.trueone.bean.User;
import com.accential.trueone.bo.CheckoutBO;
import com.accential.trueone.bo.CompaniesUserBO;
import com.accential.trueone.bo.CompanyBO;
import com.accential.trueone.bo.UtilityComponentBO;
import com.accential.trueone.interfaces.IOCompany;
import com.accential.trueone.utils.JSONUtils;

@SuppressWarnings("all")
public class CompanyDAO implements IOCompany {

	/**
	 * @author Wilson Junior
	 * @param Map
	 * @return List<Offer>
	 */
	@SuppressLint("SimpleDateFormat")
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<Company> listCompanies(Map params) {

		List<Company> companies = null;
		UtilityComponentBO bo = null;

		try {
			bo = new UtilityComponentBO();
			companies = new ArrayList<Company>();

			JSONArray array = bo
					.urlRequestToGetData("companies", "all", params);
			// Log.i("INFORMACAO_LOG", array.toString());

			if (array != null) {
				List<HashMap> objs = JSONUtils.toList(array);

				Company company = null;
				Map values = null;
				Log.e("", objs.toString());
				SimpleDateFormat sdf = new SimpleDateFormat(
						"dd/MM/yyyy HH:mm:ss");

				if (!objs.isEmpty()) {
					for (HashMap obj : objs) {
						values = JSONUtils.toMap(new JSONObject((Map) obj
								.get("Company")));
						company = new Company();
						company.setId(Integer.parseInt((String) values
								.get("id")));
						company.setCorporate_name(String.valueOf(values
								.get("corporate_name")));
						company.setFancy_name(String.valueOf(values
								.get("fancy_name")));
						company.setDescription(String.valueOf(values
								.get("description")));
						company.setSite_url(String.valueOf(values
								.get("site_url")));
						company.setCategory_id(Integer.parseInt((String) values
								.get("category_id")));
						company.setSub_category_id(Integer
								.parseInt((String) values
										.get("sub_category_id")));

						company.setCnpj(String.valueOf(values.get("cnpj")));
						company.setEmail(String.valueOf(values.get("email")));
						company.setPassword(String.valueOf(values
								.get("password")));
						company.setPhone(String.valueOf(values.get("phone")));
						company.setPhone_2(String.valueOf(values.get("phone_2")));
						company.setAddress(String.valueOf(values.get("address")));
						// company.setComplement(String.valueOf(values.get("complement")));
						company.setCity(String.valueOf(values.get("city")));
						company.setState(String.valueOf(values.get("state")));
						company.setDistrict(String.valueOf(values
								.get("district")));
						company.setNumber(String.valueOf(values.get("number")));
						company.setZip_code(String.valueOf(values
								.get("zip_code")));
						company.setResponsible_name(String.valueOf(values
								.get("responsible_name")));
						company.setResponsible_cpf(String.valueOf(values
								.get("responsible_cpf")));
						company.setResponsible_email(String.valueOf(values
								.get("responsible_email")));
						company.setResponsible_phone(String.valueOf(values
								.get("responsible_phone")));
						company.setResponsible_phone_2(String.valueOf(values
								.get("responsible_phone_2")));
						company.setResponsible_cel_phone(String.valueOf(values
								.get("responsible_cel_phone")));
						company.setLogo(String.valueOf(values.get("logo")));
						company.setStatus(String.valueOf(values.get("status")));
						company.setLogin_moip(String.valueOf(values
								.get("login_moip")));
						company.setRegister(String.valueOf(values
								.get("register")));

						/*
						 * //parse to calendar Calendar date_register =
						 * Calendar.getInstance(); date_register.setTime((Date)
						 * sdf
						 * .parse(String.valueOf(values.get("date_register")).
						 * replace("-", "/")));
						 * 
						 * company.setDate_register(date_register);
						 */

						companies.add(company);
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return companies;
	}

	@SuppressLint("SimpleDateFormat")
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public int countCompany(Map params) {
		int contador_companies = 0;
		UtilityComponentBO bo = null;

		try {
			bo = new UtilityComponentBO();
			JSONArray array = bo
					.urlRequestToGetData("companies", "all", params);

			if (array != null) {

				contador_companies = array.length();
				array = null;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return contador_companies;
	}

	@Override
	public List<Company> listAllCompanies(Map params) {

		AsyncTask<Map, Void, List<Company>> async = new AsyncTask<Map, Void, List<Company>>() {
			@Override
			protected List<Company> doInBackground(Map... params) {
				List<Company> companies = CompanyBO.listCompanies(params[0]);
				return companies;
			}
		};
		try {
			return async.execute(params).get();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * O METODO CONSISTE EM BUSCAR UMA LISTA COM TODAS AS COMPANIES E BUSCAR
	 * DENTRO DESTA LISTA UMA COM ID ESPECIFICO
	 * 
	 * @param int
	 * @author Matheus Odilon - accentialbrasil
	 * @return Company
	 */
	public Company searchById(int id) {

		Company comp = new Company();
		Map<String, Map<String, Map<String, String>>> key = new HashMap<String, Map<String, Map<String, String>>>();
		Map<String, Map<String, String>> params = new HashMap<String, Map<String, String>>();
		Map<String, String> conditions = new HashMap<String, String>();

		params.put("conditions", conditions);
		key.put("Company", params);

		List<Company> companies = CompanyBO.listAllCompanies(key);
		for (Company company : companies) {
			if (company.getId() == id) {
				comp.setId(company.getId());
				comp.setFancy_name(company.getFancy_name());
				// comp.setCorporate_name(company.getCorporate_name());
				comp.setEmail(company.getEmail());
				comp.setPhone(company.getPhone());
				comp.setLogo(company.getLogo());
				comp.setDescription(company.getDescription());
				comp.setLogo(company.getLogo());
				comp.setZip_code(company.getZip_code());
				comp.setLogin_moip(company.getLogin_moip());

				// TESTE CEP
				Log.i("COMPANYDAO - SEARCHBYID zipCod", company.getZip_code());
			}
		}

		return comp;
	}

	public int retornaCompId(Map params) {
		int id = 0;
		UtilityComponentBO bo = null;

		try {
			bo = new UtilityComponentBO();

			JSONArray array = bo.urlRequestToGetData("offers", "all", params);

			if (array != null) {
				List<HashMap> objs = JSONUtils.toList(array);
				Map values = null;
				if (!objs.isEmpty()) {
					for (HashMap obj : objs) {
						values = JSONUtils.toMap(new JSONObject((Map) obj
								.get("Offer")));
						id = Integer
								.parseInt((String) values.get("company_id"));
					}
				}
			}
		} catch (Exception e) {
			Log.e("ERRO NO METODO", "N��O PODE RETORNAR ID");
			e.printStackTrace();
		}
		return id;
	}

	// BUSCA COMPANY
	public Company searchCompByCheckoutId(int id) {

		Map<String, Map<String, Map<String, String>>> key = new HashMap<String, Map<String, Map<String, String>>>();
		Map<String, Map<String, String>> params = new HashMap<String, Map<String, String>>();
		Map<String, String> conditions = new HashMap<String, String>();

		conditions.put("Checkout.id", String.valueOf(id));
		params.put("conditions", conditions);
		key.put("Checkout", params);

		AsyncTask<Map, Void, Integer> async = new AsyncTask<Map, Void, Integer>() {

			@Override
			protected Integer doInBackground(Map... params) {
				int i = CheckoutBO.returnsObejectId(params[0], "company_id");
				return i;
			}
		};
		int y = 0;
		try {
			y = async.execute(key).get();
			return CompanyBO.searchById(y);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Company searchCompanyByCompaniesUserId(int id) {
		Map<String, Map<String, Map<String, String>>> key2 = new HashMap<String, Map<String, Map<String, String>>>();
		Map<String, Map<String, String>> params2 = new HashMap<String, Map<String, String>>();
		Map<String, String> conditions2 = new HashMap<String, String>();

		conditions2.put("CompaniesUser.id", String.valueOf(id));
		params2.put("conditions", conditions2);
		key2.put("CompaniesUser", params2);

		AsyncTask<Map, Void, Integer> async = new AsyncTask<Map, Void, Integer>() {
			@Override
			protected Integer doInBackground(Map... params) {
				int i = CompaniesUserBO.returnAtributeId(params[0],
						"company_id");
				return i;
			}
		};
		try {
			int y = async.execute(key2).get();
			return CompanyBO.searchById(y);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public Company searchCompanyByOffer(int offerId) {

		Map<String, Map<String, Map<String, String>>> key = new HashMap<String, Map<String, Map<String, String>>>();
		Map<String, Map<String, String>> params = new HashMap<String, Map<String, String>>();
		Map<String, String> conditions = new HashMap<String, String>();

		conditions.put("Offer.id", String.valueOf(offerId));
		params.put("conditions", conditions);
		key.put("Offer", params);

		AsyncTask<Map, Void, Integer> async = new AsyncTask<Map, Void, Integer>() {
			@Override
			protected Integer doInBackground(Map... params) {

				return retornaCompId(params[0]);
			}
		};

		try {
			int i = async.execute(key).get();
			return CompanyBO.searchById(i);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<Company> searchByFancyName(String name) {

		AsyncTask<Map, Void, List<Company>> async = new AsyncTask<Map, Void, List<Company>>() {
			@Override
			protected List<Company> doInBackground(Map... params) {
				List<Company> companies = CompanyBO.listCompanies(params[0]);
				return companies;
			}
		};

		try {

			Map<String, Map<String, Map<String, String>>> key = new HashMap<String, Map<String, Map<String, String>>>();
			Map<String, Map<String, String>> params = new HashMap<String, Map<String, String>>();
			Map<String, String> conditions = new HashMap<String, String>();

			String compName = "%" + name + "%";
			conditions.put("Company.fancy_name LIKE", compName);
			params.put("conditions", conditions);
			key.put("Company", params);

			return async.execute(key).get();

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

}
