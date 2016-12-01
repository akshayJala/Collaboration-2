package com.niit.collaboration.dao;

import java.util.List;

import com.niit.collaboration.model.UserDetails;

public interface UserDetailsDAO {
	public boolean saveUser(UserDetails userDetails);
	public boolean updateUser(UserDetails userDetails);
	public boolean removeUser(String userId);
	public UserDetails getUser(String userId);
	public List<UserDetails> getUserList();
	public void setOnline(String userId);
	public void setOffline(String userId);
	public UserDetails authenticate(String userId, String password);
	public boolean isUser(String userId);
	public void reject(String userId);
	public void approve(String userId);
}
