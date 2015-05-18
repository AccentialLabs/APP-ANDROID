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
import com.accential.trueone.bean.CompanySubCategory;
import com.accential.trueone.bo.CompanyCategoryBO;
import com.accential.trueone.bo.CompanySubCategoryBO;
import com.accential.trueone.bo.UtilityComponentBO;
import com.accential.trueone.interfaces.ICompanySubCategory;
import com.accential.trueone.utils.JSONUtils;

/**
 * 
 * @author Matheus Odilon - accentialbrasil
 * 
 */
@SuppressWarnings("all")
public class CompanySubCategoryDAO implements ICompanySubCategory {

	@Override
	public List<CompanySubCategory> listSubCategories(Map params) {

		List<CompanySubCategory> subCategories = null;
		UtilityComponentBO bo = null;

		try {
			bo = new UtilityComponentBO();
			subCategories = new ArrayList<CompanySubCategory>();

			JSONArray array = bo
					.urlRequestToGetData("companies", "all", params);

			if (array != null) {
				List<HashMap> objs = JSONUtils.toList(array);

				CompanySubCategory subCategory = null;
				Map values = null;

				if (!objs.isEmpty()) {
					for (HashMap obj : objs) {
						values = JSONUtils.toMap(new JSONObject((Map) obj
								.get("CompaniesSubCategory")));
						subCategory = new CompanySubCategory();

						subCategory.setId(Integer.parseInt((String) values
								.get("id")));
						subCategory.setName(String.valueOf(values.get("name")));
						subCategory
								.setPhoto(String.valueOf(values.get("photo")));

						subCategories.add(subCategory);
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return subCategories;
	}

	@Override
	public int countSubCategories(Map params) {
		int subCategoryCount = 0;
		UtilityComponentBO bo = null;

		try {
			bo = new UtilityComponentBO();
			JSONArray array = bo
					.urlRequestToGetData("companies", "all", params);

			if (array != null) {

				subCategoryCount = array.length();
				array = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return subCategoryCount;
	}

	@Override
	public List<CompanySubCategory> listAllCategories(Map params) {

		AsyncTask<Map, Void, List<CompanySubCategory>> async = new AsyncTask<Map, Void, List<CompanySubCategory>>() {

			@Override
			protected List<CompanySubCategory> doInBackground(Map... params) {
				List<CompanySubCategory> subCategories = CompanySubCategoryBO
						.listSubCategories(params[0]);

				int contador = CompanySubCategoryBO
						.countSubCategories(params[0]);
				Log.i("QUANTIDADE DE REGISTROS - SUBCATEGORY",
						Integer.toString(contador));

				for (CompanySubCategory companySubCategory : subCategories) {
					// Log.i("SUBCATEGORY", companySubCategory.getName());
				}

				return subCategories;
			}

		};
		try {
			return async.execute(params).get();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<CompanySubCategory> searchByCategory(int categoryId, Map params) {

		CompanySubCategory subCategory = new CompanySubCategory();
		List<CompanySubCategory> subs = new ArrayList<CompanySubCategory>();

		List<CompanySubCategory> subCategories = CompanySubCategoryBO
				.listAllCategories(params);
		// MUDAR PARA ID DA CATEGORY
		for (CompanySubCategory categorySub : subCategories) {

			if (categorySub.getId() == categoryId) {
				subCategory.setName(categorySub.getName());
				subCategory.setPhoto(categorySub.getPhoto());

				subs.add(subCategory);
				// Log.i("SUB CATEGORY", categorySub.getName());
			}
		}

		return subs;

	}

	@Override
	public CompanySubCategory searchById(int id) {

		CompanySubCategory subCategory = new CompanySubCategory();

		Map<String, Map<String, Map<String, String>>> key2 = new HashMap<String, Map<String, Map<String, String>>>();
		Map<String, Map<String, String>> params2 = new HashMap<String, Map<String, String>>();
		Map<String, String> conditions2 = new HashMap<String, String>();

		params2.put("conditions", conditions2);
		key2.put("CompaniesSubCategory", params2);
		List<CompanySubCategory> subCategories = CompanySubCategoryBO
				.listAllCategories(key2);

		for (CompanySubCategory categorySub : subCategories) {

			if (categorySub.getId() == id) {
				subCategory.setName(categorySub.getName());
				subCategory.setPhoto(categorySub.getPhoto());

				// params2.put("conditions", conditions2);
				// key2.put("CompaniesCategory", params2);
				// //subCategory.setCategory(CompanyCategoryBO.searchById(1,
				// params));

				Log.i("SUB CATEGORY", categorySub.getName());
			}
		}

		return subCategory;
	}

	/**
	 * Retorna lista de subcategorias de acordo com o id da categoria
	 * 
	 * @param categoryId
	 * @return
	 */
	public List<CompanySubCategory> getByCategoryId(int categoryId) {

		AsyncTask<Map, Void, List<CompanySubCategory>> async = new AsyncTask<Map, Void, List<CompanySubCategory>>() {

			@Override
			protected List<CompanySubCategory> doInBackground(Map... params) {
				List<CompanySubCategory> subCategories = CompanySubCategoryBO
						.listSubCategories(params[0]);

				return subCategories;
			}

		};
		try {

			Map<String, Map<String, Map<String, String>>> key = new HashMap<String, Map<String, Map<String, String>>>();
			Map<String, Map<String, String>> params = new HashMap<String, Map<String, String>>();
			Map<String, String> conditions = new HashMap<String, String>();

			conditions.put("CompaniesSubCategory.category_id",
					String.valueOf(categoryId));
			params.put("conditions", conditions);
			key.put("CompaniesSubCategory", params);

			return async.execute(key).get();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
