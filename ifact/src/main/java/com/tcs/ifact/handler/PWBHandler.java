package com.tcs.ifact.handler;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.tcs.ifact.bobj.PasswordForgotBObj;
import com.tcs.ifact.bobj.PasswordResetBObj;
import com.tcs.ifact.bobj.ResponseBObj;
import com.tcs.ifact.bobj.UserBObj;
import com.tcs.ifact.bobj.UserRegistrationBObj;
import com.tcs.ifact.model.UserInfo;

@Component
public class PWBHandler {
	
	@Autowired
	IFactDBHandler iFactDBHandler;

	IFactExcelHandler iFactExcelHandler = new IFactExcelHandler();
	IFactObjectHandler iFactObjectHandler = new IFactObjectHandler();
	
	HashMap ifactMap = new HashMap();
	

	public void pwbFromExcel() {
		ifactMap = iFactObjectHandler.getPWBFromExcel(ifactMap);
		ifactMap = iFactDBHandler.persistPWBObject(ifactMap);
	}

	public void uploadPWBForDM() {
		ifactMap = iFactObjectHandler.getPWBFromDM(ifactMap);
		ifactMap = iFactDBHandler.persistPWBObject(ifactMap);
	}
	
	public void uploadPWBForDP() {
		ifactMap = iFactObjectHandler.getPWBFromDP(ifactMap);
		ifactMap = iFactDBHandler.persistPWBObject(ifactMap);
	}

	public void getPWBForDP() {
		ifactMap = iFactDBHandler.getPWBObjectForDP(ifactMap);
	}

	public void getPWBForDM() {
		ifactMap = iFactDBHandler.getPWBObjectForDM(ifactMap);
	}


	public ResponseBObj uploadExcel(MultipartFile file, String fileName) {
		ResponseBObj res = iFactExcelHandler.uploadExcel(file,fileName);
		return res;
	}

	public ResponseBObj pwbFromRawData() {
		ResponseBObj res = new ResponseBObj();
		res = iFactExcelHandler.getDataFromExcel();
		ifactMap = iFactObjectHandler.constructPWBObject(ifactMap);
		ifactMap = iFactDBHandler.persistPWBObject(ifactMap);
		return res;
	}

}
