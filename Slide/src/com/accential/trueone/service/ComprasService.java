package com.accential.trueone.service;

import java.io.Serializable;
import java.util.List;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;

import com.accential.trueone.bean.Checkout;
import com.accential.trueone.bo.CheckoutBO;
import com.example.slide.ComprasActivity.ComprasResponseReceiver;

public class ComprasService extends IntentService {

	public static final String PARAM_COMPRAS_USER_ID = "userId";
	public static final String PARAM_COMPRAS_LIST = "compras";

	public ComprasService() {
		super("ComprasService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {

		int userId = intent.getIntExtra(PARAM_COMPRAS_USER_ID, 0);
		List<Checkout> compras = CheckoutBO.listByUser(userId);

		Intent broadcastIntent = new Intent();
		broadcastIntent.setAction(ComprasResponseReceiver.ACTION_RESP_COMPRAS);
		broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
		Bundle bundle = new Bundle();
		bundle.putSerializable(PARAM_COMPRAS_LIST, (Serializable) compras);
		broadcastIntent.putExtras(bundle);
		sendBroadcast(broadcastIntent);

	}

}
