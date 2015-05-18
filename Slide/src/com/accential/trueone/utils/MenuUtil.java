package com.accential.trueone.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.accential.trueone.ConfigActvity;
import com.accential.trueone.LoginActivity;
import com.accential.trueone.UserActivity;
import com.accential.trueone.bean.User;

public class MenuUtil {

	public static List<String> getMenuList(){

		List<String> opcoes = new ArrayList<String>();
		opcoes.add("");
		opcoes.add("Preferências");
		opcoes.add("Dados Cadastrais");
		opcoes.add("Sair");

		return opcoes;

	}

	/**
	 * Sair
	 * @param context
	 */
	public static void getOut(Context context){

		context.getSharedPreferences(LoginActivity.PREFS_USER, 0).edit().clear().commit();
		Intent intent = new Intent(context, LoginActivity.class);
		context.startActivity(intent);
	}

	/**
	 * Preferecias
	 * @param context
	 * @param user
	 * @return intent
	 */
	public static Intent toPreferences(Context context, User user, Map<String, Object> map){

		Intent intent = new Intent(context, ConfigActvity.class);
		intent.putExtra("user", user);
		Bundle bundle = new Bundle();
		bundle.putSerializable("newsMap", (Serializable) map);
		intent.putExtras(bundle);
		return intent;

	}

	/**
	 * Dados Cadastrais
	 * @param context
	 * @param user
	 * @return intent
	 */
	public static Intent toRegistrationData(Context context, User user, Map<String, Object> map){

		Intent intent = new Intent(context, UserActivity.class);
		intent.putExtra("user", user);
		Bundle bundle = new Bundle();
		bundle.putSerializable("newsMap", (Serializable) map);
		intent.putExtras(bundle);
		return intent;

	}

}
