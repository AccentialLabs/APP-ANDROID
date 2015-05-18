package com.accential.trueone;

import java.text.SimpleDateFormat;
import java.util.Calendar;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.accential.trueone.adapter.CompaniesUserAdapter;
import com.accential.trueone.bean.CompaniesUser;
import com.accential.trueone.bean.User;
import com.accential.trueone.bo.CompaniesUserBO;
import com.example.slide.R;

/**
 * Responsavel por exibir as informa����es da listagem de Assinaturas
 * @author Matheus Odilon - accentialbrasil
 *
 */
@SuppressWarnings("all")
public class CompaniesUserActivity extends Activity{

	private CompaniesUserAdapter compUserAdapter;
	private ListView list;
	private TextView textViewTitle;
	private EditText editTextCompaniesSearch;
	//VIEWS DE USUARIO
	private TextView textViewNameUser;
	//	private ImageView imageViewPhotoUser;
	//	private ProgressBar progress;
	private User user;
	//MENU
	private ImageView imageMenuWish;
	private TextView textMenuWish;
	private ImageView imageMenuCompras;
	private TextView textMenuCompras;
	private ImageView imageMenuDash;
	private TextView textViewMenuDash;
	private ImageView imageMenuAssinaturas;
	//TESTE UMA PLUS
	private ImageView imageAssina;

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_list_companies);

		//CARREGA VIEWS
		loadViews();
		/**
		 * Recebendo Usuario passado via Intent
		 */
		Intent intent = getIntent();
		user = (User) getIntent().getSerializableExtra("user");
		Log.i("NOME DO USUARIO", user.getName());



		textViewNameUser.setText(user.getName().toUpperCase());
		//		DownloadImagemUtil downPhoto = new DownloadImagemUtil(this);
		//		downPhoto.download(this, user.getPhoto(), imageViewPhotoUser, progress);

		list = (ListView) findViewById(R.id.listView_companies);
		textViewTitle = (TextView) findViewById(R.id.textView_companiesCountTitle);

		final List<CompaniesUser> companies = CompaniesUserBO.returnObjCompaniesUser(user.getId());

		textViewTitle.setText("Voc�� est�� recebendo ofertas de ".concat(String.valueOf(companies.size()).concat(" empresas")));

		compUserAdapter = new CompaniesUserAdapter(companies, this);
		list.setAdapter(compUserAdapter);


		///////////////////////////////////////////////////

		textMenuWish.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//AO SELECIONAR A OP����O WISHLIST NO MENU A ACTIVITY CARREGA O OBJ USUARIO E O ATRIBIUTO 'LOGADO'
				//PARA TRANSFERIR VIA INTENT
				imageMenuWish.setImageResource(R.drawable.wishlist_off);
				Intent intent = new Intent(v.getContext(), WishlistActivity.class);
				Bundle parameters = new Bundle();
				intent.putExtra("user", user);
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
				imageMenuCompras.setImageResource(R.drawable.compras_off);
				Intent intent = new Intent(v.getContext(), CheckoutActivity.class);
				intent.putExtra("user", user);
				startActivity(intent);
			}
		});	

		/**
		SELECIONA ITENS DO MENU 
		3 - DASHBOARD*
		 */
		textViewMenuDash.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				imageMenuDash.setImageResource(R.drawable.dashboard_off);
				Intent intent = new Intent(v.getContext(), MainActivity.class);
				Bundle parameters = new Bundle();
				boolean logado = true;
				parameters.putBoolean("logado", logado);
				intent.putExtra("usuario", user);
				intent.putExtras(parameters);
				startActivity(intent);
			}
		});
	}

	public void loadViews(){
		editTextCompaniesSearch  = (EditText) findViewById(R.id.editText_CompaniesSearch);
		//	USUARIO
		//		progress = (ProgressBar) findViewById(R.id.progressBarViewHeader);
		textViewNameUser = (TextView) findViewById(R.id.textViewHeader_NomeUsuario);
		//		imageViewPhotoUser = (ImageView) findViewById(R.id.imageViewHeader);
		//	MENU
		imageMenuWish = (ImageView) findViewById(R.id.imageFooterWish);
		textMenuWish = (TextView) findViewById(R.id.textViewFooterWish);
		imageMenuCompras = (ImageView) findViewById(R.id.imageFooterCompras);
		textMenuCompras = (TextView) findViewById(R.id.textViewFooterCompras);
		imageMenuDash = (ImageView) findViewById(R.id.imageFooterDashboard);
		textViewMenuDash = (TextView) findViewById(R.id.textViewFooterDash);
		imageMenuAssinaturas = (ImageView) findViewById(R.id.imageFooterAssinaturas);
		//	SETANDO IMAGENS PARA MENU
		imageMenuWish.setImageResource(R.drawable.wishlist_on);
		imageMenuCompras.setImageResource(R.drawable.compras_on);
		imageMenuAssinaturas.setImageResource(R.drawable.assinaturas_off);
		imageAssina = (ImageView) findViewById(R.id.imageView1);
	}

	/**
	 * @author Matheus Odilon - accentialbrasil
	 * Mostra lista de Company segundo paramentros enviados
	 * @param View
	 */
	public void searchCompanyByName(View v){
		String compName = editTextCompaniesSearch.getText().toString();
		final List<CompaniesUser> comps = CompaniesUserBO.searchByName(compName);

		compUserAdapter = new CompaniesUserAdapter(comps, this);
		list.setAdapter(compUserAdapter);

		//TESTE CLIQUE NO ITEM DA LISTA - ADICIONA E REMOVE

		list.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapter, View view, int pos,
					long arg3) {
				final CompaniesUser comp = (CompaniesUser)	adapter.getItemAtPosition(pos);
				ImageView imgView = (ImageView) view.findViewById(R.id.imageView1);
				final int position = pos;
				imgView.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						Log.i("CLIQUE BUTTON", comps.get(position).getCompany().getId() + " ID DO USUARIO - " + String.valueOf(user.getId()) +
								" - ID " + String.valueOf(comps.get(position).getId()));

						Calendar dateNow = Calendar.getInstance();
						CompaniesUser compsUser = new CompaniesUser();
						compsUser.setCompany(comps.get(position).getCompany());
						compsUser.setUser(user);
						compsUser.setDateRegister(dateNow);

						SimpleDateFormat f3 = new SimpleDateFormat("yyyy-MM-dd"); 
						String dateRegister = f3.format(compsUser.getDateRegister().getTime());
						Log.i("formato da dat", dateRegister);
						CompaniesUserBO.saveMyCompaniesUser(compsUser);
						Log.i("METODO SAVE OK", "SAVE");

					}
				});
			}
		});

	}
}
