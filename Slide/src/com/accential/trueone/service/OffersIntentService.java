package com.accential.trueone.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;

import com.accential.trueone.OffersWishlist.OffersResponseReceiver;
import com.accential.trueone.bean.Offer;
import com.accential.trueone.bean.UsersWishlistCompany;
import com.accential.trueone.bo.UsersWishlistCompanyBO;

public class OffersIntentService extends IntentService {
	public static final String PARAM_IN_MSG = "imsg";
	public static final String PARAM_OUT_MSG = "omsg";
	public static final String PARAM_OUT_USER = "user";
	public static final String PARAM_OUT_CATEGORIE = "categoria";
	public static final String PARAM_IN_USER_ID = "userId";
	public static final String PARAM_OUT_WISH_QTD_OFFER = "qtdOfferByWish";
	public static final String PARAM_IN_WISH_ID = "wishId";
	public static final String PARAM_OUT_LIST_OFFER = "offerList";

	public OffersIntentService() {
		super("OffersIntentService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {

		int wishId = intent.getIntExtra(PARAM_IN_WISH_ID, 0);

		Log.i("TESTE", "ID DO WISHLIST: " + String.valueOf(wishId));

		List<Offer> offers = new ArrayList<Offer>();
		List<UsersWishlistCompany> uwcs = UsersWishlistCompanyBO.listaObjUWCByWish(wishId);

		for (UsersWishlistCompany uwc : uwcs) {
			offers.add(uwc.getOffer());
			Log.i("TESTE", "TESTE DAS OFERTAS: " + uwc.getOffer().getTitle());
		}

		String msg = intent.getStringExtra(PARAM_IN_MSG);
		//SystemClock.sleep(30000); // 30 seconds
		String resultTxt = msg + " " 
				+ DateFormat.format("MM/dd/yy h:mmaa", System.currentTimeMillis());

		Log.i("TESTE EXECUTAR SERVICE", "AQUI EXECULTOU O SERVICE");

		// processing done here….
		Intent broadcastIntent = new Intent();
		broadcastIntent.setAction(OffersResponseReceiver.RESP_ACTION);
		broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
		broadcastIntent.putExtra(PARAM_OUT_MSG, resultTxt);
		Bundle bundle = new Bundle();
		bundle.putSerializable(PARAM_OUT_LIST_OFFER, (Serializable) offers);
		broadcastIntent.putExtras(bundle);
		Log.i("teste", "TERMINOU SERVIÇO: IRÁ ENVIAR BROADCAST");
		Log.i("TESTE", "BROADCAST: " + String.valueOf(broadcastIntent));

		sendBroadcast(broadcastIntent);
	}

}
