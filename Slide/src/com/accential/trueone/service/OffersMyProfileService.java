package com.accential.trueone.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;

import com.accential.trueone.bean.Offer;
import com.accential.trueone.bean.OffersUser;
import com.accential.trueone.bo.OffersUserBO;
import com.example.slide.CheckoutActivity.CheckoutResponseReceiver;
import com.example.slide.OffersListActivity.OffersMyProfileResponseReceiver;

public class OffersMyProfileService extends IntentService {
	public static final String PARAM_IN_USER_ID = "userId";
	public static final String PARAM_OUT_OFFERS = "myOffersProfile";

	public OffersMyProfileService() {
		super("OffersMyProfileService");
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

		Intent broadcastIntent = new Intent();
		broadcastIntent
				.setAction(OffersMyProfileResponseReceiver.ACTION_RESP_OFFERS_MY_PROFILE);
		broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
		Bundle bundle = new Bundle();
		bundle.putSerializable(PARAM_OUT_OFFERS, (Serializable) offers);
		broadcastIntent.putExtras(bundle);
		sendBroadcast(broadcastIntent);

	}

}
