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
/**
 * 
 * @author Matheus Odilon - accentialbrasil
 *
 */
public class CheckoutAdapter extends BaseAdapter{

	private List<Checkout> checkouts;
	private LayoutInflater mInflater;
	private TextView textViewNumPedido;
	private TextView textViewValor;
	private TextView textViewProduto;
	private TextView textViewStatus;
	private TextView textViewData;

	public CheckoutAdapter(List<Checkout> checkouts, Context context){
		this.checkouts = checkouts;
		mInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return checkouts.size();
	}

	@Override
	public Object getItem(int arg0) {
		return checkouts.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {

		Checkout check = checkouts.get(position);

		view = mInflater.inflate(R.layout.item_checkout, null);

		textViewNumPedido = ((TextView) view.findViewById(R.id.textView_checkNumPedido));
		textViewValor = ((TextView) view.findViewById(R.id.textView_checkValue));
		textViewProduto = ((TextView) view.findViewById(R.id.textView_checkOffer)); 
		textViewStatus = ((TextView) view.findViewById(R.id.textView_checkStatus));
		textViewData = ((TextView) view.findViewById(R.id.textView_checkData));

		textViewNumPedido.setText(String.valueOf(check.getId()));
		textViewValor.setText("R$ ".concat(String.valueOf(check.getTotalValue()).replace(".", ",")));
		textViewProduto.setText(check.getOffer().getTitle());
		textViewStatus.setText(String.valueOf(check.getPaymentState()));

		textViewData.setText( String.valueOf(check.getDateTime().get(Calendar.DAY_OF_MONTH) + "/" + 
				(check.getDateTime().get(Calendar.MONTH) + 1) + "/" + check.getDateTime().get(Calendar.YEAR)));

		return view;
	}


}
