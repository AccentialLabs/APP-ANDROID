package com.example.slide;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.accential.trueone.adapter.MetricAdapter;
import com.accential.trueone.bean.Company;
import com.accential.trueone.bean.CompanyPreference;
import com.accential.trueone.bean.Offer;
import com.accential.trueone.bean.OfferPhoto;
import com.accential.trueone.bean.OffersComment;
import com.accential.trueone.bean.User;
import com.accential.trueone.service.OfferDetailService;
import com.accential.trueone.utils.SessionControl;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.image.SmartImageView;

@SuppressWarnings("all")
public class OffersFragFirst extends Fragment {

	private View v;
	private TextView tvTitle;
	private TextView tvResume;
	private SmartImageView svOfferImg;
	private Offer offer;
	private SharedPreferences loggedUser;
	private Button btnComprar;
	private Button btnShared;
	private OffersDetailsResponseReceiver receiver;
	private Company company;
	private List<CompanyPreference> preferences;
	private List<OfferPhoto> photos;
	private ProgressBar pbDetails;
	private View vBlock;
	private View vResume;
	private View vTitle;
	private View vMetric;
	// private TextView tvQtdComment;

	private List<SmartImageView> photosExtras;
	private SmartImageView photoExtra1;
	private SmartImageView photoExtra2;
	private SmartImageView photoExtra3;
	private SmartImageView photoextra4;
	private SmartImageView photoextra5;

	private List<ImageView> stars;
	private ImageView star1;
	private ImageView star2;
	private ImageView star3;
	private ImageView star4;
	private ImageView star5;
	private SmartImageView compLogo;

	private MetricAdapter firstMetric;

	private ArrayAdapter<String> adapter;
	private List<String> met;

	// recebe os valores vindo do preferences
	private String firstMetricValue;
	private String secondMetricValue;

	// mostram na tela o valor das metricas selecionadas
	private TextView showFirstMetric;
	private TextView showSecondMetric;
	private TextView tvPlusLess;

	private View lineMetrics;

	private TextView tvPreco;
	private TextView tvPrecoDesconto;
	private TextView tvPrecoParcela;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (container == null) {
			return null;
		}

		v = inflater.inflate(R.layout.x_offers_tab_first, container, false);

		photosExtras = new ArrayList<SmartImageView>();
		tvPreco = (TextView) v.findViewById(R.id.tvOfferValOriginal);
		tvPrecoDesconto = (TextView) v.findViewById(R.id.tvOfferValComDesconto);
		tvPrecoParcela = (TextView) v.findViewById(R.id.textView2);

		pbDetails = (ProgressBar) v.findViewById(R.id.pbOffersDetails);
		btnComprar = (Button) v.findViewById(R.id.btnComprar);
		tvTitle = (TextView) v.findViewById(R.id.textView1);
		tvResume = (TextView) v.findViewById(R.id.textView3);
		// tvMetric = (TextView) v.findViewById(R.id.tvMetricTitle);
		svOfferImg = (SmartImageView) v.findViewById(R.id.svOfferImg);
		btnShared = (Button) v.findViewById(R.id.btnShare);
		// spMetrics = (Spinner) v.findViewById(R.id.spMetrics);
		vBlock = (View) v.findViewById(R.id.vBlockOffersDetails);
		vResume = (View) v.findViewById(R.id.view2);
		vTitle = (View) v.findViewById(R.id.view1);
		vMetric = (View) v.findViewById(R.id.vMetrics);
		lineMetrics = v.findViewById(R.id.view5);
		tvPlusLess = (TextView) v.findViewById(R.id.tvPlusOrLess);
		compLogo = (SmartImageView) v.findViewById(R.id.imgCompLogo);
		// tvQtdComment = (TextView) v.findViewById(R.id.tvOfferQtdComment);

		Typeface tf = Typeface.createFromAsset(v.getContext().getAssets(), "helvetica-35-thin-1361522141.ttf");
		btnComprar.setTypeface(tf);
		tvPlusLess.setTypeface(tf);
		
		
		showFirstMetric = (TextView) v.findViewById(R.id.textView4);
		showSecondMetric = (TextView) v.findViewById(R.id.textView5);

		met = new ArrayList<String>();
		met.add("A");
		met.add("B");
		met.add("C");
		met.add("D");

		photoExtra1 = (SmartImageView) v.findViewById(R.id.ivOfferPhoto1);
		photoExtra2 = (SmartImageView) v.findViewById(R.id.ivOfferPhoto2);
		photoExtra3 = (SmartImageView) v.findViewById(R.id.ivOfferPhoto3);
		photoextra4 = (SmartImageView) v.findViewById(R.id.ivOfferPhoto4);
		photoextra5 = (SmartImageView) v.findViewById(R.id.ivOfferPhoto5);

