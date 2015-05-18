package com.accential.trueone.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.accential.trueone.bean.CompaniesInvitationsUser;
import com.example.slide.R;

/**
 * 
 * @author Matheus Odilon - accentialbrasil
 *
 */
public class CompsInviteUserAdapter extends BaseAdapter{

	private List<CompaniesInvitationsUser> invitations;
	private LayoutInflater mInflater;
	private TextView textViewTitle;
	private TextView textViewContact;

	public CompsInviteUserAdapter(List<CompaniesInvitationsUser> invitations, Context context){
		this.invitations = invitations;
		mInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return invitations.size();
	}

	@Override
	public Object getItem(int position) {
		return invitations.get(position);
	}

	@Override
	public long getItemId(int postion) {
		return postion;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {

		CompaniesInvitationsUser invite = invitations.get(position);

		view = mInflater.inflate(R.layout.activity_detail_companies_user, null);

		textViewTitle = ((TextView) view.findViewById(R.id.textView_companiesFancy_company));
		textViewContact = ((TextView) view.findViewById(R.id.textView_companiesContact_company));

		textViewTitle.setText(invite.getCompany().getFancy_name());
		textViewContact.setText(invite.getCompany().getEmail());

		return view;
	}

}
