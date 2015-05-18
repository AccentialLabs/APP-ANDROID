package com.accential.trueone.interfaces;

import java.util.Map;

import com.accential.trueone.bean.FacebookProfile;

public interface IFacebookProfile {

	public void saveProfile(Map params);
	
	public void createFacebookProfile(FacebookProfile profile);

}
