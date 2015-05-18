package com.accential.trueone.bean;

import java.io.Serializable;
import java.util.Calendar;

/**
 * Representa perfil do usu‡rio cadastrado pelo facebook
 * @author Matheus Odilon - accentialbrasil
 *
 */
public class FacebookProfile implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private int id;
	private String facebookId;
	private User user;
	private String name;
	private String firstName;
	private String lastName;
	private String email;
	private String gender;
	private String profileLink;
	private Calendar birthday;
	private String location;
	private String relationshipStatus;
	private String religion;
	private String political;
	private Calendar updatedTime;
	
	/**
	 *GETTERS AND SETTERS 
	 */
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFacebookId() {
		return facebookId;
	}
	public void setFacebookId(String facebookId) {
		this.facebookId = facebookId;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
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
	public String getProfileLink() {
		return profileLink;
	}
	public void setProfileLink(String profileLink) {
		this.profileLink = profileLink;
	}
	public Calendar getBirthday() {
		return birthday;
	}
	public void setBirthday(Calendar birthday) {
		this.birthday = birthday;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getRelationshipStatus() {
		return relationshipStatus;
	}
	public void setRelationshipStatus(String relationshipStatus) {
		this.relationshipStatus = relationshipStatus;
	}
	public String getReligion() {
		return religion;
	}
	public void setReligion(String religion) {
		this.religion = religion;
	}
	public String getPolitical() {
		return political;
	}
	public void setPolitical(String political) {
		this.political = political;
	}
	public Calendar getUpdatedTime() {
		return updatedTime;
	}
	public void setUpdatedTime(Calendar updatedTime) {
		this.updatedTime = updatedTime;
	}
	
	
	

}
