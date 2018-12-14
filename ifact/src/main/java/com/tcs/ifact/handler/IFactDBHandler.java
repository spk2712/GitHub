package com.tcs.ifact.handler;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.tcs.ifact.bobj.LoginBObj;
import com.tcs.ifact.bobj.PasswordForgotBObj;
import com.tcs.ifact.bobj.PasswordResetBObj;
import com.tcs.ifact.bobj.ResponseBObj;
import com.tcs.ifact.dao.IDBFileDao;
import com.tcs.ifact.dao.IPivotDao;
import com.tcs.ifact.dao.IPwbDao;
import com.tcs.ifact.dao.IUserDao;
import com.tcs.ifact.dao.IUtilDao;
import com.tcs.ifact.helper.IFactConstant;
import com.tcs.ifact.helper.IFactHelper;
import com.tcs.ifact.model.DBFile;
import com.tcs.ifact.model.Pivot;
import com.tcs.ifact.model.Pwb;
import com.tcs.ifact.model.UserInfo;
import com.tcs.ifact.model.Util;

@Component
public class IFactDBHandler {
	
	private static final Logger logger = LogManager.getLogger(IFactDBHandler.class);
	
	@Autowired
	IUserDao userDao;
	
	@Autowired
	IPwbDao pwbDao;
	
	@Autowired
	IUtilDao utilDao;
	
	@Autowired
	IDBFileDao dbfileDao;
	
	@Autowired
	IPivotDao pivotDao;
	
	@Autowired
	IFactObjectHandler objectHandler;
	
	

	public ResponseBObj persistPWBObject(ResponseBObj res) {
		ResponseBObj bObj = new ResponseBObj();
		try {
			if(null != res && !res.isError() && null != res.getResponseObject() && res.getResponseObject() instanceof HashMap) {
				HashMap map = (HashMap) res.getResponseObject();
				if(null != map && map.containsKey("PWBLIST") && null != map.get("PWBLIST") && map.get("PWBLIST") instanceof ArrayList) {
					ArrayList<Pwb> pwbList =  (ArrayList<Pwb>) map.get("PWBLIST");
					if(null != pwbList && pwbList.size()>0) {
						Iterator<Pwb> itr =  pwbList.iterator();
						while(itr.hasNext()) {
							Pwb pwb = itr.next();
							if(null != pwb) {
								if(null != pwb.getEmpID() && null != pwb.getWorkerID()) {
									List<Pwb> pwbDB = pwbDao.findByEmpIDAndWorkerID(pwb.getEmpID(), pwb.getWorkerID());
									if(null != pwbDB && pwbDB.size()>0) {
										if(null != pwbDB.get(0)) {
											pwbDao.update(pwb);
										}
									}else {
										pwbDao.create(pwb);
									}
								}
							}
						}
					}
				}
			}
			bObj.setResponseObject(pwbDao.findAll());
			bObj.setError(false);
			bObj.setMessage("Pwb Data persitance completed");
		}catch(Exception ex) {
			logger.error(ex);
			bObj.setError(true);
			bObj.setMessage("Error in persistPWBObject"+ex.getMessage());
		}
		return bObj;
	}
	
	public ResponseBObj getAllPWBData() {
		ResponseBObj bObj = new ResponseBObj();
		try {
			List<Pwb> list = pwbDao.findAll();
			bObj.setError(false);
			bObj.setMessage("Data Retrevied");
			bObj.setResponseObject(list);
		}catch(Exception ex) {
			logger.error(ex);
			bObj.setError(true);
			bObj.setMessage("Error in getAllPWBData"+ex.getMessage());
		}
		return bObj;
	}

