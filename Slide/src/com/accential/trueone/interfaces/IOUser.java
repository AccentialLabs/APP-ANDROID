package com.accential.trueone.interfaces;

import java.util.List;
import java.util.Map;

import com.accential.trueone.bean.User;

@SuppressWarnings("all")
public interface IOUser {

	@SuppressWarnings("rawtypes")
	List<User> listUsers(Map params);

	@SuppressWarnings("rawtypes")
	int countUser(Map params);

	@SuppressWarnings("rawtypes")
	List<User> listAllUsers(Map params);

	User searchById(int id);

	public User searchUserByCheckoutId(int id);

	public User searchUserByCompaniesUserId(int id);

	public void recovery(String email);

	public List<User> createUser(User user);

	public boolean verifyUser(String email);

	public void uploadUser(User user);

	public void saveMainData(User user);

	@SuppressWarnings("rawtypes")
	List<User> listFirstUsers(Map params);

	List<User> userLogin(User user);

	public void saveData(Map params);

	public void saveAddressData(User user);

	public void userUsing(int userId);

	public void createUserUsing(int userId, String deviceToken);
}
