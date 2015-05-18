package com.accential.trueone.threads;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;

import com.accential.trueone.bean.User;
import com.accential.trueone.bo.UserBO;

/**
 * @author Matheus Odilon - accentialbrasil
 *
 */
@SuppressWarnings("all")
public class UThread extends AsyncTask<Map, Void, List<String>>{

	private Context context;
	private ListView list;
	


	public UThread(Context context, ListView list) {
		super();
		this.context = context;
		this.list = list;
	}
	
	@Override
	protected List<String> doInBackground(Map... pars) {
		
		List<User> users = UserBO.listUsers(pars[0]);
		List<String> teste = new ArrayList<String>();
		
		int contador = UserBO.countUser(pars[0]);
		Log.i("QUANTIDADE DE REGISTROS", Integer.toString(contador));
		
		for (User usrs : users) {
			Log.i("Nome do Usuario", usrs.getName());
			Log.i("Senha", usrs.getPassword());
			teste.add(usrs.getName());
		}
		
		return teste;
	}

}
