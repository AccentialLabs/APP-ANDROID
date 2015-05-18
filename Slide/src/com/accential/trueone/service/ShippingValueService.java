package com.accential.trueone.service;

import java.io.Serializable;
import java.util.Map;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.accential.trueone.bo.CheckoutBO;
import com.example.slide.CheckoutActivity.ShippingValueResponseReceiver;

/**
 * Faz o calculo do frete de acordo com os ceps (origem e destino) e o peso
 * enviado
 * 
 * @author Matheus Odilon - accentialbrasil
 * 
 */
@SuppressWarnings("all")
public class ShippingValueService extends IntentService {
	public static final String PARAM_IN_CEP_DESTINO = "cepDestino";
	public static final String PARAM_IN_CEP_ORIGEM = "cepOrigem";
	public static final String PARAM_IN_PESO = "produtoPeso";
	public static final String PARAM_OUT_FRETE = "mapFrete";

	public ShippingValueService() {
		super("ShippingValueService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {

		Log.e("Shipping Service", "Executando Shipping Service");

		String cepOrigem = intent.getStringExtra(PARAM_IN_CEP_ORIGEM);
		String cepDestino = intent.getStringExtra(PARAM_IN_CEP_DESTINO);
		String peso = intent.getStringExtra(PARAM_IN_PESO);

		Map frete = CheckoutBO.calculaFrete(cepOrigem, cepDestino, peso);

		Intent broadcastIntent = new Intent();
		broadcastIntent
				.setAction(ShippingValueResponseReceiver.ACTION_RESP_SHIPPING_VAL);
		broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
		Bundle bundle = new Bundle();
		bundle.putSerializable(PARAM_OUT_FRETE, (Serializable) frete);
		broadcastIntent.putExtras(bundle);
		sendBroadcast(broadcastIntent);
	}
}
