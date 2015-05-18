package com.accential.trueone.interfaces;

import java.util.List;
import java.util.Map;

import com.accential.trueone.bean.OfferFilter;

@SuppressWarnings("all")
public interface IOfferFilter {

	public List<OfferFilter> listOffersFilters(Map params);

	public List<OfferFilter> listAllOffersFilters();

	public List<OfferFilter> searchOffersFilters(OfferFilter filter);

}
