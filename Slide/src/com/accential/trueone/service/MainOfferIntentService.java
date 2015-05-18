package com.accential.trueone.service;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.accential.trueone.TesteViews2.WishResponseReceiver;
import com.accential.trueone.bean.Offer;
import com.accential.trueone.bo.OfferBO;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;

/**
 * 
 * @author Matheus Odilon - accentialbrasil
 *
 */
public class MainOfferIntentService extends IntentService{
	public static final String PARAM_OFFER_LIST = "offerList"; 

	public MainOfferIntentService() {
		super("MainOfferIntentService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		Map<String, Map<String, Map<String, String>>> key = new HashMap<String,Map<String, Map<String, String>>>();
		Map<String,Map<String,String>> params = new HashMap<String,Map<String,String>>();
		Map<String,String> conditions = new HashMap<String,String>();

		conditions.put("Offer.status", "ACTIVE");
		conditions.put("Offer.public", "ACTIVE");
		params.put("conditions", conditions);
		key.put("Offer", params);

		List<Offer> offers = OfferBO.listAllOffers(key);

		// processing done here���.
		Intent broadcastIntent = new Intent();
		broadcastIntent.setAction(WishResponseReceiver.ACTION_RESP);
		broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
		Bundle bundle = new Bundle();
		bundle.putSerializable(PARAM_OFFER_LIST, (Serializable) offers);
		broadcastIntent.putExtras(bundle);
		sendBroadcast(broadcastIntent);
	}

}
