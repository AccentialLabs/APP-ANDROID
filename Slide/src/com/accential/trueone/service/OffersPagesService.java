package com.accential.trueone.service;

import java.io.Serializable;
import java.util.List;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;

import com.accential.trueone.bean.Offer;
import com.accential.trueone.bo.OfferBO;
import com.example.slide.AdvetaInitialActivity.OffersPageResponseReceiver;

public class OffersPagesService extends IntentService {
	public static final String PARAM_IN_PAGE = "numPage";
	public static final String PARAM_OUT_OFFERS = "myOffersPage";

	public OffersPagesService() {
		super("OffersPagesService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {

		int page = intent.getIntExtra(PARAM_IN_PAGE, 0);

		List<Offer> offers = OfferBO.listValidOffersForPage(page);

		Intent broadcastIntent = new Intent();
		broadcastIntent
				.setAction(OffersPageResponseReceiver.ACTION_RESP_OFFERS_PAGE_RECEIVER);
		broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
		Bundle bundle = new Bundle();
		bundle.putSerializable(PARAM_OUT_OFFERS, (Serializable) offers);
		broadcastIntent.putExtras(bundle);
		sendBroadcast(broadcastIntent);

	}

}
