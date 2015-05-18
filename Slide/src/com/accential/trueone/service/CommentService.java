package com.accential.trueone.service;

import java.io.Serializable;
import java.util.List;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;

import com.accential.trueone.bean.OffersComment;
import com.accential.trueone.bo.OffersCommentBO;
import com.example.slide.ComprasActivity.CommentsResponseReceiver;

public class CommentService extends IntentService {
	public static final String PARAM_IN_USER_ID = "userId";
	public static final String PARAM_IN_OFFER_ID = "offerId";
	public static final String PARAM_OUT_COMMENTS = "comments";

	public CommentService() {
		super("CommentService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {

		int userId = intent.getIntExtra("userId", 0);
		int offerId = intent.getIntExtra("offerId", 0);

		List<OffersComment> comments = OffersCommentBO.listByUserAndOffer(
				userId, offerId);

		Intent broadcastIntent = new Intent();
		broadcastIntent
				.setAction(CommentsResponseReceiver.ACTION_RESP_COMMENTS_CHECK);
		broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
		Bundle bundle = new Bundle();
		bundle.putSerializable(PARAM_OUT_COMMENTS, (Serializable) comments);
		broadcastIntent.putExtras(bundle);
		sendBroadcast(broadcastIntent);
	}

}
