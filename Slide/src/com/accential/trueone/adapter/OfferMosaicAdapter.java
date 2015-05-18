package com.accential.trueone.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.accential.trueone.bean.Offer;
import com.accential.trueone.utils.DownloadImagemUtil;
import com.example.slide.CarrousselActivity;
import com.example.slide.R;
import com.loopj.android.image.SmartImageView;

public class OfferMosaicAdapter extends BaseAdapter {

	private List<Offer> offers;
	public LayoutInflater mInflater;

	public OfferMosaicAdapter(List<Offer> offers, Context context) {
		this.offers = offers;
		mInflater = LayoutInflater.from(context);
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

		view = mInflater.inflate(R.layout.x_item_offer, null);

		// carrega imagem em threads diferentes

		SmartImageView smart = new SmartImageView(view.getContext());

		((SmartImageView) view.findViewById(R.id.ivOfferPhoto))
				.setImageUrl(offer.getPhoto());

		// ((TextView) view.findViewById(R.id.tvPrecoOffer)).setText(String
		// .format("R$%.2f", offer.getValue()));

		TextView valor = (TextView) view.findViewById(R.id.tvPrecoOffer);
		valor.setText(String.format("R$%.2f", offer.getValue()));

		Typeface font = Typeface.createFromAsset(view.getContext().getAssets(),
				"Aaargh.ttf");
		valor.setTypeface(font);

		return view;
	}
}
