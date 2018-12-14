package com.tcs.ifact.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.tcs.ifact.bobj.LoginBObj;
import com.tcs.ifact.bobj.PasswordForgotBObj;
import com.tcs.ifact.bobj.PasswordResetBObj;
import com.tcs.ifact.bobj.ResponseBObj;
import com.tcs.ifact.bobj.UserBObj;
import com.tcs.ifact.bobj.UserRegistrationBObj;
import com.tcs.ifact.handler.IFactHandler;
import com.tcs.ifact.model.UserInfo;

@Service
public class IFactServiceImpl implements IIFactService {

	@Autowired
	IFactHandler ifactHandler;

	@Override
	public ResponseEntity<Object> registration(UserRegistrationBObj regBOBj) {
		ResponseBObj res = new ResponseBObj();
		res = ifactHandler.registration(regBOBj);
		if(null != res) {
			if(res.isError()) {
				return new ResponseEntity<Object>(res,HttpStatus.EXPECTATION_FAILED);
			}else {
				return new ResponseEntity<Object>(res,HttpStatus.CREATED);
			}
		}
		return new ResponseEntity<Object>(res,HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Object> forgotPassword(PasswordForgotBObj pfObj) {
		ResponseBObj res = new ResponseBObj();
		res = ifactHandler.forgotPassword(pfObj);
		if(null != res) {
			if(res.isError()) {
				return new ResponseEntity<Object>(res,HttpStatus.EXPECTATION_FAILED);
			}else {
				return new ResponseEntity<Object>(res,HttpStatus.CREATED);
			}
		}
		return new ResponseEntity<Object>(res,HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Object> restPasswordFP(PasswordResetBObj prObj) {
		ResponseBObj res = new ResponseBObj();
		res = ifactHandler.restPasswordFP(prObj);
		if(null != res) {
			if(res.isError()) {
				return new ResponseEntity<Object>(res,HttpStatus.EXPECTATION_FAILED);
			}else {
				return new ResponseEntity<Object>(res,HttpStatus.CREATED);
			}
		}
		return new ResponseEntity<Object>(res,HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Object> restPasswordPR(PasswordResetBObj prObj) {
		ResponseBObj res = new ResponseBObj();
		res = ifactHandler.restPasswordPR(prObj);
		if(null != res) {
			if(res.isError()) {
				return new ResponseEntity<Object>(res,HttpStatus.EXPECTATION_FAILED);
			}else {
				return new ResponseEntity<Object>(res,HttpStatus.CREATED);
			}
		}
		return new ResponseEntity<Object>(res,HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Object> login(LoginBObj login) {
		ResponseBObj res = new ResponseBObj();
		res = ifactHandler.login(login);
		if(null != res) {
			if(res.isError()) {
				return new ResponseEntity<Object>(res,HttpStatus.FORBIDDEN);
			}else {
				return new ResponseEntity<Object>(res,HttpStatus.ACCEPTED);
			}
		}
		return new ResponseEntity<Object>(res,HttpStatus.OK);
	}







}
