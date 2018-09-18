package com.tcs.ifact.service;

import org.springframework.http.ResponseEntity;

import com.tcs.ifact.bobj.PasswordForgotBObj;
import com.tcs.ifact.bobj.PasswordResetBObj;
import com.tcs.ifact.bobj.UserRegistrationBObj;

public interface IIFactService {

	ResponseEntity<Object> forgotPassword(PasswordForgotBObj pfObj);

	ResponseEntity<Object> registration(UserRegistrationBObj regBOBj);

	ResponseEntity<Object> restPasswordPF(PasswordResetBObj prObj);

	ResponseEntity<Object> restPasswordPR(PasswordResetBObj prObj);

	
}
