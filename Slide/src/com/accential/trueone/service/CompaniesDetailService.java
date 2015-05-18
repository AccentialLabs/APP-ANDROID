package com.accential.trueone.service;

import java.io.Serializable;
import java.util.List;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;

import com.accential.trueone.bean.Offer;
import com.accential.trueone.bean.OffersComment;
import com.accential.trueone.bo.OfferBO;
import com.accential.trueone.bo.OffersCommentBO;
import com.example.slide.CompaniesDetailFirstActivity.CompaniesDetailResponseReceiver;

/**
 * Busca ofertas e comentarios relacionados de determinada oferta para ser
 * mostrado no detalhe da Company
 * 
 * @author Matheus Odilon - accentialbrasil
 */
public class CompaniesDetailService extends IntentService {

	public static final String PARAM_IN_COMPANY_ID = "companyId";
	public static final String PARAM_OUT_OFFERS = "offers";
	public static final String PARAM_OUT_COMMENTS = "comments";

	public CompaniesDetailService() {
		super("CompaniesDetailService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {

		int companyId = intent.getIntExtra(PARAM_IN_COMPANY_ID, 0);

		// buscando ofertas
		List<Offer> offers = OfferBO.listOffersByCompany(companyId);

		// buscando comentarios de todas as ofertas da empresa
		List<OffersComment> comments = OffersCommentBO.listByCompany(companyId);

		Intent broadcastIntent = new Intent();
		broadcastIntent
				.setAction(CompaniesDetailResponseReceiver.ACTION_RESPONSE_COMPANIES_DETAIL);
		broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
		Bundle bundle = new Bundle();
		bundle.putSerializable(PARAM_OUT_OFFERS, (Serializable) offers);
		bundle.putSerializable(PARAM_OUT_COMMENTS, (Serializable) comments);
		broadcastIntent.putExtras(bundle);
		sendBroadcast(broadcastIntent);
	}
}
