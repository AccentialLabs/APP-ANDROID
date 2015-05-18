package com.accential.trueone.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

import com.accential.trueone.bean.CompaniesUser;
import com.accential.trueone.bean.Company;
import com.accential.trueone.bean.User;
import com.accential.trueone.bo.CompaniesUserBO;
import com.accential.trueone.bo.CompanyBO;
import com.accential.trueone.bo.UserBO;
import com.accential.trueone.bo.UtilityComponentBO;
import com.accential.trueone.interfaces.ICompaniesUser;
import com.accential.trueone.utils.JSONUtils;

/**
 * 
 * @author Matheus Odilon - accentialbrasil
 * 
 */
@SuppressWarnings("all")
public class CompaniesUserDAO implements ICompaniesUser {

	public List<CompaniesUser> listCompaniesUsersSQL(Map params) {
		List<CompaniesUser> companies = null;
		UtilityComponentBO bo = null;

		try {

			companies = new ArrayList<CompaniesUser>();
			bo = new UtilityComponentBO();

			JSONArray array = bo.urlRequestToGetData("users", "query", params);

			if (array != null) {
				List<HashMap> objs = JSONUtils.toList(array);
				Company comp = null;
				CompaniesUser company = null;
				Map values = null;
				Map compValues = null;

				if (!objs.isEmpty()) {
					for (HashMap obj : objs) {
						values = JSONUtils.toMap(new JSONObject((Map) obj
								.get("companies_users")));

						company = new CompaniesUser();
						company.setId(Integer.parseInt((String) values
								.get("id")));
						company.setStatus(String.valueOf(values.get("status")));
						company.setLast_status(String.valueOf(values
								.get("last_status")));
						Calendar dateRegister = Calendar.getInstance();
						String dateTime = String.valueOf(
								values.get("date_register")).replace("-", "/");
						Date date = new Date(dateTime);
						dateRegister.setTime(date);
						company.setDateRegister(dateRegister);

						// infos da comp
						compValues = JSONUtils.toMap(new JSONObject((Map) obj
								.get("companies")));

						comp = new Company();
						comp.setId(Integer.parseInt((String) compValues
								.get("id")));
						comp.setCorporate_name(String.valueOf(compValues
								.get("corporate_name")));
						comp.setFancy_name(String.valueOf(compValues
								.get("fancy_name")));
						comp.setDescription(String.valueOf(compValues
								.get("description")));
						comp.setSite_url(String.valueOf(compValues
								.get("site_url")));
						comp.setCategory_id(Integer
								.parseInt((String) compValues
										.get("category_id")));
						comp.setSub_category_id(Integer
								.parseInt((String) compValues
										.get("sub_category_id")));

						comp.setCnpj(String.valueOf(compValues.get("cnpj")));
						comp.setEmail(String.valueOf(compValues.get("email")));
						comp.setPassword(String.valueOf(compValues
								.get("password")));
						comp.setPhone(String.valueOf(compValues.get("phone")));
						comp.setPhone_2(String.valueOf(compValues
								.get("phone_2")));
						comp.setAddress(String.valueOf(compValues
								.get("address")));
						// company.setComplement(String.valueOf(values.get("complement")));
						comp.setCity(String.valueOf(compValues.get("city")));
						comp.setState(String.valueOf(compValues.get("state")));
						comp.setDistrict(String.valueOf(compValues
								.get("district")));
						comp.setNumber(String.valueOf(compValues.get("number")));
						comp.setZip_code(String.valueOf(compValues
								.get("zip_code")));
						comp.setResponsible_name(String.valueOf(compValues
								.get("responsible_name")));
						comp.setResponsible_cpf(String.valueOf(compValues
								.get("responsible_cpf")));
						comp.setResponsible_email(String.valueOf(compValues
								.get("responsible_email")));
						comp.setResponsible_phone(String.valueOf(compValues
								.get("responsible_phone")));
						comp.setResponsible_phone_2(String.valueOf(compValues
								.get("responsible_phone_2")));
						comp.setResponsible_cel_phone(String.valueOf(compValues
								.get("responsible_cel_phone")));
						comp.setLogo(String.valueOf(compValues.get("logo")));
						comp.setStatus(String.valueOf(compValues.get("status")));
						comp.setLogin_moip(String.valueOf(compValues
								.get("login_moip")));
						comp.setRegister(String.valueOf(compValues
								.get("register")));

						company.setCompany(comp);

						companies.add(company);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return companies;
	}

	public List<CompaniesUser> listFirstCompaniesUsers(Map params) {

		List<CompaniesUser> companies = null;
		UtilityComponentBO bo = null;

		try {

			companies = new ArrayList<CompaniesUser>();
			bo = new UtilityComponentBO();

			JSONArray array = bo
					.urlRequestToGetData("companies", "all", params);

			if (array != null) {
				List<HashMap> objs = JSONUtils.toList(array);

				CompaniesUser company = null;
				Map values = null;

				if (!objs.isEmpty()) {
					for (HashMap obj : objs) {
						values = JSONUtils.toMap(new JSONObject((Map) obj
								.get("CompaniesUser")));

						company = new CompaniesUser();
						company.setId(Integer.parseInt((String) values
								.get("id")));
						company.setStatus(String.valueOf(values.get("status")));
						company.setLast_status(String.valueOf(values
								.get("last_status")));

						Calendar dateRegister = Calendar.getInstance();
						String dateTime = String.valueOf(
								values.get("date_register")).replace("-", "/");
						Date date = new Date(dateTime);
						dateRegister.setTime(date);

						company.setDateRegister(dateRegister);

						companies.add(company);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return companies;
	}

	/**
	 * ATEN����O: EXECUTAR ESSE M��TODO SEMPRE EM UMA THREAD SEPARADA
	 * 
	 * @author Matheus Odilon - accentialbrasil
	 * @param Map
	 * @return List<CompaniesUser>
	 */
	@Override
	public List<CompaniesUser> listCompaniesUsers(Map params) {

		List<CompaniesUser> companies = null;
		UtilityComponentBO bo = null;

		try {

			companies = new ArrayList<CompaniesUser>();
			bo = new UtilityComponentBO();

			JSONArray array = bo
					.urlRequestToGetData("companies", "all", params);

			if (array != null) {
				List<HashMap> objs = JSONUtils.toList(array);

				CompaniesUser company = null;
				Map values = null;

				if (!objs.isEmpty()) {
					for (HashMap obj : objs) {
						values = JSONUtils.toMap(new JSONObject((Map) obj
								.get("CompaniesUser")));

						if (String.valueOf(values.get("status")).equals(
								"ACTIVE")) {
							company = new CompaniesUser();
							company.setId(Integer.parseInt((String) values
									.get("id")));
							company.setStatus(String.valueOf(values
									.get("status")));
							company.setLast_status(String.valueOf(values
									.get("last_status")));

							Calendar dateRegister = Calendar.getInstance();
							String dateTime = String.valueOf(
									values.get("date_register")).replace("-",
									"/");
							Date date = new Date(dateTime);
							dateRegister.setTime(date);

							company.setDateRegister(dateRegister);

							companies.add(company);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return companies;
	}

	/**
	 * @author Matheus Odilon - accentialbrasil
	 * @param Map
	 * @return List<CompaniesUser>
	 */
	@Override
	public List<CompaniesUser> listAllCompaniesUsers(Map params) {
		AsyncTask<Map, Void, List<CompaniesUser>> async = new AsyncTask<Map, Void, List<CompaniesUser>>() {
			@Override
			protected List<CompaniesUser> doInBackground(Map... params) {
				List<CompaniesUser> companies = CompaniesUserBO
						.listCompaniesUsers(params[0]);
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
	 * BUSCA SEM FILTRO DE STATUS
	 */
	public List<CompaniesUser> listCompaniesUsersNoFilter(Map params) {

		List<CompaniesUser> companies = null;
		UtilityComponentBO bo = null;

		try {

			companies = new ArrayList<CompaniesUser>();
			bo = new UtilityComponentBO();

			JSONArray array = bo
					.urlRequestToGetData("companies", "all", params);

			if (array != null) {
				List<HashMap> objs = JSONUtils.toList(array);

				CompaniesUser company = null;
				Map values = null;

				if (!objs.isEmpty()) {
					for (HashMap obj : objs) {
						values = JSONUtils.toMap(new JSONObject((Map) obj
								.get("CompaniesUser")));

						if (String.valueOf(values.get("status")).equals(
								"INACTIVE")) {
							company = new CompaniesUser();
							company.setId(Integer.parseInt((String) values
									.get("id")));
							company.setStatus(String.valueOf(values
									.get("status")));
							company.setLast_status(String.valueOf(values
									.get("last_status")));

							Calendar dateRegister = Calendar.getInstance();
							String dateTime = String.valueOf(
									values.get("date_register")).replace("-",
									"/");
							Date date = new Date(dateTime);
							dateRegister.setTime(date);

							company.setDateRegister(dateRegister);

							companies.add(company);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return companies;
	}

	public List<CompaniesUser> listAllCompaniesUsersNoFilters(Map params) {
		AsyncTask<Map, Void, List<CompaniesUser>> async = new AsyncTask<Map, Void, List<CompaniesUser>>() {
			@Override
			protected List<CompaniesUser> doInBackground(Map... params) {
				List<CompaniesUser> companies = CompaniesUserBO
						.listCompaniesUsersNoFilter(params[0]);
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

	@Override
	public int countCompaniesUsers(int userId) {
		int countCompaniesUser = 0;
		List<CompaniesUser> companies = null;

		Map<String, Map<String, Map<String, String>>> key2 = new HashMap<String, Map<String, Map<String, String>>>();
		Map<String, Map<String, String>> params2 = new HashMap<String, Map<String, String>>();
		Map<String, String> conditions2 = new HashMap<String, String>();

		conditions2.put("CompaniesUser.user_id", String.valueOf(userId));
		params2.put("conditions", conditions2);
		key2.put("CompaniesUser", params2);

		try {

			companies = CompaniesUserBO.listAllCompaniesUsers(key2);
			countCompaniesUser = companies.size();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return countCompaniesUser;
	}

	@Override
	public int returnAtributeId(Map params, String atribute) {
		int id = 0;
		UtilityComponentBO bo = null;

		try {
			bo = new UtilityComponentBO();
			JSONArray array = bo
					.urlRequestToGetData("companies", "all", params);

			if (array != null) {
				List<HashMap> objs = JSONUtils.toList(array);
				Map values = null;
				if (!objs.isEmpty()) {
					for (HashMap obj : objs) {
						values = JSONUtils.toMap(new JSONObject((Map) obj
								.get("CompaniesUser")));
						id = Integer.parseInt((String) values.get(atribute));
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return id;
	}

	@Override
	public List<CompaniesUser> returnObjCompaniesUser(int userId) {

		CompaniesUser compUser = new CompaniesUser();

		Map<String, Map<String, Map<String, String>>> key = new HashMap<String, Map<String, Map<String, String>>>();
		Map<String, Map<String, String>> params = new HashMap<String, Map<String, String>>();
		Map<String, String> conditions = new HashMap<String, String>();

		conditions.put("CompaniesUser.user_id", String.valueOf(userId));
		params.put("conditions", conditions);
		key.put("CompaniesUser", params);

		List<CompaniesUser> compsUser = CompaniesUserBO
				.listAllCompaniesUsers(key);
		List<CompaniesUser> compUserList = new ArrayList<CompaniesUser>();

		for (CompaniesUser comp : compsUser) {

			Company company = CompanyBO.searchCompanyByCompaniesUserId(comp
					.getId());
			comp.setCompany(company);

			compUserList.add(comp);
		}
		for (CompaniesUser companiesUser : compUserList) {
			Log.i("ID COMP", String.valueOf(companiesUser.getId()));
			Log.i("COMP NAME", companiesUser.getCompany().getFancy_name());
		}

		return compUserList;
	}

	// BUSCA SEM FILTRO
	public List<CompaniesUser> returnObjCompaniesUserNoFilter(int userId) {

		CompaniesUser compUser = new CompaniesUser();

		Map<String, Map<String, Map<String, String>>> key = new HashMap<String, Map<String, Map<String, String>>>();
		Map<String, Map<String, String>> params = new HashMap<String, Map<String, String>>();
		Map<String, String> conditions = new HashMap<String, String>();

		conditions.put("CompaniesUser.user_id", String.valueOf(userId));
		params.put("conditions", conditions);
		key.put("CompaniesUser", params);

		List<CompaniesUser> compsUser = CompaniesUserBO
				.listAllCompaniesUsersNoFilters(key);
		List<CompaniesUser> compUserList = new ArrayList<CompaniesUser>();

		for (CompaniesUser comp : compsUser) {

			Company company = CompanyBO.searchCompanyByCompaniesUserId(comp
					.getId());
			comp.setCompany(company);

			compUserList.add(comp);
		}
		for (CompaniesUser companiesUser : compUserList) {
			Log.i("ID COMP", String.valueOf(companiesUser.getId()));
			Log.i("COMP NAME", companiesUser.getCompany().getFancy_name());
		}

		return compUserList;
	}

	public List<CompaniesUser> searchByName(String compName) {

		List<Company> companies = null;

		Map<String, Map<String, Map<String, String>>> key = new HashMap<String, Map<String, Map<String, String>>>();
		Map<String, Map<String, String>> params = new HashMap<String, Map<String, String>>();
		Map<String, String> conditions = new HashMap<String, String>();

		params.put("conditions", conditions);
		key.put("Company", params);
		List<CompaniesUser> comps = null;

		try {
			companies = CompanyBO.listAllCompanies(key);
			if (companies != null) {
				Log.i("TAMANHO - LISTA DE COMPANIAS - RETORNO",
						String.valueOf(companies.size()));
				/**
				 * ARRUMAR AQUI L��GICA -
				 */
				comps = new ArrayList<CompaniesUser>();

				for (Company company : companies) {
					if (company.getFancy_name().toLowerCase()
							.contains(compName.toLowerCase())) {
						Log.i("FANCY COMP", company.getFancy_name());
						CompaniesUser compsUser = new CompaniesUser();
						compsUser.setCompany(company);
						comps.add(compsUser);
						Log.i("FANCY COMP 2", compsUser.getCompany()
								.getFancy_name());
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return comps;
	}

	/**
	 * ATEN����O: DEVE SER EXECUTADO EM UMA THREAD SEPARADA DA PRINCIPAL METODO
	 * QUE CHAMA A FUN����O 'urlRequestToSaveData' DA API -
	 * 
	 * @param params
	 * @return void
	 */
	public void save(Map params) {

		UtilityComponentBO bo = null;

		try {

			bo = new UtilityComponentBO();

			bo.urlRequestToSaveData("companies", "all", params);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * CRIA UMA THREAD PARA EXECUTAR O METODO 'save()'
	 * 
	 * @param CompaniesUser
	 * @return void
	 */
	public void saveMyCompaniesUser(CompaniesUser compUser) {

		AsyncTask<Map, Void, Void> async = new AsyncTask<Map, Void, Void>() {

			@Override
			protected Void doInBackground(Map... params) {

				CompaniesUserDAO dao = new CompaniesUserDAO();
				dao.save(params[0]);

				return null;
			}
		};

		try {

			Map<String, Map<String, String>> params = new HashMap<String, Map<String, String>>();
			Map<String, String> datas = new HashMap<String, String>();

			Calendar dateNow = Calendar.getInstance();
			SimpleDateFormat f3 = new SimpleDateFormat("yyyy-MM-dd");
			String dateRegister = f3.format(compUser.getDateRegister()
					.getTime());
			Log.i("FORMATO DA DATA", dateRegister);

			datas.put("company_id",
					String.valueOf(compUser.getCompany().getId()));
			datas.put("user_id", String.valueOf(compUser.getUser().getId()));
			datas.put("status", "ACTIVE");
			datas.put("last_status", "ACTIVE");
			datas.put("date_register", dateRegister);

			params.put("CompaniesUser", datas);
			async.execute(params).get();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Desvincula user de company
	 */
	public void inactiveCompsUser(int id) {

		AsyncTask<Map, Void, Void> async = new AsyncTask<Map, Void, Void>() {
			@Override
			protected Void doInBackground(Map... params) {
				CompaniesUserDAO dao = new CompaniesUserDAO();
				dao.save(params[0]);
				return null;
			}
		};
		try {

			Map<String, Map<String, String>> params = new HashMap<String, Map<String, String>>();
			Map<String, String> datas = new HashMap<String, String>();
			datas.put("id", String.valueOf(id));
			datas.put("status", "INACTIVE");
			params.put("CompaniesUser", datas);

			async.execute(params).get();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 */
	public void activateCompsUser(int id) {

		AsyncTask<Map, Void, Void> async = new AsyncTask<Map, Void, Void>() {
			@Override
			protected Void doInBackground(Map... params) {
				CompaniesUserDAO dao = new CompaniesUserDAO();
				dao.save(params[0]);
				return null;
			}
		};
		try {

			Map<String, Map<String, String>> params = new HashMap<String, Map<String, String>>();
			Map<String, String> datas = new HashMap<String, String>();
			datas.put("id", String.valueOf(id));
			datas.put("status", "ACTIVE");
			params.put("CompaniesUser", datas);

			async.execute(params).get();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<CompaniesUser> getInactivesSignatures(int userId) {

		AsyncTask<Map, Void, List<CompaniesUser>> async = new AsyncTask<Map, Void, List<CompaniesUser>>() {

			@Override
			protected List<CompaniesUser> doInBackground(Map... params) {

				return CompaniesUserBO
						.listAllCompaniesUsersNoFilters(params[0]);
			}

		};

		try {

			Map<String, Map<String, Map<String, String>>> key = new HashMap<String, Map<String, Map<String, String>>>();
			Map<String, Map<String, String>> params = new HashMap<String, Map<String, String>>();
			Map<String, String> conditions = new HashMap<String, String>();

			conditions.put("CompaniesUser.status", "INACTIVE");
			conditions.put("CompaniesUser.user_id", String.valueOf(userId));
			params.put("conditions", conditions);
			key.put("CompaniesUser", params);

			return async.execute(key).get();

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	public void checkSignature(int userId, int compId) {

		// verifica se ja existe a assinatura
		AsyncTask<Map, Void, List<CompaniesUser>> async = new AsyncTask<Map, Void, List<CompaniesUser>>() {
			@Override
			protected List<CompaniesUser> doInBackground(Map... params) {
				CompaniesUserDAO dao = new CompaniesUserDAO();

				return dao.listFirstCompaniesUsers(params[0]);
			}
		};

		// salva
		AsyncTask<Map, Void, Void> asyncSave = new AsyncTask<Map, Void, Void>() {

			@Override
			protected Void doInBackground(Map... params) {

				CompaniesUserDAO dao = new CompaniesUserDAO();
				dao.save(params[0]);
				return null;
			}
		};

		try {
			Map<String, Map<String, Map<String, String>>> key = new HashMap<String, Map<String, Map<String, String>>>();
			Map<String, Map<String, String>> params = new HashMap<String, Map<String, String>>();
			Map<String, String> conditions = new HashMap<String, String>();

			conditions.put("CompaniesUser.company_id", String.valueOf(compId));
			conditions.put("CompaniesUser.user_id", String.valueOf(userId));
			params.put("conditions", conditions);
			key.put("CompaniesUser", params);

			List<CompaniesUser> comps = async.execute(key).get();
			Log.e("", "*******************************************************");
			Log.e("DAO", "retorno: " + String.valueOf(comps.size()));
			Log.e("", "*******************************************************");
			// caso o retorno seja vazio, nós criamos um novo registro de
			// assinatura
			if (comps.isEmpty()) {

				Map<String, Map<String, String>> keyInsert = new HashMap<String, Map<String, String>>();
				Map<String, String> paramsInsert = new HashMap<String, String>();

				// data
				Date data = new Date();
				SimpleDateFormat f3 = new SimpleDateFormat("yyyy-MM-dd");
				String dateRegister = f3.format(data);

				paramsInsert.put("company_id", String.valueOf(compId));
				paramsInsert.put("user_id", String.valueOf(userId));
				paramsInsert.put("status", "ACTIVE");
				paramsInsert.put("last_status", "ACTIVE");
				paramsInsert.put("date_register", dateRegister);
				keyInsert.put("CompaniesUser", paramsInsert);

				asyncSave.execute(keyInsert);
				// caso ja exista um registro, simplesmente mudamos o status
				// para ACTIVE
			} else {
				Map<String, Map<String, String>> keyUpdate = new HashMap<String, Map<String, String>>();
				Map<String, String> paramsUpdate = new HashMap<String, String>();

				paramsUpdate.put("id", String.valueOf(comps.get(0).getId()));
				paramsUpdate.put("status", "ACTIVE");
				keyUpdate.put("CompaniesUser", paramsUpdate);

				asyncSave.execute(keyUpdate);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public List<CompaniesUser> listByUser(int userId) {
		AsyncTask<Map, Void, List<CompaniesUser>> async = new AsyncTask<Map, Void, List<CompaniesUser>>() {
			@Override
			protected List<CompaniesUser> doInBackground(Map... params) {
				CompaniesUserDAO dao = new CompaniesUserDAO();
				return dao.listCompaniesUsersSQL(params[0]);
			}
		};

		try {

			Map param = new HashMap();
			Map query = new HashMap();

			query.put(
					"query",
					"select * from companies_users INNER JOIN companies ON companies_users.company_id = companies.id where companies_users.user_id ="
							+ String.valueOf(userId)
							+ " and companies_users.status = 'ACTIVE';");
			param.put("User", query);

			return async.execute(param).get();
		} catch (Exception e) {
			Log.e("ERROR CompaniesUserDAO",
					"Erro CompaniesUserDAO - listByUser");
			e.printStackTrace();
			return null;
		}

	}

}
