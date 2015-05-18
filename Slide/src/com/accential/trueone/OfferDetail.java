package com.accential.trueone;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DownloadManager.Request;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.service.textservice.SpellCheckerService.Session;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.accential.trueone.bean.Checkout;
import com.accential.trueone.bean.Company;
import com.accential.trueone.bean.Offer;
import com.accential.trueone.bean.User;
import com.accential.trueone.bo.CheckoutBO;
import com.accential.trueone.bo.CompanyBO;
import com.accential.trueone.dao.CheckoutDAO;
import com.accential.trueone.service.CompaniesInvitationsService;
import com.accential.trueone.service.OfferNewsIntentService;
import com.accential.trueone.utils.FiltroDeCores;
import com.accential.trueone.utils.MenuUtil;
import com.accential.trueone.utils.OrientacaoUtils;
import com.example.slide.R;
import com.loopj.android.image.SmartImageView;

//DO LAYOUT NOVO
@SuppressLint("NewApi")
@SuppressWarnings("all")
public class OfferDetail extends Activity{

	private TextView offerTitle;
	private TextView offerDescription;
	private TextView offerValue;
	private TextView offerValueWithPercent;
	private TextView offerAmountAllowed;
	private TextView offerEndsDate;
	private TextView offerNumberOfDays;
	private SmartImageView offerPhoto;
	private SmartImageView offerCompLogo;

	private TextView numEstoque;
	private ImageView btnComprar;
	private ImageView btnCompartilhar;
	private ImageView btnEnviar;
	private ImageView linha1;
	private ImageView linha2;
	private ImageView linha3;
	private ImageView linha4;
	private ImageView linha5;
	private TableRow tblStars;
	private TableRow compPhoto;
	private TextView por;
	private TextView emAte;
	private TextView de;

	private TextView numParcelas;

	//MINIMIZAR TRABALHO
	//TABLE COR
	private View preto;
	private View cinza;
	private View branco;
	private View azul;
	private View azulClaro;
	private View verde;
	private View vermelho;

	//MENU DA OFERTA
	private TextView textView_offerSpecification;
	private TextView textView_offer;
	private TextView textView_offerSHOWSpecificarion;

	//ITENS DO MENUS - IMGS
	private ImageView imgProduto;
	private ImageView imgInfo;
	private ImageView imgSpecification;
	private ImageView imgOpinioes;
	private List<View> views;

	//BOTAO COMPRAR 
	//	private ImageView imageView_btnComprar;
	private TextView imageView_btnComprar;

	private float total;

	//MENU RODAPE
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

