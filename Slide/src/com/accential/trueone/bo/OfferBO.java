package com.accential.trueone.bo;

import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import com.accential.trueone.bean.Offer;
import com.accential.trueone.daofactory.DAOFactory;
import com.accential.trueone.interfaces.IOffer;

@SuppressWarnings("all")
public class OfferBO {

	// Criando a instancia do dao com AbstractDAOFactory design pattern
	private static IOffer dao = DAOFactory.whichFactory(DAOFactory.JSON)
			.JSONDAOOffer();

	@SuppressLint("SimpleDateFormat")
	@SuppressWarnings("rawtypes")
	public static List<Offer> listOffers(Map params) {
		return dao.listOffers(params);
	}

	public static int count_offer(Map params) {
		return dao.countOffer(params);
	}

	public static List<Offer> listAllOffers(Map params) {
		return dao.listAllOffers(params);
	}

	public static Offer searchOfferById(int offerId) {
		return dao.searchOfferById(offerId);
	}

	public static Offer searchOfferByCheckoutId(int id) {
		return dao.searchOfferByCheckoutId(id);
	}

	public static List<Offer> listAllOffersNoFilter(Map params) {
		return dao.listAllOffersNoFilter(params);
	}

	public static List<Offer> listOffersNoFilter(Map params) {
		return dao.listOffersNoFilter(params);
	}

	public static List<Offer> searchOffersByTitle(String title) {
		return dao.searchOffersByTitle(title);
	}

	public static List<Offer> listOffersByCompany(int companyId) {
		return dao.listOffersByCompany(companyId);
	}

	public static List<Offer> listAllObjOffers(Map params) {
		return dao.listAllObjOffers(params);
	}

	public static int news() {
		return dao.news();
	}

	public static List<Offer> listValidOffers() {
		return dao.listValidOffers();
	}

	public static List<Offer> listValidOffersForPage(int page) {
		return dao.listValidOffersForPage(page);
	}

}
