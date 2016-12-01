package com.niit.collaboration.controllers;


import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.niit.collaboration.dao.FriendDAO;
import com.niit.collaboration.dao.UserDetailsDAO;

import com.niit.collaboration.model.UserDetails;

@RestController
public class UserController {
@Autowired 
UserDetailsDAO userDetailsDAO;
@Autowired
FriendDAO friendDAO;
@Autowired
UserDetails userDetails;

@RequestMapping(value = "/register/", method = RequestMethod.POST)
public ResponseEntity<UserDetails> addNewUser(@RequestBody UserDetails user) {
	user.setStatus('N');
	user.setIsOnline('N');
	user.setRole("Role_User");
	if (userDetailsDAO.saveUser(user)) {

		user.setErrorCode("200");
		user.setErrorMessage("Successfully registered as user!");
		
		return new ResponseEntity<UserDetails>(user, HttpStatus.OK);
	} else {
		if(userDetailsDAO.getUser(user.getUserId())!=null){
			user.setErrorCode("404");
			user.setErrorMessage("user already exists with this Id:"+user.getUserId());
			return new ResponseEntity<UserDetails>(user, HttpStatus.OK);
		}
		UserDetails userDetails = new UserDetails();
		userDetails.setUserId("notRegisted");
		userDetails.setErrorCode("404");
		userDetails.setErrorMessage("Couldn't register!");
		
		return new ResponseEntity<UserDetails>(userDetails, HttpStatus.OK);
	}

}

@RequestMapping(value = "/users", method = RequestMethod.GET)
public ResponseEntity<List<UserDetails>> getUserList() {
	List<UserDetails> userList = userDetailsDAO.getUserList();
	if (userList.isEmpty()) {
		userDetails.setErrorCode("404");
		userDetails.setErrorMessage("Users are not available");
		userList.add(userDetails);
	}
	return new ResponseEntity<List<UserDetails>>(userList, HttpStatus.OK);
}
@RequestMapping(value = "/user/{userId}", method = RequestMethod.GET)
public ResponseEntity<UserDetails> getUser(@PathVariable(value = "userId") String userId) {
	userDetails = userDetailsDAO.getUser(userId);
	if (userDetails == null) {
		userDetails = new UserDetails();
		userDetails.setErrorCode("404");
		userDetails.setErrorMessage("User with this id doesnt exist " + userId);
	}
	return new ResponseEntity<UserDetails>(userDetails, HttpStatus.OK);
}


@RequestMapping(value= "/makeAdmin/{userId}", method= RequestMethod.PUT)
public ResponseEntity<UserDetails> makeAdmin(@PathVariable("userId") String userId){
	userDetails=userDetailsDAO.getUser(userId);
	if(userDetails==null){
		userDetails=new UserDetails();
		userDetails.setErrorCode("404");
		userDetails.setErrorMessage("user doesnt exist with this id");
		return new ResponseEntity<UserDetails>(userDetails, HttpStatus.NOT_FOUND);
	}
	userDetails.setRole("Role_Admin");
	userDetailsDAO.updateUser(userDetails);
	userDetails.setErrorCode("200");
	userDetails.setErrorMessage("role updated as admin successfully");
	return new ResponseEntity<UserDetails>(userDetails, HttpStatus.OK);
}
@RequestMapping(value = "/login", method = RequestMethod.POST)
public ResponseEntity<UserDetails> authenticate(@RequestBody UserDetails userDetails, HttpSession httpSession) {

	userDetails = userDetailsDAO.authenticate(userDetails.getUserId(), userDetails.getPassword());
	if (userDetails == null) {
		userDetails = new UserDetails();
		userDetails.setErrorCode("404");
		userDetails.setErrorMessage("Incorrect userName and password , please try again...");

	} else {
		if (userDetails.getStatus() == 'Y') {
			userDetails.setErrorCode("200");
			userDetails.setErrorMessage("You are logged in as user");
			httpSession.setAttribute("loggedInUserId", userDetails.getUserId());
			userDetails.setIsOnline('Y');
			userDetailsDAO.updateUser(userDetails);
		}else{
			userDetails.setErrorCode("200");
			userDetails.setErrorMessage("Unauthorized entry!");
			userDetails.setIsOnline('N');
			userDetails.setStatus('N');
		}
	}
	return new ResponseEntity<UserDetails>(userDetails, HttpStatus.OK);
}
@RequestMapping(value = "/logout", method = RequestMethod.GET)
public ResponseEntity<UserDetails> logOut(HttpSession httpSession) {
	String loggedInUser = (String) httpSession.getAttribute("loggedInUserId");

	userDetails = userDetailsDAO.getUser(loggedInUser);
	userDetails.setIsOnline('N');

	if (userDetailsDAO.updateUser(userDetails)) {
		userDetails.setErrorMessage("successfully logged out");
		userDetails.setErrorCode("200");
	} else {
		userDetails.setErrorMessage("error logging out");
		userDetails.setErrorCode("404");
	}
	
	httpSession.invalidate();
	return new ResponseEntity<UserDetails>(userDetails, HttpStatus.OK);

}


























@RequestMapping(value="/accept/{userId}", method=RequestMethod.GET)
public ResponseEntity<UserDetails> accept(@PathVariable("userId") String userId){
	userDetails=updateStatus(userId,'A',"");
	if(userDetailsDAO.updateUser(userDetails)){
		userDetails.setErrorCode("200");
		userDetails.setErrorMessage("successfully updated");
	}else{
		userDetails.setErrorCode("404");
		userDetails.setErrorMessage("not able to update");

}
	return new ResponseEntity<UserDetails>(userDetails, HttpStatus.OK);
}
@RequestMapping(value="/reject/{userId}/{reason}")
public ResponseEntity<UserDetails> reject(@PathVariable("userId") String userId, @PathVariable("reason") String reason){
	userDetails=userDetailsDAO.getUser(userId);
	userDetails.setStatus('R');
	userDetails.setReason(reason);
	if(userDetailsDAO.updateUser(userDetails)==true){
		userDetails.setErrorCode("200");
		userDetails.setErrorMessage("successfully rejected");
	}else{
		userDetails.setErrorCode("404");
		userDetails.setErrorMessage("couldn't reject the user");
		
	}
	return new ResponseEntity<UserDetails>(userDetails, HttpStatus.OK);
	}

























private UserDetails updateStatus(String id, char status, String reason) {
	userDetails=userDetailsDAO.getUser(id);
	if(userDetails==null){
		userDetails.setErrorCode("404");
		userDetails.setErrorMessage("couldnt update status to"+ status);
	}else{
		userDetails.setStatus(status);
		userDetails.setReason(reason);
		userDetailsDAO.updateUser(userDetails);
		userDetails.setErrorCode("200");
		userDetails.setErrorMessage("successfully updated");
		
	}

	return userDetails;
}
}
