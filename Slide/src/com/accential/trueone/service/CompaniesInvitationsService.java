package com.accential.trueone.service;

import java.io.Serializable;
import java.util.List;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;

import com.accential.trueone.CompaniesInvitationsActivity.CompaniesInvitationsResponseReceiver;
import com.accential.trueone.bean.CompaniesInvitationsUser;
import com.accential.trueone.bo.CompaniesInvitationsUserBO;

public class CompaniesInvitationsService extends IntentService {
	public static final String PARAM_IN_USER_ID = "userId";
	public static final String PARAM_OUT_INVITATIONS = "invitationsList";

	public CompaniesInvitationsService(){
		super("CompaniesInvitationsService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {

		int userId = intent.getIntExtra(PARAM_IN_USER_ID, 0);

		List<CompaniesInvitationsUser> invitations = 
				CompaniesInvitationsUserBO.returnObjsCompInviteUser(userId);

		//PROCESSAMENTO COMPLETO - ENVIANDO...
		Intent broadcastIntent = new Intent();
		broadcastIntent.setAction(CompaniesInvitationsResponseReceiver.ACTION_RESP_COMPS_INVITATION);
		broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
		Bundle bundle = new Bundle();
		bundle.putSerializable(PARAM_OUT_INVITATIONS, (Serializable) invitations);
		broadcastIntent.putExtras(bundle);
		sendBroadcast(broadcastIntent);
	}

}
