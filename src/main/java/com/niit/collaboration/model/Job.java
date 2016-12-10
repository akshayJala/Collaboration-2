package com.niit.collaboration.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Component;
@Entity
@Table(name="c_job")
@Component
public class Job extends ErrorDomain {
	@Id
private int jobId;
	private String title;
	private String UserId;
private String profile;
private String salary;
private String description;
private String qualifications;
private char status;
public String getTitle() {
	return title;
}
public void setTitle(String title) {
	this.title = title;
}
public char getStatus() {
	return status;
}
public void setStatus(char status) {
	this.status = status;
}
public String getUserId() {
	return UserId;
}
public void setUserId(String UserId) {
	this.UserId = UserId;
}
public int getJobId() {
	return jobId;
}
public void setJobId(int jobId) {
	this.jobId = jobId;
}
public String getProfile() {
	return profile;
}
public void setProfile(String profile) {
	this.profile = profile;
}
public String getSalary() {
	return salary;
}
public void setSalary(String salary) {
	this.salary = salary;
}
public String getDescription() {
	return description;
}
public void setDescription(String description) {
	this.description = description;
}

public String getQualifications() {
	return qualifications;
}
public void setQualifications(String qualifications) {
	this.qualifications = qualifications;
}

}
