package com.accential.trueone.interfaces;

import java.util.List;
import java.util.Map;

import android.content.Context;

import com.accential.trueone.bean.AditionalAddressesUser;
import com.accential.trueone.bean.Checkout;
import com.accential.trueone.bean.Company;
import com.accential.trueone.bean.Offer;
import com.accential.trueone.bean.User;

/**
 * 
 * @author Matheus Odilon - accentialbrasil
 * 
 */
public interface ICheckout {

	@SuppressWarnings("rawtypes")
	List<Checkout> listCheckouts(Map params);

	@SuppressWarnings("rawtypes")
	List<Checkout> listAllCheckouts(Map params);

	@SuppressWarnings("rawtypes")
	int returnsObejectId(Map params, String atributo);

	public List<Checkout> returnsObjCheckout(int userId);

	@SuppressWarnings("rawtypes")
	public Map calculateShippingValue(String cepDestino, String cepOrigem,
			String weight);

	public String makePayment(User user, Checkout check, Offer offer,
			Company comp, AditionalAddressesUser aa);

	public void createCheckout(Checkout check);

	public void updateUserAddress(AditionalAddressesUser aa, Checkout check);

	public List<Checkout> listAllCheckoutsByUser(int userId);

	public Map calculaFrete(final String cepOrigem, final String cepDestino,
			final String peso);

	public List<Checkout> getMyAllCheckouts(int userId);

	public List<Checkout> listByUser(int userId);

}
