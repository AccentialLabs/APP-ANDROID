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
import com.accential.trueone.bean.OfferFilter;
import com.accential.trueone.bo.OfferFilterBO;
import com.accential.trueone.bo.UtilityComponentBO;
import com.accential.trueone.interfaces.IOfferFilter;
import com.accential.trueone.utils.JSONUtils;

@SuppressWarnings("all")
public class OfferFilterDAO implements IOfferFilter {

	@SuppressLint("SimpleDateFormat")
	@Override
	public List<OfferFilter> listOffersFilters(Map params) {

		List<OfferFilter> filters = null;
		UtilityComponentBO bo = null;

		try {
			bo = new UtilityComponentBO();
			filters = new ArrayList<OfferFilter>();

			JSONArray array = bo.urlRequestToGetData("offers", "all", params);

			if (array != null) {
				List<HashMap> objs = JSONUtils.toList(array);

				OfferFilter filter = null;
				Offer offer = null;
				Map values = null;
				Map offersValues = null;
				SimpleDateFormat sdf = new SimpleDateFormat(
						"dd/MM/yyyy HH:mm:ss");
				if (!objs.isEmpty()) {
					Log.e("", "OfferFilterDAO linha 43: " + objs.toString());
					for (HashMap obj : objs) {

						values = JSONUtils.toMap(new JSONObject((Map) obj
								.get("OffersFilter")));
						filter = new OfferFilter();

						filter.setId(Integer.parseInt((String) values.get("id")));
						filter.setGender(String.valueOf(values.get("gender")));
						filter.setReligion(String.valueOf(values
								.get("religion")));
						filter.setPolitical(String.valueOf(values
								.get("political")));
						filter.setAgeGroup(String.valueOf(values
								.get("age_group")));
						filter.setLocation(String.valueOf(values
								.get("location")));
						filter.setRelatioshipStatus(String.valueOf(values
								.get("relationship_status")));

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

						filter.setOffer(offer);

						filters.add(filter);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return filters;
	}

	public List<OfferFilter> listAllOffersFilters() {

		AsyncTask<Map, Void, List<OfferFilter>> async = new AsyncTask<Map, Void, List<OfferFilter>>() {

			@Override
			protected List<OfferFilter> doInBackground(Map... params) {
				return OfferFilterBO.listOffersFilters(params[0]);
			}

		};

		try {
			Map key = new HashMap();
			Map<String, Map<String, String>> params = new HashMap<String, Map<String, String>>();
			Map<String, String> conditions = new HashMap<String, String>();

			key.put("Offer", params);
			params.put("conditions", conditions);
			key.put("OffersFilter", params);

			return async.execute(key).get();
		} catch (Exception e) {
			e.printStackTrace();
			Log.e("Error OfferFilterDAO", "Erro na classe OfferFilterDAO");
			return null;
		}

	}

	public List<OfferFilter> searchOffersFilters(OfferFilter filter) {

		AsyncTask<Map, Void, List<OfferFilter>> async = new AsyncTask<Map, Void, List<OfferFilter>>() {
			@Override
			protected List<OfferFilter> doInBackground(Map... params) {
				return OfferFilterBO.listOffersFilters(params[0]);
			}
		};

		try {

			Map key = new HashMap();
			Map<String, Map<String, String>> params = new HashMap<String, Map<String, String>>();
			Map<String, String> conditions = new HashMap<String, String>();

			key.put("Offer", params);

			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();

			Log.e("gender",
					"VALOR DO STATUS DE RELACIONAMENTOS: "
							+ filter.getRelatioshipStatus());

			conditions.put("Offer.ends_at >",
					String.valueOf(formatter.format(date)));
			conditions.put("Offer.status", "ACTIVE");
			conditions.put("OffersFilter.age_group LIKE",
					"%" + filter.getAgeGroup() + "%");
			conditions.put("OffersFilter.religion LIKE",
					"%" + filter.getReligion() + "%");
			conditions.put("OffersFilter.gender LIKE", "%" + filter.getGender()
					+ "%");
			conditions.put("OffersFilter.political LIKE",
					"%" + filter.getPolitical() + "%");
			conditions.put("OffersFilter.location LIKE",
					"%" + filter.getLocation() + "%");
			conditions.put("OffersFilter.relationship_status LIKE", "%"
					+ filter.getRelatioshipStatus() + "%");
			params.put("conditions", conditions);
			key.put("OffersFilter", params);

			Log.e("", "MAP: " + key.toString());

			return async.execute(key).get();

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
