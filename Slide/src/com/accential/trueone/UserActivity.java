package com.accential.trueone;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
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
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.accential.trueone.bean.User;
import com.accential.trueone.bo.UserBO;
import com.accential.trueone.service.CompaniesInvitationsService;
import com.accential.trueone.service.OfferNewsIntentService;
import com.accential.trueone.utils.Mask;
import com.accential.trueone.utils.OrientacaoUtils;
import com.example.slide.R;
import com.loopj.android.image.SmartImageView;

/**
 * Permite inser����o e edi����o dos dados cadastrais do cliente
 * @author accentialbrasil
 *
 */
public class UserActivity extends Activity {

	private SmartImageView userPhoto;
	private EditText userName;
	private EditText userBirthday;
	private Spinner userGender;
	private Spinner userUf;
	private EditText userEmail;
	private EditText userAddress;
	private EditText userNumber;
	private EditText userDistrict;
	private EditText userComplement;
	//private EditText userUf;
	private EditText userCity;
	private EditText userCep;
	private User user;
	private List<String> gender;
	private List<String> states;
	private TextView textViewNameUser;
	private Map<String, Object> newsMap;
	private ImageView newsImg;
	private TextView qtdAvisoNovidade;

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
	private ImageView imageMenuDashboard;
	private TextView textMenuDashboard;
	private boolean logado = true;

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);								
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_user_registration);

		//DETALHE DESSE CODIGO NO LoginActivity
		String type = getResources().getString(R.string.isTablet);
		if(type.equals("false")){
			OrientacaoUtils.setOrientationVertical(this);
		}

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
		imageMenuDashboard = (ImageView) findViewById(R.id.imageFooterInvitations);
		textMenuDashboard = (TextView) findViewById(R.id.textViewFooterInvitations);

		userPhoto = (SmartImageView) findViewById(R.id.imageView_userPhoto);
		userName = (EditText) findViewById(R.id.editText_userName);
		userBirthday = (EditText) findViewById(R.id.editText_userBirthday);
		userEmail = (EditText) findViewById(R.id.editText_userEmail);
		userAddress = (EditText) findViewById(R.id.editText_userAddress);
		userNumber = (EditText) findViewById(R.id.editText_userNumerAddress);
		userDistrict = (EditText) findViewById(R.id.editText_userDistrictAddress);
		userComplement = (EditText) findViewById(R.id.editText_userComplementsAddress);
		//userUf = (EditText) findViewById(R.id.editText_userUFAddress);
		userCity = (EditText) findViewById(R.id.editText_userCityAddress);
		userCep = (EditText) findViewById(R.id.editText_userCEPAddress);
		userGender = (Spinner) findViewById(R.id.spinner_userGender);
		textViewNameUser = (TextView) findViewById(R.id.textViewHeader_NomeUsuario);
		newsImg = (ImageView) findViewById(R.id.imageView_avisoConvite);
		qtdAvisoNovidade = (TextView) findViewById(R.id.textView_qtdConvites);
		userUf = (Spinner) findViewById(R.id.spinner_userUFAddress);
		gender = new ArrayList<String>();
		states = new ArrayList<String>();

		Intent intent = getIntent();
		User user2 = (User) getIntent().getSerializableExtra("user");
		Bundle bundle = intent.getExtras();

		newsMap = (Map<String, Object>) bundle.getSerializable("newsMap");
		qtdAvisoNovidade.setText(String.valueOf(newsMap.get(OfferNewsIntentService.MAP_TOTAL_QTD)));

		Log.i("teste", "MAPA: " + String.valueOf(newsMap.size()));

		userBirthday.addTextChangedListener(Mask.insert("##/##/####", userBirthday));

		//gender
		gender.add("Selecione");
		gender.add("Masculino");
		gender.add("Feminino");

		//states
		states.add("AC");
		states.add("AL");
		states.add("AP");
		states.add("AM");
		states.add("BA");
		states.add("CE");
		states.add("DF");
		states.add("ES");
		states.add("GO");
		states.add("MA");
		states.add("MT");
		states.add("MS");
		states.add("MG");
		states.add("PA");
		states.add("PB");
		states.add("PR");
		states.add("PE");
		states.add("PI");
		states.add("RJ");
		states.add("RN");
		states.add("RS");
		states.add("RO");
		states.add("RR");
		states.add("SC");
		states.add("SP");
		states.add("SE");
		states.add("TO");

		user = UserBO.searchById(user2.getId());

		textViewNameUser.setText(user.getName());

		loadViewsInfo();

		Log.i("teste", "Nome: " + user.getName() + "\n Email: " + user.getEmail() + "\n Address: " + user.getAddress() );


		Log.i("teste", String.valueOf(user.getBirthday()));

		if(user.getBirthday() != null){
			if(user.getBirthday().get(Calendar.DAY_OF_MONTH) != 00){
				userBirthday.setText(user.getBirthday().get(Calendar.DAY_OF_MONTH) + "/" + 
						(user.getBirthday().get(Calendar.DAY_OF_MONTH) + 1) + "/" + user.getBirthday().get(Calendar.YEAR));
			}
		}

		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, gender);
		ArrayAdapter<String> spinnerArrayAdapter = arrayAdapter;
		spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
		userGender.setAdapter(spinnerArrayAdapter);

		ArrayAdapter<String> arrayAdapterUf = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, states);
		ArrayAdapter<String> spinnerArrayAdapterUf = arrayAdapterUf;
		spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
		userUf.setAdapter(spinnerArrayAdapterUf);

		if(user.getGender().equals("male")){
			userGender.setSelection(1);
		}else if(user.getGender().equals("female")){
			userGender.setSelection(2);
		}
		int i = 0;
		for(String s : states){

			if( s.equals(user.getState())){
				userUf.setSelection(i);
			}
			i++;
		}


		//clique em novidades
		newsImg.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intentNews = new Intent(UserActivity.this, NewsActivity.class);
				intentNews.putExtra("user", user);
				Bundle bundle = new Bundle();
				bundle.putSerializable(OfferNewsIntentService.PARAM_OUT_MAP_NEWS, (Serializable) newsMap);
				intentNews.putExtras(bundle);
				startActivity(intentNews);
			}
		});

		/**
		 * MENU
		 */
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
				imageMenuDash.setImageResource(R.drawable.dashboard_off);
				imageMenuOferta.setImageResource(R.drawable.ofertas_off);

				textMenuCompras.setTextColor(Color.rgb(255, 117, 24));
				textMenuWish.setTextColor(Color.rgb(105, 105, 105));
				textMenuDash.setTextColor(Color.rgb(105, 105, 105));
				textMenuAssinaturas.setTextColor(Color.rgb(105, 105, 105));
				textMenuOferta.setTextColor(Color.rgb(105, 105, 105));

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
				imageMenuDash.setImageResource(R.drawable.dashboard_off);
				imageMenuOferta.setImageResource(R.drawable.ofertas_off);

				textMenuAssinaturas.setTextColor(Color.rgb(255, 117, 24));
				textMenuWish.setTextColor(Color.rgb(105, 105, 105));
				textMenuDash.setTextColor(Color.rgb(105, 105, 105));
				textMenuCompras.setTextColor(Color.rgb(105, 105, 105));
				textMenuOferta.setTextColor(Color.rgb(105, 105, 105));
				//MUDEI AQUI - CompaniesUserActivity

				Intent intentC = new Intent(v.getContext(), SignaturesActivity.class);
				intentC.putExtra("user", user);
				Bundle parameters = new Bundle();
				parameters.putSerializable(CompaniesInvitationsService.PARAM_OUT_INVITATIONS, (Serializable) newsMap);
				intentC.putExtras(parameters);
				startActivity(intentC);
			}
		});

		//SELECIONA ITENS DO MENU
		//4 - OFERTAS
		textMenuOferta.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				imageMenuWish.setImageResource(R.drawable.wishlist_off);
				imageMenuAssinaturas.setImageResource(R.drawable.assinaturas_off);
				imageMenuCompras.setImageResource(R.drawable.compras_off);
				imageMenuOferta.setImageResource(R.drawable.ofertas_on);
				imageMenuDash.setImageResource(R.drawable.dashboard_off);

				textMenuAssinaturas.setTextColor(Color.rgb(105, 105, 105));
				textMenuWish.setTextColor(Color.rgb(105, 105, 105));
				textMenuOferta.setTextColor(Color.rgb(255, 117, 24));
				textMenuCompras.setTextColor(Color.rgb(105, 105, 105));
				textMenuDash.setTextColor(Color.rgb(105, 105, 105));

				Intent intentD = new Intent(v.getContext(), MainActivity.class);
				Bundle bundle = new  Bundle();
				bundle.putBoolean("logado", true);
				intentD.putExtras(bundle);
				intentD.putExtra("usuario", user);
				startActivity(intentD);
			}
		});

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

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	/**
	 * Atualiza informa����es do usuario 
	 */
	public void updateUser(View v){

		User myUser = new User();
		myUser.setId(user.getId());
		myUser.setAddress(userAddress.getText().toString());
		myUser.setCity(userCity.getText().toString());
		myUser.setComplement(userComplement.getText().toString());
		myUser.setDistrict(userDistrict.getText().toString());
		myUser.setEmail(userEmail.getText().toString());
		myUser.setName(userName.getText().toString());
		myUser.setNumber(userNumber.getText().toString());
		myUser.setState(states.get(userUf.getSelectedItemPosition()));
		myUser.setZip_code(userCep.getText().toString());

		if(userGender.getSelectedItemPosition() == 1){
			myUser.setGender("male");
			Log.i("teste", "Gender �� masculino");
		}else if(userGender.getSelectedItemPosition() == 2){
			myUser.setGender("Gender �� feminino");
		}
		UserBO.uploadUser(myUser);
		Log.i("teste", "EXECUTOU UPLOAD");
		Toast.makeText(this, "Informa����es salvas!", Toast.LENGTH_LONG).show();
	}

	public void loadViewsInfo(){

		//Setando valores aos edits 
		Log.i("teste", "minha foto: " + user.getPhoto());
		userPhoto.setImageUrl(user.getPhoto());
		userName.setText(user.getName());
		userEmail.setText(user.getEmail());
		userAddress.setText(user.getAddress());
		userNumber.setText(user.getNumber());
		userDistrict.setText(user.getDistrict());
		userComplement.setText(user.getComplement());
		//userUf.setText(user.getState());
		userCity.setText(user.getCity());
		userCep.setText(user.getZip_code());

	}

	public void cancel(View v){
		loadViewsInfo();
	}
	
}
