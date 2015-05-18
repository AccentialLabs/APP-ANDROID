package com.accential.trueone.service;

import java.io.Serializable;
import java.util.List;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.accential.trueone.bean.Checkout;
import com.accential.trueone.bo.CheckoutBO;
import com.example.slide.CheckoutActivity.CheckoutResponseReceiver;

/**
 * Cria o Checkout e retorna o registro criado
 * 
 * @author Matheus Odilon - accentialbrasil
 * 
 */
public class CheckoutService extends IntentService {

	public static final String PARAM_IN_CHECKOUT = "checkObj";
	public static final String PARAM_OUT_CHECKOUT = "checkReturn";

	public CheckoutService() {
		super("CheckoutService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {

		Log.e("service", "EXECUTANDO SERVICE - 1");
		Bundle parameters = intent.getExtras();

		Checkout check = (Checkout) parameters
				.getSerializable(CheckoutService.PARAM_IN_CHECKOUT);
		Log.e("service", "EXECUTANDO SERVICE - 2");
		CheckoutBO.createCheckout(check);

		List<Checkout> returnCheck = CheckoutBO.listAllCheckoutsByUser(check
				.getUser().getId());
		Log.e("service", "EXECUTANDO SERVICE - 3");

		Checkout checkReturn = returnCheck.get(returnCheck.size() - 1);

		Intent broadcastIntent = new Intent();
		broadcastIntent
				.setAction(CheckoutResponseReceiver.ACTION_RESP_CREATE_CHECKOUT);
		broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
		Bundle bundle = new Bundle();
		bundle.putSerializable(PARAM_OUT_CHECKOUT, (Serializable) checkReturn);
		broadcastIntent.putExtras(bundle);
		sendBroadcast(broadcastIntent);

	}

}
