package com.example.slide;

import java.util.List;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.accential.trueone.bean.User;
import com.accential.trueone.bo.UserBO;
import com.accential.trueone.dao.UserDAO;
import com.accential.trueone.service.CreateUserService;
import com.accential.trueone.utils.OrientacaoUtils;
import com.accential.trueone.utils.SessionControl;
import com.parse.ParseInstallation;

@SuppressWarnings("all")
public class CreateUserActivity extends Activity {

	private EditText userName;
	private EditText userEmail;
	private EditText userPass;
	private EditText userConfirmPass;
	private Button btnCreate;
	private Button btnCancel;
	private ProgressBar pbCreate;
	private List<User> returnCreate;
	private CreateUserResponseReceiver receiver;
	private SharedPreferences loggedUser;

	private ImageView ivError;
	private TextView tvError;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.x_activity_login_create);

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

		userName = (EditText) findViewById(R.id.etCreateName);
		userEmail = (EditText) findViewById(R.id.etCreateEmail);
		userPass = (EditText) findViewById(R.id.etCreatePass);
		userConfirmPass = (EditText) findViewById(R.id.etCreateConfirm);
		btnCancel = (Button) findViewById(R.id.btnCreateCancel);
		btnCreate = (Button) findViewById(R.id.btnCreateUser);
		pbCreate = (ProgressBar) findViewById(R.id.pbCreateUser);

		ivError = (ImageView) findViewById(R.id.ivCadErrorr);
		tvError = (TextView) findViewById(R.id.tvCadError);

		pbCreate.setVisibility(View.GONE);

		// cancela
		btnCancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				// volta para login
				Intent intent = new Intent(CreateUserActivity.this,
						FBActivity.class);
				startActivity(intent);
			}
		});

		// create
		btnCreate.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// cadastra usuario
				String pass = userPass.getText().toString();
				String confirmPass = userConfirmPass.getText().toString();
				String email = userEmail.getText().toString();
				String name = userName.getText().toString();

				if (pass.equals(confirmPass)) {
					// verifica se usuario ja existe
					boolean verify = UserBO.verifyUser(email);
					if (verify) {
						tvError.setText("Email já foi cadastro!");
						ivError.setVisibility(View.VISIBLE);
						tvError.setVisibility(View.VISIBLE);
						// Toast.makeText(
						// CreateUserActivity.this,
						// "Já existe um usuario cadastrado com esse email. Verifique e tente novamente.",
						// Toast.LENGTH_LONG).show();
						userEmail.requestFocus();
					} else {

						pbCreate.setVisibility(View.VISIBLE);
						// caso esteja tudo certo chamamos o service para
						// efetivar a criaçao do usuario
						Intent commentsIntent = new Intent(
								CreateUserActivity.this,
								CreateUserService.class);
						commentsIntent.putExtra(
								CreateUserService.PARAM_CREATE_USER_EMAIL,
								email);
						commentsIntent.putExtra(
								CreateUserService.PARAM_CREATE_USER_NAME, name);
						commentsIntent.putExtra(
								CreateUserService.PARAM_CREATE_USER_PASS, pass);
						CreateUserActivity.this.startService(commentsIntent);

						// preparando o receiver para o retorno do service
						IntentFilter filter = new IntentFilter(
								CreateUserResponseReceiver.ACTION_RESP_CREATE_USER);
						filter.addCategory(Intent.CATEGORY_DEFAULT);
						receiver = new CreateUserResponseReceiver();
						CreateUserActivity.this.registerReceiver(receiver,
								filter);

						// enviando email - teste
						Log.e("TESTE DO EMAIL",
								"ENVIANDO EMAIL TESTE****************");
						UserDAO dao = new UserDAO();
						dao.sendEmailNewUser(name, email, pass);

						Intent intent = new Intent(CreateUserActivity.this,
								AdventaActivity.class);
						startActivity(intent);
					}

				} else {

					tvError.setText("As Senhas não são Iguais!");
					ivError.setVisibility(View.VISIBLE);
					tvError.setVisibility(View.VISIBLE);
					// Toast.makeText(
					// CreateUserActivity.this,
					// "A confirmação da senha não bate. Corrija e tente novamente.",
					// Toast.LENGTH_LONG).show();

					userConfirmPass.requestFocus();
				}
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public class CreateUserResponseReceiver extends BroadcastReceiver {
		public static final String ACTION_RESP_CREATE_USER = "com.mamlambo.intent.action.MESSAGE_PROCESSED_CREATE_USER";

		@Override
		public void onReceive(Context context, Intent intent) {

			loggedUser = context.getSharedPreferences(
					SessionControl.USER_LOGGED, 0);

			Log.e("USUARIO CRIADO", "USUARIO CRIADO");
			Log.e("executando o service", "executando service 3");

			Bundle parameters = intent.getExtras();
			List<User> returnList = (List<User>) parameters
					.getSerializable(CreateUserService.PARAM_CREATE_RETURN_LIST);

			User logged = new User();
			logged = returnList.get(0);

			String stringUser = SessionControl.encodeSessionUser(logged);
			SharedPreferences.Editor editor = loggedUser.edit();
			editor.putString("user", stringUser);
			editor.commit();

			// criando o Using User para salvar info que usuario está se logando
			// de um mobile android
			String android_id = Secure.getString(
					CreateUserActivity.this.getContentResolver(),
					Secure.ANDROID_ID);

			UserBO.createUserUsing(logged.getId(), android_id);

		}
	}
}
