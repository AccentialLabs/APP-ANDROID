package com.accential.trueone.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

import com.accential.trueone.bean.AditionalAddressesUser;
import com.accential.trueone.bean.Checkout;
import com.accential.trueone.bean.Company;
import com.accential.trueone.bean.Offer;
import com.accential.trueone.bean.PaymentMethod;
import com.accential.trueone.bean.PaymentState;
import com.accential.trueone.bean.User;
import com.accential.trueone.bo.CheckoutBO;
import com.accential.trueone.bo.CompanyBO;
import com.accential.trueone.bo.OfferBO;
import com.accential.trueone.bo.PaymentMethodBO;
import com.accential.trueone.bo.UtilityComponentBO;
import com.accential.trueone.interfaces.ICheckout;
import com.accential.trueone.utils.Base64Util;
import com.accential.trueone.utils.JSONUtils;

/**
 * 
 * @author Matheus Odilon - accentialbrasil
 * 
 */
@SuppressWarnings("all")
public class CheckoutDAO implements ICheckout {

	public List<Checkout> listAllSQL(Map params) {
		List<Checkout> checkouts = null;
		UtilityComponentBO bo = null;

		try {
			bo = new UtilityComponentBO();
			checkouts = new ArrayList<Checkout>();

			JSONArray array = bo.urlRequestToGetData("users", "query", params);

			if (array != null) {
				List<HashMap> objs = JSONUtils.toList(array);

				Checkout checkout = null;
				Company company = null;
				Offer offer = null;
				PaymentMethod method = null;

				Map values = null;
				Map compValues = null;
				Map offerValues = null;
				Map payValues = null;

				SimpleDateFormat sdf = new SimpleDateFormat(
						"dd/MM/yyyy HH:mm:ss");

				if (!objs.isEmpty()) {
					for (HashMap obj : objs) {
						values = JSONUtils.toMap(new JSONObject((Map) obj
								.get("checkouts")));

						checkout = new Checkout();

						checkout.setId(Integer.parseInt((String) values
								.get("id")));
						checkout.setShippingType(String.valueOf(values
								.get("shipping_type")));
						checkout.setCity(String.valueOf(values.get("city")));
						checkout.setUnitValue(Float.parseFloat((String) values
								.get("unit_value")));
						checkout.setTotalValue(Float.parseFloat((String) values
								.get("total_value")));
						checkout.setAmount(Integer.parseInt((String) values
								.get("amount")));
						checkout.setShippingValue(Double
								.parseDouble((String) values
										.get("shipping_value")));
						checkout.setDeliveryTime(Integer
								.parseInt((String) values.get("delivery_time")));
						checkout.setMetrics(String.valueOf(values
								.get("metrics")));
						checkout.setAddress(String.valueOf(values.get("adress")));
						checkout.setZipCode(String.valueOf(values
								.get("zip_code")));
						checkout.setState(String.valueOf(values.get("state")));
						checkout.setDistrict(String.valueOf(values
								.get("district")));
						checkout.setNumber(String.valueOf(values.get("number")));
						checkout.setComplement(String.valueOf(values
								.get("complement")));
						checkout.setTransactionMoipCode(String.valueOf(values
								.get("transaction_moip_code")));
						//
						checkout.setPaymentState(PaymentState.getPaymentState(Integer
								.parseInt(String.valueOf(values
										.get("payment_state_id")))));
						//

						checkout.setInstallment(Integer
								.parseInt((String) values.get("installment")));
						checkout.setPaymentState(PaymentState
								.getPaymentState(Integer
										.parseInt((String) values
												.get("payment_state_id"))));

						Calendar dateTime = Calendar.getInstance();
						String stringData = String.valueOf(values.get("date"))
								.replace("-", "/");
						Date date = new Date(stringData);
						dateTime.setTime(date);

						checkout.setDateTime(dateTime);

						// company
						compValues = JSONUtils.toMap(new JSONObject((Map) obj
								.get("companies")));

						company = new Company();
						company.setId(Integer.parseInt((String) compValues
								.get("id")));
						company.setCorporate_name(String.valueOf(compValues
								.get("corporate_name")));
						company.setFancy_name(String.valueOf(compValues
								.get("fancy_name")));
						company.setDescription(String.valueOf(compValues
								.get("description")));
						company.setSite_url(String.valueOf(compValues
								.get("site_url")));
						company.setCategory_id(Integer
								.parseInt((String) compValues
										.get("category_id")));
						company.setSub_category_id(Integer
								.parseInt((String) compValues
										.get("sub_category_id")));

						company.setCnpj(String.valueOf(compValues.get("cnpj")));
						company.setEmail(String.valueOf(compValues.get("email")));
						company.setPassword(String.valueOf(compValues
								.get("password")));
						company.setPhone(String.valueOf(compValues.get("phone")));
						company.setPhone_2(String.valueOf(compValues
								.get("phone_2")));
						company.setAddress(String.valueOf(compValues
								.get("address")));
						// company.setComplement(String.valueOf(values.get("complement")));
						company.setCity(String.valueOf(compValues.get("city")));
						company.setState(String.valueOf(compValues.get("state")));
						company.setDistrict(String.valueOf(compValues
								.get("district")));
						company.setNumber(String.valueOf(compValues
								.get("number")));
						company.setZip_code(String.valueOf(compValues
								.get("zip_code")));
						company.setResponsible_name(String.valueOf(compValues
								.get("responsible_name")));
						company.setResponsible_cpf(String.valueOf(compValues
								.get("responsible_cpf")));
						company.setResponsible_email(String.valueOf(compValues
								.get("responsible_email")));
						company.setResponsible_phone(String.valueOf(compValues
								.get("responsible_phone")));
						company.setResponsible_phone_2(String
								.valueOf(compValues.get("responsible_phone_2")));
						company.setResponsible_cel_phone(String
								.valueOf(compValues
										.get("responsible_cel_phone")));
						company.setLogo(String.valueOf(compValues.get("logo")));
						company.setStatus(String.valueOf(compValues
								.get("status")));
						company.setLogin_moip(String.valueOf(compValues
								.get("login_moip")));
						company.setRegister(String.valueOf(compValues
								.get("register")));

						// offer
						offerValues = JSONUtils.toMap(new JSONObject((Map) obj
								.get("offers")));

						offer = new Offer();

						// PARSE TO CALENDAR - ENDS_AT
						Calendar ends = Calendar.getInstance();
						String stringData2 = String.valueOf(
								offerValues.get("ends_at")).replace("-", "/");
						Date endsDate = new Date(stringData2);
						ends.setTime(endsDate);

						offer.setId(Integer.parseInt((String) offerValues
								.get("id")));
						offer.setTitle(String.valueOf(offerValues.get("title")));
						offer.setResume(String.valueOf(offerValues
								.get("resume")));
						offer.setDescription(String.valueOf(offerValues
								.get("description")));
						offer.setSpecification(String.valueOf(offerValues
								.get("specification")));
						offer.setValue(Float.valueOf((String) offerValues
								.get("value")));
						offer.setPercentageDiscount(Integer
								.valueOf((String) offerValues
										.get("percentage_discount")));
						offer.setWeight(Float.valueOf((String) offerValues
								.get("weight")));
						offer.setAmountAllowed(Integer
								.valueOf((String) offerValues
										.get("amount_allowed")));

						// parse to calendar - BEGINS_AT
						Calendar begins = Calendar.getInstance();
						String stringdata = String.valueOf(
								offerValues.get("begins_at")).replace("-", "/");
						Date begin = new Date(stringdata);
						String sting = sdf.format(begin);
						begins.setTime(begin);

						offer.setBeginsAt(begins);
						offer.setEndsAt(ends);
						offer.setPhoto(String.valueOf(offerValues.get("photo")));
						offer.setMetrics(String.valueOf(offerValues
								.get("metrics")));
						offer.setParcels(String.valueOf(offerValues
								.get("parcels")));
						offer.setParcelsOffImpost(String.valueOf(offerValues
								.get("parcels_off_impost")));
						offer.setPublicStr(String.valueOf(offerValues
								.get("public")));
						offer.setStatus(String.valueOf(offerValues
								.get("status")));
						offer.setCompany(company);

						// metodo de pagamento
						payValues = JSONUtils.toMap(new JSONObject((Map) obj
								.get("payment_methods")));

						method = new PaymentMethod();
						method.setId(Integer.parseInt((String) payValues
								.get("id")));
						method.setType(String.valueOf(payValues.get("type")));
						method.setName(String.valueOf(payValues.get("name")));
						method.setLastStatus(String.valueOf(payValues
								.get("last_status")));
						method.setStatus(String.valueOf(payValues.get("status")));

						// setando os objs em checkout
						checkout.setCompany(company);
						checkout.setOffer(offer);
						checkout.setMethod(method);

						checkouts.add(checkout);
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return checkouts;
	}

	@Override
	public List<Checkout> listCheckouts(Map params) {

		List<Checkout> checkouts = null;
		UtilityComponentBO bo = null;

		try {
			bo = new UtilityComponentBO();
			checkouts = new ArrayList<Checkout>();

			JSONArray array = bo.urlRequestToGetData("payments", "all", params);

			if (array != null) {
				List<HashMap> objs = JSONUtils.toList(array);

				Checkout checkout = null;
				Map values = null;

				Log.e("OBJS", "CHECK IMPRIME OBJS: " + String.valueOf(objs));

				if (!objs.isEmpty()) {
					for (HashMap obj : objs) {
						values = JSONUtils.toMap(new JSONObject((Map) obj
								.get("Checkout")));

						checkout = new Checkout();

						checkout.setId(Integer.parseInt((String) values
								.get("id")));
						checkout.setShippingType(String.valueOf(values
								.get("shipping_type")));
						checkout.setCity(String.valueOf(values.get("city")));
						checkout.setUnitValue(Float.parseFloat((String) values
								.get("unit_value")));
						checkout.setTotalValue(Float.parseFloat((String) values
								.get("total_value")));
						checkout.setAmount(Integer.parseInt((String) values
								.get("amount")));
						checkout.setShippingValue(Double
								.parseDouble((String) values
										.get("shipping_value")));
						checkout.setDeliveryTime(Integer
								.parseInt((String) values.get("delivery_time")));
						checkout.setMetrics(String.valueOf(values
								.get("metrics")));
						checkout.setAddress(String.valueOf(values.get("adress")));
						checkout.setZipCode(String.valueOf(values
								.get("zip_code")));
						checkout.setState(String.valueOf(values.get("state")));
						checkout.setDistrict(String.valueOf(values
								.get("district")));
						checkout.setNumber(String.valueOf(values.get("number")));
						checkout.setComplement(String.valueOf(values
								.get("complement")));
						checkout.setTransactionMoipCode(String.valueOf(values
								.get("transaction_moip_code")));
						//
						checkout.setPaymentState(PaymentState.getPaymentState(Integer
								.parseInt(String.valueOf(values
										.get("payment_state_id")))));
						//

						checkout.setInstallment(Integer
								.parseInt((String) values.get("installment")));
						checkout.setPaymentState(PaymentState
								.getPaymentState(Integer
										.parseInt((String) values
												.get("payment_state_id"))));

						Calendar dateTime = Calendar.getInstance();
						String stringData = String.valueOf(values.get("date"))
								.replace("-", "/");
						Date date = new Date(stringData);
						dateTime.setTime(date);

						checkout.setDateTime(dateTime);

						checkouts.add(checkout);
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return checkouts;
	}

	@Override
	public List<Checkout> listAllCheckouts(Map params) {
		AsyncTask<Map, Void, List<Checkout>> async = new AsyncTask<Map, Void, List<Checkout>>() {
			@Override
			protected List<Checkout> doInBackground(Map... params) {

				List<Checkout> checkouts = CheckoutBO.listCheckouts(params[0]);

				return checkouts;
			}
		};
		try {
			return async.execute(params).get();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public int returnsObejectId(Map params, String atributo) {
		int id = 0;
		UtilityComponentBO bo = null;

		try {
			bo = new UtilityComponentBO();
			JSONArray array = bo.urlRequestToGetData("payments", "all", params);

			if (array != null) {
				List<HashMap> objs = JSONUtils.toList(array);
				Map values = null;
				if (!objs.isEmpty()) {
					for (HashMap obj : objs) {
						values = JSONUtils.toMap(new JSONObject((Map) obj
								.get("Checkout")));
						id = Integer.parseInt((String) values.get(atributo));
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return id;
	}

	/**
	 * @author Matheus Odilon - accentialbrasil
	 * @param userId
	 * @return List<Checkout>
	 */
	public List<Checkout> returnsObjCheckout(int userId) {

		Offer offer = new Offer();
		Company comp = new Company();
		PaymentMethod method = new PaymentMethod();

		Map<String, Map<String, Map<String, String>>> key = new HashMap<String, Map<String, Map<String, String>>>();
		Map<String, Map<String, String>> params = new HashMap<String, Map<String, String>>();
		Map<String, String> conditions = new HashMap<String, String>();

		conditions.put("Checkout.user_id", String.valueOf(userId));
		params.put("conditions", conditions);
		key.put("Checkout", params);

		List<Checkout> checkouts = CheckoutBO.listAllCheckouts(key);
		List<Checkout> checks = new ArrayList<Checkout>();

		for (Checkout check : checkouts) {

			if (check.getTotalValue() != 0) {

				Log.i("ID CHECK", String.valueOf(check.getId()));
				offer = OfferBO.searchOfferByCheckoutId(check.getId());
				Log.i("PESQUISA OFERTA", "OK");
				comp = CompanyBO.searchCompByCheckoutId(check.getId());
				Log.i("PESQUISA COMPANY", "OK");
				method = PaymentMethodBO.searchPMethodByCheckoutId(check
						.getId());
				Log.i("PESQUISA PAYMENT METHOD", "OK");

				// Log.i("CHECK - NOME DA OFFER", offer.getTitle());

				Checkout checkout = new Checkout();

				checkout.setId(check.getId());
				checkout.setShippingType(check.getShippingType());
				checkout.setCity(check.getCity());
				checkout.setUnitValue(check.getUnitValue());
				checkout.setTotalValue(check.getTotalValue());
				checkout.setAmount(check.getAmount());
				checkout.setShippingValue(check.getShippingValue());
				checkout.setDeliveryTime(check.getDeliveryTime());
				checkout.setMetrics(check.getMetrics());
				checkout.setAddress(check.getAddress());
				checkout.setZipCode(check.getZipCode());
				checkout.setState(check.getState());
				checkout.setDistrict(check.getDistrict());
				checkout.setNumber(check.getNumber());
				checkout.setComplement(check.getComplement());
				checkout.setTransactionMoipCode(check.getTransactionMoipCode());
				checkout.setInstallment(check.getInstallment());
				checkout.setPaymentState(check.getPaymentState());
				checkout.setDateTime(check.getDateTime());

				checkout.setOffer(offer);
				checkout.setCompany(comp);
				checkout.setMethod(method);

				checks.add(checkout);
			}
		}
		return checks;
	}

	/**
	 * ATENCAO: ESSE METODO SO DEVE SER EXECUTADO EM UMA THREAD DIFERENTE DA
	 * PRINCIPAL
	 * 
	 * @author Matheus Odilon - accentialbrasil
	 * @param params
	 */
	public List<Checkout> saveCheckout(Map params) {

		UtilityComponentBO bo = null;
		List<Checkout> checks = null;

		try {
			bo = new UtilityComponentBO();
			checks = new ArrayList<Checkout>();

			JSONArray array = bo.urlRequestToSaveDataCheck("users", "all",
					params);

			Log.i("IMPRIMINDO ARRAY DE RETORNO DO SAVE DE CHECKOUTS",
					String.valueOf(array));

			if (array != null) {
				List<HashMap> objs = JSONUtils.toList(array);

				Checkout check = null;
				Map values = null;

				if (!objs.isEmpty()) {
					for (HashMap obj : objs) {
						values = JSONUtils.toMap(new JSONObject((Map) obj
								.get("Checkout")));
						check = new Checkout();
						check.setId(Integer.parseInt((String) values.get("id")));

						checks.add(check);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return checks;
	}

	public String saveCheckout2(Map params) {

		UtilityComponentBO bo = null;
		List<Checkout> checks = null;
		String data = null;

		try {
			bo = new UtilityComponentBO();
			checks = new ArrayList<Checkout>();

			data = bo.urlRequestToSaveDataCheck2("users", "all", params);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}

	public void createCheckout(Checkout check) {

		AsyncTask<Map, Void, List<Checkout>> async = new AsyncTask<Map, Void, List<Checkout>>() {

			@Override
			protected List<Checkout> doInBackground(Map... params) {

				CheckoutDAO dao = new CheckoutDAO();
				List<Checkout> checks = dao.saveCheckout(params[0]);
				return checks;
			}

		};

		try {
			Map<String, Map> arrayParams = new HashMap<String, Map>();
			Map<String, String> datas = new HashMap<String, String>();

			Calendar calendar = Calendar.getInstance();
			String data = String.valueOf(calendar.get(Calendar.YEAR) + "-"
					+ (calendar.get(Calendar.MONTH) + 1) + "-"
					+ calendar.get(Calendar.DAY_OF_MONTH));

			datas.put("user_id", String.valueOf(check.getUser().getId()));
			datas.put("company_id", String.valueOf(check.getCompany().getId()));
			datas.put("payment_method_id", "3");
			datas.put("offer_id", String.valueOf(check.getOffer().getId()));
			datas.put("payment_state_id", "14");
			datas.put("unit_value", "0");
			datas.put("total_value", "0");
			datas.put("amount", "0");
			datas.put("shipping_value", "0");
			datas.put("shipping_type", "CORREIOS");
			datas.put("delivery_time", "0");
			datas.put("metrics", "");
			datas.put("address", "");
			datas.put("city", "");
			datas.put("zip_code", "");
			datas.put("state", "");
			datas.put("district", "");
			datas.put("number", "");
			datas.put("complement", "");
			datas.put("date", data);
			datas.put("transaction_moip_code", "0");
			datas.put("installment", "0");

			arrayParams.put("Checkout", datas);

			async.execute(arrayParams).get();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Map<String, String> calculateShipping(Map params) {

		UtilityComponentBO bo = null;
		Map<String, String> myMap = null;

		try {
			myMap = new HashMap<String, String>();
			bo = new UtilityComponentBO();
			myMap = bo.urlToCalcuateShippingValue(params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return myMap;
	}

	/**
	 * CALCULA O FRETE E RETORNA VALOR E QUANTIDADE DE DIAS DA ENTREGA
	 * 
	 * @author Matheus Odilon - accentialbrasil
	 * @param String
	 *            cepDestino
	 * @param String
	 *            cepOrigem
	 * @param String
	 *            weight
	 * @return Map
	 */
	public Map<String, String> calculateShippingValue(String cepDestino,
			String cepOrigem, String weight) {

		AsyncTask<Map, Void, Map> async = new AsyncTask<Map, Void, Map>() {
			@Override
			protected Map doInBackground(Map... params) {
				CheckoutDAO dao = new CheckoutDAO();
				return dao.calculateShipping(params[0]);
			}
		};
		try {
			Map<String, String> datas2 = new HashMap<String, String>();

			datas2.put("sCepOrigem", cepOrigem);
			datas2.put("sCepDestino", cepDestino);
			datas2.put("nVlPeso", weight);
			// datas2.put("&nCdServico=", "41106");

			return async.execute(datas2).get();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	public String makePayment(User user, Checkout check, Offer offer,
			Company comp, AditionalAddressesUser aa) {

		// teste
		Log.i("IMPRIMINDO INFORMACOES DE CHECKOUT",
				"**************************************************");
		Log.i("IMPRIMINDO INFORMACOES DE CHECKOUT",
				String.valueOf(check.getId()));
		Log.i("IMPRIMINDO INFORMACOES DE CHECKOUT",
				String.valueOf(check.getTotalValue()));
		Log.i("IMPRIMINDO INFORMACOES DE CHECKOUT", comp.getFancy_name());
		Log.i("IMPRIMINDO INFORMACOES DE CHECKOUT", offer.getParcels());
		Log.i("IMPRIMINDO INFORMACOES DE CHECKOUT", offer.getParcelsOffImpost());
		Log.i("IMPRIMINDO INFORMACOES DE CHECKOUT", comp.getLogin_moip());
		Log.i("IMPRIMINDO INFORMACOES DE CHECKOUT",
				"* * * * * * * * * * * * * * * * * * * * * * * * * * *");
		Log.i("IMPRIMINDO INFORMACOES DE CHECKOUT", user.getName());
		Log.i("IMPRIMINDO INFORMACOES DE CHECKOUT", user.getEmail());
		Log.i("IMPRIMINDO INFORMACOES DE CHECKOUT",
				String.valueOf(user.getId()));
		Log.i("IMPRIMINDO INFORMACOES DE CHECKOUT",
				"* * * * * * * * * * * * * * * * * * * * * * * * * * *");
		Log.i("IMPRIMINDO INFORMACOES DE CHECKOUT", aa.getAddress());
		Log.i("IMPRIMINDO INFORMACOES DE CHECKOUT", aa.getNumber());
		Log.i("IMPRIMINDO INFORMACOES DE CHECKOUT", aa.getComplement());
		Log.i("IMPRIMINDO INFORMACOES DE CHECKOUT", aa.getCity());
		Log.i("IMPRIMINDO INFORMACOES DE CHECKOUT", aa.getDistrict());
		Log.i("IMPRIMINDO INFORMACOES DE CHECKOUT", aa.getState());
		Log.i("IMPRIMINDO INFORMACOES DE CHECKOUT", aa.getZipCode());
		Log.i("IMPRIMINDO INFORMACOES DE CHECKOUT",
				"**************************************************");

		String url = null;

		Map<String, String> transaction = new HashMap<String, String>();
		transaction.put("uniqueId", String.valueOf(check.getId()));
		transaction.put("value", String.valueOf(check.getTotalValue()));
		transaction.put("reason", comp.getFancy_name());
		transaction.put("paymentMethod", "CreditCard");
		transaction.put("isParcelsOn", offer.getParcels());
		transaction.put("isParcelsWithTax", offer.getParcelsOffImpost());
		transaction.put("companyLoginForMOIPSplit", comp.getLogin_moip());

		Map<String, Object> payer = new HashMap<String, Object>();
		payer.put("name", user.getName());
		payer.put("email", user.getEmail());
		payer.put("payerId", String.valueOf(user.getId()));

		Map<String, String> billingAddress = new HashMap<String, String>();
		billingAddress.put("address", aa.getAddress());
		billingAddress.put("number", aa.getNumber());
		billingAddress.put("complement", aa.getComplement());
		billingAddress.put("city", aa.getCity());
		billingAddress.put("neighborhood", aa.getDistrict());
		billingAddress.put("state", aa.getState());
		billingAddress.put("country", "BRA");
		billingAddress.put("zipCode", aa.getZipCode());
		billingAddress.put("phone", "(11)00000000");

		payer.put("billingAddress", billingAddress);

		Map<String, Map> paramsToSend = new HashMap<String, Map>();
		paramsToSend.put("transaction", transaction);
		paramsToSend.put("payer", payer);

		// SALVANDO ENDERE��O E INFORMACOES DO CHECKOUT
		// updateUserAddress(aa, check);

		// CODIFICANDO OS PARAMETROS
		UtilityComponentBO bo = new UtilityComponentBO();
		String token1 = bo.generateSecureToken();

		Base64Util base1 = new Base64Util();

		String json = JSONUtils.encodeJSON(paramsToSend);
		String based1 = base1.encode(json.getBytes());

		// VERIFICA QUAL �� O DEVICE QUE EST�� SENDO USADO
		Log.i("TESTE - TOKEN", token1);
		Log.i("TESTE - JSON", String.valueOf(json));
		Log.i("TESTE - BASED1", based1);
		/*
		url = "https://secure.trueone.com.br/t1mobilecore/iphone/payments/checkout/"
				+ based1 + "/" + token1;*/
		
		url = "http://acclabs.accential.com.br/adventa/mobile_api/iphone/payments/checkout/"
				+ based1 + "/" + token1;
		Log.i("URL", url);
		return url;
	}

	/**
	 * ATUALIZA ENDERECO, VALOR, DIAS DA ENTREGA, VALOR UNITARIO, QUANTIDA E
	 * METRICASA DO CHECKOUT
	 * 
	 * @param aa
	 * @param check
	 */
	public void updateUserAddress(AditionalAddressesUser aa, Checkout check) {

		Map<String, String> dados = new HashMap<String, String>();
		Map<String, Map> params = new HashMap<String, Map>();

		dados.put("id", String.valueOf(check.getId()));
		dados.put("address", aa.getAddress());
		dados.put("number", aa.getNumber());
		dados.put("complement", aa.getComplement());
		dados.put("district", aa.getComplement());
		dados.put("city", aa.getCity());
		dados.put("state", aa.getState());
		dados.put("zip_code", aa.getZipCode());
		dados.put("delivery_time", String.valueOf(check.getDeliveryTime()));
		dados.put("unit_value", String.valueOf(check.getUnitValue()));
		dados.put("amount", String.valueOf(check.getAmount()));
		dados.put("shipping_value", String.valueOf(check.getShippingValue()));

		// ATUALIZA INFOS
		dados.put("amount", String.valueOf(check.getAmount()));
		dados.put("total_value", String.valueOf(check.getTotalValue()));
		dados.put("unit_value", String.valueOf(check.getUnitValue()));
		dados.put("shipping_value", String.valueOf(check.getShippingValue()));
		dados.put("delivery_time", String.valueOf(check.getDeliveryTime()));
		dados.put("metrics", check.getMetrics());

		params.put("Checkout", dados);

		AsyncTask<Map, Void, List<Checkout>> async = new AsyncTask<Map, Void, List<Checkout>>() {
			@Override
			protected List<Checkout> doInBackground(Map... params) {
				CheckoutDAO dao = new CheckoutDAO();
				return dao.saveCheckout(params[0]);
			}
		};
		try {
			List<Checkout> checks = async.execute(params).get();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void updateCheckout(Checkout check) {

		Map<String, String> dados = new HashMap<String, String>();
		Map<String, Map> params = new HashMap<String, Map>();

		dados.put("id", String.valueOf(check.getId()));
		dados.put("amount", String.valueOf(check.getAmount()));
		dados.put("total_value", String.valueOf(check.getTotalValue()));
		dados.put("shipping_value", String.valueOf(check.getShippingValue()));
		dados.put("delivery_time", String.valueOf(check.getDeliveryTime()));
		dados.put("metrics", check.getMetrics());

		params.put("Checkout", dados);

	}

	// METODO PARA TESTE
	public List<Checkout> createCheckout2(Checkout check) {
		List<Checkout> checks = null;
		AsyncTask<Map, Void, List<Checkout>> async = new AsyncTask<Map, Void, List<Checkout>>() {

			@Override
			protected List<Checkout> doInBackground(Map... params) {
				CheckoutDAO dao = new CheckoutDAO();
				List<Checkout> checks = dao.saveCheckout(params[0]);
				return checks;
			}
		};

		try {
			Map<String, Map> arrayParams = new HashMap<String, Map>();
			Map<String, String> datas = new HashMap<String, String>();

			Calendar calendar = Calendar.getInstance();
			String data = String.valueOf(calendar.get(Calendar.YEAR) + "-"
					+ (calendar.get(Calendar.MONTH) + 1) + "-"
					+ calendar.get(Calendar.DAY_OF_MONTH));

			datas.put("user_id", String.valueOf(check.getUser().getId()));
			datas.put("company_id", String.valueOf(check.getCompany().getId()));
			datas.put("payment_method_id", "3");
			datas.put("offer_id", String.valueOf(check.getOffer().getId()));
			datas.put("payment_state_id", "14");
			datas.put("unit_value", "0");
			datas.put("total_value", "0");
			datas.put("amount", "0");
			datas.put("shipping_value", "0");
			datas.put("shipping_type", "CORREIOS");
			datas.put("delivery_time", "0");
			datas.put("metrics", "");
			datas.put("address", "");
			datas.put("city", "");
			datas.put("zip_code", "");
			datas.put("state", "");
			datas.put("district", "");
			datas.put("number", "");
			datas.put("complement", "");
			datas.put("date", data);
			datas.put("transaction_moip_code", "0");
			datas.put("installment", "0");

			arrayParams.put("Checkout", datas);

			checks = new ArrayList<Checkout>();

			return async.execute(arrayParams).get();

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String createCheckoutReturnDATA(Checkout check) {
		AsyncTask<Map, Void, String> async = new AsyncTask<Map, Void, String>() {

			@Override
			protected String doInBackground(Map... params) {

				CheckoutDAO dao = new CheckoutDAO();
				String data = dao.saveCheckout2(params[0]);
				return data;
			}

		};

		try {
			Map<String, Map> arrayParams = new HashMap<String, Map>();
			Map<String, String> datas = new HashMap<String, String>();

			Calendar calendar = Calendar.getInstance();
			String data = String.valueOf(calendar.get(Calendar.YEAR) + "-"
					+ (calendar.get(Calendar.MONTH) + 1) + "-"
					+ calendar.get(Calendar.DAY_OF_MONTH));

			datas.put("user_id", String.valueOf(check.getUser().getId()));
			datas.put("company_id", String.valueOf(check.getCompany().getId()));
			datas.put("payment_method_id", "3");
			datas.put("offer_id", String.valueOf(check.getOffer().getId()));
			datas.put("payment_state_id", "14");
			datas.put("unit_value", "0");
			datas.put("total_value", "0");
			datas.put("amount", "0");
			datas.put("shipping_value", "0");
			datas.put("shipping_type", "CORREIOS");
			datas.put("delivery_time", "0");
			datas.put("metrics", "");
			datas.put("address", "");
			datas.put("city", "");
			datas.put("zip_code", "");
			datas.put("state", "");
			datas.put("district", "");
			datas.put("number", "");
			datas.put("complement", "");
			datas.put("date", data);
			datas.put("transaction_moip_code", "0");
			datas.put("installment", "0");

			arrayParams.put("Checkout", datas);

			String dat = async.execute(arrayParams).get();

			String stringBase = dat;
			Base64Util bas64 = new Base64Util();
			byte[] chars = bas64.decode(stringBase);
			String stringDecoded;
			stringDecoded = new String(chars, "UTF-8");
			Log.i("TESTE DE DECODE", stringDecoded);

			String sub = stringDecoded.substring(46, 50);
			int id = Integer.parseInt(sub);
			Log.i("TESTE DE ENCODE - SUNSTRING", String.valueOf(id));

			return "TESTE SEM EXCEPTION";

		} catch (Exception e) {
			e.printStackTrace();
			return "TESTE COM EXCEPTION";
		}
	}

	// teste salva checkout return int
	public int saveCheckoutReturnInt(Map params) {

		UtilityComponentBO bo = null;
		List<Checkout> checks = null;
		String data = null;
		int id = 0;

		try {
			bo = new UtilityComponentBO();
			checks = new ArrayList<Checkout>();

			id = bo.urlRequestToSaveDataCheckRetornaIntId("users", "all",
					params);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return id;
	}

	public List<Checkout> listAllCheckoutsByUser(int userId) {

		Map<String, Map<String, Map<String, String>>> key2 = new HashMap<String, Map<String, Map<String, String>>>();
		Map<String, Map<String, String>> params2 = new HashMap<String, Map<String, String>>();
		Map<String, String> conditions2 = new HashMap<String, String>();

		// conditions2.put("Checkout.total_value >", "0");
		conditions2.put("Checkout.user_id", String.valueOf(userId));
		params2.put("conditions", conditions2);
		key2.put("Checkout", params2);

		List<Checkout> checks = CheckoutBO.listAllCheckouts(key2);
		Checkout returnCheck = checks.get((checks.size() - 1));

		return checks;
	}

	/**
	 * Versao 2.0 - Descomplicado, retorna Map com valor e prazo
	 * 
	 * @param cepOrigem
	 * @param cepDestino
	 * @param peso
	 * @return Map("Valor")("PrazoEntrega")
	 */
	public Map calculaFrete(final String cepOrigem, final String cepDestino,
			final String peso) {

		AsyncTask<Void, Void, Map> async = new AsyncTask<Void, Void, Map>() {

			@Override
			protected Map doInBackground(Void... params) {
				UtilityComponentBO bo = new UtilityComponentBO();
				Map retorno = bo.calculaAndFrete(cepOrigem, cepDestino, peso);
				return retorno;
			}
		};

		try {
			return async.execute().get();
		} catch (Exception e) {
			Map error = new HashMap();
			error.put("Valor", "ERROR_DAO");
			return error;
		}

	}

	public List<Checkout> getMyAllCheckouts(int userId) {

		Log.e("", "PESQUISANDO COMPRAS");
		Map<String, Map<String, Map<String, String>>> key2 = new HashMap<String, Map<String, Map<String, String>>>();
		Map<String, Map<String, String>> params2 = new HashMap<String, Map<String, String>>();
		Map<String, String> conditions2 = new HashMap<String, String>();

		conditions2.put("Checkout.total_value >", "0");
		conditions2.put("Checkout.user_id", String.valueOf(userId));
		params2.put("conditions", conditions2);
		key2.put("Checkout", params2);

		List<Checkout> checks = CheckoutBO.listAllCheckouts(key2);
		List<Checkout> returnList = new ArrayList<Checkout>();
		for (Checkout checkout : checks) {
			Offer offer = new Offer();
			Company comp = new Company();
			offer = OfferBO.searchOfferByCheckoutId(checkout.getId());
			comp = CompanyBO.searchCompByCheckoutId(checkout.getId());

			checkout.setCompany(comp);
			checkout.setOffer(offer);

			returnList.add(checkout);
		}

		return returnList;
	}

	public List<Checkout> getCheckouts(int userId) {

		AsyncTask<Map, Void, List<Checkout>> async = new AsyncTask<Map, Void, List<Checkout>>() {
			@Override
			protected List<Checkout> doInBackground(Map... params) {

				return CheckoutBO.listCheckouts(params[0]);
			}
		};

		try {
			Map key = new HashMap();
			Map<String, Map<String, String>> params = new HashMap<String, Map<String, String>>();
			Map<String, String> conditions = new HashMap<String, String>();

			key.put("Offer", params);
			conditions.put("Checkout.total_value >", "0");
			conditions.put("Checkout.user_id", String.valueOf(userId));
			params.put("conditions", conditions);
			key.put("Checkout", params);

			return async.execute(key).get();

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * CASO NÃO TENHA EM MÃOS O EER DIAGRAM DO SISTEMA COMPLETO: NÃO MEXA NESSE
	 * METODO!!!
	 */
	public List<Checkout> listByUser(int userId) {

		AsyncTask<Map, Void, List<Checkout>> async = new AsyncTask<Map, Void, List<Checkout>>() {
			@Override
			protected List<Checkout> doInBackground(Map... params) {
				CheckoutDAO dao = new CheckoutDAO();
				return dao.listAllSQL(params[0]);
			}
		};

		try {

			Map param = new HashMap();
			Map query = new HashMap();

			/**
			 * o codigo SQL faz um JOIN das tabelas checkouts, companies, offers
			 * e payment_methods isso nos traz o objeto Checkout completo, não
			 * necessitando fazer outras consultas
			 */
			query.put(
					"query",
					"select * from checkouts INNER JOIN companies ON companies.id = checkouts.company_id "
							+ " INNER JOIN offers ON offers.id = checkouts.offer_id "
							+ " INNER JOIN payment_methods ON payment_methods.id = checkouts.payment_method_id "
							+ " where checkouts.user_id = "
							+ String.valueOf(userId)
							+ " and checkouts.total_value > 0;");
			param.put("User", query);

			List<Checkout> checks = async.execute(param).get();

			return checks;

		} catch (Exception e) {
			Log.e("Error CheckoutDAO",
					"Erro na listagem de checkouts. Confira os parametros e tente novamente. CheckoutDAO - listByUser");
			e.printStackTrace();
			return null;
		}

	}
}
