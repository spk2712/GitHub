package com.tcs.ifact.handler;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.tcs.ifact.bobj.ResponseBObj;
import com.tcs.ifact.bobj.UserBObj;
import com.tcs.ifact.bobj.UserRegistrationBObj;
import com.tcs.ifact.model.UserInfo;

public class IFactObjectHandler {

	public HashMap constructPWBObject(HashMap ifactMap) {
		return ifactMap;
		// TODO Auto-generated method stub
		
	}

	public HashMap getPWBFromExcel(HashMap ifactMap) {
		// TODO Auto-generated method stub
		return null;
	}

	public HashMap getPWBFromDM(HashMap ifactMap) {
		// TODO Auto-generated method stub
		return null;
	}

	public HashMap getPWBFromDP(HashMap ifactMap) {
		// TODO Auto-generated method stub
		return null;
	}

	public Object constructUserObject(UserBObj userBObj) {
		UserInfo user = new UserInfo();
		ResponseBObj responseBObj = new ResponseBObj();
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		
		
		try {
			if(null != userBObj) {
				
				user.setFullName(userBObj.getFullName());
				user.setEnabled(userBObj.getEnabled());
				user.setPassword(passwordEncoder.encode(userBObj.getPassword()));
				user.setUser(userBObj.getUser());
				user.setProject(userBObj.getProject());
				user.setRole(userBObj.getRole());
				user.setEmail(userBObj.getEmail());
			}
		}catch(Exception e) {
			responseBObj.setError(true);
			responseBObj.setMessage(e.getMessage());
			return responseBObj;
		}
		return user;
	}

	public Object conUserObjForreg(UserRegistrationBObj regBObj) {
		UserInfo user = new UserInfo();
		ResponseBObj responseBObj = new ResponseBObj();
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

		try {
			if(null != regBObj) {
				user.setFullName(regBObj.getFullname());
				user.setEnabled("N");
				user.setPassword(passwordEncoder.encode(regBObj.getPassword()));
				user.setUser(regBObj.getUser());
				user.setProject(regBObj.getProject());
				user.setEmail(regBObj.getEmail());
				Date date =new SimpleDateFormat("dd/MMM/yyyy").parse(regBObj.getDateOfJoining()); 
				System.out.println(date);
				user.setDateofjoining(date);
			}
		}catch(Exception e) {
			responseBObj.setError(true);
			responseBObj.setMessage(e.getMessage());
			return responseBObj;
		}
		return user;
	}

}
