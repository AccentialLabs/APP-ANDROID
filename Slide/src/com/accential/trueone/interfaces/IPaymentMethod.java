package com.accential.trueone.interfaces;

import java.util.List;
import java.util.Map;

import com.accential.trueone.bean.PaymentMethod;

public interface IPaymentMethod {

	@SuppressWarnings("rawtypes")
	List<PaymentMethod> listPaymentMethods(Map params);
	
	@SuppressWarnings("rawtypes")
	List<PaymentMethod> listAllPaymentMethods(Map params);
	
	PaymentMethod searchById(int paymentId);
	
	public PaymentMethod searchPMethodByCheckoutId(int id);
	
	
}
