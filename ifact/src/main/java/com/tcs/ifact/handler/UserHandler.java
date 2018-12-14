package com.tcs.ifact.handler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tcs.ifact.bobj.PasswordForgotBObj;
import com.tcs.ifact.bobj.PasswordResetBObj;
import com.tcs.ifact.bobj.ResponseBObj;
import com.tcs.ifact.bobj.UserBObj;
import com.tcs.ifact.bobj.UserRegistrationBObj;
import com.tcs.ifact.model.UserInfo;

@Component
public class UserHandler {
	
	private static final Logger logger = LogManager.getLogger(UserHandler.class);
	
	@Autowired
	IFactDBHandler iFactDBHandler;

	@Autowired
	IFactObjectHandler iFactObjectHandler;
	
	public ResponseBObj addUser(UserBObj userBObj) {
		ResponseBObj responseBObj = new ResponseBObj();
		Object obj  = iFactObjectHandler.constructUserObject(userBObj);
		if(obj instanceof UserInfo) {
			UserInfo user = (UserInfo)obj;
			responseBObj = iFactDBHandler.persistUser(user);
		}else if(obj instanceof ResponseBObj) {
			ResponseBObj res = (ResponseBObj)obj;
			responseBObj = res;
		}		
		return responseBObj;
	}

	public ResponseBObj updateUser(UserBObj userBObj) {
		ResponseBObj responseBObj = new ResponseBObj();
		Object obj  = iFactObjectHandler.constructUserObject(userBObj);
		if(obj instanceof UserInfo) {
			UserInfo user = (UserInfo)obj;
			responseBObj = iFactDBHandler.persistUser(user);
		}else if(obj instanceof ResponseBObj) {
			ResponseBObj res = (ResponseBObj)obj;
			responseBObj = res;
		}		
		return responseBObj;
	}


	public ResponseBObj getUserByRole(String role) {
		ResponseBObj responseBObj = new ResponseBObj();
		responseBObj = iFactDBHandler.getUserByRole(role);
		return responseBObj;
	}

	public ResponseBObj getUserByProject(String project) {
		ResponseBObj responseBObj = new ResponseBObj();
		responseBObj = iFactDBHandler.getUserByProject(project);
		return responseBObj;
	}

	public ResponseBObj getUserByUserId(String user) {
		ResponseBObj responseBObj = new ResponseBObj();
		responseBObj = iFactDBHandler.getUserByUserId(user);
		return responseBObj;
	}

	public ResponseBObj getAllUser() {
		ResponseBObj responseBObj = new ResponseBObj();
		responseBObj = iFactDBHandler.getAllUser();
		return responseBObj;
	}

	public ResponseBObj deleteByUserId(String user) {
		ResponseBObj responseBObj = new ResponseBObj();
		responseBObj = iFactDBHandler.deleteByUserId(user);
		return responseBObj;
	}

	public ResponseBObj deleteAllUser() {
		ResponseBObj responseBObj = new ResponseBObj();
		responseBObj = iFactDBHandler.deleteAllUser();
		return responseBObj;
	}

	public ResponseBObj registration(UserRegistrationBObj regBObj) {
		ResponseBObj responseBObj = new ResponseBObj();
		responseBObj  = iFactObjectHandler.conUserObjForreg(regBObj);
		if(null != responseBObj) {
			if(!responseBObj.isError()) {
				UserInfo user = (UserInfo)responseBObj.getResponseObject();
				responseBObj = iFactDBHandler.persistUser(user);
			}
		}		
		return responseBObj;
	}

	public ResponseBObj forgotPassword(PasswordForgotBObj pfObj) {
		ResponseBObj responseBObj = new ResponseBObj();
		responseBObj = iFactDBHandler.validatePF(pfObj);
		return responseBObj;
	}

	public ResponseBObj restPasswordPF(PasswordResetBObj prObj) {
		ResponseBObj responseBObj = new ResponseBObj();
		if(null != prObj) {
			if(null != prObj.getOldpassword()) {
				responseBObj = iFactDBHandler.updatePWDForFP(prObj);
			}
		}
		
		return responseBObj;
	}

	public ResponseBObj restPasswordPR(PasswordResetBObj prObj) {
		ResponseBObj responseBObj = new ResponseBObj();
		if(null != prObj) {
			if(null != prObj.getOldpassword()) {
				responseBObj = iFactDBHandler.validateAndUpdatePR(prObj);
			}
		}
		
		return responseBObj;
	}

	public ResponseBObj enableAndDisableUser(String user, String flag) {
		ResponseBObj responseBObj = new ResponseBObj();
		responseBObj= iFactDBHandler.enableAndDisableUser(user, flag);
		return responseBObj;
	}

}