		// estrela
		star1 = (ImageView) v.findViewById(R.id.star1);
		star2 = (ImageView) v.findViewById(R.id.star2);
		star3 = (ImageView) v.findViewById(R.id.star3);
		star4 = (ImageView) v.findViewById(R.id.star4);
		star5 = (ImageView) v.findViewById(R.id.star5);

		stars = new ArrayList<ImageView>();
		stars.add(star1);
		stars.add(star2);
		stars.add(star3);
		stars.add(star4);
		stars.add(star5);

		// array dos textviews
		photosExtras.add(photoExtra1);
		photosExtras.add(photoExtra2);
		photosExtras.add(photoExtra3);
		photosExtras.add(photoextra4);
		photosExtras.add(photoextra5);

		// capturando dados da oferta na "sessao"
		loggedUser = v.getContext().getSharedPreferences(
				SessionControl.USER_LOGGED, 0);
		offer = SessionControl.decodeSessionOffer(loggedUser.getString(
				SessionControl.OFFER_DETAIL, null));

		company = SessionControl.decodeSessionCompany(loggedUser.getString(
				SessionControl.OFFER_DETAILS_COMPANY, null));

		photos = SessionControl.decodeSessionOffersPhoto(loggedUser.getString(
				SessionControl.OFFERS_PHOTOS, null));
	
		
		float valorComDesconto = 0;
		// valor dos precos
		if (offer.getPercentageDiscount() == 0) {
			tvPrecoDesconto.setText(String.format("Por R$%.2f",
					offer.getValue()));
			valorComDesconto = offer.getValue();
		} else {
			// calculo desconto
			float desconto = (Float.parseFloat(String.valueOf(offer
					.getPercentageDiscount())) / 100);
			float valDesconto = (desconto * offer.getValue());
			valorComDesconto = (offer.getValue() - valDesconto);
			tvPrecoDesconto.setText(String.format("Por R$%.2f",
					valorComDesconto));
		}
		tvPreco.setText(String.format("De R$%.2f", offer.getValue()));
		float valParcelas = valorComDesconto / 12;
		tvPrecoParcela.setText(String.format("12x de R$%.2f", valParcelas));

		// recebendo valores selecionados das metricas salvos no preference
		firstMetricValue = loggedUser.getString(
				SessionControl.OFFER_METRIC_VALUE_FIRST, null);
		secondMetricValue = loggedUser.getString(
				SessionControl.OFFER_METRIC_VALUE_SECOND, null);

		// primeira metrica
		if (firstMetricValue != null) {
			if (!firstMetricValue.equals("")) {
				if (firstMetricValue.contains("#")) {
					showFirstMetric.setText("");
					showFirstMetric.setBackgroundColor(Color
							.parseColor(firstMetricValue));
				} else {
					showFirstMetric.setText(firstMetricValue);
				}
			}
		}
		// segunda metrica
		if (secondMetricValue != null) {
			if (!secondMetricValue.equals("")) {
				if (secondMetricValue.contains("#")) {
					showSecondMetric.setText("");
					showSecondMetric.setBackgroundColor(Color
							.parseColor(secondMetricValue));
				} else {
					showSecondMetric.setText(secondMetricValue);
				}
			}
		}

		// caso oferta nao tenha metricas somente escondemos os quadrados
		if (offer.getMetrics().equals("")) {
			showFirstMetric.setVisibility(View.INVISIBLE);
			showSecondMetric.setVisibility(View.INVISIBLE);
			showSecondMetric.setClickable(false);
			showFirstMetric.setClickable(false);
			lineMetrics.setVisibility(View.INVISIBLE);
		}

		// ImageView userImg = (ImageView) v.findViewById(R.id.imageView2);
		//
		// // clique na imagem do usuario
		// userImg.setOnClickListener(new OnClickListener() {
		// @Override
		// public void onClick(View v) {
		// Intent intent = new Intent(v.getContext(), UserActivity.class);
		// startActivity(intent);
		// }
		// });

		// ImageView userImg = (ImageView) v.findViewById(R.id.imageView2);
		//
		// // clique na imagem do usuario
		// userImg.setOnClickListener(new OnClickListener() {
		// @Override
		// public void onClick(View v) {
		// Intent intent = new Intent(O.this,
		// UserActivity.class);
		// startActivity(intent);
		// }
		// });

		// se lista não estiver vazia
		// if (!photos.isEmpty()) {
		// mostrarExtraFotos(photosExtras, photos);
		// }

