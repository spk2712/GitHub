package com.tcs.ifact.service;


import org.springframework.http.ResponseEntity;

import com.tcs.ifact.bobj.UtilBObj;

public interface IUtilService {

	ResponseEntity<Object> findByKey(final String key);
	ResponseEntity<Object> getAllUtilData();
	ResponseEntity<String> presistUtil(final UtilBObj entity);
}
