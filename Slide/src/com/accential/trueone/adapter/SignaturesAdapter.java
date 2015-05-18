package com.accential.trueone.adapter;

import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.accential.trueone.bean.CompaniesUser;
import com.example.slide.R;
import com.loopj.android.image.SmartImageView;

/**
 * 
 * @author Matheus Odilon - accentialbrasil
 * 
 */
public class SignaturesAdapter extends BaseAdapter {

	private List<CompaniesUser> signatures;
	public LayoutInflater mInflater;

	public SignaturesAdapter(Context context, List<CompaniesUser> sigantures) {
		this.signatures = sigantures;
		mInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return signatures.size();
	}

	@Override
	public Object getItem(int position) {
		return signatures.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {

		CompaniesUser signature = signatures.get(position);
		view = mInflater.inflate(R.layout.x_activity_signatures_item, null);

		((TextView) view.findViewById(R.id.tvCompanyNAme)).setText(signature
				.getCompany().getFancy_name());

		((TextView) view.findViewById(R.id.tvCompanyEmail)).setText(signature
				.getCompany().getEmail());

		/*
		 * try { if (signature.getCompany().getLogo() == "NO_CONNECTION") {
		 * ((SmartImageView) view.findViewById(R.id.ivCompLogo))
		 * .setImageResource(R.drawable.accential); } else if
		 * (signature.getCompany().getLogo() == "UPLOAD_ERROR") {
		 * ((SmartImageView) view.findViewById(R.id.ivCompLogo))
		 * .setImageResource(R.drawable.accential); } else { ((SmartImageView)
		 * view.findViewById(R.id.ivCompLogo))
		 * .setImageUrl(signature.getCompany().getLogo()); } } catch (Exception
		 * e) { Log.e("", "Erro no downloado da imagem"); e.printStackTrace(); }
		 */

		return view;
	}
}
