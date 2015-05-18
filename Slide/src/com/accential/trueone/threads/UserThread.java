package com.accential.trueone.threads;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;

import android.os.AsyncTask;
import android.util.Log;

import com.accential.trueone.bean.User;
import com.accential.trueone.bo.UserBO;

public class UserThread extends AsyncTask<Void, Void, JSONArray>{


	@Override
	protected JSONArray doInBackground(Void... pars)  {

		Map<String, Map<String, Map<String, String>>> key = new HashMap<String,Map<String, Map<String, String>>>();
		Map<String,Map<String,String>> params = new HashMap<String,Map<String,String>>();
		Map<String,String> conditions = new HashMap<String,String>();
				
//		conditions.put("User.name", "matheus");
		params.put("conditions", conditions);
		key.put("User", params);
		
		
		
		List<User> users = UserBO.listUsers(key);

		for(User of : users)
		{
			Log.i("NOME DE USUARIO", of.getName());
			Log.i("PHOTO", of.getPhoto()); 
		}

		return null;
	}

}
