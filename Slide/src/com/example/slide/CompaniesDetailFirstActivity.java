package com.example.slide;

import java.util.List;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.accential.trueone.bean.CompaniesInvitationsUser;
import com.accential.trueone.bean.Company;
import com.accential.trueone.bean.Offer;
import com.accential.trueone.bean.OffersComment;
import com.accential.trueone.bo.CompaniesInvitationsUserBO;
import com.accential.trueone.service.CompaniesDetailService;
import com.accential.trueone.utils.SessionControl;
import com.loopj.android.image.SmartImageView;

public class CompaniesDetailFirstActivity extends Fragment {

	private View v;
	private Company company;
	private CompaniesInvitationsUser invite;
	private View vBlock;
	private ProgressBar pbPrincipal;
	private List<Offer> offers;
	private List<OffersComment> comments;
	private CompaniesDetailResponseReceiver receiver;
	private SharedPreferences loggedUser;
	private SmartImageView compPhoto;
	private TextView compName;
	private TextView compDesc;
	private TextView compNumber;
	private TextView compEmail;
	private TextView compSite;
	private Button btnSign;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		v = inflater.inflate(R.layout.x_activity_comp_details_first, null);

		vBlock = v.findViewById(R.id.vCompDetailBlock);
		pbPrincipal = (ProgressBar) v.findViewById(R.id.pbCompDetailPrincipal);
		compPhoto = (SmartImageView) v.findViewById(R.id.imgCompDetailLogo);
		compName = (TextView) v.findViewById(R.id.tvCompDetailTitle);
		compDesc = (TextView) v.findViewById(R.id.tvCompDetailDesc);
		compNumber = (TextView) v.findViewById(R.id.textView3);
		compEmail = (TextView) v.findViewById(R.id.tvCompDetailEmail);
		compSite = (TextView) v.findViewById(R.id.tvCompDetailSite);
		btnSign = (Button) v.findViewById(R.id.btnCompDetailAssinar);

		loggedUser = v.getContext().getSharedPreferences(
				SessionControl.USER_LOGGED, 0);

		company = SessionControl.decodeSessionCompany(loggedUser.getString(
				SessionControl.COMPANIES_DETAIL_COMPANY, null));

		invite = SessionControl.decodeSessionInvitatios(loggedUser.getString(
				SessionControl.COMPANIES_DETAIL_INVITE, null));

		Log.e("", "ID DO INVITE: " + String.valueOf(invite.getId()));

		compName.setText(company.getFancy_name());
		compDesc.setText(company.getDescription());
		compEmail.setText(company.getEmail());
		compNumber.setText(company.getPhone());
		compSite.setText(company.getSite_url());

		try {
			if (company.getLogo() == "NO_CONNECTION") {
				compPhoto.setImageResource(R.drawable.accential);
			} else if (company.getLogo() == "UPLOAD_ERROR") {
				compPhoto.setImageResource(R.drawable.accential);
			} else {
				compPhoto.setImageUrl(company.getLogo());
			}
		} catch (Exception e) {
			Log.e("", "Erro no downloado da imagem");
			e.printStackTrace();
		}

		// chamando service
		Intent commentsIntent = new Intent(v.getContext(),
				CompaniesDetailService.class);
		commentsIntent.putExtra(CompaniesDetailService.PARAM_IN_COMPANY_ID,
				company.getId());
		v.getContext().startService(commentsIntent);

		// preparando o receiver para o retorno do service
		IntentFilter filter = new IntentFilter(
				CompaniesDetailResponseReceiver.ACTION_RESPONSE_COMPANIES_DETAIL);
		filter.addCategory(Intent.CATEGORY_DEFAULT);
		receiver = new CompaniesDetailResponseReceiver();
		v.getContext().registerReceiver(receiver, filter);

		// clique em email
		compEmail.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent sendIntent = new Intent(Intent.ACTION_VIEW);
				sendIntent.setType("plain/text");
				sendIntent.setData(Uri.parse(company.getEmail()));
				sendIntent.setClassName("com.google.android.gm",
						"com.google.android.gm.ComposeActivityGmail");
				sendIntent.putExtra(Intent.EXTRA_EMAIL,
						new String[] { company.getEmail() });
				sendIntent.putExtra(Intent.EXTRA_SUBJECT, "Contato ADVENTA");
				startActivity(sendIntent);
			}
		});

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

	public static CompaniesDetailFirstActivity newInstance(String text) {
		CompaniesDetailFirstActivity f = new CompaniesDetailFirstActivity();
		Bundle b = new Bundle();
		b.putString("msg", text);

		f.setArguments(b);

		return f;
	}

	@SuppressWarnings("all")
	public class CompaniesDetailResponseReceiver extends BroadcastReceiver {
		public static final String ACTION_RESPONSE_COMPANIES_DETAIL = "com.mamlambo.intent.action.MESSAGE_PROCESSED_COMPANIES_DETAIL";

		@Override
		public void onReceive(Context context, Intent intent) {

			Bundle parameters = intent.getExtras();
			offers = ((List<Offer>) parameters
					.getSerializable(CompaniesDetailService.PARAM_OUT_OFFERS));

			comments = ((List<OffersComment>) parameters
					.getSerializable(CompaniesDetailService.PARAM_OUT_COMMENTS));

			Log.e("",
					"END OF SERVICE - OFFERS " + String.valueOf(offers.size()));
			SharedPreferences.Editor editor = loggedUser.edit();

			String stringOffers = SessionControl.encodeSessionOffers(offers);

			String stringComments = SessionControl
					.encodeSessionOffersComment(comments);

			editor.putString(SessionControl.COMPANIES_DETAIL_OFFERS,
					stringOffers);
			editor.putString(SessionControl.COMPANIES_DETAIL_COMMENTS,
					stringComments);

			editor.commit();

			vBlock.setVisibility(View.GONE);
			pbPrincipal.setVisibility(View.GONE);
		}
	}

}
