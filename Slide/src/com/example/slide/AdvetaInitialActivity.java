package com.example.slide;

import java.util.List;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;

import com.accential.trueone.adapter.FinalOfferAdapter;
import com.accential.trueone.bean.Offer;
import com.accential.trueone.bean.User;
import com.accential.trueone.bo.OfferBO;
import com.accential.trueone.service.OffersPagesService;
import com.accential.trueone.utils.InfiniteScrollListener;
import com.accential.trueone.utils.OrientacaoUtils;
import com.accential.trueone.utils.SessionControl;

public class AdvetaInitialActivity extends Activity {

	private SharedPreferences loggedUser;

	private List<Offer> offers;

	// private TextView tituloAdventa;

	private GridView grid;
	private ImageView ivLogin;
	private FinalOfferAdapter adapter;
	private List<Offer> mys;
	private int i;
	private OffersPageResponseReceiver receiver;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Remove title bar
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		// Remove notification bar
		// this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		// WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.x_vitrine_inicial);
		i = 2;
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

		ivLogin = (ImageView) findViewById(R.id.imageView2);
		grid = (GridView) findViewById(R.id.gridOfferInitial);
		// tituloAdventa = (TextView) findViewById(R.id.textView1);

		loggedUser = this.getSharedPreferences(SessionControl.USER_LOGGED, 0);

		if (loggedUser.getAll().isEmpty()) {

			// Typeface font = Typeface.createFromAsset(getAssets(),
			// "Aaargh.ttf");
			// tituloAdventa.setTypeface(font);

			offers = OfferBO.listValidOffers();

			adapter = new FinalOfferAdapter(this, offers);
			grid.setAdapter(adapter);

			// clique na imagem do login
			ivLogin.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// levamos o usuario para a tela de login ou criaçao
					// Intent intent = new Intent(AdvetaInitialActivity.this,
					// FBActivity.class);
					// startActivity(intent);
					SessionControl.logoof(v.getContext(), loggedUser);
				}
			});

			grid.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int location, long arg3) {

					Offer offer = offers.get(location);

					SharedPreferences loggedUser = view
							.getContext()
							.getSharedPreferences(SessionControl.USER_LOGGED, 0);
					SharedPreferences.Editor editor = loggedUser.edit();

					String stringUser = SessionControl
							.encodeSessionOffer(offer);

					editor.putString(SessionControl.OFFER_DETAIL, stringUser);
					editor.commit();

					Intent intent = new Intent(view.getContext(),
							OffersDetailActivity.class);
					startActivity(intent);
				}
			});
		} else {
			Log.e("", "ESSE USUARIO JA EXISTE----MAS DEVEMOS VERIFICAR");

			User user = SessionControl.decodeSessionUser(loggedUser.getString(
					"user", null));
			if (user == null) {

				Log.e("", "USUARIO NAO ESTA LOGADO!!!");

				SharedPreferences.Editor editor = loggedUser.edit();
				editor.remove(SessionControl.OFFER_DETAILS_COMPANY);
				editor.remove("user");
				editor.commit();

				// Typeface font = Typeface.createFromAsset(getAssets(),
				// "Aaargh.ttf");
				// tituloAdventa.setTypeface(font);

				offers = OfferBO.listValidOffers();

				adapter = new FinalOfferAdapter(this, offers);
				grid.setAdapter(adapter);

				// // clique na imagem do login
				ivLogin.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						// levamos o usuario para a tela de login ou criaçao
						// Intent intent = new
						// Intent(AdvetaInitialActivity.this,
						// FBActivity.class);
						// startActivity(intent);
						SessionControl.logoof(v.getContext(), loggedUser);
					}
				});

				grid.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int location, long arg3) {

						Offer offer = offers.get(location);

						SharedPreferences loggedUser = view.getContext()
								.getSharedPreferences(
										SessionControl.USER_LOGGED, 0);
						SharedPreferences.Editor editor = loggedUser.edit();

						String stringUser = SessionControl
								.encodeSessionOffer(offer);

						editor.putString(SessionControl.OFFER_DETAIL,
								stringUser);
						editor.commit();

						Intent intent = new Intent(view.getContext(),
								OffersDetailActivity.class);
						startActivity(intent);
					}
				});

			} else {
				Log.e("",
						"USUARIO TA CADASTRADO!!! VAMOS PARA A PROXIMA TELA!!!");
				Intent intent = new Intent(AdvetaInitialActivity.this,
						FBActivity.class);
				startActivity(intent);
			}

		}

		grid.setOnScrollListener(new InfiniteScrollListener(6) {
			@Override
			public void loadMore(int page, int totalItemsCount) {

				Log.e("", "chamando service");
				// mys = OfferBO.listValidOffersForPage(i);
				// chamando service
				Intent commentsIntent = new Intent(AdvetaInitialActivity.this,
						OffersPagesService.class);
				commentsIntent.putExtra(OffersPagesService.PARAM_IN_PAGE, i);
				AdvetaInitialActivity.this.startService(commentsIntent);

				// preparando o receiver para o retorno do service
				IntentFilter filter = new IntentFilter(
						OffersPageResponseReceiver.ACTION_RESP_OFFERS_PAGE_RECEIVER);
				filter.addCategory(Intent.CATEGORY_DEFAULT);
				receiver = new OffersPageResponseReceiver();
				AdvetaInitialActivity.this.registerReceiver(receiver, filter);
			}
		});
	}

	public class OffersPageResponseReceiver extends BroadcastReceiver {
		public static final String ACTION_RESP_OFFERS_PAGE_RECEIVER = "com.mamlambo.intent.action.MESSAGE_PROCESSED_OFFERS_PAGE_RECEIVER";

		@SuppressWarnings("unchecked")
		@Override
		public void onReceive(Context context, Intent intent) {
			Bundle parameters = intent.getExtras();

			mys = (List<Offer>) parameters
					.getSerializable(OffersPagesService.PARAM_OUT_OFFERS);
			offers.addAll(mys);
			adapter.notifyDataSetChanged();
			i++;
		}
	}
}
