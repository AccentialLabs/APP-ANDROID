package com.accential.trueone;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.accential.trueone.bean.CompanyCategory;
import com.accential.trueone.bean.CompanySubCategory;
import com.accential.trueone.bean.User;
import com.accential.trueone.bean.Wishlist;
import com.accential.trueone.bo.CompanyCategoryBO;
import com.accential.trueone.bo.CompanySubCategoryBO;
import com.example.slide.R;
/**
 * Classe recebe um obj wishlist do formulario 
 * da activity_include_wishlist e inclui
 * o mesmo na base de dados True1
 * @author Matheus Odilon - accentialbrasil
 *
 */
@SuppressWarnings("all")
public class WishlistInclude extends Activity{

	private Spinner spinnerCategory;
	private Spinner spinnerSubCategory;
	private EditText editTextProduto;
	private EditText editTextDesc;
	private EditText editTextData;

	private CompanyCategory category;
	private CompanySubCategory subCategory;
	private Wishlist wishlist;

	//VIEWS DE USUARIO
	private TextView textViewNameUser;
//	private ProgressBar progress;
//	private ImageView imageViewPhotoUser;
	private User user;

	//ELEMENTOS DO MENU
	private ImageView imageViewMenuWish;

	private ImageView imageMenuCompras;
	private TextView textMenuCompras;

	private ImageView imageMenuAssinaturas;
	private TextView textMenuAssinaturas;

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);								
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_include_wishlist);		

		loadViews();

		Intent intent = getIntent();

		//RECEBE USUARIO ENVIADO PELA WISHLISTACTIVITY=> (ver linha: 69 e 99)
		user = (User) getIntent().getSerializableExtra("user");
		textViewNameUser.setText(user.getName().toUpperCase());
