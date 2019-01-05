package com.tcs.ifact.handler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tcs.ifact.bobj.LoginBObj;
import com.tcs.ifact.bobj.PasswordForgotBObj;
import com.tcs.ifact.bobj.PasswordResetBObj;
import com.tcs.ifact.bobj.ResponseBObj;
import com.tcs.ifact.bobj.UserRegistrationBObj;
import com.tcs.ifact.model.UserInfo;

@Component
public class IFactHandler {
	
	private static final Logger logger = LogManager.getLogger(IFactHandler.class);
	
	@Autowired
	IFactDBHandler iFactDBHandler;

	@Autowired
	IFactObjectHandler iFactObjectHandler;
	
	
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

	public ResponseBObj restPasswordFP(PasswordResetBObj prObj) {
		ResponseBObj responseBObj = new ResponseBObj();
		if(null != prObj) {
				responseBObj = iFactDBHandler.updatePWDForFP(prObj);
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

	public ResponseBObj login(LoginBObj login) {
		ResponseBObj responseBObj = new ResponseBObj();
		if(null != login) {
			if(null != login.getUser() && !login.getUser().isEmpty()) {
				if(null != login.getPassword() && !login.getPassword().isEmpty()) {
					responseBObj = iFactDBHandler.validateLogin(login);
				}else {
					responseBObj.setError(true);
					responseBObj.setMessage("Please Enter User Name");
				}
			}else {
				responseBObj.setError(true);
				responseBObj.setMessage("Please Enter User Name");
				
			}
		}
		return responseBObj;
	}


}
