package com.accential.trueone.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.accential.trueone.adapter.OfferAdapter.ViewHolder;
import com.accential.trueone.bean.CompaniesUser;
import com.example.slide.R;
/**
 * Estrutura da Lista de Assinaturas
 * @author Matheus Odilon - accentialbrasil
 *
 */
public class CompaniesUserAdapter extends BaseAdapter{

	private List<CompaniesUser> companies;
	private LayoutInflater mInflater;
	private TextView textViewTitle;
	private TextView textViewContact;
	private ImageView image;

	public CompaniesUserAdapter(List<CompaniesUser> companies, Context context){
		this.companies = companies;
		mInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return companies.size();
	}

	@Override
	public Object getItem(int arg0) {
		return companies.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {

		CompaniesUser companyUser = companies.get(position);

		view = mInflater.inflate(R.layout.activity_detail_companies_user, null);

		ViewHolder holder = new ViewHolder();

		holder.image = (ImageView) view.findViewById(R.id.imageView1);

		//		holder.image.setOnClickListener(new OnClickListener() {
		//			@Override
		//			public void onClick(View view) {
		//				Log.i("CLIQUE DO BOTAO", "O CLIQUE DO BOTAO FOI GERADO PELO ITEM - Usuario:");
		//			}
		//		});

		textViewTitle = ((TextView) view.findViewById(R.id.textView_companiesFancy_company));
		textViewContact = ((TextView) view.findViewById(R.id.textView_companiesContact_company));

		textViewTitle.setText(companyUser.getCompany().getFancy_name());
		textViewContact.setText(companyUser.getCompany().getEmail());

		return view;

	}






}
