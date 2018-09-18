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
public class IFactHandler {
	
	@Autowired
	IFactDBHandler iFactDBHandler;

	IFactObjectHandler iFactObjectHandler = new IFactObjectHandler();
	
	
	public ResponseBObj registration(UserRegistrationBObj regBObj) {
		ResponseBObj responseBObj = new ResponseBObj();
		Object obj  = iFactObjectHandler.conUserObjForreg(regBObj);
		if(obj instanceof UserInfo) {
			UserInfo user = (UserInfo)obj;
			responseBObj = iFactDBHandler.persistUser(user);
		}else if(obj instanceof ResponseBObj) {
			ResponseBObj res = (ResponseBObj)obj;
			responseBObj = res;
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
				responseBObj = iFactDBHandler.updatePF(prObj);
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


}