		// reproduzindo fotos
		// photos = OfferPhotoBO.listPhotoByOffer(offer.getId());
		// Log.e("offer", "PHOTOS: " + String.valueOf(photos.size()));
		//
		// int i = 0;
		// for (OfferPhoto photo : photos) {
		// photosExtras.get(i).setImageUrl(photo.getPhotoUrl());
		// i++;
		// }

		tvTitle.setText(offer.getTitle());
		tvResume.setText(offer.getResume());
		svOfferImg.setImageUrl(offer.getPhoto());

		if (company == null) {
			Log.e("", "CMPANY ESTÁ VAZIA");
			// decodeMetricsWithTwoParams();
			pbDetails.setVisibility(View.VISIBLE);
			vBlock.setVisibility(View.VISIBLE);
			Intent commentsIntent = new Intent(v.getContext(),
					OfferDetailService.class);
			commentsIntent.putExtra(OfferDetailService.PARAM_IN_OFFER_ID,
					offer.getId());
			v.getContext().startService(commentsIntent);

			IntentFilter filter = new IntentFilter(
					OffersDetailsResponseReceiver.ACTION_RESP_OFFERS_DETAILS);
			filter.addCategory(Intent.CATEGORY_DEFAULT);
			receiver = new OffersDetailsResponseReceiver();
			v.getContext().registerReceiver(receiver, filter);
		} else {
			btnComprar.setVisibility(View.VISIBLE);

			// recupera nota e mostra nas estrelas
			float nota = Float.parseFloat(loggedUser.getString(
					"offerEvaluation", null));

			for (int y = 0; y < nota; y++) {
				stars.get(y).setImageResource(R.drawable.adventa_star_complete);
			}

			// tvQtdComment.setText("+" + );
		}

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

