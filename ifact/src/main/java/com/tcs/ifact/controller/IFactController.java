package com.tcs.ifact.controller;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.tcs.ifact.bobj.UserloginBObj;
import com.tcs.ifact.bobj.PasswordForgotBObj;
import com.tcs.ifact.bobj.PasswordResetBObj;
import com.tcs.ifact.bobj.UserBObj;
import com.tcs.ifact.bobj.UserRegistrationBObj;
import com.tcs.ifact.service.IIFactService;

@RestController
@RequestMapping(path="/ifact")
public class IFactController {
	
	private static final Logger logger = LogManager.getLogger(IFactController.class);
	
	@Autowired
	IIFactService iFactService;
	
	@RequestMapping(value="",method=RequestMethod.GET)
	public ResponseEntity<String> iFact() {
		String resp = "Welcome to iFact";	
		return new ResponseEntity<String>(resp, HttpStatus.OK);
	}
	
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public ResponseEntity<Object> ifactlogin(@RequestBody UserloginBObj pfObj) {
		logger.debug("Welcome to iFact login");	
		ResponseEntity<Object> resp = iFactService.login(pfObj);
		return new ResponseEntity<Object>(resp, HttpStatus.OK);
	}
	
	@RequestMapping(value="/forgotPassword",method=RequestMethod.POST)
	public ResponseEntity<Object> forgotPassword(@RequestBody @Valid PasswordForgotBObj pfObj) {
		logger.debug("Welcome to iFact forgotPassword");	
		ResponseEntity<Object> resp = iFactService.forgotPassword(pfObj);
		return new ResponseEntity<Object>(resp, HttpStatus.OK);
	}
	
	@RequestMapping(value="/restPasswordFP",method=RequestMethod.POST)
	public ResponseEntity<Object> restPasswordFP(@RequestBody @Valid PasswordResetBObj prObj) {
		logger.debug("Welcome to iFact restPassword");	
		ResponseEntity<Object> resp = iFactService.restPasswordFP(prObj);
		return new ResponseEntity<Object>(resp, HttpStatus.OK);
	}
	
	@RequestMapping(value="/restPasswordPR",method=RequestMethod.POST)
	public ResponseEntity<Object> restPasswordPR(@RequestBody @Valid PasswordResetBObj prObj) {
		logger.debug("Welcome to iFact restPassword");	
		ResponseEntity<Object> resp = iFactService.restPasswordPR(prObj);
		return new ResponseEntity<Object>(resp, HttpStatus.OK);
	}
	
	@RequestMapping(value="/registration",method=RequestMethod.POST)
	public ResponseEntity<Object> registration(@RequestBody @Valid UserRegistrationBObj regBOBj) {
		logger.debug("Welcome to iFact registration");	
		ResponseEntity<Object> resp = iFactService.registration(regBOBj);
		return new ResponseEntity<Object>(resp, HttpStatus.OK);
	}

}
