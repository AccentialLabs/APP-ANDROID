package com.accential.trueone.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.accential.trueone.bean.Offer;
import com.example.slide.R;
import com.loopj.android.image.SmartImageView;

public class OfferFullAdapter extends BaseAdapter {

	private List<Offer> offers;
	private LayoutInflater inflater;

	public OfferFullAdapter(Context context, List<Offer> offers) {
		this.offers = offers;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return offers.size();
	}

	@Override
	public Object getItem(int location) {
		return offers.get(location);
	}

	@Override
	public long getItemId(int location) {
		return location;
	}

	@Override
	public View getView(int location, View view, ViewGroup parent) {

		Offer offer = offers.get(location);
		view = inflater.inflate(R.layout.x_activity_item_offer_full, null);

		ImageView imgDesconto = (ImageView) view.findViewById(R.id.imgDesconto);
		TextView tvDesconto = (TextView) view
				.findViewById(R.id.OfferFullDiscount);

		SmartImageView photo = (SmartImageView) view
				.findViewById(R.id.OfferFullPhoto);
		TextView title = (TextView) view.findViewById(R.id.OfferFullTitle);
		TextView originalValue = (TextView) view
				.findViewById(R.id.OfferFullOriginalValue);
		TextView newValue = (TextView) view
				.findViewById(R.id.OfferFullNewValue);
		TextView parcelsValue = (TextView) view
				.findViewById(R.id.OfferFullParcelValue);
		Button btnCompartilhar = (Button) view.findViewById(R.id.button1);
		Button btnComprar = (Button) view.findViewById(R.id.button2);

		// setando valores
		title.setText(offer.getTitle());
		originalValue.setText(String.format("De R$%.2f", offer.getValue()));

		if (offer.getPercentageDiscount() == 0) {

			newValue.setText(String.format("Por R$%.2f", offer.getValue()));
			float parcels = offer.getValue() / 12;
			parcelsValue.setText(String.format("12x de R$%.2f", parcels));

		} else {
			// calculo desconto
			float desconto = (Float.parseFloat(String.valueOf(offer
					.getPercentageDiscount())) / 100);
			float valDesconto = (desconto * offer.getValue());
			float valorComDesconto = (offer.getValue() - valDesconto);

			float parcels = valorComDesconto / 12;
			parcelsValue.setText(String.format("12x de R$%.2f", parcels));
			newValue.setText(String.format("Por R$%.2f", valorComDesconto));

			imgDesconto.setVisibility(View.VISIBLE);
			tvDesconto.setVisibility(View.VISIBLE);
			tvDesconto.setText(String.valueOf(offer.getPercentageDiscount())
					+ "%\n off");
		}

		photo.setImageUrl(offer.getPhoto());

		btnCompartilhar.setTag(location);
		btnComprar.setTag(location);
		return view;
	}
}
