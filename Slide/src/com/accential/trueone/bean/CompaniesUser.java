package com.accential.trueone.bean;

import java.io.Serializable;
import java.util.Calendar;

/**
 * Representa a assinatura do Cliente à uma Company
 * @author Matheus Odilon - accentialbrasil
 *
 */
public class CompaniesUser implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public static final String KEY = "companies_users";
	
	private int id;
	private Company company;
	private User user;
	private String status;
	private String last_status;
	private Calendar dateRegister;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Company getCompany() {
		return company;
	}
	public void setCompany(Company company) {
		this.company = company;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getLast_status() {
		return last_status;
	}
	public void setLast_status(String last_status) {
		this.last_status = last_status;
	}
	public Calendar getDateRegister() {
		return dateRegister;
	}
	public void setDateRegister(Calendar dateRegister) {
		this.dateRegister = dateRegister;
	}
	
}
