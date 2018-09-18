package com.tcs.ifact.handler;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.tcs.ifact.bobj.PasswordForgotBObj;
import com.tcs.ifact.bobj.PasswordResetBObj;
import com.tcs.ifact.bobj.ResponseBObj;
import com.tcs.ifact.dao.IUserDao;
import com.tcs.ifact.model.UserInfo;

@Component
public class IFactDBHandler {
	
	@Autowired
	IUserDao userDao;
	
	

	public HashMap persistPWBObject(HashMap ifactMap) {
		// TODO Auto-generated method stub
		return null;
	}

	public HashMap getPWBObjectForDP(HashMap ifactMap) {
		// TODO Auto-generated method stub
		return null;
	}

	public HashMap getPWBObjectForDM(HashMap ifactMap) {
		// TODO Auto-generated method stub
		return null;
	}

	public ResponseBObj persistUser(UserInfo user) {
		ResponseBObj bObj = new ResponseBObj();
		try {
			if(null != user) {
				UserInfo userOpt= userDao.findById(user.getUser());
				if(null != userOpt) {
					userDao.update(user);
					bObj.setMessage("User Data Updated");
					bObj.setError(false);
				}else {
					userDao.create(user);
					bObj.setMessage("User Data Created");
					bObj.setError(false);
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
			bObj.setMessage("Error in User Data Creation/Updation:"+ e.getMessage());
			bObj.setError(false);
		}
	
		return bObj;
	}

	public ResponseBObj getUserByRole(String role) {
		ResponseBObj bObj = new ResponseBObj();
		try {
			if(null != role) {
				List<UserInfo> userOpt= userDao.findByRole(role);
				if(null != userOpt && userOpt.size()>0) {
					bObj.setResponseObject(userOpt);
					bObj.setMessage("Data retrived");
					bObj.setError(false);
				}else {
					bObj.setMessage("No Data Found for given search criteria");
					bObj.setError(false);
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
			bObj.setMessage("Error in User Data Retrival by role:"+ e.getMessage());
			bObj.setError(true);
		}
	
		return bObj;
	}

	public ResponseBObj getUserByProject(String project) {
		ResponseBObj bObj = new ResponseBObj();
		try {
			if(null != project) {
				List<UserInfo> userOpt= userDao.findByProject(project);
				if(null != userOpt && userOpt.size()>0) {
					bObj.setResponseObject(userOpt);
					bObj.setMessage("Data retrived");
					bObj.setError(false);
				}else {
					bObj.setMessage("No Data Found for given search criteria");
					bObj.setError(false);
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
			bObj.setMessage("Error in User Data Retrival by project:"+ e.getMessage());
			bObj.setError(true);
		}
	
		return bObj;
	}

	public ResponseBObj getUserByUserId(String user) {
		ResponseBObj bObj = new ResponseBObj();
		try {
			if(null != user) {
				UserInfo userOpt= userDao.findById(user);
				if(null != userOpt) {
					bObj.setResponseObject(userOpt);
					bObj.setMessage("Data retrived");
					bObj.setError(false);
				}else {
					bObj.setMessage("No Data Found for given search criteria");
					bObj.setError(false);
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
			bObj.setMessage("Error in User Data Retrival by userId:"+ e.getMessage());
			bObj.setError(true);
		}
	
		return bObj;
	}

	public ResponseBObj getAllUser() {
		ResponseBObj bObj = new ResponseBObj();
		try {
				List<UserInfo> userOpt= userDao.findAll();
				if(null != userOpt && userOpt.size()>0) {
					bObj.setResponseObject(userOpt);
					bObj.setMessage("Data retrived");
					bObj.setError(false);
				}else {
					bObj.setMessage("No Data Found for given search criteria");
					bObj.setError(false);
				}
		}catch(Exception e) {
			e.printStackTrace();
			bObj.setMessage("Error in User Data Retrival for User:"+ e.getMessage());
			bObj.setError(true);
		}
	
		return bObj;
	}

	public ResponseBObj deleteByUserId(String user) {
		ResponseBObj bObj = new ResponseBObj();
		try {
			if(null != user) {
				userDao.deleteById(user);
				bObj.setMessage("User Deleted Successfully");
				bObj.setError(false);	
			}
		}catch(Exception e) {
			e.printStackTrace();
			bObj.setMessage("Error in User Data Deletion deleteByUserId:"+ e.getMessage());
			bObj.setError(true);
		}
		return bObj;
	}

	public ResponseBObj deleteAllUser() {
		ResponseBObj bObj = new ResponseBObj();
		try {
			userDao.deleteAll();
			bObj.setMessage("All User Deleted Successfully");
			bObj.setError(false);	
		}catch(Exception e) {
			e.printStackTrace();
			bObj.setMessage("Error in All User Data Deletion:"+ e.getMessage());
			bObj.setError(true);
		}
		return bObj;
	}

	public ResponseBObj validatePF(PasswordForgotBObj pfObj) {
		ResponseBObj bObj = new ResponseBObj();
		try {
			if(null != pfObj) {
				Date date =new SimpleDateFormat("dd/MM/yyyy").parse(pfObj.getDateOfJoining()); 
				UserInfo userOpt=userDao.findById(pfObj.getUser());
				if(null != userOpt) {
					System.out.println(pfObj.getEmail()+"::"+userOpt.getEmail());
					if(pfObj.getEmail().equalsIgnoreCase(userOpt.getEmail())) {
						System.out.println(userOpt.getDateofjoining()+"::"+date);
						if(0==userOpt.getDateofjoining().compareTo(date)) {
							bObj.setMessage("Data Matched");
							bObj.setError(false);
						}else {
							bObj.setMessage("Date of Joining Does not Matched");
							bObj.setError(true);
						}
					}else {
						bObj.setMessage("Email Id Does not Matched");
						bObj.setError(true);
					}
					
				}else {
					bObj.setMessage("No User Found");
					bObj.setError(true);
				}
			}	
		}catch(Exception e) {
			e.printStackTrace();
			bObj.setMessage("Error in PassWordForgot:"+ e.getMessage());
			bObj.setError(true);
		}
		return bObj;
	}

	public ResponseBObj updatePF(PasswordResetBObj prObj) {
		ResponseBObj bObj = new ResponseBObj();
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		try {
			if(null != prObj) {
				UserInfo userOpt=userDao.findById(prObj.getUser());
				if(null != userOpt ) {
					userOpt.setPassword(passwordEncoder.encode(userOpt.getPassword()));
					userDao.update(userOpt);
					bObj.setMessage("Password Updated , Try with new password");
					bObj.setError(false);

				}else {
					bObj.setMessage("User Does not Exist");
					bObj.setError(true);
				}
			}	
		}catch(Exception e) {
			e.printStackTrace();
			bObj.setMessage("Error in PasswordRest:"+ e.getMessage());
			bObj.setError(true);
		}
		return bObj;
	}

	public ResponseBObj validateAndUpdatePR(PasswordResetBObj prObj) {
		ResponseBObj bObj = new ResponseBObj();
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		try {
			if(null != prObj) {
				UserInfo userOpt=userDao.findById(prObj.getUser());
				if(null != userOpt ) {
					if(null != userOpt.getPassword()) {
						if(passwordEncoder.matches(prObj.getOldpassword(), userOpt.getPassword())) {
							userOpt.setPassword(passwordEncoder.encode(userOpt.getPassword()));
							userDao.update(userOpt);
							bObj.setMessage("Password Updated , Try with new password");
							bObj.setError(false);
						}else {
							bObj.setMessage("Password Does not Match");
							bObj.setError(true);
						}
					}
					
				}else {
					bObj.setMessage("User Does not Exist");
					bObj.setError(true);
				}
			}	
		}catch(Exception e) {
			e.printStackTrace();
			bObj.setMessage("Error in PasswordRest:"+ e.getMessage());
			bObj.setError(true);
		}
		return bObj;
	}

	public ResponseBObj enableAndDisableUser(String user, String flag) {
		ResponseBObj bObj = new ResponseBObj();
		try {
			if(null != user) {
				UserInfo userOpt=userDao.findById(user);
				if(null != userOpt ) {
					userOpt.setEnabled(flag);;
					userDao.update(userOpt);
					bObj.setMessage("User Enabled");
					bObj.setError(false);
				}else {
					bObj.setMessage("User Does not Exist");
					bObj.setError(true);
				}
			}	
		}catch(Exception e) {
			e.printStackTrace();
			bObj.setMessage("Error in enableAndDisableUser:"+ e.getMessage());
			bObj.setError(true);
		}
		return bObj;
	}

}
