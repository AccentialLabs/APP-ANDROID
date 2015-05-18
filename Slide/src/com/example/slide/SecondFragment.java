package com.example.slide;

import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.accential.trueone.adapter.CheckoutAdapter;
import com.accential.trueone.bean.Checkout;
import com.accential.trueone.bo.CheckoutBO;

/**
 * Minhas compras
 * 
 * @author Matheus Odilon - accentialbrasil
 * 
 */
public class SecondFragment extends Fragment {

	private View v;
	private List<Checkout> checkouts;
	private ListView checkListView;
	private CheckoutAdapter adapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		v = inflater.inflate(R.layout.second_frag, container, false);

		TextView tv = (TextView) v.findViewById(R.id.tvFragSecond);
		checkListView = (ListView) v.findViewById(R.id.checkListView);

		tv.setText(getArguments().getString("msg"));

		// executamos o carregamento da lista em outra thread
		// para evitar o congelamento da tela
		Thread thread = new Thread() {
			@Override
			public void run() {
				try {
					checkouts = CheckoutBO.returnsObjCheckout(308);
					adapter = new CheckoutAdapter(checkouts, v.getContext());

				} catch (Exception e) {
					e.printStackTrace();

				}
			}

		};

		// thread.setPriority(Thread.NORM_PRIORITY);
		// thread.start();
		// checkListView.setAdapter(adapter);

		return v;
	}

	public static SecondFragment newInstance(String text) {

		SecondFragment f = new SecondFragment();
		Bundle b = new Bundle();
		b.putString("msg", text);

		f.setArguments(b);

		return f;
	}

}
