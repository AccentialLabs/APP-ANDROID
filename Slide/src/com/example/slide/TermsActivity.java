package com.example.slide;

import com.accential.trueone.utils.OrientacaoUtils;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;

public class TermsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.x_activity_terms_conditions);
		overridePendingTransition(R.anim.fadein, R.anim.fadeout);

		// verificamos se aplicacao está sendo usada em um smartphone ou tablet
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
	}
}
