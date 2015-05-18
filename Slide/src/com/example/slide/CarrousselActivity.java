package com.example.slide;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ProgressBar;

import com.accential.trueone.CompaniesInvitationsActivity;
import com.accential.trueone.adapter.FinalOfferAdapter;
import com.accential.trueone.adapter.InvitationAdapter;
import com.accential.trueone.adapter.OfferFullAdapter;
import com.accential.trueone.bean.CompaniesInvitationsUser;
import com.accential.trueone.bean.Offer;
import com.accential.trueone.bo.OfferBO;
import com.accential.trueone.service.CompaniesInvitationsService;
import com.accential.trueone.service.ComprasService;
import com.accential.trueone.service.OffersPagesService;
import com.accential.trueone.utils.InfiniteScrollListener;
import com.example.slide.ComprasActivity.ComprasResponseReceiver;

@SuppressLint("NewApi")
@SuppressWarnings("all")
public class CarrousselActivity extends Activity {

	private EditText busca;
	private GridView mostra;
	private List<Offer> offers;
	// private FinalOfferAdapter adapter;
	private OfferFullAdapter adapter;
	private int i;
	private List<Offer> mys;
	private ProgressBar carrega;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.teste_busca);

		carrega = (ProgressBar) findViewById(R.id.carrega);
		busca = (EditText) findViewById(R.id.busca);
		mostra = (GridView) findViewById(R.id.mostra);
		offers = OfferBO.listValidOffersForPage(1);

		adapter = new OfferFullAdapter(this, offers);
		mostra.setAdapter(adapter);
		i = 2;

		// mostra.setOnScrollListener(new InfiniteScrollListener(6) {
		// @Override
		// public void loadMore(int page, int totalItemsCount) {
		//
		// Log.e("", "chamando service");
		// // mys = OfferBO.listValidOffersForPage(i);
		// // chamando service
		// Intent commentsIntent = new Intent(CarrousselActivity.this,
		// OffersPagesService.class);
		// commentsIntent.putExtra(OffersPagesService.PARAM_IN_PAGE, i);
		// CarrousselActivity.this.startService(commentsIntent);
		//
		// // preparando o receiver para o retorno do service
		// IntentFilter filter = new IntentFilter(
		// OffersPageResponseReceiver.ACTION_RESP_OFFERS_PAGE_RECEIVER);
		// filter.addCategory(Intent.CATEGORY_DEFAULT);
		// receiver = new OffersPageResponseReceiver();
		// CarrousselActivity.this.registerReceiver(receiver, filter);
		// }
		// });
	}

	
}