package com.accential.trueone.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

import com.accential.trueone.bean.AditionalAddressesUser;
import com.accential.trueone.bo.AditionalAddressesUserBO;
import com.accential.trueone.bo.UtilityComponentBO;
import com.accential.trueone.interfaces.IAditionalAddressesUser;
import com.accential.trueone.utils.JSONUtils;

public class AditionalAddressesUserDAO implements IAditionalAddressesUser {

	/**
	 * @author Matheus Odilon - accentialbrasil
	 * @param Map
	 * @return List<AditionalAddressesUser>
	 */
	@Override
	public List<AditionalAddressesUser> listAddresses(Map params) {

		List<AditionalAddressesUser> addresses = null;
		UtilityComponentBO bo = null;

		try {
			bo = new UtilityComponentBO();
			addresses = new ArrayList<AditionalAddressesUser>();

			JSONArray array = bo.urlRequestToGetData("users", "all", params);

			if (array != null) {
				List<HashMap> objs = JSONUtils.toList(array);

				AditionalAddressesUser address = null;
				Map values = null;

				if (!objs.isEmpty()) {
					for (HashMap obj : objs) {
						values = JSONUtils.toMap(new JSONObject((Map) obj
								.get("AditionalAddressesUser")));
						address = new AditionalAddressesUser();

						address.setId(Integer.parseInt((String) values
								.get("id")));
						address.setLabel(String.valueOf(values.get("label")));
						address.setAddress(String.valueOf(values.get("address")));
						address.setNumber(String.valueOf(values.get("number")));
						address.setComplement(String.valueOf(values
								.get("complement")));
						address.setDistrict(String.valueOf(values
								.get("district")));
						address.setCity(String.valueOf(values.get("city")));
						address.setState(String.valueOf(values.get("state")));
						address.setZipCode(String.valueOf(values
								.get("zip_code")));

						addresses.add(address);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return addresses;
	}

	/**
	 * @author Matheus Odilon - accentialbrasil
	 * @param Map
	 * @return List<AditionalAddressUser>
	 */
	@Override
	public List<AditionalAddressesUser> listaAllAddresses(Map params) {

		AsyncTask<Map, Void, List<AditionalAddressesUser>> async = new AsyncTask<Map, Void, List<AditionalAddressesUser>>() {

			@Override
			protected List<AditionalAddressesUser> doInBackground(Map... params) {
				AditionalAddressesUserDAO dao = new AditionalAddressesUserDAO();
				List<AditionalAddressesUser> addresses = dao
						.listAddresses(params[0]);

				return addresses;
			}

		};
		try {
			return async.execute(params).get();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	public AditionalAddressesUser searchAddressByUserId(int id) {

		AditionalAddressesUser address = new AditionalAddressesUser();

		Map<String, Map<String, Map<String, String>>> key = new HashMap<String, Map<String, Map<String, String>>>();
		Map<String, Map<String, String>> params = new HashMap<String, Map<String, String>>();
		Map<String, String> conditions = new HashMap<String, String>();

		conditions.put("AditionalAddressesUser.user_id", String.valueOf(id));
		params.put("conditions", conditions);
		key.put("AditionalAddressesUser", params);

		AditionalAddressesUserDAO dao = new AditionalAddressesUserDAO();
		List<AditionalAddressesUser> addresses = dao.listaAllAddresses(key);
		Log.i("TAMANHO DA LISTA", String.valueOf(addresses.size()));

		if (addresses.size() > 1) {
			address = addresses.get(1);
		} else {
			for (AditionalAddressesUser ad : addresses) {
				address = ad;
			}
		}

		return address;
	}

	public List<AditionalAddressesUser> listAllByUser(int userId) {

		Map<String, Map<String, Map<String, String>>> key = new HashMap<String, Map<String, Map<String, String>>>();
		Map<String, Map<String, String>> params = new HashMap<String, Map<String, String>>();
		Map<String, String> conditions = new HashMap<String, String>();

		conditions
				.put("AditionalAddressesUser.user_id", String.valueOf(userId));
		params.put("conditions", conditions);
		key.put("AditionalAddressesUser", params);

		List<AditionalAddressesUser> addresses = AditionalAddressesUserBO
				.listaAllAddresses(key);

		return addresses;
	}

	public void save(Map params) {

		UtilityComponentBO bo = null;

		try {
			bo = new UtilityComponentBO();
			bo.urlRequestToSaveData("users", "all", params);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void saveAddress(AditionalAddressesUser address) {

		AsyncTask<Map, Void, Void> async = new AsyncTask<Map, Void, Void>() {

			@Override
			protected Void doInBackground(Map... params) {
				AditionalAddressesUserDAO dao = new AditionalAddressesUserDAO();
				dao.save(params[0]);
				return null;
			}
		};

		try {

			Map<String, Map<String, String>> param = new HashMap<String, Map<String, String>>();
			Map<String, String> datas = new HashMap<String, String>();
			Map<String, String> datasUser = new HashMap<String, String>();

			datasUser.put("id", String.valueOf(address.getUser().getId()));
			datasUser.put("address", address.getAddress());
			datasUser.put("number", address.getNumber());
			datasUser.put("complement", address.getComplement());
			datasUser.put("district", address.getDistrict());
			datasUser.put("city", address.getCity());
			datasUser.put("state", address.getState());
			datasUser.put("zip_code", address.getZipCode());

			datas.put("user_id", String.valueOf(address.getUser().getId()));
			datas.put("label", address.getLabel());
			datas.put("address", address.getAddress());
			datas.put("number", address.getNumber());
			datas.put("complement", address.getComplement());
			datas.put("district", address.getDistrict());
			datas.put("city", address.getCity());
			datas.put("state", address.getState());
			datas.put("zip_code", address.getZipCode());

			param.put("User", datasUser);
			param.put("AditionalAddressesUser", datas);

			async.execute(param).get();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
