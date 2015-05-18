package com.accential.trueone.adapter;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.accential.trueone.bean.CompaniesUser;
import com.example.slide.R;
import com.loopj.android.image.SmartImageView;

public class SignaturesLandAdapter extends BaseAdapter {

	private List<CompaniesUser> lista;
	private LayoutInflater mInflater;
	private TextView textCompName;
	private TextView textCompDesc;
	private TextView textCompEmail;
	private SmartImageView imageCompPhoto;

	public SignaturesLandAdapter(Context context, List<CompaniesUser> lista) {
		this.lista = lista;
		mInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return lista.size();
	}

	@Override
	public Object getItem(int arg0) {
		return lista.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@SuppressLint("NewApi")
	@Override
	public View getView(int position, View view, ViewGroup parent) {

		CompaniesUser compUser = lista.get(position);
		int i = 0;
		i =+ i;
		view = mInflater.inflate(R.layout.activity_item_signatures, null);

		textCompName = (TextView) view.findViewById(R.id.textView_companyName);
		textCompEmail = (TextView) view.findViewById(R.id.textView_companyEmail);
		textCompDesc = (TextView) view.findViewById(R.id.textView_companyDescription);
		imageCompPhoto = (SmartImageView) view.findViewById(R.id.imageView_compPhoto);


		textCompName.setText(compUser.getCompany().getFancy_name().toUpperCase());
		textCompEmail.setText(compUser.getCompany().getEmail());

		if(compUser.getCompany().getDescription().isEmpty()){
			textCompDesc.setText("Esta empresa n��o possui descri����o!");
		}else{
			textCompDesc.setText(compUser.getCompany().getDescription());	
		}

		if(compUser.getCompany().getLogo().equals("UPLOAD_ERROR")){
			imageCompPhoto.setImageUrl("http://uploaddeimagens.com.br/images/000/132/678/original/not_found_logo.jpg?1386618035");
		}else if(compUser.getCompany().getLogo().equals("NO_CONNECTION")){
			imageCompPhoto.setImageUrl("http://uploaddeimagens.com.br/images/000/132/678/original/not_found_logo.jpg?1386618035");
		}else{
			imageCompPhoto.setImageUrl(compUser.getCompany().getLogo());
		}

		i++;
		return view;
	}
}
