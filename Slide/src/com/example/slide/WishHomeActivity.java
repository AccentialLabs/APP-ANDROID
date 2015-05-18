package com.example.slide;

import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.accential.trueone.adapter.WishAdapter;
import com.accential.trueone.bean.User;
import com.accential.trueone.bean.Wishlist;
import com.accential.trueone.bo.WishlistBO;
import com.accential.trueone.service.WishHomeService;
import com.accential.trueone.utils.OrientacaoUtils;
import com.accential.trueone.utils.SessionControl;

@SuppressWarnings("all")
/**
 *  Rechamar o service: Mostramos a lista precarregada, mas ao mesmo tempo
 * continuamos a atualizar os dados, assim evitamos que os dados sejam "velhos"
 * ou desatualizados
 *  
 * @author Matheus Odilon - accentialbrasil
 * 
 */
public class WishHomeActivity extends Activity {
	private ListView lvWish;
	private SharedPreferences loggedUser;
	private User user;
	private List<Wishlist> wishlists;
	private Button btnCreate;
	private WishAdapter adapter;
	private WishlistHomeResponseReceiver receiver;
	private ProgressBar pbWish;
	private ProgressBar pbLoadList;
	private TextView tvTitle;
	public Map desejos;
	private Map qtdOffers;
	private TextView tvWishLoadList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.x_activity_wishlist);

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

		// recuperando usuario logado na "sessao"
		loggedUser = getSharedPreferences(SessionControl.USER_LOGGED, 0);
		user = SessionControl.decodeSessionUser(loggedUser.getString("user",
				null));

		lvWish = (ListView) findViewById(R.id.lvWishlist);
		btnCreate = (Button) findViewById(R.id.btnWishlistInclude);
		pbWish = (ProgressBar) findViewById(R.id.pbWishlist);
		pbLoadList = (ProgressBar) findViewById(R.id.pbWishLoadList);
		tvTitle = (TextView) findViewById(R.id.tvTitleWish);
		tvWishLoadList = (TextView) findViewById(R.id.tvWishLoadList);

		ImageView userImg = (ImageView) findViewById(R.id.imageView2);

		// clique na imagem do usuario
		userImg.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(WishHomeActivity.this,
						UserActivity.class);
				startActivity(intent);
			}
		});

		// menu
		ImageView imgMenuCompras = (ImageView) findViewById(R.id.imageView3footer);
		ImageView imgMenuSign = (ImageView) findViewById(R.id.imageView4);
		ImageView imgMenuWish = (ImageView) findViewById(R.id.imageView5);
		ImageView imgMenuHome = (ImageView) findViewById(R.id.imageView1footer);
		ImageView imgMenuOffer = (ImageView) findViewById(R.id.imageView2footer);

		imgMenuOffer.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(WishHomeActivity.this,
						OffersListActivity.class);
				intent.putExtra("listType", "offersMyProfile");
				startActivity(intent);
			}
		});

		// mudando icone da imagem clicada do menu
		imgMenuWish.setImageResource(R.drawable.adventa_icon_wish_blue);

		// clique em compras
		imgMenuCompras.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(WishHomeActivity.this,
						ComprasActivity.class);
				startActivity(intent);
			}
		});

		// clique em assinaturas
		imgMenuSign.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(WishHomeActivity.this,
						SignaturesActivity.class);
				startActivity(intent);
			}
		});

		// clique em wishlist
		imgMenuWish.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(WishHomeActivity.this,
						WishHomeActivity.class);
				startActivity(intent);
			}
		});

		// clique em home
		imgMenuHome.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(WishHomeActivity.this,
						AdventaActivity.class);
				startActivity(intent);
			}
		});

		Typeface font = Typeface.createFromAsset(getAssets(),
				"helvetica-35-thin-1361522141.ttf");
		tvTitle.setTypeface(font);
		btnCreate.setTag(font);

		// recebendo do shared preferences caso lista ja tenha sido carregada
		wishlists = SessionControl.decodeSessionWishlistUser(loggedUser
				.getString(SessionControl.WISHLIST_USERS, null));

		qtdOffers = SessionControl.decodeSessionMap(loggedUser.getString(
				SessionControl.MAP_OFFERS_WISH, null));

		if (wishlists == null) {

			// chamando service
			Intent commentsIntent = new Intent(this, WishHomeService.class);
			commentsIntent.putExtra(WishHomeService.PARAM_IN_USER_ID,
					user.getId());
			this.startService(commentsIntent);

			// preparando o receiver para o retorno do service
			IntentFilter filter = new IntentFilter(
					WishlistHomeResponseReceiver.ACTION_RESP_WISHLIST_HOME);
			filter.addCategory(Intent.CATEGORY_DEFAULT);
			receiver = new WishlistHomeResponseReceiver();
			this.registerReceiver(receiver, filter);

		} else {
			adapter = new WishAdapter(wishlists, WishHomeActivity.this,
					qtdOffers);
			lvWish.setAdapter(adapter);
			pbWish.setVisibility(View.GONE);

			tvWishLoadList.setVisibility(View.VISIBLE);
			pbLoadList.setVisibility(View.VISIBLE);
			Log.e("", "RECHAMANDO service");
			// "REchamando Service"
			// chamando service
			Intent commentsIntent = new Intent(this, WishHomeService.class);
			commentsIntent.putExtra(WishHomeService.PARAM_IN_USER_ID,
					user.getId());
			this.startService(commentsIntent);

			// preparando o receiver para o retorno do service
			IntentFilter filter = new IntentFilter(
					WishlistHomeResponseReceiver.ACTION_RESP_WISHLIST_HOME);
			filter.addCategory(Intent.CATEGORY_DEFAULT);
			receiver = new WishlistHomeResponseReceiver();
			this.registerReceiver(receiver, filter);

		}

		// cria desejo
		btnCreate.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(WishHomeActivity.this,
						WishlistActivity.class);
				startActivity(intent);
			}
		});

		// clique em item da lista
		lvWish.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					final int location, long arg3) {
				LayoutInflater layoutInflater = LayoutInflater
						.from(WishHomeActivity.this);

				View promptView = layoutInflater.inflate(
						R.layout.x_pop_wishlist, null);

				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
						WishHomeActivity.this);

				alertDialogBuilder.setView(promptView);

				final Button btnShare = (Button) promptView
						.findViewById(R.id.btnPopWishShare);
				final Button btnDelete = (Button) promptView
						.findViewById(R.id.btnPopWishDelete);
				final Button btnVerOffers = (Button) promptView
						.findViewById(R.id.btnPopWishVerOffer);

				// create an alert dialog
				final AlertDialog alertD = alertDialogBuilder.create();

				// clique em deletar
				btnDelete.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						WishlistBO
								.inactiveWish(wishlists.get(location).getId());

						wishlists.remove(location);
						adapter = new WishAdapter(wishlists,
								WishHomeActivity.this, qtdOffers);
						lvWish.setAdapter(adapter);

						Toast.makeText(WishHomeActivity.this,
								"Desejo excluido", Toast.LENGTH_SHORT).show();
						alertD.dismiss();
					}
				});

				// clique em compartilhar
				btnShare.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View view) {

						String shareBody = "Estou procurando "
								+ wishlists.get(location).getName() + ": "
								+ wishlists.get(location).getDescription()
								+ " Poderia me ajudar a encontrar?";

						Intent sharingIntent = new Intent(
								android.content.Intent.ACTION_SEND);
						sharingIntent.setType("text/plain");
						sharingIntent.putExtra(
								android.content.Intent.EXTRA_SUBJECT,
								"Meu Desejo");
						sharingIntent.putExtra(
								android.content.Intent.EXTRA_TEXT, shareBody);
						startActivity(Intent.createChooser(sharingIntent,
								"Compartilhar via..."));
						alertD.dismiss();
					}
				});

				// clique em ver ofertas
				btnVerOffers.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View view) {
						Intent intent = new Intent(WishHomeActivity.this,
								OffersListActivity.class);
						intent.putExtra("listType", "offersWish");
						intent.putExtra("wishId", wishlists.get(location)
								.getId());
						startActivity(intent);
					}
				});
				alertD.show();
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public class WishlistHomeResponseReceiver extends BroadcastReceiver {
		public static final String ACTION_RESP_WISHLIST_HOME = "com.mamlambo.intent.action.MESSAGE_PROCESSED_WISHLIST_HOME";

		@Override
		public void onReceive(Context context, Intent intent) {

			// recurando lista de assisnaturas capturada no service
			Bundle parameters = intent.getExtras();
			wishlists = (List<Wishlist>) parameters
					.getSerializable(WishHomeService.PARAM_OUT_WISHLIST);

			qtdOffers = (Map) parameters
					.getSerializable(WishHomeService.PARAM_OUT_OFFERS_QTD);

			// salvando lista de assinaturas no SharedPreference
			SharedPreferences.Editor editor = loggedUser.edit();

			String wishString = SessionControl
					.encodeSessionWishlistUser(wishlists);
			editor.putString(SessionControl.WISHLIST_USERS, wishString);

			String mapString = SessionControl.encodeSessionMap(qtdOffers);
			editor.putString(SessionControl.MAP_OFFERS_WISH, mapString);
			editor.commit();

			adapter = new WishAdapter(wishlists, WishHomeActivity.this,
					qtdOffers);
			lvWish.setAdapter(adapter);
			pbWish.setVisibility(View.GONE);
			pbLoadList.setVisibility(View.GONE);
			tvWishLoadList.setVisibility(View.GONE);
		}
	}
}
