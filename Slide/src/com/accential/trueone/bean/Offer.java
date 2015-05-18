package com.accential.trueone.bean;

import java.io.Serializable;
import java.util.Calendar;

/**
 * @author Henrique Alle
 */
public class Offer implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//chave utilizada para recuperacao de extras nas intents
	public static final String KEY = "offers";
	
	private int id;
	private Company company;
	private String title;
	private String resume;
	private String description;
	private String specification;
	private float value;
	private int percentageDiscount;
	private float weight;
	private int amountAllowed;
	private Calendar beginsAt;
	private Calendar endsAt;
	private String photo;
	private String metrics;
	private String parcels;
	private String parcelsOffImpost;
	private String publicStr;
	private String status;
	private String testeData;
	
	
	
	
	/*
	 * gets and sets
	 */
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getResume() {
		return resume;
	}
	public void setResume(String resume) {
		this.resume = resume;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getSpecification() {
		return specification;
	}
	public void setSpecification(String specification) {
		this.specification = specification;
	}
	public float getValue() {
		return value;
	}
	public void setValue(float value) {
		this.value = value;
	}
	public int getPercentageDiscount() {
		return percentageDiscount;
	}
	public void setPercentageDiscount(int percentageDiscount) {
		this.percentageDiscount = percentageDiscount;
	}
	public float getWeight() {
		return weight;
	}
	public void setWeight(float weight) {
		this.weight = weight;
	}
	public int getAmountAllowed() {
		return amountAllowed;
	}
	public void setAmountAllowed(int amountAllowed) {
		this.amountAllowed = amountAllowed;
	}
	public Calendar getBeginsAt() {
		return beginsAt;
	}
	public void setBeginsAt(Calendar beginsAt) {
		this.beginsAt = beginsAt;
	}
	public Calendar getEndsAt() {
		return endsAt;
	}
	public void setEndsAt(Calendar endsAt) {
		this.endsAt = endsAt;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public String getMetrics() {
		return metrics;
	}
	public void setMetrics(String metrics) {
		this.metrics = metrics;
	}
	public String getParcels() {
		return parcels;
	}
	public void setParcels(String parcels) {
		this.parcels = parcels;
	}
	public String getParcelsOffImpost() {
		return parcelsOffImpost;
	}
	public void setParcelsOffImpost(String parcelsOffImpost) {
		this.parcelsOffImpost = parcelsOffImpost;
	}
	public String getPublicStr() {
		return publicStr;
	}
	public void setPublicStr(String publicStr) {
		this.publicStr = publicStr;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Company getCompany() {
		return company;
	}
	public void setCompany(Company company) {
		this.company = company;
	}
	public String getTesteData() {
		return testeData;
	}
	public void setTesteData(String testeData) {
		this.testeData = testeData;
	}
}
