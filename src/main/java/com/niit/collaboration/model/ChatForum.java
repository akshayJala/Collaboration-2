package com.niit.collaboration.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

@Entity
@Table(name="c_chat_FORUM")
@Component

public class ChatForum extends ErrorDomain {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private String id;
	private String userId;
	private String message;
	
	private int getId;
	private Date dateOfCreation;
	public Date getDateOfCreation() {
		return dateOfCreation;
	}
	public void setDateOfCreation(Date dateOfCreation) {
		this.dateOfCreation = dateOfCreation;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	public int getGetId() {
		return getId;
	}
	public void setGetId(int getId) {
		this.getId = getId;
	}

}
