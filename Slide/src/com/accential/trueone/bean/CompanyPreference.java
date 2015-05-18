package com.accential.trueone.bean;

import java.io.Serializable;

/**
 * Objeto Preferencia da companhia
 * 
 * @author Matheus Odilon - accentialbrasil
 * 
 */
public class CompanyPreference implements Serializable {

	private static final long serialVersionUID = 1L;

	private int id;
	private Company company;
	private String cpf;
	private String bank;
	private String agency;
	private String account;
	private String accountName;
	private String backAccountStatus;
	private float shippingValue;
	private int deliveryTime;
	private boolean useCorreiosApi;

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

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public String getAgency() {
		return agency;
	}

	public void setAgency(String agency) {
		this.agency = agency;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getBackAccountStatus() {
		return backAccountStatus;
	}

	public void setBackAccountStatus(String backAccountStatus) {
		this.backAccountStatus = backAccountStatus;
	}

	public float getShippingValue() {
		return shippingValue;
	}

	public void setShippingValue(float shippingValue) {
		this.shippingValue = shippingValue;
	}

	public int getDeliveryTime() {
		return deliveryTime;
	}

	public void setDeliveryTime(int deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

	public boolean isUseCorreiosApi() {
		return useCorreiosApi;
	}

	public void setUseCorreiosApi(boolean useCorreiosApi) {
		this.useCorreiosApi = useCorreiosApi;
	}

}
