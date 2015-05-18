package com.accential.trueone;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.accential.trueone.adapter.SignaturesLandAdapter;
import com.accential.trueone.bean.CompaniesUser;
import com.accential.trueone.bean.Company;
import com.accential.trueone.bean.Offer;
import com.accential.trueone.bean.User;
import com.accential.trueone.bo.CompaniesUserBO;
import com.accential.trueone.bo.OfferBO;
import com.accential.trueone.bo.UserBO;
import com.accential.trueone.service.CompaniesInvitationsService;
import com.accential.trueone.service.OfferNewsIntentService;
import com.accential.trueone.service.SignaturesIntentService;
import com.accential.trueone.service.SignaturesSearchIntentService;
import com.accential.trueone.utils.MenuUtil;
import com.accential.trueone.utils.OrientacaoUtils;
import com.example.slide.R;

@SuppressWarnings("all")
public class SignaturesActivity extends Activity{

	private ListView lista;
	private EditText searchCompName;
	private SignaturesLandAdapter adapter;
	private List<CompaniesUser> comps;
	private TextView textQtd;
	private int id;
	//USUARIO
	private User user;
	private TextView textViewNameUser;
	private Offer offer;
	//MENU
	private ImageView imageMenuWish;
	private TextView textMenuWish;
	private ImageView imageMenuCompras;
	private TextView textMenuCompras;
	private ImageView imageMenuAssinaturas;
	private TextView textMenuAssinaturas;
	private ImageView imageMenuDashboard;
	private TextView textMenuDashboard;
	private TextView textMenuOferta;
	private ImageView imageMenuOferta;
	//
	private boolean logado;
	private WebView loader;
	private List<CompaniesUser> comps2;
	private List<CompaniesUser> comp3;

