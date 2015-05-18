package com.accential.trueone.adapter;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.accential.trueone.bean.Wishlist;
import com.example.slide.R;

@SuppressWarnings("all")
public class WishAdapter extends BaseAdapter {

	private List<Wishlist> wishes;
	private LayoutInflater mInflater;
	private Map offersQtd;

	public WishAdapter(List<Wishlist> wishes, Context context, Map offersQtd) {
		this.wishes = wishes;
		this.offersQtd = offersQtd;
		mInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return wishes.size();
	}

	@Override
	public Object getItem(int location) {
		return wishes.get(location);
	}

	@Override
	public long getItemId(int location) {
		// TODO Auto-generated method stub
		return location;
	}

	@Override
	public View getView(int location, View view, ViewGroup parent) {

		Wishlist wish = wishes.get(location);

		view = mInflater.inflate(R.layout.x_activity_wishlist_item, null);

		((TextView) view.findViewById(R.id.tvWishTitle))
				.setText(wish.getName());
		((TextView) view.findViewById(R.id.tvWishDesc)).setText(wish
				.getDescription());

		((TextView) view.findViewById(R.id.tvWishDtSolicitacao)).setText(String
				.valueOf(wish.getDateRegister().get(Calendar.DAY_OF_MONTH))
				+ "/"
				+ (wish.getDateRegister().get(Calendar.MONTH) + 1)
				+ "/"
				+ wish.getDateRegister().get(Calendar.YEAR));

		((TextView) view.findViewById(R.id.tvWishLimite)).setText(String
				.valueOf(wish.getEndsAt().get(Calendar.DAY_OF_MONTH))
				+ "/"
				+ (wish.getEndsAt().get(Calendar.MONTH) + 1)
				+ "/"
				+ wish.getEndsAt().get(Calendar.YEAR));

		((TextView) view.findViewById(R.id.tvWishQtdOffers)).setText(String
				.valueOf(offersQtd.get(String.valueOf(wish.getId()))));

		return view;
	}
}