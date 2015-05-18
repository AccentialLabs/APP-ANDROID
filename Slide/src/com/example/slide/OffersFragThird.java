package com.example.slide;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.accential.trueone.bean.Offer;
import com.accential.trueone.bean.User;
import com.accential.trueone.utils.SessionControl;

public class OffersFragThird extends Fragment {

	private View v;
	private Offer offer;
	private SharedPreferences loggedUser;
	private TextView tvOfferEsp;

	private Button btnComprar;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (container == null) {
			return null;
		}
		v = inflater.inflate(R.layout.x_offers_tab_third, container, false);
		tvOfferEsp = (TextView) v.findViewById(R.id.tvOfferEsp);
		btnComprar = (Button) v.findViewById(R.id.btnComprar);
		Typeface tf = Typeface.createFromAsset(v.getContext().getAssets(), "helvetica-35-thin-1361522141.ttf");
		btnComprar.setTypeface(tf);
		
		// capturando dados da oferta na "sessao"
		loggedUser = v.getContext().getSharedPreferences(
				SessionControl.USER_LOGGED, 0);
		offer = SessionControl.decodeSessionOffer(loggedUser.getString(
				SessionControl.OFFER_DETAIL, null));

		tvOfferEsp.setText(Html.fromHtml(offer.getSpecification()));

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
							Toast.LENGTH_SHORT).show();

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

}
