package com.accential.trueone.threads;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.accential.trueone.bean.Company;
import com.accential.trueone.bo.CompanyBO;
/**
 * 
 * @author Matheus Odilon - accentialbrasil
 *
 */
@SuppressWarnings("all")
public class CompThread extends AsyncTask<Map, Void, List<String>>{

	private Context context;
	private ListView list;
	
	public CompThread(Context context, ListView list) {
		super();
		this.context = context;
		this.list = list;
	}
	

	@Override
	protected List<String> doInBackground(Map... pars) {
		
		List<Company> companies = CompanyBO.listCompanies(pars[0]);
		List<String> teste = new ArrayList<String>();
		
		int contador = CompanyBO.countCompany(pars[0]);
		Log.i("QUANTIDADE TOTAL", Integer.toString(contador));
		
		for (Company comp : companies) {
			
			Log.i("NOME FANTASIA", comp.getFancy_name());
			teste.add(comp.getFancy_name());
		}
		
		return teste;
	}
	
	@Override
	protected void onPostExecute(List<String> result) {
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, result);
		
		list.setAdapter(adapter);
	}

}
