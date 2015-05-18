package com.accential.trueone.bo;

import java.util.List;
import java.util.Map;

import com.accential.trueone.bean.OfferFilter;
import com.accential.trueone.daofactory.DAOFactory;
import com.accential.trueone.interfaces.IOfferFilter;

@SuppressWarnings("all")
public class OfferFilterBO {

	private static IOfferFilter dao = DAOFactory.whichFactory(DAOFactory.JSON)
			.JSONDAOfferFilter();

	public static List<OfferFilter> listOffersFilters(Map params) {
		return dao.listOffersFilters(params);
	}

	public static List<OfferFilter> listAllOffersFilters() {
		return dao.listAllOffersFilters();
	}
	
	public static List<OfferFilter> searchOffersFilters(OfferFilter filter){
		return dao.searchOffersFilters(filter);
	}

}
