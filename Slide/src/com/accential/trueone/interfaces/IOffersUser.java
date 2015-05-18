package com.accential.trueone.interfaces;

import java.util.List;
import java.util.Map;

import com.accential.trueone.bean.OfferFilter;
import com.accential.trueone.bean.OffersUser;

@SuppressWarnings("all")
public interface IOffersUser {

	public List<OffersUser> listOffersUser(Map params);

	public List<OffersUser> listAllOffersUsersByUser(int userId);

}
