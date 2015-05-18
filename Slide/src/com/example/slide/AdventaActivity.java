package com.example.slide;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.accential.trueone.adapter.FinalOfferAdapter;
import com.accential.trueone.adapter.OfferFullAdapter;
import com.accential.trueone.adapter.OfferListAdapter;
import com.accential.trueone.adapter.OfferMosaicAdapter;
import com.accential.trueone.adapter.OfferStyleListAdapter;
import com.accential.trueone.bean.Offer;
import com.accential.trueone.bean.User;
import com.accential.trueone.service.AdventaPrincipalService;
import com.accential.trueone.service.BackgroundNotificationService;
import com.accential.trueone.service.OffersPagesPrincipalService;
import com.accential.trueone.utils.InfiniteScrollListener;
import com.accential.trueone.utils.OrientacaoUtils;
import com.accential.trueone.utils.SessionControl;
import com.parse.Parse;
import com.parse.PushService;

@SuppressWarnings("all")
public class AdventaActivity extends Activity {

	private ProgressBar pbAtualiza;
	private TextView tvAtualiza;

	private ProgressBar pbPrincipal;
	private OfferMosaicAdapter mosaicAdapter;
	// private GridView offersGridView;
	private static List<Offer> myOffers;
	// private TextView txUserName;
	private SharedPreferences loggedUser;
	private User user;
	private boolean conection;
	private AdventaResponseReceiver receiver;
	private OfferListAdapter listAdapter;
	private ImageView userPhoto;
	private ImageView lupa;
	private EditText editSearch;
	private boolean firstAccess;
	public static boolean isService = false;

	public GridView grid;
	public FinalOfferAdapter adapter;
	private OfferFullAdapter offerFullAdapter;

	// menu
	private ImageView imgBtnHome;
	private ImageView imgBtnCompras;
	private ImageView imgBtnWish;
	private ImageView imgBtnSign;
	private ImageView imgBtnOffers;
	private ImageView imgMenuChangeList;
	private List<Offer> mySearchOffers;
	private List<Offer> mys;

