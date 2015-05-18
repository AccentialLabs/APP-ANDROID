package com.accential.trueone.service;

import com.accential.trueone.bo.CompaniesInvitationsUserBO;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

public class InvitationsService extends IntentService {
	public static final String PARAM_IN_INVITATION_ID = "inviId";
	public static final String PARAM_IN_ACTIVE_INACTIVE = "activeOrInactive";
	public static final String PARAM_OUT_LIST = "invitationsList";

	public InvitationsService() {
		super("InvitationsService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {

		int id = intent.getIntExtra(PARAM_IN_INVITATION_ID, 0);
		int actOrInact = intent.getIntExtra(PARAM_IN_ACTIVE_INACTIVE, 100);
		Log.i("teste", "ID DO INVITE: " + String.valueOf(id));

		if (actOrInact == 0) {
			Log.i("teste", "OPCAO SELECIONADA - ATIVAR");
			// CompaniesInvitationsUserBO.activateInvite(id);
		} else if (actOrInact == 1) {
			Log.i("teste", "OPCAO SELECIONADA - DESATIVAR");
			CompaniesInvitationsUserBO.inactivateInvite(id);
		}

		// Intent intentBroadcast = new Intent();
		// intentBroadcast.setAction(ActOrAnactInvitationsResponseReceiver.ACTION_RESP_ACTIVE_INVITATION);
		// intentBroadcast.addCategory(Intent.CATEGORY_DEFAULT);
		// Bundle bundle = new Bundle();
		// bundle.putString("resultado", "SUCESSO EM EXECUTAR SERVICE");
		// intentBroadcast.putExtras(bundle);
		// sendBroadcast(intentBroadcast);

	}

}
