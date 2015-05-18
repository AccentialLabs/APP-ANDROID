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
import com.accential.trueone.bean.OffersUser;
import com.accential.trueone.bo.OffersUserBO;
import com.accential.trueone.bo.UtilityComponentBO;
import com.accential.trueone.interfaces.IOffersUser;
import com.accential.trueone.utils.JSONUtils;

@SuppressWarnings("all")
public class OffersUserDAO implements IOffersUser {

	@SuppressLint("SimpleDateFormat")
	@Override
	public List<OffersUser> listOffersUser(Map params) {

		List<OffersUser> offersUsers = null;
		UtilityComponentBO bo = null;

		try {
			bo = new UtilityComponentBO();
			offersUsers = new ArrayList<OffersUser>();

			JSONArray array = bo.urlRequestToGetData("offers", "all", params);

			if (array != null) {
				List<HashMap> objs = JSONUtils.toList(array);

				OffersUser offerUser = null;
				Offer offer = null;
				Map values = null;
				Map offersValues = null;
				SimpleDateFormat sdf = new SimpleDateFormat(
						"dd/MM/yyyy HH:mm:ss");
				if (!objs.isEmpty()) {
					for (HashMap obj : objs) {

						values = JSONUtils.toMap(new JSONObject((Map) obj
								.get("OffersUser")));
						offerUser = new OffersUser();

						offerUser.setId(Integer.parseInt(String.valueOf(values
								.get("id"))));
						offerUser
								.setTarget(String.valueOf(values.get("target")));

						// formatando data de registro
						Calendar dtResgister = Calendar.getInstance();
						String stringDtRegister = String.valueOf(
								values.get("date_register")).replace("-", "/");
						Date dtReg = new Date(stringDtRegister);
						String stingReg = sdf.format(dtReg);
						dtResgister.setTime(dtReg);

						offerUser.setDtRegister(dtResgister);

						/*
						 * DADOS DA OFERTA
						 */
						offersValues = JSONUtils.toMap(new JSONObject((Map) obj
								.get("Offer")));
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

						offerUser.setOffer(offer);

						offersUsers.add(offerUser);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return offersUsers;
	}

	@SuppressLint("SimpleDateFormat")
	public List<OffersUser> listAllOffersUsersByUser(int userId) {

		AsyncTask<Map, Void, List<OffersUser>> async = new AsyncTask<Map, Void, List<OffersUser>>() {
			@Override
			protected List<OffersUser> doInBackground(Map... params) {
				return OffersUserBO.listOffersUser(params[0]);
			}
		};

		try {
			Map key = new HashMap();
			Map<String, Map<String, String>> params = new HashMap<String, Map<String, String>>();
			Map<String, String> conditions = new HashMap<String, String>();

			// validamos a data de validade das ofertas a serem listadas
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			Date today = new Date();

			key.put("Offer", params);
			conditions.put("OffersUser.user_id", String.valueOf(userId));
			conditions.put("Offer.status", "ACTIVE");
			conditions.put("Offer.ends_at >=",
					String.valueOf(formatter.format(today)));
			conditions.put("Offer.begins_at <=",
					String.valueOf(formatter.format(today)));
			params.put("conditions", conditions);
			key.put("OffersUser", params);

			return async.execute(key).get();
		} catch (Exception e) {
			e.printStackTrace();
			Log.e("Error OfferFilterDAO", "Erro na classe OfferFilterDAO");
			return null;
		}

	}

	public List<OffersUser> listByCompanyAndUser(int userId, int compId) {

		List<OffersUser> returnOffers = new ArrayList<OffersUser>();

		AsyncTask<Map, Void, List<OffersUser>> async = new AsyncTask<Map, Void, List<OffersUser>>() {
			@Override
			protected List<OffersUser> doInBackground(Map... params) {
				return OffersUserBO.listOffersUser(params[0]);
			}
		};

		try {
			Map key = new HashMap();
			Map<String, Map<String, String>> params = new HashMap<String, Map<String, String>>();
			Map<String, String> conditions = new HashMap<String, String>();

			// validamos a data de validade das ofertas a serem listadas
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			Date today = new Date();

			key.put("Offer", params);

			conditions.put("OffersUser.user_id", String.valueOf(userId));
			conditions.put("Offer.status", "ACTIVE");
			conditions.put("Offer.ends_at >=",
					String.valueOf(formatter.format(today)));
			conditions.put("Offer.begins_at <=",
					String.valueOf(formatter.format(today)));
			params.put("conditions", conditions);
			key.put("OffersUser", params);

			List<OffersUser> myOffers = async.execute(key).get();

			return null;
		} catch (Exception e) {
			e.printStackTrace();
			Log.e("Error OfferFilterDAO", "Erro na classe OfferFilterDAO");
			return null;
		}
	}

}
