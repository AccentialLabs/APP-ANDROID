package com.accential.trueone.threads;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;

import android.os.AsyncTask;
import android.util.Log;

import com.accential.trueone.bean.Company;
import com.accential.trueone.bo.CompanyBO;


public class CompanyThread extends AsyncTask<Void, Void, JSONArray>{


	@Override
	protected JSONArray doInBackground(Void... pars)  {

		Map<String, Map<String, Map<String, String>>> key = new HashMap<String,Map<String, Map<String, String>>>();
		Map<String,Map<String,String>> params = new HashMap<String,Map<String,String>>();
		Map<String,String> conditions = new HashMap<String,String>();
		
		//conditions.put("Offer.status", "ACTIVE");
		//conditions.put("id", "84");
		params.put("conditions", conditions);
		key.put("Company", params);
		
		List<Company> companies = CompanyBO.listCompanies(key);

		for(Company of : companies)
		{
			Log.i("EMPRESA", of.getFancy_name());
			Log.i("EMPRESA RAZAO", of.getCorporate_name()); 
		}

		return null;
	}

}
