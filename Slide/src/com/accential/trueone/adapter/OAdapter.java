package com.accential.trueone.adapter;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.accential.trueone.bean.Offer;
import com.accential.trueone.utils.DownloadImageTask;
import com.example.slide.R;
import com.loopj.android.image.SmartImageView;

/**
 * Classe Adapter que representa o objeto Offer com seus referentes atributos
 * dentro de uma lista
 * 
 * @author Matheus Odilon - accentialbrasil
 * 
 */
public class OAdapter extends BaseAdapter {

	public List<Offer> offers;
	public LayoutInflater mInflater;
	public SmartImageView imageView;
	public SmartImageView smartLogoComp;
	public SmartImageView imageViewComp;
	public WebView webViewHtmlImage;
	public ProgressBar progress;
	public TextView myBtnComprar;
	public View testeView;

	public OAdapter(List<Offer> offers, Context context) {
		this.offers = offers;
		mInflater = LayoutInflater.from(context);

	}

	@Override
	public int getCount() {
		return offers.size();
	}

	@Override
	public Object getItem(int position) {
		return offers.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	/**
	 * Metodo que seta os atributos do objeto Offer nos elementos visuais da
	 * Activity item_offer
	 */
	@SuppressLint("DefaultLocale")
	@Override
	public View getView(int position, View view, ViewGroup parent) {

		// PEGA O ITEM DE ACORDO COM A POSICAO
		Offer offer = offers.get(position);

		// INFLA O LAYOUT PARA PODERMOS PREENCHER OS DADOS
		view = mInflater.inflate(R.layout.activity_item_offer_horizintal, null);

		/*
		 * troca de smart para ImageView no codigo original
		 */
		/*
		 * imageView = (SmartImageView) view
		 * .findViewById(R.id.imageView_offerFoto);
		 */
		new DownloadImageTask(
				(ImageView) view.findViewById(R.id.imageView_offerFoto))
				.execute(offer.getPhoto());

		// imageView.setTag(position);
		imageViewComp = (SmartImageView) view
				.findViewById(R.id.imageView_CompanyPhoto);

		// SETANDO BOTAO
		// myBtnComprar = (TextView)
		// view.findViewById(R.id.textView_valorAtual);
		// myBtnComprar.setTag(1);
		// --

		testeView = (View) view.findViewById(R.id.viewTeste);
		testeView.setTag(position);

		// IMAGE COMPANY
		// if(!offer.getCompany().getLogo().equals("UPLOAD_ERROR")){
		// imageViewComp.setImageUrl(offer.getCompany().getLogo());
		// }else{
		// imageViewComp.setImageResource(R.drawable.not_found_logo);
		// }

		float total = 0;
		int percentage = offer.getPercentageDiscount();
		if (percentage != 0) {
			float value = offer.getValue();
			float desconto = (value * percentage) / 100;
			total = value - desconto;
		} else {
			total = offer.getValue();
		}

		float valParcela = total / 24;

		// FORMATA VALOR DA CONTA
		DecimalFormat df = new DecimalFormat("#.00");

		((TextView) view.findViewById(R.id.textView_TituloOffer)).setText(offer
				.getTitle());
		((TextView) view.findViewById(R.id.textView_numParcelas))
				.setText("em at�� 24x de R$ "
						+ String.valueOf(df.format(valParcela)).replace(".",
								","));
		((TextView) view.findViewById(R.id.textView_valorOriginal))
				.setText("de R$".concat(String.valueOf(offer.getValue())));
		((TextView) view.findViewById(R.id.textView_valorAtual))
				.setText("Por R$".concat(String.valueOf(df.format(total))));

		((TextView) view.findViewById(R.id.textView_valiDate))
				.setText("V��lido at��".concat(String.valueOf(" "
						+ offer.getEndsAt().get(Calendar.DAY_OF_MONTH) + "/"
						+ (offer.getEndsAt().get(Calendar.MONTH) + 1) + "/"
						+ offer.getEndsAt().get(Calendar.YEAR))));

		return view;

	}

}
