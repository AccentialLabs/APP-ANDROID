package com.accential.trueone.service;

import java.io.Serializable;
import java.util.List;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.accential.trueone.ComprasActivity.CheckResponseReceiver;
import com.accential.trueone.bean.Checkout;
import com.accential.trueone.bo.CheckoutBO;

public class CheckIntentService extends IntentService {
	public static final String PARAM_IN_USER_ID = "userId";
	public static final String PARAM_OUT_CHECK_LIST = "qtdOfferByWish";

	public CheckIntentService() {
		super("CheckIntentService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {

		int id = intent.getIntExtra(PARAM_IN_USER_ID, 0);
		Log.i("TESTE EXECUTAR SERVICE", "ID DO USUARIO: " + String.valueOf(id));
		final List<Checkout> checkouts = CheckoutBO.returnsObjCheckout(id);

		Log.i("TESTE EXECUTAR SERVICE", "AQUI EXECULTOU O SERVICE DA CLASSE CHECK");
		Log.i("TESTE EXECUTAR SERVICE", "TAMANHO DA LISTA DE CHECKS: " + String.valueOf(checkouts.size()));

		// processing done here….
		Intent broadcastIntent = new Intent();
		broadcastIntent.setAction(CheckResponseReceiver.ACTION_RESP);
		broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
		Bundle bundle = new Bundle();
		bundle.putSerializable(PARAM_OUT_CHECK_LIST, (Serializable) checkouts);
		broadcastIntent.putExtras(bundle);
		sendBroadcast(broadcastIntent);
	}

}
