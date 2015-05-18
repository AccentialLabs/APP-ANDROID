package com.accential.trueone.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.util.Log;

import com.accential.trueone.bean.User;
import com.accential.trueone.bean.UsersWishlistCompany;
import com.accential.trueone.bo.CompaniesUserBO;
import com.accential.trueone.bo.UserBO;
import com.accential.trueone.bo.UtilityComponentBO;
import com.accential.trueone.interfaces.IOUser;
import com.accential.trueone.utils.JSONUtils;

@SuppressWarnings("all")
public class UserDAO implements IOUser {

	public void requestByQuery(Map params) {
		UtilityComponentBO bo = null;
		try {
			bo = new UtilityComponentBO();
			bo.urlRequestToGetData("users", "query", params);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @author Wilson Junior
	 * @param Map
	 * @return List<Offer>
	 */
	@SuppressLint("SimpleDateFormat")
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<User> listUsers(Map params) {

		List<User> users = null;
		UtilityComponentBO bo = null;

		try {
			bo = new UtilityComponentBO();
			users = new ArrayList<User>();

			JSONArray array = bo.urlRequestToGetData("users", "all", params);

			if (array != null) {
				List<HashMap> objs = JSONUtils.toList(array);

				User user = null;
				Map values = null;

				// SimpleDateFormat sdf = new
				// SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

				if (!objs.isEmpty()) {
					for (HashMap obj : objs) {
						values = JSONUtils.toMap(new JSONObject((Map) obj
								.get("User")));
						user = new User();
						user.setId(Integer.parseInt((String) values.get("id")));
						user.setName(String.valueOf(values.get("name")));
						user.setEmail(String.valueOf(values.get("email")));
						user.setGender(String.valueOf(values.get("gender")));
						user.setPassword(String.valueOf(values.get("password")));
						user.setAddress(String.valueOf(values.get("address")));
						user.setCity(String.valueOf(values.get("city")));
						user.setZip_code(String.valueOf(values.get("zip_code")));
						user.setDistrict(String.valueOf(values.get("district")));
						user.setNumber(String.valueOf(values.get("number")));
						user.setComplement(String.valueOf(values
								.get("complement")));
						user.setPhoto(String.valueOf(values.get("photo")));
						user.setStatus(String.valueOf(values.get("status")));
						user.setState(String.valueOf(values.get("state")));

						// birthday
						String data = String.valueOf(values.get("birthday"));
						SimpleDateFormat sdf = new SimpleDateFormat(
								"yyyy-MM-dd");
						Calendar cal = Calendar.getInstance();
						cal.setTime(sdf.parse(data));
						user.setBirthday(cal);
						//
						/*
						 * //calendario Calendar birthday =
						 * Calendar.getInstance(); birthday.setTime((Date)
						 * sdf.parse
						 * (String.valueOf(values.get("birthday")).replace("-",
						 * "/"))); user.setBirthday(birthday);
						 * 
						 * Calendar date_register = Calendar.getInstance();
						 * date_register.setTime((Date)
						 * sdf.parse(String.valueOf(
						 * values.get("date_register")).replace("-", "/")));
						 * user.setDate_register(birthday);
						 */
						users.add(user);
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return users;
	}

	/**
	 * Lista o primeira usuario retornado pela pesquisa
	 * 
	 * @param params
	 * @return
	 */
	public List<User> listFirstUsers(Map params) {

		List<User> users = null;
		UtilityComponentBO bo = null;

		try {
			bo = new UtilityComponentBO();
			users = new ArrayList<User>();

			JSONArray array = bo.urlRequestToGetData("users", "all", params);

			if (array != null) {
				List<HashMap> objs = JSONUtils.toList(array);

				User user = null;
				Map values = null;

				// SimpleDateFormat sdf = new
				// SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

				if (!objs.isEmpty()) {
					for (HashMap obj : objs) {
						values = JSONUtils.toMap(new JSONObject((Map) obj
								.get("User")));
						user = new User();
						user.setId(Integer.parseInt((String) values.get("id")));
						user.setName(String.valueOf(values.get("name")));
						user.setEmail(String.valueOf(values.get("email")));
						user.setGender(String.valueOf(values.get("gender")));
						user.setPassword(String.valueOf(values.get("password")));
						user.setAddress(String.valueOf(values.get("address")));
						user.setCity(String.valueOf(values.get("city")));
						user.setZip_code(String.valueOf(values.get("zip_code")));
						user.setDistrict(String.valueOf(values.get("district")));
						user.setNumber(String.valueOf(values.get("number")));
						user.setComplement(String.valueOf(values
								.get("complement")));
						user.setPhoto(String.valueOf(values.get("photo")));
						user.setStatus(String.valueOf(values.get("status")));
						user.setState(String.valueOf(values.get("state")));

						// birthday
						String data = String.valueOf(values.get("birthday"));
						SimpleDateFormat sdf = new SimpleDateFormat(
								"yyyy-MM-dd");
						Calendar cal = Calendar.getInstance();
						cal.setTime(sdf.parse(data));
						user.setBirthday(cal);
						//

						/*
						 * //calendario Calendar birthday =
						 * Calendar.getInstance(); birthday.setTime((Date)
						 * sdf.parse
						 * (String.valueOf(values.get("birthday")).replace("-",
						 * "/"))); user.setBirthday(birthday);
						 * 
						 * Calendar date_register = Calendar.getInstance();
						 * date_register.setTime((Date)
						 * sdf.parse(String.valueOf(
						 * values.get("date_register")).replace("-", "/")));
						 * user.setDate_register(birthday);
						 */
						users.add(user);
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return users;
	}

	@SuppressLint("SimpleDateFormat")
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public int countUser(Map params) {
		int contador_users = 0;
		UtilityComponentBO bo = null;

		try {
			bo = new UtilityComponentBO();
			JSONArray array = bo.urlRequestToGetData("users", "all", params);

			if (array != null) {

				contador_users = array.length();
				array = null;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return contador_users;
	}

	public List<User> listAllUsers(Map params) {

		AsyncTask<Map, Void, List<User>> async = new AsyncTask<Map, Void, List<User>>() {

			@Override
			protected List<User> doInBackground(Map... params) {
				List<User> users = UserBO.listUsers(params[0]);

				int contador = UserBO.countUser(params[0]);
				Log.i("QUANTIDADE DE REGISTROS - USERS",
						Integer.toString(contador));

				return users;
			}

		};
		try {
			return async.execute(params).get();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public void saveNewUser(Map params) {

		UtilityComponentBO bo = null;

		try {
			bo = new UtilityComponentBO();

			JSONArray array = bo.urlRequestWishlist("users", "first", params);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void realSave(Map params) {

		AsyncTask<Map, Void, Void> async = new AsyncTask<Map, Void, Void>() {

			@Override
			protected Void doInBackground(Map... params) {
				Log.i("METODO SAVE", "EXECUTANDO METODO");
				UserDAO dao = new UserDAO();
				dao.saveNewUser(params[0]);
				return null;
			}

		};
	}

	public User searchById(int id) {

		User user = new User();

		Map<String, Map<String, Map<String, String>>> key2 = new HashMap<String, Map<String, Map<String, String>>>();
		Map<String, Map<String, String>> params2 = new HashMap<String, Map<String, String>>();
		Map<String, String> conditions2 = new HashMap<String, String>();

		params2.put("conditions", conditions2);
		key2.put("User", params2);
		List<User> users = UserBO.listAllUsers(key2);

		for (User user2 : users) {
			if (user2.getId() == id) {
				user.setAddress(user2.getAddress());
				user.setBirthday(user2.getBirthday());
				user.setCity(user2.getCity());
				user.setComplement(user2.getComplement());
				user.setDistrict(user2.getDistrict());
				user.setEmail(user2.getEmail());
				user.setGender(user2.getGender());
				user.setName(user2.getName());
				user.setId(user2.getId());
				user.setNumber(user2.getNumber());
				user.setPassword(user2.getPassword());
				user.setPhoto(user2.getPhoto());
				user.setState(user2.getState());
				Log.i("TESTE USERDAO - SEARCH BY ID - STATE", user.getState());
				user.setStatus(user2.getStatus());
				user.setZip_code(user2.getZip_code());
			}
		}
		return user;
	}

	public User searchByEmail(String email) {

		User user = new User();

		Map<String, Map<String, Map<String, String>>> key2 = new HashMap<String, Map<String, Map<String, String>>>();
		Map<String, Map<String, String>> params2 = new HashMap<String, Map<String, String>>();
		Map<String, String> conditions2 = new HashMap<String, String>();

		conditions2.put("User.email", email);
		params2.put("conditions", conditions2);
		key2.put("User", params2);
		List<User> users = UserBO.listAllUsers(key2);
		try {
			user.setAddress(users.get(0).getAddress());
			user.setBirthday(users.get(0).getBirthday());
			user.setCity(users.get(0).getCity());
			user.setComplement(users.get(0).getComplement());
			user.setDistrict(users.get(0).getDistrict());
			user.setEmail(users.get(0).getEmail());
			user.setGender(users.get(0).getGender());
			user.setName(users.get(0).getName());
			user.setId(users.get(0).getId());
			user.setNumber(users.get(0).getNumber());
			user.setPassword(users.get(0).getPassword());
			user.setPhoto(users.get(0).getPhoto());
			user.setState(users.get(0).getState());
			Log.i("TESTE USERDAO - SEARCH BY ID - STATE", user.getState());
			user.setStatus(users.get(0).getStatus());
			user.setZip_code(users.get(0).getZip_code());
			return user;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	// BUSCA USER
	public User searchUserByCheckoutId(int id) {

		Map<String, Map<String, Map<String, String>>> key = new HashMap<String, Map<String, Map<String, String>>>();
		Map<String, Map<String, String>> params = new HashMap<String, Map<String, String>>();
		Map<String, String> conditions = new HashMap<String, String>();

		conditions.put("Checkout.id", String.valueOf(id));
		params.put("conditions", conditions);
		key.put("Checkout", params);

		AsyncTask<Map, Void, Integer> async = new AsyncTask<Map, Void, Integer>() {

			@Override
			protected Integer doInBackground(Map... params) {
				CheckoutDAO dao = new CheckoutDAO();
				int i = dao.returnsObejectId(params[0], "user_id");
				return i;
			}
		};
		int y = 0;
		try {
			y = async.execute(key).get();
			return UserBO.searchById(y);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public User searchUserByCompaniesUserId(int id) {

		Map<String, Map<String, Map<String, String>>> key2 = new HashMap<String, Map<String, Map<String, String>>>();
		Map<String, Map<String, String>> params2 = new HashMap<String, Map<String, String>>();
		Map<String, String> conditions2 = new HashMap<String, String>();

		conditions2.put("CompaniesUser.id", String.valueOf(id));
		params2.put("conditions", conditions2);
		key2.put("CompaniesUser", params2);

		AsyncTask<Map, Void, Integer> async = new AsyncTask<Map, Void, Integer>() {
			@Override
			protected Integer doInBackground(Map... params) {
				int i = CompaniesUserBO.returnAtributeId(params[0], "user_id");
				return i;
			}
		};
		try {
			int y = async.execute(key2).get();
			return UserBO.searchById(y);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<User> saveUser(Map params) {

		UtilityComponentBO bo = null;
		List<User> users = null;

		try {

			bo = new UtilityComponentBO();
			users = new ArrayList<User>();

			JSONArray array = bo.urlToCreateUser("users", "first", params);

			if (array != null) {
				List<HashMap> objs = JSONUtils.toList(array);

				User user = null;
				Map values = null;

				if (!objs.isEmpty()) {
					for (HashMap obj : objs) {
						values = JSONUtils.toMap(new JSONObject((Map) obj
								.get("User")));
						user = new User();

						user.setName(String.valueOf(values.get("name")));
						users.add(user);
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return users;
	}

	public List<User> saveAndUploadUser(Map params) {

		UtilityComponentBO bo = null;
		List<User> users = null;

		try {

			bo = new UtilityComponentBO();
			users = new ArrayList<User>();

			JSONArray array = bo.urlRequestToSaveData("users", "all", params);

			if (array != null) {
				List<HashMap> objs = JSONUtils.toList(array);

				User user = null;
				Map values = null;

				if (!objs.isEmpty()) {
					for (HashMap obj : objs) {
						values = JSONUtils.toMap(new JSONObject((Map) obj
								.get("User")));
						user = new User();

						user.setName(String.valueOf(values.get("name")));
						users.add(user);
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return users;
	}

	public List<User> createUser(User user) {

		AsyncTask<Map, Void, List<User>> async = new AsyncTask<Map, Void, List<User>>() {

			@Override
			protected List<User> doInBackground(Map... params) {
				UserDAO dao = new UserDAO();
				List<User> users = dao.saveUser(params[0]);
				return users;
			}
		};
		try {

			Map<String, Map<String, String>> params = new HashMap<String, Map<String, String>>();
			Map<String, String> datas = new HashMap<String, String>();

			datas.put("name", user.getName());
			datas.put("email", user.getEmail());
			datas.put("password", user.getPassword());
			params.put("User", datas);

			return async.execute(params).get();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	public void uploadUser(User user) {

		AsyncTask<Map, Void, List<User>> async = new AsyncTask<Map, Void, List<User>>() {

			@Override
			protected List<User> doInBackground(Map... params) {
				UserDAO dao = new UserDAO();
				List<User> users = dao.saveAndUploadUser(params[0]);
				return users;
			}

		};

		try {

			Map<String, Map<String, String>> params = new HashMap<String, Map<String, String>>();
			Map<String, String> datas = new HashMap<String, String>();

			datas.put("id", String.valueOf(user.getId()));
			datas.put("name", user.getName());
			datas.put("email", user.getEmail());
			datas.put("address", user.getAddress());
			datas.put("number", user.getNumber());
			datas.put("district", user.getDistrict());
			datas.put("complement", user.getComplement());
			datas.put("state", user.getState());
			datas.put("city", user.getCity());
			datas.put("zip_code", user.getZip_code());
			datas.put("gender", user.getGender());
			params.put("User", datas);

			async.execute(params).get();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// /////////////////////////////////////
	public List<User> recoverySenha(Map params) {

		UtilityComponentBO bo = null;
		List<User> users = null;

		try {

			bo = new UtilityComponentBO();
			users = new ArrayList<User>();

			JSONArray array = bo.requestPasswordRecovery("users", "first",
					params);

			if (array != null) {
				List<HashMap> objs = JSONUtils.toList(array);

				User user = null;
				Map values = null;

				if (!objs.isEmpty()) {
					for (HashMap obj : objs) {
						values = JSONUtils.toMap(new JSONObject((Map) obj
								.get("User")));
						user = new User();

						user.setName(String.valueOf(values.get("name")));
						users.add(user);
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return users;
	}

	/**
	 * Metodo usado para recuperar senha de usuario Implementado na tela de
	 * Login
	 * 
	 * @author Matheus Odilon - accentialbrasil
	 * @param String
	 *            email
	 * @return Offer
	 */
	public void recovery(String email) {
		AsyncTask<Map, Void, List<User>> async = new AsyncTask<Map, Void, List<User>>() {

			@Override
			protected List<User> doInBackground(Map... params) {
				UserDAO dao = new UserDAO();
				List<User> users = dao.recoverySenha(params[0]);
				return users;
			}
		};
		try {

			/*
			 * arrayParams = { params = { fields; conditions; } }
			 */
			Map<String, Map<String, Map<String, String>>> arrayParams = new HashMap<String, Map<String, Map<String, String>>>();
			Map<String, Map<String, String>> params = new HashMap<String, Map<String, String>>();
			Map<String, String> conditions = new HashMap<String, String>();
			Map<String, String> fields = new HashMap<String, String>();

			fields.put("User.id", "");
			fields.put("User.name", "");
			fields.put("User.email", "");

			conditions.put("User.email", email);

			params.put("fields", fields);
			params.put("conditions", conditions);

			arrayParams.put("User", params);
			Log.i("Estrutura do MAP", String.valueOf(arrayParams));

			List<User> user = async.execute(arrayParams).get();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Verifica existencia do usuario por email enviado
	 * 
	 * @author Matheus Odilon - accentialbrasil
	 * @param email
	 * @return boolean
	 */
	public boolean verifyUser(String email) {

		Map<String, Map<String, Map<String, String>>> key = new HashMap<String, Map<String, Map<String, String>>>();
		Map<String, Map<String, String>> params = new HashMap<String, Map<String, String>>();
		Map<String, String> conditions = new HashMap<String, String>();

		conditions.put("User.email", email);
		params.put("conditions", conditions);
		key.put("User", params);

		UserBO bo = new UserBO();
		List<User> users = bo.listAllUsers(key);

		if (users.size() != 0) {
			return true;
		} else {
			return false;
		}

	}

	public List<User> userLogin(User user) {

		AsyncTask<Map, Void, List<User>> async = new AsyncTask<Map, Void, List<User>>() {

			@Override
			protected List<User> doInBackground(Map... params) {
				List<User> users = UserBO.listFirstUsers(params[0]);

				return users;
			}

		};
		try {

			// maps com os dados necessarios para acesso a API
			Map<String, Map<String, Map<String, String>>> key = new HashMap<String, Map<String, Map<String, String>>>();
			Map<String, Map<String, String>> params = new HashMap<String, Map<String, String>>();
			Map<String, String> conditions = new HashMap<String, String>();

			conditions.put("User.email", user.getEmail());
			conditions.put("User.password", user.getPassword());
			params.put("conditions", conditions);
			key.put("User", params);

			return async.execute(key).get();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * salva dados do usuario
	 * 
	 * @param params
	 */
	public void saveData(Map params) {
		UtilityComponentBO bo = null;
		List<User> users = null;
		Log.e("Executando...", "Executando ... saveData");
		try {

			bo = new UtilityComponentBO();
			users = new ArrayList<User>();

			JSONArray array = bo.urlRequestToSaveData("users", "all", params);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void saveMainData(User user) {

		Log.e("Executando...", "Executando ... SaveMainData");
		AsyncTask<Map, Void, Void> async = new AsyncTask<Map, Void, Void>() {
			@Override
			protected Void doInBackground(Map... params) {
				UserBO.saveData(params[0]);
				return null;
			}
		};

		try {

			int mes = user.getBirthday().get(Calendar.MONTH);

			String birth = String
					.valueOf(user.getBirthday().get(Calendar.YEAR))
					+ "-"
					+ String.valueOf(mes + 1)
					+ "-"
					+ String.valueOf(user.getBirthday().get(
							Calendar.DAY_OF_MONTH));

			Map key = new HashMap();
			Map params = new HashMap();

			key.put("id", String.valueOf(user.getId()));
			key.put("name", user.getName());
			key.put("email", user.getEmail());
			key.put("gender", user.getGender());
			key.put("birthday", birth);
			params.put("User", key);

			async.execute(params);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void saveAddressData(User user) {

		AsyncTask<Map, Void, Void> async = new AsyncTask<Map, Void, Void>() {
			@Override
			protected Void doInBackground(Map... params) {
				UserBO.saveData(params[0]);
				return null;
			}
		};
		try {

			Map key = new HashMap();
			Map params = new HashMap();

			key.put("id", String.valueOf(user.getId()));
			key.put("address", user.getAddress());
			key.put("city", user.getCity());
			key.put("complement", user.getComplement());
			key.put("district", user.getDistrict());
			key.put("number", user.getNumber());
			key.put("state", user.getState());
			key.put("zip_code", user.getZip_code());
			params.put("User", key);

			async.execute(params);

		} catch (Exception e) {
			Log.e("ERROR UserDAO",
					"Erro ao salvar dados do endereco do usuario.");
			e.printStackTrace();
		}
	}

	public void sendEmailNewUser(String name, String email, String pass) {

		AsyncTask<Map, Void, Void> async = new AsyncTask<Map, Void, Void>() {
			@Override
			protected Void doInBackground(Map... params) {
				UtilityComponentBO bo = new UtilityComponentBO();
				bo.sendEmailNewUser(params[0]);
				return null;
			}
		};

		try {
			Map params = new HashMap();
			params.put("userName", name);
			params.put("userEmail", email);
			params.put("pwd", pass);

			async.execute(params);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void userUsing(int userId) {

		AsyncTask<Map, Void, Void> async = new AsyncTask<Map, Void, Void>() {

			@Override
			protected Void doInBackground(Map... params) {
				UserDAO dao = new UserDAO();
				dao.requestByQuery(params[0]);
				return null;
			}
		};

		try {
			Map param = new HashMap();
			Map query = new HashMap();

			query.put("query",
					"UPDATE users_using set mobile='ACTIVE', android='ACTIVE' where user_id = "
							+ String.valueOf(userId));
			param.put("User", query);

			async.execute(param).get();

		} catch (Exception e) {
			e.printStackTrace();
			Log.e("ERROR UserDAO", "ERROR UserDAO - Metodo: userUsing");
		}

	}

	public void createUserUsing(int userId, String deviceToken) {

		AsyncTask<Map, Void, Void> async = new AsyncTask<Map, Void, Void>() {

			@Override
			protected Void doInBackground(Map... params) {
				UserDAO dao = new UserDAO();
				dao.requestByQuery(params[0]);
				return null;
			}
		};

		try {
			Map param = new HashMap();
			Map query = new HashMap();

			query.put("query",
					"insert into users_using(user_id, mobile, android, ios, unique_id) values("
							+ String.valueOf(userId)
							+ ", 'ACTIVE','ACTIVE','INACTIVE', '" + deviceToken
							+ "');");
			param.put("User", query);

			async.execute(param).get();

		} catch (Exception e) {
			e.printStackTrace();
			Log.e("ERROR UserDAO", "ERROR UserDAO - Metodo: createUserUsing");
		}

	}
}
