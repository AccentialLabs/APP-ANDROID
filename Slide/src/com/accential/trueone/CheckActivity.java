package com.accential.trueone;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.accential.trueone.bean.AditionalAddressesUser;
import com.accential.trueone.bean.Checkout;
import com.accential.trueone.bean.Company;
import com.accential.trueone.bean.Offer;
import com.accential.trueone.bean.User;
import com.accential.trueone.bo.AditionalAddressesUserBO;
import com.accential.trueone.bo.CheckoutBO;
import com.accential.trueone.bo.UserBO;
import com.accential.trueone.service.CompaniesInvitationsService;
import com.accential.trueone.service.OfferNewsIntentService;
import com.accential.trueone.utils.Mask;
import com.accential.trueone.utils.MenuUtil;
import com.accential.trueone.utils.OrientacaoUtils;
import com.example.slide.R;
import com.loopj.android.image.SmartImageView;

public class CheckActivity extends Activity {

	// VIEW DE ENDERECO
	private EditText cep;
	private EditText endereco;
	private EditText numero;
	private EditText complemento;
	private EditText bairro;
	private EditText cidade;
	private EditText estado;
	private EditText nome;
	// private Spinner spinnerEstado;
	private Button btnAddAddress;
	private Button btnRemoveAddress;
	// QUANTIDADE
	private EditText edtQtd;
	private ImageView imgAdd;
	private ImageView imgRetira;
	private int contador;
	// VALORES - TOTAL, UNITARIO, FRETE
	private TextView shippingValue;
	private TextView unitValue;
	private TextView totalValue;
	private float total;
	private float valorTotal;
	// OFFER
	private SmartImageView offerImg;
	private TextView offerTitle;
	// USUARIO
	private TextView userName;
	// OBJS
	private Company company;
	private Checkout checkout;
	private DecimalFormat df;
	private User userWithAddres;
	private Offer offer;
	private AditionalAddressesUser userAddress;
	private Intent intentSend;
	private int prazoEntrega;
	private float frete;
	private int device;
	// WEBVIEW
	private WebView webPayment;
	// MENU RODAPE
	private ImageView imageMenuWish;
	private TextView textMenuWish;
	private ImageView imageMenuCompras;
	private TextView textMenuCompras;
	private ImageView imageMenuAssinaturas;
	private TextView textMenuAssinaturas;
	private ImageView imageMenuDashboard;
	private TextView textMenuDashboard;
	private TextView textMenuOferta;
	private ImageView imageMenuOferta;
	// USER MENU
	private Spinner spinnerMenu;
	private ImageView newsImg;
	private Map<String, Object> newsMap;
	private TextView qtdAvisoNovidade;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_check);

		// DETALHE DESSE CODIGO NO LoginActivity
		String type = getResources().getString(R.string.isTablet);
		if (type.equals("false")) {
			Log.i("TESTE", "IS TABLET= " + type);
			OrientacaoUtils.setOrientationVertical(this);
		}

		// FORMATAR FLOAT EM DUAS CASAS DECIMAIS
		df = new DecimalFormat("#.00");

		// ITENS DO MENU RODAPE
		imageMenuWish = (ImageView) findViewById(R.id.imageFooterWish);
		textMenuWish = (TextView) findViewById(R.id.textViewFooterWish);
		imageMenuCompras = (ImageView) findViewById(R.id.imageFooterCompras);
		textMenuCompras = (TextView) findViewById(R.id.textViewFooterCompras);
		imageMenuAssinaturas = (ImageView) findViewById(R.id.imageFooterAssinaturas);
		textMenuAssinaturas = (TextView) findViewById(R.id.textViewFooterAssinaturas);
		imageMenuDashboard = (ImageView) findViewById(R.id.imageFooterInvitations);
		textMenuDashboard = (TextView) findViewById(R.id.textViewFooterInvitations);
		imageMenuOferta = (ImageView) findViewById(R.id.imageFooterDashboard);
		textMenuOferta = (TextView) findViewById(R.id.textViewFooterDash);
		newsImg = (ImageView) findViewById(R.id.imageView_avisoConvite);

		// QUANTIDADE
		edtQtd = (EditText) findViewById(R.id.editText_qtd);
		imgAdd = (ImageView) findViewById(R.id.btn_addQtd);
		imgRetira = (ImageView) findViewById(R.id.btn_retiraQtd);

		// ENDERECO
		cep = (EditText) findViewById(R.id.end_cep);
		endereco = (EditText) findViewById(R.id.end_endereco);
		numero = (EditText) findViewById(R.id.end_numero);
		complemento = (EditText) findViewById(R.id.end_complemento);
		bairro = (EditText) findViewById(R.id.end_bairro);
		cidade = (EditText) findViewById(R.id.end_cidade);
		estado = (EditText) findViewById(R.id.end_estado);
		nome = (EditText) findViewById(R.id.end_nome);
		// spinnerEstado = (Spinner) findViewById(R.id.spinner_testeEstado);
		btnAddAddress = (Button) findViewById(R.id.button_addAddress);
		btnRemoveAddress = (Button) findViewById(R.id.button_newAddress);

		// VALORES
		shippingValue = (TextView) findViewById(R.id.textView_valorFrete);
		unitValue = (TextView) findViewById(R.id.offer_valorUnit);
		totalValue = (TextView) findViewById(R.id.textView_valorTotal);

		// OFFER
		offerImg = (SmartImageView) findViewById(R.id.imageView_imgOffer);
		offerTitle = (TextView) findViewById(R.id.textView_offerTitle);

		// WEBVIEW
		webPayment = (WebView) findViewById(R.id.webView_webPayment);

		// USER
		userName = (TextView) findViewById(R.id.textViewHeader_NomeUsuario);

		// MENU
		spinnerMenu = (Spinner) findViewById(R.id.spinner_header);

		qtdAvisoNovidade = (TextView) findViewById(R.id.textView_qtdConvites);

		List<String> opcoes = MenuUtil.getMenuList();

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				R.drawable.spinner_item, opcoes);

		spinnerMenu.setAdapter(adapter);
		spinnerMenu
				.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> parent,
							View view, int position, long id) {
						/**
						 * Clique em "Sair"
						 */
						if (parent.getItemAtPosition(position).toString()
								.equals("Sair")) {
							MenuUtil.getOut(view.getContext());
						}
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
					}
				});

		// DEVICE - SMARTPHONE OU TABLET
		device = TelephonyManager.PHONE_TYPE_NONE;

		textMenuOferta.setTextColor(Color.rgb(255, 117, 24));
		imageMenuOferta.setImageResource(R.drawable.ofertas_on);

		Intent intent = getIntent();
		Bundle parameter = intent.getExtras();

		newsMap = (Map<String, Object>) parameter
				.getSerializable(OfferNewsIntentService.PARAM_OUT_MAP_NEWS);
		qtdAvisoNovidade.setText(String.valueOf(newsMap
				.get(OfferNewsIntentService.MAP_TOTAL_QTD)));

		offer = (Offer) getIntent().getSerializableExtra("offer");
		final User user = (User) getIntent().getSerializableExtra("user");
		company = (Company) getIntent().getSerializableExtra("company");
		// CHECKOUT
		checkout = new Checkout();

		List<Checkout> checks = CheckoutBO.listAllCheckoutsByUser(user.getId());
		int checkoutId = checks.get(checks.size() - 1).getId();
		checkout.setId(checkoutId);

		Log.i("teste", "ID DO CHECKOUT: " + String.valueOf(checkoutId));

		userWithAddres = UserBO.searchById(user.getId());

		userName.setText(user.getName());

		// ATRIBUNDO VALOR �� VIEWS DE OFFER
		offerTitle.setText(offer.getTitle());
		offerImg.setImageUrl(offer.getPhoto());

		// CALCULO - VALOR COM DESCONTO
		total = 0;
		int percentage = offer.getPercentageDiscount();
		if (percentage != 0) {
			float value = offer.getValue();
			float desconto = (value * percentage) / 100;
			total = value - desconto;
		} else {
			total = offer.getValue();
		}

		unitValue.setText("R$ "
				+ String.valueOf(df.format(total)).replace(".", ","));

		contador = 1;
		edtQtd.setText(String.valueOf(contador));
		calcShippingValue();

		// CHECKOUT RECEBE 1 DE QUANTIDADE DE INICIO
		checkout.setAmount(contador);

		// ADICIONA - QUANTIDADE
		imgAdd.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				contador = contador + 1;
				edtQtd.setText(String.valueOf(contador));
				calcShippingValue();
				checkout.setTotalValue(valorTotal);

				// CHECKOUT RECEBE QUANTIDADE DE ACORDO COM AUMENTO
				checkout.setAmount(contador);
				makePayment();
			}
		});
		// RETIRA - QUANTIDADE
		imgRetira.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (contador > 1) {
					contador = contador - 1;
					edtQtd.setText(String.valueOf(contador));
					calcShippingValue();
					checkout.setTotalValue(valorTotal);

					// CHECKOUT RECEBE QUANTIDADE DE ACORDO COM DIMINUICAO
					checkout.setAmount(contador);
					makePayment();
				}
			}
		});

		// MASCARAS
		cep.addTextChangedListener(Mask.insert("#####-###", cep));

		// SALVA ENDERECO
		btnAddAddress.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				saveAddress();
			}
		});

		// LIMPA ENDERECO
		btnRemoveAddress.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				cep.setText("");
				endereco.setText("");
				numero.setText("");
				complemento.setText("");
				bairro.setText("");
				cidade.setText("");
				estado.setText("");
				nome.setText("");
			}
		});

		AditionalAddressesUser address = AditionalAddressesUserBO
				.searchAddressByUserId(285);
		userAddress = new AditionalAddressesUser();

		if (userWithAddres.getAddress() != null) {
			if (userWithAddres.getZip_code() != null) {
				cep.setText(userWithAddres.getZip_code());
			}
			if (userWithAddres.getAddress() != null) {
				endereco.setText(userWithAddres.getAddress());
			}
			if (userWithAddres.getNumber() != null) {
				numero.setText(userWithAddres.getNumber());
			}
			if (userWithAddres.getComplement() != null) {
				complemento.setText(userWithAddres.getComplement());
			}
			if (userWithAddres.getDistrict() != null) {
				bairro.setText(userWithAddres.getDistrict());
			}
			if (userWithAddres.getCity() != null) {
				cidade.setText(userWithAddres.getCity());
			}
			if (userWithAddres.getState() != null) {
				estado.setText(userWithAddres.getState());
			}
			nome.setText(userWithAddres.getAddress());

			userAddress.setAddress(userWithAddres.getAddress());
			userAddress.setZipCode(userWithAddres.getZip_code());
			userAddress.setNumber(userWithAddres.getNumber());
			userAddress.setComplement(userWithAddres.getComplement());
			userAddress.setDistrict(userWithAddres.getDistrict());
			userAddress.setCity(userWithAddres.getCity());
			userAddress.setState(userWithAddres.getState());
			// userAddress.setLabel(userWithAddres.getAddress());

		} else if (address.getAddress() != null) {
			if (address.getZipCode() != null) {
				cep.setText(address.getZipCode());
			}
			if (address.getAddress() != null) {
				endereco.setText(address.getAddress());
			}
			if (address.getNumber() != null) {
				numero.setText(address.getNumber());
			}
			if (address.getComplement() != null) {
				complemento.setText(address.getComplement());
			}
			if (address.getDistrict() != null) {
				bairro.setText(address.getDistrict());
			}
			if (address.getCity() != null) {
				cidade.setText(address.getCity());
			}
			if (address.getState() != null) {
				estado.setText(address.getState());
			}
			if (address.getLabel() != null) {
				nome.setText(address.getLabel());
			}

			userAddress.setAddress(address.getAddress());
			userAddress.setZipCode(address.getZipCode());
			userAddress.setNumber(address.getNumber());
			userAddress.setComplement(address.getComplement());
			userAddress.setDistrict(address.getDistrict());
			userAddress.setCity(address.getCity());
			userAddress.setState(address.getState());
			userAddress.setLabel(address.getLabel());
		}

		if (userAddress.getAddress() == null) {
			Log.i("teste do userAdreess", "O ENDERECO EH NULO");
			Log.i("TESTE DO ADDRESS", userWithAddres.getAddress());
		}

		makePayment();

		// RASTREAMENTO DE URL DA WEBVIEW
		webPayment.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				if (url.startsWith("trueone:")) {

					// CANCELAMENTO - VOLTA PARA TELA ANTERIOR
					if (url.endsWith("didCancelPayment")) {
						Log.i("teste requisicao da url",
								"a url termina com DIDCANCELPAYMENT");
						intentSend = new Intent(CheckActivity.this,
								OfferDetail.class);
						intentSend.putExtra("offer", offer);
						intentSend.putExtra("user", user);
						Bundle parameters = new Bundle();
						Log.i("COMPANY ID", String.valueOf(company.getId()));
						parameters.putInt("companyId", company.getId());
						parameters.putSerializable(
								OfferNewsIntentService.PARAM_OUT_MAP_NEWS,
								(Serializable) newsMap);
						intentSend.putExtras(parameters);
						startActivity(intentSend);

						// SUCESSO - MOSTRA TELA DE SUCESSO
					} else if (url.endsWith("moipPaymentSuccess")) {
						Log.i("teste requisicao da url",
								"a url termina com MOIPPAYMENTSUCCESS");
						intentSend = new Intent(CheckActivity.this,
								BuySucessActivity.class);
						Bundle bundle = new Bundle();
						bundle.putSerializable(
								OfferNewsIntentService.PARAM_OUT_MAP_NEWS,
								(Serializable) newsMap);
						intentSend.putExtras(bundle);
						intentSend.putExtra("user", user);
						startActivity(intentSend);

						// ERROR - MOSTRA TELA DE ERRO
					} else if (url.endsWith("moipError")) {

						intentSend = new Intent(CheckActivity.this,
								BuyErrorActivity.class);
						intentSend.putExtra("user", user);
						intentSend.putExtra("offer", offer);
						intentSend.putExtra("company", company);
						Bundle bundle = new Bundle();
						bundle.putSerializable(
								OfferNewsIntentService.PARAM_OUT_MAP_NEWS,
								(Serializable) newsMap);
						intentSend.putExtras(bundle);
						startActivity(intentSend);

						Log.i("teste requisicao da url",
								"a url termina com MOIPERROR");
					}
				}
				return super.shouldOverrideUrlLoading(view, url);
			}
		});

		// 1 - WISHLIST
		textMenuWish.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// AO SELECIONAR A OP����O WISHLIST NO MENU A ACTIVITY CARREGA O
				// OBJ USUARIO E O ATRIBIUTO 'LOGADO'
				// PARA TRANSFERIR VIA INTENT
				imageMenuWish.setImageResource(R.drawable.wishlist_on);
				imageMenuAssinaturas
						.setImageResource(R.drawable.assinaturas_off);
				imageMenuCompras.setImageResource(R.drawable.compras_off);
				imageMenuDashboard.setImageResource(R.drawable.dashboard_off);

				textMenuWish.setTextColor(Color.rgb(255, 117, 24));
				textMenuCompras.setTextColor(Color.rgb(105, 105, 105));
				textMenuDashboard.setTextColor(Color.rgb(105, 105, 105));
				textMenuAssinaturas.setTextColor(Color.rgb(105, 105, 105));
				// MUDEI AQUI - WishlistActivity
				Intent intent = new Intent(v.getContext(), WishlistLand.class);
				Bundle parameters = new Bundle();
				parameters.putSerializable(
						CompaniesInvitationsService.PARAM_OUT_INVITATIONS,
						(Serializable) newsMap);
				intent.putExtra("user", user);
				parameters.putBoolean("logado", true);
				Log.i("nome do user", user.getName());
				intent.putExtras(parameters);
				startActivity(intent);
			}
		});

		// SELECIONA ITENS DO MENU
		// 2 - COMPRAS
		textMenuCompras.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// AO SELECIONAR A OP����O COMPRAS DO MENU A ACTIVITY CARREGA O
				// OBJ USUARIO
				// PARA TRANSFERIR VIA INTENT
				imageMenuWish.setImageResource(R.drawable.wishlist_off);
				imageMenuAssinaturas
						.setImageResource(R.drawable.assinaturas_off);
				imageMenuCompras.setImageResource(R.drawable.compras_on);
				imageMenuDashboard.setImageResource(R.drawable.dashboard_off);

				textMenuCompras.setTextColor(Color.rgb(255, 117, 24));
				textMenuWish.setTextColor(Color.rgb(105, 105, 105));
				textMenuDashboard.setTextColor(Color.rgb(105, 105, 105));
				textMenuAssinaturas.setTextColor(Color.rgb(105, 105, 105));

				Intent intent = new Intent(v.getContext(),
						ComprasActivity.class);
				Bundle parameters = new Bundle();
				parameters.putSerializable(
						CompaniesInvitationsService.PARAM_OUT_INVITATIONS,
						(Serializable) newsMap);
				intent.putExtras(parameters);
				intent.putExtra("user", user);
				startActivity(intent);
			}
		});

		// SELECIONA ITENS DO MENU
		// 3 - ASSINATURAS
		textMenuAssinaturas.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				imageMenuWish.setImageResource(R.drawable.wishlist_off);
				imageMenuAssinaturas
						.setImageResource(R.drawable.assinaturas_on);
				imageMenuCompras.setImageResource(R.drawable.compras_off);
				imageMenuDashboard.setImageResource(R.drawable.dashboard_off);

				textMenuAssinaturas.setTextColor(Color.rgb(255, 117, 24));
				textMenuWish.setTextColor(Color.rgb(105, 105, 105));
				textMenuDashboard.setTextColor(Color.rgb(105, 105, 105));
				textMenuCompras.setTextColor(Color.rgb(105, 105, 105));
				// MUDEI AQUI - CompaniesUserActivity

				Intent intentC = new Intent(v.getContext(),
						SignaturesActivity.class);
				Bundle parameters = new Bundle();
				parameters.putSerializable(
						CompaniesInvitationsService.PARAM_OUT_INVITATIONS,
						(Serializable) newsMap);
				intentC.putExtras(parameters);
				intentC.putExtra("user", user);
				startActivity(intentC);
			}
		});

		newsImg.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intentNews = new Intent(CheckActivity.this,
						NewsActivity.class);
				intentNews.putExtra("user", user);
				Bundle bundle = new Bundle();
				bundle.putSerializable(
						OfferNewsIntentService.PARAM_OUT_MAP_NEWS,
						(Serializable) newsMap);
				intentNews.putExtras(bundle);
				startActivity(intentNews);
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void saveAddress() {

		if (!cep.getText().toString().equals("")
				&& !endereco.getText().toString().equals("")
				&& !numero.getText().toString().equals("")
				&& !bairro.getText().toString().equals("")
				&& !cidade.getText().toString().equals("")
				&& !estado.getText().toString().equals("")
				&& !estado.getText().toString().equals("")) {

			User user = new User();
			user.setId(285);

			AditionalAddressesUser address = new AditionalAddressesUser();
			address.setUser(user);
			address.setZipCode(cep.getText().toString());
			address.setAddress(endereco.getText().toString());
			address.setNumber(numero.getText().toString());
			address.setComplement(complemento.getText().toString());
			address.setDistrict(bairro.getText().toString());
			address.setCity(cidade.getText().toString());
			address.setState(estado.getText().toString());
			address.setLabel(nome.getText().toString());

			AditionalAddressesUserBO bo = new AditionalAddressesUserBO();
			bo.saveAddress(address);
			Toast.makeText(CheckActivity.this, "Local salvo!",
					Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(CheckActivity.this, "Preencha todos os campos",
					Toast.LENGTH_SHORT).show();
		}
	}

	public void calcShippingValue() {

		Map<String, String> myMap = new HashMap<String, String>();

		myMap = CheckoutBO.calculateShippingValue("08540500",
				company.getZip_code(), Integer.toString(contador));

		prazoEntrega = Integer.parseInt(myMap.get("PrazoEntrega"));
		Log.i("PRAZO DE ENTREGA", String.valueOf(prazoEntrega));
		frete = Float.parseFloat(myMap.get("Valor").replace("\"", "")
				.replace(",", "."));
		shippingValue.setText("Frete: R$ " + myMap.get("Valor"));
		String valor = myMap.get("Valor").replace(",", ".");
		valorTotal = (total * contador) + Float.parseFloat(valor);
		totalValue.setText("Total: R$ "
				+ String.valueOf(df.format(valorTotal)).replace(".", ","));

		// ATRIBUINDO VALOR A CHECKOUT
		checkout.setTotalValue(valorTotal);
		Log.i("VALOR DO CHECKOUT", String.valueOf(checkout.getTotalValue()));
	}

	/**
	 * Carrega as informa����es para intera����o com MoIP Retorno o token
	 * tratado pela API, que nos retorna uma URL
	 */
	public void makePayment() {

		checkout.setUnitValue(total);
		checkout.setShippingValue(frete);
		checkout.setTotalValue(valorTotal);
		checkout.setDeliveryTime(prazoEntrega);

		if (userAddress.getAddress() != null) {
			CheckoutBO.updateUserAddress(userAddress, checkout);
		}

		String url = CheckoutBO.makePayment(userWithAddres, checkout, offer,
				company, userAddress);

		webPayment.loadUrl(url);
		WebSettings webSettings = webPayment.getSettings();
		webSettings.setJavaScriptEnabled(true);
	}

}
