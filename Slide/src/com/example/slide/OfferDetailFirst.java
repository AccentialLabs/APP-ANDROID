package com.example.slide;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.accential.trueone.bean.Offer;
import com.accential.trueone.utils.SessionControl;
import com.loopj.android.image.SmartImageView;

public class OfferDetailFirst extends Fragment {

	private View v;
	private SharedPreferences loggedUser;
	private Offer offer;
	private TextView tvOfferTitle;
	private SmartImageView offerImg;
	private ImageView imgStar1;
	private ImageView imgStar2;
	private ImageView imgStar3;
	private ImageView imgStar4;
	private ImageView imgStar5;
	private List<ImageView> stars;
	private Button button;
	private Button btnComprar;
	private int offerNota;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		v = inflater.inflate(R.layout.x_offers_details_first, container, false);

		offerNota = getArguments().getInt("nota");
		Log.e("NOTA", "NOTA: " + String.valueOf(offerNota));

		button = (Button) v.findViewById(R.id.button1);
		btnComprar = (Button) v.findViewById(R.id.btnComprar);
		tvOfferTitle = (TextView) v.findViewById(R.id.tvTitleOffer);
		offerImg = (SmartImageView) v.findViewById(R.id.imgOffer);
		imgStar1 = (ImageView) v.findViewById(R.id.star1);
		imgStar2 = (ImageView) v.findViewById(R.id.star2);
		imgStar3 = (ImageView) v.findViewById(R.id.star3);
		imgStar4 = (ImageView) v.findViewById(R.id.star4);
		imgStar5 = (ImageView) v.findViewById(R.id.star5);

		// lógica nota da oferta
		stars = new ArrayList<ImageView>();
		stars.add(imgStar1);
		stars.add(imgStar2);
		stars.add(imgStar3);
		stars.add(imgStar4);
		stars.add(imgStar5);

		for (int i = 0; i < offerNota; i++) {
			stars.get(i).setImageResource(R.drawable.star01);
		}

		loggedUser = v.getContext().getSharedPreferences(
				SessionControl.USER_LOGGED, 0);
		offer = SessionControl.decodeSessionOffer(loggedUser.getString(
				SessionControl.OFFER_DETAIL, null));

		tvOfferTitle.setText(offer.getTitle());
		offerImg.setImageUrl(offer.getPhoto());

		// compartilhar
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				String shareBody = offer.getTitle();
				Intent sharingIntent = new Intent(
						android.content.Intent.ACTION_SEND);
				sharingIntent.setType("text/plain");
				sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
						"Oferta T1");
				sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT,
						shareBody);
				startActivity(Intent.createChooser(sharingIntent,
						"Compartilhe por..."));
			}
		});

		// comprar
		btnComprar.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
//				Intent intent = new Intent(v.getContext(),
//						CheckoutActivity.class);
//				startActivity(intent);
			}
		});
		return v;
	}

	public static OfferDetailFirst newInstance(int nota) {

		OfferDetailFirst f = new OfferDetailFirst();
		Bundle b = new Bundle();
		b.putInt("nota", nota);

		f.setArguments(b);

		return f;
	}

}
