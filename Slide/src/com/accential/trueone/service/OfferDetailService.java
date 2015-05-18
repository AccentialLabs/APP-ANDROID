package com.accential.trueone.service;

import java.io.Serializable;
import java.util.List;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.accential.trueone.bean.Company;
import com.accential.trueone.bean.CompanyPreference;
import com.accential.trueone.bean.OfferPhoto;
import com.accential.trueone.bean.OffersComment;
import com.accential.trueone.bo.CompanyPreferenceBO;
import com.accential.trueone.bo.OfferPhotoBO;
import com.accential.trueone.bo.OffersCommentBO;
import com.example.slide.OffersFragFirst.OffersDetailsResponseReceiver;

public class OfferDetailService extends IntentService {
	public static final String PARAM_OUT_PREFERENCE = "companyPreference";
	public static final String PARAM_OUT_COMPANY = "company";
	public static final String PARAM_OUT_COMMENTS = "comments";
	public static final String PARAM_OUT_PHOTOS = "offersPhotos";
	public static final String PARAM_IN_OFFER_ID = "offerId";

	public OfferDetailService() {
		super("OfferDetailService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {

		Log.e("", "EXECUTANDO SERVICE DE BUSCA DE EMPRESA E PREFERENCIA");

		int offerId = intent.getIntExtra(PARAM_IN_OFFER_ID, 0);

		List<CompanyPreference> preferences = CompanyPreferenceBO
				.searchByOfferId(offerId);

		Company company = preferences.get(0).getCompany();

		List<OffersComment> comments = OffersCommentBO.listByOffer(offerId);

		// carregando fotos
		List<OfferPhoto> photos = OfferPhotoBO.listPhotoByOffer(offerId);

		Intent broadcastIntent = new Intent();
		broadcastIntent
				.setAction(OffersDetailsResponseReceiver.ACTION_RESP_OFFERS_DETAILS);
		broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
		Bundle bundle = new Bundle();
		bundle.putSerializable(PARAM_OUT_COMMENTS, (Serializable) comments);
		bundle.putSerializable(PARAM_OUT_COMPANY, (Serializable) company);
		bundle.putSerializable(PARAM_OUT_PREFERENCE, (Serializable) preferences);
		bundle.putSerializable(PARAM_OUT_PHOTOS, (Serializable) photos);
		broadcastIntent.putExtras(bundle);
		sendBroadcast(broadcastIntent);
	}
}
