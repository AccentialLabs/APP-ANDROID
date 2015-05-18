package com.accential.trueone;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.Window;
import android.webkit.WebView;

import com.example.slide.R;

public class TermsAndConditionsActivity extends Activity{

	private WebView termsAndConditions;

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);								
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_terms);		

		termsAndConditions = (WebView) findViewById(R.id.webView_terms);
		termsAndConditions.getSettings().setJavaScriptEnabled(true);
		termsAndConditions.loadUrl("http://trueone.com.br/testes/termos_t1/termos_iPhone.html");

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
