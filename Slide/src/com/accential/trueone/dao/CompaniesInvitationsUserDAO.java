package com.accential.trueone.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

import com.accential.trueone.bean.CompaniesInvitationsUser;
import com.accential.trueone.bean.Company;
import com.accential.trueone.bo.CompaniesInvitationsUserBO;
import com.accential.trueone.bo.CompaniesUserBO;
import com.accential.trueone.bo.CompanyBO;
import com.accential.trueone.bo.UtilityComponentBO;
import com.accential.trueone.interfaces.ICompaniesInvitationsUser;
import com.accential.trueone.utils.JSONUtils;

/**
 * 
 * @author Matheus Odilon - accentialbrasil
 * 
 */
@SuppressWarnings("all")
public class CompaniesInvitationsUserDAO implements ICompaniesInvitationsUser {

	/**
	 * ATEN����O: Executar sempre em uma Thread separada
	 * 
	 * @author Matheus Odilon - accentialbrasil
	 * @param Map
	 * @return List<CompaniesInvitationsUser>
	 */
	@Override
	public List<CompaniesInvitationsUser> listCompaniesInvitationsUser(
			Map params) {
		List<CompaniesInvitationsUser> invitations = null;
		UtilityComponentBO bo = null;

		try {

			bo = new UtilityComponentBO();
			invitations = new ArrayList<CompaniesInvitationsUser>();

			JSONArray array = bo
					.urlRequestToGetData("companies", "all", params);

			if (array != null) {
				List<HashMap> objs = JSONUtils.toList(array);
				CompaniesInvitationsUser invite = null;
				Company company = null;
				Map values = null;

				Log.e("", "OBJS: " + objs.toString());

				if (!objs.isEmpty()) {
					for (HashMap obj : objs) {
						values = JSONUtils.toMap(new JSONObject((Map) obj
								.get("CompaniesInvitationsUser")));
						invite = new CompaniesInvitationsUser();

						invite.setId(Integer.parseInt((String) values.get("id")));
						invite.setPreview(String.valueOf(values.get("preview")));
						invite.setStatus(String.valueOf(values.get("status")));

						Calendar dateRegister = Calendar.getInstance();
						String data = String.valueOf(
								values.get("date_register")).replace("-", "/");
						if (!data.equals("0000/00/00")) {
							Date date = new Date(data);
							dateRegister.setTime(date);

							invite.setDateRegister(dateRegister);
						}

						company = new Company();
						company.setId(Integer.parseInt(String.valueOf(values
								.get("company_id"))));

						invite.setCompany(company);

						invitations.add(invite);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return invitations;
	}

	/**
	 * Retorna lista de CompaniesInvitationsUser segundo parametros enviados
	 * 
	 * @author Matheus Odilon -accentialbrasil
	 * @param Map
	 * @return List<CompaniesInvitationsUser>
	 */
	@Override
	public List<CompaniesInvitationsUser> listAllCompaniesInvitationsUser(
			Map params) {

		AsyncTask<Map, Void, List<CompaniesInvitationsUser>> async = new AsyncTask<Map, Void, List<CompaniesInvitationsUser>>() {
			@Override
			protected List<CompaniesInvitationsUser> doInBackground(
					Map... params) {
				List<CompaniesInvitationsUser> invitations = CompaniesInvitationsUserBO
						.listCompaniesInvitationsUser(params[0]);

				return invitations;
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
	 * Retorna lista de CompaniesInvitationsUser por id de Usuario
	 * 
	 * @author Matheus Odilon - accentialbrasil
	 * @param Map
	 * @return List<CompaniesInvitationsUser>
	 */
	@Override
	public List<CompaniesInvitationsUser> searchByUserId(int userId) {

		Map<String, Map<String, Map<String, String>>> key = new HashMap<String, Map<String, Map<String, String>>>();
		Map<String, Map<String, String>> params = new HashMap<String, Map<String, String>>();
		Map<String, String> conditions = new HashMap<String, String>();

		conditions.put("CompaniesInvitationsUser.user_id",
				String.valueOf(userId));
		params.put("conditions", conditions);
		key.put("CompaniesInvitationsUser", params);

		List<CompaniesInvitationsUser> invitations = CompaniesInvitationsUserBO
				.listAllCompaniesInvitationsUser(key);

		return invitations;
	}

	public int countInvitationsWaiting(int userId) {

		AsyncTask<Map, Void, List<CompaniesInvitationsUser>> async = new AsyncTask<Map, Void, List<CompaniesInvitationsUser>>() {
			@Override
			protected List<CompaniesInvitationsUser> doInBackground(
					Map... params) {

				return CompaniesInvitationsUserBO
						.listCompaniesInvitationsUser(params[0]);
			}
		};

		try {

			Map key = new HashMap();
			Map<String, Map<String, String>> params = new HashMap<String, Map<String, String>>();
			Map<String, String> conditions = new HashMap<String, String>();

			conditions.put("CompaniesInvitationsUser.user_id",
					String.valueOf(userId));
			conditions.put("CompaniesInvitationsUser.status", "WAIT");
			params.put("conditions", conditions);
			key.put("CompaniesInvitationsUser", params);

			List<CompaniesInvitationsUser> invits = async.execute(key).get();

			int total = invits.size();
			return total;
		} catch (Exception e) {
			Log.e("ERRO CompaniesInvitationsUserDAO",
					"Erro na listagem dos convites. CompaniesInvitationsUserDAO - line number: countInvitationsWaiting()");
			e.printStackTrace();
			return 0;
		}
	}

	public List<CompaniesInvitationsUser> searchInvitationsWaiting(int userId) {

		AsyncTask<Map, Void, List<CompaniesInvitationsUser>> async = new AsyncTask<Map, Void, List<CompaniesInvitationsUser>>() {
			@Override
			protected List<CompaniesInvitationsUser> doInBackground(
					Map... params) {

				return CompaniesInvitationsUserBO
						.listCompaniesInvitationsUser(params[0]);
			}
		};

		try {

			Map key = new HashMap();
			Map<String, Map<String, String>> params = new HashMap<String, Map<String, String>>();
			Map<String, String> conditions = new HashMap<String, String>();

			conditions.put("CompaniesInvitationsUser.user_id",
					String.valueOf(userId));
			conditions.put("CompaniesInvitationsUser.status", "WAIT");
			params.put("conditions", conditions);
			key.put("CompaniesInvitationsUser", params);

			List<CompaniesInvitationsUser> invits = async.execute(key).get();
			List<CompaniesInvitationsUser> myInvits = new ArrayList<CompaniesInvitationsUser>();
			for (CompaniesInvitationsUser invite : invits) {
				Company comp = CompanyBO
						.searchById(invite.getCompany().getId());
				invite.setCompany(comp);
				myInvits.add(invite);
			}
			return myInvits;

		} catch (Exception e) {
			Log.e("ERRO CompaniesInvitationsUserDAO",
					"Erro na listagem dos convites. CompaniesInvitationsUserDAO - searchInvitationsWaiting()");
			return null;
		}

	}

	public int returnElementId(Map params, String parameter) {
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
								.get("CompaniesInvitationsUser")));
						id = Integer.parseInt((String) values.get(parameter));
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return id;
	}

	public Company returnCompanyByInvite(int inviteId) {

		Map<String, Map<String, Map<String, String>>> key = new HashMap<String, Map<String, Map<String, String>>>();
		Map<String, Map<String, String>> params = new HashMap<String, Map<String, String>>();
		Map<String, String> conditions = new HashMap<String, String>();

		conditions.put("CompaniesInvitationsUser.id", String.valueOf(inviteId));
		params.put("conditions", conditions);
		key.put("CompaniesInvitationsUser", params);

		AsyncTask<Map, Void, Integer> async = new AsyncTask<Map, Void, Integer>() {
			@Override
			protected Integer doInBackground(Map... params) {
				CompaniesInvitationsUserDAO dao = new CompaniesInvitationsUserDAO();
				int i = dao.returnElementId(params[0], "company_id");
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

	public List<CompaniesInvitationsUser> returnObjsCompInviteUser(int userId) {

		Map<String, Map<String, Map<String, String>>> key = new HashMap<String, Map<String, Map<String, String>>>();
		Map<String, Map<String, String>> params = new HashMap<String, Map<String, String>>();
		Map<String, String> conditions = new HashMap<String, String>();

		conditions.put("CompaniesInvitationsUser.user_id",
				String.valueOf(userId));
		params.put("conditions", conditions);
		key.put("CompaniesInvitationsUser", params);

		List<CompaniesInvitationsUser> comps = new ArrayList<CompaniesInvitationsUser>();
		List<CompaniesInvitationsUser> invites = CompaniesInvitationsUserBO
				.listAllCompaniesInvitationsUser(key);

		for (CompaniesInvitationsUser invite : invites) {

			if (invite.getStatus().equals("WAIT")) {
				CompaniesInvitationsUserDAO dao = new CompaniesInvitationsUserDAO();
				Company company = dao.returnCompanyByInvite(invite.getId());

				invite.setCompany(company);

				comps.add(invite);
			}
		}
		return comps;
	}

	/**
	 * SALVA E EDITA
	 * 
	 * @param params
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

	public void activateInvite(int id, CompaniesInvitationsUser invite) {

		AsyncTask<Map, Void, Void> async = new AsyncTask<Map, Void, Void>() {

			@Override
			protected Void doInBackground(Map... params) {
				CompaniesInvitationsUserDAO dao = new CompaniesInvitationsUserDAO();
				dao.save(params[0]);
				return null;
			}
		};

		try {

			Map<String, Map<String, String>> params = new HashMap<String, Map<String, String>>();
			Map<String, String> datas = new HashMap<String, String>();
			datas.put("id", String.valueOf(id));
			datas.put("status", "ACTIVE");
			datas.put("preview", "ACTIVE");
			params.put("CompaniesInvitationsUser", datas);

			async.execute(params).get();

			Log.e("", "aceitou convite de seguir.");
			Log.e("", "Criando ou editando assinatura...");

			CompaniesUserBO.checkSignature(invite.getUser().getId(), invite
					.getCompany().getId());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void inactivateInvite(int id) {
		AsyncTask<Map, Void, Void> async = new AsyncTask<Map, Void, Void>() {

			@Override
			protected Void doInBackground(Map... params) {
				CompaniesInvitationsUserDAO dao = new CompaniesInvitationsUserDAO();
				dao.save(params[0]);
				return null;
			}
		};

		try {

			Map<String, Map<String, String>> params = new HashMap<String, Map<String, String>>();
			Map<String, String> datas = new HashMap<String, String>();
			datas.put("id", String.valueOf(id));
			datas.put("status", "INACTIVE");
			params.put("CompaniesInvitationsUser", datas);

			async.execute(params).get();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
