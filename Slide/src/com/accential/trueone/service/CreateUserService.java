package com.accential.trueone.service;

import java.io.Serializable;
import java.util.List;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.accential.trueone.bean.User;
import com.accential.trueone.bo.UserBO;
import com.accential.trueone.utils.CryptographyMD5Util;
import com.example.slide.CreateUserActivity.CreateUserResponseReceiver;

/**
 * Service responsavel pela criaçao do usuario
 * 
 * @author Matheus Odilon - accentialbrasil
 * 
 */
public class CreateUserService extends IntentService {

	// a receber
	public static final String PARAM_CREATE_USER_NAME = "userName";
	public static final String PARAM_CREATE_USER_EMAIL = "userEmail";
	public static final String PARAM_CREATE_USER_PASS = "userPass";

	// a enviar
	public static final String PARAM_CREATE_RETURN_LIST = "userReturn";

	public CreateUserService() {
		super("CreateUserService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {

		Log.e("executando o service", "executando service 1");
		// paramentros recebidos
		User user = new User();
		String userName = intent.getStringExtra(PARAM_CREATE_USER_NAME);
		String userEmail = intent.getStringExtra(PARAM_CREATE_USER_EMAIL);
		String userPass = intent.getStringExtra(PARAM_CREATE_USER_PASS);

		user.setName(userName);
		user.setEmail(userEmail);
		user.setPassword(userPass);

		// cria usuario
		UserBO.createUser(user);

		// faz login
		User userLogin = new User();
		userLogin.setEmail(userEmail);
		userLogin.setPassword(CryptographyMD5Util.encrypt(userPass));
		List<User> loggedUser = UserBO.userLogin(userLogin);

		Log.e("executando o service", "executando service 2");
		Intent broadcastIntent = new Intent();
		broadcastIntent
				.setAction(CreateUserResponseReceiver.ACTION_RESP_CREATE_USER);
		broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
		Bundle bundle = new Bundle();
		bundle.putSerializable(PARAM_CREATE_RETURN_LIST,
				(Serializable) loggedUser);
		broadcastIntent.putExtras(bundle);
		sendBroadcast(broadcastIntent);
	}
}
