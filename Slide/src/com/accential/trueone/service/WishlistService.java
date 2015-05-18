package com.accential.trueone.service;

import java.io.Serializable;
import java.util.List;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.accential.trueone.bean.CompanyCategory;
import com.accential.trueone.bean.CompanySubCategory;
import com.accential.trueone.bo.CompanyCategoryBO;
import com.accential.trueone.bo.CompanySubCategoryBO;
import com.example.slide.WishlistActivity.WishlistResponseReceiver;

public class WishlistService extends IntentService {
	public static final String PARAM_IN_MSG = "imsg";
	public static final String PARAM_OUT_MSG = "omsg";
	public static final String PARAM_OUT_USER = "user";
	public static final String PARAM_OUT_CATEGORIE = "categoria";
	public static final String PARAM_OUT_SUB_CATEGORIE = "subcategoria";
	public static final String PARAM_IN_USER_ID = "userId";
	public static final String PARAM_OUT_USER_WISH = "userWishlist";
	public static final String PARAM_OUT_WISH_QTD_OFFER = "qtdOfferByWish";

	public WishlistService() {
		super("WishlistService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		Log.e("", "EXECUTANDO SERVICE 2");

		// capturando categorias
		List<CompanyCategory> categories = CompanyCategoryBO
				.getAllCompaniesCategory();

		// capturando subcategorias
		List<CompanySubCategory> subCategories = CompanySubCategoryBO
				.getByCategoryId(1);

		// fim do processo, comecamos o envio dos dados
		Intent broadcastIntent = new Intent();
		broadcastIntent.setAction(WishlistResponseReceiver.ACTION_RESPOSTA);
		broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
		Bundle bundle = new Bundle();
		bundle.putSerializable(PARAM_OUT_CATEGORIE, (Serializable) categories);
		bundle.putSerializable(PARAM_OUT_SUB_CATEGORIE,
				(Serializable) subCategories);
		broadcastIntent.putExtras(bundle);
		sendBroadcast(broadcastIntent);
	}
}