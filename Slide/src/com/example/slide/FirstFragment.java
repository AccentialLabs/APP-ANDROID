package com.example.slide;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.accential.trueone.adapter.OAdapter;
import com.accential.trueone.adapter.OfferMosaicAdapter;
import com.accential.trueone.bean.Offer;
import com.accential.trueone.bean.User;
import com.accential.trueone.utils.SessionControl;

/**
 * Tela Inicial - Listagem das ofertas
 * 
 * @author Matheus Odilon - accentialbrasil
 * 
 */
@SuppressWarnings("unused")
public class FirstFragment extends Fragment {

	private View v;
	private OAdapter oAdapter;
	private OfferMosaicAdapter mosaicAdapter;
	private GridView offersGridView;
	private static List<Offer> myOffers;
	// private TextView txUserName;
	private SharedPreferences loggedUser;
	private User user;
	private boolean conection;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		v = inflater.inflate(R.layout.first_frag, container, false);

		// capturamos views da nossa tela e iniciando demais variaveis
		// TextView tv = (TextView) v.findViewById(R.id.tvFragFirst);
		offersGridView = (GridView) v.findViewById(R.id.offersGridView);
		// txUserName = (TextView) v.findViewById(R.id.txUserName);
		Button btnSig = (Button) v.findViewById(R.id.btnSignatures);
		Button btnCompras = (Button) v.findViewById(R.id.btnCompras);
		Button btnWish = (Button) v.findViewById(R.id.btnWish);
		Button btnRefresh = (Button) v.findViewById(R.id.btnRefresh);
		Button btnFiltrar = (Button) v.findViewById(R.id.btnFiltrar);

		loggedUser = inflater.getContext().getSharedPreferences(
				SessionControl.USER_LOGGED, 0);
		user = SessionControl.decodeSessionUser(loggedUser.getString("user",
				null));

		// apaga company salva na vizuali.. do detalhe de alguma oferta para que
		// a lista seja carregada novamente
		SharedPreferences.Editor editor = loggedUser.edit();
		editor.remove(SessionControl.OFFER_DETAILS_COMPANY);
		editor.commit();

		// verifica conexao
		conection = SessionControl.checkInternetConnection(v.getContext());
		if (conection == false) {
			btnSig.setVisibility(View.INVISIBLE);
			btnCompras.setVisibility(View.INVISIBLE);
			btnWish.setVisibility(View.INVISIBLE);
			btnRefresh.setVisibility(View.VISIBLE);
		}

		Log.e("conexao", "conexao é: " + String.valueOf(conection));

		// txUserName.setText(user.getName());

		// clique em item da lista
		offersGridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long arg3) {

				Offer offer = myOffers.get(position);
				Toast.makeText(view.getContext(), offer.getTitle(),
						Toast.LENGTH_LONG).show();

				SharedPreferences loggedUser = view.getContext()
						.getSharedPreferences(SessionControl.USER_LOGGED, 0);
				SharedPreferences.Editor editor = loggedUser.edit();

				String stringUser = SessionControl.encodeSessionOffer(offer);

				editor.putString(SessionControl.OFFER_DETAIL, stringUser);
				editor.commit();

				Intent intent = new Intent(view.getContext(),
						OffersDetailActivity.class);
				startActivity(intent);
			}
		});

		// atribuindo dados às views
		// tv.setText(getArguments().getString("msg"));

		// validando se lista está ou não vazia
		if (myOffers.isEmpty()) {
			Toast.makeText(
					v.getContext(),
					"Ops... Ocorreu algum erro durante o carregamento das ofertas."
							+ " Verifique sua conexão e tente novamente.",
					Toast.LENGTH_LONG).show();
		} else {
			mosaicAdapter = new OfferMosaicAdapter(myOffers, v.getContext());
			// oAdapter = new OAdapter(myOffers, v.getContext());
			offersGridView.setAdapter(mosaicAdapter);
		}

		btnSig.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(v.getContext(),
						SignaturesActivity.class);
				startActivity(intent);
			}
		});

		btnCompras.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(v.getContext(),
						ComprasActivity.class);
				startActivity(intent);
			}
		});

		btnWish.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(view.getContext(),
						WishHomeActivity.class);
				startActivity(intent);
			}
		});

		// clique no botao filtrar
		btnFiltrar.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				LayoutInflater layoutInflater = LayoutInflater.from(v
						.getContext());

				View promptView = layoutInflater.inflate(
						R.layout.x_pop_opcoes_filtro, null);

				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
						v.getContext());

				alertDialogBuilder.setView(promptView);

				final AlertDialog alertD = alertDialogBuilder.create();

				Button btnCategoria = (Button) promptView
						.findViewById(R.id.btnFilterByCategory);
				Button btnMyProfile = (Button) promptView
						.findViewById(R.id.btnFilterMyProfile);
				Button btnOtherProfile = (Button) promptView
						.findViewById(R.id.btnFilterOtherProfile);
				Button btnCancel = (Button) promptView
						.findViewById(R.id.btnFilterCancel);

				// cancelar
				btnCancel.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {

						alertD.cancel();
					}
				});

				// outro perfil
				btnOtherProfile.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						// vamos para a tela de criação de outro perfil
						Intent intent = new Intent(v.getContext(),
								OfferFilterActivity.class);
						startActivity(intent);
					}
				});

				// meu perfil
				btnMyProfile.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						// codigo
					}
				});

				// menu de categorias
				btnCategoria.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent intent = new Intent(v.getContext(),
								CategoriesActivity.class);
						startActivity(intent);
					}
				});

				alertD.show();
			}
		});
		return v;
	}

	// recebe paramentros enviados da activity principal
	public static FirstFragment newInstance(String text, List<Offer> offers,
			User user) {

		// nossa lista recebe as ofertas enviadas pela classe Main
		myOffers = offers;

		// recebemos usuario logado
		Log.e("FirstFrag", "nome do usuario logado: " + user.getName());

		FirstFragment f = new FirstFragment();
		Bundle b = new Bundle();
		b.putString("msg", text);

		f.setArguments(b);

		return f;
	}

}
