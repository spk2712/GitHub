package com.tcs.ifact.handler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tcs.ifact.bobj.ResponseBObj;
import com.tcs.ifact.bobj.UtilBObj;
import com.tcs.ifact.model.Util;

@Component
public class UtilHandler {
	
private static final Logger logger = LogManager.getLogger(UserHandler.class);
	
	@Autowired
	IFactDBHandler iFactDBHandler;

	@Autowired
	IFactObjectHandler iFactObjectHandler;
	
	public ResponseBObj getAllUtilData() {
		ResponseBObj responseBObj = new ResponseBObj();
		responseBObj = iFactDBHandler.getAllUtilData();
		return responseBObj;
	}
	
	public ResponseBObj presistUtil(UtilBObj utilBObj) {
		ResponseBObj responseBObj = new ResponseBObj();
		Object obj  = iFactObjectHandler.constructUtilObject(utilBObj);
		if(obj instanceof Util) {
			Util util = (Util)obj;
			responseBObj = iFactDBHandler.persistUtil(util);
		}else if(obj instanceof ResponseBObj) {
			ResponseBObj res = (ResponseBObj)obj;
			responseBObj = res;
		}		
		return responseBObj;
	}

}
