package com.example.slide;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.kxml2.wap.wv.WV;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.accential.trueone.BuyErrorActivity;
import com.accential.trueone.BuySucessActivity;
import com.accential.trueone.CheckActivity;
import com.accential.trueone.OfferDetail;
import com.accential.trueone.adapter.CheckoutAddressAdapter;
import com.accential.trueone.bean.AditionalAddressesUser;
import com.accential.trueone.bean.Checkout;
import com.accential.trueone.bean.Company;
import com.accential.trueone.bean.CompanyPreference;
import com.accential.trueone.bean.Offer;
import com.accential.trueone.bean.User;
import com.accential.trueone.bo.AditionalAddressesUserBO;
import com.accential.trueone.bo.CheckoutBO;
import com.accential.trueone.service.CheckoutService;
import com.accential.trueone.service.OfferNewsIntentService;
import com.accential.trueone.service.ShippingValueService;
import com.accential.trueone.utils.Mask;
import com.accential.trueone.utils.OrientacaoUtils;
import com.accential.trueone.utils.SessionControl;
import com.loopj.android.image.SmartImageView;

@SuppressLint("NewApi")
@SuppressWarnings("all")
/**
 * Realizar compra
 * 
 * @author Matheus Odilon - accentialbrasil
 * 
 */
public class CheckoutActivity extends Activity {

	// oferta que será comprada
	private Company company;
	private CompanyPreference preference;
	private Offer offer;
	private AditionalAddressesUser address;
	// usuario logado e todas demais infos salvas na "Sessao"
	private SharedPreferences loggedUser;
	private Button btnQtdMore;
	private Button btnQtdLess;
	private Button btnAddAddress;
	private Button btnCardPayment;
	private Button btnChooseAddress;
	private User user;
	private TextView tvOfferTitle;
	private TextView tvOfferUnitVal;
	private TextView tvFrete;
	private TextView tvTotalVal;
	private TextView tvPrazoEntrega;
	private SmartImageView sivOfferPhoto;
	private EditText etQtd;
	private Spinner spAddresses;
	private ProgressBar pbCheck;
	private List<AditionalAddressesUser> addresses;
	private Map frete;
	private String destino;
	private ShippingValueResponseReceiver shippingReceiver;
	private CheckoutResponseReceiver checkReceiver;
	private int qtdOffer;
	private Checkout checkout;
	private View vBlock;
	private View vHideMetric;
	private float totalCheck;
	private String cepOrigem;
	private WebView myWeb;

	private TextView codProduto;
	private TextView enderecoFirstLine;
	private TextView enderecoSecondLine;
	private TextView tvLabelAddress;

	private TextView tvFirstMetric;
	private TextView tvSecondMetric;
	private String firstMetricValue;
	private String secondMetricValue;
	private ImageView userPhoto;

