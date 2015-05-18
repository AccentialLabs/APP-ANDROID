package com.accential.trueone.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.accential.trueone.bean.Company;
import com.example.slide.R;

public class SingnaturesCompanyAdapter extends BaseAdapter {

	private List<Company> companies;
	private LayoutInflater mInflater;

	public SingnaturesCompanyAdapter(Context context, List<Company> companies) {
		this.companies = companies;
		mInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return companies.size();
	}

	@Override
	public Object getItem(int location) {
		return companies.get(location);
	}

	@Override
	public long getItemId(int location) {
		return location;
	}

	@Override
	public View getView(int location, View view, ViewGroup parent) {

		Company company = companies.get(location);
		view = mInflater.inflate(R.layout.x_activity_signaturessearch_item,
				null);

		((TextView) view.findViewById(R.id.tvCompanyNAme)).setText(company
				.getFancy_name());

		/*
		 * try { if (company.getLogo() == "NO_CONNECTION") { ((SmartImageView)
		 * view.findViewById(R.id.imgCompanyLogo))
		 * .setImageResource(R.drawable.accential); } else if (company.getLogo()
		 * == "UPLOAD_ERROR") { ((SmartImageView)
		 * view.findViewById(R.id.imgCompanyLogo))
		 * .setImageResource(R.drawable.accential); } else { ((SmartImageView)
		 * view.findViewById(R.id.imgCompanyLogo))
		 * .setImageUrl(company.getLogo()); } } catch (Exception e) { Log.e("",
		 * "Erro no Download da imagem!!!"); e.printStackTrace(); }
		 */

		return view;
	}

}
