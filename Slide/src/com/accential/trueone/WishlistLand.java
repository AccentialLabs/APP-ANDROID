package com.accential.trueone;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
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
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.accential.trueone.adapter.WishlistLandAdapter;
import com.accential.trueone.bean.CompanyCategory;
import com.accential.trueone.bean.CompanySubCategory;
import com.accential.trueone.bean.User;
import com.accential.trueone.bean.Wishlist;
import com.accential.trueone.bo.CompanySubCategoryBO;
import com.accential.trueone.bo.UserBO;
import com.accential.trueone.bo.WishlistBO;
import com.accential.trueone.service.CompaniesInvitationsService;
import com.accential.trueone.service.OfferNewsIntentService;
import com.accential.trueone.service.SimpleIntentServiceWish;
import com.accential.trueone.utils.Mask;
import com.accential.trueone.utils.MenuUtil;
import com.accential.trueone.utils.OrientacaoUtils;
import com.example.slide.R;

@SuppressWarnings("all")
public class WishlistLand extends Activity{

	private ListView lista;
	private Spinner spinnerCategory;
	private Spinner spinnerSubCategory;
	private EditText textViewNomeDoProduto;
	private EditText textViewEndsData;
	private EditText textViewDescription;
	private CompanyCategory category;
	private CompanySubCategory subCategory;
	private Wishlist wishlist;
	private AlertDialog alerta;
	private int id;
	private TableRow tableCreateWish;

	private User user;

	//VIEWS - CRIAR WISHLIST
	private View view1;
	private View view2;
	private View view3;
	private View view4;
	private View view5;
	private ImageView backView;
	private ImageView btnView;
	private WebView wbvLoader;

