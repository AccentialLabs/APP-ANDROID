package com.accential.trueone.bo;

import java.util.Map;

import com.accential.trueone.bean.FacebookProfile;
import com.accential.trueone.daofactory.DAOFactory;
import com.accential.trueone.interfaces.IFacebookProfile;

@SuppressWarnings("all")
public class FacebookProfileBO {

	private static IFacebookProfile dao = DAOFactory.whichFactory(
			DAOFactory.JSON).JSONDAOFacebookProfile();

	public static void saveProfile(Map params) {
		dao.saveProfile(params);
	}

	public static void createFacebookProfile(FacebookProfile profile) {
		dao.createFacebookProfile(profile);
	}

}
