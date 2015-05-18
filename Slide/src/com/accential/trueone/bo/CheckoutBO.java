package com.accential.trueone.bo;

import java.util.List;

import java.util.Map;

import com.accential.trueone.bean.AditionalAddressesUser;
import com.accential.trueone.bean.Checkout;
import com.accential.trueone.bean.Company;
import com.accential.trueone.bean.Offer;
import com.accential.trueone.bean.User;
import com.accential.trueone.daofactory.DAOFactory;
import com.accential.trueone.interfaces.ICheckout;

@SuppressWarnings("all")
/**
 * @author Matheus Odilon - accentialbrasil
 * 
 */
public class CheckoutBO {

	private static ICheckout dao = DAOFactory.whichFactory(DAOFactory.JSON)
			.JSONDAOCheckout();

	@SuppressWarnings("rawtypes")
	/**
	 * ATEN����O: Esse m��todo deve SEMPRE ser 
	 * executado em uma thread diferente da principal
	 * @param params
	 * @return
	 */
	public static List<Checkout> listCheckouts(Map params) {
		return dao.listCheckouts(params);
	}

	@SuppressWarnings("rawtypes")
	public static List<Checkout> listAllCheckouts(Map params) {
		return dao.listAllCheckouts(params);
	}

	@SuppressWarnings("rawtypes")
	/**
	 * Busca id de determinado objeto por Checkout
	 * @param params
	 * @param atributo
	 * @return int
	 */
	public static int returnsObejectId(Map params, String atributo) {
		return dao.returnsObejectId(params, atributo);
	}

	public static List<Checkout> returnsObjCheckout(int userId) {
		return dao.returnsObjCheckout(userId);
	}

	public static Map<String, String> calculateShippingValue(String cepDestino,
			String cepOrigem, String weight) {
		return dao.calculateShippingValue(cepDestino, cepOrigem, weight);
	}

	/**
	 * MANDA AS INFORMA����ES NECESSARIAS PARA REALIZACAO DE PAGAMENTO PELA API
	 * O retorno �� atribuido �� uma WebView que carrega automaticamente a tela
	 * de pagamento
	 * 
	 * @param user
	 * @param check
	 * @param offer
	 * @param comp
	 * @param aa
	 * @return String
	 */
	public static String makePayment(User user, Checkout check, Offer offer,
			Company comp, AditionalAddressesUser aa) {
		return dao.makePayment(user, check, offer, comp, aa);
	}

	public static void createCheckout(Checkout check) {
		dao.createCheckout(check);
	}

	public static void updateUserAddress(AditionalAddressesUser aa,
			Checkout check) {
		dao.updateUserAddress(aa, check);
	}

	/**
	 * Retorna todos os checkouts de um determinado cliente
	 * 
	 * @param userId
	 * @return
	 */
	public static List<Checkout> listAllCheckoutsByUser(int userId) {
		return dao.listAllCheckoutsByUser(userId);
	}

	/**
	 * Versao 2.0 - Descomplicado, retorna Map com valor e prazo
	 * 
	 * @param cepOrigem
	 * @param cepDestino
	 * @param peso
	 * @return
	 */
	public static Map calculaFrete(final String cepOrigem,
			final String cepDestino, final String peso) {
		return dao.calculaFrete(cepOrigem, cepDestino, peso);
	}

	public static List<Checkout> getMyAllCheckouts(int userId) {
		return dao.getMyAllCheckouts(userId);
	}

	/**
	 * Traz objeto Checkout COMPLETO Usamos código SQL para fazer join das
	 * tabelas e assim ter um resultado mais ágil e descomplicado.
	 * 
	 * @param userId
	 * @return List<Checkout>
	 */
	public static List<Checkout> listByUser(int userId) {
		return dao.listByUser(userId);
	}

}
