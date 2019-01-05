package com.tcs.ifact.service;


import org.springframework.http.ResponseEntity;

import com.tcs.ifact.bobj.UtilBObj;

public interface IUtilService {

	ResponseEntity<Object> findByKey(final String key);
	ResponseEntity<Object> getAllUtilData();
	ResponseEntity<String> update(final UtilBObj entity);
	ResponseEntity<String> add(final UtilBObj entity);
}
