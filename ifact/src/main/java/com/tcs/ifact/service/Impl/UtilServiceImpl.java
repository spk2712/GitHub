package com.tcs.ifact.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.tcs.ifact.bobj.ResponseBObj;
import com.tcs.ifact.bobj.UtilBObj;
import com.tcs.ifact.handler.UserHandler;
import com.tcs.ifact.handler.UtilHandler;
import com.tcs.ifact.service.IUtilService;

@Service
public class UtilServiceImpl implements IUtilService {
	
	@Autowired
	UtilHandler utilHandler;

	@Override
	public ResponseEntity<Object> findByKey(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<Object> getAllUtilData() {
		ResponseBObj res = new ResponseBObj();
		res  = utilHandler.getAllUtilData();
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
	public ResponseEntity<String> presistUtil(UtilBObj utilBObj) {
		ResponseBObj res = utilHandler.presistUtil(utilBObj);
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
