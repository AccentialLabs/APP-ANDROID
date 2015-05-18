package com.accential.trueone.threads;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import android.os.AsyncTask;
import android.util.Log;

public class JSONThread extends AsyncTask<String,Void,String>{

	/**
	 * Esse método retornará 
	 * o conteúdo da URL em uma thread 
	 * separada
	 * @param String[]
	 * @return String
	 */
	@Override
	protected String doInBackground(String... params) {
		String str = null;
		BufferedReader in = null;
		try{

			if(params[0] != null){
				  URL url = new URL(params[0]);
				  in = new BufferedReader(new InputStreamReader(url.openStream()));
				  str = in.readLine();		
			} else {
				 throw new Exception("Informar a url para requisição");
			}

		}catch(NullPointerException e){
			Log.e("NullPointerException", e.getMessage());
		} catch (MalformedURLException e) {
			Log.e("MalformedURLException", e.getMessage());
		} catch (IOException e) {
			Log.e("IOException", e.getMessage());
		} catch (Exception e) {
			Log.e("Exception", e.getMessage());
		}finally{
			try {
				in.close();
			} catch (IOException e) {
				Log.e("IOException", e.getMessage());
			}
		}
		return str;
	}



}
