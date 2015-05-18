package com.accential.trueone.bean;

import java.io.Serializable;

/**
 * Filtros da oferta
 * 
 * @author Matheus Odilon - accentialbrasil
 * 
 */
public class OfferFilter implements Serializable {

	private static final long serialVersionUID = 1L;

	private int id;
	private Offer offer;
	private String gender;
	private String religion;
	private String political;
	private String ageGroup;
	private String location;
	private String relatioshipStatus;

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

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getReligion() {
		return religion;
	}

	public void setReligion(String religion) {
		this.religion = religion;
	}

	public String getPolitical() {
		return political;
	}

	public void setPolitical(String political) {
		this.political = political;
	}

	public String getAgeGroup() {
		return ageGroup;
	}

	public void setAgeGroup(String ageGroup) {
		this.ageGroup = ageGroup;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getRelatioshipStatus() {
		return relatioshipStatus;
	}

	public void setRelatioshipStatus(String relatioshipStatus) {
		this.relatioshipStatus = relatioshipStatus;
	}

}
