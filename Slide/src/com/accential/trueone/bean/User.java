package com.accential.trueone.bean;

import java.io.Serializable;
import java.util.Calendar;

/**
 * @author WIlson Junior
 */
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	//chave utilizada para recupera��o de extras nas intents
	public static final String KEY = "users";

	private int id;
	/*criar bean users*/
	private String name;
	private String email;
	private String gender;
	private String password;
	private Calendar birthday;
	private String address;
	private String city;
	private String zip_code;
	private String state;
	private String district;
	private String number;
	private String complement;
	private String photo;
	private String status;
	private Calendar date_register;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Calendar getBirthday() {
		return birthday;
	}
	public void setBirthday(Calendar birthday) {
		this.birthday = birthday;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getZip_code() {
		return zip_code;
	}
	public void setZip_code(String zip_code) {
		this.zip_code = zip_code;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
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
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Calendar getDate_register() {
		return date_register;
	}
	public void setDate_register(Calendar date_register) {
		this.date_register = date_register;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public static String getKey() {
		return KEY;
	}

}
