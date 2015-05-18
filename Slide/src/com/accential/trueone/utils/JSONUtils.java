package com.accential.trueone.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.JSONValue;

import android.util.Log;
@SuppressWarnings("all")
public class JSONUtils {


	/**
	 * Decoding json
	 * @author Henrique Alle
	 * @param String
	 * @return JSONObject
	 */
	public static JSONArray decodeJSON(String json)
	{
		JSONObject obj = null;
		JSONArray array = null;

		try 
		{  
			if(!json.equals("")) array = new JSONArray(json);

		} catch (JSONException e) {
			e.printStackTrace();
			Log.e("JSONException ", e.getMessage());
		}		

		return array;
	}

	/**
	 * Encoding json
	 * @author Henrique Alle
	 * @param LinkedHashMap
	 * @return String
	 */
	public static String encodeJSON(Map data)
	{

		String json = null;

		try {  

			json = JSONValue.toJSONString(data);

		} catch(Exception e){
			Log.e("Exception ", e.getMessage());
		}

		return json;
	}


	public static boolean isEmptyObject(JSONObject object) {
		return object.names() == null;
	}

	public static Map<String, Object> getMap(JSONObject object, String key) throws JSONException {
		return toMap(object.getJSONObject(key));
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map<String, Object> toMap(JSONObject object) throws JSONException {
		Map<String, Object> map = new HashMap();
		Iterator keys = object.keys();
		while (keys.hasNext()) {
			String key = (String) keys.next();
			map.put(key, fromJson(object.get(key)));
		}
		return map;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List toList(JSONArray array) throws JSONException {
		List list = new ArrayList();
		for (int i = 0; i < array.length(); i++) {
			list.add(fromJson(array.get(i)));
		}
		return list;
	}
	
	public static int toInt (JSONArray array) throws JSONException {
		int count = 0;				
		return count;
	}

	private static Object fromJson(Object json) throws JSONException {
		if (json == JSONObject.NULL) {
			return null;
		} else if (json instanceof JSONObject) {
			return toMap((JSONObject) json);
		} else if (json instanceof JSONArray) {
			return toList((JSONArray) json);
		} else {
			return json;
		}
	}


}
