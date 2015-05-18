package com.accential.trueone.bean;

import java.io.Serializable;
import java.util.Calendar;

/**
 * 
 * @author Matheus Odilon - accentialbrasil
 *
 */
public class CompaniesInvitationsFilter implements Serializable{
	private static final long serialVersionUID = 1L;

	public static final String KEY = "companies_invitations_filters";
	
	private int id;
	private Company company;
	private String gender;
	private String religion;
	private String political;
	private String ageGroup;
	private String location;
	private String relationshipStatus;
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
	public String getRelationshipStatus() {
		return relationshipStatus;
	}
	public void setRelationshipStatus(String relationshipStatus) {
		this.relationshipStatus = relationshipStatus;
	}
	public Calendar getDateRegister() {
		return dateRegister;
	}
	public void setDateRegister(Calendar dateRegister) {
		this.dateRegister = dateRegister;
	}



}
