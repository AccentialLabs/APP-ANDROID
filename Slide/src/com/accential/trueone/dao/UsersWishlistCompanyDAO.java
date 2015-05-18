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

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.util.Log;

import com.accential.trueone.bean.Offer;
import com.accential.trueone.bean.UsersWishlistCompany;
import com.accential.trueone.bo.UsersWishlistCompanyBO;
import com.accential.trueone.bo.UtilityComponentBO;
import com.accential.trueone.interfaces.IUsersWishlistCompany;
import com.accential.trueone.utils.JSONUtils;

@SuppressWarnings("all")
public class UsersWishlistCompanyDAO implements IUsersWishlistCompany {

	@SuppressLint("SimpleDateFormat")
	public List<UsersWishlistCompany> listUsersWishlistByQuery(Map params) {

		List<UsersWishlistCompany> uwcList = null;
		UtilityComponentBO bo = null;

		try {
			bo = new UtilityComponentBO();
			uwcList = new ArrayList<UsersWishlistCompany>();

			JSONArray array = bo.urlRequestToGetData("users", "query", params);

			if (array != null) {
				List<HashMap> objs = JSONUtils.toList(array);

				UsersWishlistCompany uwc = null;
				Offer offer = null;
				Map values = null;
				Map offersValues;

				Log.e("OBJS", "OBJS QUERY: " + objs.toString());
				SimpleDateFormat sdf = new SimpleDateFormat(
						"dd/MM/yyyy HH:mm:ss");

				if (!objs.isEmpty()) {
					for (HashMap obj : objs) {
						values = JSONUtils.toMap(new JSONObject((Map) obj
								.get("users_wishlist_companies")));
						uwc = new UsersWishlistCompany();

						uwc.setId(Integer.parseInt((String) values.get("id")));
						uwc.setStatus(String.valueOf(values.get("status")));

						// offer
						offersValues = JSONUtils.toMap(new JSONObject((Map) obj
								.get("offers")));
						offer = new Offer();

						offer.setId(Integer.parseInt((String) offersValues
								.get("id")));
						offer.setTitle(String.valueOf(offersValues.get("title")));
						offer.setResume(String.valueOf(offersValues
								.get("resume")));
						offer.setDescription(String.valueOf(offersValues
								.get("description")));
						offer.setSpecification(String.valueOf(offersValues
								.get("specification")));
						offer.setValue(Float.valueOf((String) offersValues
								.get("value")));
						offer.setPercentageDiscount(Integer
								.valueOf((String) offersValues
										.get("percentage_discount")));
						offer.setWeight(Float.valueOf((String) offersValues
								.get("weight")));
						offer.setAmountAllowed(Integer
								.valueOf((String) offersValues
										.get("amount_allowed")));

						// parse beginAt
						Calendar begins = Calendar.getInstance();
						String stringdata = String.valueOf(
								offersValues.get("begins_at"))
								.replace("-", "/");
						Date begin = new Date(stringdata);
						String sting = sdf.format(begin);
						begins.setTime(begin);

						// parse endsAt
						Calendar ends = Calendar.getInstance();
						String stringData = String.valueOf(
								offersValues.get("ends_at")).replace("-", "/");
						Date endsDate = new Date(stringData);
						ends.setTime(endsDate);

						offer.setBeginsAt(begins);
						offer.setEndsAt(ends);
						offer.setPhoto(String.valueOf(offersValues.get("photo")));
						offer.setMetrics(String.valueOf(offersValues
								.get("metrics")));
						offer.setParcels(String.valueOf(offersValues
								.get("parcels")));
						offer.setParcelsOffImpost(String.valueOf(offersValues
								.get("parcels_off_impost")));
						offer.setPublicStr(String.valueOf(offersValues
								.get("public")));
						offer.setStatus(String.valueOf(offersValues
								.get("status")));

						uwc.setOffer(offer);

						uwcList.add(uwc);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return uwcList;
	}

	@Override
	public List<UsersWishlistCompany> listUsersWishlist(Map params) {

		List<UsersWishlistCompany> uwcList = null;
		UtilityComponentBO bo = null;

		try {
			bo = new UtilityComponentBO();
			uwcList = new ArrayList<UsersWishlistCompany>();

			JSONArray array = bo.urlRequestToGetData("users", "all", params);

			if (array != null) {
				List<HashMap> objs = JSONUtils.toList(array);

				UsersWishlistCompany uwc = null;
				Map values = null;

				Log.e("OBJS", "OBJS: " + objs.toString());

				if (!objs.isEmpty()) {
					for (HashMap obj : objs) {
						values = JSONUtils.toMap(new JSONObject((Map) obj
								.get("UsersWishlistCompany")));
						uwc = new UsersWishlistCompany();

						uwc.setId(Integer.parseInt((String) values.get("id")));
						uwc.setStatus(String.valueOf(values.get("status")));

						uwcList.add(uwc);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return uwcList;
	}

	@Override
	public List<UsersWishlistCompany> listAllUsersWishlist(Map params) {

		AsyncTask<Map, Void, List<UsersWishlistCompany>> async = new AsyncTask<Map, Void, List<UsersWishlistCompany>>() {
			@Override
			protected List<UsersWishlistCompany> doInBackground(Map... params) {
				List<UsersWishlistCompany> uwcs = UsersWishlistCompanyBO
						.listUsersWishlist(params[0]);
				return uwcs;
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
	public int countUsersWishlistCompany(Map params) {
		int contador_usersWishComp = 0;
		UtilityComponentBO bo = null;
		try {
			bo = new UtilityComponentBO();
			JSONArray array = bo.urlRequestToGetData("users", "all", params);

			if (array != null) {
				contador_usersWishComp = array.length();
				array = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return contador_usersWishComp;
	}

	@Override
	public int countUWC(Map params) {

		AsyncTask<Map, Void, Integer> async = new AsyncTask<Map, Void, Integer>() {
			@Override
			protected Integer doInBackground(Map... params) {
				return UsersWishlistCompanyBO
						.countUsersWishlistCompany(params[0]);
			}
		};
		try {
			return async.execute(params).get();
		} catch (Exception e) {
			e.printStackTrace();
			Log.e("Erro na contagem", "Erro na contagem de UWC");
			return 0;
		}
	}

	@Override
	public int countUWCByWish(int wishId) {

		Map<String, Map<String, Map<String, String>>> key2 = new HashMap<String, Map<String, Map<String, String>>>();
		Map<String, Map<String, String>> params2 = new HashMap<String, Map<String, String>>();
		Map<String, String> conditions2 = new HashMap<String, String>();

		conditions2.put("UsersWishlistCompany.wishlist_id",
				String.valueOf(wishId));
		params2.put("conditions", conditions2);
		key2.put("UsersWishlistCompany", params2);

		return UsersWishlistCompanyBO.countUWC(key2);
	}

	@Override
	public List<UsersWishlistCompany> listAllUWCByUser(int userId) {

		Map<String, Map<String, Map<String, String>>> key2 = new HashMap<String, Map<String, Map<String, String>>>();
		Map<String, Map<String, String>> params2 = new HashMap<String, Map<String, String>>();
		Map<String, String> conditions2 = new HashMap<String, String>();

		conditions2.put("UsersWishlistCompany.status", "ACTIVE");
		conditions2.put("UsersWishlistCompany.user_id", String.valueOf(userId));
		params2.put("conditions", conditions2);
		key2.put("UsersWishlistCompany", params2);

		return UsersWishlistCompanyBO.listAllUsersWishlist(key2);
	}

	@Override
	public List<UsersWishlistCompany> listAllUWCByWish(int wishId) {

		Map<String, Map<String, Map<String, String>>> key2 = new HashMap<String, Map<String, Map<String, String>>>();
		Map<String, Map<String, String>> params2 = new HashMap<String, Map<String, String>>();
		Map<String, String> conditions2 = new HashMap<String, String>();

		conditions2.put("UsersWishlistCompany.status", "ACTIVE");
		conditions2.put("UsersWishlistCompany.wishlist_id",
				String.valueOf(wishId));
		params2.put("conditions", conditions2);
		key2.put("UsersWishlistCompany", params2);

		return UsersWishlistCompanyBO.listAllUsersWishlist(key2);
	}

	public int returnsObejectId(Map params, String atributo) {
		int id = 0;
		UtilityComponentBO bo = null;

		try {
			bo = new UtilityComponentBO();
			JSONArray array = bo.urlRequestToGetData("users", "all", params);

			if (array != null) {
				List<HashMap> objs = JSONUtils.toList(array);
				Map values = null;
				if (!objs.isEmpty()) {
					for (HashMap obj : objs) {
						values = JSONUtils.toMap(new JSONObject((Map) obj
								.get("UsersWishlistCompany")));
						id = Integer.parseInt((String) values.get(atributo));
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return id;
	}

	public List<UsersWishlistCompany> listaObjUWCByWish(int wishId) {

		List<UsersWishlistCompany> returnsUwc = null;
		OfferDAO dao = null;

		List<UsersWishlistCompany> uwcs = UsersWishlistCompanyBO
				.listAllUWCByWish(wishId);

		try {
			dao = new OfferDAO();
			returnsUwc = new ArrayList<UsersWishlistCompany>();
			for (UsersWishlistCompany uwc : uwcs) {
				uwc.setOffer(dao.searchOfferByUWC(uwc.getId()));
				returnsUwc.add(uwc);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return returnsUwc;
	}

	public void offersByUsersWishlist() {

		AsyncTask<Map, Void, List<UsersWishlistCompany>> async = new AsyncTask<Map, Void, List<UsersWishlistCompany>>() {
			@Override
			protected List<UsersWishlistCompany> doInBackground(Map... params) {
				return UsersWishlistCompanyBO.listUsersWishlist(params[0]);
			}
		};

		try {

			Map key = new HashMap();
			Map<String, Map<String, String>> params = new HashMap<String, Map<String, String>>();
			Map<String, String> conditions = new HashMap<String, String>();

			conditions.put("UsersWishlistCompany.wishlist_id", "72");
			params.put("conditions", conditions);
			key.put("UsersWishlistCompany", params);

			List<UsersWishlistCompany> wishes = async.execute(key).get();

			Log.e("tamanho",
					"tamanho da lista: " + String.valueOf(wishes.size()));

		} catch (Exception e) {
			e.printStackTrace();
			Log.e("erro", "erro UsersWishlistCompany - offersByUsersWishlist");
		}

	}

	public List<Offer> listOfferByWish(int wishId) {

		// lista retorno
		List<Offer> offers = new ArrayList<Offer>();

		AsyncTask<Map, Void, List<UsersWishlistCompany>> async = new AsyncTask<Map, Void, List<UsersWishlistCompany>>() {
			@Override
			protected List<UsersWishlistCompany> doInBackground(Map... params) {
				UsersWishlistCompanyDAO dao = new UsersWishlistCompanyDAO();
				return dao.listUsersWishlistByQuery(params[0]);
			}
		};

		try {

			Map param = new HashMap();
			Map query = new HashMap();

			query.put(
					"query",
					"select * from offers INNER JOIN users_wishlist_companies ON offers.id = users_wishlist_companies.offer_id where users_wishlist_companies.wishlist_id ="
							+ String.valueOf(wishId) + ";");
			param.put("User", query);

			List<UsersWishlistCompany> wishes = async.execute(param).get();

			// foreach passando capturando ofertas retornadas
			for (UsersWishlistCompany uwc : wishes) {
				offers.add(uwc.getOffer());
			}

			return offers;

		} catch (Exception e) {
			e.printStackTrace();
			Log.e("ERROR UsersWishlistCompany",
					"ERROR UsersWishlistCompany - Metodo: listOfferByWish");
			return null;
		}

	}
}
