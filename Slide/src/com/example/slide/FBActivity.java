package com.example.slide;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.R;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.accential.trueone.bean.FacebookProfile;
import com.accential.trueone.bean.User;
import com.accential.trueone.bo.FacebookProfileBO;
import com.accential.trueone.bo.UserBO;
import com.accential.trueone.dao.UserDAO;
import com.accential.trueone.utils.CryptographyMD5Util;
import com.accential.trueone.utils.OrientacaoUtils;
import com.accential.trueone.utils.SessionControl;
import com.facebook.Response;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;
import com.facebook.widget.LoginButton.UserInfoChangedCallback;

public class FBActivity extends FragmentActivity {

	private EditText etEmail;
	private EditText etPass;
	private Button btnLogar;
	private Button btnCreateUser;
	private Button btnRecovPass;
	// private TextView tvStatusLogin;
	private SharedPreferences loggedUser;
	private LoginButton loginBtn;
	private SimpleDateFormat formatador;
	// private Button postImageBtn;
	// private Button updateStatusBtn;
	// private TextView userName;

	private UiLifecycleHelper uiHelper;
	private TextView errorWarning;
	private String continueShopping;
	private AlphaAnimation fadeIn;
	private AlphaAnimation fadeOut;
	private TextView terms;
	// Arrays.asList(
	// "publish_actions", "email", "user_religion_politics",
	// "user_relationships", "user_relationship_details", "user_hometown",
	// "user_location", "user_birthday", "user_about_me");

	private static final List<String> PERMISSIONS = Arrays.asList("email",
			"user_about_me", "user_birthday", "user_hometown", "user_location",
			"user_relationships", "user_religion_politics");

	private static String message = "Sample status posted from android app";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		uiHelper = new UiLifecycleHelper(this, statusCallback);
		uiHelper.onCreate(savedInstanceState);
		setContentView(com.example.slide.R.layout.x_activity_fbklogin);

		// verificamos se aplicacao esta sendo usada em um smartphone ou tablet
		String typeUser = getResources().getString(
				com.example.slide.R.string.isTablet);
		if (typeUser.equals("false")) {
			Log.e("tstando list de busca",
					"Estamos abrindo o app de um smartphe");
			OrientacaoUtils.setOrientationVertical(this);
		} else {
			Log.e("tstando lista de busca",
					"Estamos abrindo o app de um tablet");
			OrientacaoUtils.setOrientationHorizontal(this);
		}

		loggedUser = this.getSharedPreferences(SessionControl.USER_LOGGED, 0);

		fadeIn = new AlphaAnimation(0.0f, 1.0f);
		fadeIn.setDuration(1200);
		fadeIn.setFillAfter(true);

		fadeOut = new AlphaAnimation(1.0f, 0.0f);
		fadeOut.setDuration(500);
		fadeOut.setFillAfter(true);
		/**
		 * Recebemos o valor dessa váriavel atraves da tentativa de compra sem
		 * usuario estar logado. Variavel contem a oferta que usuario tentou
		 * comprar convetida em String. Após o login decodificamos essa variavel
		 * e "continuamos" o fluxo de compra do usuario
		 */
		Intent intentAn = getIntent();
		continueShopping = intentAn
				.getStringExtra(SessionControl.CONTINUE_SHOPPING);
		Log.e("", "Esse é o valor do continueShopping: " + continueShopping);

