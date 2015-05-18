package com.accential.trueone.service;

import java.io.Serializable;
import java.util.List;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;

import com.accential.trueone.bean.OfferFilter;
import com.accential.trueone.bo.OfferFilterBO;
import com.example.slide.OfferFilterActivity.OfferFilterResponseReceiver;

/**
 * Carrega os filtros para serem listados dentro dos Spinners na tela de
 * "criaçao de um perfil"
 * 
 * @author accentialbrasil
 * 
 */
public class OfferFilterService extends IntentService {

	public static final String PARAM_OFFERS_FILTER = "filters";

	public OfferFilterService() {
		super("OfferFilterService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {

		List<OfferFilter> filters = OfferFilterBO.listAllOffersFilters();

		Intent broadcastIntent = new Intent();
		broadcastIntent
				.setAction(OfferFilterResponseReceiver.ACTION_RESP_OFFER_FILTER);
		broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
		Bundle bundle = new Bundle();
		bundle.putSerializable(PARAM_OFFERS_FILTER, (Serializable) filters);
		broadcastIntent.putExtras(bundle);
		sendBroadcast(broadcastIntent);

	}

}
