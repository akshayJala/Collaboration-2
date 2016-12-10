package com.niit.collaboration.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Component;
@Entity
@Table(name="c_event")
@Component
public class Event extends ErrorDomain{
	@Id
private String eventId;
private String eventLocation;
private String eventDate;
private String description;
public String getEventId() {
	return eventId;
}
public void setEventId(String eventId) {
	this.eventId = eventId;
}
public String getEventLocation() {
	return eventLocation;
}
public void setEventLocation(String eventLocation) {
	this.eventLocation = eventLocation;
}
public String getEventDate() {
	return eventDate;
}
public void setEventDate(String eventDate) {
	this.eventDate = eventDate;
}
public String getDescription() {
	return description;
}
public void setDescription(String description) {
	this.description = description;
}
}
