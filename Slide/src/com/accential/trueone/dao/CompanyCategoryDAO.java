package com.accential.trueone.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

import com.accential.trueone.bean.CompanyCategory;
import com.accential.trueone.bo.CompanyCategoryBO;
import com.accential.trueone.bo.UtilityComponentBO;
import com.accential.trueone.interfaces.ICompanyCategory;
import com.accential.trueone.utils.JSONUtils;

/**
 * 
 * @author Matheus Odilon - accentialbrasil
 * 
 */
@SuppressWarnings("all")
public class CompanyCategoryDAO implements ICompanyCategory {

	@Override
	public List<CompanyCategory> listCategories(Map params) {

		List<CompanyCategory> categories = null;
		UtilityComponentBO bo = null;

		try {
			bo = new UtilityComponentBO();
			categories = new ArrayList<CompanyCategory>();

			JSONArray array = bo
					.urlRequestToGetData("companies", "all", params);

			if (array != null) {
				List<HashMap> objs = JSONUtils.toList(array);

				CompanyCategory category = null;
				Map values = null;

				if (!objs.isEmpty()) {
					for (HashMap obj : objs) {
						values = JSONUtils.toMap(new JSONObject((Map) obj
								.get("CompaniesCategory")));
						category = new CompanyCategory();
						category.setId(Integer.parseInt((String) values
								.get("id")));
						category.setName(String.valueOf(values.get("name")));
						category.setPhoto(String.valueOf(values.get("photo")));
						// Log.i("CATEGORY NAME", category.getName());
						categories.add(category);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return categories;
	}

	@Override
	public int countCategories(Map params) {
		int categoryCount = 0;
		UtilityComponentBO bo = null;

		try {
			bo = new UtilityComponentBO();
			JSONArray array = bo
					.urlRequestToGetData("companies", "all", params);

			if (array != null) {

				categoryCount = array.length();
				array = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return categoryCount;
	}

	public List<CompanyCategory> listAllCategories(Map params) {

		AsyncTask<Map, Void, List<CompanyCategory>> async = new AsyncTask<Map, Void, List<CompanyCategory>>() {

			@Override
			protected List<CompanyCategory> doInBackground(Map... params) {
				List<CompanyCategory> categories = CompanyCategoryBO
						.listCategories(params[0]);

				int contador = CompanyCategoryBO.countCategories(params[0]);
				Log.i("QUANTIDADE DE REGISTROS - CATEGORY",
						Integer.toString(contador));

				for (CompanyCategory companyCategory : categories) {
					// Log.i("CATEGORIA", companyCategory.getName());
				}

				return categories;
			}

		};

		try {
			return async.execute(params).get();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public CompanyCategory searchById(int id, Map params) {

		CompanyCategory category = new CompanyCategory();

		List<CompanyCategory> categories = CompanyCategoryBO
				.listAllCategories(params);
		for (CompanyCategory companyCategory : categories) {
			if (companyCategory.getId() == id) {
				category.setId(id);
				category.setName(companyCategory.getName());
				category.setPhoto(companyCategory.getPhoto());
				// Log.i("NOME DA CATEGORIA", category.getName());
			}
		}

		return category;
	}

	/**
	 * Retorna todas categorias cadastradas
	 * @return
	 */
	public List<CompanyCategory> getAllCompaniesCategory() {

		AsyncTask<Map, Void, List<CompanyCategory>> async = new AsyncTask<Map, Void, List<CompanyCategory>>() {

			@Override
			protected List<CompanyCategory> doInBackground(Map... params) {
				List<CompanyCategory> categories = CompanyCategoryBO
						.listCategories(params[0]);

				return categories;
			}

		};

		try {
			Map<String, Map<String, Map<String, String>>> key = new HashMap<String, Map<String, Map<String, String>>>();
			Map<String, Map<String, String>> params = new HashMap<String, Map<String, String>>();
			Map<String, String> conditions = new HashMap<String, String>();

			params.put("conditions", conditions);
			key.put("CompaniesCategory", params);

			return async.execute(key).get();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