	//ITENS DO USUARIO
	private TextView textViewNameUser;
	//USER MENU
	private Spinner spinnerMenu;
	private ImageView newsImg;
	private Map<String, Object> newsMap;
	private TextView qtdAvisoNovidade;
	//

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);								
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_offer_detail);	

		//DETALHE DESSE CODIGO NO LoginActivity
		String type = getResources().getString(R.string.isTablet);
		if(type.equals("false")){
			Log.i("TESTE", "IS TABLET= " + type);
			OrientacaoUtils.setOrientationVertical(this);
		}
		//--

		loadViews();

		preto.setVisibility(View.GONE);
		cinza.setVisibility(View.GONE);
		branco.setVisibility(View.GONE);
		azul.setVisibility(View.GONE);
		azulClaro.setVisibility(View.GONE);
		verde.setVisibility(View.GONE);
		vermelho.setVisibility(View.GONE);

		//MAP CORES
		Map<String, View> minhasCores = new HashMap<String, View>();
		minhasCores.put("preto", preto);
		minhasCores.put("cinza", cinza);
		minhasCores.put("branco", branco);
		minhasCores.put("azul", azul);
		minhasCores.put("azul_claro", azulClaro);
		minhasCores.put("verde", verde);
		minhasCores.put("vermelho", vermelho); 

		textMenuOferta.setTextColor(Color.rgb(255, 117, 24));
		imageMenuOferta.setImageResource(R.drawable.ofertas_on);
		textView_offerSHOWSpecificarion.setVisibility(View.GONE);

		Intent intent = getIntent();
		Bundle parameter = intent.getExtras();

		newsMap = (Map<String, Object>) parameter.getSerializable(OfferNewsIntentService.PARAM_OUT_MAP_NEWS);
		Log.i("teste", "offerDetail RECEBE MAP: " + String.valueOf(newsMap.size()));
		qtdAvisoNovidade.setText(String.valueOf(newsMap.get(OfferNewsIntentService.MAP_TOTAL_QTD)));

		final Offer offer = (Offer) getIntent().getSerializableExtra("offer");
		final Company comp = CompanyBO.searchById(parameter.getInt("companyId"));
		final User user = (User) getIntent().getSerializableExtra("user");

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


		textViewNameUser.setText(user.getName());
		Log.i("OFFER OBJ", String.valueOf(offer.getId()));
		//		Log.i("Company photo", comp.getLogo());

		/**
		 * MOSTRA PALETA DE CORES REFERENTE ��S M��TRICAS ENVIADAS
		 */
		FiltroDeCores filtro = new FiltroDeCores(minhasCores);

		if(!offer.getMetrics().isEmpty()){
			views = filtro.filtraCorTextView(offer);
			if(!views.isEmpty()){
				for (View view : views) {
					view.setVisibility(View.VISIBLE);
				}
			}}

		preto.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {	
				Toast.makeText(v.getContext(), "COR: PRETA", Toast.LENGTH_LONG).show();
			}
		});

		total = 0;
		int percentage = offer.getPercentageDiscount();
		if(percentage != 0){
			float value = offer.getValue();
			float desconto = (value * percentage)/100;
			total = value - desconto;
		}else {
			total = offer.getValue();
		}

		float parcel = total / 24;

		//FORMATA VALOR DA CONTA
		final DecimalFormat df = new DecimalFormat("#.00");

		//TESTE 
		Log.i("ID DA COMPANY", String.valueOf(comp.getId()));

		offerTitle.setText(offer.getTitle());
		offerDescription.setText(offer.getResume());
		offerPhoto.setImageUrl(offer.getPhoto());
		offerValue.setText("de R$ " + String.valueOf(offer.getValue()));
		offerValueWithPercent.setText("R$ " + String.valueOf(df.format(total)));
		offerAmountAllowed.setText("R$ " + String.valueOf(df.format(parcel)).replace(".", ","));
		offerEndsDate.setText("V��lido at��".concat(String.valueOf(" " + offer.getEndsAt().get(Calendar.DAY_OF_MONTH) + "/" +
				(offer.getEndsAt().get(Calendar.MONTH) + 1) + "/" +  offer.getEndsAt().get(Calendar.YEAR))));

		if(comp.getLogo().equals("UPLOAD_ERROR")){
			offerCompLogo.setImageUrl("http://uploaddeimagens.com.br/images/000/132/678/original/not_found_logo.jpg?1386618035");
		}else {
			offerCompLogo.setImageUrl(comp.getLogo());
		}


		//CLIQUE NO MENU DA OFERTA
		/**
		 * Quando clicado em ESPECIFICACOES
		 */
		textView_offerSpecification.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//hideViews();
				textView_offerSpecification.setBackgroundResource(R.drawable.bg_tab_on);
				textView_offer.setBackgroundResource(R.drawable.bg_tab_off);

				textView_offerSHOWSpecificarion.setVisibility(View.VISIBLE);
				textView_offerSHOWSpecificarion.setText(Html.fromHtml(offer.getSpecification()));
			}
		});
		/**
		 * Quando clicado em PRODUTO
		 */
		textView_offer.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				textView_offer.setBackgroundResource(R.drawable.bg_tab_on);
				textView_offerSpecification.setBackgroundResource(R.drawable.bg_tab_off);

				textView_offerSHOWSpecificarion.setVisibility(View.GONE);

			}
		});

		//CLIQUE NO BOTAO COMPRAR
		imageView_btnComprar.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				Checkout check = new Checkout();
				check.setUnitValue(Float.parseFloat(df.format(total).replace(",", ".")));
				check.setUser(user);
				check.setCompany(comp);
				check.setOffer(offer);
				//TESTE CEP
				CheckoutDAO dao = new CheckoutDAO();
				//				List<Checkout> checks = dao.createCheckout2(check);
				//				String data = dao.createCheckoutReturnDATA(check);
				//				Log.i("TESTE - OFFERDETAIL - INSERT CHECK RETURN ID", data);
				CheckoutBO.createCheckout(check);
				Intent intent = new Intent(OfferDetail.this, CheckActivity.class);
				Bundle parameters = new Bundle();
				parameters.putSerializable(OfferNewsIntentService.PARAM_OUT_MAP_NEWS, (Serializable) newsMap);
				intent.putExtra("offer", offer);
				intent.putExtra("company", comp);
				intent.putExtra("user", user);
				intent.putExtras(parameters);
				startActivity(intent);
			}
		});

		/**
		 * CLIQUES NO MENU RODAPE
		 */
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
				parameters.putBoolean("logado", true);
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
				intentD.putExtras(bundle);
				intentD.putExtra("usuario", user);
				startActivity(intentD);
			}
		});

		//5 - DASHBOARD
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
				intentD.putExtras(bundle);
				intentD.putExtra("usuario", user);
				startActivity(intentD);
			}
		});

		newsImg.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intentNews = new Intent(OfferDetail.this, NewsActivity.class);
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

	public void loadViews(){

		//ITENS DO MENU RODAPE
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
		//FIM ITENS MENU RODAPE

		//		imageView_btnComprar = (ImageView) findViewById(R.id.imageView_btnComprar);
		imageView_btnComprar = (TextView) findViewById(R.id.imageView_btnComprar);

		offerTitle = (TextView) findViewById(R.id.textView1);
		offerDescription = (TextView) findViewById(R.id.textView_descricaoOfferDetail);
		offerValue = (TextView) findViewById(R.id.textView_precoAntigoOfferDetail);
		offerValueWithPercent = (TextView) findViewById(R.id.textView_precoNovoOfferDetail);
		offerAmountAllowed = (TextView) findViewById(R.id.textView_valorTotal);
		offerEndsDate = (TextView) findViewById(R.id.textView_textoValidade);
		offerPhoto = (SmartImageView) findViewById(R.id.imageView_imagemOfferDetail);
		offerCompLogo = (SmartImageView) findViewById(R.id.imageView_imagemCompany);

		btnComprar = (ImageView) findViewById(R.id.imageView2);
		btnCompartilhar = (ImageView) findViewById(R.id.imageView_btCompartilharOfferDetail);
		btnEnviar = (ImageView) findViewById(R.id.imageView_enviaAmigoOfferDet);
		linha1 = (ImageView) findViewById(R.id.imageView3);
		linha2 = (ImageView) findViewById(R.id.imageView4);
		linha3 = (ImageView) findViewById(R.id.imageView15);
		linha4 = (ImageView) findViewById(R.id.imageView16);
		linha5 = (ImageView) findViewById(R.id.imageView5);
		tblStars = (TableRow) findViewById(R.id.tableRow2);
		compPhoto = (TableRow) findViewById(R.id.tableRow3);
		por = (TextView) findViewById(R.id.textView2);
		emAte = (TextView) findViewById(R.id.textView3);
		de = (TextView) findViewById(R.id.textView4);

		numParcelas = (TextView) findViewById(R.id.textView_valorParcelasOfferDetail);
		numEstoque = (TextView) findViewById(R.id.textView_numeroEstoque);

		//MENU DA OFERTA
		textView_offerSpecification = (TextView) findViewById(R.id.textView_textoEspecificacoes);
		textView_offer = (TextView) findViewById(R.id.textView_textoProduto);
		textView_offerSHOWSpecificarion = (TextView) findViewById(R.id.textView_offerShowSpecification);
		qtdAvisoNovidade = (TextView) findViewById(R.id.textView_qtdConvites);

		//CORES
		preto = findViewById(R.id.view_cor_preto);
		cinza = findViewById(R.id.view_cor_cinza);
		azul = findViewById(R.id.view_cor_azul);
		azulClaro = findViewById(R.id.view_cor_azul_claro);
		verde = findViewById(R.id.view_cor_verde);
		vermelho = findViewById(R.id.view_cor_vermelho);
		branco = findViewById(R.id.view_cor_branco);

		newsImg = (ImageView) findViewById(R.id.imageView_avisoConvite);

		//view do usuario 
		textViewNameUser = (TextView) findViewById(R.id.textViewHeader_NomeUsuario);

	}

	public void hideViews(){

		offerDescription.setVisibility(View.INVISIBLE);
		offerValue.setVisibility(View.INVISIBLE);
		offerValueWithPercent.setVisibility(View.INVISIBLE);
		offerAmountAllowed.setVisibility(View.INVISIBLE);
		offerEndsDate.setVisibility(View.INVISIBLE);
		offerPhoto.setVisibility(View.INVISIBLE);
		offerCompLogo.setVisibility(View.INVISIBLE);
		numEstoque.setVisibility(View.INVISIBLE);

		de.setVisibility(View.INVISIBLE);
		btnComprar.setVisibility(View.INVISIBLE);
		btnCompartilhar.setVisibility(View.INVISIBLE);
		btnEnviar.setVisibility(View.INVISIBLE);
		linha1.setVisibility(View.INVISIBLE);
		linha2.setVisibility(View.INVISIBLE);
		linha3.setVisibility(View.INVISIBLE);
		linha4.setVisibility(View.INVISIBLE);
		linha5.setVisibility(View.INVISIBLE); 
		tblStars.setVisibility(View.INVISIBLE);
		compPhoto.setVisibility(View.INVISIBLE);
		por.setVisibility(View.INVISIBLE);
		emAte.setVisibility(View.INVISIBLE);

		numParcelas.setVisibility(View.INVISIBLE);

	}

	//COMPARTILHAR NO FACEBOOK
	
	/*public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data);
	}

	public void postar(View v){
		Session.openActiveSession(this, true,
				new Session.StatusCallback() {
			public void call(Session session, SessionState state, Exception exception) {
				if (session.isOpened()) {
					if (hasPublishPermission()) {
						Request.executeStatusUpdateRequestAsync(session, "TEXTO DA PUBLICACAO AQUI!!!", new Request.Callback() {
							public void onCompleted(Response response) {
								if (response.getError() == null) {
									//sucesso
								} else {
									//erro
								}
							}
						});
					} else {
						requisitaPermissao();
					}
				}
			}
		});
	}

	public void requisitaPermissao() {
		Session session = Session.getActiveSession();
		session.requestNewPublishPermissions(new Session.NewPermissionsRequest(this, Arrays.asList("publish_actions")));
	}

	private boolean hasPublishPermission() {
		Session session = Session.getActiveSession();
		return session != null && session.getPermissions().contains("publish_actions");
	}*/



}
