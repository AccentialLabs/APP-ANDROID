package com.accential.trueone.service;

import java.io.Serializable;
import java.util.List;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.accential.trueone.bean.CompaniesInvitationsUser;
import com.accential.trueone.bo.CompaniesInvitationsUserBO;
import com.example.slide.InvitatiosActivity.InvitsResponseReceiver;

public class InvitationsIntentService extends IntentService {
	public static final String PARAM_IN_USER_ID = "userId";
	public static final String PARAM_OUT_INVITATIONS = "listInvitations";

	public InvitationsIntentService() {
		super("InvitationsIntentService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {

		int userId = intent.getIntExtra(PARAM_IN_USER_ID, 0);

		Log.e("", "SERVICE - ID DO USUARIO: " + String.valueOf(userId));

		List<CompaniesInvitationsUser> invitations = CompaniesInvitationsUserBO
				.searchInvitationsWaiting(userId);

		Intent broadcastIntent = new Intent();
		broadcastIntent
				.setAction(InvitsResponseReceiver.ACTION_RESP_INVITATIONS);
		broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
		Bundle bundle = new Bundle();
		bundle.putSerializable(PARAM_OUT_INVITATIONS,
				(Serializable) invitations);
		broadcastIntent.putExtras(bundle);
		sendBroadcast(broadcastIntent);

	}

}
