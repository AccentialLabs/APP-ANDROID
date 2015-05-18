package com.accential.trueone.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.accential.trueone.bean.Offer;
import com.accential.trueone.bean.OfferFilter;
import com.accential.trueone.bo.OfferFilterBO;
import com.example.slide.OfferFilterActivity.OfferFilterSearchResponseReceiver;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;

/**
 * Faz a pesquisa de ofertas por perfil criado por usuario
 * 
 * @author Matheus Odilon - accentialbrasil
 * 
 */
public class OfferFilterSearchService extends IntentService {
	public static final String PARAM_IN_FILTER = "filter";
	public static final String PARAM_OUT_OFFERS = "offers";

	public OfferFilterSearchService() {
		super("OfferFilterSearchService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {

		Bundle parameters = intent.getExtras();
		OfferFilter filter = (OfferFilter) parameters
				.getSerializable(PARAM_IN_FILTER);

		List<OfferFilter> filters = OfferFilterBO.searchOffersFilters(filter);

		List<Offer> offers = new ArrayList<Offer>();

		// recuperando as ofertas
		for (OfferFilter filt : filters) {
			offers.add(filt.getOffer());
		}

		Intent broadcastIntent = new Intent();
		broadcastIntent
				.setAction(OfferFilterSearchResponseReceiver.ACTION_RESP_OFFER_FILTER_SEARCH);
		broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
		Bundle bundle = new Bundle();
		bundle.putSerializable(PARAM_OUT_OFFERS, (Serializable) offers);
		broadcastIntent.putExtras(bundle);
		sendBroadcast(broadcastIntent);

	}

}
