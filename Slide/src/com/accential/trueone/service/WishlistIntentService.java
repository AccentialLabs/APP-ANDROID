package com.accential.trueone.service;


import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.format.DateFormat;
import android.util.Log;

import com.accential.trueone.WishlistLand.WishsResponseReceiver;
import com.accential.trueone.bean.CompanyCategory;
import com.accential.trueone.bo.CompanyCategoryBO;


/**
 * Faz a carga de conteudos assincronamente
 * @author Matheus Odilon - accentialbrasil
 *
 */
public class WishlistIntentService extends IntentService{

	public static final String PARAM_IN_MSG = "imsg";
	public static final String PARAM_OUT_MSG = "omsg";
	public static final String PARAM_OUT_CATEGORIE = "categories";

	public WishlistIntentService() {
		super("WishlistIntentService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {

		//		//CARREGA LISTA DE CATEGORY
		//		final Map<String, Map<String, Map<String, String>>> key = new HashMap<String,Map<String, Map<String, String>>>();
		//		final Map<String,Map<String,String>> params = new HashMap<String,Map<String,String>>();
		//		final Map<String,String> conditions = new HashMap<String,String>();
		//
		//		params.put("conditions", conditions);
		//		key.put("CompaniesCategory", params);
		//
		//		//GRAÇAS AO METODO 'toString'IMPLEMENTADO NA BEAN CompanyCategory PODEMOS CARREGOR O SPINNER COM UMA LISTA DE OBJETOS
		//		List<CompanyCategory> categories = CompanyCategoryBO.listAllCategories(key);

		String msg = intent.getStringExtra(PARAM_IN_MSG);
		SystemClock.sleep(30000); // 30 seconds
		String resultTxt = msg + " "
				+ DateFormat.format("MM/dd/yy h:mmaa", System.currentTimeMillis());

		// processing done here….
		Intent broadcastIntent = new Intent();
		//		Bundle bundle = new Bundle();

		//		bundle.putSerializable(PARAM_OUT_CATEGORIE, (Serializable) categories);

		broadcastIntent.setAction(WishsResponseReceiver.ACTION_RESPOSTA);
		broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
		broadcastIntent.putExtra(PARAM_OUT_MSG, resultTxt);
		//broadcastIntent.putExtras(bundle);
		sendBroadcast(broadcastIntent);
		Log.i("EXECULTANDO SERVICO", "TESTE DE SERVICO - EXECULTOU O SERVICO");
	}
}