	private SignaturesResponseReceiver receiver;
	private SignaturesSearchResponseReceiver receiver2;
	//USER MENU
	private Spinner spinnerMenu;
	private TextView qtdAvisoNovidade;
	private Map<String, Object> newsMap;
	private ImageView newsImg;

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);								
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_signatures);	

		if(savedInstanceState != null){
			id = savedInstanceState.getInt("id");
		}

		//DETALHE DESSE CODIGO NO LoginActivity
		String type = getResources().getString(R.string.isTablet);
		if(type.equals("false")){
			Log.i("TESTE", "IS TABLET= " + type);
			OrientacaoUtils.setOrientationVertical(this);
		}

		loader = (WebView) findViewById(R.id.webViewLoader);
		textViewNameUser = (TextView) findViewById(R.id.textViewHeader_NomeUsuario);
		lista = (ListView) findViewById(R.id.listView_listaAssinaturas);
		searchCompName = (EditText) findViewById(R.id.editText_searchByTitle);
		textQtd = (TextView) findViewById(R.id.textView_qtdCompanies);

		//	menu
		imageMenuWish = (ImageView) findViewById(R.id.imageFooterWish);
		textMenuWish = (TextView) findViewById(R.id.textViewFooterWish);
		imageMenuCompras = (ImageView) findViewById(R.id.imageFooterCompras);
		textMenuCompras = (TextView) findViewById(R.id.textViewFooterCompras);
		imageMenuAssinaturas = (ImageView) findViewById(R.id.imageFooterAssinaturas);
		textMenuAssinaturas = (TextView) findViewById(R.id.textViewFooterAssinaturas);
		imageMenuDashboard = (ImageView) findViewById(R.id.imageFooterInvitations);
		imageMenuOferta = (ImageView) findViewById(R.id.imageFooterDashboard);
		textMenuOferta = (TextView) findViewById(R.id.textViewFooterDash);
		textMenuDashboard = (TextView) findViewById(R.id.textViewFooterInvitations);
		newsImg = (ImageView) findViewById(R.id.imageView_avisoConvite);

		logado = true;

		imageMenuDashboard.setImageResource(R.drawable.dashboard_off);
		imageMenuAssinaturas.setImageResource(R.drawable.assinaturas_on);
		imageMenuWish.setImageResource(R.drawable.wishlist_off);
		imageMenuCompras.setImageResource(R.drawable.compras_off);

		qtdAvisoNovidade = (TextView) findViewById(R.id.textView_qtdConvites);

		textMenuAssinaturas.setTextColor(Color.rgb(255, 117, 24));
		textMenuWish.setTextColor(Color.rgb(105, 105, 105));
		textMenuCompras.setTextColor(Color.rgb(105, 105, 105));
		textMenuDashboard.setTextColor(Color.rgb(105, 105, 105));

		User u = null;
		if(getIntent().getSerializableExtra("user") != null){
			u = (User) getIntent().getSerializableExtra("user");
		}else {
			u = UserBO.searchById(id);
		}

		id = u.getId();
		user = u;

		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();

		newsMap = (Map<String, Object>) bundle.getSerializable(CompaniesInvitationsService.PARAM_OUT_INVITATIONS);
		qtdAvisoNovidade.setText(String.valueOf(newsMap.get(OfferNewsIntentService.MAP_TOTAL_QTD)));

		//MENU <! -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -->
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
					Intent intent = MenuUtil.toPreferences(view.getContext(), user, newsMap);
					startActivity(intent);
				}
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
		//<!-- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -->

		loader.loadUrl("https://www.baltimorecity.gov/Portals/_default/Skins/EnterpriseUI/images/icons/loading.gif");

		//STARTANDO SERVICE
		Intent msgIntent = new Intent(SignaturesActivity.this, SignaturesIntentService.class);
		msgIntent.putExtra(SignaturesIntentService.PARAM_IN_USER_ID, id);
		Log.i("START NO SERVICE", "SERVICO STARTADO");
		startService(msgIntent);

		IntentFilter filter = new IntentFilter(SignaturesResponseReceiver.ACTION_RESP_SIGNATURE);
		filter.addCategory(Intent.CATEGORY_DEFAULT);
		receiver = new SignaturesResponseReceiver();
		registerReceiver(receiver, filter);

		//NOME DO USUARIO
		textViewNameUser.setText(user.getName());

		Log.i("ID DO USUARIO", String.valueOf(user.getId()));

		//ONCLICK DA LISTA
		lista.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapter, View view, int pos,
					long arg3) {
				final CompaniesUser comp = (CompaniesUser)	adapter.getItemAtPosition(pos);
				ImageView imgRemove = (ImageView) view.findViewById(R.id.imageView_botaoDelete);
				TextView txtVerOfertas = (TextView) view.findViewById(R.id.textView2);

				final int position = pos;

				txtVerOfertas.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						Log.i("TEXT CLICK", "CLICK NO TEXTO");
						offersByCompany(comp.getCompany().getId());
					}
				});

				Log.i("TESTE", "POSICAO: " + String.valueOf(position));

				//ONCLICK DO ITEM 'REMOVE' DA IMAGEM
				imgRemove.setOnClickListener( new OnClickListener() {
					@Override
					public void onClick(View arg0) {

						Log.i("TESTE", "POSICAO: " + String.valueOf(position));

						//Log.i("TEST DO SEGUNDO CLIQUE", "POSITION: " + String.valueOf(position) + 
						//" ID COMPANY - " + String.valueOf(comps.get(position).getId()));

						CompaniesUserBO.inactiveCompsUser(comps.get(position).getId());
						Log.i("CLIQUE NO BOTAO REMOVE", String.valueOf(comps.get(position).getId()));
						atualizaLista();
					}
				});
			}
		});

		//CLIQUE NOS ITENS DO MENU
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
				parameters.putSerializable(CompaniesInvitationsService.PARAM_OUT_INVITATIONS, (Serializable) newsMap);
				intent.putExtra("user", user);
				parameters.putBoolean("logado", logado);
				Log.i("nome do user", user.getName());
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
		//4 - DASHBOARD
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

		//SELECIONA ITENS DO MENU
		//5 - OFERTA
		textMenuOferta.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				imageMenuWish.setImageResource(R.drawable.wishlist_off);
				imageMenuAssinaturas.setImageResource(R.drawable.assinaturas_off);
				imageMenuCompras.setImageResource(R.drawable.compras_off);
				imageMenuDashboard.setImageResource(R.drawable.dashboard_off);
				imageMenuOferta.setImageResource(R.drawable.ofertas_on);

				textMenuAssinaturas.setTextColor(Color.rgb(105, 105, 105));
				textMenuWish.setTextColor(Color.rgb(105, 105, 105));
				textMenuDashboard.setTextColor(Color.rgb(105, 105, 105));
				textMenuCompras.setTextColor(Color.rgb(105, 105, 105));
				textMenuOferta.setTextColor(Color.rgb(255, 117, 24));

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
				Intent intentNews = new Intent(SignaturesActivity.this, NewsActivity.class);
				intentNews.putExtra("user", user);
				Bundle bundle = new Bundle();
				bundle.putSerializable(OfferNewsIntentService.PARAM_OUT_MAP_NEWS, (Serializable) newsMap);
				intentNews.putExtras(bundle);
				startActivity(intentNews);
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	//REALIZA PEQUISA POR NOME DIGITADO POR USUARIO
	public void searchComps(View v){

		lista.setVisibility(View.GONE);
		loader.setVisibility(View.VISIBLE);

		String compName = searchCompName.getText().toString();

		execultaServiceTest(compName);

		Log.i("teste", "TITULO PESQUISADO: " +  compName);

		//final List<CompaniesUser> comps2 = CompaniesUserBO.searchByName(compName);

		//Log.i("teste", "TAMANHO DA LISTA PESQUISADA: " + String.valueOf(comps2.size()));
		//adapter = new SignaturesLandAdapter(this, comps2);
		//lista.setAdapter(adapter);

		//comp3 = CompaniesUserBO.returnObjCompaniesUserNoFilter(user.getId());

		//Log.i("tamanho da lista", String.valueOf(comp3.size()));

		//ONCLICK DA LISTA
		lista.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapter, View view, int pos,
					long arg3) {
				final CompaniesUser comp = (CompaniesUser)	adapter.getItemAtPosition(pos);
				ImageView imgView = (ImageView) view.findViewById(R.id.imageView_botaoAdd);
				ImageView imgRemove = (ImageView) view.findViewById(R.id.imageView_botaoDelete);
				final int position = pos;

				//ONCLICK DO ITEM 'ADD' DA IMAGEM
				imgView.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						int i = 0;
						int id = 0;
						for (CompaniesUser companies : comp3) {
							if(companies.getCompany().getId() == comps2.get(position).getCompany().getId()){
								i = 1;
								id = companies.getId();
								Toast.makeText(SignaturesActivity.this, "VOC�� EST�� SEGUINDO ESTA EMPRESA", Toast.LENGTH_LONG).show();
								Log.i("SAVE EXISTENTE", "EDITOU EXISTENTE");
								atualizaLista();
							}
						}
						if(i == 1){
							CompaniesUserBO.activateCompsUser(id);
						}else{
							User user = new User();
							user.setId(user.getId());

							Company comp = new Company();
							comp.setId(comps2.get(position).getCompany().getId());

							Calendar dateNow = Calendar.getInstance();

							CompaniesUser compU = new CompaniesUser();
							compU.setCompany(comp);
							compU.setUser(user);
							compU.setDateRegister(dateNow);

							CompaniesUserBO.saveMyCompaniesUser(compU);
							Toast.makeText(SignaturesActivity.this, "VOC�� EST�� SEGUINDO ESTA EMPRESA", Toast.LENGTH_LONG).show();

							Log.i("SAVE NEW", "CRIOU NOVO REGISTRO");
							atualizaLista();
						}
					}
				});
				//ONCLICK DO ITEM 'REMOVE' DA IMAGEM
				imgRemove.setOnClickListener( new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						int i = 0;
						int id = 0;
						for (CompaniesUser companies : comps) {
							if(companies.getCompany().getId() == comps2.get(position).getCompany().getId()){
								i = 1;
								id = companies.getId();
							}
						}
						if(i == 1){
							CompaniesUserBO.inactiveCompsUser(id);
							Toast.makeText(SignaturesActivity.this, "VOC�� DEIXOU DE SEGUIR ESTA EMPRESA", Toast.LENGTH_LONG).show();
							atualizaLista();
						}
					}
				});
			}
		});
	}

	public void atualizaLista(){

		Intent msgIntent = new Intent(SignaturesActivity.this, SignaturesIntentService.class);
		msgIntent.putExtra(SignaturesIntentService.PARAM_IN_USER_ID, id);
		Log.i("START NO SERVICE", "SERVICO STARTADO");
		startService(msgIntent);

		IntentFilter filter = new IntentFilter(SignaturesResponseReceiver.ACTION_RESP_SIGNATURE);
		filter.addCategory(Intent.CATEGORY_DEFAULT);
		receiver = new SignaturesResponseReceiver();
		registerReceiver(receiver, filter);
	}

	@Override  
	protected void onSaveInstanceState(Bundle outState) {  
		super.onSaveInstanceState(outState);  
		outState.putInt("id", id);
	}  

	public void offersByCompany(int id){

		Log.i("TESTE CLICK DO TEXTO", "CLIQUEI NO TEXTO");

		final List<Offer> offers = OfferBO.listOffersByCompany(id);
		String[] teste = new String[offers.size()];
		int i = 0;
		for (Offer offer : offers) {
			teste[i] = offer.getTitle();
			i++;
		}

		String nome = "OFERTA4";

		CharSequence[] items = teste;

		//DADOS QUE LEVAM PARA TELA DE DETALHES DA OFERTA
		final Intent intent = new Intent(this, OfferDetail.class);
		Bundle parameters = new Bundle();

		Log.i("IMPRIME ID", String.valueOf(id));
		parameters.putInt("companyId", id);
		intent.putExtra("user", user);
		boolean logado = true;
		parameters.putBoolean("logado", logado);
		intent.putExtras(parameters);

		if(!offers.isEmpty()){
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("Clique na oferta para ver detalhes!");
			builder.setItems(items, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int item) {
					Log.i("Position do item", String.valueOf(item));
					offer = offers.get(item);
					intent.putExtra("offer", offer);
					Log.i("TESTE DA OFERTA", "O id da oferta �� " + String.valueOf(offer.getId()) + "; O titulo �� " + offer.getTitle());

					// FAZ TRANSA����O PARA TELA DE DETALHES DA OFERTA 
					startActivity(intent);
				}
			});
			AlertDialog alert = builder.create();
			alert.show();
		}else{
			Toast.makeText(this, "Essa empresa n��o possui ofertas v��lidas no momento!", Toast.LENGTH_LONG).show();
		}

	}

	public class SignaturesResponseReceiver extends BroadcastReceiver {
		public static final String ACTION_RESP_SIGNATURE =    
				"com.mamlambo.intent.action.MESSAGE_PROCESSED_SIGNATURE";

		@Override
		public void onReceive(Context context, Intent intent) {

			Bundle parameters = intent.getExtras();
			comps = (List<CompaniesUser>) parameters.getSerializable(SignaturesIntentService.PARAM_OUT_LIST_SIGNATURE);
			Log.i("TESTE", "TAMANHO DA LISTA DE ASSINATURAS - SERVICE: " +  String.valueOf(comps.size()));

			adapter = new SignaturesLandAdapter(SignaturesActivity.this, comps);
			lista.setAdapter(adapter);

			if(comps.size() == 0){
				textQtd.setText("Voc�� n��o �� assinante de nenhuma empresa.");
			}else{
				textQtd.setText("Voc�� est�� recebendo ofertas de ".concat(String.valueOf(comps.size())).concat(" empresa(s)."));
			}
			loader.setVisibility(View.GONE);
		}
	}

	public void execultaServiceTest(String searchTitle){

		Intent msgIntent = new Intent(SignaturesActivity.this, SignaturesSearchIntentService.class);
		msgIntent.putExtra(SignaturesSearchIntentService.PARAM_IN_TITLE, searchTitle);
		msgIntent.putExtra(SignaturesSearchIntentService.PARAM_IN_USER_ID, user.getId());
		Log.i("START NO SERVICE", "SERVICO STARTADO");

		startService(msgIntent);

		IntentFilter filter = new IntentFilter(SignaturesSearchResponseReceiver.ACTION_RESP_SIGNATURE_SEARCH);
		filter.addCategory(Intent.CATEGORY_DEFAULT);
		receiver2 = new SignaturesSearchResponseReceiver();
		registerReceiver(receiver2, filter);
	}

	public class SignaturesSearchResponseReceiver extends BroadcastReceiver {
		public static final String ACTION_RESP_SIGNATURE_SEARCH =    
				"com.mamlambo.intent.action.MESSAGE_PROCESSED_SIGNATURE_SEARCH";
		@Override
		public void onReceive(Context context, Intent intent) {
			Bundle parameters = intent.getExtras();

			comps2 = (List<CompaniesUser>) parameters.getSerializable(SignaturesSearchIntentService.PARAM_OUT_COMP_LIST);
			comp3 = (List<CompaniesUser>) parameters.getSerializable(SignaturesSearchIntentService.PARAM_OUT_SIGNED_COMPS);

			Log.i("TESTE", "TAMANHO DA LISTA DE ASSINATURAS - SERVICE execult: " +  String.valueOf(comps2.size()));
			Log.i("TESTE", "TAMANHO DA LISTA COMPANIAS ASSINADAS - SERVICE execult: " + String.valueOf(comp3.size()));

			adapter = new SignaturesLandAdapter(SignaturesActivity.this, comps2);
			lista.setAdapter(adapter);

			textQtd.setTag(String.valueOf(comps2.size())+" empresa(s) de retorno.");

			loader.setVisibility(View.GONE);
			lista.setVisibility(View.VISIBLE);
		}
	}
}
