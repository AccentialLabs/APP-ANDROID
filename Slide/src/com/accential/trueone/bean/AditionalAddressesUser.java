package com.accential.trueone.bean;

import java.io.Serializable;

/**
 * Endereco adicional do cliente, usado no checkout
 * 
 * @author Matheus Odilon - accentialbrasil
 * 
 */
public class AditionalAddressesUser implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final String KEY = "aditional_addresses_users";

	private int id;
	private User user;
	// casa - empresa - condominio
	private String label;
	// rua - avenida - passagem
	private String address;
	// number String? ����? '-
	private String number;
	private String complement;
	// bairro
	private String district;
	private String city;
	private String state;
	// cep
	private String zipCode;

	public AditionalAddressesUser(int id, User user, String label,
			String address, String number, String complement, String district,
			String city, String state, String zipCode) {
		this.id = id;
		this.user = user;
		this.label = label;
		this.address = address;
		this.number = number;
		this.complement = complement;
		this.district = district;
		this.city = city;
		this.state = state;
		this.zipCode = zipCode;
	}

	public AditionalAddressesUser() {

	}

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

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getComplement() {
		return complement;
	}

	public void setComplement(String complement) {
		this.complement = complement;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String toString() {
		return (this.getLabel());
	}

}
