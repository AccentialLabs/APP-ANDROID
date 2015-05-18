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

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.util.Log;

import com.accential.trueone.bean.Company;
import com.accential.trueone.bean.Offer;
import com.accential.trueone.bo.CompanyBO;
import com.accential.trueone.bo.OfferBO;
import com.accential.trueone.bo.UtilityComponentBO;
import com.accential.trueone.interfaces.IOffer;
import com.accential.trueone.utils.JSONUtils;

@SuppressWarnings("all")
public class OfferDAO implements IOffer {
	/**
	 * @author Henrique Alle
	 * @param Map
	 * @return List<Offer>
	 */
	@SuppressLint("SimpleDateFormat")
	@Override
	public List<Offer> listOffers(Map params) {

		List<Offer> offers = null;
		UtilityComponentBO bo = null;

		try {
			bo = new UtilityComponentBO();
			offers = new ArrayList<Offer>();

			JSONArray array = bo.urlRequestToGetData("offers", "all", params);

			if (array != null) {
				List<HashMap> objs = JSONUtils.toList(array);

				Offer offer = null;
				Map values = null;

				SimpleDateFormat sdf = new SimpleDateFormat(
						"dd/MM/yyyy HH:mm:ss");

				// para extrair o metodo alt+shift+M
				if (!objs.isEmpty()) {
					for (HashMap obj : objs) {
						values = JSONUtils.toMap(new JSONObject((Map) obj
								.get("Offer")));
						offer = new Offer();

						// PARSE TO CALENDAR - ENDS_AT
						Calendar ends = Calendar.getInstance();
						String stringData = String.valueOf(
								values.get("ends_at")).replace("-", "/");
						Date endsDate = new Date(stringData);
						ends.setTime(endsDate);

						if (ends.after(Calendar.getInstance())) {

							offer.setId(Integer.parseInt((String) values
									.get("id")));
							offer.setTitle(String.valueOf(values.get("title")));
							offer.setResume(String.valueOf(values.get("resume")));
							offer.setDescription(String.valueOf(values
									.get("description")));
							offer.setSpecification(String.valueOf(values
									.get("specification")));
							offer.setValue(Float.valueOf((String) values
									.get("value")));
							offer.setPercentageDiscount(Integer
									.valueOf((String) values
											.get("percentage_discount")));
							offer.setWeight(Float.valueOf((String) values
									.get("weight")));
							offer.setAmountAllowed(Integer
									.valueOf((String) values
											.get("amount_allowed")));

							// parse to calendar - BEGINS_AT
							Calendar begins = Calendar.getInstance();
							String stringdata = String.valueOf(
									values.get("begins_at")).replace("-", "/");
							Date begin = new Date(stringdata);
							String sting = sdf.format(begin);
							begins.setTime(begin);

							offer.setBeginsAt(begins);
							offer.setEndsAt(ends);
							offer.setPhoto(String.valueOf(values.get("photo")));
							offer.setMetrics(String.valueOf(values
									.get("metrics")));
							offer.setParcels(String.valueOf(values
									.get("parcels")));
							offer.setParcelsOffImpost(String.valueOf(values
									.get("parcels_off_impost")));
							offer.setPublicStr(String.valueOf(values
									.get("public")));
							offer.setStatus(String.valueOf(values.get("status")));

							offers.add(offer);
						}
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return offers;
	}

	@SuppressLint("SimpleDateFormat")
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public int countOffer(Map params) {
		int contador_ofertas = 0;
		UtilityComponentBO bo = null;

		try {
			bo = new UtilityComponentBO();
			JSONArray array = bo.urlRequestToGetData("offers", "all", params);

			if (array != null) {

				contador_ofertas = array.length();
				array = null;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return contador_ofertas;
	}

	/**
	 * @author Matheus Odilon - accentialbrasil
	 * @param Map
	 * @return List<Offer>
	 */
	@Override
	public List<Offer> listAllOffers(Map params) {

		AsyncTask<Map, Void, List<Offer>> async = new AsyncTask<Map, Void, List<Offer>>() {

			@Override
			protected List<Offer> doInBackground(Map... params) {
				List<Offer> offers = OfferBO.listOffers(params[0]);

				int contador = OfferBO.count_offer(params[0]);
				Log.i("QUANTIDADE DE REGISTROS - OFFER",
						Integer.toString(contador));

				return offers;
			}
		};

		try {
			return async.execute(params).get();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	public Offer searchOfferById(int offerId) {

		Offer offer = new Offer();

		Log.i("EXECUTOU ", " searchOfferById ");
		Map<String, Map<String, Map<String, String>>> key2 = new HashMap<String, Map<String, Map<String, String>>>();
		Map<String, Map<String, String>> params2 = new HashMap<String, Map<String, String>>();
		Map<String, String> conditions2 = new HashMap<String, String>();

		conditions2.put("Offer.id", String.valueOf(offerId));
		params2.put("conditions", conditions2);
		key2.put("Offer", params2);

		List<Offer> offers = OfferBO.listAllOffersNoFilter(key2);
		Log.i("TAMANHO DA LISTA OFFER SEARCHBYID",
				String.valueOf(offers.size()));

		for (Offer offer2 : offers) {

			if (offer2.getId() == offerId) {
				offer.setId(offer2.getId());
				offer.setTitle(offer2.getTitle());
				offer.setResume(offer2.getResume());
				offer.setDescription(offer2.getDescription());
				offer.setSpecification(offer2.getSpecification());
				offer.setValue(offer2.getValue());
				offer.setPercentageDiscount(offer2.getPercentageDiscount());
				offer.setWeight(offer2.getWeight());
				offer.setAmountAllowed(offer2.getAmountAllowed());

				offer.setEndsAt(offer2.getEndsAt());
				offer.setBeginsAt(offer2.getBeginsAt());

				offer.setPhoto(offer2.getPhoto());
				offer.setMetrics(offer2.getMetrics());
				offer.setParcels(offer2.getParcels());
				offer.setParcelsOffImpost(offer2.getParcelsOffImpost());
				offer.setPublicStr(offer2.getPublicStr());
				offer.setStatus(offer2.getStatus());
			}
		}
		return offer;
	}

	// BUSCAR OFFER
	public Offer searchOfferByCheckoutId(int id) {

		Map<String, Map<String, Map<String, String>>> key = new HashMap<String, Map<String, Map<String, String>>>();
		Map<String, Map<String, String>> params = new HashMap<String, Map<String, String>>();
		Map<String, String> conditions = new HashMap<String, String>();

		conditions.put("Checkout.id", String.valueOf(id));
		params.put("conditions", conditions);
		key.put("Checkout", params);

		AsyncTask<Map, Void, Integer> async = new AsyncTask<Map, Void, Integer>() {
			@Override
			protected Integer doInBackground(Map... params) {
				CheckoutDAO dao = new CheckoutDAO();
				int i = dao.returnsObejectId(params[0], "offer_id");
				Log.i("searchOfferByCheckoutId", String.valueOf(i));
				return i;
			}
		};
		int y = 0;
		try {
			y = async.execute(key).get();
			Log.i("searchOfferByCheckoutId - VALOR DO Y", String.valueOf(y));
			return OfferBO.searchOfferById(y);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// METODOS DE BUSCAR OFFERS SEM FILTROS DE DATAS
	/**
	 * METODO SEM FILTRO DE DATA
	 * 
	 * @param params
	 * @return List<Offer>
	 */
	public List<Offer> listOffersNoFilter(Map params) {

		List<Offer> offers = null;
		UtilityComponentBO bo = null;

		try {
			bo = new UtilityComponentBO();
			offers = new ArrayList<Offer>();

			JSONArray array = bo.urlRequestToGetData("offers", "all", params);

			if (array != null) {
				List<HashMap> objs = JSONUtils.toList(array);

				Offer offer = null;
				Map values = null;

				SimpleDateFormat sdf = new SimpleDateFormat(
						"dd/MM/yyyy HH:mm:ss");

				// para extrair o metodo alt+shift+M
				if (!objs.isEmpty()) {
					for (HashMap obj : objs) {
						values = JSONUtils.toMap(new JSONObject((Map) obj
								.get("Offer")));
						offer = new Offer();

						// PARSE TO CALENDAR - ENDS_AT
						Calendar ends = Calendar.getInstance();
						String stringData = String.valueOf(
								values.get("ends_at")).replace("-", "/");
						Date endsDate = new Date(stringData);
						ends.setTime(endsDate);

						offer.setId(Integer.parseInt((String) values.get("id")));
						offer.setTitle(String.valueOf(values.get("title")));
						offer.setResume(String.valueOf(values.get("resume")));
						offer.setDescription(String.valueOf(values
								.get("description")));
						offer.setSpecification(String.valueOf(values
								.get("specification")));
						offer.setValue(Float.valueOf((String) values
								.get("value")));
						offer.setPercentageDiscount(Integer
								.valueOf((String) values
										.get("percentage_discount")));
						offer.setWeight(Float.valueOf((String) values
								.get("weight")));
						offer.setAmountAllowed(Integer.valueOf((String) values
								.get("amount_allowed")));

						// parse to calendar - BEGINS_AT
						Calendar begins = Calendar.getInstance();
						String stringdata = String.valueOf(
								values.get("begins_at")).replace("-", "/");
						Date begin = new Date(stringdata);
						String sting = sdf.format(begin);
						begins.setTime(begin);

						offer.setBeginsAt(begins);
						offer.setEndsAt(ends);
						offer.setPhoto(String.valueOf(values.get("photo")));
						offer.setMetrics(String.valueOf(values.get("metrics")));
						offer.setParcels(String.valueOf(values.get("parcels")));
						offer.setParcelsOffImpost(String.valueOf(values
								.get("parcels_off_impost")));
						offer.setPublicStr(String.valueOf(values.get("public")));
						offer.setStatus(String.valueOf(values.get("status")));

						offers.add(offer);
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return offers;
	}

	/**
	 * LISTA TODAS AS OFERTAS SEM FILTRO DE DATA (INDEPENDENTE DE TEREM PASSADO
	 * DO PRAZO DE VALIDADE)
	 * 
	 * @param params
	 * @return List<Offer>
	 */
	public List<Offer> listAllOffersNoFilter(Map params) {
		AsyncTask<Map, Void, List<Offer>> async = new AsyncTask<Map, Void, List<Offer>>() {
			@Override
			protected List<Offer> doInBackground(Map... params) {
				List<Offer> offers = OfferBO.listOffersNoFilter(params[0]);

				int contador = OfferBO.count_offer(params[0]);
				Log.i("QUANTIDADE DE REGISTROS - OFFER",
						Integer.toString(contador));

				return offers;
			}
		};
		try {
			return async.execute(params).get();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<Offer> searchOffersByTitle(String title) {

		List<Offer> offers = null;

		Map<String, Map<String, Map<String, String>>> key = new HashMap<String, Map<String, Map<String, String>>>();
		Map<String, Map<String, String>> params = new HashMap<String, Map<String, String>>();
		Map<String, String> conditions = new HashMap<String, String>();

		params.put("conditions", conditions);
		key.put("Offer", params);
		List<Offer> offs = null;

		try {
			offers = OfferBO.listAllOffers(key);
			if (offers != null) {
				offs = new ArrayList<Offer>();

				for (Offer offer : offers) {
					if (offer.getTitle().toLowerCase()
							.contains(title.toLowerCase())) {
						Offer of = new Offer();
						of = offer;
						offs.add(of);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return offs;

	}

	/**
	 * LISTA TODAS AS OFERTAS V��LIDAS DE DETERMINADA COMPANY POR ID DA MESMA
	 * 
	 * @param int
	 * @author Matheus Odilon - accentialbrasil
	 */
	public List<Offer> listOffersByCompany(int companyId) {

		List<Offer> offers = null;

		Map<String, Map<String, Map<String, String>>> key = new HashMap<String, Map<String, Map<String, String>>>();
		Map<String, Map<String, String>> params = new HashMap<String, Map<String, String>>();
		Map<String, String> conditions = new HashMap<String, String>();

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();

		conditions.put("Offer.ends_at >",
				String.valueOf(formatter.format(date)));
		conditions.put("Offer.status", "ACTIVE");
		conditions.put("Offer.public", "ACTIVE");
		conditions.put("Offer.company_id", String.valueOf(companyId));
		params.put("conditions", conditions);
		key.put("Offer", params);

		try {
			offers = new ArrayList<Offer>();
			offers = OfferBO.listAllOffers(key);

			Log.i("METODO - LISTA POR COMPANHIA - TAMANHO",
					String.valueOf(offers.size()));

		} catch (Exception e) {
			e.printStackTrace();
		}

		return offers;
	}

	// substitui o -ListAllOffer
	public List<Offer> listAllObjOffers(Map params) {

		List<Offer> offers = null;
		List<Offer> offersReturn = new ArrayList<Offer>();

		AsyncTask<Map, Void, List<Offer>> async = new AsyncTask<Map, Void, List<Offer>>() {

			@Override
			protected List<Offer> doInBackground(Map... params) {
				List<Offer> offers = OfferBO.listOffers(params[0]);

				int contador = OfferBO.count_offer(params[0]);
				Log.i("QUANTIDADE DE REGISTROS - OFFER",
						Integer.toString(contador));
				Log.i("QUANTIDADE DE REGISTROS NA LISTA",
						Integer.toString(offers.size()));

				return offers;
			}
		};

		try {

			offers = async.execute(params).get();
			Log.i("TAMANHO DA LISTA", String.valueOf(offers.size()));
			for (Offer of : offers) {
				Log.i("ID DA OFERTA", String.valueOf(of.getId()));
				Company comp = CompanyBO.searchCompanyByOffer(of.getId());
				of.setCompany(comp);
				offersReturn.add(of);
			}
			return offersReturn;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * UWC = UsersWishlistCompany
	 */
	public Offer searchOfferByUWC(int uwcId) {

		Map<String, Map<String, Map<String, String>>> key = new HashMap<String, Map<String, Map<String, String>>>();
		Map<String, Map<String, String>> params = new HashMap<String, Map<String, String>>();
		Map<String, String> conditions = new HashMap<String, String>();

		conditions.put("UsersWishlistCompany.id", String.valueOf(uwcId));
		params.put("conditions", conditions);
		key.put("UsersWishlistCompany", params);

		AsyncTask<Map, Void, Integer> async = new AsyncTask<Map, Void, Integer>() {
			@Override
			protected Integer doInBackground(Map... params) {
				UsersWishlistCompanyDAO dao = new UsersWishlistCompanyDAO();
				int i = dao.returnsObejectId(params[0], "offer_id");
				Log.i("searchOfferByUWCId", String.valueOf(i));
				return i;
			}
		};
		int y = 0;
		try {
			y = async.execute(key).get();
			Log.i("searchOfferByCheckoutId - VALOR DO Y", String.valueOf(y));
			return OfferBO.searchOfferById(y);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Traz Novidades: Numero de ofertas cadastradas nos ultimos 4 dias
	 * 
	 * @return int
	 * @param
	 * @author Matheus Odilon - accentialbrasil
	 */
	public int news() {

		int contador = 0;
		Map<String, Map<String, Map<String, String>>> key = new HashMap<String, Map<String, Map<String, String>>>();
		Map<String, Map<String, String>> params = new HashMap<String, Map<String, String>>();
		Map<String, String> conditions = new HashMap<String, String>();

		params.put("conditions", conditions);
		key.put("Offer", params);
		List<Offer> offers = OfferBO.listAllOffers(key);

		Calendar calcCalendar = Calendar.getInstance();
		calcCalendar.add(Calendar.DAY_OF_MONTH, -4);

		for (Offer offer : offers) {
			if (offer.getBeginsAt().after(calcCalendar)) {
				contador = contador + 1;
			}
		}
		return contador;
	}

	public List<Offer> listValidOffers() {

		Map<String, Map<String, Map<String, String>>> key = new HashMap<String, Map<String, Map<String, String>>>();
		// Map<String, Map<String, String>> paramss = new HashMap<String,
		// Map<String, String>>();
		Map paramss = new HashMap();
		Map<String, String> conditions = new HashMap<String, String>();

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();

		conditions.put("Offer.ends_at >",
				String.valueOf(formatter.format(date)));
		conditions.put("Offer.status", "ACTIVE");
		conditions.put("Offer.public", "ACTIVE");
		paramss.put("conditions", conditions);
		paramss.put("limit", "6");
		key.put("Offer", paramss);

		List<Offer> offers = OfferBO.listAllOffers(key);
		return offers;
	}

	public List<Offer> listValidOffersForPage(int page) {

		Map key = new HashMap();
		Map paramss = new HashMap();
		Map<String, String> conditions = new HashMap<String, String>();

		List<Map> arrayOffer = new ArrayList<Map>();

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();

		conditions.put("Offer.ends_at >",
				String.valueOf(formatter.format(date)));
		conditions.put("Offer.status", "ACTIVE");
		conditions.put("Offer.public", "ACTIVE");
		paramss.put("conditions", conditions);
		paramss.put("limit", "6");
		paramss.put("page", String.valueOf(page));

		key.put("Offer", paramss);

		Log.e("", "MAP OFFEES: " + key.toString());

		List<Offer> offers = OfferBO.listAllOffers(key);
		return offers;

	}

	public List<Offer> listRelateds(int compId) {

		Map<String, Map<String, Map<String, String>>> key = new HashMap<String, Map<String, Map<String, String>>>();
		Map<String, Map<String, String>> paramss = new HashMap<String, Map<String, String>>();
		Map<String, String> conditions = new HashMap<String, String>();

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();

		conditions.put("Offer.company_id", String.valueOf(compId));
		conditions.put("Offer.ends_at >",
				String.valueOf(formatter.format(date)));
		conditions.put("Offer.status", "ACTIVE");
		conditions.put("Offer.public", "ACTIVE");
		paramss.put("conditions", conditions);
		key.put("Offer", paramss);

		List<Offer> offers = OfferBO.listAllOffers(key);
		return offers;
	}
}
