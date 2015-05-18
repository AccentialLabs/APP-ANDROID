package com.accential.trueone.service;

import com.accential.trueone.bo.CompaniesInvitationsUserBO;
import com.example.slide.SignaturesActivity.InvitationsCountResponseReceiver;

import android.app.IntentService;
import android.content.Intent;

public class CheckInvitationsService extends IntentService {
	public static final String PARAM_IN_USER_ID = "userId";
	public static final String PARAM_OUT_INVITATIONS = "qtdInvitations";

	public CheckInvitationsService() {
		super("CheckInvitationsService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {

		int userId = intent.getIntExtra(PARAM_IN_USER_ID, 0);

		int total = CompaniesInvitationsUserBO.countInvitationsWaiting(userId);

		Intent broadcastIntent = new Intent();
		broadcastIntent
				.setAction(InvitationsCountResponseReceiver.ACTION_RESP_INVITE_COUNT);
		broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
		broadcastIntent.putExtra(PARAM_OUT_INVITATIONS, total);
		sendBroadcast(broadcastIntent);

	}

}