		if (loggedUser.getAll().isEmpty()) {

			// userName = (TextView) findViewById(R.id.user_name);
			etEmail = (EditText) findViewById(com.example.slide.R.id.etEmail);
			etPass = (EditText) findViewById(com.example.slide.R.id.etPassword);
			btnLogar = (Button) findViewById(com.example.slide.R.id.btnLogar);
			// tvStatusLogin = (TextView) findViewById(R.id.tvStatusLogin);
			btnCreateUser = (Button) findViewById(com.example.slide.R.id.btnLoginCreateNew);
			btnRecovPass = (Button) findViewById(com.example.slide.R.id.btnLoginRecovPass);
			errorWarning = (TextView) findViewById(com.example.slide.R.id.tvUserLoginWarning);
			// loginBtn = (LoginButton)
			// findViewById(com.example.slide.R.id.fb_login_button);
			terms = (TextView) findViewById(com.example.slide.R.id.tvLoginTermsConditions);

			// setando permissoes ao login
			// loginBtn.setReadPermissions(PERMISSIONS);

			// desaparece o aviso de erro
			etEmail.setOnFocusChangeListener(new OnFocusChangeListener() {
				@Override
				public void onFocusChange(View v, boolean hasFocus) {
					errorWarning.startAnimation(fadeOut);
					errorWarning.setVisibility(View.GONE);
				}
			});

			// desaparece o aviso de erro
			etPass.setOnFocusChangeListener(new OnFocusChangeListener() {
				@Override
				public void onFocusChange(View v, boolean hasFocus) {
					errorWarning.startAnimation(fadeOut);
					errorWarning.setVisibility(View.GONE);
				}
			});

			/*
			 * loginBtn.setUserInfoChangedCallback(new UserInfoChangedCallback()
			 * {
			 * 
			 * @Override public void onUserInfoFetched(GraphUser user) {
			 * 
			 * if (user != null) { // userName.setText("Hello, " +
			 * user.getName()); Log.e("", "Usuario logado: " +
			 * String.valueOf(user.getFirstName())); boolean testUser =
			 * UserBO.verifyUser(user.getProperty( "email").toString());
			 * 
			 * // se usuario já existe, fazemos somente login if (testUser ==
			 * true) { Log.e("LOGIN",
			 * "ESSE USUARIO JÁ EXISTE ENTÃO FARIAMOS O LOGIN");
			 * autoLogin(user.getProperty("email").toString()); } // se usuario
			 * ainda não existe, criamos um usuario else { Log.e("LOGIN",
			 * "ESSE USUARIO NÃO EXISTE, CRIAMOS O USUARIO");
			 * cadastraFacebookUser(user); } } else {
			 * 
			 * } } });
			 */

			/*
			 * postImageBtn = (Button) findViewById(R.id.post_image);
			 * postImageBtn.setOnClickListener(new OnClickListener() {
			 * 
			 * @Override public void onClick(View view) { postImage(); } });
			 * 
			 * updateStatusBtn = (Button) findViewById(R.id.update_status);
			 * updateStatusBtn.setOnClickListener(new OnClickListener() {
			 * 
			 * @Override public void onClick(View v) {
			 * 
			 * } });
			 */

			// novo usuario
			btnCreateUser.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(v.getContext(),
							CreateUserActivity.class);
					startActivity(intent);
				}
			});

			// recupera senha
			btnRecovPass.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(FBActivity.this,
							RecoveryPassActivity.class);
					startActivity(intent);
				}
			});

			// clique em termos e condicoes
			terms.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(FBActivity.this,
							TermsActivity.class);
					startActivity(intent);
				}
			});

			btnLogar.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View view) {

					String email = etEmail.getText().toString();
					String pass = CryptographyMD5Util.encrypt(etPass.getText()
							.toString());

					User user = new User();
					user.setEmail(email);
					user.setPassword(pass);

					List<User> firtsUser = UserBO.userLogin(user);
					if (firtsUser.isEmpty()) {
						// tvStatusLogin.setText("Ops... Erro no Login");
						errorWarning.setVisibility(View.VISIBLE);
						errorWarning.startAnimation(fadeIn);
					} else {
						// caso retorno seja positivo,
						// fazemos o login e salvamos o usuario no
						// SharedPreferences
						User logged = firtsUser.get(0);
						// tvStatusLogin.setText("Bem vindo " +
						// logged.getName());

						String stringUser = SessionControl
								.encodeSessionUser(logged);
						SharedPreferences.Editor editor = loggedUser.edit();
						editor.putString("user", stringUser);
						editor.commit();

						// salva info que usuario está se logando de um
						// dispositivo mobile e android
						UserBO.userUsing(logged.getId());

						if (continueShopping == null) {
							Intent intent = new Intent(view.getContext(),
									AdventaActivity.class);
							startActivity(intent);
						} else {
							// salvamos a oferta antes clicada para seguirmos o
							// fluxo da compra diretamente do detalhe da oferta
							SharedPreferences.Editor editorContinue = loggedUser
									.edit();
							editorContinue.putString(
									SessionControl.OFFER_DETAIL,
									continueShopping);
							editorContinue.commit();

							Intent intent = new Intent(view.getContext(),
									OffersDetailActivity.class);
							startActivity(intent);
						}

					}
				}
			});
			buttonsEnabled(false);

		} else {
			Intent intent = new Intent(this, AdventaActivity.class);
			startActivity(intent);
		}
	}

	/**
	 * Realiza o login quando usuario já realizou cadastro pelo facebook
	 * 
	 * @param email
	 */
	public void autoLogin(String email) {
		UserDAO dao = new UserDAO();
		User logged = dao.searchByEmail(email);
		// tvStatusLogin.setText("Bem vindo " + logged.getName());
		String stringUser = SessionControl.encodeSessionUser(logged);
		SharedPreferences.Editor editor = loggedUser.edit();
		editor.putString("user", stringUser);
		editor.commit();

		Intent intent = new Intent(FBActivity.this, AdventaActivity.class);
		startActivity(intent);
	}

	/**
	 * Caso o usuario tenta fazer login pelo facebook mais ainda não estiver
	 * cadastrado, cadastramos aqui
	 * 
	 * @param user
	 */
	public void cadastraFacebookUser(GraphUser user) {
		formatador = new SimpleDateFormat("dd/MM/yyyy");

		String birthday = "";
		birthday = "01/01/1970";

		// if (user.getBirthday().toString().equals("")) {
		// birthday = user.getBirthday().toString();
		//
		// } else {
		//
		// }

		Calendar dateBirth = Calendar.getInstance();
		Date date = null;

		try {
			date = formatador.parse(birthday);
		} catch (java.text.ParseException e) {
			e.printStackTrace();
		}

		dateBirth.setTime(date);

		User logUser = new User();
		FacebookProfile fbProfile = new FacebookProfile();

		// perfil do facebook
		logUser.setName(user.getName());

		logUser.setEmail(user.getProperty("email").toString());
		logUser.setStatus("ACTIVE");
		logUser.setBirthday(dateBirth);
		logUser.setPassword(user.getProperty("email").toString());

		// perfil do facebook
		fbProfile.setEmail(user.getProperty("email").toString());
		fbProfile.setFacebookId(user.getId());
		fbProfile.setFirstName(user.getFirstName());

		fbProfile.setName(user.getName());
		fbProfile.setProfileLink(user.getLink());

		if (user.getProperty("gender") == null) {
			fbProfile.setGender("");
			logUser.setGender("");
		} else {
			logUser.setGender(user.getProperty("gender").toString());
			fbProfile.setGender(user.getProperty("gender").toString());
		}

		fbProfile.setLastName(user.getLastName());

		if (user.getProperty("location") == null) {
			fbProfile.setLocation("");
		} else {
			fbProfile.setLocation(user.getProperty("location").toString());
		}

		if (user.getProperty("political") == null) {
			fbProfile.setPolitical("");
		} else {
			fbProfile.setPolitical(user.getProperty("political").toString());
		}

		if (user.getProperty("relationship_status") == null) {
			fbProfile.setRelationshipStatus("");
		} else {
			fbProfile.setRelationshipStatus(user.getProperty(
					"relationship_status").toString());
		}

		if (user.getProperty("religion") == null) {
			fbProfile.setReligion("");
		} else {
			fbProfile.setReligion(user.getProperty("religion").toString());
		}

		fbProfile.setBirthday(dateBirth);

		FacebookProfileBO.createFacebookProfile(fbProfile);

		// após criar fazemos o login
		UserDAO dao = new UserDAO();
		User logged = dao.searchByEmail(user.getProperty("email").toString());

		// tvStatusLogin.setText("Bem vindo " + logged.getName());
		String stringUser = SessionControl.encodeSessionUser(logged);
		SharedPreferences.Editor editor = loggedUser.edit();
		editor.putString("user", stringUser);
		editor.commit();

		Intent intent = new Intent(FBActivity.this, AdventaActivity.class);
		startActivity(intent);
	}

	private com.facebook.Session.StatusCallback statusCallback = new com.facebook.Session.StatusCallback() {
		@Override
		public void call(com.facebook.Session session, SessionState state,
				Exception exception) {
			if (state.isOpened()) {
				buttonsEnabled(true);
				Log.d("FacebookSampleActivity", "Facebook session opened");
			} else if (state.isClosed()) {
				buttonsEnabled(false);
				Log.d("FacebookSampleActivity", "Facebook session closed");
			}
		}
	};

	public void buttonsEnabled(boolean isEnabled) {
		// postImageBtn.setEnabled(isEnabled);
		// updateStatusBtn.setEnabled(isEnabled);
	}

	public void postImage() {
		if (checkPermissions()) {
			Bitmap img = BitmapFactory.decodeResource(getResources(),
					com.example.slide.R.drawable.ic_launcher);
			com.facebook.Request uploadRequest = com.facebook.Request
					.newUploadPhotoRequest(
							com.facebook.Session.getActiveSession(), img,
							new com.facebook.Request.Callback() {
								@Override
								public void onCompleted(Response response) {
									Toast.makeText(FBActivity.this,
											"Photo uploaded successfully",
											Toast.LENGTH_LONG).show();
								}
							});
			uploadRequest.executeAsync();
		} else {
			requestPermissions();
		}
	}

	public void postStatusMessage() {
		if (checkPermissions()) {
			com.facebook.Request request = com.facebook.Request
					.newStatusUpdateRequest(
							com.facebook.Session.getActiveSession(), message,
							new com.facebook.Request.Callback() {
								@Override
								public void onCompleted(Response response) {
									if (response.getError() == null)
										Toast.makeText(FBActivity.this,
												"Status updated successfully",
												Toast.LENGTH_LONG).show();
								}
							});
			request.executeAsync();
		} else {
			requestPermissions();
		}
	}

	public boolean checkPermissions() {
		com.facebook.Session s = com.facebook.Session.getActiveSession();
		if (s != null) {
			return s.getPermissions().contains("publish_actions");
		} else
			return false;
	}

	public void requestPermissions() {
		com.facebook.Session s = com.facebook.Session.getActiveSession();
		if (s != null)
			s.requestNewPublishPermissions(new com.facebook.Session.NewPermissionsRequest(
					this, PERMISSIONS));
	}

	@Override
	public void onResume() {
		super.onResume();
		uiHelper.onResume();
		buttonsEnabled(com.facebook.Session.getActiveSession().isOpened());
	}

	@Override
	public void onPause() {
		super.onPause();
		uiHelper.onPause();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		// tvStatusLogin.setText("");
		uiHelper.onDestroy();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		uiHelper.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onSaveInstanceState(Bundle savedState) {
		super.onSaveInstanceState(savedState);
		uiHelper.onSaveInstanceState(savedState);
	}

}