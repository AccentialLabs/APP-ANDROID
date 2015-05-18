package com.accential.trueone.bean;

import java.io.Serializable;
/**
 * @author Matheus Odilon - accentialbrasil
 */
public class CompanySubCategory implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final String KEY = "companies_sub_categories";

	private int id;
	private CompanyCategory category;
	private String name;
	private String photo;


	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public CompanyCategory getCategory() {
		return category;
	}
	public void setCategory(CompanyCategory category) {
		this.category = category;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}

	//METODO USADO PRA RETORNAR O NOME DA SUBCATEGORIA NA SPINNER DE CADASTRO (ver: WishlistInclude)
	public String toString(){
		return (this.getName());
	}

}
