package com.accential.trueone.base64;

import android.util.Base64;

public class Criptography64Utils {

	public static String encodeBase64String(String str){
		try{

			return new String(Base64.encode(str.getBytes(), Base64.DEFAULT));
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	public static String decodeBase64String(String str){
		try{
			return new String(Base64.decode(str, Base64.DEFAULT));
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
}
