package com.accential.trueone;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.accential.trueone.adapter.SignaturesLandAdapter;
import com.accential.trueone.bean.CompaniesUser;
import com.accential.trueone.bean.CompanyCategory;
import com.accential.trueone.bean.CompanySubCategory;
import com.accential.trueone.bean.Wishlist;
import com.example.slide.R;
import com.loopj.android.image.SmartImageView;

@SuppressWarnings("all")
public class TesteViews extends Activity {

	private WebView web;
	private Spinner spinner;
	private Spinner spinner2;
	private EditText testData;
	private TextView checkDetail;
	private ImageView imageTeste;
	private ProgressDialog barra;
	private SignaturesLandAdapter adapter;
	private ListView list;
	private List<CompaniesUser> comps;

	private Button but;

	// TESTE DA OFERTA
	private TextView tituloOferta;
	private TextView resumeOferta;
	private SmartImageView foto;
	private TextView valorRealOferta;
	private TextView valorPromocional;
	private TextView dataValidade;
	private TextView valorParcelado;

	// inclusao wishlist
	private ListView lista;
	private Spinner spinnerCategory;
	private Spinner spinnerSubCategory;
	private EditText textViewNomeDoProduto;
	private EditText textViewEndsData;
	private EditText textViewDescription;
	private CompanyCategory category;
	private CompanySubCategory subCategory;
	private Wishlist wishlist;
	private AlertDialog alerta;
	private GridView gridview;
	// private ProfilePictureView pic;

	private CheckBox preto;
	private CheckBox branco;
	private CheckBox cinza;
	private CheckBox azul;
	private CheckBox verde;
	private TextView textCor;

	private View viewPreta;
	private View viewAzul;
	private View viewVerde;
	private int i;

	// private ResponseReceiver receiver;
	// private ResponseLoginTestReceiver loginReceiver;

