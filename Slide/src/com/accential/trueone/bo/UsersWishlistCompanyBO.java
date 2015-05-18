package com.accential.trueone.bo;

import java.util.List;
import java.util.Map;

import com.accential.trueone.bean.Offer;
import com.accential.trueone.bean.UsersWishlistCompany;
import com.accential.trueone.daofactory.DAOFactory;
import com.accential.trueone.interfaces.IOffer;
import com.accential.trueone.interfaces.IUsersWishlistCompany;

@SuppressWarnings("all")
public class UsersWishlistCompanyBO {

	private static IUsersWishlistCompany dao = DAOFactory.whichFactory(
			DAOFactory.JSON).JSONDAOUsersWishlistCompany();

	/**
	 * Lista todos registros de acordo com parametros enviados DEVE SER
	 * EXECUTADO EM THREAD DIFERENTE DA PRINCIPAL
	 * 
	 * @param Map
	 *            params
	 * @return List<UsersWishlistCompany>
	 */
	public static List<UsersWishlistCompany> listUsersWishlist(Map params) {
		return dao.listUsersWishlist(params);
	}

	/**
	 * Lista todos registros de acordo com parametros enviados N��o ��
	 * necessario thread diferente
	 * 
	 * @param Map
	 *            params
	 * @return
	 */
	public static List<UsersWishlistCompany> listAllUsersWishlist(Map params) {
		return dao.listAllUsersWishlist(params);
	}

	/**
	 * Retorna quantidade - DEVE SER EXECUTADO EM THREAD DIFERENTE DA PRINCIPAL
	 * 
	 * @param Map
	 *            params
	 * @return int
	 */
	public static int countUsersWishlistCompany(Map params) {
		return dao.countUsersWishlistCompany(params);
	}

	/**
	 * Retorna quantidade - N��o �� necessario thread diferente
	 * 
	 * @param Map
	 *            params
	 * @return List<UsersWishlistCompany>
	 */
	public static int countUWC(Map params) {
		return dao.countUWC(params);
	}

	/**
	 * Retorna quantidade de ofertas relacionadas a um desejo especifico - N��o
	 * �� necessario thread diferente
	 * 
	 * @param int wishId
	 * @return int
	 */
	public static int countUWCByWish(int wishId) {
		return dao.countUWCByWish(wishId);
	}

	/**
	 * Retorna lista contendo todas ofertas direcionadas a determinado cliente,
	 * em resposta a seu desejo.
	 * 
	 * @param userId
	 * @return List<UsersWishlistCompany>
	 */
	public static List<UsersWishlistCompany> listAllUWCByUser(int userId) {
		return dao.listAllUWCByUser(userId);
	}

	/**
	 * Retorna lista contento todas ofertas direcionadas a um determinado
	 * desejo.
	 * 
	 * @param int wishId
	 * @return List<UsersWishlistCompany>
	 */
	public static List<UsersWishlistCompany> listAllUWCByWish(int wishId) {
		return dao.listAllUWCByWish(wishId);
	}

	/**
	 * Retorna lista de UWC contendo objeto lista
	 * 
	 * @param wishId
	 * @return
	 */
	public static List<UsersWishlistCompany> listaObjUWCByWish(int wishId) {
		return dao.listaObjUWCByWish(wishId);
	}

	/**
	 * Retorna lista de ofertas que são direcionadas a algum desejo
	 * 
	 * @param wishId
	 * @return List<Offer>
	 */
	public static List<Offer> listOfferByWish(int wishId) {
		return dao.listOfferByWish(wishId);
	}

}
