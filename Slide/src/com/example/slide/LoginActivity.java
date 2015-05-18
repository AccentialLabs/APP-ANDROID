package com.example.slide;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.accential.trueone.bean.User;
import com.accential.trueone.bo.UserBO;
import com.accential.trueone.utils.CryptographyMD5Util;
import com.accential.trueone.utils.OrientacaoUtils;
import com.accential.trueone.utils.SessionControl;

/**
 * Código mais simples e descomplicado Login mais rápido - NO BUGS
 * 
 * @author Matheus Odilon - accentialbrasil
 * @version 2.0
 */
public class LoginActivity extends Activity {

	private EditText etEmail;
	private EditText etPass;
	private Button btnLogar;
	private Button btnCreateUser;
	private Button btnRecovPass;
	private TextView tvStatusLogin;
	private SharedPreferences loggedUser;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.x_activity_login);
		// verificamos se aplicacao esta sendo usada em um smartphone ou tablet
		String typeUser = getResources().getString(R.string.isTablet);
		if (typeUser.equals("false")) {
			Log.e("tstando list de busca",
					"Estamos abrindo o app de um smartphe");
			OrientacaoUtils.setOrientationVertical(this);
		} else {
			Log.e("tstando lista de busca",
					"Estamos abrindo o app de um tablet");
			OrientacaoUtils.setOrientationHorizontal(this);
		}
		// recuperamos as preferencias do usuario
		// caso já tenha sido salva fazemos o "login automatico"
		// caso não, seguimos o fluxo normalmente
		loggedUser = this.getSharedPreferences(SessionControl.USER_LOGGED, 0);

		// SharedPreferences vazio
		if (loggedUser.getAll().isEmpty()) {

			etEmail = (EditText) findViewById(R.id.etEmail);
			etPass = (EditText) findViewById(R.id.etPassword);
			btnLogar = (Button) findViewById(R.id.btnLogar);
			tvStatusLogin = (TextView) findViewById(R.id.tvStatusLogin);
			btnCreateUser = (Button) findViewById(R.id.btnLoginCreateNew);
			btnRecovPass = (Button) findViewById(R.id.btnLoginRecovPass);

			// novo usuario
			btnCreateUser.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(v.getContext(),
							CreateUserActivity.class);
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
						tvStatusLogin.setText("Ops... Erro no Login");
					} else {
						// caso retorno seja positivo,
						// fazemos o login e salvamos o usuario no
						// SharedPreferences
						User logged = firtsUser.get(0);
						tvStatusLogin.setText("Bem vindo " + logged.getName());

						String stringUser = SessionControl
								.encodeSessionUser(logged);
						SharedPreferences.Editor editor = loggedUser.edit();
						editor.putString("user", stringUser);
						editor.commit();

						Intent intent = new Intent(view.getContext(),
								AdventaActivity.class);
						startActivity(intent);
					}
				}
			});

			// recupera senha
			btnRecovPass.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(LoginActivity.this,
							RecoveryPassActivity.class);
					startActivity(intent);
				}
			});

			// SharedPreferences salvo usuario
		} else {
			Intent intent = new Intent(this, AdventaActivity.class);
			startActivity(intent);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