	// ofertas em lista
	OfferStyleListAdapter styleListAdapter;
	private String listOrientation;
	private int i;
	private OffersPagePrincipalResponseReceiver receiverPrincipal;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.first_frag);

		TelephonyManager manager = (TelephonyManager) this
				.getSystemService(Context.TELEPHONY_SERVICE);
		if (manager.getPhoneType() == TelephonyManager.PHONE_TYPE_NONE) {
			Log.i("TESTE", "É UM TABLET");
			Log.i("TESTE",
					"PHONE TYPE= " + String.valueOf(manager.getPhoneType()));
		} else {
			Log.i("TESTE", "É UM SMARTPHONE");
			Log.i("TESTE",
					"PHONE TYPE= " + String.valueOf(manager.getPhoneType()));
		}

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

		i = 2;
		Log.e("", "Iniciando garbage collector...");
		try {
			System.gc();
			Log.e("", "Iniciou o Garbage Collector....");
		} catch (Exception e) {
			Log.e("Error AdventaActivity",
					"Erro ao iniciar garbage colector - linha 61");
			e.printStackTrace();
		}

		editSearch = (EditText) findViewById(R.id.editText1);
		lupa = (ImageView) findViewById(R.id.btnSearchOffer);
		pbAtualiza = (ProgressBar) findViewById(R.id.pbPrincipalAtualiza);
		tvAtualiza = (TextView) findViewById(R.id.tvPrincipalAtualiza);
		pbPrincipal = (ProgressBar) findViewById(R.id.pbAdventaPrincipal);
		// TextView tv = (TextView) findViewById(R.id.tvFragFirst);
		// offersGridView = (GridView) findViewById(R.id.offersGridView);
		userPhoto = (ImageView) findViewById(R.id.imageView2);
		grid = (GridView) findViewById(R.id.gridOffersAdventa);

		Button btnRefresh = (Button) findViewById(R.id.btnRefresh);
		Button btnFiltrar = (Button) findViewById(R.id.btnFiltrar);

		imgBtnHome = (ImageView) findViewById(R.id.imageView1footer);
		imgBtnCompras = (ImageView) findViewById(R.id.imageView3footer);
		imgBtnSign = (ImageView) findViewById(R.id.imageView4);
		imgBtnWish = (ImageView) findViewById(R.id.imageView5);
		imgBtnOffers = (ImageView) findViewById(R.id.imageView2footer);
		imgMenuChangeList = (ImageView) findViewById(R.id.imageView3);

		// trocando imagem do menu
		imgBtnHome.setImageResource(R.drawable.adventa_icon_home_blue);

		loggedUser = this.getSharedPreferences(SessionControl.USER_LOGGED, 0);
		user = SessionControl.decodeSessionUser(loggedUser.getString("user",
				null));

		myOffers = SessionControl.decodeSessionOffers(loggedUser.getString(
				SessionControl.OFFERS, null));

		listOrientation = loggedUser.getString(SessionControl.LIST_ORIENTATION,
				null);

		if (listOrientation == null) {
			listOrientation = "stylePrincipal";
		}

		// if (myOffers == null) {

		pbAtualiza.setVisibility(View.GONE);
		tvAtualiza.setVisibility(View.GONE);

		// start service
		Intent commentsIntent = new Intent(this, AdventaPrincipalService.class);
		this.startService(commentsIntent);

		IntentFilter filter = new IntentFilter(
				AdventaResponseReceiver.ACTION_RESP_ADVENTA_PRINCIPAL);
		filter.addCategory(Intent.CATEGORY_DEFAULT);
		receiver = new AdventaResponseReceiver();
		this.registerReceiver(receiver, filter);
		//
		// } else {
		//
		// pbPrincipal.setVisibility(View.GONE);
		//
		// if (listOrientation.equals("stylePrincipal")) {
		// adapter = new FinalOfferAdapter(AdventaActivity.this, myOffers);
		// grid.setNumColumns(2);
		// grid.setAdapter(adapter);
		// imgMenuChangeList
		// .setImageResource(R.drawable.adventa_icon_inicio_off);
		// } else if (listOrientation.equals("styleList")) {
		// styleListAdapter = new OfferStyleListAdapter(
		// AdventaActivity.this, myOffers);
		// grid.setNumColumns(1);
		// grid.setAdapter(styleListAdapter);
		// imgMenuChangeList
		// .setImageResource(R.drawable.adventa_icon_inicio_blue);
		// } else if (listOrientation.equals("styleFull")) {
		// offerFullAdapter = new OfferFullAdapter(AdventaActivity.this,
		// myOffers);
		// grid.setNumColumns(1);
		// grid.setAdapter(offerFullAdapter);
		//
		// imgMenuChangeList
		// .setImageResource(R.drawable.adventa_icon_inicio_on);
		// } else {
		// adapter = new FinalOfferAdapter(AdventaActivity.this, myOffers);
		// grid.setNumColumns(2);
		// grid.setAdapter(adapter);
		// imgMenuChangeList
		// .setImageResource(R.drawable.adventa_icon_inicio_off);
		// }
		//
		// // Intent commentsIntent = new Intent(this,
		// // AdventaPrincipalService.class);
		// // this.startService(commentsIntent);
		// //
		// // IntentFilter filter = new IntentFilter(
		// // AdventaResponseReceiver.ACTION_RESP_ADVENTA_PRINCIPAL);
		// // filter.addCategory(Intent.CATEGORY_DEFAULT);
		// // receiver = new AdventaResponseReceiver();
		// // this.registerReceiver(receiver, filter);
		//
		// }

		// apaga company salva na vizuali.. do detalhe de alguma oferta para que
		// a lista seja carregada novamente
		SharedPreferences.Editor editor = loggedUser.edit();
		editor.remove(SessionControl.OFFER_DETAILS_COMPANY);
		editor.commit();

		// verifica conexao
		conection = SessionControl.checkInternetConnection(this);
		if (conection == false) {
			btnRefresh.setVisibility(View.VISIBLE);
		}

		// clique na lupa
		lupa.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				editSearch.setVisibility(View.VISIBLE);
			}
		});

		// inserindo texto no edit de busca
		// lógica da busca e mostrar lista
		editSearch.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {

				if (!editSearch.getText().toString().equals("")) {
					String texto = editSearch.getText().toString();
					mySearchOffers = new ArrayList<Offer>();

					Log.e("",
							"Tamanho das ofertas: "
									+ String.valueOf(myOffers.size()));

					for (Offer offer : myOffers) {
						if (offer.getTitle().toLowerCase()
								.contains(texto.toLowerCase())) {
							mySearchOffers.add(offer);
						}
					}

					// OfferFullAdapter myAdapter = new OfferFullAdapter(
					// AdventaActivity.this, mySearchOffers);
					// grid.setAdapter(myAdapter);

					// logica para mostrar itens na lista
					if (listOrientation.equals("stylePrincipal")) {
						adapter = new FinalOfferAdapter(AdventaActivity.this,
								mySearchOffers);
						grid.setNumColumns(2);
						grid.setAdapter(adapter);
					} else if (listOrientation.equals("styleList")) {
						styleListAdapter = new OfferStyleListAdapter(
								AdventaActivity.this, mySearchOffers);
						grid.setNumColumns(1);
						grid.setAdapter(styleListAdapter);
					} else if (listOrientation.equals("styleFull")) {
						offerFullAdapter = new OfferFullAdapter(
								AdventaActivity.this, mySearchOffers);
						grid.setNumColumns(1);
						grid.setAdapter(offerFullAdapter);
					} else {
						adapter = new FinalOfferAdapter(AdventaActivity.this,
								mySearchOffers);
						grid.setNumColumns(2);
						grid.setAdapter(adapter);
					}

					grid.setOnItemClickListener(new OnItemClickListener() {
						@Override
						public void onItemClick(AdapterView<?> parent,
								View view, int location, long arg3) {
							// TODO Auto-generated method stub
							Offer offer = mySearchOffers.get(location);

							if (offer.getValue() == 0) {
								Log.e("Clicou na oferta em branco",
										"Ops.. Clique na oferta em branco, aqui não vai, sem Link!!!");

							} else {
								SharedPreferences loggedUser = view
										.getContext().getSharedPreferences(
												SessionControl.USER_LOGGED, 0);
								SharedPreferences.Editor editor = loggedUser
										.edit();

								String stringUser = SessionControl
										.encodeSessionOffer(offer);

								editor.putString(SessionControl.OFFER_DETAIL,
										stringUser);
								editor.commit();

								Intent intent = new Intent(view.getContext(),
										OffersDetailActivity.class);
								startActivity(intent);
							}
						}
					});
				} else {
					OfferFullAdapter myAdap = new OfferFullAdapter(
							AdventaActivity.this, myOffers);
					grid.setAdapter(myAdap);
				}

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
			}
		});

		// userPhoto.setImageUrl(user.getPhoto());

		// clique longo em item da lista
		grid.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int location, long arg3) {

				final Offer offer = myOffers.get(location);

				LayoutInflater layoutInflater = LayoutInflater
						.from(AdventaActivity.this);

				View promptView = layoutInflater.inflate(R.layout.x_pop_offers,
						null);

				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
						AdventaActivity.this);

				alertDialogBuilder.setView(promptView);

				Button btnVoltar = (Button) promptView
						.findViewById(R.id.btnVoltar);
				Button btnShare = (Button) promptView
						.findViewById(R.id.btnShare);

				// create an alert dialog
				final AlertDialog alertD = alertDialogBuilder.create();
				btnVoltar.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View view) {
						alertD.cancel();
					}
				});

				// clique em compartilhar
				btnShare.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						String shareBody = "Aproveite essa oferta exclusiva que está disponível somente no ADVENTA: "
								+ offer.getTitle();

						Intent sharingIntent = new Intent(
								android.content.Intent.ACTION_SEND);
						sharingIntent.setType("text/plain");
						sharingIntent.putExtra(
								android.content.Intent.EXTRA_SUBJECT,
								"Oferta ADVENTA: " + offer.getTitle());
						sharingIntent.putExtra(
								android.content.Intent.EXTRA_TEXT, shareBody);
						startActivity(Intent.createChooser(sharingIntent,
								"Compartilhar via..."));
						alertD.dismiss();
					}
				});

				alertD.show();
				return false;
			}
		});

		grid.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int location, long arg3) {
				// TODO Auto-generated method stub
				Offer offer = myOffers.get(location);

				if (offer.getValue() == 0) {
					Log.e("Clicou na oferta em branco",
							"Ops.. Clique na oferta em branco, aqui não vai, sem Link!!!");

				} else {
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
			}
		});

		// clique em item da lista
		/*
		 * offersGridView.setOnItemClickListener(new OnItemClickListener() {
		 * 
		 * @Override public void onItemClick(AdapterView<?> parent, View view,
		 * int position, long arg3) {
		 * 
		 * Offer offer = myOffers.get(position);
		 * 
		 * SharedPreferences loggedUser = view.getContext()
		 * .getSharedPreferences(SessionControl.USER_LOGGED, 0);
		 * SharedPreferences.Editor editor = loggedUser.edit();
		 * 
		 * String stringUser = SessionControl.encodeSessionOffer(offer);
		 * 
		 * editor.putString(SessionControl.OFFER_DETAIL, stringUser);
		 * editor.commit();
		 * 
		 * Intent intent = new Intent(view.getContext(),
		 * OffersDetailActivity.class); startActivity(intent); } });
		 */

		imgBtnSign.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(v.getContext(),
						SignaturesActivity.class);
				startActivity(intent);
			}
		});

		imgBtnCompras.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(v.getContext(),
						ComprasActivity.class);
				startActivity(intent);
			}
		});

		imgBtnWish.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(AdventaActivity.this,
						WishHomeActivity.class);
				startActivity(intent);
			}
		});

		imgBtnOffers.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(AdventaActivity.this,
						OffersListActivity.class);
				intent.putExtra("listType", "offersMyProfile");
				startActivity(intent);
			}
		});

		// clique no botao filtrar
		btnFiltrar.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				LayoutInflater layoutInflater = LayoutInflater.from(v
						.getContext());

				View promptView = layoutInflater.inflate(
						R.layout.x_pop_opcoes_filtro, null);

				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
						v.getContext());

				alertDialogBuilder.setView(promptView);

				final AlertDialog alertD = alertDialogBuilder.create();

				Button btnCategoria = (Button) promptView
						.findViewById(R.id.btnFilterByCategory);
				Button btnMyProfile = (Button) promptView
						.findViewById(R.id.btnFilterMyProfile);
				Button btnOtherProfile = (Button) promptView
						.findViewById(R.id.btnFilterOtherProfile);
				Button btnCancel = (Button) promptView
						.findViewById(R.id.btnFilterCancel);

				// cancelar
				btnCancel.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {

						alertD.cancel();
					}
				});

				// outro perfil
				btnOtherProfile.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						// vamos para a tela de criação de outro perfil
						Intent intent = new Intent(v.getContext(),
								OfferFilterActivity.class);
						startActivity(intent);
					}
				});

				// meu perfil
				btnMyProfile.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent intent = new Intent(AdventaActivity.this,
								OffersListActivity.class);
						intent.putExtra("listType", "offersMyProfile");
						startActivity(intent);
					}
				});

				// menu de categorias
				btnCategoria.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent intent = new Intent(v.getContext(),
								CategoriesActivity.class);
						startActivity(intent);
					}
				});
				alertD.show();
			}
		});

		// clique na foto do usuario - Link para "MEUS DADOS"
		userPhoto.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(AdventaActivity.this,
						UserActivity.class);
				startActivity(intent);
			}
		});

		// verificamos se é primeiro acesso do usuario
		// caso PRIMEIRO executamos o service responsavel por disparar as
		// notificações
		// caso NAO SEJA continuamos o fluxo normalmente
		firstAccess = SessionControl.verifyFirstAccess(loggedUser);

		if (firstAccess == true) {
			startService(new Intent(AdventaActivity.this,
					BackgroundNotificationService.class));
			isService = true;

			SharedPreferences.Editor preferencesEditor = loggedUser.edit();
			editor.putString(SessionControl.FIRST_ACCESS, "false");
			editor.commit();
		}

		// clique no botao home do cabeçalho
		imgMenuChangeList.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {

				if (listOrientation.equals("styleFull")) {
					styleListAdapter = new OfferStyleListAdapter(
							AdventaActivity.this, myOffers);
					grid.setNumColumns(1);
					grid.setAdapter(styleListAdapter);

					// salvando valor do tipo de lista
					listOrientation = "styleList";

					// salvando o tipo da lista no preferences
					SharedPreferences.Editor editor = loggedUser.edit();

					editor.putString(SessionControl.LIST_ORIENTATION,
							listOrientation);
					editor.commit();

					imgMenuChangeList
							.setImageResource(R.drawable.adventa_icon_inicio_blue);

				} else if (listOrientation.equals("styleList")) {

					adapter = new FinalOfferAdapter(AdventaActivity.this,
							myOffers);
					grid.setNumColumns(2);
					grid.setAdapter(adapter);

					// salvando valor do tipo de lista
					listOrientation = "stylePrincipal";

					// salvando o tipo da lista no preferences
					SharedPreferences.Editor editor = loggedUser.edit();

					editor.putString(SessionControl.LIST_ORIENTATION,
							listOrientation);
					editor.commit();

					imgMenuChangeList
							.setImageResource(R.drawable.adventa_icon_inicio_off);

				} else if (listOrientation.equals("stylePrincipal")) {
					offerFullAdapter = new OfferFullAdapter(
							AdventaActivity.this, myOffers);
					grid.setNumColumns(1);
					grid.setAdapter(offerFullAdapter);

					// salvando valor do tipo de lista
					listOrientation = "styleFull";

					// salvando o tipo da lista no preferences
					SharedPreferences.Editor editor = loggedUser.edit();

					editor.putString(SessionControl.LIST_ORIENTATION,
							listOrientation);
					editor.commit();

					imgMenuChangeList
							.setImageResource(R.drawable.adventa_icon_inicio_on);
				} else {
					adapter = new FinalOfferAdapter(AdventaActivity.this,
							myOffers);
					grid.setNumColumns(2);
					grid.setAdapter(adapter);
				}

			}
		});

		grid.setOnScrollListener(new InfiniteScrollListener(6) {
			@Override
			public void loadMore(int page, int totalItemsCount) {

				Log.e("", "chamando service");
				// mys = OfferBO.listValidOffersForPage(i);
				// chamando service
				Intent commentsIntent = new Intent(AdventaActivity.this,
						OffersPagesPrincipalService.class);
				commentsIntent.putExtra(
						OffersPagesPrincipalService.PARAM_IN_PAGE, i);
				AdventaActivity.this.startService(commentsIntent);

				// preparando o receiver para o retorno do service
				IntentFilter filter = new IntentFilter(
						OffersPagePrincipalResponseReceiver.ACTION_RESP_OFFERS_PAGE_PRINCIPAL);
				filter.addCategory(Intent.CATEGORY_DEFAULT);
				receiverPrincipal = new OffersPagePrincipalResponseReceiver();
				AdventaActivity.this
						.registerReceiver(receiverPrincipal, filter);
				pbAtualiza.setVisibility(View.VISIBLE);
			}
		});

		

	}

	public class AdventaResponseReceiver extends BroadcastReceiver {
		public static final String ACTION_RESP_ADVENTA_PRINCIPAL = "com.mamlambo.intent.action.MESSAGE_PROCESSED_ADVENTA_PRINCIPAL";

		@Override
		public void onReceive(Context context, Intent intent) {

			Bundle parameters = intent.getExtras();
			myOffers = ((List<Offer>) parameters
					.getSerializable(AdventaPrincipalService.PARAM_OFFERS));

			// validando se lista está ou não vazia
			if (myOffers.isEmpty()) {
				Toast.makeText(
						AdventaActivity.this,
						"Ops... Ocorreu algum erro durante o carregamento das ofertas."
								+ " Verifique sua conexão e tente novamente.",
						Toast.LENGTH_LONG).show();
			} else {

				// caso orientacao sera chamada "stylePrincipal", senao
				// "styleList"
				if (listOrientation.equals("stylePrincipal")) {
					adapter = new FinalOfferAdapter(AdventaActivity.this,
							myOffers);
					grid.setNumColumns(2);
					grid.setAdapter(adapter);
				} else if (listOrientation.equals("styleList")) {
					styleListAdapter = new OfferStyleListAdapter(
							AdventaActivity.this, myOffers);
					grid.setNumColumns(1);
					grid.setAdapter(styleListAdapter);
				} else if (listOrientation.equals("styleFull")) {
					offerFullAdapter = new OfferFullAdapter(
							AdventaActivity.this, myOffers);
					grid.setNumColumns(1);
					grid.setAdapter(offerFullAdapter);

					imgMenuChangeList
							.setImageResource(R.drawable.adventa_icon_inicio_on);
				} else {
					adapter = new FinalOfferAdapter(AdventaActivity.this,
							myOffers);
					grid.setNumColumns(2);
					grid.setAdapter(adapter);
				}

				SharedPreferences.Editor editor = loggedUser.edit();

				String homeOffers = SessionControl
						.encodeSessionOffers(myOffers);

				editor.putString(SessionControl.OFFERS, homeOffers);
				editor.commit();
			}
			pbPrincipal.setVisibility(View.GONE);
			pbAtualiza.setVisibility(View.GONE);
			tvAtualiza.setVisibility(View.GONE);
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);

		// Carrega o arquivo de menu.
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
		case R.id.action_users_settings:
			starIntentUser();
			return true;
		case R.id.action_settings:
			SessionControl.shareApp(AdventaActivity.this);
			return true;

		case R.id.action_users_offers_as_list:
			showAsList();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public void starIntentUser() {
		Intent intent = new Intent(AdventaActivity.this, UserActivity.class);
		startActivity(intent);
	}

	public void showAsList() {
		// offersGridView.setNumColumns(1);
		// listAdapter = new OfferListAdapter(myOffers, AdventaActivity.this);
		// offersGridView.setAdapter(listAdapter);
	}

	public void share(View v) {

		if (mySearchOffers == null) {

			String shareBody = "Aproveite essa oferta exclusiva que está disponível somente no ADVENTA: "
					+ myOffers
							.get(Integer.parseInt(String.valueOf(v.getTag())))
							.getTitle();
			Intent sharingIntent = new Intent(
					android.content.Intent.ACTION_SEND);
			sharingIntent.setType("text/plain");
			sharingIntent.putExtra(
					android.content.Intent.EXTRA_SUBJECT,
					"Oferta "
							+ myOffers
									.get(Integer.parseInt(String.valueOf(v
											.getTag()))).getTitle());
			sharingIntent
					.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
			startActivity(Intent.createChooser(sharingIntent,
					"Compartilhe por..."));

		} else {

			String shareBody = "Aproveite essa oferta exclusiva que está disponível somente no ADVENTA: "
					+ mySearchOffers.get(
							Integer.parseInt(String.valueOf(v.getTag())))
							.getTitle();
			Intent sharingIntent = new Intent(
					android.content.Intent.ACTION_SEND);
			sharingIntent.setType("text/plain");
			sharingIntent.putExtra(
					android.content.Intent.EXTRA_SUBJECT,
					"Oferta "
							+ mySearchOffers
									.get(Integer.parseInt(String.valueOf(v
											.getTag()))).getTitle());
			sharingIntent
					.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
			startActivity(Intent.createChooser(sharingIntent,
					"Compartilhe por..."));

		}
	}

	public void comprar(View v) {

		if (mySearchOffers == null) {

			Offer offer = myOffers.get(Integer.parseInt(String.valueOf(v
					.getTag())));
			SharedPreferences loggedUser = v.getContext().getSharedPreferences(
					SessionControl.USER_LOGGED, 0);
			SharedPreferences.Editor editor = loggedUser.edit();

			String stringUser = SessionControl.encodeSessionOffer(offer);

			editor.putString(SessionControl.OFFER_DETAIL, stringUser);
			editor.commit();

			Intent intent = new Intent(v.getContext(),
					OffersDetailActivity.class);
			startActivity(intent);

		} else {

			Offer offer = mySearchOffers.get(Integer.parseInt(String.valueOf(v
					.getTag())));
			SharedPreferences loggedUser = v.getContext().getSharedPreferences(
					SessionControl.USER_LOGGED, 0);
			SharedPreferences.Editor editor = loggedUser.edit();

			String stringUser = SessionControl.encodeSessionOffer(offer);

			editor.putString(SessionControl.OFFER_DETAIL, stringUser);
			editor.commit();

			Intent intent = new Intent(v.getContext(),
					OffersDetailActivity.class);
			startActivity(intent);

		}
	}

	/**
	 * Receiver do carregamento progressivo da lista
	 * 
	 * @author Matheus Odilon - accentialbrasil
	 * 
	 */
	public class OffersPagePrincipalResponseReceiver extends BroadcastReceiver {
		public static final String ACTION_RESP_OFFERS_PAGE_PRINCIPAL = "com.mamlambo.intent.action.MESSAGE_PROCESSED_OFFERS_PAGE_PRINCIPAL";

		@SuppressWarnings("unchecked")
		@Override
		public void onReceive(Context context, Intent intent) {
			Bundle parameters = intent.getExtras();

			Log.e("", "chegou no response");
			mys = (List<Offer>) parameters
					.getSerializable(OffersPagesPrincipalService.PARAM_OUT_OFFERS);
			myOffers.addAll(mys);

			if (listOrientation.equals("stylePrincipal")) {
				adapter.notifyDataSetChanged();
			} else if (listOrientation.equals("styleList")) {
				styleListAdapter.notifyDataSetChanged();
			} else if (listOrientation.equals("styleFull")) {
				offerFullAdapter.notifyDataSetChanged();
			} else {
				adapter.notifyDataSetChanged();
			}
			pbAtualiza.setVisibility(View.GONE);
			i++;
		}
	}
}
