package com.example.slide;

import java.util.List;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.ListView;
import android.widget.TextView;

import com.accential.trueone.adapter.OfferMosaicAdapter;
import com.accential.trueone.bean.Offer;
import com.accential.trueone.utils.SessionControl;

public class FilteredOfferActivity extends Activity {

	private ListView lvOffers;
	private OfferMosaicAdapter mosaicAdapter;
	private List<Offer> offers;
	private SharedPreferences loggedUser;
	private TextView tvWarning;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.x_activity_filtered_offer);

		lvOffers = (ListView) findViewById(R.id.lvFilteredOffer);
		tvWarning = (TextView) findViewById(R.id.tvFilteredWarning);

		loggedUser = getSharedPreferences(SessionControl.USER_LOGGED, 0);

		offers = SessionControl.decodeSessionOffers(loggedUser.getString(
				SessionControl.FILTERED_OFFERS, null));

		if (offers == null) {
			tvWarning.setVisibility(View.VISIBLE);
		} else {

			mosaicAdapter = new OfferMosaicAdapter(offers, this);
			lvOffers.setAdapter(mosaicAdapter);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
