package com.accential.trueone.adapter;

import java.util.Calendar;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.accential.trueone.bean.Checkout;
import com.example.slide.R;

public class CheckoutLandAdapter extends BaseAdapter{

	private List<Checkout> checkouts;
	private LayoutInflater mInflater;

	public CheckoutLandAdapter(Context context, List<Checkout> checkouts){
		this.checkouts = checkouts;
		mInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return checkouts.size();
	}

	@Override
	public Object getItem(int position) {
		return checkouts.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {

		Checkout check = checkouts.get(position);

		view = mInflater.inflate(R.layout.activity_item_compras, null);

		((TextView) view.findViewById(R.id.textView_ComprasNumPedido)).setText(String.valueOf(check.getId()));

		//((SmartImageView) view.findViewById(R.id.smartImageView_ComprasPhotoPedido)).setImageUrl(check.getOffer().getPhoto());

		((TextView) view.findViewById(R.id.textView_ComprasTitleOffer)).setText(check.getOffer().getTitle());
		((TextView) view.findViewById(R.id.textView_ComprasDescOffer)).setText(check.getOffer().getResume());

		((TextView) view.findViewById(R.id.textView_ComprasDataCompra)).setText( String.valueOf(check.getDateTime().get(Calendar.DAY_OF_MONTH) + "/" + 
				(check.getDateTime().get(Calendar.MONTH) + 1) + "/" + check.getDateTime().get(Calendar.YEAR)));

		((TextView) view.findViewById(R.id.textView_ComprasValorTotal))
		.setText("R$".concat(String.valueOf(check.getTotalValue()).replace(".", ",")));

		((TextView) view.findViewById(R.id.textView_ComprasStatus)).setText(String.valueOf(check.getPaymentState()));

		return view;
	}

}
