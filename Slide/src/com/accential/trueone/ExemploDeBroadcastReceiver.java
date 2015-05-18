package com.accential.trueone;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class ExemploDeBroadcastReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		Log.i("teste", "ESSE É MEU RECEIVER TESTE");
		Toast.makeText(context, "ESSE É O TEXTO DO ALARME", Toast.LENGTH_LONG).show();
	}

}