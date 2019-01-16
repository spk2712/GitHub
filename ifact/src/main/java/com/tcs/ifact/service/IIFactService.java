package com.tcs.ifact.service;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;

import com.tcs.ifact.bobj.UserloginBObj;
import com.tcs.ifact.bobj.PasswordForgotBObj;
import com.tcs.ifact.bobj.PasswordResetBObj;
import com.tcs.ifact.bobj.UserRegistrationBObj;

public interface IIFactService {

	ResponseEntity<Object> forgotPassword(PasswordForgotBObj pfObj);

	ResponseEntity<Object> registration(UserRegistrationBObj regBOBj);

	ResponseEntity<Object> restPasswordFP(PasswordResetBObj prObj);

	ResponseEntity<Object> restPasswordPR(PasswordResetBObj prObj);

	ResponseEntity<Object> login(UserloginBObj login);

	
}
