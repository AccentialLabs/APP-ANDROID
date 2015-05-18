package com.accential.trueone;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.accential.trueone.adapter.OAdapter;
import com.accential.trueone.bean.Offer;
import com.accential.trueone.bean.User;
import com.accential.trueone.dao.CompanyDAO;
import com.accential.trueone.service.CompaniesInvitationsService;
import com.accential.trueone.service.OfferNewsIntentService;
import com.accential.trueone.service.OffersIntentService;
import com.accential.trueone.utils.MenuUtil;
import com.accential.trueone.utils.OrientacaoUtils;
import com.example.slide.R;

@SuppressWarnings("all")
public class OffersWishlist extends Activity{

	private TextView wishTitle;
	private OAdapter oAdapter;
	private GridView list;
	private WebView wbvLoad;
	private String wishlistName;
	private OffersResponseReceiver receiver; 
	private User user;
	private List<Offer> offers;
	//itens do menu
	private ImageView imageMenuWish;
	private TextView textMenuWish;
	private ImageView imageMenuCompras;
	private TextView textMenuCompras;
	private ImageView imageMenuAssinaturas;
	private TextView textMenuAssinaturas;
	private ImageView imageMenuDashboard;
	private TextView textMenuDashboard;
	//item do usuario
	private TextView textViewNameUser;
	private  boolean logado = true;
	//item - quantidade novidades
	private int qtdsNova;
	//user menu
	private Spinner spinnerMenu;
	private Map<String, Object> newsMap;
	private TextView qtdAvisoNovidade;
	private ImageView newsImg;

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);								
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_offers_by_wish);		

		//DETALHE DESSE CODIGO NO LoginActivity
		String type = getResources().getString(R.string.isTablet);
		if(type.equals("false")){
			Log.i("TESTE", "IS TABLET= " + type);
			OrientacaoUtils.setOrientationVertical(this);
		}

		//ITENS DO MENU
		imageMenuWish = (ImageView) findViewById(R.id.imageFooterWish);
		textMenuWish = (TextView) findViewById(R.id.textViewFooterWish);
		imageMenuCompras = (ImageView) findViewById(R.id.imageFooterCompras);
		textMenuCompras = (TextView) findViewById(R.id.textViewFooterCompras);
		imageMenuAssinaturas = (ImageView) findViewById(R.id.imageFooterAssinaturas);
		textMenuAssinaturas = (TextView) findViewById(R.id.textViewFooterAssinaturas);
		imageMenuDashboard = (ImageView) findViewById(R.id.imageFooterInvitations);
		textMenuDashboard = (TextView) findViewById(R.id.textViewFooterInvitations);
		//item do usuario
		textViewNameUser = (TextView) findViewById(R.id.textViewHeader_NomeUsuario);

		wishTitle = (TextView) findViewById(R.id.textView_titleOfferByWish);

		newsImg = (ImageView) findViewById(R.id.imageView_avisoConvite);
		qtdAvisoNovidade = (TextView) findViewById(R.id.textView_qtdConvites);

		//USER MENU
		spinnerMenu = (Spinner) findViewById(R.id.spinner_header);

		List<String> opcoes = MenuUtil.getMenuList();

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.drawable.spinner_item,opcoes);

		spinnerMenu.setAdapter(adapter);
		spinnerMenu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				/**
				 * Clique em "Sair"
				 */
				if(parent.getItemAtPosition(position).toString().equals("Sair")){
					MenuUtil.getOut(view.getContext());
				}
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});

		list = (GridView) findViewById(R.id.list);
		wbvLoad = (WebView) findViewById(R.id.webView1);
		imageMenuWish.setImageResource(R.drawable.wishlist_on);
		textMenuWish.setTextColor(Color.rgb(255, 117, 24));
		wbvLoad.loadUrl("https://www.baltimorecity.gov/Portals/_default/Skins/EnterpriseUI/images/icons/loading.gif");

		Intent intent = getIntent();
		Bundle parameter = intent.getExtras();
		newsMap = (Map<String, Object>) parameter.getSerializable(OfferNewsIntentService.PARAM_OUT_MAP_NEWS);
		qtdAvisoNovidade.setText(String.valueOf(newsMap.get(OfferNewsIntentService.MAP_TOTAL_QTD)));

		int id = parameter.getInt("idWish");
		String wishlistTitle = parameter.getString("nameWish");
		user = (User) getIntent().getSerializableExtra("user");

		textViewNameUser.setText(user.getName());

		//START SERVICE
		Intent msgIntent = new Intent(OffersWishlist.this, OffersIntentService.class);
		msgIntent.putExtra(OffersIntentService.PARAM_IN_WISH_ID, id);
		Log.i("START NO SERVICE", "SERVICO STARTADO - ");
		startService(msgIntent);

		IntentFilter filter = new IntentFilter(OffersResponseReceiver.RESP_ACTION);
		filter.addCategory(Intent.CATEGORY_DEFAULT);
		receiver = new OffersResponseReceiver();
		registerReceiver(receiver, filter);
		//************************************************

		//PARA EXECUTAR SEM SERVICE, SEGUE CODIGO
		//		offers = new ArrayList<Offer>();
		//		List<UsersWishlistCompany> uwcs = UsersWishlistCompanyBO.listaObjUWCByWish(id);
		//
		//		for (UsersWishlistCompany uwc : uwcs) {
		//			offers.add(uwc.getOffer());
		//		}
		//
		//		wishTitle.setText(wishlistTitle);
		//		oAdapter = new OAdapter(offers, OffersWishlist.this);
		//		list.setAdapter(oAdapter);
		//		wbvLoad.setVisibility(View.GONE);

		list.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapter, View view, int position,
					long id) {

				Intent intent = new Intent(view.getContext(), OfferDetail.class);
				Bundle parameters = new Bundle();

				Offer offer = offers.get(position);

				intent.putExtra("offer", offer);
				intent.putExtra("user", user);
				parameters.putBoolean("logado", true);

				//REALIZA PESQUISA DE COMPANY POR OFFER
				Map<String, Map<String, Map<String, String>>> key2 = new HashMap<String,Map<String, Map<String, String>>>();
				Map<String,Map<String,String>> params2 = new HashMap<String,Map<String,String>>();
				Map<String,String> conditions2 = new HashMap<String,String>();

				conditions2.put("Offer.id", String.valueOf(offer.getId()));
				params2.put("conditions", conditions2);
				key2.put("Offer", params2);

				AsyncTask<Map, Void, Integer> async = new AsyncTask<Map, Void, Integer>(){

					@Override
					protected Integer doInBackground(Map... params) {
						CompanyDAO dao = new CompanyDAO();
						int i = dao.retornaCompId(params[0]);
						return i;
					}
				};
				int y = 0;
				try {
					y = async.execute(key2).get();
				} catch (Exception e) {
					Log.e("ERRO", "ERRO EM REALIZAR PESQUISA DE COMPANY");
					e.printStackTrace();
				}

				parameters.putInt("companyId", y);

				intent.putExtras(parameters);
				startActivity(intent);

			}
		});


		//CLIQUE NO MENU
		// 1 - WISHLIST
		textMenuWish.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//AO SELECIONAR A OP����O WISHLIST NO MENU A ACTIVITY CARREGA O OBJ USUARIO E O ATRIBIUTO 'LOGADO'
				//PARA TRANSFERIR VIA INTENT
				imageMenuWish.setImageResource(R.drawable.wishlist_on);
				imageMenuAssinaturas.setImageResource(R.drawable.assinaturas_off);
				imageMenuCompras.setImageResource(R.drawable.compras_off);
				imageMenuDashboard.setImageResource(R.drawable.dashboard_off);

				textMenuWish.setTextColor(Color.rgb(255, 117, 24));
				textMenuCompras.setTextColor(Color.rgb(105, 105, 105));
				textMenuDashboard.setTextColor(Color.rgb(105, 105, 105));
				textMenuAssinaturas.setTextColor(Color.rgb(105, 105, 105));
				//MUDEI AQUI - WishlistActivity
				Intent intent = new Intent(v.getContext(), WishlistLand.class);
				Bundle parameters = new Bundle();

				intent.putExtra("user", user);
				parameters.putBoolean("logado", logado);
				Log.i("nome do user", user.getName());
				parameters.putSerializable(CompaniesInvitationsService.PARAM_OUT_INVITATIONS, (Serializable) newsMap);
				intent.putExtras(parameters);
				startActivity(intent);
			}
		});

		//SELECIONA ITENS DO MENU
		//2 - COMPRAS
		textMenuCompras.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//AO SELECIONAR A OP����O COMPRAS DO MENU A ACTIVITY CARREGA O OBJ USUARIO 
				//PARA TRANSFERIR VIA INTENT
				imageMenuWish.setImageResource(R.drawable.wishlist_off);
				imageMenuAssinaturas.setImageResource(R.drawable.assinaturas_off);
				imageMenuCompras.setImageResource(R.drawable.compras_on);
				imageMenuDashboard.setImageResource(R.drawable.dashboard_off);

				textMenuCompras.setTextColor(Color.rgb(255, 117, 24));
				textMenuWish.setTextColor(Color.rgb(105, 105, 105));
				textMenuDashboard.setTextColor(Color.rgb(105, 105, 105));
				textMenuAssinaturas.setTextColor(Color.rgb(105, 105, 105));

				Intent intent = new Intent(v.getContext(), ComprasActivity.class);
				Bundle parameters = new Bundle();
				parameters.putSerializable(CompaniesInvitationsService.PARAM_OUT_INVITATIONS, (Serializable) newsMap);
				intent.putExtras(parameters);
				intent.putExtra("user", user);
				startActivity(intent);
			}
		});

		//SELECIONA ITENS DO MENU
		//3 - ASSINATURAS
		textMenuAssinaturas.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				imageMenuWish.setImageResource(R.drawable.wishlist_off);
				imageMenuAssinaturas.setImageResource(R.drawable.assinaturas_on);
				imageMenuCompras.setImageResource(R.drawable.compras_off);
				imageMenuDashboard.setImageResource(R.drawable.dashboard_off);

				textMenuAssinaturas.setTextColor(Color.rgb(255, 117, 24));
				textMenuWish.setTextColor(Color.rgb(105, 105, 105));
				textMenuDashboard.setTextColor(Color.rgb(105, 105, 105));
				textMenuCompras.setTextColor(Color.rgb(105, 105, 105));
				//MUDEI AQUI - CompaniesUserActivity

				Intent intentC = new Intent(v.getContext(), SignaturesActivity.class);
				Bundle parameters = new Bundle();
				parameters.putSerializable(CompaniesInvitationsService.PARAM_OUT_INVITATIONS, (Serializable) newsMap);
				intentC.putExtras(parameters);
				intentC.putExtra("user", user);
				startActivity(intentC);
			}
		});

		//SELECIONA ITENS DO MENU
		//4 - ASSINATURAS
		textMenuDashboard.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				imageMenuWish.setImageResource(R.drawable.wishlist_off);
				imageMenuAssinaturas.setImageResource(R.drawable.assinaturas_off);
				imageMenuCompras.setImageResource(R.drawable.compras_off);
				imageMenuDashboard.setImageResource(R.drawable.dashboard_on);

				textMenuAssinaturas.setTextColor(Color.rgb(105, 105, 105));
				textMenuWish.setTextColor(Color.rgb(105, 105, 105));
				textMenuDashboard.setTextColor(Color.rgb(255, 117, 24));
				textMenuCompras.setTextColor(Color.rgb(105, 105, 105));

				Intent intentD = new Intent(v.getContext(), MainActivity.class);
				Bundle bundle = new  Bundle();
				bundle.putBoolean("logado", true);
				bundle.putSerializable(CompaniesInvitationsService.PARAM_OUT_INVITATIONS, (Serializable) newsMap);
				intentD.putExtras(bundle);
				intentD.putExtra("usuario", user);
				startActivity(intentD);
			}
		});

		newsImg.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intentNews = new Intent(OffersWishlist.this, NewsActivity.class);
				intentNews.putExtra("user", user);
				Bundle bundle = new Bundle();
				bundle.putSerializable(OfferNewsIntentService.PARAM_OUT_MAP_NEWS, (Serializable) newsMap);
				intentNews.putExtras(bundle);
				startActivity(intentNews);
			}
		});

	}

	public void executaTesteService(){

		Intent msgIntents = new Intent(this, OffersIntentService.class);
		String strInputMsg = "ESSA EH A MENSAGEM PASSADA POR PARAMETRO";
		msgIntents.putExtra(OffersIntentService.PARAM_IN_MSG, strInputMsg);
		msgIntents.putExtra(OffersIntentService.PARAM_IN_MSG, "Teste");
		msgIntents.putExtra(OffersIntentService.PARAM_IN_WISH_ID, 72);

		startService(msgIntents);

		IntentFilter filter = new IntentFilter(OffersResponseReceiver.RESP_ACTION);
		filter.addCategory(Intent.CATEGORY_DEFAULT);
		receiver = new OffersResponseReceiver();
		registerReceiver(receiver, filter);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public class OffersResponseReceiver extends BroadcastReceiver {
		public static final String RESP_ACTION =    
				"com.mamlambo.intent.action.MESSAGE_PROCESSED_OFFS_WISHLIST";
		@Override
		public void onReceive(Context context, Intent intent) {

			Bundle parameters = intent.getExtras();

			offers = (List<Offer>) parameters.getSerializable(OffersIntentService.PARAM_OUT_LIST_OFFER);

			Log.i("teste", "SERVICE OFFERS WISHLIST: tamanho da lista - " + String.valueOf(offers.size()));

			oAdapter = new OAdapter(offers, OffersWishlist.this);
			list.setAdapter(oAdapter);

			wbvLoad.setVisibility(View.GONE);
		}

	}

	public void selectOfferItem(View v){

		Log.i("TESTE", "EXECUTA SELECT OFFER DO WISH");

		int pos = (Integer) v.getTag();

		Intent intent = new Intent(v.getContext(), OfferDetail.class);
		Bundle parameters = new Bundle();

		Offer offer = offers.get(pos);

		intent.putExtra("offer", offer);
		intent.putExtra("user", user);
		parameters.putBoolean("logado", true);
		parameters.putSerializable(OfferNewsIntentService.PARAM_OUT_MAP_NEWS, (Serializable) newsMap);

		//REALIZA PESQUISA DE COMPANY POR OFFER
		Map<String, Map<String, Map<String, String>>> key2 = new HashMap<String,Map<String, Map<String, String>>>();
		Map<String,Map<String,String>> params2 = new HashMap<String,Map<String,String>>();
		Map<String,String> conditions2 = new HashMap<String,String>();

		conditions2.put("Offer.id", String.valueOf(offer.getId()));
		params2.put("conditions", conditions2);
		key2.put("Offer", params2);

		AsyncTask<Map, Void, Integer> async = new AsyncTask<Map, Void, Integer>(){

			@Override
			protected Integer doInBackground(Map... params) {
				CompanyDAO dao = new CompanyDAO();
				int i = dao.retornaCompId(params[0]);
				return i;
			}
		};
		int y = 0;
		try {
			y = async.execute(key2).get();
		} catch (Exception e) {
			Log.e("ERRO", "ERRO EM REALIZAR PESQUISA DE COMPANY");
			e.printStackTrace();
		}

		parameters.putInt("companyId", y);

		intent.putExtras(parameters);
		startActivity(intent);

	}
}
