package com.accential.trueone;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.accential.trueone.bean.User;
import com.accential.trueone.bo.UserBO;
import com.example.slide.R;

public class CreateUserActivity extends Activity{

	private EditText name;
	private EditText email;
	private EditText password;
	private ImageView botaoCadastra;
	private ImageView botaoCancela;
	private User user;
	private UserBO userBO;

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);								
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_create_user);		

		name = (EditText) findViewById(R.id.editText_createUserName);
		email = (EditText) findViewById(R.id.editText_createUserEmail);
		password = (EditText) findViewById(R.id.editText_createUserPassword);
		botaoCadastra = (ImageView) findViewById(R.id.imageView5);
		botaoCancela = (ImageView) findViewById(R.id.imageView6);
		user = new User();
		userBO = new UserBO();

		botaoCadastra.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {

				user.setName(name.getText().toString());
				user.setEmail(email.getText().toString());
				user.setPassword(password.getText().toString());

				userBO.createUser(user);

				Toast.makeText(view.getContext(), "Cadastro Realizado", Toast.LENGTH_LONG).show();

				Intent intent = new Intent(view.getContext(), LoginActivity.class);
				startActivity(intent);
			}
		});
		botaoCancela.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				Intent intent = new Intent(v.getContext(), LoginActivity.class);
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
