package com.accential.trueone;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TextView;

import com.accential.trueone.adapter.CheckoutAdapter;
import com.accential.trueone.bean.Checkout;
import com.accential.trueone.bean.User;
import com.accential.trueone.bo.CheckoutBO;
import com.accential.trueone.utils.DownloadImagemUtil;
import com.example.slide.R;
/**
 * Activity respons��vel mostrar lista de Compras
 * @author Matheus Odilon - accentialbrasil
 */
public class CheckoutActivity extends Activity{
	//VIEW DA ACTIVITY
	private Checkout checkout;
	private ListView list;
	private CheckoutAdapter checkAdapter;
	//VIEWS DE USUARIO
	private TextView textViewNameUser;
//	private ImageView imageViewPhotoUser;
//	private ProgressBar progress;
	//VIEW DO MENU
	private ImageView imageMenuWish;
	private TextView textMenuWish;
	private ImageView imageMenuCompras;
	private ImageView imageMenuAssinaturas;
	private TextView textMenuAssinaturas;
	//VIEW DO DETALHE DA COMPRA
	private TableLayout tableLayoutCheck;
	private ImageView imageViewDetailCheck;
	private ProgressBar progressBarCheck;
	private TextView textViewEntCheck;
	private TextView textViewPagCheck;
	private TextView textViewMoipCodCheck;
	private TextView textViewCompCheck;
	private TextView textViewContatCheck;

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);								
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_list_checkouts);

		loadViews();

		imageMenuAssinaturas.setImageResource(R.drawable.assinaturas_on);
		imageMenuWish.setImageResource(R.drawable.wishlist_on);
		imageMenuCompras.setImageResource(R.drawable.compras_on);

		final Intent intentWish = new Intent(this, WishlistActivity.class);
		final Intent intentAssinaturas = new Intent(this, CompaniesUserActivity.class);
		final DownloadImagemUtil dow = new DownloadImagemUtil(this);

		//RECEBENDO OBJETO USUARIO ENVIADO POR ACTIVITY
		Intent intent = getIntent();
		User user = (User) intent.getSerializableExtra("user");

		intentWish.putExtra("user", user);
		intentAssinaturas.putExtra("user", user);

		//ATRIBUINDO VALORES DO OBJETO �� VIEW
		textViewNameUser.setText(user.getName().toUpperCase());
//		DownloadImagemUtil downPhoto = new DownloadImagemUtil(this);
//		downPhoto.download(this, user.getPhoto(), imageViewPhotoUser, progress);

		//LISTA RECEBE COMPRAS DO USUARIO
		final List<Checkout> checks = CheckoutBO.returnsObjCheckout(user.getId());
		Log.i("PESO - LISTA DE CHECKOUTS", String.valueOf(checks.size()));
		checkAdapter = new CheckoutAdapter(checks, this);
		list.setAdapter(checkAdapter);

		tableLayoutCheck.setVisibility(View.GONE);

		//CLIQUE - ITEM DA LISTA -> DETALHES DA COMPRA
		list.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				checkout = checks.get(position);

				dow.download(getParent(), checkout.getOffer().getPhoto(), imageViewDetailCheck, progressBarCheck);

				tableLayoutCheck.setVisibility(View.VISIBLE);

				textViewEntCheck.setText(String.valueOf(checkout.getDeliveryTime()).concat(" dias ��teis"));
				textViewPagCheck.setText(checkout.getMethod().getName().concat(" em ").concat(String.valueOf(checkout.getAmount()).concat("x")));
				textViewMoipCodCheck.setText(checkout.getTransactionMoipCode());
				textViewCompCheck.setText(checkout.getCompany().getFancy_name());
				textViewContatCheck.setText(checkout.getCompany().getEmail().concat(" - \n ".concat(checkout.getCompany().getPhone())));

			}
		});

		//CLIQUE - PARA FECHAR TABELA DE DETALHES 
		tableLayoutCheck.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				tableLayoutCheck.setVisibility(View.GONE);
			}
		});

		//CLIQUE - MENU WISHLIST
		textMenuWish.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				imageMenuWish.setImageResource(R.drawable.wishlist_off);
				startActivity(intentWish);
			}
		});

		//CLIQUE - MENU ASSINATURAS 
		textMenuAssinaturas.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				imageMenuAssinaturas.setImageResource(R.drawable.assinaturas_off);
				startActivity(intentAssinaturas);
			}
		});
	}

	public void loadViews(){

		list = (ListView) findViewById(R.id.list_Checkout);

//		progress = (ProgressBar) findViewById(R.id.progressBarViewHeader);
		textViewNameUser = (TextView) findViewById(R.id.textViewHeader_NomeUsuario);
//		imageViewPhotoUser = (ImageView) findViewById(R.id.imageViewHeader);

		imageMenuWish = (ImageView) findViewById(R.id.imageFooterWish);
		textMenuWish = (TextView) findViewById(R.id.textViewFooterWish);

		imageMenuCompras = (ImageView) findViewById(R.id.imageFooterCompras);

		tableLayoutCheck = (TableLayout) findViewById(R.id.tableLayout_Check);
		imageViewDetailCheck = (ImageView) findViewById(R.id.imageView_checkout);
		progressBarCheck = (ProgressBar) findViewById(R.id.progressBar_checkout);
		textViewEntCheck = (TextView) findViewById(R.id.textView_checkDiasUteis);
		textViewPagCheck = (TextView) findViewById(R.id.textView_checkPagamento);
		textViewMoipCodCheck = (TextView) findViewById(R.id.textView_checkMoipCode);
		textViewCompCheck = (TextView) findViewById(R.id.textView_Company);
		textViewContatCheck = (TextView) findViewById(R.id.textView_checkContato);

		imageMenuAssinaturas = (ImageView) findViewById(R.id.imageFooterAssinaturas);
		textMenuAssinaturas = (TextView) findViewById(R.id.textViewFooterAssinaturas);
	}

}
