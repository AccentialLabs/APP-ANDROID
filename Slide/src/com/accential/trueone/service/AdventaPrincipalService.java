package com.accential.trueone.service;

import java.io.Serializable;
import java.util.List;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;

import com.accential.trueone.bean.Offer;
import com.accential.trueone.bo.OfferBO;
import com.example.slide.AdventaActivity.AdventaResponseReceiver;

/**
 * Carrega as ofertas principais
 * 
 * @author Matheus Odilon - accentialbrasil
 * 
 */
public class AdventaPrincipalService extends IntentService {

	public static final String PARAM_OFFERS = "offers";

	public AdventaPrincipalService() {
		super("AdventaPrincipalService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {

		List<Offer> offers = OfferBO.listValidOffers();

		Intent broadcastIntent = new Intent();
		broadcastIntent
				.setAction(AdventaResponseReceiver.ACTION_RESP_ADVENTA_PRINCIPAL);
		broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
		Bundle bundle = new Bundle();
		bundle.putSerializable(PARAM_OFFERS, (Serializable) offers);
		broadcastIntent.putExtras(bundle);
		sendBroadcast(broadcastIntent);

	}

}
