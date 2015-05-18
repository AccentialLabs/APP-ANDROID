package com.accential.trueone.bean;

import java.io.Serializable;
import java.util.Calendar;

/**
 * Representa o convite de Company para User
 * @author Matheus Odilon - accentialbrasil
 *
 */
public class CompaniesInvitationsUser implements Serializable{
	private static final long serialVersionUID = 1L;

	public static final String KEY = "companies_invitations_users";

	private int id;
	private CompaniesInvitationsFilter invitation;
	private User user;
	private Company company;
	private String status;
	private String preview;
	private Calendar dateRegister;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public CompaniesInvitationsFilter getInvitation() {
		return invitation;
	}
	public void setInvitation(CompaniesInvitationsFilter invitation) {
		this.invitation = invitation;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Company getCompany() {
		return company;
	}
	public void setCompany(Company company) {
		this.company = company;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getPreview() {
		return preview;
	}
	public void setPreview(String preview) {
		this.preview = preview;
	}
	public Calendar getDateRegister() {
		return dateRegister;
	}
	public void setDateRegister(Calendar dateRegister) {
		this.dateRegister = dateRegister;
	}
}
