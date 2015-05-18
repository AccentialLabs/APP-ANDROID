package com.accential.trueone.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import android.os.AsyncTask;

import com.accential.trueone.bean.PaymentMethod;
import com.accential.trueone.bo.CheckoutBO;
import com.accential.trueone.bo.PaymentMethodBO;
import com.accential.trueone.bo.UtilityComponentBO;
import com.accential.trueone.interfaces.IPaymentMethod;
import com.accential.trueone.utils.JSONUtils;


public class PaymentMethodDAO implements IPaymentMethod {

	@Override
	public List<PaymentMethod> listPaymentMethods(Map params) {

		List<PaymentMethod> methods = null;
		UtilityComponentBO bo = null;
		
		try{
			bo = new UtilityComponentBO();
			methods = new ArrayList<PaymentMethod>();
			
			JSONArray array = bo.urlRequestToGetData("payments", "all", params);
			
			if(array != null){
				List<HashMap> objs = JSONUtils.toList(array);
				
				PaymentMethod method = null;
				Map values = null;
				
				if(!objs.isEmpty())
				{
					for (HashMap obj : objs) {
						values = JSONUtils.toMap(new JSONObject((Map) obj.get("PaymentMethod")));
						method = new PaymentMethod();
						method.setId(Integer.parseInt((String) values.get("id")));
						method.setType(String.valueOf(values.get("type")));
						method.setName(String.valueOf(values.get("name")));
						method.setLastStatus(String.valueOf("last_status"));
						method.setStatus(String.valueOf(values.get("status")));
						
						methods.add(method);
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return methods;
	}

	@Override
	public List<PaymentMethod> listAllPaymentMethods(Map params) {

		AsyncTask<Map, Void, List<PaymentMethod>> async = new AsyncTask<Map, Void, List<PaymentMethod>>(){
			@Override
			protected List<PaymentMethod> doInBackground(Map... params) {
				List<PaymentMethod> methods = PaymentMethodBO.listPaymentMethods(params[0]);
				
				return methods;
			}
		};
		try{
			return async.execute(params).get();
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public PaymentMethod searchById(int paymentId) {
		
		PaymentMethod method = new PaymentMethod();
		
		Map<String, Map<String, Map<String, String>>> key2 = new HashMap<String,Map<String, Map<String, String>>>();
		Map<String,Map<String,String>> params2 = new HashMap<String,Map<String,String>>();
		Map<String,String> conditions2 = new HashMap<String,String>();

		conditions2.put("PaymentMethod.id", String.valueOf(paymentId));
		params2.put("conditions", conditions2);
		key2.put("PaymentMethod", params2);
		List<PaymentMethod> methods = PaymentMethodBO.listAllPaymentMethods(key2);
		
		for (PaymentMethod paymentMethod : methods) {
			method.setId(paymentMethod.getId());
			method.setType(paymentMethod.getType());
			method.setName(paymentMethod.getName());
			method.setLastStatus(paymentMethod.getLastStatus());
			method.setStatus(paymentMethod.getStatus());
		}
		
		return method;
	}

	@Override
	public PaymentMethod searchPMethodByCheckoutId(int id) {

		Map<String, Map<String, Map<String, String>>> key = new HashMap<String,Map<String, Map<String, String>>>();
		Map<String,Map<String,String>> params = new HashMap<String,Map<String,String>>();
		Map<String,String> conditions = new HashMap<String,String>();

		conditions.put("Checkout.id", String.valueOf(id));
		params.put("conditions", conditions);
		key.put("Checkout", params);
		
		AsyncTask<Map, Void, Integer> async = new AsyncTask<Map, Void, Integer>(){
			@Override
			protected Integer doInBackground(Map... params) {
				int i = CheckoutBO.returnsObejectId(params[0], "payment_method_id");
				return i;
			}
		};
		try{
			int y = async.execute(key).get();
			return PaymentMethodBO.searchById(y);
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
}
