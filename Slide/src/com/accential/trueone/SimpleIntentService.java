package com.accential.trueone;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;

import com.accential.trueone.SignaturesActivity.SignaturesSearchResponseReceiver;
import com.accential.trueone.TesteViews2.WishResponseReceiver;
import com.accential.trueone.bean.CompanyCategory;
import com.accential.trueone.bean.Offer;
import com.accential.trueone.bean.Wishlist;
import com.accential.trueone.bo.CompanyCategoryBO;
import com.accential.trueone.bo.OfferBO;
import com.accential.trueone.bo.WishlistBO;

public class SimpleIntentService extends IntentService {
	public static final String PARAM_IN_MSG = "imsg";
	public static final String PARAM_OUT_MSG = "omsg";
	public static final String PARAM_OUT_USER = "user";
	public static final String PARAM_OUT_CATEGORIE = "categoria";
	public static final String PARAM_IN_USER_ID = "userId";
	public static final String PARAM_OUT_WISH_QTD_OFFER = "qtdOfferByWish";

	public SimpleIntentService() {
		super("SimpleIntentService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {

		Map<String, Integer> qtdOffersWish = new HashMap<String, Integer>(); 

		//CARREGA LISTA DE CATEGORY
		final Map<String, Map<String, Map<String, String>>> key = new HashMap<String,Map<String, Map<String, String>>>();
		final Map<String,Map<String,String>> params = new HashMap<String,Map<String,String>>();
		final Map<String,String> conditions = new HashMap<String,String>();
		params.put("conditions", conditions);
		key.put("CompaniesCategory", params);
		List<CompanyCategory> categories = CompanyCategoryBO.listAllCategories(key);
		//-----------------------------------------------

		String msg = intent.getStringExtra(PARAM_IN_MSG);
		//SystemClock.sleep(30000); // 30 seconds
		String resultTxt = msg + " " 
				+ DateFormat.format("MM/dd/yy h:mmaa", System.currentTimeMillis());

		int id = intent.getIntExtra(PARAM_IN_USER_ID, 0);
		Log.i("TESTE EXECUTAR SERVICE", "ID DO USUARIO: " + String.valueOf(id));
		final List<Wishlist> wishies = WishlistBO.retornaWishies(id);

		for (Wishlist wishlist : wishies) {
			List<Offer> offs = OfferBO.searchOffersByTitle(wishlist.getName());
			qtdOffersWish.put(wishlist.getName(), offs.size());
		}

		Log.i("TESTE EXECUTAR SERVICE", "AQUI EXECULTOU O SERVICE");
		Log.i("TESTE EXECUTAR SERVICE", "TAMANHO DA LISTA DE DESEJOS: " + String.valueOf(wishies.size()));

		// processing done here….
		Intent broadcastIntent = new Intent();
		broadcastIntent.setAction(SignaturesSearchResponseReceiver.ACTION_RESP_SIGNATURE_SEARCH);
		broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
		broadcastIntent.putExtra(PARAM_OUT_MSG, resultTxt);
		Bundle bundle = new Bundle();
		bundle.putSerializable(PARAM_OUT_WISH_QTD_OFFER, (Serializable) qtdOffersWish);
		bundle.putSerializable(PARAM_OUT_CATEGORIE, (Serializable) categories);
		broadcastIntent.putExtras(bundle);
		sendBroadcast(broadcastIntent);
	}
}