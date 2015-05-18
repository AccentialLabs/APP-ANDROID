package com.example.slide;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.accential.trueone.bean.Offer;
import com.accential.trueone.bean.OfferFilter;
import com.accential.trueone.service.OfferFilterSearchService;
import com.accential.trueone.service.OfferFilterService;
import com.accential.trueone.utils.SessionControl;

@SuppressWarnings("all")
/**
 * PRONTA - CRIAR SERVICE PARA MOSTRAR PROGRESS ENQUANTO PESQUISA É REALIZADA
 * 
 * @author accentialbrasil
 * 
 */
public class OfferFilterActivity extends Activity {

	private SharedPreferences loggedUser;

	private Button btnFilter;
	private ProgressBar pbFilter;
	private OfferFilterResponseReceiver receiver;
	private OfferFilterSearchResponseReceiver searchReceiver;
	private View viewBack;

	private String age = "Selecione,";
	private String location = "Selecione-";
	private String political = "Selecione,";
	private String religion = "Selecione,";
	private String[] genderMap = { "", "male", "female" };
	private String[] relationShipMap = { "", "Single", "In a Relationship",
			"Engaged", "Married", "In an open relationship",
			"It's Complicated", "Divorced" };

	private List<OfferFilter> filters;
	private Spinner spGender;
	private Spinner spLocation;
	private Spinner spReligion;
	private Spinner spPolitical;
	private Spinner spAge;
	private Spinner spRelationship;

	private ArrayAdapter<String> genderAdapter;
	private ArrayAdapter<String> localAdapter;
	private ArrayAdapter<String> politicAdapter;
	private ArrayAdapter<String> ageAdapter;
	private ArrayAdapter<String> relationAdapter;
	private ArrayAdapter<String> religionAdapter;

