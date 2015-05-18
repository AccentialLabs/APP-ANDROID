package com.accential.trueone.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import android.os.AsyncTask;

import com.accential.trueone.bean.OfferPhoto;
import com.accential.trueone.bo.OfferPhotoBO;
import com.accential.trueone.bo.UtilityComponentBO;
import com.accential.trueone.interfaces.IOfferPhoto;
import com.accential.trueone.utils.JSONUtils;

@SuppressWarnings("all")
public class OfferPhotoDAO implements IOfferPhoto {

	@Override
	public List<OfferPhoto> listOfferPhotos(Map params) {

		List<OfferPhoto> photos = null;
		UtilityComponentBO bo = null;

		try {
			bo = new UtilityComponentBO();
			photos = new ArrayList<OfferPhoto>();

			JSONArray array = bo.urlRequestToGetData("offers", "all", params);

			if (array != null) {
				List<HashMap> objs = JSONUtils.toList(array);

				OfferPhoto photo = null;
				Map values = null;

				if (!objs.isEmpty()) {
					for (HashMap obj : objs) {
						values = JSONUtils.toMap(new JSONObject((Map) obj
								.get("OffersPhoto")));
						photo = new OfferPhoto();
						photo.setId(Integer.parseInt((String) values.get("id")));
						photo.setPhotoUrl(String.valueOf(values.get("photo")));

						photos.add(photo);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return photos;
	}

	@Override
	public List<OfferPhoto> listPhotoByOffer(int offerId) {

		AsyncTask<Map, Void, List<OfferPhoto>> async = new AsyncTask<Map, Void, List<OfferPhoto>>() {
			@Override
			protected List<OfferPhoto> doInBackground(Map... params) {

				return OfferPhotoBO.listOfferPhotos(params[0]);
			}
		};

		try {

			Map<String, Map<String, Map<String, String>>> key = new HashMap<String, Map<String, Map<String, String>>>();
			Map<String, Map<String, String>> params = new HashMap<String, Map<String, String>>();
			Map<String, String> conditions = new HashMap<String, String>();

			conditions.put("OffersPhoto.offer_id", String.valueOf(offerId));
			params.put("conditions", conditions);
			key.put("OffersPhoto", params);

			return async.execute(key).get();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

}
