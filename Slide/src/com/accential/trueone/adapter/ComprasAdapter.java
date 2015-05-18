package com.accential.trueone.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.accential.trueone.bean.Checkout;
import com.example.slide.R;

public class ComprasAdapter extends BaseAdapter {

	private List<Checkout> compras;
	private LayoutInflater inflater;

	public ComprasAdapter(Context context, List<Checkout> compras) {
		this.compras = compras;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return compras.size();
	}

	@Override
	public Object getItem(int location) {
		return compras.get(location);
	}

	@Override
	public long getItemId(int location) {
		return location;
	}

	@Override
	public View getView(int location, View view, ViewGroup parent) {

		Checkout compra = compras.get(location);

		view = inflater.inflate(R.layout.x_activity_compras_item, null);

		// id
		((TextView) view.findViewById(R.id.tvCheckId)).setText(String
				.valueOf(compra.getId()));

		// titulo oferta
		((TextView) view.findViewById(R.id.tvCheckOfferTitle)).setText(compra
				.getOffer().getTitle());

		// valor total
		((TextView) view.findViewById(R.id.tvCheckTotalValue)).setText("R$"
				.concat(String.valueOf(compra.getTotalValue())));

		// status do pagamento
		((TextView) view.findViewById(R.id.tvCheckStatus)).setText(String
				.valueOf(compra.getPaymentState()));

		// data
		TextView tvCheckData = (TextView) view.findViewById(R.id.tvCheckData);
		
		
		return view;
	}
}
