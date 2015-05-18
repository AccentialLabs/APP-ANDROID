package com.example.slide;

import java.util.List;

import org.w3c.dom.Text;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.accential.trueone.adapter.CategoriesAdapter;
import com.accential.trueone.bean.CompanyCategory;
import com.accential.trueone.bo.CompanyCategoryBO;
import com.accential.trueone.service.CategoriesService;
import com.accential.trueone.service.SignaturesService;
import com.accential.trueone.utils.OrientacaoUtils;
import com.example.slide.SignaturesActivity.SignaturesResponseReceiver;

@SuppressWarnings("all")
public class CategoriesActivity extends Activity {

	private ListView lvCategories;
	private ProgressBar pbCategories;
	private List<CompanyCategory> categories;
	private CategoriesAdapter adapter;
	private CategoriesResponseReceiver receiver;
	private TextView tvTitle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.x_activity_categories);

		// verificamos se aplicacao esta sendo usada em um smartphone ou tablet
		String typeUser = getResources().getString(R.string.isTablet);
		if (typeUser.equals("false")) {
			Log.e("tstando list de busca",
					"Estamos abrindo o app de um smartphe");
			OrientacaoUtils.setOrientationVertical(this);
		} else {
			Log.e("tstando lista de busca",
					"Estamos abrindo o app de um tablet");
			OrientacaoUtils.setOrientationHorizontal(this);
		}

		lvCategories = (ListView) findViewById(R.id.lvCategories);
		pbCategories = (ProgressBar) findViewById(R.id.pbCategories);
		tvTitle = (TextView) findViewById(R.id.tvTitleCategories);

		Typeface font = Typeface.createFromAsset(getAssets(),
				"helvetica-35-thin-1361522141.ttf");
		tvTitle.setTypeface(font);

		// start service de busca dos comentarios
		Intent intent = new Intent(this, CategoriesService.class);
		this.startService(intent);

		// preparando o receiver para o retorno do service
		IntentFilter filter = new IntentFilter(
				CategoriesResponseReceiver.ACTION_RESP_CATEGORIES);
		filter.addCategory(Intent.CATEGORY_DEFAULT);
		receiver = new CategoriesResponseReceiver();
		this.registerReceiver(receiver, filter);

		// menu
		ImageView imgMenuCompras = (ImageView) findViewById(R.id.imageView3footer);
		ImageView imgMenuSign = (ImageView) findViewById(R.id.imageView4);
		ImageView imgMenuWish = (ImageView) findViewById(R.id.imageView5);
		ImageView imgMenuHome = (ImageView) findViewById(R.id.imageView1footer);

		// clique em compras
		imgMenuCompras.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(CategoriesActivity.this,
						ComprasActivity.class);
				startActivity(intent);
			}
		});

		// clique em assinaturas
		imgMenuSign.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(CategoriesActivity.this,
						SignaturesActivity.class);
				startActivity(intent);
			}
		});

		// clique em wishlist
		imgMenuWish.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(CategoriesActivity.this,
						WishHomeActivity.class);
				startActivity(intent);
			}
		});

		// clique em home
		imgMenuHome.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(CategoriesActivity.this,
						AdventaActivity.class);
				startActivity(intent);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public class CategoriesResponseReceiver extends BroadcastReceiver {
		public static final String ACTION_RESP_CATEGORIES = "com.mamlambo.intent.action.MESSAGE_PROCESSED_CATEGORIES";

		@Override
		public void onReceive(Context context, Intent intent) {

			// recurando lista de assisnaturas capturada no service
			Bundle parameters = intent.getExtras();
			categories = (List<CompanyCategory>) parameters
					.getSerializable(CategoriesService.PARAM_CATEGORIES_LIST);

			// esconde o progress e mostra lista
			pbCategories.setVisibility(View.GONE);
			adapter = new CategoriesAdapter(categories, CategoriesActivity.this);
			lvCategories.setAdapter(adapter);

		}
	}

}
