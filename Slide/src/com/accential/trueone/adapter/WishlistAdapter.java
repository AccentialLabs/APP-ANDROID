package com.accential.trueone.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.accential.trueone.bean.Wishlist;
import com.example.slide.R;

public class WishlistAdapter extends BaseAdapter{

	private List<Wishlist> wishes;
	public LayoutInflater mInflater;

	public WishlistAdapter(List<Wishlist> wishes, Context context){
		this.wishes = wishes;
		mInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return wishes.size();
	}

	@Override
	public Object getItem(int arg0) {
		return wishes.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {

		Wishlist wishlist = wishes.get(position);

		view = mInflater.inflate(R.layout.activity_wishlist, null);
/*
		((TextView) view.findViewById(R.id.textView_whish_title)).setText("Desejo ".concat(String.valueOf(position + 1)));
		((TextView) view.findViewById(R.id.textView_whish1)).setText(wishlist.getName());
		((TextView) view.findViewById(R.id.textView_wish2)).setText(wishlist.getDescription());
		((TextView) view.findViewById(R.id.textView_whish3)).setText(wishlist.getCategory().getName().replace(",", "\n"));
		((TextView) view.findViewById(R.id.textView_wish4)).setText(wishlist.getSubCategory().getName().replace(",", "\n"));
		((TextView) view.findViewById(R.id.textView_whish_dataSolicita)).setText(
				String.valueOf( wishlist.getDateRegister().get(Calendar.DAY_OF_MONTH)) + "/"
						+ (wishlist.getDateRegister().get(Calendar.MONTH) + 1) + "/" + wishlist.getDateRegister().get(Calendar.YEAR));
		//			((TextView) view.findViewById(R.id.textView_wish0)).setText(wishlist.getUser().getName());
*/
		return view;

	}

}
