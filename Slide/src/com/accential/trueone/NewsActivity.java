package com.accential.trueone;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.accential.trueone.bean.CompaniesInvitationsUser;
import com.accential.trueone.bean.User;
import com.accential.trueone.service.CompaniesInvitationsService;
import com.accential.trueone.service.OfferNewsIntentService;
import com.accential.trueone.utils.OrientacaoUtils;
import com.example.slide.R;

@SuppressWarnings("all")
public class NewsActivity extends Activity {

	private TextView qtdTotalTitle;
	private TextView qtdInvite;
	private TextView qtdNewsOffer;

	private ImageView imageViewInvite;
	private ImageView imageViewNewsOffer;

	private Map<String, Object> myNewsMap;
	private List<CompaniesInvitationsUser> invitations;

	private User user;

	private TextView textViewNameUser;

	private TextView qtdAvisoNovidade;
	private int qtdAvisoTotal;

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
	private boolean logado = true;

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);								
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_news);		

		//DETALHE DESSE CODIGO NO LoginActivity
		String type = getResources().getString(R.string.isTablet);
		if(type.equals("false")){
			Log.i("TESTE", "IS TABLET= " + type);
			OrientacaoUtils.setOrientationVertical(this);
		}

		qtdTotalTitle = (TextView) findViewById(R.id.textView_qtdNotifications);
		qtdInvite = (TextView) findViewById(R.id.textView_pendingInvitation);
		qtdNewsOffer = (TextView) findViewById(R.id.textView_newsOffer);

		imageViewInvite = (ImageView) findViewById(R.id.imageView_invitation);
		imageViewNewsOffer = (ImageView) findViewById(R.id.imageView_newsOffer);

		qtdAvisoNovidade = (TextView) findViewById(R.id.textView_qtdConvites);

		textViewNameUser = (TextView) findViewById(R.id.textViewHeader_NomeUsuario);

		imageMenuWish = (ImageView) findViewById(R.id.imageFooterWish);
		textMenuWish = (TextView) findViewById(R.id.textViewFooterWish);
		imageMenuCompras = (ImageView) findViewById(R.id.imageFooterCompras);
		textMenuCompras = (TextView) findViewById(R.id.textViewFooterCompras);
		imageMenuAssinaturas = (ImageView) findViewById(R.id.imageFooterAssinaturas);
		textMenuAssinaturas = (TextView) findViewById(R.id.textViewFooterAssinaturas);
		imageMenuDash = (ImageView) findViewById(R.id.imageFooterInvitations);
		textMenuDash = (TextView) findViewById(R.id.textViewFooterInvitations);
		imageMenuOferta = (ImageView) findViewById(R.id.imageFooterDashboard);
		textMenuOferta = (TextView) findViewById(R.id.textViewFooterDash);

		Intent intent = getIntent();
		Bundle parameters = intent.getExtras();

		user = (User) getIntent().getSerializableExtra("user");

		textViewNameUser.setText(user.getName());

		myNewsMap = (Map<String, Object>) parameters.getSerializable(OfferNewsIntentService.PARAM_OUT_MAP_NEWS);

		invitations = (List<CompaniesInvitationsUser>) myNewsMap.get(OfferNewsIntentService.MAP_INVITES);

		//SETANDO TEXTOS
		final int total = (Integer) myNewsMap.get(OfferNewsIntentService.MAP_TOTAL_QTD);
		int totalInvitation = invitations.size();
		int totalNewsOffer = (Integer) myNewsMap.get(OfferNewsIntentService.MAP_NEWS);

		qtdAvisoNovidade.setText(String.valueOf(total));

		//LOGICAS
		//TITULO
		if(total < 1){
			qtdTotalTitle.setText("N��o h�� notifica����es no momento.");
		}
		else if(total == 1){
			qtdTotalTitle.setText(String.valueOf(myNewsMap.get(OfferNewsIntentService.MAP_TOTAL_QTD))
					+ " Notifica����o");
		}
		else if(total > 1){
			qtdTotalTitle.setText(String.valueOf(myNewsMap.get(OfferNewsIntentService.MAP_TOTAL_QTD))
					+ " Notifica����es");
		}

		//CONVITES
		if(totalInvitation < 1){
			qtdInvite.setText("Voc�� n��o tem convites pendentes.");
		}
		else if(totalInvitation == 1){
			qtdInvite.setText("Voc�� tem 1 convite pendente.");
		}
		else if(totalInvitation > 1){
			qtdInvite.setText("Voc�� tem " + totalInvitation + " convites pendentes.");
		}

		//OFERTAS
		if(totalNewsOffer < 1){
			qtdNewsOffer.setText("Veja as ofertas do TrueOne");
		}
		else if(totalNewsOffer == 1){
			qtdNewsOffer.setText("1 nova oferta.");
		}
		else if(totalNewsOffer > 1){
			qtdNewsOffer.setText(totalNewsOffer + " novas ofertas.");
		}


		//ONCLICK EM QUANTIDADE DE OFERTAS...
		qtdNewsOffer.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {

				Intent intentMain = new Intent(NewsActivity.this, MainActivity.class);
				Bundle bundleMain = new Bundle();
				bundleMain.putBoolean("logado", true);
				intentMain.putExtras(bundleMain);
				intentMain.putExtra("usuario", user);
				intentMain.putExtra(MainActivity.QTQ_NOVA, qtdAvisoTotal);
				startActivity(intentMain);
			}
		});

		imageViewNewsOffer.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intentMain = new Intent(NewsActivity.this, MainActivity.class);
				Bundle bundleMain = new Bundle();
				bundleMain.putBoolean("logado", true);
				intentMain.putExtras(bundleMain);
				intentMain.putExtra("usuario", user);
				intentMain.putExtra(MainActivity.QTQ_NOVA, qtdAvisoTotal);
				startActivity(intentMain);				
			}
		});

		//ONCLICK EM QUANTIDADE DE CONVITES
		imageViewInvite.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intentMain = new Intent(NewsActivity.this, CompaniesInvitationsActivity.class);
				Bundle bundleMain = new Bundle();
				bundleMain.putBoolean("logado", true);
				bundleMain.putSerializable(CompaniesInvitationsService.PARAM_OUT_INVITATIONS, (Serializable) myNewsMap);
				intentMain.putExtras(bundleMain);
				intentMain.putExtra("usuario", user);
				startActivity(intentMain);	
			}
		});

		qtdInvite.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intentMain = new Intent(NewsActivity.this, CompaniesInvitationsActivity.class);
				Bundle bundleMain = new Bundle();
				bundleMain.putBoolean("logado", true);
				bundleMain.putSerializable(CompaniesInvitationsService.PARAM_OUT_INVITATIONS, (Serializable) myNewsMap);
				intentMain.putExtras(bundleMain);
				intentMain.putExtra("usuario", user);
				startActivity(intentMain);
			}
		});


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

				intent.putExtra(MainActivity.QTQ_NOVA, qtdAvisoTotal);
				intent.putExtra("user", user);
				parameters.putBoolean("logado", logado);
				parameters.putInt(MainActivity.QTQ_NOVA, total);
				parameters.putSerializable(CompaniesInvitationsService.PARAM_OUT_INVITATIONS, (Serializable) myNewsMap);
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

				Bundle parameters = new Bundle();
				parameters.putSerializable(CompaniesInvitationsService.PARAM_OUT_INVITATIONS, (Serializable) myNewsMap);
				intent.putExtras(parameters);
				intent.putExtra(MainActivity.QTQ_NOVA, qtdAvisoTotal);
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
				imageMenuDash.setImageResource(R.drawable.dashboard_off);
				imageMenuOferta.setImageResource(R.drawable.ofertas_off);

				textMenuAssinaturas.setTextColor(Color.rgb(255, 117, 24));
				textMenuWish.setTextColor(Color.rgb(105, 105, 105));
				textMenuDash.setTextColor(Color.rgb(105, 105, 105));
				textMenuCompras.setTextColor(Color.rgb(105, 105, 105));
				textMenuOferta.setTextColor(Color.rgb(105, 105, 105));
				//MUDEI AQUI - CompaniesUserActivity

				Intent intentC = new Intent(v.getContext(), SignaturesActivity.class);

				Bundle parameters = new Bundle();
				parameters.putSerializable(CompaniesInvitationsService.PARAM_OUT_INVITATIONS, (Serializable) myNewsMap);
				intentC.putExtras(parameters);

				intentC.putExtra(MainActivity.QTQ_NOVA, qtdAvisoTotal);
				intentC.putExtra("user", user);
				startActivity(intentC);
			}
		});

		//SELECIONA ITENS DO MENU
		//4 - DASHBOARD
		textMenuDash.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				imageMenuWish.setImageResource(R.drawable.wishlist_off);
				imageMenuAssinaturas.setImageResource(R.drawable.assinaturas_off);
				imageMenuCompras.setImageResource(R.drawable.compras_off);
				imageMenuDash.setImageResource(R.drawable.dashboard_on);

				textMenuAssinaturas.setTextColor(Color.rgb(105, 105, 105));
				textMenuWish.setTextColor(Color.rgb(105, 105, 105));
				textMenuDash.setTextColor(Color.rgb(255, 117, 24));
				textMenuCompras.setTextColor(Color.rgb(105, 105, 105));

				Intent intentD = new Intent(v.getContext(), MainActivity.class);
				Bundle bundle = new  Bundle();
				bundle.putBoolean("logado", true);
				bundle.putSerializable(CompaniesInvitationsService.PARAM_OUT_INVITATIONS, (Serializable) myNewsMap);
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
				imageMenuDash.setImageResource(R.drawable.dashboard_off);
				imageMenuOferta.setImageResource(R.drawable.ofertas_on);

				textMenuAssinaturas.setTextColor(Color.rgb(105, 105, 105));
				textMenuWish.setTextColor(Color.rgb(105, 105, 105));
				textMenuDash.setTextColor(Color.rgb(105, 105, 105));
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


	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
