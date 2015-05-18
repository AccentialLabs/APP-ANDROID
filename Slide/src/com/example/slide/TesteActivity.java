package com.example.slide;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.accential.trueone.dao.UserDAO;

public class TesteActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activiy_teste);

		Log.e("EXECUTANDO MUDANDO USERS USING", "ALTERANDO USERS USINGS");
		UserDAO dao = new UserDAO();
		dao.userUsing(289);

		Log.e("EXECUTANDO MUDANDO USERS USING",
				"***********************ALTER USER USING EXECUTED");
	}

}