	// escolher endereço
	private CheckoutAddressAdapter chooseAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.x_checkout);

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

		address = new AditionalAddressesUser();
		btnAddAddress = (Button) findViewById(R.id.btnCheckAddAddress);
		btnCardPayment = (Button) findViewById(R.id.btnPagamentoCartao);
		btnQtdMore = (Button) findViewById(R.id.btnPlus);
		btnQtdLess = (Button) findViewById(R.id.btnLess);
		etQtd = (EditText) findViewById(R.id.etQtd);
		tvOfferTitle = (TextView) findViewById(R.id.tvCheckOfferTitle);
		tvFrete = (TextView) findViewById(R.id.tvCheckFrete);
		tvOfferUnitVal = (TextView) findViewById(R.id.tvCheckUniVal);
		tvTotalVal = (TextView) findViewById(R.id.tvCheckTotalVal);
		tvPrazoEntrega = (TextView) findViewById(R.id.tvCheckQtdEntrega);
		sivOfferPhoto = (SmartImageView) findViewById(R.id.ivCheckOfferPhoto);
		spAddresses = (Spinner) findViewById(R.id.spCheckAddresses);
		pbCheck = (ProgressBar) findViewById(R.id.pbCheck);
		vBlock = findViewById(R.id.vBlock);
		myWeb = (WebView) findViewById(R.id.wvMakePayment);
		codProduto = (TextView) findViewById(R.id.tvCheckCodDoProduto);
		btnChooseAddress = (Button) findViewById(R.id.button1);

		tvFirstMetric = (TextView) findViewById(R.id.textView9);
		tvSecondMetric = (TextView) findViewById(R.id.textView10);

		tvLabelAddress = (TextView) findViewById(R.id.tvAddLabel);
		enderecoFirstLine = (TextView) findViewById(R.id.tvCheckEndereco);
		enderecoSecondLine = (TextView) findViewById(R.id.textView8);
		// vHideMetric = (View) findViewById(R.id.vHideMetrics);

		Typeface tf = Typeface.createFromAsset(getAssets(),
				"helvetica-35-thin-1361522141.ttf");
		btnCardPayment.setTypeface(tf);
		btnQtdMore.setTypeface(tf);
		btnQtdLess.setTypeface(tf);
		tvOfferTitle.setTypeface(tf);

		userPhoto = (ImageView) findViewById(R.id.imageView2);

		// clique na foto do usuario - Link para "MEUS DADOS"
		userPhoto.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(CheckoutActivity.this,
						UserActivity.class);
				startActivity(intent);
			}
		});

		// capturando infos da sessao
		loggedUser = getSharedPreferences(SessionControl.USER_LOGGED, 0);
		// oferta relacionada
		offer = SessionControl.decodeSessionOffer(loggedUser.getString(
				SessionControl.OFFER_DETAIL, null));
		user = SessionControl.decodeSessionUser(loggedUser.getString("user",
				null));
		company = SessionControl.decodeSessionCompany(loggedUser.getString(
				SessionControl.OFFER_DETAILS_COMPANY, null));

		preference = SessionControl.decodeSessionCompanyPreference(loggedUser
				.getString(SessionControl.OFFER_DETAILS_COMPANY_PREFERENCES,
						null));

		firstMetricValue = loggedUser.getString(
				SessionControl.OFFER_METRIC_VALUE_FIRST, null);
		secondMetricValue = loggedUser.getString(
				SessionControl.OFFER_METRIC_VALUE_SECOND, null);

		if (offer.getMetrics().equals("")) {
			// vHideMetric.setVisibility(View.GONE);
		}
		// primeira metrica
		if (firstMetricValue != null) {
			if (!firstMetricValue.equals("")) {
				if (firstMetricValue.contains("#")) {
					tvFirstMetric.setText("");
					tvFirstMetric.setBackgroundColor(Color
							.parseColor(firstMetricValue));
				} else {
					tvFirstMetric.setText(firstMetricValue);
				}
			}
		}
		// segunda metrica
		if (secondMetricValue != null) {
			if (!secondMetricValue.equals("")) {
				if (secondMetricValue.contains("#")) {
					tvSecondMetric.setText("");
					tvSecondMetric.setBackgroundColor(Color
							.parseColor(secondMetricValue));
				} else {
					tvSecondMetric.setText(secondMetricValue);
				}

			}
		}

		ImageView userImg = (ImageView) findViewById(R.id.imageView2);

		// clique na imagem do usuario
		userImg.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(CheckoutActivity.this,
						UserActivity.class);
				startActivity(intent);
			}
		});

		cepOrigem = company.getZip_code().replace("-", "");

		Log.e("usuario", "usuario:" + user.getName());
		// setando dados ao obj checkout
		checkout = new Checkout();
		checkout.setOffer(offer);
		checkout.setUser(user);
		checkout.setCompany(company);
		// testando criaçao do registro de checkout

		// mostrando views de carregamento
		vBlock.setVisibility(View.VISIBLE);
		pbCheck.setVisibility(View.VISIBLE);

		// chamando service
		Log.e("Chamando service", "chamando service");

		Intent checkIntent = new Intent(this, CheckoutService.class);
		Bundle bundle = new Bundle();
		bundle.putSerializable(CheckoutService.PARAM_IN_CHECKOUT,
				(Serializable) checkout);
		checkIntent.putExtras(bundle);
		this.startService(checkIntent);

		IntentFilter filter = new IntentFilter(
				CheckoutResponseReceiver.ACTION_RESP_CREATE_CHECKOUT);
		filter.addCategory(Intent.CATEGORY_DEFAULT);
		checkReceiver = new CheckoutResponseReceiver();
		this.registerReceiver(checkReceiver, filter);

		//
		tvOfferUnitVal.setText(String.format("R$ %.2f", offer.getValue()));

		tvOfferTitle.setText(offer.getTitle());
		sivOfferPhoto.setImageUrl(offer.getPhoto());
		etQtd.setText(String.valueOf(1));
		codProduto.setTag(String.valueOf(offer.getId()));

		addresses = AditionalAddressesUserBO.listAllByUser(user.getId());
		ArrayAdapter<AditionalAddressesUser> addressAdapter = new ArrayAdapter<AditionalAddressesUser>(
				CheckoutActivity.this, android.R.layout.simple_spinner_item,
				addresses);
		spAddresses.setAdapter(addressAdapter);

		// spinner dos endereços adicionais
		Log.e("", "ESSE É O ID DO USUARIO:" + String.valueOf(user.getId()));
		Log.e("", "ENDEREÇOS DO USUARIO:" + String.valueOf(addresses.size()));

		address = addresses.get(spAddresses.getSelectedItemPosition());

		// verifica se companhia usa API dos Correios
		boolean correios = preference.isUseCorreiosApi();
		Log.e("preference", "id: " + String.valueOf(preference.getId()));
		Log.e("preference",
				"correios: " + String.valueOf(preference.isUseCorreiosApi()));
		if (correios) {
			Log.e("", "Usando API do correio");

			if (!user.getZip_code().equals("")) {
				destino = user.getZip_code();
			} else {
				destino = addresses.get(0).getZipCode();
			}
			// calcula frete
			frete = CheckoutBO.calculaFrete(cepOrigem, destino,
					String.valueOf(offer.getWeight()));

			if (!frete.get("Valor").equals("ERROR_DAO")) {
				tvFrete.setText("R$ " + frete.get("Valor"));
				tvPrazoEntrega.setText(frete.get("PrazoEntrega")
						+ " dias úteis");
			} else {
				Toast.makeText(
						CheckoutActivity.this,
						"Ops.. Ocorreu um erro no calculo do frete. Confira o endereço selecionado e continue.",
						Toast.LENGTH_LONG).show();
				tvFrete.setText("Ops...");
				tvPrazoEntrega.setText("Ops...");
			}

			float total = (Float.parseFloat(frete.get("Valor").toString()
					.replace(",", ".")) + offer.getValue());
			totalCheck = total;
			tvTotalVal.setText(String.format("R$ %.2f", total));

		} else {

			Log.e("", "NAO usando API do correio");

			tvFrete.setText(String.format("R$ %.2f",
					preference.getShippingValue()));
			tvPrazoEntrega.setText(String.valueOf(preference.getDeliveryTime())
					+ " dias úteis");
			float total = (preference.getShippingValue() + offer.getValue());
			totalCheck = total;
			tvTotalVal.setText(String.format("R$ %.2f", total));
		}

		qtdOffer = Integer.parseInt(etQtd.getText().toString());

		// ESCOLHE ENDEREÇO
		spAddresses.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int location, long arg3) {

				// Executa o calculo do frete somente se companhia estiver
				// usando a API dos correios
				if (preference.isUseCorreiosApi()) {
					AditionalAddressesUser addressSelect = (AditionalAddressesUser) parent
							.getSelectedItem();

					// verifica que se cep é válido
					if (addressSelect.getZipCode().length() == 8) {

						float peso = offer.getWeight() * qtdOffer;
						String cepDestino = addressSelect.getZipCode();

						pbCheck.setVisibility(View.VISIBLE);
						callCalculateShippingVal(cepOrigem, cepDestino,
								String.valueOf(peso));

						address = addressSelect;
						Log.e("ENDERECO",
								"ENDERECO ESCOLHIDO: " + address.getLabel());

					} else {
						Toast.makeText(CheckoutActivity.this, "Ops..",
								Toast.LENGTH_LONG).show();

						tvFrete.setText("Ops...");
						tvPrazoEntrega.setText("Ops...");
						tvTotalVal.setText("Ops...");
					}
				}

				address = addresses.get(spAddresses.getSelectedItemPosition());
			}

			@Override
			public void onNothingSelected(AdapterView<?> adapter) {
			}
		});

		// QUANTIDADE - mais
		btnQtdMore.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {

				if (preference.isUseCorreiosApi()) {

					if (offer.getAmountAllowed() == 0) {
						int qtd = Integer.parseInt(etQtd.getText().toString());
						qtd++;
						etQtd.setText(String.valueOf(qtd));
						qtdOffer = qtd;
						pbCheck.setVisibility(View.VISIBLE);
						float peso = offer.getWeight() * qtd;
						callCalculateShippingVal(cepOrigem,
								address.getZipCode(), String.valueOf(peso));

					} else {
						int qtd = Integer.parseInt(etQtd.getText().toString());
						if (qtd < offer.getAmountAllowed()) {
							qtd++;
							etQtd.setText(String.valueOf(qtd));
							qtdOffer = qtd;
							pbCheck.setVisibility(View.VISIBLE);
							float peso = offer.getWeight() * qtd;
							callCalculateShippingVal(cepOrigem,
									address.getZipCode(), String.valueOf(peso));
						} else {
							Toast.makeText(CheckoutActivity.this,
									"Essa é a quantidade maxima permitida!",
									Toast.LENGTH_SHORT).show();
						}
					}
				} else {

					if (offer.getAmountAllowed() == 0) {
						int qtd = Integer.parseInt(etQtd.getText().toString());
						qtd++;
						etQtd.setText(String.valueOf(qtd));
						float precoBruto = offer.getValue() * qtd;
						float frete = preference.getShippingValue() * qtd;
						float total = frete + precoBruto;

						totalCheck = total;
						tvFrete.setText(String.format("R$ %.2f", frete));
						tvTotalVal.setText(String.format("R$ %.2f", total));
					} else {
						int qtd = Integer.parseInt(etQtd.getText().toString());
						if (qtd < offer.getAmountAllowed()) {
							qtd++;
							etQtd.setText(String.valueOf(qtd));
							float precoBruto = offer.getValue() * qtd;
							float frete = preference.getShippingValue() * qtd;
							float total = frete + precoBruto;

							totalCheck = total;
							tvFrete.setText(String.format("R$ %.2f", frete));
							tvTotalVal.setText(String.format("R$ %.2f", total));
						} else {
							Toast.makeText(CheckoutActivity.this,
									"Essa é a quantidade maxima permitida!",
									Toast.LENGTH_SHORT).show();
						}
					}
				}
			}
		});

		// QUANTIDADE - menos
		btnQtdLess.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {

				// verifica se companhia utiliza API dos correios
				if (preference.isUseCorreiosApi()) {

					int qtd = Integer.parseInt(etQtd.getText().toString());
					if (qtd > 1) {
						qtd--;
						qtdOffer = qtd;
						etQtd.setText(String.valueOf(qtd));

						pbCheck.setVisibility(View.VISIBLE);
						float peso = offer.getWeight() * qtd;
						callCalculateShippingVal(cepOrigem,
								address.getZipCode(), String.valueOf(peso));

					} else {
						etQtd.setText(String.valueOf(1));
					}
				} else {
					int qtd = Integer.parseInt(etQtd.getText().toString());
					if (qtd > 1) {
						qtd--;
						qtdOffer = qtd;
						etQtd.setText(String.valueOf(qtd));

						float precoBruto = offer.getValue() * qtd;
						float frete = preference.getShippingValue() * qtd;
						float total = frete + precoBruto;
						totalCheck = total;
						tvFrete.setText(String.format("R$ %.2f", frete));
						tvTotalVal.setText(String.format("R$ %.2f", total));
					}

				}

			}
		});

		btnAddAddress.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {

				LayoutInflater layoutInflater = LayoutInflater
						.from(CheckoutActivity.this);

				View promptView = layoutInflater.inflate(
						R.layout.x_pop_add_address, null);

				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
						CheckoutActivity.this);

				alertDialogBuilder.setView(promptView);

				final AlertDialog alertD = alertDialogBuilder.create();

				String estado = "UF";
				if (!user.getState().equals("")) {
					estado = user.getState();
				}
				String[] estados = { estado, "AC", "AL", "AP", "AM", "BA",
						"CE", "DF", "ES", "GO", "MA", "MG", "MS", "MT", "PA",
						"PB", "PR", "PE", "PI", "RJ", "RN", "RS", "RO", "RR",
						"SC", "SP", "SE", "TO" };

				ArrayAdapter<String> osEstados = new ArrayAdapter<String>(
						CheckoutActivity.this,
						android.R.layout.simple_spinner_item, estados);

				final Spinner spnEstados = (Spinner) promptView
						.findViewById(R.id.spAddAddressUF);

				final EditText etLabel = (EditText) promptView
						.findViewById(R.id.etAddAddressLabel);
				final EditText etAddress = (EditText) promptView
						.findViewById(R.id.etAddAddress);
				final EditText etDistrict = (EditText) promptView
						.findViewById(R.id.etAddAddressDistrict);
				final EditText etCity = (EditText) promptView
						.findViewById(R.id.etAddAddressCity);
				final EditText etNumber = (EditText) promptView
						.findViewById(R.id.etAddAddressNumber);
				final EditText etCep = (EditText) promptView
						.findViewById(R.id.etAddAdressCEP);
				final EditText etComplement = (EditText) promptView
						.findViewById(R.id.etAddAddressComplement);
				final Button btnSave = (Button) promptView
						.findViewById(R.id.btnAddAddressSave);
				final Button btnCancel = (Button) promptView
						.findViewById(R.id.btnAddAddressCancel);

				etCep.addTextChangedListener(Mask.insert("#####-###", etCep));

				spnEstados.setAdapter(osEstados);

				alertD.show();

				btnSave.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						// criando obj e inserindo dados do form dentro dele
						AditionalAddressesUser address = new AditionalAddressesUser();
						address.setAddress(etAddress.getText().toString());
						address.setCity(etCity.getText().toString());
						address.setComplement(etComplement.getText().toString());
						address.setDistrict(etDistrict.getText().toString());
						address.setLabel(etLabel.getText().toString());
						address.setNumber(etNumber.getText().toString());
						address.setUser(user);
						address.setZipCode(etCep.getText().toString()
								.replace("-", ""));
						address.setState(spnEstados.getSelectedItem()
								.toString());

						AditionalAddressesUserBO.saveAddress(address);
						Toast.makeText(CheckoutActivity.this, "Salvo",
								Toast.LENGTH_SHORT).show();
						alertD.dismiss();

						addresses.add(address);
						ArrayAdapter<AditionalAddressesUser> addressAdapter = new ArrayAdapter<AditionalAddressesUser>(
								CheckoutActivity.this,
								android.R.layout.simple_spinner_item, addresses);
						spAddresses.setAdapter(addressAdapter);

					}
				});

				// cancelar
				btnCancel.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View view) {
						alertD.dismiss();
					}
				});
			}
		});

		btnCardPayment.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				makePayment();
			}
		});

		// adicionando texto as views de endereço
		if (!user.getAddress().equals("")) {
			enderecoFirstLine.setText(user.getAddress() + ", "
					+ user.getNumber() + " - " + user.getComplement() + " - "
					+ user.getCity() + " - " + user.getState());

			enderecoSecondLine.setText("CEP: " + user.getZip_code());

		}

		// clique no botao escolher endereco
		btnChooseAddress.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				LayoutInflater layoutInflater = LayoutInflater
						.from(CheckoutActivity.this);

				View promptView = layoutInflater.inflate(
						R.layout.x_checkout_choose_address, null);

				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
						CheckoutActivity.this);

				alertDialogBuilder.setView(promptView);

				final AlertDialog alertD = alertDialogBuilder.create();

				// codigo
				final Button btnVoltar = (Button) promptView
						.findViewById(R.id.btnChooseAddVoltar);
				final ListView lvAddress = (ListView) promptView
						.findViewById(R.id.lvAddress);

				chooseAdapter = new CheckoutAddressAdapter(view.getContext(),
						addresses);
				lvAddress.setAdapter(chooseAdapter);

				// clique em item da lista
				lvAddress.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> adapter, View view,
							int location, long arg3) {

						AditionalAddressesUser endereco = addresses
								.get(location);

						// Executa o calculo do frete somente se companhia
						// estiver
						// usando a API dos correios
						if (preference.isUseCorreiosApi()) {
							AditionalAddressesUser addressSelect = addresses
									.get(location);

							// verifica que se cep é válido
							if (addressSelect.getZipCode().length() == 8) {

								float peso = offer.getWeight() * qtdOffer;
								String cepDestino = addressSelect.getZipCode();

								pbCheck.setVisibility(View.VISIBLE);
								callCalculateShippingVal(cepOrigem, cepDestino,
										String.valueOf(peso));

								address = addressSelect;
								Log.e("ENDERECO", "ENDERECO ESCOLHIDO: "
										+ address.getLabel());

							} else {
								Toast.makeText(CheckoutActivity.this, "Ops..",
										Toast.LENGTH_LONG).show();

								tvFrete.setText("Ops...");
								tvPrazoEntrega.setText("Ops...");
								tvTotalVal.setText("Ops...");
							}
						}

						address = addresses.get(spAddresses
								.getSelectedItemPosition());

						selectAddress(endereco);
						alertD.cancel();
					}
				});

				// clique no botao voltar
				btnVoltar.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						alertD.cancel();
					}
				});

				alertD.show();
			}
		});
	}

	public void makePayment() {

		// btnCardPayment.setVisibility(View.GONE);
		// myWeb.setVisibility(View.VISIBLE);

		address = addresses.get(spAddresses.getSelectedItemPosition());
		checkout.setTotalValue(totalCheck);

		Log.e("Checkout",
				"Checkout valor total: "
						+ String.valueOf(checkout.getTotalValue()));
		String url = CheckoutBO.makePayment(user, checkout, offer, company,
				address);

		Intent intent = new Intent(CheckoutActivity.this,
				CheckoutFinalizeActivity.class);
		intent.putExtra("url", url);
		startActivity(intent);
		/*
		 * WebSettings webSettings = myWeb.getSettings();
		 * webSettings.setJavaScriptEnabled(true);
		 * myWeb.setHorizontalScrollBarEnabled(false); myWeb.loadUrl(url);
		 * 
		 * // controlar caminhos da webView myWeb.setWebViewClient(new
		 * WebViewClient() {
		 * 
		 * @Override public boolean shouldOverrideUrlLoading(WebView view,
		 * String url) { if (url.startsWith("trueone:")) {
		 * 
		 * // Clique no botao cancelar compras if
		 * (url.endsWith("didCancelPayment")) { myWeb.setVisibility(View.GONE);
		 * btnCardPayment.setVisibility(View.VISIBLE); // Sucesso na compra }
		 * else if (url.endsWith("moipPaymentSuccess")) {
		 * 
		 * String sucesso = "<strong>Compra realizada com sucesso!!!</strong>" +
		 * "Vá em <font style=\"color: orange;\">Minhas Compras</font> e veja os detalhes."
		 * ; myWeb.loadData(sucesso, "text/html", null); // erro no
		 * processamento } else if (url.endsWith("moipError")) {
		 * 
		 * Toast.makeText( CheckoutActivity.this,
		 * "Ocorreu um erro durante o processamento do pagamento. Reveja os dados e tente novamente."
		 * , Toast.LENGTH_LONG).show();
		 * 
		 * // recarrega webView myWeb.loadUrl(url);
		 * 
		 * } } return super.shouldOverrideUrlLoading(view, url); } });
		 */

	}

	/**
	 * Chama service de calculo de Frete e já muda as labels automaticamente
	 * 
	 * @param origem
	 * @param destino
	 * @param peso
	 */
	public void callCalculateShippingVal(String origem, String destino,
			String peso) {

		// chamando service
		Intent shippingIntent = new Intent(this, ShippingValueService.class);
		shippingIntent.putExtra(ShippingValueService.PARAM_IN_CEP_ORIGEM,
				origem);
		shippingIntent.putExtra(ShippingValueService.PARAM_IN_CEP_DESTINO,
				destino);
		shippingIntent.putExtra(ShippingValueService.PARAM_IN_PESO, peso);
		this.startService(shippingIntent);

		// preparando o receiver para o retorno do service
		IntentFilter filter = new IntentFilter(
				ShippingValueResponseReceiver.ACTION_RESP_SHIPPING_VAL);
		filter.addCategory(Intent.CATEGORY_DEFAULT);
		shippingReceiver = new ShippingValueResponseReceiver();
		this.registerReceiver(shippingReceiver, filter);

	}

	// retorno do calculo do frete
	public class ShippingValueResponseReceiver extends BroadcastReceiver {
		public static final String ACTION_RESP_SHIPPING_VAL = "com.mamlambo.intent.action.MESSAGE_PROCESSED_SHIP_VAL";

		@Override
		public void onReceive(Context context, Intent intent) {

			// recurando lista de assisnaturas capturada no service
			Bundle parameters = intent.getExtras();
			frete = (Map) parameters
					.getSerializable(ShippingValueService.PARAM_OUT_FRETE);

			pbCheck.setVisibility(View.INVISIBLE);

			// mudando valor das labels
			if (!frete.get("Valor").equals("ERROR_DAO")) {
				tvFrete.setText("R$ " + frete.get("Valor"));
				tvPrazoEntrega.setText(frete.get("PrazoEntrega")
						+ " dias úteis");
			} else {
				Toast.makeText(
						CheckoutActivity.this,
						"Ops.. Ocorreu um erro no calculo do frete. Confira o endereço selecionado e continue.",
						Toast.LENGTH_LONG).show();
				tvFrete.setText("Ops...");
				tvPrazoEntrega.setText("Ops...");
			}

			// valor X quantidade
			float valor = offer.getValue() * qtdOffer;
			// resultado + frete
			float total = (Float.parseFloat(frete.get("Valor").toString()
					.replace(",", ".")) + valor);
			totalCheck = total;
			tvTotalVal.setText(String.format("R$ %.2f", total));
		}
	}

	/**
	 * Recebe o retorno do Service de criacao do registro de Checkout
	 * 
	 * @author Matheus Odilon - accentialbrasil
	 * 
	 */
	public class CheckoutResponseReceiver extends BroadcastReceiver {
		public static final String ACTION_RESP_CREATE_CHECKOUT = "com.mamlambo.intent.action.MESSAGE_PROCESSED_CREATE_CHECKOUT";

		@Override
		public void onReceive(Context context, Intent intent) {

			Bundle parameters = intent.getExtras();
			checkout = (Checkout) parameters
					.getSerializable(CheckoutService.PARAM_OUT_CHECKOUT);

			vBlock.setVisibility(View.GONE);
			pbCheck.setVisibility(View.GONE);
			Log.e("retorno",
					"O id do checkout é: " + String.valueOf(checkout.getId()));
		}
	}

	// quando selecionado o endereço
	public void selectAddress(AditionalAddressesUser endereco) {

		enderecoFirstLine.setText(endereco.getAddress() + ", "
				+ endereco.getNumber() + " - " + endereco.getComplement()
				+ " - " + endereco.getCity() + " - " + endereco.getState());
		enderecoSecondLine.setText("CEP: " + endereco.getZipCode());

		tvLabelAddress.setText(endereco.getLabel());

	}

}