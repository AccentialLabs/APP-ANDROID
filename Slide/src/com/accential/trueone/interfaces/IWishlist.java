package com.accential.trueone.interfaces;

import java.util.List;
import java.util.Map;

import com.accential.trueone.bean.Wishlist;

/**
 * 
 * @author Matheus Odilon - accentialbrasil
 * 
 */
@SuppressWarnings("all")
public interface IWishlist {

	List<Wishlist> listWishlists(Map params);

	int countWhishlist(Map params);

	List<Wishlist> listAllWishlists(Map params);

	public int retornaIdByWish(Map params, String atributo);

	public int retornaCategoryId(int id);

	public int retornaSubCategoryId(int id);

	public int retornaUserId(int id);

	public List<Wishlist> retornaObj(int userId);

	public List<Wishlist> saveMyWishlist(Wishlist wishlist);

	public List<Wishlist> inactiveWish(int id);

	public List<Wishlist> retornaWishies(int userId);

	public Map listWithQtdOffers(int userId);
}
