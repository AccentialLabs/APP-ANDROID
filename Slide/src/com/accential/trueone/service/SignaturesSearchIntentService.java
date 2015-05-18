package com.accential.trueone.service;

import java.io.Serializable;
import java.util.List;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.accential.trueone.SignaturesActivity.SignaturesSearchResponseReceiver;
import com.accential.trueone.bean.CompaniesUser;
import com.accential.trueone.bo.CompaniesUserBO;

public class SignaturesSearchIntentService extends IntentService {
	public static final String PARAM_IN_TITLE = "searchMyTitle";
	public static final String PARAM_IN_USER_ID = "userId";
	public static final String PARAM_OUT_COMP_LIST = "qtdOfferByWish";
	public static final String PARAM_OUT_SIGNED_COMPS = "signedComps";

	public SignaturesSearchIntentService() {
		super("SignaturesSearchIntentService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {

		/**
		 * PARAMENTROS ENVIANDO PELA ACTIVITY VIA INTENT
		 * title = PARA REALIZAR PESQUISA DE COMPANY POR NOME
		 * id = PARA REALIZAR PESQUISA DAS COMPANIES ASSINADAS PELO USUARIO
		 */
		String title = intent.getStringExtra(PARAM_IN_TITLE);
		int id = intent.getIntExtra(PARAM_IN_USER_ID, 0);

		Log.i("teste", "TITULO A SER PESQUISADO: " + title);
		Log.i("teste", "ID DO USUARIO A SER PESQUISADO: " + String.valueOf(id));

		List<CompaniesUser> searchComps = CompaniesUserBO.searchByName(title);
		List<CompaniesUser> singnedComps = CompaniesUserBO.returnObjCompaniesUserNoFilter(id);

		Log.i("TESTE EXECUTAR SERVICE", "AQUI EXECULTOU O SERVICE");
		Log.i("TESTE EXECUTAR SERVICE", "TAMANHO DA LISTA DE ASSINATURA - SERVICE: " + String.valueOf(searchComps.size()));
		Log.i("TESTE EXECUTAR SERVICE", "TAMANHO DA COMPANIAS ASSINADAS - SERVICE: " + String.valueOf(singnedComps.size()));

		// processing done here….
		Intent broadcastIntent = new Intent();
		broadcastIntent.setAction(SignaturesSearchResponseReceiver.ACTION_RESP_SIGNATURE_SEARCH);
		broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
		Bundle bundle = new Bundle();
		bundle.putSerializable(PARAM_OUT_COMP_LIST, (Serializable) searchComps);
		bundle.putSerializable(PARAM_OUT_SIGNED_COMPS, (Serializable) singnedComps);
		broadcastIntent.putExtras(bundle);
		sendBroadcast(broadcastIntent);
	}

}