		btnShared.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String shareBody = "Aproveite essa oferta exclusiva que está disponível somente no ADVENTA: "
						+ offer.getTitle();
				Intent sharingIntent = new Intent(
						android.content.Intent.ACTION_SEND);
				sharingIntent.setType("text/plain");
				sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
						"Oferta " + offer.getTitle());
				sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT,
						shareBody);
				startActivity(Intent.createChooser(sharingIntent,
						"Compartilhe por..."));
			}
		});

		// clique na imagem principal - zoom
		svOfferImg.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(v.getContext(),
						PhotoZoomActivity.class);
				intent.putExtra("urlDaPhoto", offer.getPhoto());
				startActivity(intent);
			}
		});
		// clique na imagem extra 1
		photoExtra1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(v.getContext(),
						PhotoZoomActivity.class);
				intent.putExtra("urlDaPhoto", photos.get(0).getPhotoUrl());
				startActivity(intent);
			}
		});
		// clique na imagem extra 2
		photoExtra2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(v.getContext(),
						PhotoZoomActivity.class);
				intent.putExtra("urlDaPhoto", photos.get(1).getPhotoUrl());
				startActivity(intent);
			}
		});
		// clique na imagem extra 3
		photoExtra3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(v.getContext(),
						PhotoZoomActivity.class);
				intent.putExtra("urlDaPhoto", photos.get(2).getPhotoUrl());
				startActivity(intent);
			}
		});
		// clique na imagem extra 4
		photoextra4.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(v.getContext(),
						PhotoZoomActivity.class);
				intent.putExtra("urlDaPhoto", photos.get(3).getPhotoUrl());
				startActivity(intent);
			}
		});
		// clique na imagem extra 5
		photoextra5.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(v.getContext(),
						PhotoZoomActivity.class);
				intent.putExtra("urlDaPhoto", photos.get(4).getPhotoUrl());
				startActivity(intent);
			}
		});

		tvPlusLess.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (vResume.getVisibility() == View.GONE) {
					vResume.setVisibility(View.VISIBLE);
					tvResume.setVisibility(View.VISIBLE);
					tvPlusLess.setText("-");
				} else {
					vResume.setVisibility(View.GONE);
					tvResume.setVisibility(View.GONE);
					tvPlusLess.setText("+");
				}
			}
		});

		// clique em escolher metricas
		if (!offer.getMetrics().equals("")) {
			vMetric.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(v.getContext(),
							OfferDetailMetricActivity.class);
					startActivity(intent);
				}
			});
		} else {
			vMetric.setBackgroundColor(Color.WHITE);
		}

		return v;
	}

	public class OffersDetailsResponseReceiver extends BroadcastReceiver {
		public static final String ACTION_RESP_OFFERS_DETAILS = "com.mamlambo.intent.action.MESSAGE_PROCESSED_RESP_OFFER_DETAIL";

		@Override
		public void onReceive(Context context, Intent intent) {

			// recurando company e companyPreference
			Bundle parameters = intent.getExtras();
			company = (Company) parameters
					.getSerializable(OfferDetailService.PARAM_OUT_COMPANY);

			preferences = (List<CompanyPreference>) parameters
					.getSerializable(OfferDetailService.PARAM_OUT_PREFERENCE);

			List<OffersComment> comments = (List<OffersComment>) parameters
					.getSerializable(OfferDetailService.PARAM_OUT_COMMENTS);

			// fotos da oferta
			photos = (List<OfferPhoto>) parameters
					.getSerializable(OfferDetailService.PARAM_OUT_PHOTOS);

			Log.e("", "QTD DE FOTOS EXTRAS: " + String.valueOf(photos.size()));

			calculaNota(comments);

			if (!photos.isEmpty()) {
				mostrarExtraFotos(photosExtras, photos);
			}

			CompanyPreference preference = preferences.get(0);

			// salvando dados na "sessão"
			SharedPreferences.Editor editor = loggedUser.edit();

			String companyString = SessionControl.encodeSessionCompany(company);
			String preferenceString = SessionControl
					.encodeSessionCompanyPreference(preference);
			String commentsString = SessionControl
					.encodeSessionOffersComment(comments);
			String offersPhotoString = SessionControl
					.encodeSessionOffersPhotos(photos);

			editor.putString(SessionControl.OFFERS_PHOTOS, offersPhotoString);
			editor.putString(SessionControl.OFFER_COMMENT, commentsString);
			editor.putString(SessionControl.OFFER_DETAILS_COMPANY,
					companyString);
			editor.putString(SessionControl.OFFER_DETAILS_COMPANY_PREFERENCES,
					preferenceString);

			editor.commit();

			btnComprar.setVisibility(View.VISIBLE);

			pbDetails.setVisibility(View.GONE);
			vBlock.setVisibility(View.GONE);
			btnComprar.setClickable(true);
		}
	}

	public void calculaNota(List<OffersComment> comments) {

		float nota = 0;

		for (OffersComment comment : comments) {
			nota = nota + Float.parseFloat(comment.getEvaluation());
		}

		float notaFinal = nota / comments.size();

		for (int i = 0; i < notaFinal; i++) {
			stars.get(i).setImageResource(R.drawable.adventa_star_complete);
		}

		// tvQtdComment.setText("+" + String.valueOf(comments.size()));

		// salvando a nota na "sessao"
		SharedPreferences.Editor editor = loggedUser.edit();
		editor.putString("offerEvaluation", String.valueOf(notaFinal));
		editor.commit();
	}

	public void mostrarExtraFotos(List<SmartImageView> siv,
			List<OfferPhoto> photos) {

		int qtdFotos = photos.size();
		// int usa = 5 - qtdFotos;
		int usa = qtdFotos;
		for (int i = 0; i < usa; i++) {
			siv.get(i).setImageUrl(photos.get(i).getPhotoUrl());
			siv.get(i).setVisibility(View.VISIBLE);
		}
	}

	public void decodeMetrics() {
		if (!offer.getMetrics().equals("")) {
			String[] chaveValor = offer.getMetrics().split(":");
			String chave = chaveValor[0].replace("[", "").replace("\"", "")
					.replace("{", "");

			String valor = chaveValor[1].replace("[", "").replace("]", "")
					.replace("}", "").replace("\"", "");

			String[] valorMetric = valor.split(",");

			Map metrics = new HashMap();
			metrics.put(chave, valorMetric);

			adapter = new ArrayAdapter<String>(v.getContext(),
					android.R.layout.simple_spinner_item,
					(String[]) metrics.get(chave));

			Log.e("", "ESSE É O MAPA DE METRICAS: " + metrics.toString());

			// spMetrics.setAdapter(adapter);
			// tvMetric.setText(chave);
		} else {
			// spMetrics.setVisibility(View.GONE);
			// tvMetric.setVisibility(View.GONE);
		}
	}

	/**
	 * Transforma string metrics para Map
	 */
	public Map decodeMetricsWithTwoParams() {

		if (!offer.getMetrics().equals("")) {
			try {

				Gson gson = new GsonBuilder().create();
				java.lang.reflect.Type typeOfHashMap = new TypeToken<Map<String, List<String>>>() {
				}.getType();
				Map<String, List<String>> newMap = gson.fromJson(
						offer.getMetrics(), typeOfHashMap);

				return newMap;
			} catch (Exception e) {
				Log.e("", "ERROR ERROR ERROR ERROR ERROR ERROR ERROR ");
				e.printStackTrace();
				return null;
			}
		} else {
			return null;
		}
	}
}