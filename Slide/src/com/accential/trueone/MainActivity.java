package com.accential.trueone;


import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.accential.trueone.adapter.OAdapter;
import com.accential.trueone.bean.CompaniesInvitationsUser;
import com.accential.trueone.bean.Offer;
import com.accential.trueone.bean.User;
import com.accential.trueone.bo.OfferBO;
import com.accential.trueone.bo.UserBO;
import com.accential.trueone.dao.CompanyDAO;
import com.accential.trueone.service.CompaniesInvitationsService;
import com.accential.trueone.service.MainOfferIntentService;
import com.accential.trueone.service.OfferNewsIntentService;
import com.accential.trueone.utils.MenuUtil;
import com.accential.trueone.utils.OrientacaoUtils;
import com.example.slide.R;
import com.loopj.android.image.SmartImageView;

/**
 *Tela principal do sistema, �� redirecionada logo ap��s a tela de login
 * @author accentialbrasil
 *
 */
@SuppressLint("NewApi")
@SuppressWarnings("all")
public class MainActivity extends Activity {

	public static final String QTQ_NOVA = "qtdsNovas";
	private boolean logado;
	private GridView list;
	private EditText usuario;
	private EditText senha;
	private OAdapter oAdapter;
	private TextView tView;
	private WebView wbV;
	private TextView textViewNotFound;
	private WebView wbvLoading;
	//MENU
	private ImageView imageMenuWish;
	private TextView textMenuWish;
	private ImageView imageMenuCompras;
	private TextView textMenuCompras;
	private ImageView imageMenuAssinaturas;
	private TextView textMenuAssinaturas;
	private ImageView imageMenuDash;
	private TextView textMenuDash;
	private TextView textMenuOferta;
	private ImageView imageMenuOferta;
	//INFO DO USUARIO
	private TextView textViewNameUser;
	private SmartImageView imageViewPhotoUser;
	private TableRow tabRow_offerNotFound;
	private int id;
	private String teste;
	private TextView qtdAvisoNovidade;
	private ImageView imgAvisoNovidade;
	//PROCURA
	private ImageView btnProcurar;
	//
	private MainResponseReceiver receiver;
	private NewsOfferReceiver receiverNews;
	//
	private List<Offer> listOffer;
	private User u;
	//
	private int qtdNova;
	//NOTIFICATION
	private String tickerText;
	private CharSequence notificationTitulo;
	private CharSequence notificationMensagem;
	//USER MENU
	private Spinner spinnerMenu;
	//TIMERS
	private Timer timerForHour;
	private Timer timerForDay; 
	private Timer timerForWeek;
	//
	private Map<String, Object> newsMap;
	private ImageView newsImg;

