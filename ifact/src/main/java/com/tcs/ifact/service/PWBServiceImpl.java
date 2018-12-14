package com.tcs.ifact.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.tcs.ifact.bobj.PWBUpdateBObj;
import com.tcs.ifact.bobj.ResponseBObj;
import com.tcs.ifact.handler.PWBHandler;

@Service
public class PWBServiceImpl implements IPWBService {
	
	@Autowired
	PWBHandler pwbHandler;
	

	@Override
	public ResponseEntity<Object> pwbFromRawData(String user) {
		ResponseBObj res = new ResponseBObj();
		res = pwbHandler.pwbFromRawData(user);
		if(null != res) {}
		if(res.isError()) {
			return new ResponseEntity<Object>(res,HttpStatus.EXPECTATION_FAILED);
		}else {
			return new ResponseEntity<Object>(res,HttpStatus.CREATED);
		}
	}

	@Override
	public ResponseEntity<Object> pwbFromExcel(String user) {	
		ResponseBObj res = new ResponseBObj();
		res = pwbHandler.pwbBillingData(user);
		if(null != res) {}
		if(res.isError()) {
			return new ResponseEntity<Object>(res,HttpStatus.EXPECTATION_FAILED);
		}else {
			return new ResponseEntity<Object>(res,HttpStatus.CREATED);
		}
}
	
	@Override
	public ResponseEntity<Object> getAllPWBData() {
		ResponseBObj res = new ResponseBObj();
		res = pwbHandler.getAllPWBData();
		if(null != res) {
			if(res.isError()) {
				return new ResponseEntity<Object>(res,HttpStatus.EXPECTATION_FAILED);
			}else {
				return new ResponseEntity<Object>(res,HttpStatus.FOUND);
			}
		}
		return new ResponseEntity<Object>(res,HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Object> getPWBDataByUser(String user) {
		ResponseBObj res = new ResponseBObj();
		res = pwbHandler.getPWBDataByUser(user);
		if(null != res) {
			if(!res.isError()) {
				return new ResponseEntity<Object>(res,HttpStatus.FOUND);
			}else {
				return new ResponseEntity<Object>(res,HttpStatus.EXPECTATION_FAILED);
				
			}
		}
		return new ResponseEntity<Object>(res,HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Object> upadtePWBData(List<PWBUpdateBObj> pwbUpdate, String user) {	
		ResponseBObj res = new ResponseBObj();
		res = pwbHandler.updatePWBData(pwbUpdate,user);
		if(null != res) {}
		if(res.isError()) {
			return new ResponseEntity<Object>(res,HttpStatus.EXPECTATION_FAILED);
		}else {
			return new ResponseEntity<Object>(res,HttpStatus.CREATED);
		}
}

	@Override
	public ResponseEntity<Object> pwbWeekFreeze() {
		ResponseBObj res = new ResponseBObj();
		res = pwbHandler.pwbWeekFreeze();
		if(null != res) {
			if(res.isError()) {
				return new ResponseEntity<Object>(res,HttpStatus.EXPECTATION_FAILED);
			}else {
				return new ResponseEntity<Object>(res,HttpStatus.FOUND);
			}
		}
		return new ResponseEntity<Object>(res,HttpStatus.OK);
	}

	/*@Override
	public ResponseEntity<String> uploadPWBForDM() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<String> uploadPWBForDP() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<String> getPWBForDP() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<String> getPWBForDM() {
		// TODO Auto-generated method stub
		return null;
	}

	
	@Override
	public ResponseEntity<String> uploadExcel(MultipartFile file,String fileName) {
		ResponseBObj res = pwbHandler.uploadExcel(file,fileName);
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
	public ResponseEntity<Object> getPWBinExcel() {
		ResponseBObj res = new ResponseBObj();
		res = pwbHandler.getPWBinExcel();
		if(null != res) {
			if(res.isError()) {
				return new ResponseEntity<Object>(res,HttpStatus.EXPECTATION_FAILED);
			}else {
				return new ResponseEntity<Object>(res,HttpStatus.FOUND);
			}
		}
		return new ResponseEntity<Object>(res,HttpStatus.OK);
	}*/
	
}
