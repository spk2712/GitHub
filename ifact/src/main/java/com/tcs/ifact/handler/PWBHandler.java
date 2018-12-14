package com.tcs.ifact.handler;

import java.util.HashMap;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.tcs.ifact.bobj.PWBUpdateBObj;
import com.tcs.ifact.bobj.PasswordForgotBObj;
import com.tcs.ifact.bobj.PasswordResetBObj;
import com.tcs.ifact.bobj.ResponseBObj;
import com.tcs.ifact.bobj.UserBObj;
import com.tcs.ifact.bobj.UserRegistrationBObj;
import com.tcs.ifact.model.UserInfo;

@Component
public class PWBHandler {
	
	private static final Logger logger = LogManager.getLogger(PWBHandler.class);
	
	@Autowired
	IFactDBHandler iFactDBHandler;
	
	@Autowired
	IFactExcelHandler iFactExcelHandler;
	
	@Autowired
	IFactObjectHandler iFactObjectHandler;
	
	

	public ResponseBObj pwbBillingData(String user) {
		ResponseBObj res = new ResponseBObj();
		res = iFactExcelHandler.getPWBFromExcel(user);
		if(null != res) {
			if(!res.isError()) {
				res = iFactObjectHandler.constructPWBforBillingUpdate(res,user);
				if(!res.isError()) {
					res = iFactDBHandler.UpdatePWB(res,user);
				}
			}
		}
		return res;
	}

/*	public void uploadPWBForDM() {
	//	ifactMap = iFactObjectHandler.getPWBFromDM(ifactMap);
	}
	
	public void uploadPWBForDP() {
	//	ifactMap = iFactObjectHandler.getPWBFromDP(ifactMap);
	}

	public void getPWBForDP() {
		//ifactMap = iFactDBHandler.getPWBObjectForDP(ifactMap);
	}

	public void getPWBForDM() {
		//ifactMap = iFactDBHandler.getPWBObjectForDM(ifactMap);
	}*/


	/*public ResponseBObj uploadExcel(MultipartFile file, String fileName) {
		ResponseBObj res = iFactExcelHandler.uploadExcel(file,fileName);
		return res;
	}*/

	public ResponseBObj pwbFromRawData(String user) {
		ResponseBObj res = new ResponseBObj();
		res = iFactExcelHandler.getDataFromRawDataExcel(user);
		if(null != res) {
			if(!res.isError()) {
				res = iFactDBHandler.persistPWBObject(res);
			}
		}
		return res;
	}

	public ResponseBObj getAllPWBData() {
		ResponseBObj res = new ResponseBObj();
		res = iFactDBHandler.getAllPWBData();
		if(null != res) {
			if(!res.isError()) {
				res = iFactObjectHandler.constructPWBRespObject(res,null);
			}
		}
		return res;
	}

/*	public ResponseBObj getPWBinExcel() {
		ResponseBObj res = new ResponseBObj();
		res = iFactDBHandler.getAllPWBData();
		if(null != res) {
			if(!res.isError()) {
				res = iFactObjectHandler.constructPWBFullExcelObject(res);
				if(!res.isError()) {
					res = iFactExcelHandler.getPWBDataInExcel(res);
				}
			}
		}
		return res;
	}*/

	public ResponseBObj getPWBDataByUser(String user) {
		ResponseBObj res = new ResponseBObj();
		res = iFactDBHandler.getPWBDataByUser(user);
		if(null != res) {
			if(!res.isError()) {
				res = iFactObjectHandler.constructPWBRespObject(res,user);
			}
		}
		return res;
	}

	public ResponseBObj updatePWBData(List<PWBUpdateBObj> pwbUpdate, String user) {
		ResponseBObj res = new ResponseBObj();
		res = iFactObjectHandler.getPWBFromUpdate(pwbUpdate,user);
		if(null != res) {
			if(!res.isError()) {
				res = iFactObjectHandler.constructPWBforBillingUpdate(res,user);
				if(!res.isError()) {
					res = iFactDBHandler.UpdatePWB(res,user);
				}
			}
		}
		return res;
	}

	public ResponseBObj pwbWeekFreeze() {
		ResponseBObj res = new ResponseBObj();
		res = iFactDBHandler.getAllPWBData();
		if(null != res) {
			if(!res.isError()) {
				res = iFactObjectHandler.constructPWBRespObject(res,null);
				if(!res.isError()) {
					res = iFactObjectHandler.constructPWBPivotObject(res, null);
					if(!res.isError()) {
						res = iFactDBHandler.insertPWBPivotObject(res, null);
					}
				}
			}
		}
		return res;
	}

}
