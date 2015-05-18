package com.accential.trueone;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import com.accential.trueone.bo.UserBO;
import com.example.slide.R;

@SuppressLint("NewApi")
public class RecoveryPasswordActivity extends Activity {

	private EditText editTextEmail;
	private TextView textViewAvisoConfirmacao;
	private TextView textViewTxtButton;
	private TextView textViewError;
	private View view;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_recovery_password);

		editTextEmail = (EditText) findViewById(R.id.editText_email);
		textViewAvisoConfirmacao = (TextView) findViewById(R.id.textView_confirmacao);

		view = findViewById(R.id.view3);
		textViewTxtButton = (TextView) findViewById(R.id.textView3);

		textViewError = (TextView) findViewById(R.id.textView_error);

		textViewError.setVisibility(View.GONE);
		textViewAvisoConfirmacao.setVisibility(View.GONE);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void recoverySenha(View v) {

		// VERIFICA SE CAIXA DE TEXTO N��O EST�� VAZIA
		if (!editTextEmail.getText().toString().isEmpty()) {
			// VERIFICA SE EMAIL EXISTE NA BASE DE DADOS
			if (UserBO.verifyUser(editTextEmail.getText().toString())) {

				textViewError.setVisibility(View.GONE);
				UserBO.recovery(editTextEmail.getText().toString());
				editTextEmail.setVisibility(View.INVISIBLE);
				textViewTxtButton.setVisibility(View.INVISIBLE);
				view.setVisibility(View.INVISIBLE);

				textViewAvisoConfirmacao.setVisibility(View.VISIBLE);

			} else {
				textViewError
						.setText("Email informado nao foi encontrado na base do Trueone.");
				textViewError.setVisibility(View.VISIBLE);
			}
		} else {
			textViewError.setText("Preencha o campo com seu email.");
			textViewError.setVisibility(View.VISIBLE);
		}

	}

	public void voltar(View v) {
		Intent intent = new Intent(this, LoginActivity.class);
		startActivity(intent);
	}

}
