package com.niit.collaboration.Controller;


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


import com.niit.collaboration.dao.UserDAO;

import com.niit.collaboration.model.User;

@RestController
public class UserController {
@Autowired 
UserDAO userDAO;
@Autowired
User user;

@RequestMapping(value = "/register/", method = RequestMethod.POST)
public ResponseEntity<User> addNewUser(@RequestBody User user) {
	user.setStatus('N');// if the admin accepts the registration, the status will set to 'Y'
	user.setIsOnline('N');
	user.setRole("Role_User");
	if (userDAO.saveUser(user)) {

		user.setErrorCode("200");
		user.setErrorMessage("Successfully registered as user!");
		
		return new ResponseEntity<User>(user, HttpStatus.OK);
	} else {
		if(userDAO.getUser(user.getUserId())!=null){
			user.setErrorCode("404");
			user.setErrorMessage("user already exists with this Id:"+user.getUserId());
			return new ResponseEntity<User>(user, HttpStatus.OK);
		}
		User user1 = new User();
		user1.setUserId("notRegisted");
		user1.setErrorCode("404");
		user1.setErrorMessage("Couldn't register!");
		
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

}

@RequestMapping(value = "/users", method = RequestMethod.GET)
public ResponseEntity<List<User>> getUserList() {
	List<User> userList = userDAO.getUserList();
	if (userList.isEmpty()) {
		user.setErrorCode("404");
		user.setErrorMessage("Users are not available");
		userList.add(user);
	}
	return new ResponseEntity<List<User>>(userList, HttpStatus.OK);
}
@RequestMapping(value = "/user/{userId}", method = RequestMethod.GET)
public ResponseEntity<User> getUser(@PathVariable(value = "userId") String userId) {
	user = userDAO.getUser(userId);
	if (user == null) {
		user = new User();
		user.setErrorCode("404");
		user.setErrorMessage("User with this id doesnt exist " + userId);
	}
	return new ResponseEntity<User>(user, HttpStatus.OK);
}


@RequestMapping(value= "/makeAdmin/{userId}", method= RequestMethod.PUT)
public ResponseEntity<User> makeAdmin(@PathVariable("userId") String userId){
	user=userDAO.getUser(userId);
	if(user==null){
		user=new User();
		user.setErrorCode("404");
		user.setErrorMessage("user doesnt exist with this id");
		return new ResponseEntity<User>(user, HttpStatus.NOT_FOUND);
	}
	user.setRole("Role_Admin");
	userDAO.updateUser(user);
	user.setErrorCode("200");
	user.setErrorMessage("role updated as admin successfully");
	return new ResponseEntity<User>(user, HttpStatus.OK);
}
@RequestMapping(value = "/login", method = RequestMethod.POST)
public ResponseEntity<User> authenticate(@RequestBody User user, HttpSession httpSession) {

	user = userDAO.authenticate(user.getUserId(), user.getPassword());
	if (user == null) {
		user = new User();
		user.setErrorCode("404");
		user.setErrorMessage("Incorrect userName and password , please try again...");

	} else {
		if (user.getStatus() == 'Y') {
			user.setErrorCode("200");
			user.setErrorMessage("You are logged in as user");
			httpSession.setAttribute("loggedInUserId", user.getUserId());
			user.setIsOnline('Y');
			userDAO.updateUser(user);
		}else{
			user.setErrorCode("404");
			user.setErrorMessage("Unauthorized entry!");
			user.setIsOnline('N');
			user.setStatus('N');
		}
	}
	return new ResponseEntity<User>(user, HttpStatus.OK);
}
@RequestMapping(value = "/logout", method = RequestMethod.GET)
public ResponseEntity<User> logOut(HttpSession httpSession) {
	String loggedInUser = (String) httpSession.getAttribute("loggedInUserId");

	user = userDAO.getUser(loggedInUser);
	user.setIsOnline('N');

	if (userDAO.updateUser(user)) {
		user.setErrorMessage("successfully logged out");
		user.setErrorCode("200");
	} else {
		user.setErrorMessage("error logging out");
		user.setErrorCode("404");
	}
	
	httpSession.invalidate();
	return new ResponseEntity<User>(user, HttpStatus.OK);

}


























@RequestMapping(value="/accept/{userId}", method=RequestMethod.GET)
public ResponseEntity<User> accept(@PathVariable("userId") String userId){
	user=updateStatus(userId,'A',"");
	if(userDAO.updateUser(user)){
		user.setErrorCode("200");
		user.setErrorMessage("successfully updated");
	}else{
		user.setErrorCode("404");
		user.setErrorMessage("not able to update");

}
	return new ResponseEntity<User>(user, HttpStatus.OK);
}
@RequestMapping(value="/reject/{userId}/{reason}")
public ResponseEntity<User> reject(@PathVariable("userId") String userId, @PathVariable("reason") String reason){
	user=userDAO.getUser(userId);
	user.setStatus('R');
	user.setReason(reason);
	if(userDAO.updateUser(user)==true){
		user.setErrorCode("200");
		user.setErrorMessage("successfully rejected");
	}else{
		user.setErrorCode("404");
		user.setErrorMessage("couldn't reject the user");
		
	}
	return new ResponseEntity<User>(user, HttpStatus.OK);
	}

























private User updateStatus(String id, char status, String reason) {
	user=userDAO.getUser(id);
	if(user==null){
		user.setErrorCode("404");
		user.setErrorMessage("couldnt update status to"+ status);
	}else{
		user.setStatus(status);
		user.setReason(reason);
		userDAO.updateUser(user);
		user.setErrorCode("200");
		user.setErrorMessage("successfully updated");
		
	}

	return user;
}
}