	private WishsResponseReceiver receiver;
	private List<CompanyCategory> categoriasTeste;
	private List<Wishlist> wishies;
	private Map qtdOFfer;
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
	//USER
	private TextView textViewNameUser;
	private boolean logado;
	//AVISOS
	//private ImageView imgAvisoBandeira;
	//private TextView qtdAvisoNovidade;
	private int qtdsNova;
	//USER MENU
	private Spinner spinnerMenu;
	private TextView qtdAvisoNovidade;
	private ImageView newsImg;
	private Map<String, Object> newsMap;


	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);								
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_wishlist);

		//DETALHE DESSE CODIGO NO LoginActivity
		String type = getResources().getString(R.string.isTablet);
		if(type.equals("false")){
			Log.i("TESTE", "IS TABLET= " + type);
			OrientacaoUtils.setOrientationVertical(this);
		}
		//--

		if(savedInstanceState != null){
			id = savedInstanceState.getInt("id");
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
		imageMenuOferta = (ImageView) findViewById(R.id.imageFooterDashboard);
		textMenuOferta = (TextView) findViewById(R.id.textViewFooterDash);

		//ITEM DO USUARIO
		textViewNameUser = (TextView) findViewById(R.id.textViewHeader_NomeUsuario);

		wbvLoader = (WebView) findViewById(R.id.webView_loading);
		tableCreateWish = (TableRow) findViewById(R.id.tableRow1);
		lista = (ListView) findViewById(R.id.listView_listaWishlist);
		spinnerCategory = (Spinner) findViewById(R.id.spinner_category);
		spinnerSubCategory = (Spinner) findViewById(R.id.spinner_subCategory);
		textViewNomeDoProduto = (EditText) findViewById(R.id.editText_productName);
		textViewEndsData = (EditText) findViewById(R.id.editText2);
		textViewDescription = (EditText) findViewById(R.id.editText_coment);
		newsImg = (ImageView) findViewById(R.id.imageView_avisoConvite);
		qtdAvisoNovidade = (TextView) findViewById(R.id.textView_qtdConvites);
		//imgAvisoBandeira = (ImageView) findViewById(R.id.imageView_avisoBandeira);
		//qtdAvisoNovidade = (TextView) findViewById(R.id.textView_qtdAvisos);

		imageMenuWish.setImageResource(R.drawable.wishlist_on);
		textMenuWish.setTextColor(Color.rgb(255, 117, 24));
		wbvLoader.loadUrl("https://www.baltimorecity.gov/Portals/_default/Skins/EnterpriseUI/images/icons/loading.gif");
		logado = true;
		//INSERINDO MASCARA
		textViewEndsData.addTextChangedListener(Mask.insert("##/##/####", textViewEndsData));

		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		int i = bundle.getInt(MainActivity.QTQ_NOVA);
		Log.i("teste", "QUANTIDADE PASSADA PELO INTENT - BUNDLE: " + String.valueOf(i));
		qtdAvisoNovidade.setText(String.valueOf(i));

		newsMap = (Map<String, Object>) bundle.getSerializable(CompaniesInvitationsService.PARAM_OUT_INVITATIONS);

		User u = null;
		if(getIntent().getSerializableExtra("user") != null){
			u = (User) getIntent().getSerializableExtra("user");
		}else {
			u = UserBO.searchById(id);
		}

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
					Intent intent = MenuUtil.toPreferences(view.getContext(), user, newsMap);
					startActivity(intent);
				}
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});


		id = u.getId();
		user = u;
		//RECEBE NUM DE QUANTIDADES DE NOVIDADES DA TELA ANTERIOR

		textViewNameUser.setText(user.getName());

		//START SERVICE
		AsyncTask<Void, Void, Void> async = new AsyncTask<Void, Void, Void>(){
			@Override
			protected Void doInBackground(Void... params) {

				Intent msgIntent = new Intent(WishlistLand.this, SimpleIntentServiceWish.class);
				msgIntent.putExtra(SimpleIntentServiceWish.PARAM_IN_USER_ID, id);
				Log.i("START NO SERVICE", "SERVICO STARTADO - ");
				startService(msgIntent);

				IntentFilter filter = new IntentFilter(WishsResponseReceiver.ACTION_RESPOSTA);
				filter.addCategory(Intent.CATEGORY_DEFAULT);
				receiver = new WishsResponseReceiver();
				registerReceiver(receiver, filter);
				return null;

			}
		};

		try {
			async.execute().get();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//CLIQUE NO DESEJO DA LISTA
		lista.setOnItemClickListener( new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

				final Wishlist wish = wishies.get(arg2);

				if(!qtdOFfer.get(wish.getName()).equals(0)){

					//EXIBE O DIALOG PARA EXCLUSAO 
					AlertDialog.Builder builder = new AlertDialog.Builder(arg1.getContext());
					builder.setTitle("Ver ofertas");
					builder.setMessage("Deseja ver as ofertas relacionadas a ".concat(wish.getName()).concat(" ?"));
					builder.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface arg0, int arg1) {

							Intent offsIntent = new Intent(WishlistLand.this, OffersWishlist.class);
							Bundle bundle = new Bundle();
							bundle.putInt("idWish", wish.getId());
							bundle.putString("nameWish", wish.getName());
							bundle.putSerializable(OfferNewsIntentService.PARAM_OUT_MAP_NEWS, (Serializable) newsMap);
							offsIntent.putExtra(MainActivity.QTQ_NOVA, qtdsNova);
							offsIntent.putExtra("user", user);
							offsIntent.putExtras(bundle);
							startActivity(offsIntent);

						}
					});
					builder.setNegativeButton("N��O", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface arg0, int arg1) {
						} });
					alerta = builder.create();
					alerta.show();

					//----------------------------------------
					//					Intent offsIntent = new Intent(WishlistLand.this, OffersWishlist.class);
					//					Bundle bundle = new Bundle();
					//					bundle.putInt("idWish", wish.getId());
					//					bundle.putString("nameWish", wish.getName());
					//					offsIntent.putExtra("user", user);
					//					offsIntent.putExtras(bundle);
					//					startActivity(offsIntent);
				}else{
					Toast.makeText(WishlistLand.this, "N��o existe oferta cadastrada para esse desejo!", Toast.LENGTH_LONG).show();
				}
			}
		});

		hideView();

		//TESTE CLIQUE NO TABLEROW - CRIAR WISHLIST
		tableCreateWish.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//	SE ESTIVER ESCONDIDO
				if(backView.getVisibility() == View.GONE)
				{
					showView();

					//CARREGA LISTA DE CATEGORY
					final Map<String, Map<String, Map<String, String>>> key = new HashMap<String,Map<String, Map<String, String>>>();
					final Map<String,Map<String,String>> params = new HashMap<String,Map<String,String>>();
					final Map<String,String> conditions = new HashMap<String,String>();

					params.put("conditions", conditions);
					key.put("CompaniesCategory", params);

					//										GRA��AS AO METODO 'toString'IMPLEMENTADO NA BEAN CompanyCategory PODEMOS CARREGOR O SPINNER COM UMA LISTA DE OBJETOS
					//										List<CompanyCategory> categories = CompanyCategoryBO.listAllCategories(key);
					//										ArrayAdapter<CompanyCategory> categoryAdapter = new 
					//												ArrayAdapter<CompanyCategory>(WishlistLand.this, android.R.layout.simple_spinner_item, categories);
					//										spinnerCategory.setAdapter(categoryAdapter);

					spinnerCategory.setOnItemSelectedListener(new OnItemSelectedListener() {
						@Override
						public void onItemSelected(AdapterView<?> parent, View view,
								int position, long arg3) {

							//VARIAVEL DE CATEGORIA RECEBE O OBJ DA LISTA SELECIONADO PELO USUARIO
							category  = (CompanyCategory) parent.getSelectedItem();
							Log.i("CATEGOTY PARENT", "Lets search category");
							Log.i("CATEGORY PARENT", category.getName());

							conditions.put("CompaniesSubCategory.category_id", String.valueOf(category.getId()));
							params.put("conditions", conditions);
							key.put("CompaniesSubCategory", params);

							//CRIANDO LISTA E ADAPTER DE SUBCATEGORIA - 
							//O CONTEUDO DA LISTA SER�� SEGUNDO O C��DIGO ENVIADO PELA LISTA DE CATEGORIA
							List<CompanySubCategory> subCategories = CompanySubCategoryBO.listAllCategories(key);
							ArrayAdapter<CompanySubCategory> subCategoryAdapter = new
									ArrayAdapter<CompanySubCategory>(WishlistLand.this, android.R.layout.simple_spinner_item, subCategories);
							spinnerSubCategory.setAdapter(subCategoryAdapter);

							/**
							 * OnSelected da Lista de SUBCATEGORY
							 */
							spinnerSubCategory.setOnItemSelectedListener(new OnItemSelectedListener() {

								@Override
								public void onItemSelected(AdapterView<?> parent, View view,
										int position, long arg3) {

									subCategory = (CompanySubCategory) parent.getSelectedItem();
									Log.i("SUBCATEGORY PARENT", subCategory.getName());
								}

								@Override
								public void onNothingSelected(AdapterView<?> arg0) {
								}
							});
						}
						@Override
						public void onNothingSelected(AdapterView<?> arg0) {
						}
					});

				}
				else
					//SE ESTIVER �� MOSTRA
					if(backView.getVisibility() == View.VISIBLE)
					{
						hideView();
					}
			}
		});

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
				intentD.putExtras(bundle);
				intentD.putExtra("usuario", user);
				startActivity(intentD);
			}
		});

		newsImg.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intentNews = new Intent(WishlistLand.this, NewsActivity.class);
				intentNews.putExtra("user", user);
				Bundle bundle = new Bundle();
				bundle.putSerializable(OfferNewsIntentService.PARAM_OUT_MAP_NEWS, (Serializable) newsMap);
				intentNews.putExtras(bundle);
				startActivity(intentNews);
			}
		});

		/**
		 * CLIQUE NA BANDEIRA DE NOVIDADES
		 */
		//NOVIDADES  
		//		imgAvisoBandeira.setOnClickListener(new OnClickListener() {
		//
		//			@Override
		//			public void onClick(View v) {
		//
		//				//EXIBE O DIALOG PARA VER LISTA DE NOVIDADES
		//				AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
		//				builder.setTitle("Ver Novidades");
		//				builder.setMessage("Deseja ver a lista de novas ofertas?");
		//				builder.setPositiveButton("VER", new DialogInterface.OnClickListener() {
		//					public void onClick(DialogInterface arg0, int arg1) {
		//
		//						Intent intentD = new Intent(WishlistLand.this, MainActivity.class);
		//						Bundle bundle = new  Bundle();
		//						bundle.putBoolean("logado", true);
		//						intentD.putExtras(bundle);
		//						intentD.putExtra("usuario", user);
		//						startActivity(intentD);
		//					}
		//				});
		//				builder.setNegativeButton("N��O", new DialogInterface.OnClickListener() {
		//					public void onClick(DialogInterface arg0, int arg1) {
		//					} });
		//				alerta = builder.create();
		//				alerta.show();
		//
		//			}
		//		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	/**
	 * Salva desejo
	 * @param view
	 */
	public void saveWishlist(View v){

		wishlist = new Wishlist();

		String produto = textViewNomeDoProduto.getText().toString();
		String descricao = textViewDescription.getText().toString();

		category = (CompanyCategory) spinnerCategory.getSelectedItem();
		subCategory = (CompanySubCategory) spinnerSubCategory.getSelectedItem();

		Calendar dateNow = Calendar.getInstance();
		Calendar dateLimit = Calendar.getInstance();

		SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");

		String dataFinal = textViewEndsData.getText().toString();
		Date date = null;
		try {
			date = formatador.parse(dataFinal);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		dateLimit.setTime(date);

		wishlist.setCategory(category);
		wishlist.setSubCategory(subCategory);
		wishlist.setUser(user);
		wishlist.setName(produto);
		wishlist.setDescription(descricao);
		wishlist.setDateRegister(dateNow);
		wishlist.setEndsAt(dateLimit);

		WishlistBO.saveMyWishlist(wishlist);

		atualizaListaWish();

		Log.i("TESTE SALVA WISH", "SAIU DO METODO");
	}

	public void atualizaListaWish(){
		List<Wishlist> wishies = WishlistBO.retornaObj(user.getId());
		WishlistLandAdapter adapter = new WishlistLandAdapter(this, wishies, qtdOFfer);
		lista.setAdapter(adapter);
	}

	/**
	 * Salva as informa����es do usuario caso haja reconstrucao na Activity
	 */
	@Override  
	protected void onSaveInstanceState(Bundle outState) {  
		super.onSaveInstanceState(outState);  
		outState.putInt("id", id);
	}  

	//ESCONDE VIEWS DE 'CRIAR WISHLIST'
	private void hideView(){

		//ATRIBUI
		view1 = findViewById(R.id.view1);
		view2 = findViewById(R.id.view2);
		view3 = findViewById(R.id.view3);
		view4 = findViewById(R.id.view4);
		view5 = findViewById(R.id.view5);
		backView = (ImageView) findViewById(R.id.imageView1);
		btnView = (ImageView) findViewById(R.id.imageView_btnCadastrar);

		//ESCONDE
		view1.setVisibility(View.GONE);
		view2.setVisibility(View.GONE);
		view3.setVisibility(View.GONE);
		view4.setVisibility(View.GONE);
		view5.setVisibility(View.GONE);
		spinnerCategory.setVisibility(View.GONE);
		spinnerSubCategory.setVisibility(View.GONE);
		textViewEndsData.setVisibility(View.GONE);
		textViewNomeDoProduto.setVisibility(View.GONE);
		textViewDescription.setVisibility(View.GONE);
		btnView.setVisibility(View.GONE);
		backView.setVisibility(View.GONE);
	}

	//MOSTRA VIEWS DE 'CRIAR WISHLIST'
	public void showView(){

		view1.setVisibility(View.VISIBLE);
		view2.setVisibility(View.VISIBLE);
		view3.setVisibility(View.VISIBLE);
		view4.setVisibility(View.VISIBLE);
		view5.setVisibility(View.VISIBLE);
		spinnerCategory.setVisibility(View.VISIBLE);
		spinnerSubCategory.setVisibility(View.VISIBLE);
		textViewEndsData.setVisibility(View.VISIBLE);
		textViewNomeDoProduto.setVisibility(View.VISIBLE);
		textViewDescription.setVisibility(View.VISIBLE);
		btnView.setVisibility(View.VISIBLE);
		backView.setVisibility(View.VISIBLE);

	}

	public class WishsResponseReceiver extends BroadcastReceiver {
		public static final String ACTION_RESPOSTA =    
				"com.mamlambo.intent.action.MESSAGE_PROCESSED_WISH";
		@Override
		public void onReceive(Context context, Intent intent) {

			Bundle parameters = intent.getExtras();

			List<CompanyCategory> categorias = (List<CompanyCategory>) parameters.getSerializable(SimpleIntentServiceWish.PARAM_OUT_CATEGORIE);
			List<Wishlist> wishies2 = (List<Wishlist>) parameters.getSerializable(SimpleIntentServiceWish.PARAM_OUT_USER_WISH);
			Map<String, Integer> map = (Map<String, Integer>) parameters.getSerializable(SimpleIntentServiceWish.PARAM_OUT_WISH_QTD_OFFER);
			qtdOFfer = map;

			Log.i("teste", "EXECUTANDO SERVICE DO WISHLISTLAND!!!");
			ArrayAdapter<CompanyCategory> categoryAdapter = new 
					ArrayAdapter<CompanyCategory>(WishlistLand.this, android.R.layout.simple_spinner_item, categorias);
			spinnerCategory.setAdapter(categoryAdapter);

			wishies = wishies2;
			Log.i("LIST WISH", "USER ID " + String.valueOf(user.getId()));
			WishlistLandAdapter adapter = new WishlistLandAdapter(WishlistLand.this, wishies, map);
			lista.setAdapter(adapter);

			wbvLoader.setVisibility(View.GONE);
		}
	}

	public void deleteWish(View v){

		int pos = (Integer) v.getTag();
		final Wishlist wish = wishies.get(pos);

		Log.i("teste", "CLIQUEI NO BOTAO EXCLUIR DE PRIMEIRA!!!");
		Log.i("teste", "O CLIQUE FOI NA POSICAO: " + String.valueOf(pos));

		//EXIBE O DIALOG PARA EXCLUSAO 
		AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
		builder.setTitle("Excluir Desejo");
		builder.setMessage("Voc�� deseja excluir o desejo ".concat(wish.getName()).concat(" ?"));
		builder.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface arg0, int arg1) {
				WishlistBO.inactiveWish(wish.getId());
				Log.i("METODO SET POSITIVE", "DESEJO DESATIVADO");
				List<Wishlist> wishies = WishlistBO.retornaObj(user.getId());
				WishlistLandAdapter adapter = new WishlistLandAdapter(WishlistLand.this, wishies, qtdOFfer);
				lista.setAdapter(adapter);
			}
		});
		builder.setNegativeButton("N��O", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface arg0, int arg1) {
			} });
		alerta = builder.create();
		alerta.show();

	}

}
