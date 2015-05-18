package com.accential.trueone.service;


import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.accential.trueone.TesteViews2.NewsOfferReceiver;
import com.accential.trueone.bean.CompaniesInvitationsUser;
import com.accential.trueone.bo.CompaniesInvitationsUserBO;
import com.accential.trueone.bo.OfferBO;

public class OfferNewsIntentService extends IntentService {
	public static final String PARAM_OUT_QTD_NEWS = "qtdNews";
	public static final String PARAM_OUT_MAP_NEWS = "";
	public static final String PARAM_IN_USER_ID = "userId";
	public static final String MAP_NEWS = "offerNews";
	public static final String MAP_INVITES = "inviteNews";
	public static final String MAP_TOTAL_QTD = "qtdTotal";

	public OfferNewsIntentService() {
		super("OfferNewsIntentService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {

		Log.i("TESTE", "EXECULTA SERVICE DE NEWS OFFER");

		int userId = intent.getIntExtra(PARAM_IN_USER_ID, 0);

		List<CompaniesInvitationsUser> invites = CompaniesInvitationsUserBO.searchByUserId(userId);

		int qtdNews = OfferBO.news();

		Map<String, Object> myNews = new HashMap<String, Object>();
		myNews.put(MAP_NEWS, qtdNews);
		myNews.put(MAP_INVITES, invites);
		myNews.put(MAP_TOTAL_QTD, qtdNews + invites.size());

		Log.i("teste", "SERVICE - Quantidade de novas: " + String.valueOf(qtdNews));

		// processing done here….
		Intent broadcastIntent = new Intent();
		broadcastIntent.setAction(NewsOfferReceiver.ACTION_RESP_NEWS_OFFERS);
		broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
		broadcastIntent.putExtra(PARAM_OUT_QTD_NEWS, qtdNews);
		Bundle bundle = new Bundle();
		bundle.putSerializable(PARAM_OUT_MAP_NEWS, (Serializable) myNews);
		broadcastIntent.putExtras(bundle);
		sendBroadcast(broadcastIntent);
	}
}
