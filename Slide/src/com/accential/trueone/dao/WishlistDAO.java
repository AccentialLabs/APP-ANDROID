package com.accential.trueone.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

import com.accential.trueone.bean.CompanyCategory;
import com.accential.trueone.bean.CompanySubCategory;
import com.accential.trueone.bean.User;
import com.accential.trueone.bean.UsersWishlistCompany;
import com.accential.trueone.bean.Wishlist;
import com.accential.trueone.bo.CompanyCategoryBO;
import com.accential.trueone.bo.UsersWishlistCompanyBO;
import com.accential.trueone.bo.UtilityComponentBO;
import com.accential.trueone.bo.WishlistBO;
import com.accential.trueone.interfaces.IWishlist;
import com.accential.trueone.utils.JSONUtils;

/**
 * 
 * @author Matheus Odilon - accentialbrasil
 * 
 */
@SuppressWarnings("all")
public class WishlistDAO implements IWishlist {

	@Override
	public List<Wishlist> listWishlists(Map params) {

		List<Wishlist> whishlists = null;
		UtilityComponentBO bo = null;

		try {
			bo = new UtilityComponentBO();
			whishlists = new ArrayList<Wishlist>();

			JSONArray array = bo.urlRequestToGetData("users", "all", params);

			if (array != null) {
				List<HashMap> objs = JSONUtils.toList(array);

				Wishlist wishlist = null;
				Map values = null;

				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

				Log.e("", "RETORNO OBJ: " + objs.toString());

				if (!objs.isEmpty()) {
					for (HashMap obj : objs) {
						values = JSONUtils.toMap(new JSONObject((Map) obj
								.get("UsersWishlist")));
						wishlist = new Wishlist();
						wishlist.setId(Integer.parseInt((String) values
								.get("id")));
						wishlist.setName(String.valueOf(values.get("name")));
						wishlist.setDescription(String.valueOf(values
								.get("description")));
						wishlist.setStatus(String.valueOf(values.get("status")));

						Calendar registerDate = Calendar.getInstance();
						String stringRegiterData = String.valueOf(
								values.get("date_register")).replace("-", "/");
						Date date = new Date(stringRegiterData);
						registerDate.setTime(date);
						wishlist.setDateRegister(registerDate);

						Calendar endsDate = Calendar.getInstance();
						String stringEndsDate = String.valueOf(
								values.get("ends_at")).replace("-", "/");
						if (!stringEndsDate.equals("0000/00/00")) {
							Date endDate = new Date(stringEndsDate);
							endsDate.setTime(endDate);
							wishlist.setEndsAt(endsDate);
						}

						whishlists.add(wishlist);

					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return whishlists;
	}

	@Override
	public int countWhishlist(Map params) {
		int contador_wishlist = 0;
		UtilityComponentBO bo = null;

		try {
			bo = new UtilityComponentBO();
			JSONArray array = bo.urlRequestToGetData("users", "all", params);

			if (array != null) {

				contador_wishlist = array.length();
				array = null;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return contador_wishlist;
	}

	@Override
	public List<Wishlist> listAllWishlists(Map params) {

		AsyncTask<Map, Void, List<Wishlist>> async = new AsyncTask<Map, Void, List<Wishlist>>() {

			@Override
			protected List<Wishlist> doInBackground(Map... params) {
				List<Wishlist> wishlists = WishlistBO.listWishlists(params[0]);

				int contador = WishlistBO.countWhishlist(params[0]);
				Log.i("QUANTIDADE DE REGISTROS - WHISHLIST",
						Integer.toString(contador));
				for (Wishlist wishlist : wishlists) {
					Log.i("TITULO DA LISTA", wishlist.getName());
				}

				return wishlists;
			}

		};

		try {
			return async.execute(params).get();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public int retornaIdByWish(Map params, String atributo) {

		int id = 0;
		UtilityComponentBO bo = null;

		try {
			bo = new UtilityComponentBO();

			JSONArray array = bo.urlRequestToGetData("users", "all", params);

			if (array != null) {
				List<HashMap> objs = JSONUtils.toList(array);
				Map values = null;
				if (!objs.isEmpty()) {
					for (HashMap obj : objs) {
						values = JSONUtils.toMap(new JSONObject((Map) obj
								.get("UsersWishlist")));
						id = Integer.parseInt((String) values.get(atributo));
					}
				}
			}
		} catch (Exception e) {
			Log.e("ERRO NO METODO", "N��O PODE RETORNAR ID");
			e.printStackTrace();
		}
		return id;
	}

	public int retornaCategoryId(int id) {

		Map<String, Map<String, Map<String, String>>> key = new HashMap<String, Map<String, Map<String, String>>>();
		Map<String, Map<String, String>> params = new HashMap<String, Map<String, String>>();
		Map<String, String> conditions = new HashMap<String, String>();

		conditions.put("UsersWishlist.id", String.valueOf(id));
		params.put("conditions", conditions);
		key.put("UsersWishlist", params);

		AsyncTask<Map, Void, Integer> async = new AsyncTask<Map, Void, Integer>() {

			@Override
			protected Integer doInBackground(Map... params) {
				WishlistDAO dao = new WishlistDAO();
				int i = dao.retornaIdByWish(params[0], "category_id");
				return i;
			}
		};
		int y = 0;
		try {
			y = async.execute(key).get();
			Log.i("CATEGORY ID", String.valueOf(y));
			return y;
		} catch (Exception e) {
			Log.e("ERRO", "ERRO EM REALIZAR PESQUISA DE COMPANY");
			e.printStackTrace();
			return 0;
		}

	}

	public int retornaSubCategoryId(int id) {

		Map<String, Map<String, Map<String, String>>> key = new HashMap<String, Map<String, Map<String, String>>>();
		Map<String, Map<String, String>> params = new HashMap<String, Map<String, String>>();
		Map<String, String> conditions = new HashMap<String, String>();

		conditions.put("UsersWishlist.id", String.valueOf(id));
		params.put("conditions", conditions);
		key.put("UsersWishlist", params);

		AsyncTask<Map, Void, Integer> async = new AsyncTask<Map, Void, Integer>() {

			@Override
			protected Integer doInBackground(Map... params) {
				WishlistDAO dao = new WishlistDAO();
				int i = dao.retornaIdByWish(params[0], "sub_category_id");
				return i;
			}
		};
		int y = 0;
		try {
			y = async.execute(key).get();
			Log.i("SUB CATEGORY ID", String.valueOf(y));
			return y;
		} catch (Exception e) {
			Log.e("ERRO", "ERRO EM REALIZAR PESQUISA DE COMPANY");
			e.printStackTrace();
			return 0;
		}

	}

	public int retornaUserId(int id) {

		Map<String, Map<String, Map<String, String>>> key = new HashMap<String, Map<String, Map<String, String>>>();
		Map<String, Map<String, String>> params = new HashMap<String, Map<String, String>>();
		Map<String, String> conditions = new HashMap<String, String>();

		conditions.put("UsersWishlist.id", String.valueOf(id));
		params.put("conditions", conditions);
		key.put("UsersWishlist", params);

		AsyncTask<Map, Void, Integer> async = new AsyncTask<Map, Void, Integer>() {

			@Override
			protected Integer doInBackground(Map... params) {
				WishlistDAO dao = new WishlistDAO();
				int i = dao.retornaIdByWish(params[0], "user_id");
				return i;
			}
		};
		int y = 0;
		try {
			y = async.execute(key).get();
			Log.i("USER ID", String.valueOf(y));
			return y;
		} catch (Exception e) {
			Log.e("ERRO", "ERRO EM REALIZAR PESQUISA DE COMPANY");
			e.printStackTrace();
			return 0;
		}
	}

	/**
	 * Retorna lista de desejos com todos os parametros completos: Usuario,
	 * Categoria, SubCategoria e as demais informa����es.
	 * 
	 * @param int userId
	 * @author Matheus Odilon - accentialbrasil
	 * @return List<Wishlist>
	 */
	public List<Wishlist> retornaObj(int userId) {

		Map<String, Map<String, Map<String, String>>> key = new HashMap<String, Map<String, Map<String, String>>>();
		Map<String, Map<String, String>> params = new HashMap<String, Map<String, String>>();
		Map<String, String> conditions = new HashMap<String, String>();

		Map<String, Map<String, Map<String, String>>> key2 = new HashMap<String, Map<String, Map<String, String>>>();
		Map<String, Map<String, String>> params2 = new HashMap<String, Map<String, String>>();
		Map<String, String> conditions2 = new HashMap<String, String>();

		conditions.put("UsersWishlist.user_id", String.valueOf(userId));
		params.put("conditions", conditions);
		key.put("UsersWishlist", params);

		List<Wishlist> wishes = WishlistBO.listAllWishlists(key);
		List<Wishlist> wishes2 = new ArrayList<Wishlist>();

		WishlistDAO dao = new WishlistDAO();
		UserDAO uDao = new UserDAO();

		for (Wishlist wishlist : wishes) {

			if (!wishlist.getStatus().equals("INACTIVE")) {

				int i = dao.retornaCategoryId(wishlist.getId());
				int y = dao.retornaSubCategoryId(wishlist.getId());

				key2.put("CompaniesCategory", params2);

				CompanyCategory comp = CompanyCategoryBO.searchById(i, key2);
				CompanySubCategoryDAO dao2 = new CompanySubCategoryDAO();
				CompanySubCategory sub = dao2.searchById(y);

				User user = uDao
						.searchById(dao.retornaUserId(wishlist.getId()));
				Wishlist wish = new Wishlist();
				wish.setDateRegister(wishlist.getDateRegister());
				wish.setEndsAt(wishlist.getEndsAt());
				wish.setName(wishlist.getName());
				wish.setDescription(wishlist.getDescription());
				wish.setId(wishlist.getId());
				wish.setStatus(wishlist.getStatus());
				wish.setCategory(comp);
				wish.setSubCategory(sub);
				wish.setUser(user);
				wishes2.add(wish);
			}
		}

		return wishes2;
	}

	/**
	 * Retorna lista de desejos com os parametros simples, sem Usuario,
	 * Categoria e SubCategoria
	 * 
	 * @author Matheus Odilon - accentialbrasil
	 * @param user
	 * @return List<Wishlist>
	 */
	public List<Wishlist> retornaWishies(int userId) {

		Map<String, Map<String, Map<String, String>>> key = new HashMap<String, Map<String, Map<String, String>>>();
		Map<String, Map<String, String>> params = new HashMap<String, Map<String, String>>();
		Map<String, String> conditions = new HashMap<String, String>();

		Map<String, Map<String, Map<String, String>>> key2 = new HashMap<String, Map<String, Map<String, String>>>();
		Map<String, Map<String, String>> params2 = new HashMap<String, Map<String, String>>();
		Map<String, String> conditions2 = new HashMap<String, String>();

		conditions.put("UsersWishlist.status", "ACTIVE");
		conditions.put("UsersWishlist.user_id", String.valueOf(userId));
		params.put("conditions", conditions);
		key.put("UsersWishlist", params);

		List<Wishlist> wishes = WishlistBO.listAllWishlists(key);
		List<Wishlist> wishes2 = new ArrayList<Wishlist>();

		WishlistDAO dao = new WishlistDAO();
		UserDAO uDao = new UserDAO();

		for (Wishlist wishlist : wishes) {

			Wishlist wish = new Wishlist();
			wish.setDateRegister(wishlist.getDateRegister());
			wish.setEndsAt(wishlist.getEndsAt());
			wish.setName(wishlist.getName());
			wish.setDescription(wishlist.getDescription());
			wish.setId(wishlist.getId());
			wish.setStatus(wishlist.getStatus());
			wishes2.add(wish);
		}

		return wishes2;
	}

	public void deleteData(Map params) {

		UtilityComponentBO bo = null;
		try {
			bo = new UtilityComponentBO();

			bo.urlRequestToDeleteData("users", "fist", params);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// METODO DELETA USUARIOS
	// RESOLVER PROBLEMA - DEVE DELETAR WISHLIST
	public void deleteWishlist(int wishlistId) {
		AsyncTask<Integer, Void, Void> async = new AsyncTask<Integer, Void, Void>() {

			@Override
			protected Void doInBackground(Integer... parms) {

				Map<String, Map<String, Map<String, String>>> key = new HashMap<String, Map<String, Map<String, String>>>();
				Map<String, Map<String, String>> params = new HashMap<String, Map<String, String>>();
				Map<String, String> conditions = new HashMap<String, String>();

				conditions.put("id", String.valueOf(parms[0]));
				params.put("conditions", conditions);
				key.put("Users.UsersWishlist", params);

				WishlistDAO dao = new WishlistDAO();
				dao.deleteData(key);
				return null;
			}

		};
		try {
			async.execute(wishlistId);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<Wishlist> save(Map params) {

		List<Wishlist> wishies = null;
		UtilityComponentBO bo = null;

		try {
			bo = new UtilityComponentBO();
			wishies = new ArrayList<Wishlist>();

			JSONArray array = bo.urlRequestToSaveData("users", "all", params);

			if (array != null) {
				List<HashMap> objs = JSONUtils.toList(array);

				Wishlist wish = null;
				Map values = null;

				if (!objs.isEmpty()) {
					for (HashMap obj : objs) {
						values = JSONUtils.toMap(new JSONObject((Map) obj
								.get("UsersWishlist")));
						wish = new Wishlist();

						wish.setName(String.valueOf(values.get("name")));

						wishies.add(wish);
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return wishies;
	}

	public List<Wishlist> saveMyWishlist(Wishlist wishlist) {

		AsyncTask<Map, Void, List<Wishlist>> async = new AsyncTask<Map, Void, List<Wishlist>>() {

			@Override
			protected List<Wishlist> doInBackground(Map... params) {

				WishlistDAO dao = new WishlistDAO();
				List<Wishlist> wishies = dao.save(params[0]);

				return wishies;
			}
		};
		try {

			Map<String, Map<String, String>> params = new HashMap<String, Map<String, String>>();
			Map<String, String> datas = new HashMap<String, String>();

			SimpleDateFormat f3 = new SimpleDateFormat("yyyy-MM-dd");
			String endsDate = f3.format(wishlist.getEndsAt().getTime());
			String dateRegister = f3.format(wishlist.getDateRegister()
					.getTime());

			datas.put("user_id", String.valueOf(wishlist.getUser().getId()));
			datas.put("category_id",
					String.valueOf(wishlist.getCategory().getId()));
			datas.put("sub_category_id",
					String.valueOf(wishlist.getSubCategory().getId()));
			datas.put("name", wishlist.getName());
			datas.put("description", wishlist.getDescription());
			datas.put("ends_at", endsDate);
			datas.put("status", "ACTIVE");
			datas.put("date_register", dateRegister);
			params.put("UsersWishlist", datas);

			return async.execute(params).get();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<Wishlist> inactiveWish(int id) {

		AsyncTask<Map, Void, List<Wishlist>> async = new AsyncTask<Map, Void, List<Wishlist>>() {

			@Override
			protected List<Wishlist> doInBackground(Map... params) {

				WishlistDAO dao = new WishlistDAO();
				List<Wishlist> wishies = dao.save(params[0]);
				return wishies;
			}
		};
		try {

			Map<String, Map<String, String>> params = new HashMap<String, Map<String, String>>();
			Map<String, String> datas = new HashMap<String, String>();

			datas.put("id", String.valueOf(id));
			datas.put("status", "INACTIVE");
			params.put("UsersWishlist", datas);

			return async.execute(params).get();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public int countOfferByWish(String wishTitle) {

		return 0;

	}

	// teste
	public Map listWithQtdOffers(int userId) {

		AsyncTask<Map, Void, List<Wishlist>> async = new AsyncTask<Map, Void, List<Wishlist>>() {
			@Override
			protected List<Wishlist> doInBackground(Map... params) {

				return WishlistBO.listWishlists(params[0]);
			}
		};

		try {

			Map key = new HashMap();
			Map<String, Map<String, String>> params = new HashMap<String, Map<String, String>>();
			Map<String, String> conditions = new HashMap<String, String>();

			conditions.put("UsersWishlist.user_id", String.valueOf(userId));
			conditions.put("UsersWishlist.status", "ACTIVE");
			params.put("conditions", conditions);
			key.put("UsersWishlist", params);

			List<Wishlist> lista = async.execute(key).get();

			Map qtdOffes = new HashMap();
			Map wishs = new HashMap();

			for (Wishlist wishlist : lista) {
				qtdOffes.put(String.valueOf(wishlist.getId()), String
						.valueOf(UsersWishlistCompanyBO.countUWCByWish(wishlist
								.getId())));
			}

			wishs.put("wishes", lista);
			wishs.put("offersQtd", qtdOffes);

			return wishs;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}
}
