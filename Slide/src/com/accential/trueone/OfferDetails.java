package com.accential.trueone;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.accential.trueone.bean.Company;
import com.accential.trueone.bean.Offer;
import com.accential.trueone.bean.User;
import com.accential.trueone.bo.CompanyBO;
import com.accential.trueone.utils.DownloadImagemUtil;
import com.example.slide.R;
import com.loopj.android.image.SmartImageView;

/**
 * Classe responsavel por mostrar os detalhes de uma determinada Offer que �� selecionada na lista da MainActivity
 * @author Matheus Odilon - accentialbrasil
 *
 */
@SuppressWarnings("all")
public class OfferDetails extends Activity {

	private TextView textViewTitle;
	private TextView textViewResume;
	private TextView textViewValue;
	private TextView textViewInfo;
	private TextView textViewEsp;
	private TextView textViewOpn;
	private TextView textViewDesc;
	private TextView textViewValorRiscado;
	private TextView texteViewCompName;
	private SmartImageView imageViewPhoto;
	private ProgressBar progressBarPhoto;

	//INFO DO USUARIO
	private TextView textViewNameUser;
	//private ImageView imageViewPhotoUser;
	//private ProgressBar progress;

	//STATUS LOGIN
	private boolean logado;

	//ITENS DO MENU
	private TextView textViewWish;
	private ImageView imageViewWish;
	private ImageView imageMenuCompras;
	private TextView textMenuCompras;
	private ImageView imageMenuAssinaturas;
	private TextView textMenuAssinaturas;

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);	
		setContentView(R.layout.activity_details);	

		//VIEWS DE USUARIO
		//progress = (ProgressBar) findViewById(R.id.progressBarViewHeader);
		textViewNameUser = (TextView) findViewById(R.id.textViewHeader_NomeUsuario);
		//		imageViewPhotoUser = (ImageView) findViewById(R.id.imageViewHeader);

		//SETANDO VIEWS
		textViewTitle = (TextView) findViewById(R.id.textView_datails1);
		textViewResume = (TextView) findViewById(R.id.textView_details2);
		textViewValue = (TextView) findViewById(R.id.textView_details3);
		textViewInfo = (TextView) findViewById(R.id.textView_datails5);
		textViewEsp = (TextView) findViewById(R.id.textView_datails6);
		textViewOpn = (TextView) findViewById(R.id.textView_datails7);
		textViewDesc = (TextView) findViewById(R.id.textView_datails8);
		texteViewCompName = (TextView) findViewById(R.id.textView_detailsCompName);
		textViewValorRiscado = (TextView) findViewById(R.id.textView_valorRiscado);
		imageViewPhoto = (SmartImageView) findViewById(R.id.imageView_details);
		progressBarPhoto = (ProgressBar) findViewById(R.id.progressBar_details);

		//ITENS DO MENU
		textViewWish = (TextView) findViewById(R.id.textViewFooterWish);
		imageViewWish = (ImageView) findViewById(R.id.imageFooterWish);

		imageMenuCompras = (ImageView) findViewById(R.id.imageFooterCompras);
		textMenuCompras = (TextView) findViewById(R.id.textViewFooterCompras);

		imageMenuAssinaturas = (ImageView) findViewById(R.id.imageFooterAssinaturas);
		textMenuAssinaturas = (TextView) findViewById(R.id.textViewFooterAssinaturas);

		imageMenuAssinaturas.setImageResource(R.drawable.assinaturas_on);
		imageViewWish.setImageResource(R.drawable.wishlist_on);
		imageMenuCompras.setImageResource(R.drawable.compras_on);

		DownloadImagemUtil dow = new DownloadImagemUtil(this);

		//INTENT QUE TRANSPORTA OFFER E USER
		Intent intent = getIntent();

		//BUNDLE PASSADO PELA INTENT COM OS ELEMENTOS DA OFFER SELECIONADA
		final Bundle parameter = intent.getExtras();
		logado = parameter.getBoolean("logado");

		//RECEBENDO ID DA COMPANY E FAZENDO PESQUISA - RETORNA UM OBJETO COMPANY 
		Company comp = CompanyBO.searchById(parameter.getInt("companyId"));
		Log.i("COMPANY'S FANCY NAME", comp.getFancy_name());

		//RECEBENDO OBJ USER E OFFER DA ACTIVITY MainAcitivity
		final Offer offer = (Offer) getIntent().getSerializableExtra("offer");
		Log.i("LOGIN OFFER", String.valueOf(logado));

		imageViewPhoto.setImageUrl(offer.getPhoto());

		final Intent intentWishlist = new Intent(this, WishlistActivity.class);
		final Intent intentCompras = new Intent(this, CheckoutActivity.class);
		final Intent intentAssinaturas = new  Intent(this, CompaniesUserActivity.class);

		//AVALIA STATUS DO LOGIN
		//CASO USUARIO ESTEJA LOGADO ENT��O AS VIEWS RECEBEM SUAS INFORMA����ES 
		if(logado == true){

			final User u = (User) getIntent().getSerializableExtra("user");

			intentWishlist.putExtra("user", u);
			intentCompras.putExtra("user", u);
			intentAssinaturas.putExtra("user", u);

			textViewNameUser.setText(u.getName().toUpperCase());
			//			DownloadImagemUtil downPhoto = new DownloadImagemUtil(this);
			//			downPhoto.download(this, u.getPhoto(), imageViewPhotoUser, progress);

		} 
		//CASO USUARIO N��O ESTEJA LOGADO AS VIEWS RECEBEM UMA INFORMA����O PADR��O
		else if(logado == false){

			//ESCONDE ITENS DE MENU - SOMENTE PARA USUARIOS LOGADOS
			//WISHLIST
			textViewWish.setVisibility(View.GONE);
			imageViewWish.setVisibility(View.GONE);

			textMenuCompras.setVisibility(View.GONE);
			imageMenuCompras.setVisibility(View.GONE);

			imageMenuAssinaturas.setVisibility(View.GONE);
			textMenuAssinaturas.setVisibility(View.GONE);

			//			progress.setVisibility(View.GONE);
			textViewNameUser.setText("ENTRAR");
			textViewNameUser.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intentLog = new Intent(OfferDetails.this, LoginActivity.class);
					startActivity(intentLog);
				}
			});

		}
		if(parameter != null)
		{
			//BUSCANDO ATRIBUTOS PASSADOS PELO BUNDLE 
			//FAZENDO CONTA DE PORCENTAGEM
			float total = 0;
			int percentage = offer.getPercentageDiscount();
			if(percentage != 0){
				float value = offer.getValue();
				float desconto = (value * percentage)/100;
				total = value - desconto;
			}else {
				total = offer.getValue();
			}


			//textViewTitle.setText(parameter.getString("titulo"));
			textViewTitle.setText(offer.getTitle());
			textViewResume.setText(offer.getResume());

			textViewValorRiscado.setText("De: " + "R$ " + String.valueOf(offer.getValue()));
			textViewValue.setText("Por: " + "R$ " + String.valueOf(total));
			texteViewCompName.setText("Empresa Anunciante - " + comp.getFancy_name());

		}

		//MOSTRA DESCRICAO DA OFFER SELECIONADA QUANDO CLICADO NO TEXTVIEW 'textViewInfo' 
		textViewInfo.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//SE OFFER NAO TIVER DESCRICAO SETADA, ENTAO 'textViewDesc' RECEBE "OFERTA SEM DESCRICAO"
				if(!offer.getDescription().equals(""))
				{
					textViewDesc.setText(Html.fromHtml(offer.getDescription()));
				}
				else
				{
					textViewDesc.setText(Html.fromHtml("Oferta Sem Descri����o"));
				}

			}
		});

		//MOSTRA ESPECIFICACOES DA OFFER SELECIONADA QUANDO CLICADO NO TEXTVIEW 'textViewEsp'
		textViewEsp.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//SE OFFER NAO TIVER ESPECIFICACAO SETADA, ENTAO 'textViewDesc' RECEBE "OFERTA SEM ESPECIFICACAO"
				if(!offer.getSpecification().equals(""))
				{
					textViewDesc.setText(Html.fromHtml(offer.getSpecification()));
				}
				else
				{
					textViewDesc.setText("Oferta Sem Especifica����o");
				}

			}
		});

		//MOSTRA OPINIOES DA OFFER SELECIONADA QUANDO CLICADO NO TEXTVIEW 'textiewOpn'
		textViewOpn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(OfferDetails.this, "MOSTRA OPINIAO", Toast.LENGTH_LONG).show();
			}
		});

		/**
		 * ITENS DO MENU
		 */
		//CLIQUE - WISHLIST MENU
		textViewWish.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(intentWishlist);
			}
		});

		//CLIQUE - COMPRAS MENU
		textMenuCompras.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(intentCompras);
			}
		});

		//CLIQUE - ASSINATURAS MENU
		textMenuAssinaturas.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(intentAssinaturas);
			}
		});
	}




}
