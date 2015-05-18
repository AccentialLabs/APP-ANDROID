package com.accential.trueone.interfaces;

import java.util.List;
import java.util.Map;

import com.accential.trueone.bean.Offer;

@SuppressWarnings("all")
public interface IOffer {

	@SuppressWarnings("rawtypes")
	public List<Offer> listOffers(Map params);

	public int countOffer(Map params);

	public List<Offer> listAllOffers(Map params);

	public Offer searchOfferById(int offerId);

	public Offer searchOfferByCheckoutId(int id);

	public List<Offer> listAllOffersNoFilter(Map params);

	public List<Offer> listOffersNoFilter(Map params);

	public List<Offer> searchOffersByTitle(String title);

	public List<Offer> listOffersByCompany(int companyId);

	public List<Offer> listAllObjOffers(Map params);

	public int news();

	public List<Offer> listValidOffers();

	public List<Offer> listValidOffersForPage(int page);
}
