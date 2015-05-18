package com.accential.trueone.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.accential.trueone.bean.AditionalAddressesUser;
import com.example.slide.R;

public class CheckoutAddressAdapter extends BaseAdapter {

	private List<AditionalAddressesUser> address;
	private LayoutInflater inflate;

	public CheckoutAddressAdapter(Context context,
			List<AditionalAddressesUser> address) {
		this.address = address;
		inflate = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return address.size();
	}

	@Override
	public Object getItem(int location) {
		return address.get(location);
	}

	@Override
	public long getItemId(int location) {
		return location;
	}

	@Override
	public View getView(int location, View view, ViewGroup parent) {

		AditionalAddressesUser endereco = address.get(location);
		view = inflate.inflate(R.layout.x_activity_address_item, null);

		TextView label = (TextView) view.findViewById(R.id.tvAddressName);
		TextView firstLine = (TextView) view
				.findViewById(R.id.tvAddressFirstLine);
		TextView secondLine = (TextView) view
				.findViewById(R.id.tvAddressSecondLine);

		// valores
		if (endereco.getLabel().equals("")) {

		} else {
			label.setText(String.valueOf(endereco.getLabel()));
		}

		firstLine.setText(endereco.getAddress() + ", " + endereco.getNumber()
				+ " - " + endereco.getComplement() + " - "
				+ endereco.getDistrict() + " - " + endereco.getCity() + " - "
				+ endereco.getState());

		secondLine.setText("Cep: " + endereco.getZipCode());
		return view;
	}
}
