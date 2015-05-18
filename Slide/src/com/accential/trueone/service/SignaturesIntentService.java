package com.accential.trueone.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.accential.trueone.SignaturesActivity.SignaturesResponseReceiver;
import com.accential.trueone.TesteViews2.WishResponseReceiver;
import com.accential.trueone.bean.CompaniesUser;
import com.accential.trueone.bean.Wishlist;
import com.accential.trueone.bo.CompaniesUserBO;
import com.accential.trueone.bo.WishlistBO;

public class SignaturesIntentService extends IntentService {
	public static final String PARAM_IN_USER_ID = "userId";
	public static final String PARAM_OUT_LIST_SIGNATURE = "qtdSignatures";

	public SignaturesIntentService() {
		super("SignaturesIntentService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		List<CompaniesUser> comps = new ArrayList<CompaniesUser>();

		int id = intent.getIntExtra(PARAM_IN_USER_ID, 0);
		Log.i("TESTE EXECUTAR SERVICE", "ID DO USUARIO: " + String.valueOf(id));
		comps = CompaniesUserBO.returnObjCompaniesUser(id);

		Log.i("TESTE EXECUTAR SERVICE", "AQUI EXECULTOU O SERVICE");
		Log.i("TESTE EXECUTAR SERVICE", "TAMANHO DA LISTA DE ASSINATURAS: " + String.valueOf(comps.size()));

		// processing done here…
		Intent broadcastIntent = new Intent();
		broadcastIntent.setAction(SignaturesResponseReceiver.ACTION_RESP_SIGNATURE);
		broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
		Bundle bundle = new Bundle();
		bundle.putSerializable(PARAM_OUT_LIST_SIGNATURE, (Serializable) comps);
		broadcastIntent.putExtras(bundle);
		sendBroadcast(broadcastIntent);

	}
}
