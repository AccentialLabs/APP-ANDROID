package com.accential.trueone.bo;

import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;

import com.accential.trueone.bean.User;
import com.accential.trueone.daofactory.DAOFactory;
import com.accential.trueone.interfaces.IOUser;

@SuppressWarnings("all")
/**
 * 
 * @author Matheus Odilon - accentialbrasil
 * 
 */
public class UserBO {

	// Criando a instancia do dao com AbstractDAOFactory design pattern
	private static IOUser dao = DAOFactory.whichFactory(DAOFactory.JSON)
			.JSONDAOUser();

	@SuppressLint("SimpleDateFormat")
	@SuppressWarnings("rawtypes")
	public static List<User> listUsers(Map params) {
		return dao.listUsers(params);
	}

	@SuppressLint("SimpleDateFormat")
	@SuppressWarnings("rawtypes")
	public static int countUser(Map params) {
		return dao.countUser(params);
	}

	@SuppressWarnings("rawtypes")
	public static List<User> listAllUsers(Map params) {
		return dao.listAllUsers(params);
	}

	public static User searchById(int id) {
		return dao.searchById(id);
	}

	public static User searchUserByCheckoutId(int id) {
		return dao.searchUserByCheckoutId(id);
	}

	public static User searchUserByCompaniesUserId(int id) {
		return dao.searchUserByCompaniesUserId(id);
	}

	public static List<User> createUser(User user) {
		return dao.createUser(user);
	}

	public static void recovery(String email) {
		dao.recovery(email);
	}

	/**
	 * Caso TRUE - ja existe um usuario com o mesmo email. Caso FALSE - não foi
	 * encontrado nenhum usuario com esse email
	 * 
	 * @param email
	 * @return
	 */
	public static boolean verifyUser(String email) {
		return dao.verifyUser(email);
	}

	public static void uploadUser(User user) {
		dao.uploadUser(user);
	}

	@SuppressWarnings("rawtypes")
	public static List<User> listFirstUsers(Map params) {
		return dao.listFirstUsers(params);
	}

	public static List<User> userLogin(User user) {
		return dao.userLogin(user);
	}

	public static void saveData(Map params) {
		dao.saveData(params);
	}

	/**
	 * Salva dados principais do usuario (Nome, Email, gender e dtNascimento)
	 * 
	 * @param user
	 */
	public static void saveMainData(User user) {
		dao.saveMainData(user);
	}

	public static void saveAddressData(User user) {
		dao.saveAddressData(user);
	}
	
	public static void userUsing(int userId) {
		dao.userUsing(userId);
	}
	
	public static void createUserUsing(int userId, String deviceToken) {
		dao.createUserUsing(userId, deviceToken);
	}

}
