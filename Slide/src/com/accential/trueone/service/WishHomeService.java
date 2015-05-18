package com.accential.trueone.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;

import com.accential.trueone.bean.Wishlist;
import com.accential.trueone.bo.WishlistBO;
import com.example.slide.WishHomeActivity.WishlistHomeResponseReceiver;

@SuppressWarnings("all")
public class WishHomeService extends IntentService {
	public static final String PARAM_IN_USER_ID = "userId";
	public static final String PARAM_OUT_WISHLIST = "userWishlist";
	public static final String PARAM_OUT_OFFERS_QTD = "qtd";

	public WishHomeService() {
		super("WishHomeService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {

		// recupera user id
		int id = intent.getIntExtra(PARAM_IN_USER_ID, 0);

		// List<Wishlist> wishs = WishlistBO.retornaWishies(id);

		Map wishes = WishlistBO.listWithQtdOffers(id);
		Map qtd = (Map) wishes.get("offersQtd");
		List<Wishlist> wishs = (List<Wishlist>) wishes.get("wishes");

		// enviando a lista para a tela da listagem de desejos
		Intent broadcastIntent = new Intent();
		broadcastIntent
				.setAction(WishlistHomeResponseReceiver.ACTION_RESP_WISHLIST_HOME);
		broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
		Bundle bundle = new Bundle();
		bundle.putSerializable(PARAM_OUT_WISHLIST, (Serializable) wishs);
		bundle.putSerializable(PARAM_OUT_OFFERS_QTD, (Serializable) qtd);
		broadcastIntent.putExtras(bundle);
		sendBroadcast(broadcastIntent);

	}
}
