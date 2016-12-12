package com.niit.collaboration.dao;

import java.util.List;

import com.niit.collaboration.model.User;

public interface UserDAO {
	public boolean saveUser(User userDetails);
	public boolean updateUser(User userDetails);
	public boolean removeUser(String userId);
	public User getUser(String userId);
	public List<User> getUserList();
	public void setOnline(String userId);
	public void setOffline(String userId);
	public User authenticate(String userId, String password);
	public boolean isUser(String userId);
	public void reject(String userId);
	public void approve(String userId);
}
