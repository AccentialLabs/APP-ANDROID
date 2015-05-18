package com.accential.trueone.interfaces;

import java.util.List;
import java.util.Map;

import com.accential.trueone.bean.Offer;
import com.accential.trueone.bean.UsersWishlistCompany;

@SuppressWarnings("all")
public interface IUsersWishlistCompany {

	public List<UsersWishlistCompany> listUsersWishlist(Map params);

	public List<UsersWishlistCompany> listAllUsersWishlist(Map params);

	public int countUsersWishlistCompany(Map params);

	public int countUWC(Map params);

	public int countUWCByWish(int wishId);

	public List<UsersWishlistCompany> listAllUWCByUser(int userId);

	public List<UsersWishlistCompany> listAllUWCByWish(int wishId);

	public List<UsersWishlistCompany> listaObjUWCByWish(int wishId);

	public List<Offer> listOfferByWish(int wishId);

}
