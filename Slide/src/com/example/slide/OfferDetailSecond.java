package com.example.slide;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.accential.trueone.bean.Offer;
import com.accential.trueone.utils.SessionControl;

public class OfferDetailSecond extends Fragment {

	private View v;
	private SharedPreferences loggedUser;
	private Offer offer;
	private TextView tvOfferDesc;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		v = inflater
				.inflate(R.layout.x_offers_details_second, container, false);

		tvOfferDesc = (TextView) v.findViewById(R.id.tvOfferDesc);

		loggedUser = v.getContext().getSharedPreferences(
				SessionControl.USER_LOGGED, 0);
		offer = SessionControl.decodeSessionOffer(loggedUser.getString(
				SessionControl.OFFER_DETAIL, null));

		tvOfferDesc.setText(Html.fromHtml(offer.getDescription()));
		return v;
	}

	public static OfferDetailSecond newInstance(String text) {

		OfferDetailSecond f = new OfferDetailSecond();
		Bundle b = new Bundle();
		b.putString("msg", text);

		f.setArguments(b);

		return f;
	}
}
