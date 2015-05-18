package com.example.slide;

import java.util.ArrayList;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.accential.trueone.adapter.OffersCommentAdapter;
import com.accential.trueone.bean.CompaniesInvitationsUser;
import com.accential.trueone.bean.OffersComment;
import com.accential.trueone.bo.CompaniesInvitationsUserBO;
import com.accential.trueone.utils.SessionControl;

public class CompaniesDetailThirdActivity extends Fragment {

	private View v;
	private List<OffersComment> comments;
	private OffersCommentAdapter adapter;
	private ListView list;
	private ImageView star1;
	private ImageView star2;
	private ImageView star3;
	private ImageView star4;
	private ImageView star5;
	private List<ImageView> stars;
	private TextView qtdVotation;
	private TextView tvWarning;

	private float nota;
	private float finalEvaluation;
	private SharedPreferences loggedUser;
	private Button btnSign;
	private CompaniesInvitationsUser invite;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		v = inflater.inflate(R.layout.x_activity_comp_details_third, null);

		loggedUser = v.getContext().getSharedPreferences(
				SessionControl.USER_LOGGED, 0);

		list = (ListView) v.findViewById(R.id.lvCompDetailComments);
		star1 = (ImageView) v.findViewById(R.id.starCompDetail1);
		star2 = (ImageView) v.findViewById(R.id.starCompDetail2);
		star3 = (ImageView) v.findViewById(R.id.starCompDetail3);
		star4 = (ImageView) v.findViewById(R.id.starCompDetail4);
		star5 = (ImageView) v.findViewById(R.id.starCompDetail5);
		qtdVotation = (TextView) v.findViewById(R.id.tvCompDetailCommentQtd);
		tvWarning = (TextView) v.findViewById(R.id.tvCompDetailWarningComment);
		btnSign = (Button) v.findViewById(R.id.btnCompDetailAssinar);

		stars = new ArrayList<ImageView>();
		stars.add(star1);
		stars.add(star2);
		stars.add(star3);
		stars.add(star4);
		stars.add(star5);

		nota = 0;
		comments = SessionControl.decodeSessionOffersComments(loggedUser
				.getString(SessionControl.COMPANIES_DETAIL_COMMENTS, null));

		invite = SessionControl.decodeSessionInvitatios(loggedUser.getString(
				SessionControl.COMPANIES_DETAIL_INVITE, null));

		if (!comments.isEmpty()) {
			for (OffersComment comment : comments) {
				nota = nota + Float.parseFloat(comment.getEvaluation());
				finalEvaluation = nota / comments.size();
			}

			showEvaluation(finalEvaluation, stars);

			adapter = new OffersCommentAdapter(v.getContext(), comments);
			list.setAdapter(adapter);
			qtdVotation.setText("+" + String.valueOf(comments.size()));
		} else {
			list.setVisibility(View.GONE);
			tvWarning.setVisibility(View.VISIBLE);
		}

		btnSign.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
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

	public static CompaniesDetailThirdActivity newInstance(String text) {
		CompaniesDetailThirdActivity f = new CompaniesDetailThirdActivity();
		Bundle b = new Bundle();
		b.putString("msg", text);

		f.setArguments(b);
		return f;
	}

	public void showEvaluation(float evaluation, List<ImageView> stars) {

		for (int i = 0; i < evaluation; i++) {
			stars.get(i).setImageResource(R.drawable.adventa_star_complete);
		}

	}

}
