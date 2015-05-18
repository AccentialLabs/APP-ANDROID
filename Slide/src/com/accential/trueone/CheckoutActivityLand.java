package com.accential.trueone;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TableRow;
import android.widget.TextView;

import com.accential.trueone.adapter.CheckoutLandAdapter;
import com.accential.trueone.bean.Checkout;
import com.accential.trueone.bean.User;
import com.accential.trueone.bo.CheckoutBO;
import com.example.slide.R;
import com.loopj.android.image.SmartImageView;

public class CheckoutActivityLand extends Activity{

	private ListView lista;

	//INFO - DETALHES DA COMPRA
	private TextView checkCompName;
	private TextView checkCompPhone;
	private TextView checkCompEmail;
	private TextView checkDeadline;
	private TextView checkMethodTypeName;
	//prestacoes
	private TextView checkInstallment;
	private TextView moipCode;
	private SmartImageView checkCompLogo;

	//VIEWS
	private View viewBack;
	private View view1;
	private View view2;
	private View view3;
	private View view4;
	private ImageView imgVert1;
	private ImageView imgVert2;
	private ImageView imgVert3;
	private TableRow tableRowComp;
	private TableRow tableRowDelivery;
	private TableRow tableSisPagamento;
	private TableRow tableFormaPag;
	private TextView checkVerEmail;
	private TextView checkPrevisaoDeAte;
	private TextView checkJuros;
	private TextView checkTitleCod;
	private ImageView checkFaixaEmail;

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);								
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_compras);	

		loadViews();

		//INFOS - DETALHES DA COMPRA
		checkCompName = (TextView) findViewById(R.id.textView_ComprasDetailCompanyName);
		checkCompPhone = (TextView) findViewById(R.id.textView_ComprasDetailCompanyTel);
		checkCompEmail = (TextView) findViewById(R.id.textView10);
		checkDeadline = (TextView) findViewById(R.id.textView_ComprasDetailCompanyPrazo);
		checkMethodTypeName = (TextView )findViewById(R.id.textView_ComprasDetailCompanyFormaPagamento);
		checkInstallment = (TextView) findViewById(R.id.textView_ComprasDetailCompanyNumParcelas);
		moipCode = (TextView) findViewById(R.id.textView_ComprasDeatilCompanyCodMoip); 
		checkCompLogo = (SmartImageView) findViewById(R.id.imageView_ComprasDetailImage);
		lista = (ListView) findViewById(R.id.list);

		checkCompLogo.setImageUrl("http://uploaddeimagens.com.br/images/000/132/678/original/not_found_logo.jpg?1386618035");

		hideViews();

		Intent intent = getIntent();
		User user = (User) getIntent().getSerializableExtra("user");

		final List<Checkout> checkouts = CheckoutBO.returnsObjCheckout(user.getId());
		CheckoutLandAdapter adapter = new CheckoutLandAdapter(this, checkouts);
		lista.setAdapter(adapter);

		lista.setOnItemClickListener(new OnItemClickListener() {

			//MOSTRA DETALHES
			@Override
			public void onItemClick(AdapterView<?> adapter, View view, int position,
					long arg3) {

				Checkout check = checkouts.get(position);
				Log.e("TESTE URL IMAGE", check.getCompany().getLogo());

				checkCompName.setText(check.getCompany().getFancy_name());
				checkCompPhone.setText(check.getCompany().getPhone());
				checkCompEmail.setText(check.getCompany().getEmail());
				checkDeadline.setText(String.valueOf(check.getDeliveryTime()) + " dias ��teis");
				checkMethodTypeName.setText(check.getMethod().getType() + " - " + check.getMethod().getName());
				checkInstallment.setText("em " + check.getInstallment() + "x");
				moipCode.setText(String.valueOf(check.getTransactionMoipCode()));

				//				if(check.getCompany().getLogo().equals("UPLOAD_ERROR")){
				//					checkCompLogo.setImageUrl("http://uploaddeimagens.com.br/images/000/132/678/original/not_found_logo.jpg?1386618035");
				//				}else{
				//					checkCompLogo.setImageUrl(check.getCompany().getLogo());
				//				}


				showViews();
				checkVerEmail.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						checkCompEmail.setVisibility(View.VISIBLE);
						checkFaixaEmail.setVisibility(View.VISIBLE);
					}
				});
				checkVerEmail.setOnLongClickListener(new OnLongClickListener() {
					@Override
					public boolean onLongClick(View v) {
						checkCompEmail.setVisibility(View.GONE);
						checkFaixaEmail.setVisibility(View.GONE);
						return false;
					}
				});
				Log.i("CLIQUE EM ELEMENTO DA LISTA", " ELEMENTO CLICADO - ".concat(String.valueOf(check.getTransactionMoipCode())));
			}
		});

		//ESCONDE DETALHES
		lista.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				hideViews();
				return false;
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

		viewBack = findViewById(R.id.view2);
		view1 = findViewById(R.id.view3);
		view2 = findViewById(R.id.View01);
		view3 = findViewById(R.id.View02);
		view4 = findViewById(R.id.View03);

		imgVert1 = (ImageView) findViewById(R.id.imageView1);
		imgVert2 = (ImageView) findViewById(R.id.imageView6);
		imgVert3 = (ImageView) findViewById(R.id.imageView7);

		tableRowComp = (TableRow) findViewById(R.id.tableRow3);
		tableRowDelivery = (TableRow) findViewById(R.id.tableRow4);
		tableSisPagamento = (TableRow) findViewById(R.id.tableRow8);
		tableFormaPag = (TableRow) findViewById(R.id.tableRow7);

		checkVerEmail = (TextView) findViewById(R.id.textView_ComprasDetailCompanyEmail);
		checkPrevisaoDeAte = (TextView) findViewById(R.id.textView7);
		checkJuros = (TextView) findViewById(R.id.textView8);
		checkTitleCod = (TextView) findViewById(R.id.textView9);

		checkFaixaEmail = (ImageView) findViewById(R.id.imageView12);
	}

	public void hideViews(){

		//VIEWS QUE RECEBEM INFORMA����ES
		checkCompName.setVisibility(View.GONE);
		checkCompPhone.setVisibility(View.GONE);
		checkCompEmail.setVisibility(View.GONE);
		checkDeadline.setVisibility(View.GONE);
		checkMethodTypeName.setVisibility(View.GONE);
		checkInstallment.setVisibility(View.GONE);
		moipCode.setVisibility(View.GONE);
		checkCompLogo.setVisibility(View.GONE);
		//SECUNDARIOS
		viewBack.setVisibility(View.GONE);
		view1.setVisibility(View.GONE);
		view2.setVisibility(View.GONE);
		view3.setVisibility(View.GONE);
		view4.setVisibility(View.GONE);
		imgVert1.setVisibility(View.GONE);
		imgVert2.setVisibility(View.GONE);
		imgVert3.setVisibility(View.GONE);
		tableRowComp.setVisibility(View.GONE);
		tableRowDelivery.setVisibility(View.GONE);
		tableSisPagamento.setVisibility(View.GONE);
		tableFormaPag.setVisibility(View.GONE);
		checkVerEmail.setVisibility(View.GONE);
		checkPrevisaoDeAte.setVisibility(View.GONE);
		checkJuros.setVisibility(View.GONE);
		checkTitleCod.setVisibility(View.GONE);
		checkFaixaEmail.setVisibility(View.GONE);
	}

	public void showViews(){

		checkCompName.setVisibility(View.VISIBLE);
		checkCompPhone.setVisibility(View.VISIBLE);

		checkDeadline.setVisibility(View.VISIBLE);
		checkMethodTypeName.setVisibility(View.VISIBLE);
		checkInstallment.setVisibility(View.VISIBLE);
		moipCode.setVisibility(View.VISIBLE);
		checkCompLogo.setVisibility(View.VISIBLE);
		//SECUNDARIOS
		viewBack.setVisibility(View.VISIBLE);
		view1.setVisibility(View.VISIBLE);
		view2.setVisibility(View.VISIBLE);
		view3.setVisibility(View.VISIBLE);
		view4.setVisibility(View.VISIBLE);
		imgVert1.setVisibility(View.VISIBLE);
		imgVert2.setVisibility(View.VISIBLE);
		imgVert3.setVisibility(View.VISIBLE);
		tableRowComp.setVisibility(View.VISIBLE);
		tableRowDelivery.setVisibility(View.VISIBLE);
		tableSisPagamento.setVisibility(View.VISIBLE);
		tableFormaPag.setVisibility(View.VISIBLE);
		checkVerEmail.setVisibility(View.VISIBLE);
		checkPrevisaoDeAte.setVisibility(View.VISIBLE);
		checkJuros.setVisibility(View.VISIBLE);
		checkTitleCod.setVisibility(View.VISIBLE);

	}

}
