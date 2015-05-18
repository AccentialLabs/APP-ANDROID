package com.example.slide;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PointF;
import android.graphics.RectF;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.accential.trueone.utils.DownloadImageTask;
import com.accential.trueone.utils.OrientacaoUtils;
import com.example.slide.TouchImageView.OnTouchImageViewListener;
import com.loopj.android.image.SmartImageView;

public class PhotoZoomActivity extends Activity {

	private TouchImageView image;
	private Button btnVoltar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.x_activity_photo_zoom);

		// verificamos se aplicacao esta sendo usada em um smartphone ou tablet
		String type = getResources().getString(R.string.isTablet);
		if (type.equals("false")) {
			Log.e("tstando list de busca",
					"Estamos abrindo o app de um smartphe");
			OrientacaoUtils.setOrientationVertical(this);
		} else {
			Log.e("tstando lista de busca",
					"Estamos abrindo o app de um tablet");
			OrientacaoUtils.setOrientationHorizontal(this);
		}

		Intent intent = getIntent();
		String urlImage = intent.getStringExtra("urlDaPhoto");

		image = (TouchImageView) findViewById(R.id.img);
		btnVoltar = (Button) findViewById(R.id.btnVoltar);

		new DownloadImageTask(image).execute(urlImage);

		image.setOnTouchImageViewListener(new OnTouchImageViewListener() {

			@Override
			public void onMove() {
				PointF point = image.getScrollPosition();
				RectF rect = image.getZoomedRect();
				float currentZoom = image.getCurrentZoom();
				boolean isZoomed = image.isZoomed();
			}
		});

		// clique no botao voltar
		btnVoltar.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				onBackPressed();
			}
		});
	}
}
