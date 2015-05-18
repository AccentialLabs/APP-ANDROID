package com.accential.trueone.dao;

import java.util.HashMap;
import java.util.Map;

import android.os.AsyncTask;
import android.util.Log;

import com.accential.trueone.bean.FacebookProfile;
import com.accential.trueone.bo.UtilityComponentBO;
import com.accential.trueone.interfaces.IFacebookProfile;

@SuppressWarnings("all")
/**
 * 
 * @author Matheus Odilon - accentialbrasil
 *
 */
public class FacebookProfileDAO implements IFacebookProfile {

	/**
	 * ATEN������O: ESSE METODO S��� DEVE SER EXECUTADO EM UMA THREAD DIFERENTE
	 * DA PRINCIPAL
	 * 
	 * @author Matheus Odilon - accentialbrasil
	 */
	@Override
	public void saveProfile(Map params) {

		UtilityComponentBO bo = null;

		try {
			bo = new UtilityComponentBO();

			bo.urlRequestToSaveData("users", "first", params);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * METODO QUE DEVE SER EXECUTADO DIRETAMENTE
	 * 
	 * @author Matheus Odilon - accentialbrasil
	 */
	@Override
	public void createFacebookProfile(FacebookProfile profile) {

		AsyncTask<Map, Void, Void> async = new AsyncTask<Map, Void, Void>() {

			@Override
			protected Void doInBackground(Map... params) {

				FacebookProfileDAO dao = new FacebookProfileDAO();
				dao.saveProfile(params[0]);

				return null;
			}
		};
		try {

			Map<String, Map<String, String>> params = new HashMap<String, Map<String, String>>();
			Map<String, String> datas = new HashMap<String, String>();
			Map<String, String> datas1 = new HashMap<String, String>();

			datas1.put("name", profile.getName());
			datas1.put("password", profile.getEmail());
			datas1.put("email", profile.getEmail());
			datas1.put("gender", profile.getGender());
			datas1.put("photo",
					"http://graph.facebook.com/" + profile.getFacebookId()
							+ "/picture?type=small");
			datas1.put("status", "ACTIVE");

			datas.put("facebook_id", profile.getFacebookId());
			datas.put("name", profile.getName());
			datas.put("first_name", profile.getFirstName());
			datas.put("last_name", profile.getLastName());
			datas.put("email", profile.getEmail());
			datas.put("gender", profile.getGender());
			datas.put("profile_link", profile.getProfileLink());
			// datas.put("birthday", profile.getBirthday());
			datas.put("location", profile.getLocation());
			datas.put("relationship_status", profile.getRelationshipStatus());
			datas.put("religion", profile.getReligion());
			datas.put("political", profile.getPolitical());
			// datas.put("updated_time", profile.getUpdatedTime());

			params.put("User", datas1);
			params.put("FacebookProfile", datas);

			Log.e("Map da FacebookProfile",
					"FacebookProfile - Map de Criação: "
							+ String.valueOf(params));

			async.execute(params).get();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
