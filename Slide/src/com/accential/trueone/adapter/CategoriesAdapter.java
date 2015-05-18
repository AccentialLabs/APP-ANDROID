package com.accential.trueone.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.accential.trueone.bean.CompanyCategory;
import com.example.slide.R;

/**
 * 
 * @author Matheus Odilon - accentialbrasil
 * 
 */
public class CategoriesAdapter extends BaseAdapter {

	private List<CompanyCategory> categories;
	private LayoutInflater inflater;

	public CategoriesAdapter(List<CompanyCategory> categories, Context context) {
		this.categories = categories;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return categories.size();
	}

	@Override
	public Object getItem(int location) {
		return categories.get(location);
	}

	@Override
	public long getItemId(int location) {
		return location;
	}

	@Override
	public View getView(int location, View view, ViewGroup parent) {

		CompanyCategory category = categories.get(location);

		view = inflater.inflate(R.layout.x_activity_categories_item, null);

		TextView tvCategoryName = (TextView) view
				.findViewById(R.id.tvCategoryName);
		tvCategoryName.setText(category.getName());

		// adicionando font externa ao nosso textview
		Typeface font = Typeface.createFromAsset(parent.getContext()
				.getAssets(), "helvetica-normal.ttf");
		tvCategoryName.setTypeface(font);

		return view;
	}
}
