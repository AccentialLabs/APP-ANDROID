package com.accential.trueone.service;

import android.app.IntentService;
import android.content.Intent;

import com.accential.trueone.bo.CompaniesUserBO;
import com.example.slide.SignaturesActivity.SignCompResponseReceiver;

/**
 * Responsavel por fazer a assinatura da empresa (criar ou editar)
 * 
 * @author Matheus Odilon - accentialbrasil
 * 
 */
public class SignCompanyService extends IntentService {
	public static final String PARAM_IN_USER_ID = "userId";
	public static final String PARAM_IN_COMPANY_ID = "compId";

	public SignCompanyService() {
		super("SignCompanyService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {

		int userId = intent.getIntExtra(PARAM_IN_USER_ID, 0);
		int compId = intent.getIntExtra(PARAM_IN_COMPANY_ID, 0);

		CompaniesUserBO.checkSignature(userId, compId);

		Intent broadcastIntent = new Intent();
		broadcastIntent
				.setAction(SignCompResponseReceiver.ACTION_RESP_SIGN_COMP);
		broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
		sendBroadcast(broadcastIntent);
	}
}
