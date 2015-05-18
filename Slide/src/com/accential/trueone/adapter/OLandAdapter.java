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

public class OLandAdapter extends BaseAdapter{

	private List<Offer> offers;
	private LayoutInflater mInflate;

	public OLandAdapter(List<Offer> offers, Context context){
		this.offers = offers;
		mInflate = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return offers.size();
	}
	@Override
	public Object getItem(int position) {
		return offers.get(position);
	}
	@Override
	public long getItemId(int position) {
		return position;
	}
	@Override
	public View getView(int position, View view, ViewGroup parent) {

		Offer offer = offers.get(position);

		view = mInflate.inflate(R.layout.activity_item_offer, null);

		((TextView) view.findViewById(R.id.textView_tituloOferta2)).setText(offer.getTitle());
		((TextView) view.findViewById(R.id.textView_tituloOferta3)).setText(offer.getTitle());
		((TextView) view.findViewById(R.id.textView_tituloOferta1)).setText(offer.getTitle());

		return view;
	}



}
