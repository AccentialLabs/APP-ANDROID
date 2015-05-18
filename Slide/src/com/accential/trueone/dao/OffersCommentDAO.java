package com.accential.trueone.dao;

import java.text.SimpleDateFormat;
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

import com.accential.trueone.bean.OffersComment;
import com.accential.trueone.bean.User;
import com.accential.trueone.bo.OffersCommentBO;
import com.accential.trueone.bo.UtilityComponentBO;
import com.accential.trueone.interfaces.IOffersComment;
import com.accential.trueone.utils.JSONUtils;

/**
 * 
 * @author Matheus Odilon - accentialbrasil
 */
@SuppressWarnings("all")
public class OffersCommentDAO implements IOffersComment {

	/**
	 * Traz os comentarios atraves da requisiçao feita por sql
	 * 
	 * @param params
	 * @return
	 */
	public List<OffersComment> listCommentsSQL(Map params) {
		List<OffersComment> comments = null;
		UtilityComponentBO bo = null;
		try {
			bo = new UtilityComponentBO();
			comments = new ArrayList<OffersComment>();

			JSONArray array = bo.urlRequestToGetData("users", "query", params);

			if (array != null) {
				List<HashMap> objs = JSONUtils.toList(array);
				OffersComment comment = null;
				User user = null;
				Map values = null;
				Map usersValues = null;

				if (!objs.isEmpty()) {
					for (HashMap obj : objs) {
						values = JSONUtils.toMap(new JSONObject((Map) obj
								.get("offers_comments")));
						comment = new OffersComment();

						comment.setId(Integer.parseInt((String) values
								.get("id")));
						comment.setTitle(String.valueOf(values.get("title")));
						comment.setDescricao(String.valueOf(values
								.get("description")));
						comment.setEvaluation(String.valueOf(values
								.get("evaluation")));
						comment.setStatus(String.valueOf(values.get("status")));
						// DATE_REGISTER
						Calendar registerDate = Calendar.getInstance();
						String stringData = String.valueOf(
								values.get("date_register")).replace("-", "/");
						Date register = new Date(stringData);
						registerDate.setTime(register);

						comment.setDateRegister(registerDate);

						// dados do usuario criador do comentario
						usersValues = JSONUtils.toMap(new JSONObject((Map) obj
								.get("users")));
						user = new User();
						user.setId(Integer.parseInt((String) usersValues
								.get("id")));
						user.setName(String.valueOf(usersValues.get("name")));
						user.setEmail(String.valueOf(usersValues.get("email")));
						user.setGender(String.valueOf(usersValues.get("gender")));
						user.setPassword(String.valueOf(usersValues
								.get("password")));
						user.setAddress(String.valueOf(usersValues
								.get("address")));
						user.setCity(String.valueOf(usersValues.get("city")));
						user.setZip_code(String.valueOf(usersValues
								.get("zip_code")));
						user.setDistrict(String.valueOf(usersValues
								.get("district")));
						user.setNumber(String.valueOf(usersValues.get("number")));
						user.setComplement(String.valueOf(usersValues
								.get("complement")));
						user.setPhoto(String.valueOf(usersValues.get("photo")));
						user.setStatus(String.valueOf(usersValues.get("status")));
						user.setState(String.valueOf(usersValues.get("state")));

						// birthday
						String data = String.valueOf(usersValues
								.get("birthday"));
						SimpleDateFormat sdf = new SimpleDateFormat(
								"yyyy-MM-dd");
						Calendar cal = Calendar.getInstance();
						cal.setTime(sdf.parse(data));
						user.setBirthday(cal);

						comment.setUser(user);

						comments.add(comment);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return comments;
	}

	/**
	 * ATEN����O: EXECUTAR ESSE M��TODO SEMPRE EM UMA THREAD SEPARADA
	 * 
	 * @author Matheus Odilon - accentialbrasil
	 * @param Map
	 * @return List<OffersComment>
	 */
	@Override
	public List<OffersComment> listOffersComments(Map params) {
		List<OffersComment> comments = null;
		UtilityComponentBO bo = null;
		Log.e("EXECUTANDO..", "EXECUTANDO O LISTOFFERS - 1");
		try {
			bo = new UtilityComponentBO();
			comments = new ArrayList<OffersComment>();

			JSONArray array = bo.urlRequestToGetData("offers", "all", params);

			Log.e("EXECUTANDO..", "EXECUTANDO O LISTOFFERS - 1");
			Log.e("EXECUTANDO..", "ARRAY" + String.valueOf(array));
			if (array != null) {
				List<HashMap> objs = JSONUtils.toList(array);
				OffersComment comment = null;
				Map values = null;

				if (!objs.isEmpty()) {
					for (HashMap obj : objs) {
						values = JSONUtils.toMap(new JSONObject((Map) obj
								.get("OffersComment")));
						comment = new OffersComment();

						comment.setId(Integer.parseInt((String) values
								.get("id")));
						comment.setTitle(String.valueOf(values.get("title")));
						comment.setDescricao(String.valueOf(values
								.get("description")));
						comment.setEvaluation(String.valueOf(values
								.get("evaluation")));
						comment.setStatus(String.valueOf(values.get("status")));
						// DATE_REGISTER
						Calendar registerDate = Calendar.getInstance();
						String stringData = String.valueOf(
								values.get("date_register")).replace("-", "/");
						Date register = new Date(stringData);
						registerDate.setTime(register);

						comment.setDateRegister(registerDate);

						comments.add(comment);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return comments;
	}

	/**
	 * ATEN����O: EXECUTAR ESSE M��TODO SEMPRE EM UMA THREAD SEPARADA Retorna a
	 * quantidade (em n��meros) total da lista de comentarios de acordo com os
	 * paramentros enviados
	 * 
	 * @param Map
	 * @return int
	 * @author Matheus Odilon - accentialbrasil
	 */
	@Override
	public int countOffersComments(Map params) {
		int cont_comments = 0;
		UtilityComponentBO bo = null;

		try {
			bo = new UtilityComponentBO();
			JSONArray array = bo.urlRequestToGetData("offers", "all", params);

			if (array != null) {
				cont_comments = array.length();
				array = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cont_comments;
	}

	public int amountOffersCommentByOffer(int offerId) {

		Map<String, Map<String, Map<String, String>>> key2 = new HashMap<String, Map<String, Map<String, String>>>();
		Map<String, Map<String, String>> params2 = new HashMap<String, Map<String, String>>();
		Map<String, String> conditions2 = new HashMap<String, String>();

		conditions2.put("OffersComment.offer_id", String.valueOf(offerId));
		params2.put("conditions", conditions2);
		key2.put("OffersComment", params2);

		AsyncTask<Map, Void, Integer> asycn = new AsyncTask<Map, Void, Integer>() {
			@Override
			protected Integer doInBackground(Map... params) {
				int i = OffersCommentBO.countOffersComments(params[0]);
				return i;
			}
		};
		try {
			return asycn.execute(key2).get();
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	/**
	 * ATEN����O: Esse m��todo n��o necessita ser executado em uma thread
	 * separada, pois j�� �� executando de forma ass��ncrona
	 * 
	 * @author Matheus Odilon - accentialbrasil
	 * @param Map
	 * @return List<OffersComment>
	 */
	@Override
	public List<OffersComment> listAllOffersComments(Map params) {

		AsyncTask<Map, Void, List<OffersComment>> async = new AsyncTask<Map, Void, List<OffersComment>>() {
			@Override
			protected List<OffersComment> doInBackground(Map... params) {
				List<OffersComment> comments = OffersCommentBO
						.listOffersComments(params[0]);
				return comments;
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
	public List<OffersComment> searchByOffer(int offerId) {

		Map<String, Map<String, Map<String, String>>> key2 = new HashMap<String, Map<String, Map<String, String>>>();
		Map<String, Map<String, String>> params2 = new HashMap<String, Map<String, String>>();
		Map<String, String> conditions2 = new HashMap<String, String>();

		conditions2.put("OffersComment.offer_id", String.valueOf(offerId));
		params2.put("conditions", conditions2);
		key2.put("OffersComment", params2);

		List<OffersComment> comments = OffersCommentBO
				.listAllOffersComments(key2);

		return comments;
	}

	public void save(Map params) {

		UtilityComponentBO bo = null;

		try {
			bo = new UtilityComponentBO();

			JSONArray array = bo.urlRequestToSaveData("offers", "all", params);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void saveComment(OffersComment comment) {

		AsyncTask<Map, Void, Void> async = new AsyncTask<Map, Void, Void>() {
			@Override
			protected Void doInBackground(Map... params) {
				OffersCommentDAO dao = new OffersCommentDAO();
				dao.save(params[0]);
				return null;
			}
		};

		Map<String, Map<String, String>> key = new HashMap<String, Map<String, String>>();
		Map<String, String> params = new HashMap<String, String>();

		List<OffersComment> comments = OffersCommentBO.listByUserAndOffer(
				comment.getUser().getId(), comment.getOffer().getId());

		if (comments.isEmpty()) {

			Date data = new Date();
			SimpleDateFormat f3 = new SimpleDateFormat("yyyy-MM-dd");
			String dateRegister = f3.format(data);

			params.put("offer_id", String.valueOf(comment.getOffer().getId()));
			params.put("user_id", String.valueOf(comment.getUser().getId()));
			params.put("title", comment.getTitle());
			params.put("description", comment.getDescricao());
			params.put("evaluation", comment.getEvaluation());
			params.put("date_register", dateRegister);
			params.put("status", "ACTIVE");
			key.put("OffersComment", params);

		} else {
			Date data = new Date();
			SimpleDateFormat f3 = new SimpleDateFormat("yyyy-MM-dd");
			String dateRegister = f3.format(data);

			Log.e("",
					"ID DO COMENTARIO: "
							+ String.valueOf(comments.get(0).getId()));
			params.put("id", String.valueOf(comments.get(0).getId()));
			params.put("title", comment.getTitle());
			params.put("description", comment.getDescricao());
			params.put("evaluation", comment.getEvaluation());
			params.put("date_register", dateRegister);
			params.put("status", "ACTIVE");
			key.put("OffersComment", params);
		}

		try {
			async.execute(key);
		} catch (Exception e) {
			e.printStackTrace();
			Log.e("ERROR OffersComments",
					"OCORREU ALGUM ERRO EM OfferCommentDAO,"
							+ " VERIFIQUE OS DADOS ENVIADOS COMO"
							+ " PARAMENTROS E TENTE NOVAMENTE");
		}
	};

	/**
	 * Retorna comentario de determinada oferta, comentario e usuario
	 * responsavel
	 * 
	 * @param offerId
	 * @return List<OffersComment>
	 */
	public List<OffersComment> listByOffer(int offerId) {
		AsyncTask<Map, Void, List<OffersComment>> async = new AsyncTask<Map, Void, List<OffersComment>>() {
			@Override
			protected List<OffersComment> doInBackground(Map... params) {
				OffersCommentDAO dao = new OffersCommentDAO();
				return dao.listCommentsSQL(params[0]);
			}
		};
		try {

			Map param = new HashMap();
			Map query = new HashMap();
			query.put(
					"query",
					"select * from offers_comments INNER JOIN users ON offers_comments.user_id = users.id where offers_comments.offer_id ="
							+ String.valueOf(offerId) + ";");
			param.put("User", query);
			List<OffersComment> comments = async.execute(param).get();
			return comments;
		} catch (Exception e) {
			Log.e("Erro", "ERRO OffersCommentDAO - listByOffer");
			e.printStackTrace();
			return null;
		}
	}

	public List<OffersComment> listByCompany(int compId) {

		AsyncTask<Map, Void, List<OffersComment>> async = new AsyncTask<Map, Void, List<OffersComment>>() {
			@Override
			protected List<OffersComment> doInBackground(Map... params) {
				OffersCommentDAO dao = new OffersCommentDAO();
				return dao.listCommentsSQL(params[0]);
			}
		};

		try {
			Map param = new HashMap();
			Map query = new HashMap();
			query.put(
					"query",
					"select * from offers_comments INNER JOIN offers ON offers.id = offers_comments.offer_id INNER JOIN users ON offers_comments.user_id = users.id where offers.company_id = "
							+ String.valueOf(compId) + ";");
			param.put("User", query);
			List<OffersComment> comments = async.execute(param).get();
			return comments;

		} catch (Exception e) {
			Log.e("Error",
					"Erro em Lista comentários por CompanyID. OffersCommentDAO - Linha 385");
			e.printStackTrace();
		}

		return null;
	}

	public List<OffersComment> listByUserAndOffer(int userId, int offerId) {

		Map<String, Map<String, Map<String, String>>> key2 = new HashMap<String, Map<String, Map<String, String>>>();
		Map<String, Map<String, String>> params2 = new HashMap<String, Map<String, String>>();
		Map<String, String> conditions2 = new HashMap<String, String>();

		conditions2.put("OffersComment.user_id", String.valueOf(userId));
		conditions2.put("OffersComment.offer_id", String.valueOf(offerId));
		params2.put("conditions", conditions2);
		key2.put("OffersComment", params2);

		List<OffersComment> comments = OffersCommentBO
				.listAllOffersComments(key2);

		return comments;
	}

}
