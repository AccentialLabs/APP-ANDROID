package com.accential.trueone.bean;

import java.io.Serializable;

/**
 * Representa a oferta/proposta de uma empresa para o 
 * desejo de um determinado usuario
 * @author Matheus Odilon - accentialbrasil
 * @version 1.0
 *
 */
public class UsersWishlistCompany implements Serializable{
	private static final long serialVersionUID = 1L;

	private int id;
	private Company company;
	private Wishlist wishlist;
	private String status;
	private Offer offer;

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
	public Wishlist getWishlist() {
		return wishlist;
	}
	public void setWishlist(Wishlist wishlist) {
		this.wishlist = wishlist;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Offer getOffer() {
		return offer;
	}
	public void setOffer(Offer offer) {
		this.offer = offer;
	}



}

