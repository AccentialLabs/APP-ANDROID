package com.accential.trueone.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.accential.trueone.bean.Offer;
import com.example.slide.R;
import com.loopj.android.image.SmartImageView;

public class TwoListMosaicAdapter extends BaseAdapter {

	private List<Offer> offers;
	private LayoutInflater inflater;

	public TwoListMosaicAdapter(Context context, List<Offer> offers) {
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

		view = inflater.inflate(R.layout.item, null);

		SmartImageView siv = (SmartImageView) view
				.findViewById(R.id.item_image);
		TextView valor = (TextView) view.findViewById(R.id.item_valor);

		if (offer.getPercentageDiscount() == 0) {
			valor.setText(" ");
			valor.setVisibility(View.GONE);
		} else {
			// calculo desconto
			float desconto = (Float.parseFloat(String.valueOf(offer
					.getPercentageDiscount())) / 100);
			float valDesconto = (desconto * offer.getValue());
			float valorComDesconto = (offer.getValue() - valDesconto);

			valor.setText(String.format("R$%.2f", valorComDesconto));

			Typeface font = Typeface.createFromAsset(view.getContext()
					.getAssets(), "Aaargh.ttf");
			valor.setTypeface(font);
		}

		siv.setImageUrl(offer.getPhoto());

		return view;
	}
}
