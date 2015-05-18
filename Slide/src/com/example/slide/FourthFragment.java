package com.example.slide;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.accential.trueone.adapter.WishlistLandAdapter;
import com.accential.trueone.bean.Wishlist;
import com.accential.trueone.bo.WishlistBO;

public class FourthFragment extends Fragment {

	private View v;
	private List<Wishlist> wishlists;
	private ListView wishListView;
	private WishlistLandAdapter wAdapter;
	private Button btnNewWish;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		v = inflater.inflate(R.layout.forth_frag, container, false);

		TextView tv = (TextView) v.findViewById(R.id.tvFragFourth);
		wishListView = (ListView) v.findViewById(R.id.wishListView);
		btnNewWish = (Button) v.findViewById(R.id.btnNewWish);

		tv.setText(getArguments().getString("msg"));

		btnNewWish.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(v.getContext(),
						WishlistActivity.class);
				startActivity(intent);
			}
		});

		// executamos o carregamento da lista em outra thread
		// para evitar o congelamento da tela
		Thread thread = new Thread() {
			@Override
			public void run() {
				try {

					Map<String, Map<String, Map<String, String>>> key = new HashMap<String, Map<String, Map<String, String>>>();
					Map<String, Map<String, String>> params = new HashMap<String, Map<String, String>>();
					Map<String, String> conditions = new HashMap<String, String>();

					conditions
							.put("UsersWishlist.user_id", String.valueOf(308));
					params.put("conditions", conditions);
					key.put("UsersWishlist", params);

					wishlists = WishlistBO.retornaWishies(308);

					wAdapter = new WishlistLandAdapter(v.getContext(),
							wishlists, key);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};

		thread.setPriority(Thread.MAX_PRIORITY);
		thread.start();
		wishListView.setAdapter(wAdapter);

		return v;
	}

	public static FourthFragment newInstance(String text) {

		FourthFragment f = new FourthFragment();
		Bundle b = new Bundle();
		b.putString("msg", text);

		f.setArguments(b);

		return f;
	}

}
