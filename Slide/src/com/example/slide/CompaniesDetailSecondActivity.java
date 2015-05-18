package com.example.slide;

import java.util.List;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.accential.trueone.adapter.FinalOfferAdapter;
import com.accential.trueone.bean.CompaniesInvitationsUser;
import com.accential.trueone.bean.Offer;
import com.accential.trueone.bo.CompaniesInvitationsUserBO;
import com.accential.trueone.bo.OfferBO;
import com.accential.trueone.utils.SessionControl;

public class CompaniesDetailSecondActivity extends Fragment {

	private View v;
	private GridView grid;
	private FinalOfferAdapter adapter;
	private List<Offer> offers;
	private SharedPreferences loggedUser;
	private TextView tvEmptyList;

	private Button btnSign;
	private CompaniesInvitationsUser invite;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		v = inflater.inflate(R.layout.x_activity_comp_details_second, null);

		loggedUser = v.getContext().getSharedPreferences(
				SessionControl.USER_LOGGED, 0);

		tvEmptyList = (TextView) v.findViewById(R.id.tvCompDetailWarning);
		grid = (GridView) v.findViewById(R.id.gridbtnCompDetailOffers);
		btnSign = (Button) v.findViewById(R.id.btnCompDetailAssinar);

		invite = SessionControl.decodeSessionInvitatios(loggedUser.getString(
				SessionControl.COMPANIES_DETAIL_INVITE, null));

		// offers = SessionControl.decodeSessionOffers(loggedUser.getString(
		// SessionControl.COMPANIES_DETAIL_OFFERS, null));

		offers = OfferBO.listOffersByCompany(invite.getCompany().getId());

		if (!offers.isEmpty()) {
			adapter = new FinalOfferAdapter(v.getContext(), offers);
			grid.setAdapter(adapter);
		} else {
			grid.setVisibility(View.GONE);
			tvEmptyList.setVisibility(View.VISIBLE);
		}

		// clique em assinar
		btnSign.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				CompaniesInvitationsUserBO.activateInvite(invite.getId(),
						invite);
				Toast.makeText(v.getContext(), "Empresa assinada!",
						Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(v.getContext(),
						InvitatiosActivity.class);
				v.getContext().startActivity(intent);
			}
		});
		return v;
	}

	public static CompaniesDetailSecondActivity newInstance(String text) {
		CompaniesDetailSecondActivity f = new CompaniesDetailSecondActivity();
		Bundle b = new Bundle();
		b.putString("msg", text);

		f.setArguments(b);

		return f;
	}

}
