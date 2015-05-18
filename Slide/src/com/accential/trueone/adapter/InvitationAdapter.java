package com.accential.trueone.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.accential.trueone.bean.CompaniesInvitationsUser;
import com.example.slide.R;

/**
 * Adapter responsavel pela listagem de convites de Companias para usuario
 * @author Matheus Odilon - accentialbrasil
 *
 */
public class InvitationAdapter extends BaseAdapter{

	public List<CompaniesInvitationsUser> invitations;
	public LayoutInflater mInflater;
	private ImageView btnAdd;
	private ImageView btnRemove;

	public InvitationAdapter(List<CompaniesInvitationsUser> invitations, Context context){
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
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {

		CompaniesInvitationsUser invite = invitations.get(position);

		view = mInflater.inflate(R.layout.activity_invitation_item, null);

		((TextView) view.findViewById(R.id.textView_titleCompany)).setText(invite.getCompany().getFancy_name());
		btnAdd = (ImageView) view.findViewById(R.id.imageView_add);
		btnRemove = (ImageView) view.findViewById(R.id.imageView_remove);
		btnAdd.setTag(position);
		btnRemove.setTag(position);

		return view;
	}

}
