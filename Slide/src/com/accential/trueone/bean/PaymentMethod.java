package com.accential.trueone.bean;

import java.io.Serializable;

public class PaymentMethod implements Serializable{
	
	public static final String KEY = "payment_methods";
	
	private int id;
	private String type;
	private String status;
	private String lastStatus;
	private String name;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getLastStatus() {
		return lastStatus;
	}
	public void setLastStatus(String lastStatus) {
		this.lastStatus = lastStatus;
	}
	
	
	
	

}
