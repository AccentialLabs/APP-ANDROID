package com.accential.trueone.bo;

import java.util.List;
import java.util.Map;

import com.accential.trueone.bean.Wishlist;
import com.accential.trueone.daofactory.DAOFactory;
import com.accential.trueone.interfaces.IWishlist;

/**
 * 
 * @author Matheus Odilon - accentialbrasil
 * 
 */
@SuppressWarnings("all")
public class WishlistBO {

	private static IWishlist dao = DAOFactory.whichFactory(DAOFactory.JSON)
			.JSONDAOWishlist();

	public static List<Wishlist> listWishlists(Map params) {
		return dao.listWishlists(params);

	}

	public static int countWhishlist(Map params) {
		return dao.countWhishlist(params);
	}

	public static List<Wishlist> listAllWishlists(Map params) {
		return dao.listAllWishlists(params);
	}

	public int retornaIdByWish(Map params, String atributo) {
		return dao.retornaIdByWish(params, atributo);
	}

	public static int retornaCategoryId(int wishlistId) {
		return dao.retornaCategoryId(wishlistId);
	}

	public static int retornaSubCategoryId(int wishlistId) {
		return dao.retornaSubCategoryId(wishlistId);
	}

	public static int retornaUserId(int wishlistId) {
		return dao.retornaUserId(wishlistId);
	}

	public static List<Wishlist> retornaObj(int userId) {
		return dao.retornaObj(userId);
	}

	public static List<Wishlist> saveMyWishlist(Wishlist wishlist) {
		return dao.saveMyWishlist(wishlist);
	}

	public static List<Wishlist> inactiveWish(int id) {
		return dao.inactiveWish(id);
	}

	public static List<Wishlist> retornaWishies(int userId) {
		return dao.retornaWishies(userId);
	}

	/**
	 * 
	 * @param userId
	 * @return Map
	 */
	public static Map listWithQtdOffers(int userId) {
		return dao.listWithQtdOffers(userId);
	}
}
