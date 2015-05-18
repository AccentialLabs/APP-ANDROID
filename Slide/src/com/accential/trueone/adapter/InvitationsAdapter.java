package com.accential.trueone.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.accential.trueone.bean.CompaniesInvitationsUser;
import com.example.slide.R;
import com.loopj.android.image.SmartImageView;

public class InvitationsAdapter extends BaseAdapter {

	private List<CompaniesInvitationsUser> invitations;
	private LayoutInflater inflater;

	public InvitationsAdapter(Context context,
			List<CompaniesInvitationsUser> invitations) {
		this.invitations = invitations;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return invitations.size();
	}

	@Override
	public Object getItem(int location) {
		return invitations.get(location);
	}

	@Override
	public long getItemId(int location) {
		return location;
	}

	@Override
	public View getView(int location, View view, ViewGroup parent) {

		CompaniesInvitationsUser invite = invitations.get(location);

		view = inflater.inflate(R.layout.x_activity_invitations_item, null);

		// verifica se company possui um logo para ser exibido
		if (!invite.getCompany().getLogo().equals("UPLOAD_ERROR")
				&& !invite.getCompany().getLogo().equals("NO_CONNECTION")) {
			((SmartImageView) view.findViewById(R.id.ivInviteCompLogo))
					.setImageUrl(invite.getCompany().getLogo());
		}

		// nome da empresa
		TextView tvCompFancyName = (TextView) view
				.findViewById(R.id.tvIviteCompName);

		// mudando font do TextView
		tvCompFancyName.setText(invite.getCompany().getFancy_name());
		Typeface font = Typeface.createFromAsset(view.getContext().getAssets(),
				"helvetica-normal.ttf");
		tvCompFancyName.setTypeface(font);

		// email da empresa
		TextView tvCompEmail = (TextView) view
				.findViewById(R.id.tvIviteCompEmail);
		tvCompEmail.setText(invite.getCompany().getEmail());

		return view;
	}
}
