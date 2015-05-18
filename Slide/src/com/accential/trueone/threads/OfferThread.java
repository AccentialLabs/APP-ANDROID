package com.accential.trueone.threads;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.accential.trueone.bean.Offer;
import com.accential.trueone.bo.OfferBO;

/**
 * 
 * @author accentialbrasil
 *
 */
@SuppressWarnings("all")
public class OfferThread extends AsyncTask<Map, Void, List<String>>{
	
	private Context context;
	private ListView list;

	public OfferThread(Context context, ListView list) {
		super();
		this.context = context;
		this.list = list;
	}

	/*@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
	}
	
	@Override
	protected void onPostExecute(JSONArray result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
	}*/

	@Override
	protected List<String> doInBackground(Map... pars)  {
		
		List<Offer> offers = OfferBO.listOffers(pars[0]);
		List<String> teste = new ArrayList<String>();

		int contador = OfferBO.count_offer(pars[0]);		
		Log.i("QUANTIDADE DE REGISTROS", Integer.toString(contador));
		
		for(Offer of : offers)
		{			
			Log.i("Titulo da oferta", of.getTitle());
			Log.i("STATUS", of.getStatus());
			Log.i("METRICS", of.getMetrics());
			Log.i("PARCELS", of.getParcels());
			Log.i("PARCELS OFF IMPOST", of.getParcelsOffImpost());
			Log.i("PUBLIC STR", of.getPublicStr());
			Log.i("WEIGHT", String.valueOf(of.getWeight()));
			Calendar data = of.getBeginsAt();
			SimpleDateFormat s = new SimpleDateFormat("dd/MM/yyyy");  
			String endsDate = s.format(data.getTime());
			Log.i("BEGIN DATE", endsDate);
			
			teste.add(of.getTitle());
		}

		return teste;
	}
	
	@Override
	protected void onPostExecute(List<String> result) {
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, result);
		
		list.setAdapter(adapter);
	}

}
