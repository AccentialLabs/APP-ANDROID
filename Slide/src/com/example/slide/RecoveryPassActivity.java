package com.example.slide;

import com.accential.trueone.bo.UserBO;
import com.accential.trueone.utils.OrientacaoUtils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.opengl.Visibility;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;

/**
 * Recura senha do usuario
 * 
 * @author Matheus Odilon - accentialbrasil
 * 
 */
public class RecoveryPassActivity extends Activity {

	private TextView tvReturn;
	private EditText etEmail;
	private Button btnSend;
	private Button btnBack;
	private TableRow tbLinha;

	private ImageView imgStatus;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.x_activity_login_password);

		// verificamos se aplicacao esta sendo usada em um smartphone ou tablet
		String type = getResources().getString(R.string.isTablet);
		if (type.equals("false")) {
			Log.e("tstando list de busca",
					"Estamos abrindo o app de um smartphe");
			OrientacaoUtils.setOrientationVertical(this);
		} else {
			Log.e("tstando lista de busca",
					"Estamos abrindo o app de um tablet");
			OrientacaoUtils.setOrientationHorizontal(this);
		}

		tvReturn = (TextView) findViewById(R.id.tvRecovPassReturn);
		etEmail = (EditText) findViewById(R.id.etRecovPass);
		btnSend = (Button) findViewById(R.id.btnRecoverPass);
		btnBack = (Button) findViewById(R.id.btnRecovVoltar);
		imgStatus = (ImageView) findViewById(R.id.imageView3);
		tbLinha = (TableRow) findViewById(R.id.tableRow1);

		// clique em recuperar a senha
		btnSend.setOnClickListener(new OnClickListener() {
			@SuppressLint("NewApi")
			@Override
			public void onClick(View view) {
				// recebendo email do edittext
				String email = etEmail.getText().toString();
				if (!etEmail.getText().toString().isEmpty()) {
					if (UserBO.verifyUser(email)) {
						UserBO.recovery(email);
						etEmail.setEnabled(false);
						etEmail.setFocusable(false);
						imgStatus
								.setImageResource(R.drawable.adventa_recovery_enviado);
						tvReturn.setText("Enviamos uma nova senha para \n"
								+ email);
						btnSend.setVisibility(View.GONE);
						etEmail.setVisibility(View.INVISIBLE);
						tbLinha.setVisibility(View.INVISIBLE);
						btnBack.setVisibility(View.VISIBLE);
					} else {
						tvReturn.setText("Ops! \n Esse email não foi encontrado \n em nossa base");
					}
				} else {
					tvReturn.setText("Insira o email.");
				}
			}
		});

		// clique em voltar
		btnBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(RecoveryPassActivity.this,
						FBActivity.class);
				startActivity(intent);
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
