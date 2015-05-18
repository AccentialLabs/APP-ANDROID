package com.example.slide;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.accential.trueone.adapter.MetricAdapter;
import com.accential.trueone.bean.Offer;
import com.accential.trueone.utils.SessionControl;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

/**
 * responsavel por mostrar as tabelas para a escolha das metricas do produto a
 * ser comprado e retornar os valores selecionados
 * 
 * O retorno nunca será menor do que 1 (UM). A não ser que a requisicao seja
 * cancelada
 * 
 * @author Matheus Odilon - accentialbrasil
 * 
 */
public class OfferDetailMetricActivity extends Activity {

	private Button btnConfirmar;
	private Offer offer;
	private SharedPreferences loggedUser;

	private GridView gridFirst;
	private GridView gridSecond;
	private MetricAdapter adapterFirst;
	private MetricAdapter adapterSecond;

	private TextView tvTitleFirst;
	private TextView tvTitleSecond;
	private List<String> metricsFirst;
	private List<String> metricsSecond;

	private String firstMetricValue;
	private String secondMetricValue;
	private Map<String, List<String>> metrics;
	private View secondTitleBack;
	private int selected = -1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.x_activity_offerdetails_metrics);

		btnConfirmar = (Button) findViewById(R.id.btnConfirmar);
		loggedUser = this.getSharedPreferences(SessionControl.USER_LOGGED, 0);
		gridFirst = (GridView) findViewById(R.id.firstGrid);
		gridSecond = (GridView) findViewById(R.id.secondGrid);
		tvTitleFirst = (TextView) findViewById(R.id.tvMetricsTitleSuper);
		tvTitleSecond = (TextView) findViewById(R.id.tvMetricsTitleInfer);
		secondTitleBack = findViewById(R.id.view2);

		offer = SessionControl.decodeSessionOffer(loggedUser.getString(
				SessionControl.OFFER_DETAIL, null));

		// metrics
		metrics = decodeMetricsWithTwoParams();

		List<String> keys = new ArrayList<String>();
		for (String key : metrics.keySet()) {
			keys.add(key);
		}

		metricsFirst = metrics.get(keys.get(0));
		adapterFirst = new MetricAdapter(this, metricsFirst);
		gridFirst.setAdapter(adapterFirst);
		tvTitleFirst.setText("Selecione a/o " + keys.get(0));
		if (keys.size() > 1) {
			tvTitleSecond.setText("Selecione a/o " + keys.get(1));
			metricsSecond = metrics.get(keys.get(1));
			adapterSecond = new MetricAdapter(this, metricsSecond);
			gridSecond.setAdapter(adapterSecond);

		} else {
			tvTitleSecond.setVisibility(View.INVISIBLE);
			secondTitleBack.setVisibility(View.INVISIBLE);
			gridSecond.setVisibility(View.INVISIBLE);
		}

		// clique no botao
		btnConfirmar.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				SharedPreferences.Editor editor = loggedUser.edit();

				editor.putString(SessionControl.OFFER_METRIC_VALUE_FIRST,
						firstMetricValue);
				editor.putString(SessionControl.OFFER_METRIC_VALUE_SECOND,
						secondMetricValue);
				editor.commit();

				Intent intent = new Intent(OfferDetailMetricActivity.this,
						OffersDetailActivity.class);
				startActivity(intent);
			}
		});

		// clique em metricas
		// primeiro gride
		gridFirst.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int location, long arg3) {
				firstMetricValue = metricsFirst.get(location);
				Log.e("", "USUARIO CLICOU EM : " + metricsFirst.get(location));
				selected = location;
				adapterFirst.select(location);
			}
		});

		// segundo grid
		gridSecond.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int location, long arg3) {
				secondMetricValue = metricsSecond.get(location);
				Log.e("", "USUARIO CLICOU EM: " + metricsSecond.get(location));
				selected = location;
				adapterSecond.select(location);
			}
		});
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
