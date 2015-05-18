package com.accential.trueone.service;

import java.io.Serializable;
import java.util.List;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.accential.trueone.bean.CompanyCategory;
import com.accential.trueone.bo.CompanyCategoryBO;
import com.example.slide.CategoriesActivity.CategoriesResponseReceiver;

/**
 * 
 * @author Matheus Odilon - accentialbrasil
 * 
 */
public class CategoriesService extends IntentService {

	public static final String PARAM_CATEGORIES_LIST = "categories";

	public CategoriesService() {
		super("CategoriesService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {

		Log.e("", "SERVICE CHAMADO");
		List<CompanyCategory> categories = CompanyCategoryBO
				.getAllCompaniesCategory();

		Intent broadcastIntent = new Intent();
		broadcastIntent
				.setAction(CategoriesResponseReceiver.ACTION_RESP_CATEGORIES);
		broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
		Bundle bundle = new Bundle();
		bundle.putSerializable(PARAM_CATEGORIES_LIST, (Serializable) categories);
		broadcastIntent.putExtras(bundle);
		sendBroadcast(broadcastIntent);
	}
}