	/*public HashMap getPWBObjectForDP(HashMap ifactMap) {
		// TODO Auto-generated method stub
		return null;
	}

	public HashMap getPWBObjectForDM(HashMap ifactMap) {
		// TODO Auto-generated method stub
		return null;
	}*/

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
		}catch(Exception ex) {
			ex.printStackTrace();
			logger.error(ex.fillInStackTrace());
			bObj.setMessage("Error in User Data Creation/Updation:"+ ex.getMessage());
			bObj.setError(true);
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
		}catch(Exception ex) {
			logger.error(ex);
			bObj.setMessage("Error in User Data Retrival by role:"+ ex.getMessage());
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
		}catch(Exception ex) {
			logger.error(ex);
			bObj.setMessage("Error in User Data Retrival by project:"+ ex.getMessage());
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
		}catch(Exception ex) {
			logger.error(ex);
			bObj.setMessage("Error in User Data Retrival by userId:"+ ex.getMessage());
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
		}catch(Exception ex) {
			logger.error(ex);
			bObj.setMessage("Error in User Data Retrival for User:"+ ex.getMessage());
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
		}catch(Exception ex) {
			logger.error(ex);
			bObj.setMessage("Error in User Data Deletion deleteByUserId:"+ ex.getMessage());
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
		}catch(Exception ex) {
			logger.error(ex);
			bObj.setMessage("Error in All User Data Deletion:"+ ex.getMessage());
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
		}catch(Exception ex) {
			logger.error(ex);
			bObj.setMessage("Error in PassWordForgot:"+ ex.getMessage());
			bObj.setError(true);
		}
		return bObj;
	}

	public ResponseBObj updatePWDForFP(PasswordResetBObj prObj) {
		ResponseBObj bObj = new ResponseBObj();
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		try {
			if(null != prObj) {
				UserInfo userOpt=userDao.findById(prObj.getUser());
				if(null != userOpt ) {
					userOpt.setPassword(passwordEncoder.encode(prObj.getPassword()));
					userDao.update(userOpt);
					bObj.setMessage("Password Updated , Try with new password");
					bObj.setError(false);

				}else {
					bObj.setMessage("User Does not Exist");
					bObj.setError(true);
				}
			}	
		}catch(Exception ex) {
			logger.error(ex);
			bObj.setMessage("Error in PasswordRest:"+ ex.getMessage());
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
		}catch(Exception ex) {
			logger.error(ex);
			bObj.setMessage("Error in PasswordRest:"+ ex.getMessage());
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
		}catch(Exception ex) {
			logger.error(ex);
			bObj.setMessage("Error in enableAndDisableUser:"+ ex.getMessage());
			bObj.setError(true);
		}
		return bObj;
	}

	public String getUtilValueByKey(String key) {
		String value = null;
		try {
			if(null != key) {
				List<Util> utillist = utilDao.findByKey(key);
				if(null != utillist && utillist.size()>0) {
					Iterator<Util> uItr = utillist.iterator();
					while(uItr.hasNext()) {
						Util util = uItr.next();
						if(null != util) {
							value = util.getUtilvalue();
						}
					}
				}
			}
		}catch(Exception ex) {
			logger.error(ex);	
		}
		
		return value;
	}
	
	public String updateUtilValueByKey(String key,String value) {
		try {
			if(null != key) {
				List<Util> utillist = utilDao.findByKey(key);
				if(null != utillist && utillist.size()>0) {
					Iterator<Util> uItr = utillist.iterator();
					while(uItr.hasNext()) {
						Util util = uItr.next();
						if(null != util) {
							util.setUtilvalue(value);
							utilDao.update(util);
						}
					}
				}
			}
		}catch(Exception ex) {
			logger.error(ex);	
		}
		
		return value;
	}

	public ResponseBObj persistDBFile(DBFile dbFile) {
		ResponseBObj bObj = new ResponseBObj();
		LocalDate today = LocalDate.now();
		Date date = Date.from(today.atStartOfDay(ZoneId.systemDefault()).toInstant());
		try {
			if(null != dbFile) {
				DBFile dbfDbData = dbfileDao.findById(dbFile.getUser());
				if(null != dbfDbData) {
					DBFile dbf = dbfDbData;
					dbf.setData(dbFile.getData());
					dbf.setLastUpdatedDate(date);
					dbf.setLastUpdatedUser(dbFile.getUser());
					dbf.setFilename(dbFile.getFilename());
					dbf.setFiletype(dbFile.getFiletype());
					dbfileDao.update(dbf);
					bObj.setResponseObject(dbfileDao.findById(dbFile.getUser()));
					bObj.setMessage("DBFile Data Updated");
					bObj.setError(false);
				}else {
					dbFile.setLastUpdatedDate(date);
					dbFile.setLastUpdatedUser(dbFile.getUser());
					dbfileDao.create(dbFile);
					bObj.setResponseObject(dbfileDao.findById(dbFile.getUser()));
					bObj.setMessage("DBFile Data Created");
					bObj.setError(false);
				}
			}
		}catch(Exception ex) {
			logger.error(ex);
			bObj.setMessage("Error in DBFile Data Creation/Updation:"+ ex.getMessage());
			bObj.setError(true);
		}

		return bObj;
	}

	public ResponseBObj getDBFileByUser(String user) {
		ResponseBObj bObj = new ResponseBObj();
		try {
			if(null != user) {
				DBFile dbfDBObj = dbfileDao.findById(user);
				bObj.setResponseObject(dbfDBObj);
				bObj.setMessage("DBFile Data fetched");
				bObj.setError(false);
			}
		}catch(Exception ex) {
			logger.error(ex);
			bObj.setMessage("Error in getting DBFile Data :"+ ex.getMessage());
			bObj.setError(true);
		}
		return bObj;
	}

	public HashMap<String, Pwb> getPWBObjectForUserinMap(String user) {
		HashMap<String, Pwb> pwbMap = new HashMap<String, Pwb>(); 
		if(null != user) {
			List<Pwb> pwbL = null;
			String name = null;
			String role = null;

			UserInfo userInfo = userDao.findById(user);
			if(null != userInfo) {
				String userrole = userInfo.getRole();
				if(null != userrole && userrole.equalsIgnoreCase(IFactConstant.ROLE_PMO)) {
					String manager = userInfo.getReportto();
					UserInfo mangerInfo = userDao.findById(manager);
					if(null != mangerInfo) {
						role = mangerInfo.getRole();
						name = mangerInfo.getNickname();
					}
					
				}else  {
					role = userInfo.getRole();
					name = userInfo.getNickname();
				}
			}

			/*List<Util> util = utilDao.findByKey(user);
			if(null != util && util.size()>0 && null != util.get(0)) {
				Util ut = util.get(0);
				if(null != ut && null != ut.getValue()) {
					String value = ut.getValue();
					if(null != value) {
						String[] splitted= value.split("\\|");
						name = splitted[0];
						role = splitted[1];
					}
				}
			}*/
			
			if(null != name && null != role) {
				if(null != role) {
					if(role.equalsIgnoreCase(IFactConstant.ROLE_DP)) {
						pwbL = pwbDao.findByDP(name);
					}else if(role.equalsIgnoreCase(IFactConstant.ROLE_DM)){
						pwbL = pwbDao.findByDM(name);
					}else if(role.equalsIgnoreCase(IFactConstant.ROLE_ADMIN)){
						pwbL = pwbDao.findAll();
					}
				}
				if(null != pwbL && pwbL.size()>0) {
					Iterator<Pwb> itr = pwbL.iterator();
					while(itr.hasNext()) {
						Pwb pwb = itr.next();
						if(null != pwb && null != pwb.getWorkerID() && null != pwb.getEmpID()) {
							pwbMap.put(pwb.getWorkerID()+pwb.getEmpID(), pwb);
						}
					}
				}
			}

		}		
		return pwbMap;
	}

	public ResponseBObj UpdatePWB(ResponseBObj res,String user) {
		ResponseBObj bObj = new ResponseBObj();
		try {
			if(null != res && !res.isError() && null != res.getResponseObject() && res.getResponseObject() instanceof ArrayList) {
				LocalDate today = LocalDate.now();
				Date date = Date.from(today.atStartOfDay(ZoneId.systemDefault()).toInstant());
				ArrayList<Pwb> pwbtoUpdate = (ArrayList<Pwb>) res.getResponseObject();
				if(null != pwbtoUpdate && pwbtoUpdate.size()>0) {
					Iterator<Pwb> pwbtoUitr = pwbtoUpdate.iterator();
					while(pwbtoUitr.hasNext()) {
						Pwb pwbUp = pwbtoUitr.next();
						if(null != pwbUp) {
							pwbDao.update(pwbUp);
						}
					}
				}
				bObj.setMessage("UpdatePWB Success");
				bObj.setError(true);
			}
		}catch(Exception ex) {
			logger.error(ex.fillInStackTrace());
			bObj.setMessage("Error in UpdatePWB:"+ ex.getMessage());
			bObj.setError(true);
		}
		
		return bObj;
	}

	public ResponseBObj getPWBDataByUser(String user) {
		ResponseBObj bObj = new ResponseBObj();
		List<Pwb> pwbL = null;
		try {	
			if(null != user) {
				
				String name = null;
				String role = null;

				UserInfo userInfo = userDao.findById(user);
				if(null != userInfo) {
					String userrole = userInfo.getRole();
					if(null != userrole && userrole.equalsIgnoreCase(IFactConstant.ROLE_PMO)) {
						String manager = userInfo.getReportto();
						UserInfo mangerInfo = userDao.findById(manager);
						if(null != mangerInfo) {
							role = mangerInfo.getRole();
							name = mangerInfo.getNickname();
						}
						
					}else  {
						role = userInfo.getRole();
						name = userInfo.getNickname();
					}
				}
				
				if(null != name && null != role) {
					if(null != role) {
						if(role.equalsIgnoreCase(IFactConstant.ROLE_DP)) {
							pwbL = pwbDao.findByDP(name);
						}else if(role.equalsIgnoreCase(IFactConstant.ROLE_DM)){
							pwbL = pwbDao.findByDM(name);
						}else if(role.equalsIgnoreCase(IFactConstant.ROLE_ADMIN)){
							pwbL = pwbDao.findAll();
						}
					}	
				}

			}
			
			bObj.setResponseObject(pwbL);
			bObj.setMessage("getPWBDataByUser Success");
			bObj.setError(false);
		}catch(Exception ex) {
			logger.error(ex);
			bObj.setMessage("Error in UpdatePWB:"+ ex.getMessage());
			bObj.setError(true);
		}
		
		return bObj;
	}

	public ResponseBObj insertPWBPivotObject(ResponseBObj res, Object object) {
		ResponseBObj bObj = new ResponseBObj();
		try {
			String week = getUtilValueByKey(IFactConstant.PivotWeekCouter);
			String newweek = IFactHelper.add2String(week, "1");
			if(null != res && !res.isError() && null != res.getResponseObject() && res.getResponseObject() instanceof ArrayList) {
				ArrayList<Pivot> pivotList = (ArrayList<Pivot>) res.getResponseObject();
				if(null != pivotList && pivotList.size()>0) {
					Iterator<Pivot> pivotItr = pivotList.iterator();
					while(pivotItr.hasNext()) {
						Pivot pivot = pivotItr.next();
						if(null != pivot) {
							pivot.setWeek(newweek);
							pivotDao.create(pivot);
						}
					}
				}
			}
			updateUtilValueByKey(IFactConstant.PivotWeekCouter,newweek);
			bObj.setMessage("getPWBDataByUser Success");
			bObj.setError(false);
		}catch(Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
			bObj.setMessage("Error in insertPWBPivotObject:"+ ex.getMessage());
			bObj.setError(true);
		}
		
		return bObj;
	}

	public ResponseBObj validateLogin(LoginBObj login) {
		ResponseBObj bObj = new ResponseBObj();
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		try {
			if(null != login) {
				UserInfo userInfo = userDao.findById(login.getUser());
				if(null != userInfo) {
					if(passwordEncoder.matches(login.getPassword(), userInfo.getPassword())) {
						bObj.setResponseObject(objectHandler.mapLoginObj(login,userInfo));
						bObj.setError(false);
					}else {
						bObj.setMessage("Password Does not Match");
						bObj.setError(true);
					}
				}else {
					bObj.setMessage("User Does Not Exsist");
					bObj.setError(true);
				}
			}
		}catch(Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
			bObj.setMessage("Error in validateLogin:"+ ex.getMessage());
			bObj.setError(true);
		}
		
		return bObj;
	}

/*	public String getRoleAndName(String user) {
		String value = null;
		if(null != user) {
			List<Util> util = utilDao.findByKey(user);
			if(null != util && util.size()>0 && null != util.get(0)) {
				Util ut = util.get(0);
				if(null != ut && null != ut.getValue()) {
					value = ut.getValue();
				}
			}
		}
		return value;
	}*/



}
