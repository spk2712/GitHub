package com.tcs.ifact.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.tcs.ifact.bobj.ResponseBObj;
import com.tcs.ifact.bobj.UserBObj;
import com.tcs.ifact.handler.IFactHandler;
import com.tcs.ifact.handler.UserHandler;


@Service
public class UserServiceImpl implements IUserService {
	
	@Autowired
	UserHandler userHandler;
	
	@Override
	public ResponseEntity<String> addUser(UserBObj userBObj) {
		ResponseBObj res = userHandler.addUser(userBObj);
		if(null != res) {
			if(res.isError()) {
				return new ResponseEntity<String>(res.getMessage(),HttpStatus.EXPECTATION_FAILED);
			}else {
				return new ResponseEntity<String>(res.getMessage(),HttpStatus.CREATED);
			}
		}
		return new ResponseEntity<String>("Default Message",HttpStatus.OK);
	}

	@Override
	public ResponseEntity<String> updateUser(UserBObj userBObj) {
		ResponseBObj res  = userHandler.updateUser(userBObj);
		if(null != res) {
			if(res.isError()) {
				return new ResponseEntity<String>(res.getMessage(),HttpStatus.EXPECTATION_FAILED);
			}else {
				return new ResponseEntity<String>(res.getMessage(),HttpStatus.CREATED);
			}
		}
		return new ResponseEntity<String>("Default Message",HttpStatus.OK);
	}


	@Override
	public ResponseEntity<Object> getAllUser() {
		ResponseBObj res = new ResponseBObj();
		res.setError(true);
		res.setMessage("Error getUserByRole");
		res  = userHandler.getAllUser();
		if(null != res) {
			if(res.isError()) {
				return new ResponseEntity<Object>(res,HttpStatus.EXPECTATION_FAILED);
			}else {
				return new ResponseEntity<Object>(res,HttpStatus.FOUND);
			}
		}
		return new ResponseEntity<Object>(res,HttpStatus.EXPECTATION_FAILED);
	}

	@Override
	public ResponseEntity<Object> getUserByRole(String role) {
		ResponseBObj res = new ResponseBObj();
		res.setError(true);
		res.setMessage("Error getUserByRole");
		res  = userHandler.getUserByRole(role);
		if(null != res) {
			if(res.isError()) {
				return new ResponseEntity<Object>(res,HttpStatus.EXPECTATION_FAILED);
			}else {
				return new ResponseEntity<Object>(res,HttpStatus.FOUND);
			}
		}
		return new ResponseEntity<Object>(res,HttpStatus.EXPECTATION_FAILED);
	}

	@Override
	public ResponseEntity<Object> getUserByProject(String project) {
		ResponseBObj res = new ResponseBObj();
		res.setError(true);
		res.setMessage("Error getUserByProject");
		res  = userHandler.getUserByProject(project);
		if(null != res) {
			if(res.isError()) {
				return new ResponseEntity<Object>(res,HttpStatus.EXPECTATION_FAILED);
			}else {
				return new ResponseEntity<Object>(res,HttpStatus.FOUND);
			}
		}
		return new ResponseEntity<Object>(res,HttpStatus.EXPECTATION_FAILED);
	}

	@Override
	public ResponseEntity<Object> getUserByUserId(String user) {
		ResponseBObj res = new ResponseBObj();
		res.setError(true);
		res.setMessage("Error getUserByUserId");
		res  = userHandler.getUserByUserId(user);
		if(null != res) {
			if(res.isError()) {
				return new ResponseEntity<Object>(res,HttpStatus.EXPECTATION_FAILED);
			}else {
				return new ResponseEntity<Object>(res,HttpStatus.FOUND);
			}
		}
		return new ResponseEntity<Object>(res,HttpStatus.EXPECTATION_FAILED);
	}

	@Override
	public ResponseEntity<String> deleteByUserId(String user) {
		ResponseBObj res  = userHandler.deleteByUserId(user);
		if(null != res) {
			if(res.isError()) {
				return new ResponseEntity<String>(res.getMessage(),HttpStatus.EXPECTATION_FAILED);
			}else {
				return new ResponseEntity<String>(res.getMessage(),HttpStatus.CREATED);
			}
		}
		return new ResponseEntity<String>("Default Message",HttpStatus.OK);
	}

	@Override
	public ResponseEntity<String> deleteAllUser() {
		ResponseBObj res  = userHandler.deleteAllUser();
		if(null != res) {
			if(res.isError()) {
				return new ResponseEntity<String>(res.getMessage(),HttpStatus.EXPECTATION_FAILED);
			}else {
				return new ResponseEntity<String>(res.getMessage(),HttpStatus.CREATED);
			}
		}
		return new ResponseEntity<String>("Default Message",HttpStatus.OK);
	}

	@Override
	public ResponseEntity<String> enableAndDisableUser(String user, String flag) {
		ResponseBObj res  = userHandler.enableAndDisableUser(user, flag);
		if(null != res) {
			if(res.isError()) {
				return new ResponseEntity<String>(res.getMessage(),HttpStatus.EXPECTATION_FAILED);
			}else {
				return new ResponseEntity<String>(res.getMessage(),HttpStatus.CREATED);
			}
		}
		return new ResponseEntity<String>("Default Message",HttpStatus.OK);
	}

}
