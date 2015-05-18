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

public class OfferStyleListAdapter extends BaseAdapter {

	private List<Offer> offers;
	private LayoutInflater inflater;

	public OfferStyleListAdapter(Context context, List<Offer> offers) {
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
		view = inflater.inflate(R.layout.x_activity_offer_list_item, null);

		ImageView ivDesconto = (ImageView) view.findViewById(R.id.ivDesconto);
		SmartImageView siv = (SmartImageView) view
				.findViewById(R.id.ivOfferPhoto);
		TextView title = (TextView) view.findViewById(R.id.tvOfferTitle);
		TextView valorOriginal = (TextView) view
				.findViewById(R.id.tvOfferValOriginal);
		TextView valorComDesconto = (TextView) view
				.findViewById(R.id.tvOfferValComDesc);
		TextView parcelas = (TextView) view.findViewById(R.id.tvOfferParcel);
		TextView tvDesconto = (TextView) view.findViewById(R.id.tvValDesconto);
		Button btnShare = (Button) view.findViewById(R.id.button1);
		Button btnComprar = (Button) view.findViewById(R.id.button2);

		// valores
		siv.setImageUrl(offer.getPhoto());
		title.setText(offer.getTitle());
		valorOriginal.setText(String.format("De R$%.2f", offer.getValue()));

		// valores com desconto
		if (offer.getPercentageDiscount() == 0) {
			valorComDesconto.setText(String.format("Por R$%.2f",
					offer.getValue()));
			tvDesconto.setVisibility(View.GONE);
		} else {
			// calculo desconto
			float desconto = (Float.parseFloat(String.valueOf(offer
					.getPercentageDiscount())) / 100);
			float valDesconto = (desconto * offer.getValue());
			float valorComDescontoF = (offer.getValue() - valDesconto);

			valorComDesconto.setText(String.format("Por R$%.2f",
					valorComDescontoF));

			tvDesconto.setText(String.format(
					"%.0f",
					Float.parseFloat(String.valueOf(offer
							.getPercentageDiscount()))).concat("%\noff"));
			ivDesconto.setVisibility(View.VISIBLE);
		}

		float valorParcela = offer.getValue() / 12;
		parcelas.setText(String.format("12x de R$%.2f", valorParcela));

		btnShare.setTag(location);
		btnComprar.setTag(location);

		return view;
	}
}
