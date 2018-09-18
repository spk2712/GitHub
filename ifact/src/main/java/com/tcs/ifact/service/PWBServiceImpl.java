package com.tcs.ifact.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.tcs.ifact.bobj.ResponseBObj;
import com.tcs.ifact.dao.IPwbDao;
import com.tcs.ifact.handler.IFactHandler;
import com.tcs.ifact.handler.PWBHandler;
import com.tcs.ifact.model.Pwb;

@Service
public class PWBServiceImpl implements IPWBService {
	
	@Autowired
	PWBHandler pwbHandler;

	@Override
	public ResponseEntity<String> pwbFromRawData() {
		ResponseBObj res = pwbHandler.pwbFromRawData();
		return null;
	}

	@Override
	public ResponseEntity<String> pwbFromExcel() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
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
	
}
