package com.accential.trueone.bean;

import java.io.Serializable;
import java.util.Calendar;

public class OffersUser implements Serializable {

	private static final long serialVersionUID = 1L;

	private int id;
	private Offer offer;
	private User user;
	private Calendar dtRegister;
	private String target;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Offer getOffer() {
		return offer;
	}

	public void setOffer(Offer offer) {
		this.offer = offer;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Calendar getDtRegister() {
		return dtRegister;
	}

	public void setDtRegister(Calendar dtRegister) {
		this.dtRegister = dtRegister;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

}
