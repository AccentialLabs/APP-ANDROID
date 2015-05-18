package com.accential.trueone.service;

import java.io.Serializable;
import java.util.List;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.accential.trueone.bean.CompaniesUser;
import com.accential.trueone.bo.CompaniesUserBO;
import com.example.slide.SignaturesActivity.SignaturesResponseReceiver;

public class SignaturesService extends IntentService {
	public static final String PARAM_USER_ID = "userId";
	public static final String PARAM_SIGNATURES_LIST = "signatures";

	public SignaturesService() {
		super("SignaturesService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		int userId = intent.getIntExtra(PARAM_USER_ID, 0);

		List<CompaniesUser> signatures = CompaniesUserBO.listByUser(userId);

		Log.e("", "service lista:" + String.valueOf(signatures.size()));

		Intent broadcastIntent = new Intent();
		broadcastIntent
				.setAction(SignaturesResponseReceiver.ACTION_RESP_SIGNATURES);
		broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
		Bundle bundle = new Bundle();
		bundle.putSerializable(PARAM_SIGNATURES_LIST, (Serializable) signatures);
		broadcastIntent.putExtras(bundle);
		sendBroadcast(broadcastIntent);
	}
}
