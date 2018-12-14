package com.tcs.ifact.service;


import java.util.List;

import org.springframework.http.ResponseEntity;

import com.tcs.ifact.bobj.PWBUpdateBObj;



public interface IPWBService {
	
	ResponseEntity<Object> pwbFromRawData(final String user);
	ResponseEntity<Object> pwbFromExcel(final String user);
	ResponseEntity<Object> getAllPWBData();
	ResponseEntity<Object> getPWBDataByUser(String user);
	ResponseEntity<Object> upadtePWBData(List<PWBUpdateBObj> pwbUpdate, String user);
	ResponseEntity<Object> pwbWeekFreeze();
	

}
