package com.accential.trueone.service;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.accential.trueone.bean.Offer;
import com.accential.trueone.bo.OfferBO;

public class FirstFragmentService extends IntentService {

	public static final String PARAM_OUT_FIRST_FRAG = "offersList";

	public FirstFragmentService() {
		super("FirstFragmentService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		Log.e("SERVICO","EXECUTANDO O SERVICO");
		// faz a busca das ofertas
		Map<String, Map<String, Map<String, String>>> key = new HashMap<String, Map<String, Map<String, String>>>();
		Map<String, Map<String, String>> paramss = new HashMap<String, Map<String, String>>();
		Map<String, String> conditions = new HashMap<String, String>();

		conditions.put("Offer.ends_at >", "2014-10-20");
		conditions.put("Offer.status", "ACTIVE");
		conditions.put("Offer.public", "ACTIVE");
		paramss.put("conditions", conditions);
		key.put("Offer", paramss);

		List<Offer> offers = OfferBO.listAllOffers(key);
		
		
		//envia o resultado da busca do servico
		Intent broadcastIntent = new Intent();
		//broadcastIntent.setAction(FirstFragResponseReceiver.ACTION_RESP_FIRST_FRAG);
		broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
		Bundle bundle = new Bundle();
		bundle.putSerializable(PARAM_OUT_FIRST_FRAG, (Serializable) offers);
		broadcastIntent.putExtras(bundle);
		sendBroadcast(broadcastIntent);
	}

}
