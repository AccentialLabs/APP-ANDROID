package com.accential.trueone;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import android.app.ExpandableListActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.accential.trueone.bean.Checkout;
import com.accential.trueone.bean.User;
import com.accential.trueone.bo.UserBO;
import com.accential.trueone.service.CheckIntentService;
import com.accential.trueone.service.CompaniesInvitationsService;
import com.accential.trueone.service.OfferNewsIntentService;
import com.accential.trueone.utils.MenuUtil;
import com.accential.trueone.utils.OrientacaoUtils;
import com.example.slide.R;

/**
 * Exibe tela de Compras e seus respectivos detalhes
 * @author Matheus Odilon - accentialbrasil
 *
 */
public class ComprasActivity extends ExpandableListActivity {

	private List<Checkout> compras = new ArrayList<Checkout>();
	private TextView compEmail;
	private ImageView compImgEmail;
	private int id;

	private TextView textViewNameUser;
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
	private User u;
	private CheckResponseReceiver receiver;
	private WebView wbvLoad;

	private boolean logado;
	//USER MENU
	private Spinner spinnerMenu;
	private TextView qtdAvisoNovidade;
	private Map<String, Object> newsMap;
	private ImageView newsImg;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_compras);
		//setListAdapter(new ExpandableListAdapter(this));

		//DETALHE DESSE CODIGO NO LoginActivity
		String type = getResources().getString(R.string.isTablet);
		if(type.equals("false")){
			Log.i("TESTE", "IS TABLET= " + type);
			OrientacaoUtils.setOrientationVertical(this);
		}
		//--

		textViewNameUser = (TextView) findViewById(R.id.textViewHeader_NomeUsuario);
		//ITENS DO MENU
		wbvLoad = (WebView) findViewById(R.id.webViewLoad);
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
		qtdAvisoNovidade = (TextView) findViewById(R.id.textView_qtdConvites);
		newsImg = (ImageView) findViewById(R.id.imageView_avisoConvite);

		logado = true;

		wbvLoad.loadUrl("https://www.baltimorecity.gov/Portals/_default/Skins/EnterpriseUI/images/icons/loading.gif");
		imageMenuCompras.setImageResource(R.drawable.compras_on);
		textMenuCompras.setTextColor(Color.rgb(255, 117, 24));

		//ID RECEBE O SEU VALOR CASO HAJA MUDAN��A DE HORIENTA����O
		if(savedInstanceState != null){
			id = savedInstanceState.getInt("id");
		}

		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();

		newsMap = (Map<String, Object>) bundle.getSerializable(CompaniesInvitationsService.PARAM_OUT_INVITATIONS);
		qtdAvisoNovidade.setText(String.valueOf(newsMap.get(OfferNewsIntentService.MAP_TOTAL_QTD)));


		User user = null;
		if(getIntent().getSerializableExtra("user") != null){
			user = (User) getIntent().getSerializableExtra("user");
		}else {
			user = UserBO.searchById(id);
		}

		id = user.getId();
		Log.i("valor do id", String.valueOf(id));
		u = user;

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
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});


		//START SERVICE
		Intent msgIntent = new Intent(ComprasActivity.this, CheckIntentService.class);
		Log.i("START NO SERVICE", "SERVICO STARTADO");
		msgIntent.putExtra(CheckIntentService.PARAM_IN_USER_ID, id);
		startService(msgIntent);

		IntentFilter filter = new IntentFilter(CheckResponseReceiver.ACTION_RESP);
		filter.addCategory(Intent.CATEGORY_DEFAULT);
		receiver = new CheckResponseReceiver();
		registerReceiver(receiver, filter);

		Log.i("NUMERO DO ID", String.valueOf(u.getId()));

		textViewNameUser.setText(u.getName().toUpperCase());
		//compras = CheckoutBO.returnsObjCheckout(u.getId());

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
				parameters.putSerializable(CompaniesInvitationsService.PARAM_OUT_INVITATIONS, (Serializable) newsMap);
				intent.putExtra("user", u);
				parameters.putBoolean("logado", logado);
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
				imageMenuDashboard.setImageResource(R.drawable.dashboard_off);

				textMenuCompras.setTextColor(Color.rgb(255, 117, 24));
				textMenuWish.setTextColor(Color.rgb(105, 105, 105));
				textMenuDashboard.setTextColor(Color.rgb(105, 105, 105));
				textMenuAssinaturas.setTextColor(Color.rgb(105, 105, 105));

				Intent intent = new Intent(v.getContext(), ComprasActivity.class);
				Bundle parameters = new Bundle();
				parameters.putSerializable(CompaniesInvitationsService.PARAM_OUT_INVITATIONS, (Serializable) newsMap);
				intent.putExtras(parameters);
				intent.putExtra("user", u);
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
				intentC.putExtra("user", u);
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
				intentD.putExtra("usuario", u);
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
				intentD.putExtra("usuario", u);
				startActivity(intentD);
			}
		});

		newsImg.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intentNews = new Intent(ComprasActivity.this, NewsActivity.class);
				intentNews.putExtra("user", u);
				Bundle bundle = new Bundle();
				bundle.putSerializable(OfferNewsIntentService.PARAM_OUT_MAP_NEWS, (Serializable) newsMap);
				intentNews.putExtras(bundle);
				startActivity(intentNews);
			}
		});
	}

	public class ExpandableListAdapter extends BaseExpandableListAdapter {
		private Context myContext;
		public ExpandableListAdapter(Context context) {
			////////////
			Log.i("teste", "USANDO LISTA COMPRAS - TAMANHO DA LISTA: " + String.valueOf(compras.size()));
			myContext = context;
		}
		@Override
		public Object getChild(int groupPosition, int childPosition) {
			return null;
		}
		@Override
		public long getChildId(int groupPosition, int childPosition) {
			return 0;
		}
		@Override
		public View getChildView(int groupPosition, int childPosition,
				boolean isLastChild, View convertView, ViewGroup parent) {
			if (convertView == null) {
				LayoutInflater inflater = (LayoutInflater) myContext
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				convertView = inflater.inflate(
						R.layout.activity_compras_detalhes, null);
			}
			TextView compName = (TextView) convertView
					.findViewById(R.id.textView_ComprasDetailCompanyName);

			TextView compTel = (TextView) convertView
					.findViewById(R.id.textView_ComprasDetailCompanyTel);

			TextView compPrazo = (TextView) convertView
					.findViewById(R.id.textView_ComprasDetailCompanyPrazo);

			TextView compMethod = (TextView) convertView
					.findViewById(R.id.textView_ComprasDetailCompanyFormaPagamento);

			TextView compNumParcela = (TextView) convertView
					.findViewById(R.id.textView_ComprasDetailCompanyNumParcelas);

			TextView compCodMoip = (TextView) convertView
					.findViewById(R.id.textView_ComprasDeatilCompanyCodMoip);

			compEmail = (TextView) convertView
					.findViewById(R.id.textView10);
			compEmail.setVisibility(View.GONE);

			compImgEmail = (ImageView) convertView.findViewById(R.id.imageView12);
			compImgEmail.setVisibility(View.GONE);

			/* SmartImageView compLogo = (SmartImageView) convertView
					.findViewById(R.id.imageView_ComprasDetailImage); */

			TextView compMostraEmail = (TextView) convertView
					.findViewById(R.id.textView_ComprasDetailCompanyEmail);

			Checkout check = compras.get(groupPosition);

			compName.setText(check.getCompany().getFancy_name());
			compTel.setText(check.getCompany().getPhone());
			compPrazo.setText(String.valueOf(check.getDeliveryTime()) + " dias ��teis");
			compMethod.setText(check.getMethod().getType() + " - " + check.getMethod().getName());
			compNumParcela.setText("em " + check.getInstallment() + "x");
			compCodMoip.setText(String.valueOf(check.getTransactionMoipCode()));
			compEmail.setText(check.getCompany().getEmail());

			//CONDICAO DA IMAGEM
			/*if(!check.getCompany().getLogo().equals("UPLOAD_ERROR")){
				compLogo.setImageUrl(check.getCompany().getLogo());
			}else {
				compLogo.setImageUrl("http://uploaddeimagens.com.br/images/000/132/678/original/not_found_logo.jpg?1386618035");
			} */

			compMostraEmail.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					compEmail.setVisibility(View.VISIBLE);
					compImgEmail.setVisibility(View.VISIBLE);
				}
			});

			return convertView;
		}
		@Override
		public int getChildrenCount(int groupPosition) {
			return 1;
		}
		@Override
		public Object getGroup(int groupPosition) {
			return null;
		}
		@Override
		public int getGroupCount() {
			return compras.size();
		}
		@Override
		public long getGroupId(int groupPosition) {
			return 0;
		}
		@Override
		public View getGroupView(int groupPosition, boolean isExpanded,
				View convertView, ViewGroup parent) {
			if (convertView == null) {
				LayoutInflater inflater = (LayoutInflater) myContext
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				convertView = inflater.inflate(
						R.layout.activity_item_compras, null);
			}
			((TextView) convertView.findViewById(R.id.textView_ComprasNumPedido)).setText(String.valueOf(compras.get(groupPosition).getId()));

			//			((SmartImageView) convertView.findViewById(R.id.smartImageView_ComprasPhotoPedido)).setImageUrl(compras.get(groupPosition).getOffer().getPhoto());

			((TextView) convertView.findViewById(R.id.textView_ComprasTitleOffer)).setText(compras.get(groupPosition).getOffer().getTitle());
			((TextView) convertView.findViewById(R.id.textView_ComprasDescOffer)).setText(compras.get(groupPosition).getOffer().getResume());

			((TextView) convertView.findViewById(R.id.textView_ComprasDataCompra)).setText( String.valueOf(compras.get(groupPosition).getDateTime().get(Calendar.DAY_OF_MONTH) + "/" + 
					(compras.get(groupPosition).getDateTime().get(Calendar.MONTH) + 1) + "/" + compras.get(groupPosition).getDateTime().get(Calendar.YEAR)));

			((TextView) convertView.findViewById(R.id.textView_ComprasValorTotal))
			.setText("R$".concat(String.valueOf(compras.get(groupPosition).getTotalValue()).replace(".", ",")));

			((TextView) convertView.findViewById(R.id.textView_ComprasStatus)).setText(String.valueOf(compras.get(groupPosition).getPaymentState()));

			return convertView;
		}
		@Override
		public boolean hasStableIds() {
			return false;
		}
		@Override
		public boolean isChildSelectable(int groupPosition, int childPosition) {
			return true;
		}
	}

	@Override  
	protected void onSaveInstanceState(Bundle outState) {  
		super.onSaveInstanceState(outState);  
		outState.putInt("id", id);
	}  

	public class CheckResponseReceiver extends BroadcastReceiver {
		public static final String ACTION_RESP =    
				"com.mamlambo.intent.action.MESSAGE_PROCESSED_CHECK";
		@Override
		public void onReceive(Context context, Intent intent) {

			Bundle parameters = intent.getExtras();
			//String teste = parameters.getString(LoginIntentService.PARAM_OUT_RESPOSTA);
			List<Checkout> checks = (List<Checkout>) parameters.getSerializable(CheckIntentService.PARAM_OUT_CHECK_LIST); 
			Log.i("TESTE DE RETORNO DA LISTA", "EXECULTOU O SERVICE CHECK");
			Log.i("TESTE", "TAMANHO DA LISTA DE OFERTA - SERVICE: " +  String.valueOf(checks.size()));
			compras = checks;
			wbvLoad.setVisibility(View.GONE);

			//	TESTANDO EXECUTAR ADAPTER AP��S 
			setListAdapter(new ExpandableListAdapter(ComprasActivity.this));
		}

	}

}

