package com.niit.collaboration.dao;

import java.util.List;

import com.niit.collaboration.model.Event;

public interface EventDAO {
	public boolean saveEvent(Event event);
	public boolean updateEvent(Event event);
	public Event getEvent(String eventId);
	public List<Event> eventList();
	public boolean deleteEvent(String eventId);

}