	@SuppressLint("DefaultLocale")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activiy_teste);

		web = (WebView) findViewById(R.id.meuWebView);
		String[] string = new String[10];
		preto = (CheckBox) findViewById(R.id.preto);
		branco = (CheckBox) findViewById(R.id.branco);
		cinza = (CheckBox) findViewById(R.id.cinza);
		azul = (CheckBox) findViewById(R.id.azul);
		verde = (CheckBox) findViewById(R.id.verd);
		textCor = (TextView) findViewById(R.id.textCor);

		viewPreta = (View) findViewById(R.id.viewPreta);
		viewAzul = (View) findViewById(R.id.viewAzul);
		viewVerde = (View) findViewById(R.id.viewVerde);
		but = (Button) findViewById(R.id.button1);

		preto.setVisibility(View.GONE);
		branco.setVisibility(View.GONE);
		cinza.setVisibility(View.GONE);
		azul.setVisibility(View.GONE);
		verde.setVisibility(View.GONE);
		viewAzul.setVisibility(View.GONE);
		viewAzul.setVisibility(View.GONE);
		viewVerde.setVisibility(View.GONE);

		//
		// EditText input = (EditText) findViewById(R.id.txt_input);
		// String strInputMsg = input.getText().toString();
		// Intent msgIntent = new Intent(this, SimpleIntentService.class);
		// msgIntent.putExtra(SimpleIntentService.PARAM_IN_MSG, strInputMsg);
		// startService(msgIntent);
		//
		// IntentFilter filter = new IntentFilter(ResponseReceiver.ACTION_RESP);
		// filter.addCategory(Intent.CATEGORY_DEFAULT);
		// receiver = new ResponseReceiver();
		// registerReceiver(receiver, filter);

		// ----------------------------------
		// Offer offer = new Offer();
		// offer.setParcels("ACTIVE");
		// offer.setParcelsOffImpost("INACTIVE");
		//
		// Checkout check = new Checkout();
		// check.setId(2853);
		// check.setTotalValue(1000);
		//
		// Company comp = new Company();
		// comp.setFancy_name("QUALICON");
		// comp.setLogin_moip("daniela_bittencourt@hotmail.com.br");
		//
		// User user = new User();
		// user.setName("trueoneteste");
		// user.setEmail("trueone@teste.com");
		// user.setId(285);
		//
		// AditionalAddressesUser aa = new AditionalAddressesUser();
		// aa.setAddress("Rua 10");
		// aa.setNumber("500");
		// aa.setComplement("conj");
		// aa.setCity("S��o Paulo");
		// aa.setDistrict("Fazendo II");
		// aa.setState("SP");
		// aa.setZipCode("08540-500");
		//
		// //String url = CheckoutBO.makePayment(user, check, offer, comp, aa);
		// String url = "https://www.google.com.br";
		//
		// web.loadUrl(url);
		// WebSettings webSettings = web.getSettings();
		// webSettings.setJavaScriptEnabled(true);
		//
		// ////////
		// Map<String, Map<String, Map<String, String>>> key2 = new
		// HashMap<String,Map<String, Map<String, String>>>();
		// Map<String,Map<String,String>> params2 = new
		// HashMap<String,Map<String,String>>();
		// Map<String,String> conditions2 = new HashMap<String,String>();
		//
		// conditions2.put("Offer.title", "teste");
		// params2.put("conditions", conditions2);
		// key2.put("Offer", params2);
		//
		// List<Offer> offers = OfferBO.searchOffersByTitle("teste");
		//
		// Log.i("TESTE", "TAMANHO DA LISTA DE OFERTAS: " +
		// String.valueOf(offers.size()));

		// AsyncTask<Map, Void, Integer> async = new AsyncTask<Map, Void,
		// Integer>(){
		// @Override
		// protected Integer doInBackground(Map... params) {
		//
		// int i = OfferBO.count_offer(params[0]);
		//
		// return i;
		// }
		//
		// };
		//
		// try {
		// int i = async.execute(key2).get();
		// Log.i("TESTE", "TESTE DE PESQUISA E CONTA DE OFERTA - QUANTIDADE: " +
		// String.valueOf(i));
		// } catch (Exception e) {
		// e.printStackTrace();
		// }

		// ------------------------------------
		// Map<String, String> transaction = new HashMap<String, String>();
		// transaction.put("uniqueId", "2813");
		// transaction.put("value", "89.10");
		// transaction.put("reason", "QUALICON");
		// transaction.put("paymentMethod", "CreditCard");
		// transaction.put("isParcelsOn", "ACTIVE");
		// transaction.put("isParcelsWithTax", "INACTIVE");
		// transaction.put("companyLoginForMOIPSplit",
		// "daniela_bittencourt@hotmail.com.br");

		// Map<String, Object> payer = new HashMap<String, Object>();
		// payer.put("name", "trueoneteste");
		// payer.put("email", "trueone@teste.com");
		// payer.put("payerId", "285");

		// Map<String, String> billingAddress = new HashMap<String, String>();
		// billingAddress.put("address", "Rua 110");
		// billingAddress.put("number", "501");
		// billingAddress.put("complement", "Conj 6");
		// billingAddress.put("city", "Suzano");
		// billingAddress.put("neighborhood", "Fazenda 33");
		// billingAddress.put("state", "SP");
		// billingAddress.put("country", "BRA");
		// billingAddress.put("zipCode", "08540500");
		// billingAddress.put("phone", "(11)00000000");

		// payer.put("billingAddress", billingAddress);

		// Map<String, Map> paramsToSend = new HashMap<String, Map>();
		// paramsToSend.put("transaction", transaction);
		// paramsToSend.put("payer", payer);

		// UtilityComponentBO bo = new UtilityComponentBO();
		// String token = bo.generateSecureToken();

		// Base64Util base1 = new Base64Util();
		// String json = JSONUtils.encodeJSON(paramsToSend);
		// String based1 = base1.encode(json.getBytes());

		// Log.i("PARAMETROS", String.valueOf(paramsToSend));
		// Log.i("TOKEN", token);
		// Log.i("DECODE JSON", String.valueOf(bo.decodeData("")));
		// Log.i("URL",
		// "https://secure.trueone.com.br/t1mobilecore/iphone/payments/checkout/"
		// + based1 + "/" + token);
		// String url =
		// "https://secure.trueone.com.br/t1mobilecore/iphone/payments/checkout/"
		// + based1 + "/" + token;

		// Log.i("PAYER", String.valueOf(bo.encodeData(payer)));

		// AsyncTask<Map, Void, Void> async = new AsyncTask<Map, Void, Void>(){
		//
		// @Override
		// protected Void doInBackground(Map... params) {
		// UtilityComponentBO bo = new UtilityComponentBO();
		// bo.urlRequestToGetPayments(params[0]);
		// return null;
		// }
		//
		// };
		//
		// Checkout checkout = new Checkout();
		// checkout.setId(2813);
		// checkout.setDeliveryTime(4);
		// checkout.setUnitValue(89);
		// checkout.setAmount(1);
		// checkout.setShippingValue(12.5);
		// checkout.setMetrics("");

		// aa.setAddress("Rua St Antonio");
		// aa.setNumber("123");
		// aa.setCity("S��o Sebasti��o");
		// aa.setComplement("Condominio Crista Clara");
		// aa.setDistrict("Ali");
		// aa.setState("SP");
		// aa.setZipCode("08540500");

		// TESTE DO LOGIN COM FBK

		// Base64Util base = new Base64Util();
		// String based = base.encode(paramsEncoded.getBytes());

		// Log.i("ENCODE BASED", based);
		// byte[] bytes = base.decode(based);
		// //Log.i("DECODE BASED",
		// String.valueOf(bo.decodeData("eyJzdGF0dXMiOiJFWFBJUkVEX0FDQ0VTUyJ9")));

		// // async.execute(paramsToSend);
		// String token1 = bo.generateSecureToken();
		// Base64Util base1 = new Base64Util();
		// String json = JSONUtils.encodeJSON(paramsToSend);
		// String based1 = base1.encode(json.getBytes());
		// String url =
		// "http://secure.trueone.com.br/t1mobilecore/iphone/payments/checkout/"
		// + based1 + "/" + token1;

		// CheckoutBO checkbo = new CheckoutBO();
		// String url = checkbo.makePayment(user, check, offer, comp, aa);

		// web.loadUrl(url);
		// WebSettings webSettings = web.getSettings();
		// webSettings.setJavaScriptEnabled(true);

		// TESTANDO RASTREAMENTO DE REQUISI����ES DA WEBVIEW
		// web.setWebViewClient(new WebViewClient() {
		// @Override
		// public boolean shouldOverrideUrlLoading(WebView view, String url) {
		// if(url.startsWith("trueone:")){
		// if ( url.endsWith("didCancelPayment")) {
		// Log.i("teste requisicao da url",
		// "a url termina com DIDCANCELPAYMENT");
		// }else if(url.endsWith("moipPaymentSuccess")){
		// Log.i("teste requisicao da url",
		// "a url termina com MOIPPAYMENTSUCCESS");
		// }else if(url.endsWith("moipError")){
		// Log.i("teste requisicao da url", "a url termina com MOIPERROR");
		// }
		// }
		// return super.shouldOverrideUrlLoading(view, url);
		// }
		// });

		// Calendar calendar = Calendar.getInstance();
		// String data = String.valueOf(calendar.get(Calendar.YEAR) + "-" +
		// calendar.get(Calendar.MONTH) + "-" +
		// calendar.get(Calendar.DAY_OF_MONTH));
		// Log.i("IMPRIME DATA", data);

		// Checkout check3 = new Checkout();
		// check3.setUser(user);
		// check3.setCompany(comp);
		// check3.setOffer(offer);
		// CheckoutDAO dao = new CheckoutDAO();
		// List<Checkout> checks = dao.createCheckout2(check3);
		// Log.i("TAMANHO DA LISTA", String.valueOf(checks.size()));

		/**
		 * String stringBase =
		 * "eyJzdGF0dXMiOiJTQVZFX09LIiwiZGF0YSI6eyJDaGVja291dCI6eyJpZCI6IjI4NDUiLCJ1c2VyX2lkIjoiMjg1IiwiY29tcGFueV9pZCI6Ijc4IiwicGF5bWVudF9tZXRob2RfaWQiOiIzIiwib2ZmZXJfaWQiOiIxNzIiLCJwYXltZW50X3N0YXRlX2lkIjoiMTQiLCJ1bml0X3ZhbHVlIjoiMC4wMCIsInRvdGFsX3ZhbHVlIjoiMC4wMCIsImFtb3VudCI6IjAiLCJzaGlwcGluZ192YWx1ZSI6IjAuMDAiLCJzaGlwcGluZ190eXBlIjoiQ09SUkVJT1MiLCJkZWxpdmVyeV90aW1lIjoiMCIsIm1ldHJpY3MiOiIiLCJhZGRyZXNzIjoiIiwiY2l0eSI6IiIsInppcF9jb2RlIjoiIiwic3RhdGUiOiIiLCJkaXN0cmljdCI6IiIsIm51bWJlciI6IiIsImNvbXBsZW1lbnQiOiIiLCJkYXRlIjoiMjAxNC0wMi0wNCAwMDowMDowMCIsInRyYW5zYWN0aW9uX21vaXBfY29kZSI6IjAiLCJpbnN0YWxsbWVudCI6IjAifSwiVXNlciI6eyJpZCI6IjI4NSIsIm5hbWUiOiJ0cnVlb25ldGVzdGUiLCJlbWFpbCI6InRydWVvbmVAdGVzdGUuY29tIiwiZ2VuZGVyIjoiIiwicGFzc3dvcmQiOiI3NmJiMWZmMzY5OWUwYWYzNzUwZTlmYTExOWRlYTQ0ZSIsImJpcnRoZGF5IjoiMDAwMC0wMC0wMCIsImFkZHJlc3MiOiJSdWEgMTEwIiwiY2l0eSI6IlN1emFubyAiLCJ6aXBfY29kZSI6IjA4NTQwNTAwIiwic3RhdGUiOiJTUCIsImRpc3RyaWN0IjoiRmF6ZW5kbyAzMyIsIm51bWJlciI6IjUwMSIsImNvbXBsZW1lbnQiOiJDb25qIDYiLCJwaG90byI6Imh0dHA6XC9cL3RydWVvbmUuY29tLmJyXC91cGxvYWRzXC9pbWctdXNlcnNcL21hbGUuanBnIiwic3RhdHVzIjoiQUNUSVZFIiwiZGF0ZV9yZWdpc3RlciI6IjIwMTMtMTAtMjIgMTQ6NTM6NDUifSwiUGF5bWVudE1ldGhvZCI6eyJpZCI6IjMiLCJ0eXBlIjoiQ2FydGFvIGRlIENyZWRpdG8iLCJuYW1lIjoiVmlzYSIsInN0YXR1cyI6IkFDVElWRSIsImxhc3Rfc3RhdHVzIjoiQUNUSVZFIn0sIk9mZmVyIjp7ImlkIjoiMTcyIiwiY29tcGFueV9pZCI6Ijc4IiwidGl0bGUiOiJUZXN0ZSAtIEVkc29uIiwicmVzdW1lIjoiTG9yZW0gaXBzdW0gZG9sb3Igc2l0IGFtZXQsIGNvbnNlY3RldHVyIGFkaXBpc2NpbmcgZWxpdC4gTWFlY2VuYXMgYWMganVzdG8gbWkuIiwiZGVzY3JpcHRpb24iOiI8cCBzdHlsZT1cInRleHQtYWxpZ246IGp1c3RpZnk7IGZvbnQtc2l6ZTogMTFweDsgbGluZS1oZWlnaHQ6IDE0cHg7IG1hcmdpbjogMHB4IDBweCAxNHB4OyBwYWRkaW5nOiAwcHg7IGZvbnQtZmFtaWx5OiBBcmlhbCwgSGVsdmV0aWNhLCBzYW5zO1wiPkxvcmVtIGlwc3VtIGRvbG9yIHNpdCBhbWV0LCBjb25zZWN0ZXR1ciBhZGlwaXNjaW5nIGVsaXQuIE1hZWNlbmFzIGFjIGp1c3RvIG1pLiBDcmFzIHZpdGFlIHJob25jdXMgbWkuIERvbmVjIGNvbmRpbWVudHVtIHBoYXJldHJhIGFyY3UsIHNlZCBvcm5hcmUgYW50ZSBsYWNpbmlhIG5lYy4gVml2YW11cyBtYWxlc3VhZGEsIGxhY3VzIGF0IHZ1bHB1dGF0ZSBwcmV0aXVtLCBmZWxpcyBqdXN0byBibGFuZGl0IGFyY3UsIGV1IGNvbnNlY3RldHVyIGxlbyBkdWkgcXVpcyB1cm5hLiBTZWQgdXQgcXVhbSBldCBhcmN1IGRpZ25pc3NpbSBhY2N1bXNhbi4gRHVpcyBxdWlzIG1hZ25hIHBoYXJldHJhLCBoZW5kcmVyaXQgcXVhbSBldCwgaGVuZHJlcml0IGp1c3RvLiBWaXZhbXVzIGNvbnNlY3RldHVyIHVybmEgZXJvcywgbWF0dGlzIHN1c2NpcGl0IGVzdCBydXRydW0gYS4gRG9uZWMgZXQgc29kYWxlcyB0dXJwaXMuIEFlbmVhbiBlZ2V0IGxhY3VzIG5lYyBsaWd1bGEgZWxlbWVudHVtIGdyYXZpZGEgaW4gbm9uIG1hZ25hLiBEb25lYyBub24gZGlhbSB2aXRhZSBtYWduYSBmcmluZ2lsbGEgYXVjdG9yIGF0IGlkIGR1aS4gUGhhc2VsbHVzIGV0IG1hc3NhIGVnZXQgc2VtIGlhY3VsaXMgb3JuYXJlLiBBZW5lYW4gY29udmFsbGlzIHZlbCBsaWd1bGEgcG9zdWVyZSB2aXZlcnJhLiBQaGFzZWxsdXMgdm9sdXRwYXQgY29uc2VxdWF0IHR1cnBpcyBldSB2dWxwdXRhdGUuIFZpdmFtdXMgZW5pbSBsaWJlcm8sIHNlbXBlciBldCBzZW0gbmVjLCBvcm5hcmUgZGlnbmlzc2ltIGxlby48XC9wPlxyXG5cclxuPHAgc3R5bGU9XCJ0ZXh0LWFsaWduOiBqdXN0aWZ5OyBmb250LXNpemU6IDExcHg7IGxpbmUtaGVpZ2h0OiAxNHB4OyBtYXJnaW46IDBweCAwcHggMTRweDsgcGFkZGluZzogMHB4OyBmb250LWZhbWlseTogQXJpYWwsIEhlbHZldGljYSwgc2FucztcIj5NYWVjZW5hcyB2ZWwgZ3JhdmlkYSB1cm5hLCBzaXQgYW1ldCBlbGVpZmVuZCBhbnRlLiBWZXN0aWJ1bHVtIGFudGUgaXBzdW0gcHJpbWlzIGluIGZhdWNpYnVzIG9yY2kgbHVjdHVzIGV0IHVsdHJpY2VzIHBvc3VlcmUgY3ViaWxpYSBDdXJhZTsgRHVpcyBpbnRlcmR1bSBpZCBwdXJ1cyBhYyBtYWxlc3VhZGEuIEV0aWFtIGluIHZlbmVuYXRpcyBmZWxpcy4gTW9yYmkgbG9yZW0gZXJvcywgZWdlc3RhcyBldSBwbGFjZXJhdCBldCwgdmVuZW5hdGlzIGluIHNhcGllbi4gU2VkIHRlbXB1cyBsZW8gdXQgZGlhbSBkaWN0dW0gYmliZW5kdW0uIEV0aWFtIG9ybmFyZSBtaSBhdCBqdXN0byBwbGFjZXJhdCB2ZW5lbmF0aXMuIE51bGxhbSBydXRydW0sIGp1c3RvIGlkIGludGVyZHVtIGNvbmRpbWVudHVtLCBhdWd1ZSBlbGl0IGVsZW1lbnR1bSBpcHN1bSwgZWdldCB1bHRyaWNlcyBwdXJ1cyBlbGl0IGVnZXQgZHVpLiBNb3JiaSBhIGV1aXNtb2QgdXJuYSwgcXVpcyBjdXJzdXMgc2FwaWVuLiBQaGFzZWxsdXMgc2VkIGVuaW0gYXQgbGVjdHVzIGRpY3R1bSBzY2VsZXJpc3F1ZS4gU2VkIHZpdmVycmEgcXVpcyBtYWduYSBldSBjb25ndWUuIE1hdXJpcyBzaXQgYW1ldCB1bHRyaWNlcyB0ZWxsdXMsIHZlc3RpYnVsdW0gZmFjaWxpc2lzIHB1cnVzLiBQcm9pbiBldCB0ZWxsdXMgdWx0cmljaWVzLCBpYWN1bGlzIG51bGxhIGV0LCBmYXVjaWJ1cyBvZGlvLjxcL3A"
		 * + "+"; Base64Util bas64 = new Base64Util(); byte[] chars =
		 * bas64.decode(stringBase); String stringDecoded; try { stringDecoded =
		 * new String(chars, "UTF-8"); Log.i("TESTE DE DECODE", stringDecoded);
		 * 
		 * String sub = stringDecoded.substring(46, 50); int id =
		 * Integer.parseInt(sub); Log.i("TESTE DE ENCODE - SUNSTRING",
		 * String.valueOf(id)); } catch (Exception e) { Log.e("ERROR",
		 * "exception"); e.printStackTrace(); }
		 **/
		// metodo pra salvar checkout
		/**
		 * AsyncTask<Map, Void, String> asyn1 = new AsyncTask<Map, Void,
		 * String>(){
		 * 
		 * @Override protected String doInBackground(Map... params) {
		 * 
		 *           CheckoutDAO dao = new CheckoutDAO(); String string =
		 *           dao.saveCheckout2(params[0]); return string; }
		 * 
		 *           }; /// Map<String, Map> arrayParams = new
		 *           HashMap<String,Map>(); Map<String,String> datas = new
		 *           HashMap<String,String>();
		 * 
		 *           Calendar calendar = Calendar.getInstance(); String data =
		 *           String.valueOf(calendar.get(Calendar.YEAR) + "-" +
		 *           (calendar.get(Calendar.MONTH) + 1) + "-" +
		 *           calendar.get(Calendar.DAY_OF_MONTH));
		 * 
		 *           datas.put("user_id", "285"); datas.put("company_id", "78");
		 *           datas.put("payment_method_id", "3"); datas.put("offer_id",
		 *           "172"); datas.put("payment_state_id", "14");
		 *           datas.put("unit_value", "0"); datas.put("total_value",
		 *           "0"); datas.put("amount", "0"); datas.put("shipping_value",
		 *           "0"); datas.put("shipping_type", "CORREIOS");
		 *           datas.put("delivery_time", "0"); datas.put("metrics", "");
		 *           datas.put("address", ""); datas.put("city", "");
		 *           datas.put("zip_code", ""); datas.put("state", "");
		 *           datas.put("district", ""); datas.put("number", "");
		 *           datas.put("complement", ""); datas.put("date", data);
		 *           datas.put("transaction_moip_code", "0");
		 *           datas.put("installment", "0");
		 * 
		 *           arrayParams.put("Checkout", datas); Log.i("TESTE TESTE",
		 *           "TESTE TESTE"); try { String string1 =
		 *           asyn1.execute(arrayParams).get();
		 * 
		 *           Log.i("TESTE DA STRING", string1);
		 * 
		 *           String stringBase = string1 + "+";
		 * 
		 *           Log.i("TESTE DA STRING 2", stringBase);
		 * 
		 *           Base64Util bas64 = new Base64Util(); byte[] chars =
		 *           bas64.decode(stringBase); String stringDecoded; try {
		 *           stringDecoded = new String(chars, "UTF-8");
		 *           Log.i("TESTE DE DECODE", stringDecoded);
		 * 
		 *           String sub = stringDecoded.substring(46, 50); int id =
		 *           Integer.parseInt(sub); Log.i("TESTE DE ENCODE - SUNSTRING",
		 *           String.valueOf(id)); } catch (Exception e) { Log.e("ERROR",
		 *           "exception"); e.printStackTrace(); } } catch (Exception e)
		 *           { e.printStackTrace(); }
		 **/

		// String encode64 = bo.encodeData2(paramsToSend);
		// Log.i("DECODE TOKEN",
		// String.valueOf(bo.decodeData("eyJzdGF0dXMiOiJFWFBJUkVEX0FDQ0VTUyJ9")));
		// Log.i("DECODE",
		// String.valueOf(bo.decodeData("eyJwYXllciI6eyJwYXllcklkIjoiMjg1IiwiZW1haWwiOiJ0cnVlb25lQHRlc3RlLmNvbSIsImJpbGxpbmdBZGRyZXNzIjp7ImNvbXBsZW1lbnQiOiJDb25qIDYiLCJwaG9uZSI6IigxMSkwMDAwMDAwMCIsImFkZHJlc3MiOiJSdWEgMTEwIiwiemlwQ29kZSI6IjA4NTQwNTAwIiwic3RhdGUiOiJTUCIsIm5laWdoYm9yaG9vZCI6IkZhemVuZGEgMzMiLCJudW1iZXIiOiI1MDEiLCJjb3VudHJ5IjoiQlJBIiwiY2l0eSI6IlN1emFubyJ9LCJuYW1lIjoidHJ1ZW9uZXRlc3RlIn0sInRyYW5zYWN0aW9uIjp7ImNvbXBhbnlMb2dpbkZvck1PSVBTcGxpdCI6ImRhbmllbGFfYml0dGVuY291cnRAaG90bWFpbC5jb20uYnIiLCJ2YWx1ZSI6Ijk5IiwiaXNQYXJjZWxzV2l0aFRheCI6IklOQUNUSVZFIiwiaXNQYXJjZWxzT24iOiJBQ1RJVkUiLCJyZWFzb24iOiJRVUFMSUNPTiIsInBheW1lbnRNZXRob2QiOiJDcmVkaXRDYXJkIiwidW5pcXVlSWQiOiIyODExIn19")));
		// Log.i("DECODE",
		// bo.decodeData("bGxpbmdBZGRyZXNzIjp7ImNvbXBsZW1lbnQiOiJDb25qIDYiLCJwaG9uZSI6IigxMSkwMDAwMDAw").toString());
		// Log.i("DECODE",
		// bo.decodeData("MCIsImFkZHJlc3MiOiJSdWEgMTEwIiwiemlwQ29kZSI6IjA4NTQwNTAwIiwic3RhdGUiOiJTUCIs").toString());
		// Log.i("DECODE",
		// bo.decodeData("Im5laWdoYm9yaG9vZCI6IkZhemVuZGEgMzMiLCJudW1iZXIiOiI1MDEiLCJjb3VudHJ5IjoiQlJB").toString());
		// Log.i("DECODE",
		// bo.decodeData("IiwiY2l0eSI6IlN1emFubyJ9LCJuYW1lIjoidHJ1ZW9uZXRlc3RlIn0sInRyYW5zYWN0aW9uIjp7").toString());
		// Log.i("DECODE",
		// bo.decodeData("ImNvbXBhbnlMb2dpbkZvck1PSVBTcGxpdCI6ImRhbmllbGFfYml0dGVuY291cnRAaG90bWFpbC5j").toString());
		// Log.i("DECODE",
		// bo.decodeData("b20uYnIiLCJ2YWx1ZSI6Ijk5IiwiaXNQYXJjZWxzV2l0aFRheCI6IklOQUNUSVZFIiwiaXNQYXJj").toString());
		// Log.i("DECODE",
		// bo.decodeData("ZWxzT24iOiJBQ1RJVkUiLCJyZWFzb24iOiJRVUFMSUNPTiIsInBheW1lbnRNZXRob2QiOiJDcmVk").toString());
		// Log.i("DECODE",
		// bo.decodeData("aXRDYXJkIiwidW5pcXVlSWQiOiIyODExIn19").toString());

		// async.execute(paramsToSend);

		// CheckoutDAO dao = new CheckoutDAO();
		// dao.createCheckout();
		// UtilityComponentBO bo = new UtilityComponentBO();
		//
		// Map<String, Map<String, Map<String, String>>> key3 = new
		// HashMap<String,Map<String, Map<String, String>>>();
		// Map<String,Map<String,String>> params3 = new
		// HashMap<String,Map<String,String>>();
		// Map<String,String> conditions3 = new HashMap<String,String>();
		// //
		// params3.put("conditions", conditions3);
		// key3.put("Checkout", params3);
		//
		// Map<String, Map> params = new HashMap<String, Map>();
		// Map<String, String> datas = new HashMap<String, String>();
		//
		// //datas.put("id", "2798");
		// datas.put("user_id", "285");
		// datas.put("company_id", "78");
		// datas.put("payment_method_id", "3");
		// datas.put("offer_id", "166");
		// datas.put("payment_state_id", "14");
		// datas.put("unit_value", "0");
		// datas.put("total_value", "0");
		// datas.put("amount", "0");
		// datas.put("shipping_value", "0");
		// datas.put("shipping_type", "CORREIOS");
		// datas.put("delivery_time", "0");
		// datas.put("date", "2014-01-20");
		// datas.put("transaction_moip_code", "0");
		// datas.put("installment", "0");
		//
		// params.put("Checkout", datas);
		//
		// Map<String, String> datas2 = new HashMap<String, String>();
		//
		// datas2.put("sCepOrigem", "04101000");
		// datas2.put("sCepDestino", "08540500");
		// datas2.put("nVlPeso", "2");
		//
		// //bo.urlToCalcuateShippingValue(datas2);
		//
		// AsyncTask<Map, Void, Map> async = new AsyncTask<Map, Void, Map>(){
		//
		// @Override
		// protected Map doInBackground(Map... params) {
		// UtilityComponentBO bo = new UtilityComponentBO();
		// return bo.urlToCalcuateShippingValue(params[0]);
		// }
		//
		// };
		// CheckoutDAO dao = new CheckoutDAO();
		// Map<String, String> mapString = new HashMap<String, String>();
		// mapString = dao.calculateShippingValue("08540500", "04101-000", "1");
		// Log.i("VALOR", mapString.get("Valor"));

		// try {
		// async.execute(params).get();
		// } catch (Exception e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

		// LOGICA DAS CORES
		// Offer offer = OfferBO.searchOfferById(172);
		// String cores[] = new String[10];
		//
		// String arrayCarac = offer.getMetrics();
		//
		// String teste = arrayCarac.replace(":", "").
		// replace("}", "").replace("{", "").replace("[", "").replace("]",
		// "").replace(" ", "").replace("\"", "");
		// StringBuilder stringBuilder = new StringBuilder(teste);
		// stringBuilder.insert(3, ',');
		//
		// cores = stringBuilder.toString().split(",");
		//
		// for (String string : cores) {
		// if(string.equals("preto")){
		// preto.setVisibility(View.VISIBLE);
		// }if(string.equals("cinza")){
		// cinza.setVisibility(View.VISIBLE);
		// }if(string.equals("branco")){
		// branco.setVisibility(View.VISIBLE);
		// }if(string.equals("azul")){
		// azul.setVisibility(View.VISIBLE);
		// }
		// }

		/**
		 * CheckoutDAO dao = new CheckoutDAO(); Checkout check = new Checkout();
		 * 
		 * User user = new User(); user.setId(285); Company company = new
		 * Company(); company.setId(78); Offer offer = new Offer();
		 * offer.setId(172); PaymentMethod method = new PaymentMethod();
		 * method.setId(3);
		 * 
		 * check.setUser(user); check.setCompany(company);
		 * check.setOffer(offer); check.setMethod(method);
		 * check.setUnitValue(0); check.setTotalValue(0); check.setAmount(0);
		 * check.setShippingValue(0); check.setShippingType("CORREIOS");
		 * check.setDeliveryTime(0); check.setTransactionMoipCode("0");
		 * check.setInstallment(0);
		 * 
		 * dao.createCheckout(check);
		 **/
		// //TESTANDO METODO EXCLUIR
		// WishlistDAO dao = new WishlistDAO();
		// //dao.deleteWishlist(285);
		// dao.saveWishlist(1);

		// lista = (ListView) findViewById(R.id.listView_listaCompras);
		// spinnerCategory = (Spinner) findViewById(R.id.spinner_category);
		// spinnerSubCategory = (Spinner)
		// findViewById(R.id.spinner_subCategory);
		// textViewNomeDoProduto = (EditText)
		// findViewById(R.id.editText_productName);
		// textViewEndsData = (EditText) findViewById(R.id.editText2);
		// textViewDescription = (EditText) findViewById(R.id.editText1);

		// Map<String, Map<String, Map<String, String>>> key3 = new
		// HashMap<String,Map<String, Map<String, String>>>();
		// Map<String,Map<String,String>> params3 = new
		// HashMap<String,Map<String,String>>();
		// Map<String,String> conditions3 = new HashMap<String,String>();
		//
		// conditions3.put("Offer.id", "175");
		// params3.put("conditions", conditions3);
		// key3.put("Offer", params3);

		/*
		 * List<CompaniesUser> compsUser =
		 * CompaniesUserBO.returnObjCompaniesUser(285);
		 * qtdCompanies.setText("Voc�� est�� recebendo ofertas de "
		 * .concat(String.valueOf(compsUser.size())).concat(" empresas"));
		 * SignaturesLandAdapter adapter = new SignaturesLandAdapter(this,
		 * compsUser); lista.setAdapter(adapter);
		 */

		// List<Wishlist> wishies = WishlistBO.retornaObj(285);
		// WishlistLandAdapter adapter = new WishlistLandAdapter(this, wishies);
		// lista.setAdapter(adapter);

		// ProgressBar progress = (ProgressBar) findViewById(R.id.progress);
		// TextView texto = (TextView) findViewById(R.id.texto);
		// SmartImageView imagem = (SmartImageView) findViewById(R.id.imagem);
		//
		// SignaturesLandAdapter adapter = new SignaturesLandAdapter(this,
		// lista);
		// new MinhaTask(context, progressBar, adapter, compsUser, lista)

		/*
		 * Map<String, Map<String, Map<String, String>>> key3 = new
		 * HashMap<String,Map<String, Map<String, String>>>();
		 * Map<String,Map<String,String>> params3 = new
		 * HashMap<String,Map<String,String>>(); Map<String,String> conditions3
		 * = new HashMap<String,String>();
		 * 
		 * conditions3.put("UsersWishlist.id", "50");
		 * conditions3.put("UsersWishlist.user_id", "285");
		 * conditions3.put("UsersWishlist.category_id", "1");
		 * conditions3.put("UsersWishlist.sub_category_id", "5");
		 * conditions3.put("UsersWishlist.id", "76");
		 * conditions3.put("UsersWishlist.description", "teste insercao");
		 * params3.put("conditions", conditions3); key3.put("UsersWishlist",
		 * params3);
		 * 
		 * WishlistDAO wishDAO = new WishlistDAO();
		 * wishDAO.saveMyWishlists(key3);
		 */

		// List<Wishlist> wishies = WishlistBO.listAllWishlists(key3);

		// List<Checkout> checks = CheckoutBO.returnsObjCheckout(285);

		// CheckoutLandAdapter adapter = new CheckoutLandAdapter(this, checks);
		// lista.setAdapter(adapter);
		/*
		 * float total = 0; int percentage = offer.getPercentageDiscount();
		 * if(percentage != 0){ float value = offer.getValue(); float desconto =
		 * (value * percentage)/100; total = value - desconto; }else { total =
		 * offer.getValue(); }
		 * 
		 * tituloOferta.setText(offer.getTitle());
		 * resumeOferta.setText(Html.fromHtml(offer.getDescription()));
		 * valorRealOferta
		 * .setText("de R$ ".concat(String.valueOf(offer.getValue(
		 * )).replace(".", ","))); fotoOferta.setImageUrl(offer.getPhoto());
		 * 
		 * float valParcela = offer.getValue() / 24;
		 * valorPromocional.setText("R$ "
		 * .concat(String.valueOf(total).replace(".", ",")));
		 * dataValidade.setText("V��lido at��".concat(String.valueOf(" " +
		 * offer.getEndsAt().get(Calendar.DAY_OF_MONTH) + "/" +
		 * (offer.getEndsAt().get(Calendar.MONTH) + 1) + "/" +
		 * offer.getEndsAt().get(Calendar.YEAR))));
		 * valorParcelado.setText("R$ ".concat(String.valueOf(valParcela)));
		 */

		/**
		 * final ImageView image1 = (ImageView) findViewById(R.id.imageView9);
		 * final ImageView image2 = (ImageView) findViewById(R.id.imageView10);
		 * final ImageView image3 = (ImageView) findViewById(R.id.imageView11);
		 * final ImageView image4 = (ImageView) findViewById(R.id.imageView12);
		 * 
		 * image1.setOnClickListener(new OnClickListener() {
		 * 
		 * @Override public void onClick(View arg0) {
		 *           image1.setImageResource(R.drawable.bg_tab_on);
		 *           image2.setImageResource(R.drawable.bg_tab_off);
		 *           image3.setImageResource(R.drawable.bg_tab_off);
		 *           image4.setImageResource(R.drawable.bg_tab_off); } });
		 * 
		 *           image2.setOnClickListener(new OnClickListener() {
		 * @Override public void onClick(View arg0) {
		 *           image2.setImageResource(R.drawable.bg_tab_on);
		 *           image1.setImageResource(R.drawable.bg_tab_off);
		 *           image3.setImageResource(R.drawable.bg_tab_off);
		 *           image4.setImageResource(R.drawable.bg_tab_off); } });
		 * 
		 *           image3.setOnClickListener(new OnClickListener() {
		 * @Override public void onClick(View arg0) {
		 *           image3.setImageResource(R.drawable.bg_tab_on);
		 *           image1.setImageResource(R.drawable.bg_tab_off);
		 *           image2.setImageResource(R.drawable.bg_tab_off);
		 *           image4.setImageResource(R.drawable.bg_tab_off); } });
		 * 
		 *           image4.setOnClickListener(new OnClickListener() {
		 * @Override public void onClick(View arg0) {
		 *           image4.setImageResource(R.drawable.bg_tab_on);
		 *           image1.setImageResource(R.drawable.bg_tab_off);
		 *           image2.setImageResource(R.drawable.bg_tab_off);
		 *           image3.setImageResource(R.drawable.bg_tab_off); } });
		 **/

		/**
		 * List<Wishlist> wishies = WishlistBO.retornaObj(285);
		 * WishlistLandAdapter adapter = new WishlistLandAdapter(this, wishies);
		 * lista.setAdapter(adapter);
		 **/
		// adapter = new SignaturesLandAdapter(this, comps);
		// list =(ListView) findViewById(R.id.listView_listaAssinaturas);
		// list.setAdapter(adapter);

		// img.setImageUrl("https://www.google.com.br/logos/doodles/2013/ary-barrosos-110th-birthday-5910046797987840-hp.jpg");
		// DownloadImagemUtil util = new DownloadImagemUtil(this);
		// Bitmap bm =
		// util.getBitmap("https://www.google.com.br/logos/doodles/2013/ary-barrosos-110th-birthday-5910046797987840-hp.jpg");
		// img.setImageBitmap(bm);

		// web.setOnTouchListener(new OnTouchListener() {
		// @Override
		// public boolean onTouch(View v, MotionEvent event) {
		// Toast.makeText(v.getContext(), "EXIBE TEXTO TESTE",
		// Toast.LENGTH_LONG).show();
		// return false;
		// } });

		// User user = UserBO.searchUserByCompaniesUserId(305);
		// Log.i("NOME USUARIO", user.getName());

		// Company company = new Company();
		// company = CompanyBO.searchCompanyByCompaniesUserId(304);
		// Log.i("COMP NAME", company.getFancy_name());

		// Company comp = CompanyBO.searchCompanyByCompaniesUserId(304);
		// Log.i("FANCY COMP", comp.getFancy_name());

		// Display display = getWindowManager().getDefaultDisplay();
		// int width=display.getWidth();
		// int width2 = 300;
		// String
		// data="<html><head><title>Example</title><meta name=\"viewport\"\"content=\"width="+width+", initial-scale=0.65 \" /></head>";
		// data=data+"<body><center><img width=\""+width2+"\" src=\""+
		// "http://imagensgratis.com.br/imagens/imagens-imagens-playboy-73778f.jpg"
		// +"\" /></center></body></html>";
		// web.loadData(data, "text/html", null);
		// web.setVerticalScrollBarEnabled(false);
		// web.setHorizontalScrollBarEnabled(false);
		//
		// web.setVisibility(View.GONE);

		// conditions3.put("Offer.title", "a");
		// params3.put("conditions", conditions3);
		// key3.put("Offer", params3);
		//
		// List<Offer> offers = OfferBO.listAllOffers(key3);

		/**
		 * AsyncTask<Map, Void, Void> async = new AsyncTask<Map, Void, Void>(){
		 * 
		 * @Override protected Void doInBackground(Map... params) { UserDAO dao
		 *           = new UserDAO(); dao.saveNewUser(params[0]); return null; }
		 * 
		 *           }; async.execute(key3);
		 **/

		/**
		 * List<Offer> offers = OfferBO.listAllOffers(key3); for (Offer offer :
		 * offers) { Log.i("DATA TESTE:",
		 * String.valueOf(offer.getEndsAt().get(Calendar.DAY_OF_MONTH) + "/" +
		 * (offer.getEndsAt().get(Calendar.MONDAY) + 1) + "/" +
		 * offer.getEndsAt().get(Calendar.YEAR))); }
		 **/

		/**
		 * List<Wishlist> wishs = WishlistBO.retornaObj(285); for (Wishlist
		 * wishlist : wishs) { Log.i("TESTE ENDS DATE", String.valueOf(
		 * wishlist.getDateRegister().get(Calendar.DAY_OF_MONTH)) + "/" +
		 * (wishlist.getDateRegister().get(Calendar.MONTH) + 1) + "/" +
		 * wishlist.getDateRegister().get(Calendar.YEAR)); }
		 **/
		// List<Checkout> checks = CheckoutBO.listAllCheckouts(key3);
		// List<Offer> offers = OfferBO.listAllOffers(key3);
		// List<Checkout> checks = CheckoutBO.listAllCheckouts(key3);
		// for (Checkout checkout : checks) {
		// Log.i("TESTE DATE CHECK -",
		// String.valueOf(checkout.getDateTime().get(Calendar.DAY_OF_MONTH) +
		// "/" +
		// (checkout.getDateTime().get(Calendar.MONTH) + 1) + "/" +
		// checkout.getDateTime().get(Calendar.YEAR) ));
		// }
		// List<Wishlist> wishies = WishlistBO.listAllWishlists(key3);
		// for (Offer offer : offers) {
		// //SimpleDateFormat s = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		// // Date desc = new Date(offer.getTesteData());
		// // String a = s.format(offer.getBeginsAt());
		// Log.i("DATA TESTE:",
		// String.valueOf(offer.getBeginsAt().get(Calendar.DAY_OF_MONTH) + "/"
		// + (offer.getBeginsAt().get(Calendar.MONDAY) + 1) + "/" +
		// offer.getBeginsAt().get(Calendar.YEAR)));
		// }
		// Calendar c = Calendar.getInstance();
		// SimpleDateFormat s = new SimpleDateFormat("dd/MM/yyyy");
		// String a = s.format(c.getTime());
		// Log.i("DATA TESTE:", String.valueOf(a));

		// List<Offer> ofers = OfferBO.listAllOffers(key3);
		// List<Checkout> checks = CheckoutBO.listAllCheckouts(key3);

		// for (Checkout checkout : checks) {
		// Log.i("CHECKOUT ", String.valueOf(checkout.getId()));
		// Log.i("CHECKOUT Produto", checkout.getOffer().getTitle());
		// }
		// // OfferDAO dao = new OfferDAO();
		// Offer offer = dao.searchOfferById(182);

		// List<CompanySubCategory> subs =
		// CompanySubCategoryBO.listAllCategories(key);
		// Log.i("Numero de registros", String.valueOf(subs.size()));
		// for (CompanySubCategory companySubCategory : subs) {
		// CompanyCategory comp = CompanyCategoryBO.searchById(1);
		// Log.i("Categoria", comp.getName());
		// Log.i("Subcategoria", companySubCategory.getName());
		// }
		//

		// CompanyCategoryBO.searchById(1);

		// List<CompanyCategory> categories =
		// CompanyCategoryBO.listAllCategories(key);
		// for (CompanyCategory companyCategory : categories) {
		// Log.i("NOME CATEGORIA", String.valueOf(companyCategory.getId()));
		// CompanySubCategory sub = CompanySubCategoryBO.searchByCategoryId(5);
		// Log.i("NOME DA SUBCATEGORIA", sub.getName());
		//
		// }

		// List<Wishlist> wishs = WishlistBO.listAllWishlists(key);
		// for (Wishlist wishlist : wishs) {
		// Log.i("TITLE OF WISH", wishlist.getName());
		// }
		// UserBO.listAllUsers(key);
		// CompanySubCategoryBO.listAllCategories(key);
		// List<Offer> offers = OfferBO.listAllOffers(key);
		// List<Company> companies = CompanyBO.listAllCompanies(key);
		// for (Company company : companies) {
		// Log.i("C��digo category", String.valueOf(company.getCategory_id()));
		// Log.i("Fancy name", company.getFancy_name());
		// }
		// UserDAO dao = new UserDAO();
		// dao.realSave(key);
		// List<CompanyCategory> lista =
		// CompanyCategoryBO.listAllCategories(key);
		// AsyncTask<Map, Void, Void> async = new AsyncTask<Map, Void, Void>(){
		//
		// @Override
		// protected Void doInBackground(Map... params) {
		// CompanyCategoryBO.listCategories(params[0]);
		// return null;
		// }
		//
		// };

		//
		// async.execute(key);
		//
		//
		// List<Offer> offers = OfferBO.listAllOffers(key);
		// for (Offer offer : offers) {
		// SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		// Calendar begins = Calendar.getInstance();
		// Calendar ends = Calendar.getInstance();
		// try {
		// begins.setTime(sdf.parse(offer.getBeginsAt().toString()));
		// ends.setTime(sdf.parse(offer.getEndsAt().toString()));
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		// Log.i("NAME OFFER", offer.getTitle());
		// Log.i("DATA BEGIN", String.valueOf(begins.MONTH));
		// Log.i("DATA END", ends.get(Calendar.DAY_OF_MONTH) + "/" +
		// ends.get(Calendar.MONTH) + "/"+ ends.get(Calendar.YEAR));
		// }

		// AsyncTask<Map, Void, Integer> async = new AsyncTask<Map, Void,
		// Integer>(){
		//
		// @Override
		// protected Integer doInBackground(Map... params) {
		// CompanyDAO dao = new CompanyDAO();
		// int i = dao.retornaCompId(params[0]);
		// return i;
		// }
		// };

		// int y = 0;
		// try {
		// y = async.execute(key).get();
		// } catch (InterruptedException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// } catch (ExecutionException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// Log.i("TESTE ASYNC", String.valueOf(y));
		//
		// Company comp = CompanyBO.searchById(76);
		// Log.i("COMPANY'S FANCY NAME", comp.getFancy_name());

	}

	public static Bitmap getBitmapFromURL(String src) {
		try {
			URL url = new URL(src);
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setDoInput(true);
			connection.connect();
			InputStream input = connection.getInputStream();
			Bitmap myBitmap = BitmapFactory.decodeStream(input);
			return myBitmap;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	// private static Session openActiveSession(Activity activity, boolean
	// allowLoginUI, Session.StatusCallback statusCallback) {
	// OpenRequest openRequest = new OpenRequest(activity);
	// openRequest.setPermissions(Arrays.asList("user_birthday", "email",
	// "user_religion_politics", "user_relationships",
	// "user_relationship_details"));
	// openRequest.setCallback(statusCallback);
	// Session session = new Session.Builder(activity).build();
	// if(SessionState.CREATED_TOKEN_LOADED.equals(session.getState()) ||
	// allowLoginUI) {
	// Session.setActiveSession(session); session.openForRead(openRequest);
	// return session;
	// }
	// return null;
	// }

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		// Session.getActiveSession().onActivityResult(this, requestCode,
		// resultCode, data);
	}

	public void postar(View v) {

		// Session.openActiveSession(this, true, new Session.StatusCallback() {
		// public void call(Session session, SessionState state, Exception
		// exception) { if (session.isOpened()) { if (hasPublishPermission()) {
		// Request.executeStatusUpdateRequestAsync(session, "Teste", new
		// Request.Callback() { public void onCompleted(Response response) { if
		// (response.getError() == null) { //sucesso } else { //erro } } }); }
		// else { requisitaPermissao(); } } } });

	}

	public void requisitaPermissao() {
		// Session session = Session.getActiveSession();
		// session.requestNewPublishPermissions(new
		// Session.NewPermissionsRequest(this,
		// Arrays.asList("publish_actions")));
	}

	// private boolean hasPublishPermission() {
	// Session session = Session.getActiveSession();
	// return session != null &&
	// session.getPermissions().contains("publish_actions");
	// }

	// TESTE DE LOGIN COM FBK
	public void onFBLoginClick(View view) {

		// TESTE - APAGAR
		// Log.i("TESTE METODO", "1");

		// openActiveSession(this, true, statusCallback);
	}

	// Session.StatusCallback statusCallback = new Session.StatusCallback() {

	/*
	 * @Override public void call(final Session session, SessionState state,
	 * Exception exception) { Log.i("TESTE METODO", "2"); if(session.isOpened())
	 * { Log.i("TESTE METODO", "3"); Request.executeMeRequestAsync(session, new
	 * Request.GraphUserCallback() {
	 * 
	 * @Override public void onCompleted(GraphUser user, Response response) {
	 * 
	 * Log.i("TESTE METODO", "4");
	 * 
	 * //VERIFICA SE USUARIO J�� EXISTE
	 * 
	 * if(user != null) {
	 * 
	 * //txtUserName.setText(session.getAccessToken()); String gender =
	 * user.getProperty("gender").toString(); String email =
	 * user.getProperty("email").toString(); Log.i("TESTE METODO - USUARIO",
	 * "USUARIO NAO �� NULO");
	 * 
	 * if(UserBO.verifyUser(email) == false){ //TRATANDO DATA SimpleDateFormat
	 * formatador = new SimpleDateFormat("dd/MM/yyyy");
	 * 
	 * String birthday = user.getBirthday().toString(); Calendar dateBirth =
	 * Calendar.getInstance(); Date date = null; try { date =
	 * formatador.parse(birthday); } catch (java.text.ParseException e) { //
	 * TODO Auto-generated catch block e.printStackTrace(); }
	 * dateBirth.setTime(date);
	 * 
	 * //USUARIO - TRUEONE User userT1 = new User();
	 * userT1.setName(user.getName());
	 * userT1.setGender(user.getProperty("gender").toString());
	 * userT1.setPassword(user.getProperty("email").toString());
	 * userT1.setEmail(user.getProperty("email").toString());
	 * userT1.setBirthday(dateBirth);
	 * 
	 * 
	 * //USUARIO - PERFIL FACEBOOK FacebookProfile fb = new FacebookProfile();
	 * //RELATIONSHIP STTAUS if(!(user.getProperty("relationship_status") ==
	 * null)){
	 * fb.setRelationshipStatus(user.getProperty("relationship_status").toString
	 * ()); }else{ fb.setRelationshipStatus(""); } //RELIGION
	 * if(!(user.getProperty("religion") == null)){
	 * fb.setReligion(user.getProperty("religion").toString()); }else{
	 * fb.setReligion(""); } //POLITICAL if(!(user.getProperty("political") ==
	 * null)){ fb.setPolitical(user.getProperty("political").toString()); }else{
	 * fb.setPolitical(""); } //LOCATION
	 * if(!(user.getLocation().getProperty("name") == null)){
	 * fb.setLocation(user.getLocation().getProperty("name").toString()); }else{
	 * fb.setLocation(""); } fb.setFacebookId(user.getId());
	 * fb.setName(user.getName()); fb.setFirstName(user.getFirstName());
	 * fb.setLastName(user.getLastName());
	 * fb.setEmail(user.getProperty("email").toString());
	 * fb.setGender(user.getProperty("gender").toString());
	 * fb.setProfileLink(user.getLink()); fb.setBirthday(dateBirth);
	 * 
	 * //FacebookProfileBO.createFacebookProfile(fb);
	 * 
	 * Log.i("ENTROU NO METODO LOGIN AUTO","AQUI ELE CADASTRA NOVO!!!");
	 * 
	 * Log.i("NOME DO USUARIO", user.getName()); Log.i("ID DO USUARIO",
	 * user.getId()); Log.i("LOCATION DO USUARIO",
	 * user.getLocation().getProperty("name").toString());
	 * Log.i("RELATIONSHIP DO USUARIO", fb.getRelationshipStatus());
	 * Log.i("RELIGION DO USUARIO", fb.getReligion());
	 * Log.i("POLITICAL DO USUARIO", fb.getPolitical());
	 * 
	 * Log.i("ESTA CRIANDO NOVO", "CRIANDO NOVO");
	 * 
	 * //saveUserData(user.getId(), user.getName(), user.getBirthday(),
	 * user.asMap().get("email").toString());
	 * //saveAccessToken(session.getAccessToken());
	 * //getFacebookUserProfilePicture(session.getAccessToken()); }else{
	 * 
	 * //LOGA DIRETO logar2(email); Log.i("teste", email);
	 * Log.i("TESTE LOGAR - ELSE", "ENTROU NO ELSE = LOGAR2(EMAIL)");
	 * 
	 * } }else{ Log.i("TESTE METODO - USUARIO", "USUARIO �� NULO"); } } }); }
	 * Log.i("TESTE METODO - SESSAO", "SESSAO NAO ABERTA"); } };
	 */
	// private static Session openActiveSession(Activity activity,
	// boolean allowLoginUI, SessionCallback statusCallback) {
	// OpenRequest openRequest = new OpenRequest(activity);
	// openRequest.setPermissions(Arrays.asList("user_birthday", "email",
	// "user_religion_politics", "user_relationships",
	// "user_relationship_details"));
	// // openRequest.setCallback(statusCallback);
	// // Session session = new Session.Builder(activity).build();
	// // if(SessionState.CREATED_TOKEN_LOADED.equals(session.getState()) ||
	// // allowLoginUI) {
	// // Session.setActiveSession(session); session.openForRead(openRequest);
	// Log.i("TESTE DO METODOS - RETORNO - OPENREQUEST",
	// String.valueOf(openRequest));
	// Log.i("TESTE DO METODOS - RETORNO", "RETORNA SESSAO");
	// // return session;
	// // }
	// Log.i("TESTE DO METODO - RESTORNO", "RETORNA NULO");
	// return null;

	// // INTENT RECIVER
	// public class ResponseReceiver extends BroadcastReceiver {
	// public static final String ACTION_RESP =
	// "com.mamlambo.intent.action.MESSAGE_PROCESSED";
	//
	// @Override
	// public void onReceive(Context context, Intent intent) {
	// TextView result = (TextView) findViewById(R.id.txt_result);
	// String text = intent
	// .getStringExtra(SimpleIntentService.PARAM_OUT_MSG);
	// result.setText(text);
	//
	// }
	// }
	//
	// // INTENT RECIVER
	// public class ResponseLoginTestReceiver extends BroadcastReceiver {
	// public static final String ACTION_RESP =
	// "com.mamlambo.intent.action.MESSAGE_PROCESSE_LOGIN_RECEIVER";
	//
	// @Override
	// public void onReceive(Context context, Intent intent) {
	// String msgDoLogin = intent
	// .getStringExtra(LoginIntentService.PARAM_OUT_RESPOSTA);
	// Log.i("teste", "RESPOSTA: " + msgDoLogin);
	//
	// }
	// }
	//
	// @Override
	// public boolean onCreateOptionsMenu(Menu menu) {
	// MenuInflater inflater = getMenuInflater();
	// // inflater.inflate(R.menu.main_activi_action, menu);
	//
	// return super.onCreateOptionsMenu(menu);
	//
	// }
	//
	// // @Override
	// // public boolean onOptionsItemSelected(MenuItem item) {
	// // // Take appropriate action for each action item click
	// // switch (item.getItemId()) {
	// // case R.id.action_search:
	// // Log.i("teste","CLIQUEI");
	// // // search action
	// // return true;
	// // default:
	// // return super.onOptionsItemSelected(item);
	// // }
	// // }
	//
	// public void sair(View v) {
	//
	// v.getContext().getSharedPreferences(LoginActivity.PREFS_USER, 0).edit()
	// .clear().commit();
	// Intent i = new Intent(TesteViews.this, LoginActivity.class);
	// startActivity(i);
	// }
	//
	// public void start(View v) {
	// Intent msgIntent = new Intent(TesteViews.this, LoginIntentService.class);
	// msgIntent.putExtra(LoginIntentService.PARAM_IN_USER_EMAIL,
	// "trueone@teste.com");
	// msgIntent.putExtra(LoginIntentService.PARAM_IN_USER_SENHA, "teste");
	// Log.i("START NO SERVICE", "SERVICO STARTADO - ");
	// startService(msgIntent);
	//
	// IntentFilter filter = new IntentFilter(
	// ResponseLoginTestReceiver.ACTION_RESP);
	// filter.addCategory(Intent.CATEGORY_DEFAULT);
	// loginReceiver = new ResponseLoginTestReceiver();
	// registerReceiver(loginReceiver, filter);
	// }
	//
	// public void logar2(String email) {
	//
	// List<User> users = null;
	// User objUser = new User();
	// Map<String, Map<String, Map<String, String>>> key = new HashMap<String,
	// Map<String, Map<String, String>>>();
	// Map<String, Map<String, String>> params = new HashMap<String, Map<String,
	// String>>();
	// Map<String, String> conditions = new HashMap<String, String>();
	//
	// conditions.put("User.email", email);
	// params.put("conditions", conditions);
	// key.put("User", params);
	//
	// users = UserBO.listAllUsers(key);
	// int i = 0;
	// if (users.size() != 0) {
	// Log.i("QUANTIDADE DE REGISTROS DA LISTA",
	// Integer.toString(users.size()));
	// for (User user : users) {
	// if (user.getEmail().equals(email)) {
	// if (i == 0) {
	// Log.i("LOGIN - NOME", user.getName());
	// Log.i("LOGIN - EMAIL", user.getEmail());
	// Log.i("LOG USER - NAME", user.getName());
	// objUser.setName(user.getName());
	// objUser.setPhoto(user.getPhoto());
	// objUser.setId(user.getId());
	// objUser.setAddress(user.getAddress());
	// objUser.setCity(user.getCity());
	// objUser.setComplement(user.getComplement());
	// objUser.setDistrict(user.getDistrict());
	// objUser.setNumber(user.getNumber());
	// objUser.setState(user.getState());
	// objUser.setZip_code(user.getZip_code());
	// i++;
	// }
	// }
	// }
	// boolean logado = true;
	//
	// Intent intent = new Intent(this, MainActivity.class);
	// Bundle parameters = new Bundle();
	//
	// parameters.putBoolean("logado", logado);
	// intent.putExtra("usuario", objUser);
	// intent.putExtras(parameters);
	// startActivity(intent);
	// }
	// }
}
