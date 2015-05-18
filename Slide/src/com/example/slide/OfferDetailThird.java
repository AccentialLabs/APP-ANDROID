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

public class OfferDetailThird extends Fragment {

	private View v;
	private SharedPreferences loggedUser;
	private Offer offer;
	private TextView tvOfferEsp;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		v = inflater.inflate(R.layout.x_offer_detail_third, container, false);

		tvOfferEsp = (TextView) v.findViewById(R.id.tvOfferEsp);

		loggedUser = v.getContext().getSharedPreferences(
				SessionControl.USER_LOGGED, 0);
		offer = SessionControl.decodeSessionOffer(loggedUser.getString(
				SessionControl.OFFER_DETAIL, null));

		tvOfferEsp.setText(Html.fromHtml(offer.getSpecification()));
		return v;
	}

	public static OfferDetailThird newInstance(String text) {

		OfferDetailThird f = new OfferDetailThird();
		Bundle b = new Bundle();
		b.putString("msg", text);

		f.setArguments(b);

		return f;
	}
}
