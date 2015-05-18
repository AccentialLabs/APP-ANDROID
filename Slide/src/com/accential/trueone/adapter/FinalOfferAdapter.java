package com.accential.trueone.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.accential.trueone.bean.Offer;
import com.example.slide.R;
import com.loopj.android.image.SmartImageView;

public class FinalOfferAdapter extends BaseAdapter {

	private List<Offer> offers;
	private LayoutInflater inflater;

	public FinalOfferAdapter(Context context, List<Offer> offers) {
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

		view = inflater.inflate(R.layout.activity_item_offer_final, null);

		// comentei aqui para o emulador nao travar
		SmartImageView img = (SmartImageView) view.findViewById(R.id.imgTeste);
		img.setImageUrl(offer.getPhoto());

		TextView tvDesconto = (TextView) view.findViewById(R.id.tvDesconto);
		ImageView ivDesconto = (ImageView) view.findViewById(R.id.imgDesconto);

		// nova linha ->
		TextView valorSemDesconto = (TextView) view
				.findViewById(R.id.item_valor_sem_desconto);

		valorSemDesconto.setText(String.format("De R$%.2f", offer.getValue()));
		Typeface font = Typeface.createFromAsset(view.getContext().getAssets(),
				"Aaargh.ttf");
		valorSemDesconto.setTypeface(font);

		TextView valor = (TextView) view.findViewById(R.id.item_valor);
		if (offer.getPercentageDiscount() == 0) {

			valor.setText(String.format("R$%.2f", offer.getValue()));

			valor.setTypeface(font);

		} else {
			// calculo desconto
			float desconto = (Float.parseFloat(String.valueOf(offer
					.getPercentageDiscount())) / 100);
			float valDesconto = (desconto * offer.getValue());
			float valorComDesconto = (offer.getValue() - valDesconto);

			valor.setText(String.format("R$%.2f", valorComDesconto));

			valor.setTypeface(font);

			tvDesconto.setText(String.valueOf(offer.getPercentageDiscount())
					+ "%\noff");
			ivDesconto.setVisibility(View.VISIBLE);
			tvDesconto.setVisibility(View.VISIBLE);
		}

		return view;
	}
}
