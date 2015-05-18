package com.accential.trueone.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.accential.trueone.bean.Offer;
import com.accential.trueone.bean.OffersUser;
import com.accential.trueone.bo.OffersUserBO;
import com.example.slide.SignaturesActivity.CompOffersProfileResponseReceiver;

public class CompaniesOfferProfileService extends IntentService {
	public static final String PARAM_IN_USER_ID = "userId";
	public static final String PARAM_OUT_OFFERS = "offers";

	public CompaniesOfferProfileService() {
		super("CompaniesOfferProfileService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {

		int userId = intent.getIntExtra(PARAM_IN_USER_ID, 0);

		List<OffersUser> offerUsers = OffersUserBO
				.listAllOffersUsersByUser(userId);

		List<Offer> offers = new ArrayList<Offer>();

		for (OffersUser offersUser : offerUsers) {
			offers.add(offersUser.getOffer());
		}

		Log.e("", "SERVICE - OFERTAS PROFILE: " + String.valueOf(offers.size()));

		Intent broadcastIntent = new Intent();
		broadcastIntent
				.setAction(CompOffersProfileResponseReceiver.ACTION_RESP_OFFERS_MY_PROFILE);
		broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
		Bundle bundle = new Bundle();
		bundle.putSerializable(PARAM_OUT_OFFERS, (Serializable) offers);
		broadcastIntent.putExtras(bundle);
		sendBroadcast(broadcastIntent);
	}

}
