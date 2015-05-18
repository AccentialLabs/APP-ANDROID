package com.example.slide;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.util.Log;
import android.view.Window;

import com.accential.trueone.utils.OrientacaoUtils;
import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.PushService;

public class SplashActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.x_activity_splashscreen);

		// verificamos se aplicacao esta sendo usada em um smartphone ou tablet
		String type = getResources().getString(R.string.isTablet);
		if (type.equals("false")) {
			Log.e("tstando list de busca",
					"Estamos abrindo o app de um smartphe");
			OrientacaoUtils.setOrientationVertical(this);
		} else {
			Log.e("tstando lista de busca",
					"Estamos abrindo o app de um tablet");
			OrientacaoUtils.setOrientationHorizontal(this);
		}

		// SETANDO INFOS RESPONSAVEIS POR RECEBER E TRATAR AS NOTIFICAÇÕES
		// Initialize the Parse SDK.

		// SETANDO INFOS RESPONSAVEIS POR RECEBER E TRATAR AS NOTIFICAÇÕES
		// Initialize the Parse SDK.
		Parse.initialize(this, "IyTyFpCdR1duRL7ArQxc1JHwqtrd4ZTgCqwTtyxZ",
				"yDr6RXY957GF0epw0bihWwct5v43dPlw939E8w5i");

		// Specify an Activity to handle all pushes by default.
		PushService
				.setDefaultPushCallback(this, NotificationRespActivity.class);
		
		//capturando id do aparelho e salvando como identificador unico
		String android_id = Secure.getString(this.getContentResolver(),
				Secure.ANDROID_ID);
		
		Log.e("", "*************************************************************");
		Log.e("", "ANDROID ID >>" + android_id);
		Log.e("", "*************************************************************");
		
		ParseInstallation installation = ParseInstallation
				.getCurrentInstallation();
		installation.put("UniqueId", android_id);

		installation.saveInBackground();

		String deviceToken = (String) ParseInstallation
				.getCurrentInstallation().get("UniqueId");

		Log.e("", "*******************************************");
		Log.e("", "PARSE INICADO...");
		Log.e("", "*******************************************");

		/****** Create Thread that will sleep for 5 seconds *************/
		Thread background = new Thread() {
			public void run() {

				try {
					// Thread will sleep for 5 seconds
					sleep(2 * 1000);

					// After 5 seconds redirect to another intent
					Intent i = new Intent(getBaseContext(),
							AdvetaInitialActivity.class);
					startActivity(i);

					// Remove activity
					finish();

				} catch (Exception e) {

				}
			}
		};

		background.start();
	}

	@Override
	protected void onDestroy() {

		super.onDestroy();

	}

}
