package com.accential.trueone.bean;

import java.io.Serializable;
import java.util.Calendar;

/**
 * @author Wilson Junior
 */
public class Company implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//chave utilizada para recuperacao de extras nas intents
	public static final String KEY = "companies";
	
	private int id;
	private String corporate_name;
	private String fancy_name;
	private String description;
	private String site_url;
	private int category_id;
	private int sub_category_id;
	private String cnpj;
	private String email;
	private String password;
	private String phone;
	private String phone_2;
	private String address;	
	private String city;
	private String zip_code;
	private String state;
	private String district;
	private String number;
	private String complement;
	private String responsible_name;
	private String responsible_cpf;
	private String responsible_email;
	private String responsible_phone;
	private String responsible_phone_2;
	private String responsible_cel_phone;
	private String logo;
	private String status;
	private String login_moip;
	private String register;
	private Calendar date_register;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCorporate_name() {
		return corporate_name;
	}
	public void setCorporate_name(String corporate_name) {
		this.corporate_name = corporate_name;
	}
	public String getFancy_name() {
		return fancy_name;
	}
	public void setFancy_name(String fancy_name) {
		this.fancy_name = fancy_name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getSite_url() {
		return site_url;
	}
	public void setSite_url(String site_url) {
		this.site_url = site_url;
	}
	public int getCategory_id() {
		return category_id;
	}
	public void setCategory_id(int category_id) {
		this.category_id = category_id;
	}
	public int getSub_category_id() {
		return sub_category_id;
	}
	public void setSub_category_id(int sub_category_id) {
		this.sub_category_id = sub_category_id;
	}
	public String getCnpj() {
		return cnpj;
	}
	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getPhone_2() {
		return phone_2;
	}
	public void setPhone_2(String phone_2) {
		this.phone_2 = phone_2;
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
	public String getResponsible_name() {
		return responsible_name;
	}
	public void setResponsible_name(String responsible_name) {
		this.responsible_name = responsible_name;
	}
	public String getResponsible_cpf() {
		return responsible_cpf;
	}
	public void setResponsible_cpf(String responsible_cpf) {
		this.responsible_cpf = responsible_cpf;
	}
	public String getResponsible_email() {
		return responsible_email;
	}
	public void setResponsible_email(String responsible_email) {
		this.responsible_email = responsible_email;
	}
	public String getResponsible_phone() {
		return responsible_phone;
	}
	public void setResponsible_phone(String responsible_phone) {
		this.responsible_phone = responsible_phone;
	}
	public String getResponsible_phone_2() {
		return responsible_phone_2;
	}
	public void setResponsible_phone_2(String responsible_phone_2) {
		this.responsible_phone_2 = responsible_phone_2;
	}
	public String getResponsible_cel_phone() {
		return responsible_cel_phone;
	}
	public void setResponsible_cel_phone(String responsible_cel_phone) {
		this.responsible_cel_phone = responsible_cel_phone;
	}
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getLogin_moip() {
		return login_moip;
	}
	public void setLogin_moip(String login_moip) {
		this.login_moip = login_moip;
	}
	public String getRegister() {
		return register;
	}
	public void setRegister(String register) {
		this.register = register;
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
