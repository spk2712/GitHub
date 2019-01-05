package com.tcs.ifact.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tcs.ifact.bobj.UtilBObj;
import com.tcs.ifact.model.Util;
import com.tcs.ifact.service.IUtilService;

@RestController
@RequestMapping(path="/ifact/util")
@Secured ({"ROLE_ADMIN"})
public class UtilController {
	
private static final Logger logger = LogManager.getLogger(UtilController.class);
	
	@Autowired
	IUtilService iutilService;
	
	@RequestMapping(value = "/getAllUtilData",method=RequestMethod.GET)
	public ResponseEntity<Object> getAllUtilData() {
		logger.debug("Welcome to getAllUtilData");
		return iutilService.getAllUtilData();	
	}
	
	@RequestMapping(value = "/findByKey",method=RequestMethod.GET)
	public ResponseEntity<Object> findByKey(@RequestParam String key) {
		logger.debug("Welcome to findByKey");
		return iutilService.findByKey(key);	
	}
	
	@RequestMapping(value= "/presistUtil",method=RequestMethod.PUT)
	public ResponseEntity<String> presistUtil(@RequestBody UtilBObj utilBObj) {
		logger.debug("Welcome to presistUtil");
		return iutilService.presistUtil(utilBObj);
	
	}

}
