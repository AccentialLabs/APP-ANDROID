package com.accential.trueone;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TabHost;
import android.widget.TextView;

import com.accential.trueone.bean.CompaniesInvitationsUser;
import com.accential.trueone.bo.CompaniesInvitationsUserBO;
import com.example.slide.R;

public class OfferActivity extends Activity {

	private TextView textViewTitleConvite;
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.teste_layout);

		/*OfferThread tOffers = new OfferThread();
		tOffers.execute();*/
		
		textViewTitleConvite = (TextView) findViewById(R.id.textView_compInviteCountTitle2);
		
		List<CompaniesInvitationsUser> invis = CompaniesInvitationsUserBO.searchByUserId(128);
		textViewTitleConvite.setText("Voc�� tem " + String.valueOf(invis.size()) + " convite(s) esperando confirma����o");
		TabHost tabs=(TabHost)findViewById(R.id.tabhost); 
		tabs.setup(); 
		
		TabHost.TabSpec spec = tabs.newTabSpec("tag1"); 
		spec.setContent(R.id.tab1); 
		spec.setIndicator("Analog Clock"); 
		tabs.addTab(spec); 
		
		spec = tabs.newTabSpec("tag2"); 
		spec.setContent(R.id.linearLayout_companies); 
		spec.setIndicator("TESTE LINEAR"); 
		tabs.addTab(spec); 
		
		spec = tabs.newTabSpec("tag3"); 
		spec.setContent(R.id.linearLayout_companiesAss); 
		spec.setIndicator("TESTE LINEAR 2"); 
		tabs.addTab(spec); 
		
		tabs.setCurrentTab(0); 
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
