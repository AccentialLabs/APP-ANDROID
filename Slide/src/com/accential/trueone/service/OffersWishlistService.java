package com.accential.trueone.service;

import java.io.Serializable;
import java.util.List;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.accential.trueone.bean.Offer;
import com.accential.trueone.bo.UsersWishlistCompanyBO;
import com.example.slide.OffersListActivity.OffersWishResponseReceiver;

public class OffersWishlistService extends IntentService {
	public static final String PARAM_IN_WISH_ID = "wishId";
	public static final String PARAM_OUT_OFFERS = "offers";

	public OffersWishlistService() {
		super("OffersWishlistService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {

		Log.e("", "PASSSO --- 2");

		int id = intent.getIntExtra(PARAM_IN_WISH_ID, 0);

		List<Offer> offers = UsersWishlistCompanyBO.listOfferByWish(id);

		Log.e("", "PASSO --- 3");

		Intent broadcastIntent = new Intent();
		broadcastIntent
				.setAction(OffersWishResponseReceiver.ACTION_RESP_OFFERS_WISH);
		broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
		Bundle bundle = new Bundle();
		bundle.putSerializable(PARAM_OUT_OFFERS, (Serializable) offers);
		broadcastIntent.putExtras(bundle);
		sendBroadcast(broadcastIntent);
	}

}
