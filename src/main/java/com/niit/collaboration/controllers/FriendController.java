package com.niit.collaboration.controllers;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.niit.collaboration.dao.FriendsDAO;
import com.niit.collaboration.model.Friends;
import com.niit.collaboration.model.UserDetails;

@RestController

public class FriendController {

	@Autowired
	FriendsDAO friendsDAO;
	
	@Autowired
	Friends friends;
	
	@RequestMapping(value="/myFriends",method = RequestMethod.GET)
    public ResponseEntity<List<Friends>> getMyFriends(HttpSession session) {
		
		UserDetails loggedInUser = (UserDetails) session.getAttribute("loggedInUser");
		List<Friends> myFriends = friendsDAO.getMyFriend(loggedInUser.getUserId());
		return new ResponseEntity<List<Friends>>(myFriends, HttpStatus.OK);
	}
	
	@RequestMapping(value="/addFriend/{friendId}", method = RequestMethod.POST)
	public ResponseEntity<Friends> sendFriendRequest(@PathVariable("friendId") String friendId,HttpSession session) {
		
		UserDetails loggedInUser=(UserDetails) session.getAttribute("loggedInUser");
		friends.setUserId(loggedInUser.getUserId());
		friends.setFriendId(friendId);
		friends.setStatus('N');//A - Accepted, N - New , R- Rejected 
		friends.setIsOnline('Y');
		friendsDAO.save(friends);
		return new ResponseEntity<Friends>(friends, HttpStatus.OK);
	}
	
	@RequestMapping(value="/unFriend/{friendId}", method = RequestMethod.GET)
	public ResponseEntity<Friends> unFriend(@PathVariable("friendId") String friendId, HttpSession session) {
		
		UserDetails loggedInUser = (UserDetails) session.getAttribute("loggedInUser");
		friends.setUserId(loggedInUser.getUserId());
		friends.setFriendId(friendId);
		friends.setStatus('U');
		friendsDAO.save(friends);
		return new ResponseEntity<Friends>(friends, HttpStatus.OK);
	}
}
