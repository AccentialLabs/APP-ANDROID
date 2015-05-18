package com.accential.trueone.service;

import java.io.Serializable;
import java.util.List;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;

import com.accential.trueone.bean.Company;
import com.accential.trueone.bo.CompanyBO;
import com.example.slide.SignaturesActivity.CompanySearchResponseReceiver;

public class SignaturesSearchService extends IntentService {
	public static final String PARAM_COMP_NAME = "compName";
	public static final String PARAM_COMPANIES_LIST = "companies";

	public SignaturesSearchService() {
		super("SignaturesSearchService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		String compName = intent.getStringExtra(PARAM_COMP_NAME);

		List<Company> companies = CompanyBO.searchByFancyName(compName);

		Intent broadcastIntent = new Intent();
		broadcastIntent
				.setAction(CompanySearchResponseReceiver.ACTION_RESP_COMPANY_SEARCH);
		broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
		Bundle bundle = new Bundle();
		bundle.putSerializable(PARAM_COMPANIES_LIST, (Serializable) companies);
		broadcastIntent.putExtras(bundle);
		sendBroadcast(broadcastIntent);
	}
}
