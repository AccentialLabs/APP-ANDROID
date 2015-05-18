package com.accential.trueone.bean;

import java.io.Serializable;
import java.util.Calendar;
/**
 * Representa comentário/avaliação do usuario sobre alguma oferta especifica 
 * @author Matheus Odilon - accentialbrasil
 *
 */
public class OffersComment implements Serializable{

	private static final long serialVersionUID = 1L;

	public static final String KEY = "offers_comments"; 
	
	private int id;
	private Offer offer;
	private User user;
	private String title;
	private String descricao;
	private String evaluation;
	private Calendar dateRegister;
	private String status;
	
	/**
	 * getters and setters
	 * @return
	 */
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
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getEvaluation() {
		return evaluation;
	}
	public void setEvaluation(String evaluation) {
		this.evaluation = evaluation;
	}
	public Calendar getDateRegister() {
		return dateRegister;
	}
	public void setDateRegister(Calendar dateRegister) {
		this.dateRegister = dateRegister;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
	
	

}
