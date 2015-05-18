package com.example.slide;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.accential.trueone.adapter.FinalOfferAdapter;
import com.accential.trueone.adapter.OfferMosaicAdapter;
import com.accential.trueone.bean.Offer;
import com.accential.trueone.bean.User;
import com.accential.trueone.bo.OfferBO;
import com.accential.trueone.service.CompsOfferService;
import com.accential.trueone.service.OffersMyProfileService;
import com.accential.trueone.service.OffersWishlistService;
import com.accential.trueone.utils.OrientacaoUtils;
import com.accential.trueone.utils.SessionControl;

@SuppressWarnings("all")
/**
 * Classe de uso generico. Pode ser usada pra todo tipo de listagem de OFERTAS
 * !!!!
 * 
 * @author Matheus Odilon - accentialbrasil
 * @version 1.0
 * 
 */
public class OffersListActivity extends Activity {

	private SharedPreferences loggedUser;
	private User user;

	private GridView gridOffersList;
	private TextView myProfileWarning;
	private TextView offersWishWarning;
	private ProgressBar pbGeneric;

	private List<Offer> myOffers;
	private OfferMosaicAdapter mosaicAdapter;
	private FinalOfferAdapter adapter;
	private OffersMyProfileResponseReceiver myProfileReceiver;
	private OffersWishResponseReceiver offersWishReceiver;
	private CompsOfferResponseReceiver compOfferReceiver;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.x_activity_generic_offers_list);

		// verificamos se aplicacao esta sendo usada em um smartphone ou tablet
		String typeUser = getResources().getString(R.string.isTablet);
		if (typeUser.equals("false")) {
			Log.e("tstando list de busca",
					"Estamos abrindo o app de um smartphe");
			OrientacaoUtils.setOrientationVertical(this);
		} else {
			Log.e("tstando lista de busca",
					"Estamos abrindo o app de um tablet");
			OrientacaoUtils.setOrientationHorizontal(this);
		}

		gridOffersList = (GridView) findViewById(R.id.offersGridView);
		myProfileWarning = (TextView) findViewById(R.id.tvOffersMyProfileWarning);
		offersWishWarning = (TextView) findViewById(R.id.tvOffersWishWarning);
		pbGeneric = (ProgressBar) findViewById(R.id.pbGeneric);

		ImageView userImg = (ImageView) findViewById(R.id.imageView2);

		// clique na imagem do usuario
		userImg.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(OffersListActivity.this,
						UserActivity.class);
				startActivity(intent);
			}
		});

		loggedUser = this.getSharedPreferences(SessionControl.USER_LOGGED, 0);
		user = SessionControl.decodeSessionUser(loggedUser.getString("user",
				null));

		myOffers = new ArrayList<Offer>();

		Intent intent = getIntent();
		String type = intent.getStringExtra("listType");

		// validando tipo de listagem
		// ofertas para MEU perfil
		if (type.equals("offersMyProfile")) {

			// chamando service
			Intent commentsIntent = new Intent(this,
					OffersMyProfileService.class);
			commentsIntent.putExtra(OffersMyProfileService.PARAM_IN_USER_ID,
					user.getId());
			this.startService(commentsIntent);

			// preparando o receiver para o retorno do service
			IntentFilter filter = new IntentFilter(
					OffersMyProfileResponseReceiver.ACTION_RESP_OFFERS_MY_PROFILE);
			filter.addCategory(Intent.CATEGORY_DEFAULT);
			myProfileReceiver = new OffersMyProfileResponseReceiver();
			this.registerReceiver(myProfileReceiver, filter);

		} else
		// ofertas direcionada a desejo
		if (type.equals("offersWish")) {

			// chamando service
			int wishId = intent.getIntExtra("wishId", 0);
			Intent commentsIntent = new Intent(this,
					OffersWishlistService.class);
			commentsIntent.putExtra(OffersWishlistService.PARAM_IN_WISH_ID,
					wishId);
			this.startService(commentsIntent);

			// preparando o receiver para o retorno do service
			IntentFilter filter = new IntentFilter(
					OffersWishResponseReceiver.ACTION_RESP_OFFERS_WISH);
			filter.addCategory(Intent.CATEGORY_DEFAULT);
			offersWishReceiver = new OffersWishResponseReceiver();
			this.registerReceiver(offersWishReceiver, filter);

		} else if (type.equals("offersByComp")) {

			int compId = intent.getIntExtra("compId", 0);
			myOffers = OfferBO.listOffersByCompany(compId);

			if (!myOffers.isEmpty()) {
				mosaicAdapter = new OfferMosaicAdapter(myOffers,
						OffersListActivity.this);
				gridOffersList.setAdapter(mosaicAdapter);
				pbGeneric.setVisibility(View.GONE);

			} else {
				gridOffersList.setVisibility(View.GONE);
				pbGeneric.setVisibility(View.GONE);
				offersWishWarning.setVisibility(View.VISIBLE);
			}
			// // chamando service
			// int compId = intent.getIntExtra("compId", 0);
			// Log.e("", "COMPANY ID: " + String.valueOf(compId));
			// Intent commentsIntent = new Intent(this,
			// CompsOfferService.class);
			// commentsIntent.putExtra(CompsOfferService.PARAM_IN_COMP_ID,
			// compId);
			// this.startService(commentsIntent);
			//
			// // preparando o receiver para o retorno do service
			// IntentFilter filter = new IntentFilter(
			// CompsOfferResponseReceiver.ACTION_RESP_COMPANIES_OFFER);
			// filter.addCategory(Intent.CATEGORY_DEFAULT);
			// compOfferReceiver = new CompsOfferResponseReceiver();
			// this.registerReceiver(offersWishReceiver, filter);

		}

		// toque em item do grid
		gridOffersList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long arg3) {

				Offer offer = myOffers.get(position);
				Toast.makeText(view.getContext(), offer.getTitle(),
						Toast.LENGTH_LONG).show();

				SharedPreferences loggedUser = view.getContext()
						.getSharedPreferences(SessionControl.USER_LOGGED, 0);
				SharedPreferences.Editor editor = loggedUser.edit();

				String stringUser = SessionControl.encodeSessionOffer(offer);

				editor.putString(SessionControl.OFFER_DETAIL, stringUser);
				editor.commit();

				Intent intent = new Intent(view.getContext(),
						OffersDetailActivity.class);
				startActivity(intent);
			}
		});

		ImageView imgMenuCompras = (ImageView) findViewById(R.id.imageView3footer);
		ImageView imgMenuSign = (ImageView) findViewById(R.id.imageView4);
		ImageView imgMenuWish = (ImageView) findViewById(R.id.imageView5);
		ImageView imgMenuHome = (ImageView) findViewById(R.id.imageView1footer);
		ImageView imgMenuOffer = (ImageView) findViewById(R.id.imageView2footer);

		imgMenuOffer.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(OffersListActivity.this,
						OffersListActivity.class);
				intent.putExtra("listType", "offersMyProfile");
				startActivity(intent);
			}
		});

		// mudando icone da imagem clicada do menu
		//imgMenuWish.setImageResource(R.drawable.adventa_icon_wish_blue);
		//imgMenuOffer.setImageResource(R.drawable.adventa_icon_offer_clicked);

		// clique em compras
		imgMenuCompras.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(OffersListActivity.this,
						ComprasActivity.class);
				startActivity(intent);
			}
		});

		// clique em assinaturas
		imgMenuSign.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(OffersListActivity.this,
						SignaturesActivity.class);
				startActivity(intent);
			}
		});

		// clique em wishlist
		imgMenuWish.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(OffersListActivity.this,
						WishHomeActivity.class);
				startActivity(intent);
			}
		});

		// clique em home
		imgMenuHome.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(OffersListActivity.this,
						AdventaActivity.class);
				startActivity(intent);
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);

		// Carrega o arquivo de menu.
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);

		return true;
	}

	/**
	 * Resposta ao Service que recupera as ofertas para o MEU perfil
	 * 
	 * @author Matheus Odilon - accentialbrasil
	 * 
	 */
	public class OffersMyProfileResponseReceiver extends BroadcastReceiver {
		public static final String ACTION_RESP_OFFERS_MY_PROFILE = "com.mamlambo.intent.action.MESSAGE_PROCESSED_OFFERS_MY_PROFILE";

		@Override
		public void onReceive(Context context, Intent intent) {

			Bundle parameters = intent.getExtras();
			myOffers = ((List<Offer>) parameters
					.getSerializable(OffersMyProfileService.PARAM_OUT_OFFERS));

			if (!myOffers.isEmpty()) {
				// mosaicAdapter = new OfferMosaicAdapter(myOffers,
				// OffersListActivity.this);
				adapter = new FinalOfferAdapter(OffersListActivity.this,
						myOffers);
				gridOffersList.setAdapter(adapter);
				pbGeneric.setVisibility(View.GONE);

			} else {
				gridOffersList.setVisibility(View.GONE);
				pbGeneric.setVisibility(View.GONE);
				myProfileWarning.setVisibility(View.VISIBLE);
			}

		}
	}

	/**
	 * Resposta ao Service que recupe oferta de algum desejo especifico
	 * 
	 * @author Matheus Odilon - accentialbrasil
	 * 
	 */
	public class OffersWishResponseReceiver extends BroadcastReceiver {
		public static final String ACTION_RESP_OFFERS_WISH = "com.mamlambo.intent.action.MESSAGE_PROCESSED_OFFERS_WISH";

		@Override
		public void onReceive(Context context, Intent intent) {

			Bundle parameters = intent.getExtras();
			myOffers = ((List<Offer>) parameters
					.getSerializable(OffersWishlistService.PARAM_OUT_OFFERS));

			if (!myOffers.isEmpty()) {
				// mosaicAdapter = new OfferMosaicAdapter(myOffers,
				// OffersListActivity.this);
				adapter = new FinalOfferAdapter(OffersListActivity.this,
						myOffers);
				gridOffersList.setAdapter(mosaicAdapter);
				pbGeneric.setVisibility(View.GONE);

			} else {
				gridOffersList.setVisibility(View.GONE);
				pbGeneric.setVisibility(View.GONE);
				offersWishWarning.setVisibility(View.VISIBLE);
			}

		}
	}

	/**
	 * Resposta ao Service que recupe OFERTAS company especifica
	 * 
	 * @author Matheus Odilon - accentialbrasil
	 * 
	 */
	public class CompsOfferResponseReceiver extends BroadcastReceiver {
		public static final String ACTION_RESP_COMPANIES_OFFER = "com.mamlambo.intent.action.MESSAGE_PROCESSED_COMPANIES_OFFER";

		@Override
		public void onReceive(Context context, Intent intent) {

			Log.e("", "RESPONSE - 1");

			Bundle parameters = intent.getExtras();
			myOffers = ((List<Offer>) parameters
					.getSerializable(CompsOfferService.PARAM_OUT_OFFERS));

			Log.e("", "RESPONSE - 2");

			if (!myOffers.isEmpty()) {
				// mosaicAdapter = new OfferMosaicAdapter(myOffers,
				// OffersListActivity.this);
				adapter = new FinalOfferAdapter(OffersListActivity.this,
						myOffers);
				gridOffersList.setAdapter(adapter);
				pbGeneric.setVisibility(View.GONE);

			} else {
				gridOffersList.setVisibility(View.GONE);
				pbGeneric.setVisibility(View.GONE);
				offersWishWarning.setVisibility(View.VISIBLE);
			}

			Log.e("", "RESPONSE - 3");

		}
	}
}