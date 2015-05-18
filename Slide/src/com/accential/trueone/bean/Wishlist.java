package com.accential.trueone.bean;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
/**
 * 
 * @author Matheus Odilon - accentialbrasil
 *
 */
public class Wishlist implements Serializable{

	private static final long serialVersionUID = 1L;

	public static final String KEY = "users_wishlists";
	
	private int id;
	//user_id
	private User user;
	//category_id
	private CompanyCategory category;
	//sub_category_id
	private CompanySubCategory subCategory;
	private String name;
	private String description;
	private Calendar endsAt;
	private String status;
	private Calendar dateRegister;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public CompanyCategory getCategory() {
		return category;
	}
	public void setCategory(CompanyCategory category) {
		this.category = category;
	}
	public CompanySubCategory getSubCategory() {
		return subCategory;
	}
	public void setSubCategory(CompanySubCategory subCategory) {
		this.subCategory = subCategory;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Calendar getEndsAt() {
		return endsAt;
	}
	public void setEndsAt(Calendar endsAt) {
		this.endsAt = endsAt;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Calendar getDateRegister() {
		return dateRegister;
	}
	public void setDateRegister(Calendar dateRegister) {
		this.dateRegister = dateRegister;
	}
	
	
	
}
