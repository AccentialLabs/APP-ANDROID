package com.accential.trueone.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.accential.trueone.bean.Offer;
import com.example.slide.R;
import com.loopj.android.image.SmartImageView;

public class OfferListAdapter extends BaseAdapter {

	private List<Offer> offers;
	private LayoutInflater inflater;

	public OfferListAdapter(List<Offer> offers, Context context) {
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

		view = inflater.inflate(R.layout.x_item_offer_list, null);

		((SmartImageView) view.findViewById(R.id.ivOfferPhoto))
				.setImageUrl(offer.getPhoto());

		((TextView) view.findViewById(R.id.tvOfferTitle)).setText(offer
				.getTitle());

		((TextView) view.findViewById(R.id.tvOfferValue)).setText(String
				.format("R$ %.2f", offer.getValue()));

		return view;
	}
}
