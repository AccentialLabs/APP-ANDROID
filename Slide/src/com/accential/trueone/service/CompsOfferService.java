package com.accential.trueone.service;

import java.io.Serializable;
import java.util.List;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.accential.trueone.bean.Offer;
import com.accential.trueone.bo.OfferBO;
import com.example.slide.OffersListActivity.CompsOfferResponseReceiver;

public class CompsOfferService extends IntentService {
	public static final String PARAM_IN_COMP_ID = "companyId";
	public static final String PARAM_OUT_OFFERS = "myOffers";

	public CompsOfferService() {
		super("CompsOfferService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {

		int compId = intent.getIntExtra(PARAM_IN_COMP_ID, 0);

		List<Offer> offers = OfferBO.listOffersByCompany(compId);

		Log.e("",
				"SERVICE ---------- OFERTAS: " + String.valueOf(offers.size()));

		Intent broadcastIntent = new Intent();
		broadcastIntent
				.setAction(CompsOfferResponseReceiver.ACTION_RESP_COMPANIES_OFFER);
		broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
		Bundle bundle = new Bundle();
		bundle.putSerializable(PARAM_OUT_OFFERS, (Serializable) offers);
		broadcastIntent.putExtras(bundle);
		sendBroadcast(broadcastIntent);
	}

}
