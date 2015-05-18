package com.accential.trueone.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;

import com.accential.trueone.bean.OffersComment;
import com.accential.trueone.bo.OffersCommentBO;
import com.example.slide.OffersFragFourth.OffersCommentsResponseReceiver;

/**
 * Faz carregamento dos comentarios de acordo com o id da oferta
 * 
 * @author Matheus Odilon - accentialbrasil
 * 
 */
public class OffersCommentService extends IntentService {
	public static final String PARAM_OFFER_ID = "offerId";
	public static final String PARAM_COMMENTS_LIST = "comments";

	public OffersCommentService() {
		super("OffersCommentService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		List<OffersComment> comments = new ArrayList<OffersComment>();

		// recebe o id da oferta via intent
		int offerId = intent.getIntExtra(PARAM_OFFER_ID, 0);
		// faz a pesquisa dos comentarios de acordo com os id
		comments = OffersCommentBO.searchByOffer(offerId);

		// fazendo o envio da lista de comentarios
		Intent broadcastIntent = new Intent();
		broadcastIntent
				.setAction(OffersCommentsResponseReceiver.ACTION_RESP_OFFERSCOMMENT);
		broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
		Bundle bundle = new Bundle();
		bundle.putSerializable(PARAM_COMMENTS_LIST, (Serializable) comments);
		broadcastIntent.putExtras(bundle);
		sendBroadcast(broadcastIntent);
	}
}
