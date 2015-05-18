package com.accential.trueone.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.accential.trueone.bean.User;
import com.accential.trueone.bo.UserBO;
import com.accential.trueone.utils.CryptographyMD5Util;

public class LoginIntentService extends IntentService {
	public static final String PARAM_IN_MSG = "imsg";
	public static final String PARAM_OUT_MSG = "omsg";
	public static final String PARAM_OUT_USER = "user";
	public static final String PARAM_IN_USER_SENHA = "senhaUser";
	public static final String PARAM_IN_USER_EMAIL = "emailUser";
	public static final String PARAM_OUT_RESPOSTA = "respostaLogado";

	public LoginIntentService() {
		super("LoginIntentService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {

		String msgLogin = new String();
		boolean logado = false;
		List<User> users = new ArrayList<User>();
		User objUser = new User();
		String email = intent.getStringExtra(PARAM_IN_USER_EMAIL);
		String senha = intent.getStringExtra(PARAM_IN_USER_SENHA);

		Log.i("teste", "usuario recebido : " + email + "- senha: " + senha);

		String strSenhaMD5 = CryptographyMD5Util.encrypt(senha);

		// MAPS RESPONSAVEIS POR LEVAREM O CONTEUDO PARA PESQUISA DE USUARIO NA
		// API
		Map<String, Map<String, Map<String, String>>> key = new HashMap<String, Map<String, Map<String, String>>>();
		Map<String, Map<String, String>> params = new HashMap<String, Map<String, String>>();
		Map<String, String> conditions = new HashMap<String, String>();

		conditions.put("User.email", email);
		conditions.put("User.password", strSenhaMD5);
		params.put("conditions", conditions);
		key.put("User", params);

		users = UserBO.listAllUsers(key);

		if (users.size() != 0) {
			Log.i("QUANTIDADE DE REGISTROS DA LISTA",
					Integer.toString(users.size()));
			for (User user : users) {
				if (user.getEmail().equals(email)
						|| user.getPassword().equals(strSenhaMD5)) {
					Log.i("LOGIN - NOME", user.getName());
					Log.i("LOGIN - EMAIL", user.getEmail());
					Log.i("LOG USER - NAME", user.getName());
					objUser.setName(user.getName());
					objUser.setPhoto(user.getPhoto());
					objUser.setId(user.getId());
				}
				logado = true;
				intent.putExtra("usuario", objUser);
				msgLogin = "USUARIO FOI VALIDADO: LOGADO";

			}
		} else {
			msgLogin = "USUARIO FOI VALIDADO: INVALIDO";
		}

		// processing done here���.
		Intent broadcastIntent = new Intent();
		// broadcastIntent.setAction(ResponseLoginTestReceiver.ACTION_RESP);
		broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
		broadcastIntent.putExtra(PARAM_OUT_RESPOSTA, msgLogin);
		Bundle bundle = new Bundle();
		broadcastIntent.putExtras(bundle);
		sendBroadcast(broadcastIntent);
	}

}