	@SuppressLint("DefaultLocale")
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);	
		setContentView(R.layout.activity_main);	

		//DETALHE DESSE CODIGO EM LoginActivity
		String type = getResources().getString(R.string.isTablet);
		if(type.equals("false")){
			Log.i("TESTE", "IS TABLET= " + type);
			OrientacaoUtils.setOrientationVertical(this);
		}
		
		if(savedInstanceState != null){
			teste = (String) savedInstanceState.get("teste");
			id = savedInstanceState.getInt("id");
		}
		Log.i("VALOR DO ID", String.valueOf(id));

		tView = (TextView) findViewById(R.id.textView_itemOffer1);

		//wbV = (WebView) findViewById(R.id.webView1);

		//avisoNovidade = (TextView) findViewById(R.id.textView_qtdAvisos);

		//progress = (ProgressBar) findViewById(R.id.progressBarViewHeader);
		textViewNameUser = (TextView) findViewById(R.id.textViewHeader_NomeUsuario);
		tabRow_offerNotFound = (TableRow) findViewById(R.id.tableRow_offerNotFound); 

		wbvLoading = (WebView) findViewById(R.id.webViewLoad);
		imageViewPhotoUser = (SmartImageView) findViewById(R.id.imageView_userPhoto);
		//ITENS DO MENU
		imageMenuWish = (ImageView) findViewById(R.id.imageFooterWish);
		textMenuWish = (TextView) findViewById(R.id.textViewFooterWish);
		imageMenuCompras = (ImageView) findViewById(R.id.imageFooterCompras);
		textMenuCompras = (TextView) findViewById(R.id.textViewFooterCompras);
		imageMenuAssinaturas = (ImageView) findViewById(R.id.imageFooterAssinaturas);
		textMenuAssinaturas = (TextView) findViewById(R.id.textViewFooterAssinaturas);
		imageMenuDash = (ImageView) findViewById(R.id.imageFooterInvitations);
		textMenuDash = (TextView) findViewById(R.id.textViewFooterInvitations);
		textViewNotFound = (TextView) findViewById(R.id.textViewOffersNotFound);
		btnProcurar = (ImageView) findViewById(R.id.imageView_btnProcurar);
		imageMenuOferta = (ImageView) findViewById(R.id.imageFooterDashboard);
		textMenuOferta = (TextView) findViewById(R.id.textViewFooterDash);
		qtdAvisoNovidade = (TextView) findViewById(R.id.textView_qtdConvites);
		newsImg = (ImageView) findViewById(R.id.imageView_avisoConvite);

		newsImg.setVisibility(View.INVISIBLE);

		//TIMERS
		timerForHour = new Timer();
		timerForDay = new Timer(); 
		timerForWeek = new Timer(); 

		//MENU
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
					spinnerMenu.setSelection(0);
					MenuUtil.getOut(view.getContext());
				}

				/**
				 * Clique em "Prefer��ncia"
				 */
				else if(parent.getItemAtPosition(position).toString().equals("Prefer��ncias")){
					//	TESTANDO METODO ESTATICO 
					spinnerMenu.setSelection(0);
					Intent intent = MenuUtil.toPreferences(view.getContext(), u, newsMap);
					startActivity(intent);
				}

				/**
				 * Clique em "Dados Cadastrais"
				 */
				else if(parent.getItemAtPosition(position).toString().equals("Dados Cadastrais")){
					//	TESTANDO METODO ESTATICO 
					spinnerMenu.setSelection(0);
					Intent intent = MenuUtil.toRegistrationData(view.getContext(), u, newsMap);
					startActivity(intent);
				}
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});

		//qtdAvisoNovidade = (TextView) findViewById(R.id.textView_qtdAvisos);

		imageMenuAssinaturas.setImageResource(R.drawable.assinaturas_off);
		imageMenuWish.setImageResource(R.drawable.wishlist_off);
		imageMenuCompras.setImageResource(R.drawable.compras_off);
		imageMenuDash.setImageResource(R.drawable.dashboard_on);

		//imgAvisoBandeira = (ImageView) findViewById(R.id.imageView_avisoBandeira);

		textMenuDash.setTextColor(Color.rgb(255, 117, 24));

		//STARTANDO SERVICE NEWS
		//		Log.i("teste", "STANDO SERVICE NEWS OFFER");
		//		Intent msgIntent2 = new Intent(MainActivity.this, OfferNewsIntentService.class);
		//		startService(msgIntent2);
		//
		//		IntentFilter filter2 = new IntentFilter(NewsOfferReceiver.ACTION_RESP_NEWS_OFFERS);
		//		filter2.addCategory(Intent.CATEGORY_DEFAULT);
		//		receiverNews = new NewsOfferReceiver();
		//		registerReceiver(receiverNews, filter2);
		//----------------

		wbvLoading.loadUrl("https://www.baltimorecity.gov/Portals/_default/Skins/EnterpriseUI/images/icons/loading.gif");

		list = (GridView) findViewById(R.id.list);
		//INTENT RESPONSAVEL POR ENVIAR OBJ USER E OFERTA		
		Intent intent = getIntent();
		Bundle parametersUser = intent.getExtras();
		logado = parametersUser.getBoolean("logado");

		//final User u = (User) getIntent().getSerializableExtra("usuario");
		textViewNotFound.setVisibility(View.GONE);
		//tabRow_offerNotFound.setVisibility(View.GONE);

		//STANTANDO SERVICE
		Log.i("TESTE", "START SERVICE NOW!!!");
		Intent msgIntent = new Intent(MainActivity.this, MainOfferIntentService.class);
		Log.i("START NO SERVICE", "SERVICO STARTADO");

		startService(msgIntent);

		IntentFilter filter = new IntentFilter(MainResponseReceiver.ACTION_RESP);
		filter.addCategory(Intent.CATEGORY_DEFAULT);
		receiver = new MainResponseReceiver();
		registerReceiver(receiver, filter);
		//INTERROMPE SERVICO

		User user = null;
		if(getIntent().getSerializableExtra("usuario") != null){
			user = (User) getIntent().getSerializableExtra("usuario");
		}else {
			user = UserBO.searchById(id);
		}

		id = user.getId();
		u = user;

		//TIMER
		SimpleDateFormat df = new SimpleDateFormat("HH:mm");
		Date dt1 = new Date();
		Log.i("teste", "DATA: " + String.valueOf(df.format(dt1)));

		//testeTimer();

		//CONDI����O DE LOGIN USER
		if(logado == true){

			textViewNameUser.setText(u.getName());
			//			imageViewPhotoUser.setImageUrl(u.getPhoto());
			//			DownloadImagemUtil downPhoto = new DownloadImagemUtil(this);
			//			downPhoto.download(this, u.getPhoto(), imageViewPhotoUser, progress);

		} else if(logado == false){
			//INTENS DO MENU
			imageMenuWish.setVisibility(View.GONE);
			textMenuWish.setVisibility(View.GONE);

			imageMenuCompras.setVisibility(View.GONE);
			textMenuCompras.setVisibility(View.GONE);

			imageMenuAssinaturas.setVisibility(View.GONE);
			textMenuAssinaturas.setVisibility(View.GONE);

			//progress.setVisibility(View.GONE);
			textViewNameUser.setText("ENTRAR");
			textViewNameUser.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intentLog = new Intent(MainActivity.this, LoginActivity.class);
					startActivity(intentLog);
				}
			});
		}

		//SELECIONA ITENS DO MENU
		// 1 - WISHLIST
		textMenuWish.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//AO SELECIONAR A OP����O WISHLIST NO MENU A ACTIVITY CARREGA O OBJ USUARIO E O ATRIBIUTO 'LOGADO'
				//PARA TRANSFERIR VIA INTENT
				imageMenuWish.setImageResource(R.drawable.wishlist_on);
				imageMenuAssinaturas.setImageResource(R.drawable.assinaturas_off);
				imageMenuCompras.setImageResource(R.drawable.compras_off);
				imageMenuDash.setImageResource(R.drawable.dashboard_off);
				imageMenuOferta.setImageResource(R.drawable.ofertas_off);

				textMenuWish.setTextColor(Color.rgb(255, 117, 24));
				textMenuCompras.setTextColor(Color.rgb(105, 105, 105));
				textMenuDash.setTextColor(Color.rgb(105, 105, 105));
				textMenuAssinaturas.setTextColor(Color.rgb(105, 105, 105));
				textMenuOferta.setTextColor(Color.rgb(105, 105, 105));
				//MUDEI AQUI - WishlistActivity
				Intent intent = new Intent(v.getContext(), WishlistLand.class);
				Bundle parameters = new Bundle();

				Log.i("teste","PASSANDO QUANTIDADE : " + String.valueOf(qtdNova));
				intent.putExtra(MainActivity.QTQ_NOVA, qtdNova);
				intent.putExtra("user", u);
				parameters.putBoolean("logado", logado);
				parameters.putInt(MainActivity.QTQ_NOVA, qtdNova);
				parameters.putSerializable(CompaniesInvitationsService.PARAM_OUT_INVITATIONS, (Serializable) newsMap);
				Log.i("nome do user", u.getName());
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
				imageMenuDash.setImageResource(R.drawable.dashboard_off);
				imageMenuOferta.setImageResource(R.drawable.ofertas_off);

				textMenuCompras.setTextColor(Color.rgb(255, 117, 24));
				textMenuWish.setTextColor(Color.rgb(105, 105, 105));
				textMenuDash.setTextColor(Color.rgb(105, 105, 105));
				textMenuAssinaturas.setTextColor(Color.rgb(105, 105, 105));
				textMenuOferta.setTextColor(Color.rgb(105, 105, 105));

				Intent intent = new Intent(v.getContext(), ComprasActivity.class);

				intent.putExtra(MainActivity.QTQ_NOVA, qtdNova);
				intent.putExtra("user", u);
				Bundle parameters = new Bundle();
				parameters.putSerializable(CompaniesInvitationsService.PARAM_OUT_INVITATIONS, (Serializable) newsMap);
				intent.putExtras(parameters);
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
				imageMenuDash.setImageResource(R.drawable.dashboard_off);
				imageMenuOferta.setImageResource(R.drawable.ofertas_off);

				textMenuAssinaturas.setTextColor(Color.rgb(255, 117, 24));
				textMenuWish.setTextColor(Color.rgb(105, 105, 105));
				textMenuDash.setTextColor(Color.rgb(105, 105, 105));
				textMenuCompras.setTextColor(Color.rgb(105, 105, 105));
				textMenuOferta.setTextColor(Color.rgb(105, 105, 105));
				//MUDEI AQUI - CompaniesUserActivity

				Intent intentC = new Intent(v.getContext(), SignaturesActivity.class);
				intentC.putExtra(MainActivity.QTQ_NOVA, qtdNova);
				intentC.putExtra("user", u);
				Bundle parameters = new Bundle();
				parameters.putSerializable(CompaniesInvitationsService.PARAM_OUT_INVITATIONS, (Serializable) newsMap);
				intentC.putExtras(parameters);
				startActivity(intentC);
			}
		});

		//SELECIONA ITENS DO MENU
		//4 - OFERTAS
		textMenuOferta.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				imageMenuWish.setImageResource(R.drawable.wishlist_off);
				imageMenuAssinaturas.setImageResource(R.drawable.assinaturas_off);
				imageMenuCompras.setImageResource(R.drawable.compras_off);
				imageMenuOferta.setImageResource(R.drawable.ofertas_on);
				imageMenuDash.setImageResource(R.drawable.dashboard_off);

				textMenuAssinaturas.setTextColor(Color.rgb(105, 105, 105));
				textMenuWish.setTextColor(Color.rgb(105, 105, 105));
				textMenuOferta.setTextColor(Color.rgb(255, 117, 24));
				textMenuCompras.setTextColor(Color.rgb(105, 105, 105));
				textMenuDash.setTextColor(Color.rgb(105, 105, 105));
			}
		});
		//IMPLEMENTAR NO TABLET
		//		btnProcurar.setOnClickListener(new OnClickListener() {
		//			
		//			@Override
		//			public void onClick(View v) {
		//				searchOfferByTitle();
		//			}
		//		});

		startNewsService();
	}


	@Override  
	protected void onSaveInstanceState(Bundle outState) {  
		super.onSaveInstanceState(outState);  
		outState.putString("teste", teste);  
		outState.putInt("id", id);
	}  

	public void searchOfferByTitle(){

		EditText textTitle = (EditText) findViewById(R.id.editText1);

		if(textTitle.getText().toString().isEmpty()){
			Toast.makeText(this, "Insira nome do produto ou qualquer parte do nome.", Toast.LENGTH_LONG).show();
		}else{
			List<Offer> offers = OfferBO.searchOffersByTitle(textTitle.getText().toString());

			oAdapter = new OAdapter(offers, this);
			list.setAdapter(oAdapter);
			oAdapter.notifyDataSetChanged();
		}

	}

	public class MainResponseReceiver extends BroadcastReceiver {
		public static final String ACTION_RESP =    
				"com.mamlambo.intent.action.MESSAGE_PROCESSED";


		@Override
		public void onReceive(Context context, Intent intent) {

			Log.i("teste", "EXECUTANDO RECEIVER DA CLASSE MainActivity");

			Bundle parameters = intent.getExtras();
			listOffer = (List<Offer>) parameters.getSerializable(MainOfferIntentService.PARAM_OFFER_LIST);

			if(listOffer.size() != 0){

				tabRow_offerNotFound.setVisibility(View.GONE);

				oAdapter = new OAdapter(listOffer, MainActivity.this);
				list.setAdapter(oAdapter);
				Log.i("TAMANHO DA LISTA", String.valueOf(listOffer.size()));

				//METODO ACIONADO QUANDO HA CLIQUE EM ALGUM ELEMENTO DA LISTA
				//				list.setOnItemClickListener(new OnItemClickListener() {
				//					@Override
				//					public void onItemClick(AdapterView parent, View view, int position,
				//							long id) {
				//
				//						Intent intent = new Intent(view.getContext(), OfferDetail.class);
				//						Bundle parameters = new Bundle();
				//
				//						Offer offer = listOffer.get(position);
				//
				//						intent.putExtra("offer", offer);
				//						intent.putExtra("user", u);
				//						parameters.putBoolean("logado", logado);
				//
				//						//REALIZA PESQUISA DE COMPANY POR OFFER
				//						Map<String, Map<String, Map<String, String>>> key2 = new HashMap<String,Map<String, Map<String, String>>>();
				//						Map<String,Map<String,String>> params2 = new HashMap<String,Map<String,String>>();
				//						Map<String,String> conditions2 = new HashMap<String,String>();
				//
				//						conditions2.put("Offer.id", String.valueOf(offer.getId()));
				//						params2.put("conditions", conditions2);
				//						key2.put("Offer", params2);
				//
				//						AsyncTask<Map, Void, Integer> async = new AsyncTask<Map, Void, Integer>(){
				//
				//							@Override
				//							protected Integer doInBackground(Map... params) {
				//								CompanyDAO dao = new CompanyDAO();
				//								int i = dao.retornaCompId(params[0]);
				//								return i;
				//							}
				//						};
				//						int y = 0;
				//						try {
				//							y = async.execute(key2).get();
				//						} catch (Exception e) {
				//							Log.e("ERRO", "ERRO EM REALIZAR PESQUISA DE COMPANY");
				//							e.printStackTrace();
				//						}
				//
				//						parameters.putInt("companyId", y);
				//
				//						intent.putExtras(parameters);
				//						startActivity(intent);
				//					}
				//
				//				});
				//END IF
			}else{
				list.setVisibility(View.GONE);
				//tabRow_offerNotFound.setVisibility(View.VISIBLE);
				textViewNotFound.setVisibility(View.VISIBLE);
			}
			wbvLoading.setVisibility(View.GONE);
			Log.i("TESTE DE RETORNO DA LISTA", "EXECULTOU O SERVICE");
			Log.i("TESTE", "TAMANHO DA LISTA DE OFERTA - SERVICE: " +  String.valueOf(listOffer.size()));

			Log.i("teste", "CHAMAMOS METODO DENTRO DO SERVICE AGORA ->");
			testeTimer();
			//avisoNovidade.setText(String.valueOf(listOffer.size()));
		}

	}

	//teste do clique no botao comprar
	public void selectOfferItem(View myView){

		Log.i("teste", "EXECUTA SELECT OFFER DO MAIN");

		int myPosition = (Integer) myView.getTag(); 

		Log.i("teste","POSITION SELECIONADO: " + String.valueOf(myPosition));

		Intent intent = new Intent(myView.getContext(), OfferDetail.class);
		Bundle parameters = new Bundle();

		Offer offer = listOffer.get(myPosition);

		intent.putExtra("offer", offer);
		intent.putExtra("user", u);
		parameters.putBoolean("logado", logado);
		Log.i("teste", "ENVIA MAP - MAIN: " + String.valueOf(newsMap.size()));

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

	public void teste(View v){
		Log.i("TESTE", "teste do position: " + String.valueOf(v.getTag()));
	}

	public void startNewsService(){
		Log.i("teste", "EXECULTANDO METODO START SERVICE");

		Intent msgIntent = new Intent(MainActivity.this, OfferNewsIntentService.class);
		msgIntent.putExtra(OfferNewsIntentService.PARAM_IN_USER_ID, id);
		startService(msgIntent);

		IntentFilter filter = new IntentFilter(NewsOfferReceiver.ACTION_RESP_NEWS_OFFERS);
		filter.addCategory(Intent.CATEGORY_DEFAULT);
		receiverNews = new NewsOfferReceiver();
		registerReceiver(receiverNews, filter);
	}

	/**
	 * Recebedor do Servico de contagem de novidades - OfferNewsIntentService
	 * @author Matheus Odilon - accentialbrasil
	 */
	public class NewsOfferReceiver extends BroadcastReceiver {
		public static final String ACTION_RESP_NEWS_OFFERS =    
				"com.mamlambo.intent.action.MESSAGE_PROCESSED_NEWS_OFFER";
		@Override
		public void onReceive(Context context, Intent intent) {

			Bundle params = intent.getExtras();
			newsMap = (Map<String, Object>) params.getSerializable(OfferNewsIntentService.PARAM_OUT_MAP_NEWS);
			List<CompaniesInvitationsUser> invites = (List<CompaniesInvitationsUser>) newsMap.get(OfferNewsIntentService.MAP_INVITES);
			int total = (Integer) newsMap.get(OfferNewsIntentService.MAP_TOTAL_QTD);

			Log.i("TESTE", "RECEIVER - QUANTIDADE DE NOVAS: " +  String.valueOf(qtdNova));
			Log.i("teste", "RECEIVER - QUANTIDADE DE CONVITES: " + String.valueOf(invites.size()));
			Log.i("TOTAL", "RECEIVER - QUANTIDADE DE NOVIDADES TOTAL: " + String.valueOf(newsMap.get(OfferNewsIntentService.MAP_TOTAL_QTD)));

			qtdAvisoNovidade.setText(String.valueOf(total));
			newsImg.setVisibility(View.VISIBLE);
			qtdNova = total;
			qtdAvisoNovidade.setTag(total);
			Log.i("tste", "LIBERADO ONCLICK");

			final Map<String, Object> transferMap =  newsMap;
			Log.i("teste", "TAMANHO DO MAP ANTES DE ENVIAR: " + String.valueOf(newsMap.size()));

			newsImg.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intentNews = new Intent(MainActivity.this, NewsActivity.class);
					intentNews.putExtra("user", u);
					Bundle bundle = new Bundle();
					bundle.putSerializable(OfferNewsIntentService.PARAM_OUT_MAP_NEWS, (Serializable) newsMap);
					intentNews.putExtras(bundle);
					startActivity(intentNews);
				}
			});
		}
	}

	/**
	 * menu bar
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		//inflater.inflate(R.menu., menu);

		return super.onCreateOptionsMenu(menu);
	}
	//	@Override
	//	public boolean onOptionsItemSelected(MenuItem item) {
	//		// Take appropriate action for each action item click
	//		switch (item.getItemId()) {
	//		case R.id.action_search:
	//			Log.i("teste","CLIQUEI");
	//			// search action
	//			return true;
	//		default:
	//			return super.onOptionsItemSelected(item);
	//		}
	//	}

	/**
	 * Desloga
	 * @param view
	 */
	public static void sair(View v){
		v.getContext().getSharedPreferences(LoginActivity.PREFS_USER, 0).edit().clear().commit();
		Intent i = new Intent(v.getContext(), LoginActivity.class);
		v.getContext().startActivity(i);

	}

	/**
	 * EXIBE NOTIFICACAO
	 * @param Context context
	 * @param CharSequence mensagemBarraStatus
	 * @param CharSequence titulo
	 * @param CharSequence mensagem
	 * @param Class activity
	 */
	protected void criarNotificacao(Context context, CharSequence mensagemBarraStatus,
			CharSequence titulo, CharSequence mensagem, Class activity) {

		AsyncTask<Void, Void, Void> async = new AsyncTask<Void, Void, Void>(){

			@Override
			protected Void doInBackground(Void... params) {
				// TODO Auto-generated method stub
				return null;
			}

		};
		// Recupera o servico do NotificationManager
		NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

		Notification n = new Notification(R.drawable.symbol_trueone, mensagemBarraStatus, System.currentTimeMillis());

		Log.i("teste","Time in millis: " + String.valueOf(System.currentTimeMillis()));

		// PendingIntent para executar a Activity se o usu?rio selecionar a notifica??o
		//TESTE PARA TRANSPORTAR MAP
		Intent notificationsIntent = new Intent(this, NewsActivity.class);
		notificationsIntent.putExtra("user", u);
		Log.i("teste", "ENVIANDO QUANTIDADE: " + String.valueOf(qtdNova));
		Bundle bundle = new Bundle();
		bundle.putInt(MainActivity.QTQ_NOVA, qtdNova);
		bundle.putSerializable(OfferNewsIntentService.PARAM_OUT_MAP_NEWS, (Serializable) newsMap);
		notificationsIntent.putExtras(bundle);

		//PendingIntent p = PendingIntent.getActivity(this, 0, new Intent(this, activity), 0);
		PendingIntent p = PendingIntent.getActivity(this, 0, notificationsIntent, 0);

		// Flag utilizada para remover a notifica??o da barra de status
		// quando o usu?rio clicar nela
		n.flags |= Notification.FLAG_AUTO_CANCEL;

		// Informa??es
		n.setLatestEventInfo(this, titulo, mensagem, p);

		// Espera 100ms e vibra por 250ms, espera por mais 100ms e vibra por 500ms
		n.vibrate = new long[] { 100, 250, 100, 500 };

		// id da notificacao
		nm.notify(R.string.app_name, n);
	}

	/**
	 * DISPARA A TASK PARA AS NOTIFICA����ES
	 * 
	 */
	public void testeTimer(){

		//RECUPERA A CONFIGURACAO DAS PREFERENCIAS DO USUARIO
		SharedPreferences settings = getSharedPreferences(LoginActivity.PREFS_USER, 0);
		int frequency = settings.getInt(ConfigActvity.FREQUENCY_NOTIFICATION, 0);

		Log.i("teste", "Frequencia " + String.valueOf(frequency));
		/** 
		 * DEFAULT
		 * CASO SEJA '3' O APLICATIVO IR�� DISPARAR A TASK PARA EXECUTAR 
		 * A NOTIFICACAO DE 1 EM 1 HORA
		 */
		//1 - UMA VEZ POR HORA
		if(frequency == 0){

			//CANCELANDO OUTROS TIMERS CASO ALGUM DELES ESTEJA RODANDO
			timerForDay.purge();
			timerForWeek.purge();

			SharedPreferences.Editor editor = settings.edit();		
			editor.putInt(ConfigActvity.PREVIOUS_FREQUENCY, frequency);

			TimerTask task = new TimerTask() {

				@Override
				public void run() {
					//criarNotificacao(MainActivity.this, "Voc�� tem notifica����es novas!!!", "Novidades", "Veja as ultimas novidades do TrueOne!", TesteViews2.class);
					//criarNotificacao(MainActivity.this, "Voc�� tem notifica����es novas!!!", "TrueOne", "Voc�� tem novas notifica����es.", TesteViews2.class);
					Log.i("teste" , "FREQUENCIA 0");

				}
			};

			long interval = 1000 * 60 * 60;
			timerForHour.scheduleAtFixedRate(task, interval, interval);

		}
		/**
		 * CASO SEJA '1' O APLICATIVO IR�� DISPARAR A TASK PARA EXECUTAR
		 * A NOTIFICACAO APENAS UMA HORA POR DIA
		 * ��S 13H
		 * 
		 */
		//2 - UMA VEZ POR DIA
		else if(frequency == 1){

			//CANCELANDO OUTROS TIMERS CASO ALGUM DELES ESTEJA RODANDO
			timerForHour.purge();
			timerForWeek.purge();

			SharedPreferences.Editor editor = settings.edit();		
			editor.putInt(ConfigActvity.PREVIOUS_FREQUENCY, frequency);

			Calendar calendar = Calendar.getInstance();  
			calendar.set(Calendar.HOUR_OF_DAY, 16);  
			calendar.set(Calendar.MINUTE, 00);  
			calendar.set(Calendar.SECOND, 0);  
			Date time = calendar.getTime();  

			TimerTask task = new TimerTask() {

				@Override
				public void run() {
					criarNotificacao(MainActivity.this, "Voc�� tem notifica����es novas!!!", "TrueOne", "Voc�� tem novas notifica����es.", TesteViews2.class);
					Log.i("teste", "FREQUENCIA 1");
				}
			};
			long interval = 1000 * 60 * 1440;
			timerForDay.scheduleAtFixedRate(task, interval, interval);
			//timerForDay.schedule(task, time); 
		}
		/**
		 * 
		 * CASO SEJA '2' O APLICATIVO IR�� DISPARAR A TASK PARA EXECUTAR 
		 * A NOTIFICACAO APENAS UMA VEZ POR SEMANA
		 * SER�� DISPARAR
		 *  
		 */
		//3 - UMA VEZ POR SEMANA
		else if(frequency == 2){

			//CANCELANDO OUTROS TIMERS CASO ALGUM DELES ESTEJA RODANDO
			timerForDay.cancel();
			timerForHour.cancel();

			//salva o previous frequency com o n��mero atual
			SharedPreferences.Editor editor = settings.edit();		
			editor.putInt(ConfigActvity.PREVIOUS_FREQUENCY, frequency);

			TimerTask taskWeek = new TimerTask() {

				@Override
				public void run() {
					criarNotificacao(MainActivity.this, "Voc�� tem notifica����es novas!!!", "TrueOne", "Voc�� tem novas notifica����es.", TesteViews2.class);
				}
			};

			long interval = 1000 * 60 * 10080;
			timerForWeek.scheduleAtFixedRate(taskWeek, interval, interval);
		}
	}

	public void executeNotificationsTask(){

		Timer timer = new Timer();

		TimerTask task = new TimerTask() {

			@Override
			public void run() {
				testeTimer();
			}
		};
	}

	/**
	 * Quando h�� clique no bot��o voltar a aplica��ao finaliza, para n��o voltar para a tela de login
	 */
	//	@Override
	//	public void onBackPressed() {
	//	}
}
