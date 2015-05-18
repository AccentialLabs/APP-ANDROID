package com.example.slide;

import com.accential.trueone.utils.OrientacaoUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.Toast;

public class CheckoutFinalizeActivity extends Activity {

	private WebView myWeb;
	private String url;
	private ImageView userPhoto;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.x_checkout_finalize);

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

		myWeb = (WebView) findViewById(R.id.wvPayment);
		userPhoto = (ImageView) findViewById(R.id.imageView2);

		// clique na foto do usuario - Link para "MEUS DADOS"
		userPhoto.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(CheckoutFinalizeActivity.this,
						UserActivity.class);
				startActivity(intent);
			}
		});

		Intent intent = getIntent();
		url = intent.getStringExtra("url");

		WebSettings webSettings = myWeb.getSettings();
		webSettings.setJavaScriptEnabled(true);
		myWeb.setHorizontalScrollBarEnabled(false);
		myWeb.loadUrl(url);

		myWeb.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				if (url.startsWith("trueone:")) {

					// Clique no botao cancelar compras
					if (url.endsWith("didCancelPayment")) {
						myWeb.setVisibility(View.GONE);
						// Sucesso na compra
					} else if (url.endsWith("moipPaymentSuccess")) {

						String sucesso = "<strong>Compra realizada com sucesso!!!</strong>"
								+ "Vá em <font style=\"color: orange;\">Minhas Compras</font> e veja os detalhes.";
						myWeb.loadData(sucesso, "text/html", null);
						// erro no processamento
					} else if (url.endsWith("moipError")) {

						Toast.makeText(
								CheckoutFinalizeActivity.this,
								"Ocorreu um erro durante o processamento do pagamento. Reveja os dados e tente novamente.",
								Toast.LENGTH_LONG).show();

						// recarrega webView
						myWeb.loadUrl(url);

					}
				}
				return super.shouldOverrideUrlLoading(view, url);
			}
		});
	}
}
