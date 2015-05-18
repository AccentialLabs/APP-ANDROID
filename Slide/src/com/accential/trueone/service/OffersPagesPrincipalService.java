package com.accential.trueone.service;

import java.io.Serializable;
import java.util.List;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.accential.trueone.bean.Offer;
import com.accential.trueone.bo.OfferBO;
import com.example.slide.AdventaActivity.OffersPagePrincipalResponseReceiver;

public class OffersPagesPrincipalService extends IntentService {

	public static final String PARAM_IN_PAGE = "numPagePrincipal";
	public static final String PARAM_OUT_OFFERS = "myOffersPagePrincipal";

	public OffersPagesPrincipalService() {
		super("OffersPagesPrincipalService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {

		Log.e("", "CHAMOU O SERVICE");
		int page = intent.getIntExtra(PARAM_IN_PAGE, 0);

		List<Offer> offers = OfferBO.listValidOffersForPage(page);

		Intent broadcastIntent = new Intent();
		broadcastIntent
				.setAction(OffersPagePrincipalResponseReceiver.ACTION_RESP_OFFERS_PAGE_PRINCIPAL);
		broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
		Bundle bundle = new Bundle();
		bundle.putSerializable(PARAM_OUT_OFFERS, (Serializable) offers);
		broadcastIntent.putExtras(bundle);
		sendBroadcast(broadcastIntent);

	}

}
