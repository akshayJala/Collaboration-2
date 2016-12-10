package com.niit.collaboration.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Component;
@Entity
@Table(name="c_blog")
@Component
public class Blog extends ErrorDomain {
	@Id
private int blogId;
private String title;
private String description;
private String userId;
private char status;
private String reason;
private Date dateofcreation;
public Date getDateofcreation() {
	return dateofcreation;
}
public void setDateofcreation(Date dateofcreation) {
	this.dateofcreation = dateofcreation;
}
public int getBlogId() {
	return blogId;
}
public void setBlogId(int blogId) {
	this.blogId = blogId;
}
public String getTitle() {
	return title;
}
public void setTitle(String title) {
	this.title = title;
}
public String getDescription() {
	return description;
}
public void setDescription(String description) {
	this.description = description;
}
public String getUserId() {
	return userId;
}
public void setUserId(String userId) {
	this.userId = userId;
}
public char getStatus() {
	return status;
}
public void setStatus(char status) {
	this.status = status;
}
public String getReason() {
	return reason;
}
public void setReason(String reason) {
	this.reason = reason;
}



}
