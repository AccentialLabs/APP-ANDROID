package com.accential.trueone;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.service.textservice.SpellCheckerService.Session;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.accential.trueone.bean.User;
import com.accential.trueone.bo.UserBO;
import com.accential.trueone.utils.CryptographyMD5Util;
import com.accential.trueone.utils.OrientacaoUtils;
import com.example.slide.R;

/**
 * Classe responsavel por cuidar do login do cliente/usuario
 * 
 * @author Matheus Odilon - accentialbrasil
 */
@SuppressWarnings("all")
public class LoginActivity extends Activity {

	public static final String PREFS_USER = "Preferences";
	private EditText user;
	private EditText password;
	private TextView cadastraNovo;

	private ImageView erroLoginImg;
	private TextView erroLoginTxt;

	private TextView textViewTermsAndConditions;

	private CheckBox checkConect;

	public boolean logado = false;
	static int id;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main_login);

		// VERIFICANDO SE �� UM TABLET
		String type = getResources().getString(R.string.isTablet);
		// CASO N��O SEJA UM TABLET O USUARIO SER�� 'FORCADO' A USAR A TELA
		// SOMENTE PORTRAIT
		// CASO SEJA ELE PODERA USAR NAS DUAS ORIENTACOES
		if (type.equals("false")) {
			Log.i("TESTE", "IS TABLET= " + type);
			OrientacaoUtils.setOrientationVertical(this);
		}

		// CODIGO PARA FIXAR ORIENTA��CAO - DEVE SER INSERIDO DO MANIFEST
		// android:screenOrientation="portrait"

		checkConect = (CheckBox) findViewById(R.id.checkBox_conect);
		user = (EditText) findViewById(R.id.editText1);
		password = (EditText) findViewById(R.id.editText2);

		textViewTermsAndConditions = (TextView) findViewById(R.id.textView_termosDeUso);

		erroLoginImg = (ImageView) findViewById(R.id.imageView_dadosIncorretos);
		erroLoginTxt = (TextView) findViewById(R.id.textView_dadosIncorretos);

		erroLoginImg.setVisibility(View.GONE);
		erroLoginTxt.setVisibility(View.GONE);

		textViewTermsAndConditions.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				Log.i("CLIQUE NO TEXTO", "TESTE DO CLIQUE");
				Intent intent = new Intent(view.getContext(),
						TermsAndConditionsActivity.class);
				startActivity(intent);
			}
		});

		SharedPreferences settings = getSharedPreferences(PREFS_USER, 0);
		Log.i("teste",
				"SETTINGS DO SHAREDPREFERENCES: "
						+ String.valueOf(settings.getAll()));
		if (settings.getAll().isEmpty()) {
			Log.i("teste", "SETTINGS EST�� VAZIO!!!");
		} else {

			Log.i("teste", "SETTINGS N��O EST�� VAZIO!!!");

			user.setText(settings.getString("PrefUsuario", ""));
			password.setText(settings.getString("PrefSenha", ""));
			String automaticUser = "";
			String automaticPassword = "";

			if (!user.getText().toString().equals("")
					&& !password.getText().toString().equals("")) {
				automaticUser = user.getText().toString();
				automaticPassword = password.getText().toString();
				Log.i("teste", "Usuario: " + automaticUser + " - " + "Senha: "
						+ automaticPassword);
				Log.i("teste", "INICIA LOGIN AUTOMATICO --> ");
				automaticLogin();

			}

		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	/**
	 * @author Matheus Odilon - accentialbrasil Inicia o login automaticamente
	 *         sem a necessidade de clique em qualquer bot��o. Inicia somente
	 *         caso usuario j�� tenha logado alguma vez
	 */
	public void automaticLogin() {
		Intent intent = new Intent(this, MainActivity.class);
		Bundle parameters = new Bundle();

		List<User> users;
		User objUser = new User();
		String strUsuario = user.getText().toString();
		String strSenha = password.getText().toString();

		// RECEBE TEXTO DA SENHA E CRIPTOGRAFA PARA MD5
		String strSenhaMD5 = CryptographyMD5Util.encrypt(strSenha);

		// MAPS RESPONSAVEIS POR LEVAREM O CONTEUDO PARA PESQUISA DE USUARIO NA
		// API
		Map<String, Map<String, Map<String, String>>> key = new HashMap<String, Map<String, Map<String, String>>>();
		Map<String, Map<String, String>> params = new HashMap<String, Map<String, String>>();
		Map<String, String> conditions = new HashMap<String, String>();

		conditions.put("User.email", strUsuario);
		conditions.put("User.password", strSenhaMD5);
		params.put("conditions", conditions);
		key.put("User", params);

		users = UserBO.listAllUsers(key);

		if (users.size() != 0) {
			Log.i("QUANTIDADE DE REGISTROS DA LISTA",
					Integer.toString(users.size()));
			for (User user : users) {
				if (user.getEmail().equals(strUsuario)
						|| user.getPassword().equals(strSenhaMD5)) {
					Log.i("LOGIN - NOME", user.getName());
					Log.i("LOGIN - EMAIL", user.getEmail());
					Log.i("LOG USER - NAME", user.getName());
					objUser.setName(user.getName());
					objUser.setPhoto(user.getPhoto());
					objUser.setId(user.getId());
				}
				logado = true;
				erroLoginTxt.setText("OK");
				parameters.putBoolean("logado", logado);
				intent.putExtra("usuario", objUser);
				intent.putExtras(parameters);
				startActivity(intent);

			}
		} else {
			Toast.makeText(this, "Dados Inv��lidos. Tente Novamente.",
					Toast.LENGTH_LONG).show();
			erroLoginImg.setVisibility(View.VISIBLE);
			erroLoginTxt.setVisibility(View.VISIBLE);
		}

	}

	/**
	 * Metodo responsavel por fazer login do cliente/usuario realizando devidas
	 * validacoes Recebe entrada de usuario e senha
	 * 
	 * @param View
	 */
	public void login(View v) {

		Intent intent = new Intent(this, MainActivity.class);
		Bundle parameters = new Bundle();

		List<User> users;
		User objUser = new User();
		String strUsuario = user.getText().toString();
		String strSenha = password.getText().toString();

		Log.i("USUARIO", "USER: " + strUsuario + "Pass: " + strSenha);

		// RECEBE TEXTO DA SENHA E CRIPTOGRAFA PARA MD5
		String strSenhaMD5 = CryptographyMD5Util.encrypt(strSenha);

		// MAPS RESPONSAVEIS POR LEVAREM O CONTEUDO PARA PESQUISA DE USUARIO NA
		// API
		Map<String, Map<String, Map<String, String>>> key = new HashMap<String, Map<String, Map<String, String>>>();
		Map<String, Map<String, String>> params = new HashMap<String, Map<String, String>>();
		Map<String, String> conditions = new HashMap<String, String>();

		conditions.put("User.email", strUsuario);
		conditions.put("User.password", strSenhaMD5);
		params.put("conditions", conditions);
		key.put("User", params);

		Log.i("MAPA", key.toString());

		users = UserBO.listAllUsers(key);

		if (users.size() != 0) {
			Log.i("QUANTIDADE DE REGISTROS DA LISTA",
					Integer.toString(users.size()));
			for (User user : users) {
				if (user.getEmail().equals(strUsuario)
						|| user.getPassword().equals(strSenhaMD5)) {
					Log.i("LOGIN - NOME", user.getName());
					Log.i("LOGIN - EMAIL", user.getEmail());
					Log.i("LOG USER - NAME", user.getName());
					objUser.setName(user.getName());
					objUser.setPhoto(user.getPhoto());
					objUser.setId(user.getId());
				}
				logado = true;
				erroLoginTxt.setText("OK");
				parameters.putBoolean("logado", logado);
				intent.putExtra("usuario", objUser);
				intent.putExtras(parameters);
				startActivity(intent);

			}
		} else {
			erroLoginImg.setVisibility(View.VISIBLE);
			erroLoginTxt.setVisibility(View.VISIBLE);
		}

	}

	/**
	 * Metodo responsavel por fazer trativa do cliente/usuario que n��o realizar
	 * o login
	 * 
	 * @author Matheus Odilon - accentialbrasil
	 * @param v
	 * @return void
	 */
	public void noLogin(View v) {
		Log.i("STATUS DO LOGIN", String.valueOf(logado));
		logado = false;
		Intent intentNoLogin = new Intent(this, MainActivity.class);
		Bundle parameters2 = new Bundle();
		parameters2.putBoolean("logado", logado);
		intentNoLogin.putExtras(parameters2);
		startActivity(intentNoLogin);
	}

	public void cadastraNovo(View v) {
		Intent intent = new Intent(v.getContext(), CreateUserActivity.class);
		startActivity(intent);
	}

	public void esqueciMinhaSenha(View v) {
		Intent intent = new Intent(this, RecoveryPasswordActivity.class);
		startActivity(intent);
	}

	public void termsAndCondition(View v) {
		Log.i("TESTE", "clique no texto");
		Intent intent = new Intent(this, TermsAndConditionsActivity.class);
		startActivity(intent);
	}

	/**
	 * TESTE DO LOGIN PELO FACEBOOK BEGIN
	 */

	/**
	 * TESTE DO LOGIN PELO FACEBOOK END
	 */

	public void logar2(String email) {

		List<User> users = null;
		User objUser = new User();
		Map<String, Map<String, Map<String, String>>> key = new HashMap<String, Map<String, Map<String, String>>>();
		Map<String, Map<String, String>> params = new HashMap<String, Map<String, String>>();
		Map<String, String> conditions = new HashMap<String, String>();

		conditions.put("User.email", email);
		params.put("conditions", conditions);
		key.put("User", params);

		users = UserBO.listAllUsers(key);
		int i = 0;
		if (users.size() != 0) {
			Log.i("QUANTIDADE DE REGISTROS DA LISTA",
					Integer.toString(users.size()));
			for (User user : users) {
				if (user.getEmail().equals(email)) {
					if (i == 0) {
						Log.i("LOGIN - NOME", user.getName());
						Log.i("LOGIN - EMAIL", user.getEmail());
						Log.i("LOG USER - NAME", user.getName());
						objUser.setName(user.getName());
						objUser.setPhoto(user.getPhoto());
						objUser.setId(user.getId());
						objUser.setAddress(user.getAddress());
						objUser.setCity(user.getCity());
						objUser.setComplement(user.getComplement());
						objUser.setDistrict(user.getDistrict());
						objUser.setNumber(user.getNumber());
						objUser.setState(user.getState());
						objUser.setZip_code(user.getZip_code());
						i++;
					}
				}
			}
			logado = true;
			erroLoginTxt.setText("OK");

			Intent intent = new Intent(this, MainActivity.class);
			Bundle parameters = new Bundle();

			parameters.putBoolean("logado", logado);
			intent.putExtra("usuario", objUser);
			intent.putExtras(parameters);
			startActivity(intent);
		}
	}

	/** Chamado quando a Activity �� encerrada. */
	@Override
	protected void onStop() {
		super.onStop();
		/**
		 * Armazena os dados apenas se o checkbox estiver setado
		 */
		if (checkConect.isChecked() == true) {

			SharedPreferences settings = getSharedPreferences(PREFS_USER, 0);
			SharedPreferences.Editor editor = settings.edit();
			editor.putString("PrefUsuario", user.getText().toString());
			editor.putString("PrefSenha", password.getText().toString());

			// Confirma a grava����o dos dados
			editor.commit();
		} else {
			this.getSharedPreferences(PREFS_USER, 0).edit().clear().commit();
			Log.i("teste", "BOX IS NOT CHECKED - REMOVED");
		}
	}

	// /////////////
	/*
	 * private static Session openActiveSession(Activity activity, boolean
	 * allowLoginUI, Session.StatusCallback statusCallback) { OpenRequest
	 * openRequest = new OpenRequest(activity);
	 * openRequest.setPermissions(Arrays.asList("user_birthday", "email",
	 * "user_religion_politics", "user_relationships",
	 * "user_relationship_details")); openRequest.setCallback(statusCallback);
	 * Session session = new Session.Builder(activity).build();
	 * if(SessionState.CREATED_TOKEN_LOADED.equals(session.getState()) ||
	 * allowLoginUI) { Session.setActiveSession(session);
	 * session.openForRead(openRequest);
	 * Log.i("TESTE DO METODOS - RETORNO - OPENREQUEST",
	 * String.valueOf(openRequest));
	 * Log.i("TESTE DO METODOS - RETORNO - SESSION", String.valueOf(session));
	 * Log.i("TESTE DO METODOS - RETORNO", "RETORNA SESSAO"); return session; }
	 * Log.i("TESTE DO METODO - RESTORNO", "RETORNA NULO"); return null; }
	 */

	// TESTE DE LOGIN COM FBK
	public void onFBLoginClick(View view) {

		// TESTE - APAGAR
		Log.i("TESTE METODO", "1");

		 //openActiveSession(this, true, statusCallback);
	}
	
//	 Session.StatusCallback statusCallback = new Session.StatusCallback() {
//	 }
	 /* 
	 * @Override public void call(final Session session, SessionState state,
	 * Exception exception) { Log.i("TESTE METODO", "2"); if(session.isOpened())
	 * { Log.i("TESTE METODO", "3"); Request.executeMeRequestAsync(session, new
	 * Request.GraphUserCallback() {
	 * 
	 * @Override public void onCompleted(GraphUser user, Response response) {
	 * 
	 * Log.i("TESTE METODO", "4");
	 * 
	 * //VERIFICA SE USUARIO J�� EXISTE
	 * 
	 * if(user != null) {
	 * 
	 * //txtUserName.setText(session.getAccessToken()); String gender =
	 * user.getProperty("gender").toString(); String email =
	 * user.getProperty("email").toString(); Log.i("TESTE METODO - USUARIO",
	 * "USUARIO NAO �� NULO");
	 * 
	 * if(UserBO.verifyUser(email) == false){ //TRATANDO DATA SimpleDateFormat
	 * formatador = new SimpleDateFormat("dd/MM/yyyy");
	 * 
	 * String birthday = user.getBirthday().toString(); Calendar dateBirth =
	 * Calendar.getInstance(); Date date = null; try { date =
	 * formatador.parse(birthday); } catch (java.text.ParseException e) { //
	 * TODO Auto-generated catch block e.printStackTrace(); }
	 * dateBirth.setTime(date);
	 * 
	 * //USUARIO - TRUEONE User userT1 = new User();
	 * userT1.setName(user.getName());
	 * userT1.setGender(user.getProperty("gender").toString());
	 * userT1.setPassword(user.getProperty("email").toString());
	 * userT1.setEmail(user.getProperty("email").toString());
	 * userT1.setBirthday(dateBirth);
	 * 
	 * 
	 * //USUARIO - PERFIL FACEBOOK FacebookProfile fb = new FacebookProfile();
	 * //RELATIONSHIP STTAUS if(!(user.getProperty("relationship_status") ==
	 * null)){
	 * fb.setRelationshipStatus(user.getProperty("relationship_status").toString
	 * ()); }else{ fb.setRelationshipStatus(""); } //RELIGION
	 * if(!(user.getProperty("religion") == null)){
	 * fb.setReligion(user.getProperty("religion").toString()); }else{
	 * fb.setReligion(""); } //POLITICAL if(!(user.getProperty("political") ==
	 * null)){ fb.setPolitical(user.getProperty("political").toString()); }else{
	 * fb.setPolitical(""); } //LOCATION
	 * if(!(user.getLocation().getProperty("name") == null)){
	 * fb.setLocation(user.getLocation().getProperty("name").toString()); }else{
	 * fb.setLocation(""); } fb.setFacebookId(user.getId());
	 * fb.setName(user.getName()); fb.setFirstName(user.getFirstName());
	 * fb.setLastName(user.getLastName());
	 * fb.setEmail(user.getProperty("email").toString());
	 * fb.setGender(user.getProperty("gender").toString());
	 * fb.setProfileLink(user.getLink()); fb.setBirthday(dateBirth);
	 * 
	 * //FacebookProfileBO.createFacebookProfile(fb);
	 * 
	 * Log.i("ENTROU NO METODO LOGIN AUTO","AQUI ELE CADASTRA NOVO!!!");
	 * 
	 * Log.i("NOME DO USUARIO", user.getName()); Log.i("ID DO USUARIO",
	 * user.getId()); Log.i("LOCATION DO USUARIO",
	 * user.getLocation().getProperty("name").toString());
	 * Log.i("RELATIONSHIP DO USUARIO", fb.getRelationshipStatus());
	 * Log.i("RELIGION DO USUARIO", fb.getReligion());
	 * Log.i("POLITICAL DO USUARIO", fb.getPolitical());
	 * 
	 * Log.i("ESTA CRIANDO NOVO", "CRIANDO NOVO");
	 * 
	 * //saveUserData(user.getId(), user.getName(), user.getBirthday(),
	 * user.asMap().get("email").toString());
	 * //saveAccessToken(session.getAccessToken());
	 * //getFacebookUserProfilePicture(session.getAccessToken()); }else{
	 * 
	 * //LOGA DIRETO logar2(email); Log.i("teste", email);
	 * Log.i("TESTE LOGAR - ELSE", "ENTROU NO ELSE = LOGAR2(EMAIL)");
	 * 
	 * } }else{ Log.i("TESTE METODO - USUARIO", "USUARIO �� NULO"); } } }); }
	 * Log.i("TESTE METODO - SESSAO", "SESSAO NAO ABERTA"); } };
	 */

}