//		DownloadImagemUtil downPhoto = new DownloadImagemUtil(this);
//		downPhoto.download(this, user.getPhoto(), imageViewPhotoUser, progress);

		//CRIANDO INTENT PARA MENU COMPRAS
		final Intent intentCompras = new Intent(this, CheckoutActivity.class);
		final Intent intentAssinaturas = new Intent(this, CompaniesUserActivity.class);
		intentAssinaturas.putExtra("user", user);
		intentCompras.putExtra("user", user);	

		//MAPS NECESASRIOS PARA PESQUISA DE CATEGORY E SUBCATEGORY
		final Map<String, Map<String, Map<String, String>>> key = new HashMap<String,Map<String, Map<String, String>>>();
		final Map<String,Map<String,String>> params = new HashMap<String,Map<String,String>>();
		final Map<String,String> conditions = new HashMap<String,String>();

		params.put("conditions", conditions);
		key.put("CompaniesCategory", params);

		//GRA��AS AO METODO 'toString'IMPLEMENTADO NA BEAN CompanyCategory PODEMOS CARREGOR O SPINNER COM UMA LISTA DE OBJETOS
		List<CompanyCategory> categories = CompanyCategoryBO.listAllCategories(key);
		ArrayAdapter<CompanyCategory> categoryAdapter = new 
				ArrayAdapter<CompanyCategory>(WishlistInclude.this, android.R.layout.simple_spinner_item, categories);
		spinnerCategory.setAdapter(categoryAdapter);

		/**
		 * OnSelected da Lista de CATEGORY
		 */
		spinnerCategory.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long arg3) {

				//VARIAVEL DE CATEGORIA RECEBE O OBJ DA LISTA SELECIONADO PELO USUARIO
				category  = (CompanyCategory) parent.getSelectedItem();
				Log.i("CATEGORY PARENT", category.getName());

				conditions.put("CompaniesSubCategory.category_id", String.valueOf(category.getId()));
				params.put("conditions", conditions);
				key.put("CompaniesSubCategory", params);

				//CRIANDO LISTA E ADAPTER DE SUBCATEGORIA - 
				//O CONTEUDO DA LISTA SER�� SEGUNDO O C��DIGO ENVIADO PELA LISTA DE CATEGORIA
				List<CompanySubCategory> subCategories = CompanySubCategoryBO.listAllCategories(key);
				ArrayAdapter<CompanySubCategory> subCategoryAdapter = new
						ArrayAdapter<CompanySubCategory>(WishlistInclude.this, android.R.layout.simple_spinner_item, subCategories);
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


		//CLIQUE - MENU COMPRAS
		textMenuCompras.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(intentCompras);
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	/**
	 * REFERENCIANDO VARIAVEIS �� VIEWS
	 * @return void
	 * @param 
	 */
	public void loadViews(){

		//CARREGANDO VIEWS DE USER
//		progress = (ProgressBar) findViewById(R.id.progressBarViewHeader);
		textViewNameUser = (TextView) findViewById(R.id.textViewHeader_NomeUsuario);
//		imageViewPhotoUser = (ImageView) findViewById(R.id.imageViewHeader);

		spinnerCategory = (Spinner) findViewById(R.id.spinner_includeWishCategory);
		spinnerSubCategory = (Spinner) findViewById(R.id.spinner_includeWishSubCateg);

		editTextProduto = (EditText) findViewById(R.id.editText1_includeWishProduto);
		editTextDesc = (EditText) findViewById(R.id.editText_includeWishDesc);
		editTextData = (EditText) findViewById(R.id.editText1_includeWishData);

		imageMenuCompras = (ImageView) findViewById(R.id.imageFooterCompras);
		textMenuCompras = (TextView) findViewById(R.id.textViewFooterCompras);

		imageMenuAssinaturas = (ImageView) findViewById(R.id.imageFooterAssinaturas);
		textMenuAssinaturas = (TextView) findViewById(R.id.textViewFooterAssinaturas);


		imageMenuAssinaturas.setImageResource(R.drawable.assinaturas_on);
		imageMenuCompras.setImageResource(R.drawable.compras_on);

		imageViewMenuWish = (ImageView) findViewById(R.id.imageFooterWish);
		imageViewMenuWish.setImageResource(R.drawable.wishlist_off);

	}

	/**
	 * @param view
	 * @return void
	 */
	public void addWish(View view){

		Calendar dateNow = Calendar.getInstance();
		Calendar dateLimit = Calendar.getInstance();

		SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy"); 

		wishlist = new Wishlist();

		category = (CompanyCategory) spinnerCategory.getSelectedItem();
		subCategory = (CompanySubCategory) spinnerSubCategory.getSelectedItem();

		String produto = editTextProduto.getText().toString();
		String descricao = editTextDesc.getText().toString();
		//RECEBENDO E TRATANDO DATA
		String dataFinal = editTextData.getText().toString();
		Date date = null;
		try {
			date = formatador.parse(dataFinal);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		dateLimit.setTime(date);

		//SETANDO VALORES PARA WISHLIST
		wishlist.setCategory(category);
		wishlist.setSubCategory(subCategory);
		wishlist.setUser(user);
		wishlist.setName(produto);
		wishlist.setDescription(descricao);
		wishlist.setDateRegister(dateNow);
		wishlist.setEndsAt(dateLimit);

		//ITEM PARA TESTE - EXCLUIR AP��S CRIAR METODO DE INCLUS��O DE WISHLIST
		Toast.makeText(view.getContext(), "CATEGORIA: " + category.getName(), Toast.LENGTH_LONG).show();
		Toast.makeText(view.getContext(), "SUBCATEGORIA: " + wishlist.getSubCategory().getName(), Toast.LENGTH_LONG).show();
		Toast.makeText(view.getContext(), "USUARIO: " + wishlist.getUser().getName(), Toast.LENGTH_LONG).show();
		Toast.makeText(view.getContext(), "PRODUTO: " + wishlist.getName(), Toast.LENGTH_LONG).show();
		Toast.makeText(view.getContext(), "DATA DE REGISTRO ".
				concat(String.valueOf(wishlist.getDateRegister().get(Calendar.DAY_OF_MONTH)) + "/"
						+ (wishlist.getDateRegister().get(Calendar.MONTH) + 1) + "/"
						+ wishlist.getDateRegister().get(Calendar.YEAR)), Toast.LENGTH_LONG).show();

		Toast.makeText(view.getContext(), "DATA LIMITE ".
				concat(String.valueOf(wishlist.getEndsAt().get(Calendar.DAY_OF_MONTH)) + "/"
						+ (wishlist.getEndsAt().get(Calendar.MONTH) + 1) + "/"
						+ wishlist.getEndsAt().get(Calendar.YEAR)), Toast.LENGTH_LONG).show();
	}

}
