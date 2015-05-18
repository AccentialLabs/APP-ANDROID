package com.accential.trueone;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.accential.trueone.bean.Offer;
import com.accential.trueone.bo.OfferBO;
import com.example.slide.R;

public class SecondVersionActivity extends Activity {

	private ListView list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_second_version);

		list = (ListView) findViewById(R.id.list);

		Map<String, Map<String, Map<String, String>>> key = new HashMap<String, Map<String, Map<String, String>>>();
		Map<String, Map<String, String>> params = new HashMap<String, Map<String, String>>();
		Map<String, String> conditions = new HashMap<String, String>();

		
		conditions.put("Offer.status", "ACTIVE");
		conditions.put("Offer.public", "ACTIVE");
		conditions.put("Offer.id", "193");
		params.put("conditions", conditions);
		key.put("Offer", params);

		List<Offer> offers = OfferBO.listAllOffers(key);
		Log.i("TESTANDO ------------------", String.valueOf(offers.size()));
		Toast.makeText(this, String.valueOf(offers.size()), Toast.LENGTH_LONG)
				.show();

		ArrayList<String> titles = new ArrayList<String>();
		for (Offer offer : offers) {
			titles.add(offer.getTitle());
		}

		String[] titulos = new String[titles.size()];
		titulos = titles.toArray(titulos);

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, titulos);

		list.setAdapter(adapter);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