	private TextView tvTitle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.x_activity_offers_filters);

		loggedUser = getSharedPreferences(SessionControl.USER_LOGGED, 0);

		String[] genders = { "Selecione", "Masculino", "Feminino" };
		String[] relationshipStatus = { "Selecione", "Solteiro", "Namorando",
				"Noivo", "Casado", "Relacionamento aberto", "Enrolado",
				"Divorciado" };

		spGender = (Spinner) findViewById(R.id.spFilterGender);
		spAge = (Spinner) findViewById(R.id.spFilterAge);
		spLocation = (Spinner) findViewById(R.id.spFilterLocation);
		spPolitical = (Spinner) findViewById(R.id.spnFilterPolitical);
		spRelationship = (Spinner) findViewById(R.id.spFilterRelationshipStatus);
		spReligion = (Spinner) findViewById(R.id.spFilterReligion);
		btnFilter = (Button) findViewById(R.id.btnFilter);
		pbFilter = (ProgressBar) findViewById(R.id.pbFilter);
		viewBack = (View) findViewById(R.id.viewBack);
		tvTitle = (TextView) findViewById(R.id.textView1);

		Typeface font = Typeface.createFromAsset(getAssets(),
				"helvetica-35-thin-1361522141.ttf");
		tvTitle.setTypeface(font);

		// gender adapter
		genderAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, genders);
		spGender.setAdapter(genderAdapter);

		// relationship adapter
		relationAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, relationshipStatus);
		spRelationship.setAdapter(relationAdapter);

		startandoService();

		btnFilter.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				searchOffers();
			}
		});
	}

	public void startandoService() {
		// start service de busca dos comentarios
		Intent intent = new Intent(OfferFilterActivity.this,
				OfferFilterService.class);
		this.startService(intent);

		// preparando o receiver para o retorno do service
		IntentFilter filter = new IntentFilter(
				OfferFilterResponseReceiver.ACTION_RESP_OFFER_FILTER);
		filter.addCategory(Intent.CATEGORY_DEFAULT);
		receiver = new OfferFilterResponseReceiver();
		OfferFilterActivity.this.registerReceiver(receiver, filter);
	}

	public void populaSpinners(List<OfferFilter> filters) {

		for (OfferFilter filter : filters) {

			if (!filter.getAgeGroup().equals("")) {
				age = age + filter.getAgeGroup() + ",";
			}
			if (!filter.getLocation().equals("")) {
				location = location + filter.getLocation() + "-";
			}
			if (!filter.getPolitical().equals("")) {
				political = political + filter.getPolitical() + ",";
			}
			if (!filter.getReligion().equals("")) {
				religion = religion + filter.getReligion() + ",";
			}
		}

		String[] ages = eliminaRepetidos(age.split(","));
		String[] locations = eliminaRepetidos(location.split("-"));
		String[] politicals = eliminaRepetidos(political.split(","));
		String[] religions = eliminaRepetidos(religion.split(","));

		// age adapter
		ageAdapter = new ArrayAdapter<String>(OfferFilterActivity.this,
				android.R.layout.simple_spinner_item, ages);
		spAge.setAdapter(ageAdapter);
		spAge.setSelection(ages.length - 1);

		// location adapter
		localAdapter = new ArrayAdapter<String>(OfferFilterActivity.this,
				android.R.layout.simple_spinner_item, locations);
		spLocation.setAdapter(localAdapter);
		spLocation.setSelection(locations.length - 1);

		// politic adapter
		politicAdapter = new ArrayAdapter<String>(OfferFilterActivity.this,
				android.R.layout.simple_spinner_item, politicals);
		spPolitical.setAdapter(politicAdapter);
		spPolitical.setSelection(politicals.length - 1);

		// religion adapter
		religionAdapter = new ArrayAdapter<String>(OfferFilterActivity.this,
				android.R.layout.simple_spinner_item, religions);
		spReligion.setAdapter(religionAdapter);
		spReligion.setSelection(religions.length - 1);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void searchOffers() {

		// mostrando views
		pbFilter.setVisibility(View.VISIBLE);
		viewBack.setVisibility(View.VISIBLE);

		String valGender = genderMap[spGender.getSelectedItemPosition()];
		// comentei aqui
		// String valRelationStatus = (String) spRelationship.getSelectedItem();
		String valLocation = (String) spLocation.getSelectedItem();
		String valReligion = (String) spReligion.getSelectedItem();
		String valPolitical = (String) spPolitical.getSelectedItem();
		String valAge = (String) spAge.getSelectedItem();

		valGender = verificaValor(valGender);
		valAge = verificaValor(valAge);
		valLocation = verificaValor(valLocation);
		valPolitical = verificaValor(valPolitical);

		// mudei aqui
		String valRelationStatus = relationShipMap[spRelationship
				.getSelectedItemPosition()];

		valReligion = verificaValor(valReligion);

		OfferFilter filter = new OfferFilter();

		filter.setGender(valGender);
		filter.setRelatioshipStatus(valRelationStatus);
		filter.setReligion(valReligion);
		filter.setLocation(valLocation);
		filter.setPolitical(valPolitical);
		filter.setAgeGroup(valAge);

		// start service de busca das ofertas
		Intent intent = new Intent(OfferFilterActivity.this,
				OfferFilterSearchService.class);
		Bundle bundle = new Bundle();
		bundle.putSerializable(OfferFilterSearchService.PARAM_IN_FILTER,
				(Serializable) filter);
		intent.putExtras(bundle);
		this.startService(intent);

		// preparando o receiver para o retorno do service
		IntentFilter intentFilter = new IntentFilter(
				OfferFilterSearchResponseReceiver.ACTION_RESP_OFFER_FILTER_SEARCH);
		intentFilter.addCategory(Intent.CATEGORY_DEFAULT);
		searchReceiver = new OfferFilterSearchResponseReceiver();
		OfferFilterActivity.this.registerReceiver(searchReceiver, intentFilter);

	}

	public String[] eliminaRepetidos(String[] strings) {

		HashSet<String> hsStrings = new HashSet<String>();

		for (String s : strings) {
			hsStrings.add(s);
		}

		int i = 0;
		String[] semRepetidos = new String[hsStrings.size()];

		for (String string : hsStrings) {
			semRepetidos[i++] = string;
		}

		return semRepetidos;
	}

	public String verificaValor(String valor) {

		String retorno = "";

		if (!valor.equals("Selecione")) {
			retorno = valor;
		}

		return retorno;
	}

	public class OfferFilterResponseReceiver extends BroadcastReceiver {
		public static final String ACTION_RESP_OFFER_FILTER = "com.mamlambo.intent.action.MESSAGE_PROCESSED_OFFER_FILTER";

		@Override
		public void onReceive(Context context, Intent intent) {

			Log.e("Retorno do Service", "Retorno do Service");
			// recurando lista de offersFilter capturada no service
			Bundle parameters = intent.getExtras();
			filters = (List<OfferFilter>) parameters
					.getSerializable(OfferFilterService.PARAM_OFFERS_FILTER);

			//
			populaSpinners(filters);
			pbFilter.setVisibility(View.GONE);
		}
	}

	public class OfferFilterSearchResponseReceiver extends BroadcastReceiver {
		public static final String ACTION_RESP_OFFER_FILTER_SEARCH = "com.mamlambo.intent.action.MESSAGE_PROCESSED_OFFER_FILTER_SEARCH";

		@Override
		public void onReceive(Context context, Intent intent) {

			Bundle parameters = intent.getExtras();

			List<Offer> offers = (List<Offer>) parameters
					.getSerializable(OfferFilterSearchService.PARAM_OUT_OFFERS);

			SharedPreferences.Editor editor = loggedUser.edit();

			String offersString = SessionControl.encodeSessionOffers(offers);
			editor.putString(SessionControl.FILTERED_OFFERS, offersString);
			editor.commit();

			pbFilter.setVisibility(View.GONE);
			viewBack.setVisibility(View.GONE);
			Intent listOffersIntent = new Intent(OfferFilterActivity.this,
					FilteredOfferActivity.class);
			startActivity(listOffersIntent);

		}
	}
}
