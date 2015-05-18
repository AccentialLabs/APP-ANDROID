package com.accential.trueone.bo;

import java.util.List;
import java.util.Map;

import com.accential.trueone.bean.PaymentMethod;
import com.accential.trueone.daofactory.DAOFactory;
import com.accential.trueone.interfaces.IPaymentMethod;
/**
 * 
 * @author Matheus Odilon - accentialbrasil
 *
 */
public class PaymentMethodBO {

	private static IPaymentMethod dao = DAOFactory.whichFactory(DAOFactory.JSON).JSONDAOPaymentMethod();
	
	@SuppressWarnings("rawtypes")
	public static List<PaymentMethod> listPaymentMethods(Map params) {
		return dao.listPaymentMethods(params);
	}
	
	@SuppressWarnings("rawtypes")
	public static List<PaymentMethod> listAllPaymentMethods(Map params) {
		return dao.listAllPaymentMethods(params);
	}
	
	public static PaymentMethod searchById(int paymentId) {
		return dao.searchById(paymentId);
	}
	
	public static PaymentMethod searchPMethodByCheckoutId(int id){
		return dao.searchPMethodByCheckoutId(id);
	}
}
