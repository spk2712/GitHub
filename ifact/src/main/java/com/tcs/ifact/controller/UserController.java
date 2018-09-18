package com.tcs.ifact.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tcs.ifact.bobj.UserBObj;
import com.tcs.ifact.service.IUserService;

@RestController
@RequestMapping(path="/ifact/user")
@Secured ({"ROLE_ADMIN"})
public class UserController {
	@Autowired
	IUserService iuserService;
	
	@RequestMapping(value = "/getAllUser",method=RequestMethod.GET)
	public ResponseEntity<Object> getAllUser() {
		System.out.println("Welcome to getAllUser");
		return iuserService.getAllUser();
		
	}
	
	@RequestMapping(value = "/getUserByRole",method=RequestMethod.GET)
	public ResponseEntity<Object> getUserByRole(@RequestParam String role) {
		System.out.println("Welcome to getUserByRole");
		return iuserService.getUserByRole(role);
		
	}
	
	@RequestMapping(value = "/getUserByProject",method=RequestMethod.GET)
	public ResponseEntity<Object> getUserByProject(@RequestParam String project) {
		System.out.println("Welcome to getUserByProject");
		return iuserService.getUserByProject(project);
		
	}
	
	@RequestMapping(value = "/getUserByUserId",method=RequestMethod.GET)
	public ResponseEntity<Object> getUserByUserId(@RequestParam String user) {
		System.out.println("Welcome to getUserByUserId");
		return iuserService.getUserByUserId(user);
		
	}
	
	
	@RequestMapping(value = "/addUser",method=RequestMethod.PUT)
	public ResponseEntity<String> addUser(@RequestBody UserBObj userBObj) {
		System.out.println("Welcome to addUser");
		return iuserService.addUser(userBObj);
		
	}
	
	@RequestMapping(value= "/updateUser",method=RequestMethod.PUT)
	public ResponseEntity<String> updateUser(@RequestBody UserBObj userBObj) {
		System.out.println("Welcome to updateUser");
		return iuserService.updateUser(userBObj);
	
	}
	
	@RequestMapping(value= "/deleteByUserId",method=RequestMethod.DELETE)
	public ResponseEntity<String> deleteByUserId(@RequestParam String user) {
		System.out.println("Welcome to deleteByUserId");
		return iuserService.deleteByUserId(user);
	
	}
	
	@RequestMapping(value= "/deleteAllUser",method=RequestMethod.DELETE)
	public ResponseEntity<String> deleteAllUser() {
		System.out.println("Welcome to deleteAllUser");
		return iuserService.deleteAllUser();
	
	}
	
	@RequestMapping(value= "/enableAndDisableUser",method=RequestMethod.PUT)
	public ResponseEntity<String> enableAndDisableUser(@RequestParam String user,@RequestParam String flag) {
		System.out.println("Welcome to deleteAllUser");
		return iuserService.enableAndDisableUser(user,flag);
	
	}

}
