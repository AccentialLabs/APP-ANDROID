package com.accential.trueone.service;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;

import com.accential.trueone.WishlistLand.WishsResponseReceiver;
import com.accential.trueone.bean.CompanyCategory;
import com.accential.trueone.bean.Offer;
import com.accential.trueone.bean.Wishlist;
import com.accential.trueone.bo.CompanyCategoryBO;
import com.accential.trueone.bo.OfferBO;
import com.accential.trueone.bo.UsersWishlistCompanyBO;
import com.accential.trueone.bo.WishlistBO;
/**
 * Carrega informações do cliente e sua wishlist
 * @author Matheus Odilon - accentialbrasil
 *
 */
public class SimpleIntentServiceWish extends IntentService {
	public static final String PARAM_IN_MSG = "imsg";
	public static final String PARAM_OUT_MSG = "omsg";
	public static final String PARAM_OUT_USER = "user";
	public static final String PARAM_OUT_CATEGORIE = "categoria";
	public static final String PARAM_IN_USER_ID = "userId";
	public static final String PARAM_OUT_USER_WISH = "userWishlist";
	public static final String PARAM_OUT_WISH_QTD_OFFER = "qtdOfferByWish";

	public SimpleIntentServiceWish() {
		super("SimpleIntentServiceWish");
	}

	@Override
	protected void onHandleIntent(Intent intent) {

		Map<String, Integer> qtdOffersByWish = new HashMap<String, Integer>(); 

		//CARREGA LISTA DE WISH DO USUARIO
		int id = intent.getIntExtra(PARAM_IN_USER_ID, 0);
		List<Wishlist> wishies = WishlistBO.retornaWishies(id);

		for (Wishlist wishlist : wishies) {
			qtdOffersByWish.put(wishlist.getName(), UsersWishlistCompanyBO.countUWCByWish(wishlist.getId()));
		}

		//CARREGA LISTA DE CATEGORY
		final Map<String, Map<String, Map<String, String>>> key = new HashMap<String,Map<String, Map<String, String>>>();
		final Map<String,Map<String,String>> params = new HashMap<String,Map<String,String>>();
		final Map<String,String> conditions = new HashMap<String,String>();

		params.put("conditions", conditions);
		key.put("CompaniesCategory", params);

		List<CompanyCategory> categories = CompanyCategoryBO.listAllCategories(key);

		Log.i("TESTE EXECUTAR SERVICE", "AQUI EXECULTOU O SERVICE");

		String msg = intent.getStringExtra(PARAM_IN_MSG);
		//SystemClock.sleep(30000); // 30 seconds
		String resultTxt = msg + " " 
				+ DateFormat.format("MM/dd/yy h:mmaa", System.currentTimeMillis());

		// processing done here….
		Intent broadcastIntent = new Intent();
		broadcastIntent.setAction(WishsResponseReceiver.ACTION_RESPOSTA);
		Log.i("test", "ACTION DO INTENT: " + broadcastIntent.getAction());
		broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
		broadcastIntent.putExtra(PARAM_OUT_MSG, resultTxt);
		Bundle bundle = new Bundle();
		bundle.putSerializable(PARAM_OUT_CATEGORIE, (Serializable) categories);
		bundle.putSerializable(PARAM_OUT_USER_WISH, (Serializable) wishies);
		bundle.putSerializable(PARAM_OUT_WISH_QTD_OFFER, (Serializable) qtdOffersByWish);
		broadcastIntent.putExtras(bundle);
		sendBroadcast(broadcastIntent);

	}
}