package com.example.slide;

import java.util.List;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.accential.trueone.adapter.OffersCommentAdapter;
import com.accential.trueone.bean.Offer;
import com.accential.trueone.bean.OffersComment;
import com.accential.trueone.bean.User;
import com.accential.trueone.service.OffersCommentService;
import com.accential.trueone.utils.SessionControl;

@SuppressWarnings("all")
public class OffersFragFourth extends Fragment {

	private View v;
	private Offer offer;
	private SharedPreferences loggedUser;
	private ListView lvOffersComment;
	private List<OffersComment> comments;
	private OffersCommentAdapter adapter;
	private OffersCommentsResponseReceiver receiver;
	private ProgressBar pbLoadList;
	private TextView tvWarning;

	private Button btnComprar;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (container == null) {
			return null;
		}
		v = inflater.inflate(R.layout.x_offers_tab_fourth, container, false);
		lvOffersComment = (ListView) v.findViewById(R.id.lvOffersComment);
		pbLoadList = (ProgressBar) v.findViewById(R.id.pbLoadList);
		tvWarning = (TextView) v.findViewById(R.id.tvOfferCommentsWarning);
		btnComprar = (Button) v.findViewById(R.id.btnComprar);
		Typeface tf = Typeface.createFromAsset(v.getContext().getAssets(), "helvetica-35-thin-1361522141.ttf");
		btnComprar.setTypeface(tf);
		
		Typeface font = Typeface.createFromAsset(v.getContext().getAssets(),
				"helvetica-normal.ttf");
		tvWarning.setTypeface(font);

		// capturando dados da oferta na "sessao"
		loggedUser = v.getContext().getSharedPreferences(
				SessionControl.USER_LOGGED, 0);

		offer = SessionControl.decodeSessionOffer(loggedUser.getString(
				SessionControl.OFFER_DETAIL, null));

		comments = SessionControl.decodeSessionOffersComments(loggedUser
				.getString(SessionControl.OFFER_COMMENT, null));

		if (comments.isEmpty()) {
			pbLoadList.setVisibility(View.GONE);
			tvWarning.setVisibility(View.VISIBLE);
			Log.e("", "A LISTA DE COMENTARIOS ESTÁ VAZIA!!!");
		} else {
			adapter = new OffersCommentAdapter(v.getContext(), comments);
			lvOffersComment.setAdapter(adapter);
			pbLoadList.setVisibility(View.GONE);
		}

		// start service de busca dos comentarios
		/*
		 * Intent commentsIntent = new Intent(v.getContext(),
		 * OffersCommentService.class);
		 * commentsIntent.putExtra(OffersCommentService.PARAM_OFFER_ID,
		 * offer.getId()); v.getContext().startService(commentsIntent);
		 */

		// preparando o receiver para o retorno do service
		/*
		 * IntentFilter filter = new IntentFilter(
		 * OffersCommentsResponseReceiver.ACTION_RESP_OFFERSCOMMENT);
		 * filter.addCategory(Intent.CATEGORY_DEFAULT); receiver = new
		 * OffersCommentsResponseReceiver();
		 * v.getContext().registerReceiver(receiver, filter);
		 */

		/**
		 * Clique no botao comprar - Verificamos se usuario está logado ou não.
		 * Caso esteja seguimos o fluxo e vamos ao checkout. - Caso nao esteja
		 * mostramos tela de login
		 */
		btnComprar.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				// logica de verificacao aqui
				User user = SessionControl.decodeSessionUser(loggedUser
						.getString("user", null));

				if (user == null) {
					Toast.makeText(v.getContext(),
							"Faça o Login e continue sua compra!!!",
							Toast.LENGTH_LONG).show();

					// SessionControl.logoof(v.getContext(), loggedUser);
					// codigo do metodo logoof
					SharedPreferences.Editor editor = loggedUser.edit();
					editor.clear();
					editor.commit();

					Intent intent = new Intent(v.getContext(), FBActivity.class);
					intent.putExtra(SessionControl.CONTINUE_SHOPPING,
							SessionControl.encodeSessionOffer(offer));
					startActivity(intent);

				} else {
					Intent intent = new Intent(v.getContext(),
							CheckoutActivity.class);
					startActivity(intent);
				}
			}
		});

		return v;
	}

	public class OffersCommentsResponseReceiver extends BroadcastReceiver {
		public static final String ACTION_RESP_OFFERSCOMMENT = "com.mamlambo.intent.action.MESSAGE_PROCESSED_OFFERSCOMMENT";

		@Override
		public void onReceive(Context context, Intent intent) {

			Bundle parameters = intent.getExtras();
			comments = (List<OffersComment>) parameters
					.getSerializable(OffersCommentService.PARAM_COMMENTS_LIST);

			adapter = new OffersCommentAdapter(v.getContext(), comments);
			lvOffersComment.setAdapter(adapter);

			pbLoadList.setVisibility(View.GONE);
		}
	}

}