package com.accential.trueone.bean;

import java.io.Serializable;

/**
 * Objeto Foto extra da oferta
 * 
 * @author Matheus Odilon - accentialbrasil
 * 
 */
public class OfferPhoto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int id;
	private Offer offer;
	private String photoUrl;

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

	public String getPhotoUrl() {
		return photoUrl;
	}

	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}

	public String toString() {
		return (this.getPhotoUrl());
	}

}
