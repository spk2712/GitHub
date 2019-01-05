package com.tcs.ifact.handler;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.tcs.ifact.bobj.CPRevBObj;
import com.tcs.ifact.bobj.DMRevBObj;
import com.tcs.ifact.bobj.DPRevBObj;
import com.tcs.ifact.bobj.LoginBObj;
import com.tcs.ifact.bobj.PWBBObj;
import com.tcs.ifact.bobj.PWBRevBObj;
import com.tcs.ifact.bobj.PWBUpdateBObj;
import com.tcs.ifact.bobj.ResponseBObj;
import com.tcs.ifact.bobj.UserBObj;
import com.tcs.ifact.bobj.UserRegistrationBObj;
import com.tcs.ifact.bobj.UtilBObj;
import com.tcs.ifact.helper.IFactConstant;
import com.tcs.ifact.helper.IFactHelper;
import com.tcs.ifact.model.DBFile;
import com.tcs.ifact.model.Leakage;
import com.tcs.ifact.model.Pivot;
import com.tcs.ifact.model.Pwb;
import com.tcs.ifact.model.UserInfo;
import com.tcs.ifact.model.Util;

@Component
public class IFactObjectHandler {
	
	private static final Logger logger = LogManager.getLogger(IFactObjectHandler.class);

	@Autowired
	IFactDBHandler iFactDBHandler;


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
				user.setProjectrole(userBObj.getProjectrole());
				user.setRole(userBObj.getRole());
				user.setEmail(userBObj.getEmail());
			}
		}catch(Exception e) {
			logger.error(e);
			responseBObj.setError(true);
			responseBObj.setMessage(e.getMessage());
			return responseBObj;
		}
		return user;
	}

	public ResponseBObj conUserObjForreg(UserRegistrationBObj regBObj) {
		UserInfo user = new UserInfo();
		ResponseBObj responseBObj = new ResponseBObj();
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

		try {
			if(null != regBObj) {
				if(null != regBObj.getProjectrole()&& !regBObj.getProjectrole().trim().isEmpty()) {
					if(null != regBObj.getProjectrole() && regBObj.getProjectrole().equalsIgnoreCase(IFactConstant.PMO)) {
						if(null != regBObj.getReportto()&& !regBObj.getReportto().trim().isEmpty()) {
							user.setReportto(regBObj.getReportto().trim());
							user.setRole(IFactConstant.ROLE_PMO);
						}else {
							responseBObj.setError(true);
							responseBObj.setMessage("Please Enter Manager EmpId for PMO role");
						}
					}else if(regBObj.getProjectrole().equalsIgnoreCase(IFactConstant.DP)) {
						user.setRole(IFactConstant.ROLE_DP);
					}else if(regBObj.getProjectrole().equalsIgnoreCase(IFactConstant.DM)) {
						user.setRole(IFactConstant.ROLE_DM);
					}

					user.setFullName(regBObj.getFullname());
					user.setEnabled("N");
					user.setPassword(passwordEncoder.encode(regBObj.getPassword()));
					user.setUser(regBObj.getUser());
					user.setProjectrole(regBObj.getProjectrole());
					user.setEmail(regBObj.getEmail());
					Date date =new SimpleDateFormat("dd/MMM/yyyy").parse(regBObj.getDateOfJoining()); 
					user.setDateofjoining(date);
				}else {
					responseBObj.setError(true);
					responseBObj.setMessage("Please Enter Valid role");
				}

			}

			responseBObj.setError(false);
			responseBObj.setMessage("UserInfo Object Has been created");
			responseBObj.setResponseObject(user);

		}catch(Exception e) {
			e.printStackTrace();
			logger.error(e);
			responseBObj.setError(true);
			responseBObj.setMessage("Error in User Registration");
		}
		return responseBObj;
	}

	/*public ResponseBObj constructPWBFullExcelObject(ResponseBObj res) {
		ResponseBObj responseBObj = new ResponseBObj();
		try {
			ArrayList<PWBExcelBObj> pwbExcelList =  new ArrayList<PWBExcelBObj>();
			if(null != res && null != res.getResponseObject() && res.getResponseObject() instanceof List ) {
				ArrayList<Pwb> pwbList =  (ArrayList<Pwb>) res.getResponseObject();
				String dollerRate = iFactDBHandler.getUtilValueByKey(IFactConstant.DollerRate);
				if(null != pwbList) {
					Iterator<Pwb> itr = pwbList.iterator();
					while(itr.hasNext()){
						try {
							Pwb pwb = itr.next();
							PWBExcelBObj pwbExObj = new PWBExcelBObj();
							pwbExObj.setWorkerID(pwb.getWorkerID());
							pwbExObj.setEmpID(pwb.getEmpID());
							pwbExObj.setGDCEID(pwb.getGdceid());
							pwbExObj.setFirstName(pwb.getFirstName());
							pwbExObj.setLastName(pwb.getLastName());
							pwbExObj.setWONNumber(pwb.getWONNumber());
							pwbExObj.setWONName(pwb.getWONName());
							pwbExObj.setWONType(pwb.getWONType());
							pwbExObj.setWorkLocation(pwb.getWorkLocation());
							pwbExObj.setWorkCountry(pwb.getWorkCountry());
							pwbExObj.setSOWWorkerRole(pwb.getSOWWorkerRole());
							pwbExObj.setProjectType(pwb.getProjectType());
							pwbExObj.setDM(pwb.getDm());
							pwbExObj.setDP(pwb.getDp());
							pwbExObj.setCP(pwb.getCp());
							pwbExObj.setWorkerStartDate(IFactHelper.getDateInString(pwb.getWorkerStartDate()));
							pwbExObj.setWorkerEndDate(IFactHelper.getDateInString(pwb.getWorkerEndDate()));
							pwbExObj.setCurrentBillRate(pwb.getCurrentBillRate());

							pwbExObj.setMonth1Act(pwb.getMonth1Act());
							pwbExObj.setMonth1Max(pwb.getMonth1Max());
							pwbExObj.setMonth1Diff(IFactHelper.deriveMonthDiff(pwb.getMonth1Max(), pwb.getMonth1Act()));

							pwbExObj.setMonth2Act(pwb.getMonth2Act());
							pwbExObj.setMonth2Max(pwb.getMonth2Max());
							pwbExObj.setMonth2Diff(IFactHelper.deriveMonthDiff(pwb.getMonth2Max(), pwb.getMonth2Act()));

							pwbExObj.setMonth3Act(pwb.getMonth3Act());
							pwbExObj.setMonth3Max(pwb.getMonth3Max());
							pwbExObj.setMonth3Diff(IFactHelper.deriveMonthDiff(pwb.getMonth3Max(), pwb.getMonth3Act()));

							if(null != pwb.getLeakages()) {
								ArrayList<Leakage> leakageList = new ArrayList<Leakage>();
								if(null != leakageList && leakageList.size()>0) {
									Iterator<Leakage> leakitr = leakageList.iterator();
									while(leakitr.hasNext()) {
										try {

											Leakage leakage = leakitr.next();
											if(null != leakage) {
												if(null != leakage.getLeakageHrs()) {
													if(null != leakage.getLeakageMonth()) {
														if(IFactConstant.Month1.equalsIgnoreCase(leakage.getLeakageMonth())) {
															if(null != leakage.getLeakageReason() && IFactConstant.Leave.equalsIgnoreCase(leakage.getLeakageReason())) {
																pwbExObj.setMonth1Leave(leakage.getLeakageHrs());
																pwbExObj.setMonth1LeaveRev(leakage.getLeakageRevenue());
															}else if(null != leakage.getLeakageReason() && IFactConstant.RDwRepl.equalsIgnoreCase(leakage.getLeakageReason())) {
																pwbExObj.setMonth1RDwRepl(leakage.getLeakageHrs());
																pwbExObj.setMonth1RDwReplRev(leakage.getLeakageRevenue());
															}else if(null != leakage.getLeakageReason() && IFactConstant.RDwoRepl.equalsIgnoreCase(leakage.getLeakageReason())) {
																pwbExObj.setMonth1RDwoRepl(leakage.getLeakageHrs());
																pwbExObj.setMonth1RDwoReplRev(leakage.getLeakageRevenue());
															}else if(null != leakage.getLeakageReason() && IFactConstant.Parking.equalsIgnoreCase(leakage.getLeakageReason())) {
																pwbExObj.setMonth1Parking(leakage.getLeakageHrs());
																pwbExObj.setMonth1ParkingRev(leakage.getLeakageRevenue());
															}else if(null != leakage.getLeakageReason() && IFactConstant.Movement.equalsIgnoreCase(leakage.getLeakageReason())) {
																pwbExObj.setMonth1Movmt(leakage.getLeakageHrs());
																pwbExObj.setMonth1MovmtRev(leakage.getLeakageRevenue());
															}else if(null != leakage.getLeakageReason() && IFactConstant.Others.equalsIgnoreCase(leakage.getLeakageReason())) {
																pwbExObj.setMonth1Others(leakage.getLeakageHrs());
																pwbExObj.setMonth1OthersRev(leakage.getLeakageRevenue());
															}		
														}else if(IFactConstant.Month2.equalsIgnoreCase(leakage.getLeakageMonth())) {
															if(null != leakage.getLeakageReason() && IFactConstant.Leave.equalsIgnoreCase(leakage.getLeakageReason())) {
																pwbExObj.setMonth2Leave(leakage.getLeakageHrs());
																pwbExObj.setMonth2LeaveRev(leakage.getLeakageRevenue());
															}else if(null != leakage.getLeakageReason() && IFactConstant.RDwRepl.equalsIgnoreCase(leakage.getLeakageReason())) {
																pwbExObj.setMonth2RDwRepl(leakage.getLeakageHrs());
																pwbExObj.setMonth2RDwReplRev(leakage.getLeakageRevenue());
															}else if(null != leakage.getLeakageReason() && IFactConstant.RDwoRepl.equalsIgnoreCase(leakage.getLeakageReason())) {
																pwbExObj.setMonth2RDwoRepl(leakage.getLeakageHrs());
																pwbExObj.setMonth2RDwoReplRev(leakage.getLeakageRevenue());
															}else if(null != leakage.getLeakageReason() && IFactConstant.Parking.equalsIgnoreCase(leakage.getLeakageReason())) {
																pwbExObj.setMonth2Parking(leakage.getLeakageHrs());
																pwbExObj.setMonth2ParkingRev(leakage.getLeakageRevenue());
															}else if(null != leakage.getLeakageReason() && IFactConstant.Movement.equalsIgnoreCase(leakage.getLeakageReason())) {
																pwbExObj.setMonth2Movmt(leakage.getLeakageHrs());
																pwbExObj.setMonth2MovmtRev(leakage.getLeakageRevenue());
															}else if(null != leakage.getLeakageReason() && IFactConstant.Others.equalsIgnoreCase(leakage.getLeakageReason())) {
																pwbExObj.setMonth2Others(leakage.getLeakageHrs());
																pwbExObj.setMonth2OthersRev(leakage.getLeakageRevenue());
															}		
														}else if(IFactConstant.Month3.equalsIgnoreCase(leakage.getLeakageMonth())) {
															if(null != leakage.getLeakageReason() && IFactConstant.Leave.equalsIgnoreCase(leakage.getLeakageReason())) {
																pwbExObj.setMonth3Leave(leakage.getLeakageHrs());
																pwbExObj.setMonth3LeaveRev(leakage.getLeakageRevenue());
															}else if(null != leakage.getLeakageReason() && IFactConstant.RDwRepl.equalsIgnoreCase(leakage.getLeakageReason())) {
																pwbExObj.setMonth3RDwRepl(leakage.getLeakageHrs());
																pwbExObj.setMonth3RDwReplRev(leakage.getLeakageRevenue());
															}else if(null != leakage.getLeakageReason() && IFactConstant.RDwoRepl.equalsIgnoreCase(leakage.getLeakageReason())) {
																pwbExObj.setMonth3RDwoRepl(leakage.getLeakageHrs());
																pwbExObj.setMonth3RDwoReplRev(leakage.getLeakageRevenue());
															}else if(null != leakage.getLeakageReason() && IFactConstant.Parking.equalsIgnoreCase(leakage.getLeakageReason())) {
																pwbExObj.setMonth3Parking(leakage.getLeakageHrs());
																pwbExObj.setMonth3ParkingRev(leakage.getLeakageRevenue());
															}else if(null != leakage.getLeakageReason() && IFactConstant.Movement.equalsIgnoreCase(leakage.getLeakageReason())) {
																pwbExObj.setMonth3Movmt(leakage.getLeakageHrs());
																pwbExObj.setMonth3MovmtRev(leakage.getLeakageRevenue());
															}else if(null != leakage.getLeakageReason() && IFactConstant.Others.equalsIgnoreCase(leakage.getLeakageReason())) {
																pwbExObj.setMonth3Others(leakage.getLeakageHrs());
																pwbExObj.setMonth3OthersRev(leakage.getLeakageRevenue());
															}		
														}
													}
												}
											}
										}catch(Exception ex) {
											ex.printStackTrace();
											System.out.println("Error in constructPWBFullExcelObject in side Leakage Loop"+ex.getMessage());
										}
									}
								}
							}

							pwbExObj.setMonth1RevINR(IFactHelper.calculateMonthRev(pwbExObj.getMonth1LeaveRev(),pwbExObj.getMonth1RDwReplRev(),pwbExObj.getMonth1RDwoReplRev(),pwbExObj.getMonth1ParkingRev(),pwbExObj.getMonth1MovmtRev(),pwbExObj.getMonth1OthersRev()));
							pwbExObj.setMonth2RevINR(IFactHelper.calculateMonthRev(pwbExObj.getMonth2LeaveRev(),pwbExObj.getMonth2RDwReplRev(),pwbExObj.getMonth2RDwoReplRev(),pwbExObj.getMonth2ParkingRev(),pwbExObj.getMonth2MovmtRev(),pwbExObj.getMonth2OthersRev()));
							pwbExObj.setMonth3RevINR(IFactHelper.calculateMonthRev(pwbExObj.getMonth3LeaveRev(),pwbExObj.getMonth3RDwReplRev(),pwbExObj.getMonth3RDwoReplRev(),pwbExObj.getMonth3ParkingRev(),pwbExObj.getMonth3MovmtRev(),pwbExObj.getMonth3OthersRev()));

							pwbExObj.setMonth1Rev(IFactHelper.calculateRevInDoller(pwbExObj.getMonth1RevINR(),dollerRate));
							pwbExObj.setMonth2Rev(IFactHelper.calculateRevInDoller(pwbExObj.getMonth2RevINR(),dollerRate));
							pwbExObj.setMonth3Rev(IFactHelper.calculateRevInDoller(pwbExObj.getMonth3RevINR(),dollerRate));

							pwbExcelList.add(pwbExObj);
						}catch(Exception ex) {
							ex.printStackTrace();
							System.out.println("Error in constructPWBFullExcelObject in side pwb Loop"+ex.getMessage());
						}
					}
				}
				responseBObj.setError(false);
				responseBObj.setMessage("PWBExcelBObj List Created");
				responseBObj.setResponseObject(pwbExcelList);

			}
		}catch(Exception ex) {
			ex.printStackTrace();
			responseBObj.setError(true);
			responseBObj.setMessage("Error in PWBExcelBObj List Creation" + ex.getMessage());
		}
		return responseBObj;
	}*/

	public ResponseBObj constructPWBforBillingUpdate(ResponseBObj res, String user) {
		ResponseBObj responseBObj = new ResponseBObj();
		try {
			ArrayList<String> keystoUpdate = new ArrayList<String>();
			ArrayList<Pwb> pwbtoUpdate = new ArrayList<Pwb>();
			HashMap<String,PWBBObj> pwbBObjMap = null;
			HashMap<String,Pwb> pwbDBMap = null;
			
			LocalDate today = LocalDate.now();
			Date date = Date.from(today.atStartOfDay(ZoneId.systemDefault()).toInstant());
			
			boolean all3M = false;
			boolean last2M = false;
			boolean last1M = false;
			
			
			
			String all3Months = iFactDBHandler.getUtilValueByKey(IFactConstant.All3Months);
			String last2Months = iFactDBHandler.getUtilValueByKey(IFactConstant.Last2Months);
			String last1Month = iFactDBHandler.getUtilValueByKey(IFactConstant.Last1Month);
			
			if(null != all3Months && !all3Months.isEmpty() && all3Months.equalsIgnoreCase(IFactConstant.Y)) {
				all3M = true;
			}
			if(null != last2Months && !last2Months.isEmpty() && last2Months.equalsIgnoreCase(IFactConstant.Y)) {
				last2M = true;
			}
			if(null != last1Month && !last1Month.isEmpty() && last1Month.equalsIgnoreCase(IFactConstant.Y)) {
				last1M = true;
			}

			if(null != res && !res.isError() && null != res.getResponseObject() && res.getResponseObject() instanceof HashMap) {
				HashMap map = (HashMap)res.getResponseObject();
				if(null != map && map.containsKey("PWBMAP") && null != map.get("PWBMAP") && map.get("PWBMAP") instanceof HashMap) {
					pwbBObjMap = (HashMap<String, PWBBObj>) map.get("PWBMAP");
					pwbDBMap = iFactDBHandler.getPWBObjectForUserinMap(user);
					logger.debug("pwbBObjMap Size"+pwbBObjMap.size());
					logger.debug("pwbDBMap Size"+pwbDBMap.size());
					if(null != pwbBObjMap && null != pwbDBMap) {
						for (String key : pwbBObjMap.keySet()) {
							if(pwbDBMap.containsKey(key)) {
								keystoUpdate.add(key);

							}
						}
					}
				}
			}

			if(null != keystoUpdate && keystoUpdate.size()>0) {
				Iterator<String> itrString = keystoUpdate.iterator();
				while(itrString.hasNext()) {
					String key = itrString.next();
					if(null != pwbBObjMap && pwbBObjMap.containsKey(key) && null != pwbBObjMap.get(key)) {
						PWBBObj pwbBObj = pwbBObjMap.get(key);
						if(null != pwbBObj) {
							if(null != pwbDBMap && pwbDBMap.containsKey(key) && null != pwbDBMap.get(key)) {
								Pwb pwb = pwbDBMap.get(key);
								if(null != pwb) {
									updateLeakageInList(pwb,pwbBObj,date,user,all3M,last2M,last1M);
									pwb.setLastUpdatedDate(date);
									pwb.setLastUpdatedUser(user);
									pwb.setLeakages(pwb.getLeakages());
									pwbtoUpdate.add(pwb);
								}
							}
						}
					}
				}
			}
			
		
			responseBObj.setResponseObject(pwbtoUpdate);
			responseBObj.setError(false);
			responseBObj.setMessage("constructPWBforBillingUpdate Success");
		}catch(Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
			responseBObj.setError(true);
			responseBObj.setMessage("Error in constructPWBforBillingUpdate" + ex.getMessage());
		}
		return responseBObj;
	}

	private void updateLeakageInList(Pwb pwb, PWBBObj pwbBObj, Date date, String user, boolean all3M, boolean last2M,boolean last1M) {

		boolean month3Leavae = false;
		boolean month3RDwRepl = false;
		boolean month3RDwoRepl = false;
		boolean month3Parking = false;
		boolean month3Movmt = false;
		boolean month3Others = false;
		boolean month2Leavae = false;
		boolean month2RDwRepl = false;
		boolean month2RDwoRepl = false;
		boolean month2Parking = false;
		boolean month2Movmt = false;
		boolean month2Others = false;
		boolean month1Leavae = false;
		boolean month1RDwRepl = false;
		boolean month1RDwoRepl = false;
		boolean month1Parking = false;
		boolean month1Movmt = false;
		boolean month1Others = false;

		if(all3M) {
			if(null != pwbBObj.getMonth1Act()) {
				pwb.setMonth1Act(pwbBObj.getMonth1Act());
			}

			if(null != pwbBObj.getMonth2Act()) {
				pwb.setMonth2Act(pwbBObj.getMonth2Act());
			}

			if(null != pwbBObj.getMonth3Act()) {
				pwb.setMonth3Act(pwbBObj.getMonth3Act());
			}
		}

		if(all3M||last2M) {
			if(null != pwbBObj.getMonth2Act()) {
				pwb.setMonth2Act(pwbBObj.getMonth2Act());
			}

			if(null != pwbBObj.getMonth3Act()) {
				pwb.setMonth3Act(pwbBObj.getMonth3Act());
			}
		}

		if(all3M||last2M||last1M) {

			if(null != pwbBObj.getMonth3Act()) {
				pwb.setMonth3Act(pwbBObj.getMonth3Act());
			}
		}


		if(null != pwb.getLeakages() && pwb.getLeakages().size()>0) {
			List<Leakage> leakList = pwb.getLeakages();
			if(null != leakList) {
				Iterator<Leakage> leakitr = leakList.iterator();
				while(leakitr.hasNext()) {
					Leakage leak = leakitr.next();
					if(null != leak) {
						if(all3M) {
							if(null != leak.getLeakageMonth() && leak.getLeakageMonth().equalsIgnoreCase(IFactConstant.Month1) && leak.getLeakageReason().equalsIgnoreCase(IFactConstant.Leave)) {
								if(null != pwbBObj.getMonth1Leave()) {
									leak.setLeakageHrs(pwbBObj.getMonth1Leave());
									leak.setLeakageRevenue(pwbBObj.getMonth1LeaveRevINR());
									leak.setLastUpdatedDate(date);
									leak.setLastUpdatedUser(user);
									month1Leavae = true;
								}
							} if(null != leak.getLeakageMonth() && leak.getLeakageMonth().equalsIgnoreCase(IFactConstant.Month1) && leak.getLeakageReason().equalsIgnoreCase(IFactConstant.RDwRepl)) {
								if(null != pwbBObj.getMonth1RDwRepl()) {
									leak.setLeakageHrs(pwbBObj.getMonth1RDwRepl());
									leak.setLeakageRevenue(pwbBObj.getMonth1RDwReplRevINR());
									leak.setLastUpdatedDate(date);
									leak.setLastUpdatedUser(user);
									month1RDwRepl = true;
								}
							} if(null != leak.getLeakageMonth() && leak.getLeakageMonth().equalsIgnoreCase(IFactConstant.Month1) && leak.getLeakageReason().equalsIgnoreCase(IFactConstant.RDwoRepl)) {
								if(null != pwbBObj.getMonth1RDwoRepl()) {
									leak.setLeakageHrs(pwbBObj.getMonth1RDwoRepl());
									leak.setLeakageRevenue(pwbBObj.getMonth1RDwoReplRevINR());
									leak.setLastUpdatedDate(date);
									leak.setLastUpdatedUser(user);
									month1RDwoRepl = true;
								}
							} if(null != leak.getLeakageMonth() && leak.getLeakageMonth().equalsIgnoreCase(IFactConstant.Month1) && leak.getLeakageReason().equalsIgnoreCase(IFactConstant.Parking)) {
								if(null != pwbBObj.getMonth1Parking()) {
									leak.setLeakageHrs(pwbBObj.getMonth1Parking());
									leak.setLeakageRevenue(pwbBObj.getMonth1ParkingRevINR());
									leak.setLastUpdatedDate(date);
									leak.setLastUpdatedUser(user);
									month1Parking = true;
								}
							} if(null != leak.getLeakageMonth() && leak.getLeakageMonth().equalsIgnoreCase(IFactConstant.Month1) && leak.getLeakageReason().equalsIgnoreCase(IFactConstant.Movement)) {
								if(null != pwbBObj.getMonth1Movmt()) {
									leak.setLeakageHrs(pwbBObj.getMonth1Movmt());
									leak.setLeakageRevenue(pwbBObj.getMonth1MovmtRevINR());
									leak.setLastUpdatedDate(date);
									leak.setLastUpdatedUser(user);
									month1Movmt = true;
								}
							} if(null != leak.getLeakageMonth() && leak.getLeakageMonth().equalsIgnoreCase(IFactConstant.Month1) && leak.getLeakageReason().equalsIgnoreCase(IFactConstant.Others)) {
								if(null != pwbBObj.getMonth2Others()) {
									leak.setLeakageHrs(pwbBObj.getMonth1Others());
									leak.setLeakageRevenue(pwbBObj.getMonth1OthersRevINR());
									leak.setLastUpdatedDate(date);
									leak.setLastUpdatedUser(user);
									month2Others = true;
								}
							}
						}

						if(all3M || last2M) {

							if(null != leak.getLeakageMonth() && leak.getLeakageMonth().equalsIgnoreCase(IFactConstant.Month2) && leak.getLeakageReason().equalsIgnoreCase(IFactConstant.Leave)) {
								if(null != pwbBObj.getMonth2Leave()) {
									leak.setLeakageHrs(pwbBObj.getMonth2Leave());
									leak.setLeakageRevenue(pwbBObj.getMonth2LeaveRevINR());
									leak.setLastUpdatedDate(date);
									leak.setLastUpdatedUser(user);
									month2Leavae = true;
								}
							}

							if(null != leak.getLeakageMonth() && leak.getLeakageMonth().equalsIgnoreCase(IFactConstant.Month2) && leak.getLeakageReason().equalsIgnoreCase(IFactConstant.RDwRepl)) {
								if(null != pwbBObj.getMonth2RDwRepl()) {
									leak.setLeakageHrs(pwbBObj.getMonth2RDwRepl());
									leak.setLeakageRevenue(pwbBObj.getMonth2RDwReplRevINR());
									leak.setLastUpdatedDate(date);
									leak.setLastUpdatedUser(user);
									month2RDwRepl = true;
								}
							}

							if(null != leak.getLeakageMonth() && leak.getLeakageMonth().equalsIgnoreCase(IFactConstant.Month2) && leak.getLeakageReason().equalsIgnoreCase(IFactConstant.RDwoRepl)) {
								if(null != pwbBObj.getMonth2RDwoRepl()) {
									leak.setLeakageHrs(pwbBObj.getMonth2RDwoRepl());
									leak.setLeakageRevenue(pwbBObj.getMonth2RDwoReplRevINR());
									leak.setLastUpdatedDate(date);
									leak.setLastUpdatedUser(user);
									month2RDwoRepl = true;
								}
							}

							if(null != leak.getLeakageMonth() && leak.getLeakageMonth().equalsIgnoreCase(IFactConstant.Month2) && leak.getLeakageReason().equalsIgnoreCase(IFactConstant.Parking)) {
								if(null != pwbBObj.getMonth2Parking()) {
									leak.setLeakageHrs(pwbBObj.getMonth2Parking());
									leak.setLeakageRevenue(pwbBObj.getMonth2ParkingRevINR());
									leak.setLastUpdatedDate(date);
									leak.setLastUpdatedUser(user);
									month2Parking = true;
								}
							}

							if(null != leak.getLeakageMonth() && leak.getLeakageMonth().equalsIgnoreCase(IFactConstant.Month2) && leak.getLeakageReason().equalsIgnoreCase(IFactConstant.Movement)) {
								if(null != pwbBObj.getMonth2Movmt()) {
									leak.setLeakageHrs(pwbBObj.getMonth2Movmt());
									leak.setLeakageRevenue(pwbBObj.getMonth2MovmtRevINR());
									leak.setLastUpdatedDate(date);
									leak.setLastUpdatedUser(user);
									month2Movmt = true;
								}
							}

							if(null != leak.getLeakageMonth() && leak.getLeakageMonth().equalsIgnoreCase(IFactConstant.Month2) && leak.getLeakageReason().equalsIgnoreCase(IFactConstant.Others)) {
								if(null != pwbBObj.getMonth2Others()) {
									leak.setLeakageHrs(pwbBObj.getMonth2Others());
									leak.setLeakageRevenue(pwbBObj.getMonth2OthersRevINR());
									leak.setLastUpdatedDate(date);
									leak.setLastUpdatedUser(user);
									month2Others = true;
								}
							}
						}

						if(all3M || last2M || last1M) {

							if(null != leak.getLeakageMonth() && leak.getLeakageMonth().equalsIgnoreCase(IFactConstant.Month3) && leak.getLeakageReason().equalsIgnoreCase(IFactConstant.Leave)) {
								if(null != pwbBObj.getMonth3Leave()) {
									leak.setLeakageHrs(pwbBObj.getMonth3Leave());
									leak.setLeakageRevenue(pwbBObj.getMonth3LeaveRevINR());
									leak.setLastUpdatedDate(date);
									leak.setLastUpdatedUser(user);
									month3Leavae = true;
								}
							}


							if(null != leak.getLeakageMonth() && leak.getLeakageMonth().equalsIgnoreCase(IFactConstant.Month3) && leak.getLeakageReason().equalsIgnoreCase(IFactConstant.RDwRepl)) {
								if(null != pwbBObj.getMonth3RDwRepl()) {
									leak.setLeakageHrs(pwbBObj.getMonth3RDwRepl());
									leak.setLeakageRevenue(pwbBObj.getMonth3RDwReplRevINR());
									leak.setLastUpdatedDate(date);
									leak.setLastUpdatedUser(user);
									month3RDwRepl = true;
								}
							}


							if(null != leak.getLeakageMonth() && leak.getLeakageMonth().equalsIgnoreCase(IFactConstant.Month3) && leak.getLeakageReason().equalsIgnoreCase(IFactConstant.RDwoRepl)) {
								if(null != pwbBObj.getMonth3RDwoRepl()) {
									leak.setLeakageHrs(pwbBObj.getMonth3RDwoRepl());
									leak.setLeakageRevenue(pwbBObj.getMonth3RDwoReplRevINR());
									leak.setLastUpdatedDate(date);
									leak.setLastUpdatedUser(user);
									month3RDwoRepl = true;
								}
							}


							if(null != leak.getLeakageMonth() && leak.getLeakageMonth().equalsIgnoreCase(IFactConstant.Month3) && leak.getLeakageReason().equalsIgnoreCase(IFactConstant.Parking)) {
								if(null != pwbBObj.getMonth3Parking()) {
									leak.setLeakageHrs(pwbBObj.getMonth3Parking());
									leak.setLeakageRevenue(pwbBObj.getMonth3ParkingRevINR());
									leak.setLastUpdatedDate(date);
									leak.setLastUpdatedUser(user);
									month3Parking = true;
								}
							}

							if(null != leak.getLeakageMonth() && leak.getLeakageMonth().equalsIgnoreCase(IFactConstant.Month3) && leak.getLeakageReason().equalsIgnoreCase(IFactConstant.Movement)) {
								if(null != pwbBObj.getMonth3Movmt()) {
									leak.setLeakageHrs(pwbBObj.getMonth3Movmt());
									leak.setLeakageRevenue(pwbBObj.getMonth3MovmtRevINR());
									leak.setLastUpdatedDate(date);
									leak.setLastUpdatedUser(user);
									month3Movmt = true;
								}
							}

							if(null != leak.getLeakageMonth() && leak.getLeakageMonth().equalsIgnoreCase(IFactConstant.Month3) && leak.getLeakageReason().equalsIgnoreCase(IFactConstant.Others)) {
								if(null != pwbBObj.getMonth3Others()) {
									leak.setLeakageHrs(pwbBObj.getMonth3Others());
									leak.setLeakageRevenue(pwbBObj.getMonth3OthersRevINR());
									leak.setLastUpdatedDate(date);
									leak.setLastUpdatedUser(user);
									month3Others = true;
								}
							}
						}

					}
				}
			}
		}

		addLeakagetoList(pwb,pwbBObj,date,user,all3M,last2M,last1M,month1Leavae,month1RDwRepl,month1RDwoRepl,month1Parking,month1Movmt,month1Others,month2Leavae,month2RDwRepl,month2RDwoRepl,month2Parking,month2Movmt,month2Others,month3Leavae,month3RDwRepl,month3RDwoRepl,month3Parking,month3Movmt,month3Others);

	}

	private void addLeakagetoList(Pwb pwb, PWBBObj pwbBObj, Date date, String user, boolean all3M, boolean last2M,
			boolean last1M, boolean month1Leavae, boolean month1RDwRepl, boolean month1RDwoRepl, boolean month1Parking,
			boolean month1Movmt, boolean month1Others, boolean month2Leavae, boolean month2RDwRepl,
			boolean month2RDwoRepl, boolean month2Parking, boolean month2Movmt, boolean month2Others,
			boolean month3Leavae, boolean month3RDwRepl, boolean month3RDwoRepl, boolean month3Parking,
			boolean month3Movmt, boolean month3Others) {
		
		if(all3M) {
			if(null != pwbBObj.getMonth1Leave() && !month1Leavae) {
				pwb.addLeakage(addLeakage(IFactConstant.Month1,IFactConstant.Leave,pwbBObj.getMonth1Leave(),pwbBObj.getMonth1LeaveRevINR(),date,user));
			}

			if(null != pwbBObj.getMonth1RDwRepl() && !month1RDwRepl) {
				pwb.addLeakage(addLeakage(IFactConstant.Month1,IFactConstant.RDwRepl,pwbBObj.getMonth1RDwRepl(),pwbBObj.getMonth1RDwReplRevINR(),date,user));
			}

			if(null != pwbBObj.getMonth1RDwoRepl() && !month1RDwoRepl) {
				pwb.addLeakage(addLeakage(IFactConstant.Month1,IFactConstant.RDwoRepl,pwbBObj.getMonth1RDwoRepl(),pwbBObj.getMonth1RDwoReplRevINR(),date,user));
			}

			if(null != pwbBObj.getMonth1Parking() && !month1Parking) {
				pwb.addLeakage(addLeakage(IFactConstant.Month1,IFactConstant.Parking,pwbBObj.getMonth1Parking(),pwbBObj.getMonth1ParkingRevINR(),date,user));
			}

			if(null != pwbBObj.getMonth1Movmt() && !month1Movmt) {
				pwb.addLeakage(addLeakage(IFactConstant.Month1,IFactConstant.Movement,pwbBObj.getMonth1Movmt(),pwbBObj.getMonth1MovmtRevINR(),date,user));
			}

			if(null != pwbBObj.getMonth1Others() && !month1Others) {
				pwb.addLeakage(addLeakage(IFactConstant.Month1,IFactConstant.Others,pwbBObj.getMonth1Others(),pwbBObj.getMonth1OthersRevINR(),date,user));
			}
		}
		
		if(all3M || last2M) {
			if(null != pwbBObj.getMonth2Leave() && !month2Leavae) {
				pwb.addLeakage(addLeakage(IFactConstant.Month2,IFactConstant.Leave,pwbBObj.getMonth2Leave(),pwbBObj.getMonth2LeaveRevINR(),date,user));
			}

			if(null != pwbBObj.getMonth2RDwRepl() && !month2RDwRepl) {
				pwb.addLeakage(addLeakage(IFactConstant.Month2,IFactConstant.RDwRepl,pwbBObj.getMonth2RDwRepl(),pwbBObj.getMonth2RDwReplRevINR(),date,user));
			}

			if(null != pwbBObj.getMonth2RDwoRepl() && !month2RDwoRepl) {
				pwb.addLeakage(addLeakage(IFactConstant.Month2,IFactConstant.RDwoRepl,pwbBObj.getMonth2RDwoRepl(),pwbBObj.getMonth2RDwoReplRevINR(),date,user));
			}

			if(null != pwbBObj.getMonth2Parking() && !month2Parking) {
				pwb.addLeakage(addLeakage(IFactConstant.Month2,IFactConstant.Parking,pwbBObj.getMonth2Parking(),pwbBObj.getMonth2ParkingRevINR(),date,user));
			}

			if(null != pwbBObj.getMonth2Movmt() && !month2Movmt) {
				pwb.addLeakage(addLeakage(IFactConstant.Month2,IFactConstant.Movement,pwbBObj.getMonth2Movmt(),pwbBObj.getMonth2MovmtRevINR(),date,user));
			}

			if(null != pwbBObj.getMonth2Others() && !month2Others) {
				pwb.addLeakage(addLeakage(IFactConstant.Month2,IFactConstant.Others,pwbBObj.getMonth2Others(),pwbBObj.getMonth2OthersRevINR(),date,user));
			}
		}
		
		
		if(all3M || last2M || last1M) {
			if(null != pwbBObj.getMonth3Leave() && !month3Leavae) {
				pwb.addLeakage(addLeakage(IFactConstant.Month3,IFactConstant.Leave,pwbBObj.getMonth3Leave(),pwbBObj.getMonth3LeaveRevINR(),date,user));
			}

			if(null != pwbBObj.getMonth3RDwRepl() && !month3RDwRepl) {
				pwb.addLeakage(addLeakage(IFactConstant.Month3,IFactConstant.RDwRepl,pwbBObj.getMonth3RDwRepl(),pwbBObj.getMonth3RDwReplRevINR(),date,user));
			}

			if(null != pwbBObj.getMonth3RDwoRepl() && !month3RDwoRepl) {
				pwb.addLeakage(addLeakage(IFactConstant.Month3,IFactConstant.RDwoRepl,pwbBObj.getMonth3RDwoRepl(),pwbBObj.getMonth3RDwoReplRevINR(),date,user));
			}

			if(null != pwbBObj.getMonth3Parking() && !month3Parking) {
				pwb.addLeakage(addLeakage(IFactConstant.Month3,IFactConstant.Parking,pwbBObj.getMonth3Parking(),pwbBObj.getMonth3ParkingRevINR(),date,user));
			}

			if(null != pwbBObj.getMonth3Movmt() && !month3Movmt) {
				pwb.addLeakage(addLeakage(IFactConstant.Month3,IFactConstant.Movement,pwbBObj.getMonth3Movmt(),pwbBObj.getMonth3MovmtRevINR(),date,user));
			}

			if(null != pwbBObj.getMonth3Others() && !month3Others) {
				pwb.addLeakage(addLeakage(IFactConstant.Month3,IFactConstant.Others,pwbBObj.getMonth3Others(),pwbBObj.getMonth3OthersRevINR(),date,user));
			}
		}
		
	}

	private Leakage addLeakage(String month, String reason, String hrs, String revINR, Date date,String user) {
		Leakage newLeak = new Leakage();
		newLeak.setLeakageMonth(month);
		newLeak.setLeakageReason(reason);
		newLeak.setLeakageHrs(hrs);
		newLeak.setLeakageRevenue(revINR);
		newLeak.setLastUpdatedDate(date);
		newLeak.setLastUpdatedUser(user);
		return newLeak;
	}

	public ResponseBObj constructPWBRespObject(ResponseBObj res, String user) {
		ResponseBObj responseBObj = new ResponseBObj();
		PWBRevBObj pwbRevBObj = new PWBRevBObj();
		try {
			if(null != res && null != res.getResponseObject() && res.getResponseObject() instanceof List ) {
				ArrayList<Pwb> pwbList =  (ArrayList<Pwb>) res.getResponseObject();
				String dollerRate = iFactDBHandler.getUtilValueByKey(IFactConstant.DollerRate);
				if(null != pwbList) {
					Iterator<Pwb> itr = pwbList.iterator();
					while(itr.hasNext()){
						try {
							Pwb pwb = itr.next();
							PWBBObj pwbExObj = new PWBBObj();
							pwbExObj.setWorkerID(pwb.getWorkerID());
							pwbExObj.setEmpID(pwb.getEmpID());
							pwbExObj.setGDCEID(pwb.getGdceid());
							pwbExObj.setFirstName(pwb.getFirstName());
							pwbExObj.setLastName(pwb.getLastName());
							pwbExObj.setWONNumber(pwb.getWONNumber());
							pwbExObj.setWONName(pwb.getWONName());
							pwbExObj.setWONType(pwb.getWONType());
							pwbExObj.setWorkLocation(pwb.getWorkLocation());
							pwbExObj.setWorkCountry(pwb.getWorkCountry());
							pwbExObj.setSOWWorkerRole(pwb.getSOWWorkerRole());
							pwbExObj.setProjectType(pwb.getProjectType());
							pwbExObj.setDM(pwb.getDm());
							pwbExObj.setDP(pwb.getDp());
							pwbExObj.setCP(pwb.getCp());
							pwbExObj.setWorkerStartDate(IFactHelper.getDateInString(pwb.getWorkerStartDate()));
							pwbExObj.setWorkerEndDate(IFactHelper.getDateInString(pwb.getWorkerEndDate()));
							pwbExObj.setCurrentBillRate(pwb.getCurrentBillRate());

							pwbExObj.setMonth1Act(pwb.getMonth1Act());
							pwbExObj.setMonth1Max(pwb.getMonth1Max());
							pwbExObj.setMonth1Diff(IFactHelper.deriveMonthDiff(pwb.getMonth1Max(), pwb.getMonth1Act()));
							
							

							pwbExObj.setMonth2Act(pwb.getMonth2Act());
							pwbExObj.setMonth2Max(pwb.getMonth2Max());
							pwbExObj.setMonth2Diff(IFactHelper.deriveMonthDiff(pwb.getMonth2Max(), pwb.getMonth2Act()));

							pwbExObj.setMonth3Act(pwb.getMonth3Act());
							pwbExObj.setMonth3Max(pwb.getMonth3Max());
							pwbExObj.setMonth3Diff(IFactHelper.deriveMonthDiff(pwb.getMonth3Max(), pwb.getMonth3Act()));

							if(null != pwb.getLeakages()) {
								ArrayList<Leakage> leakageList = new ArrayList<Leakage>(pwb.getLeakages());
								if(null != leakageList && leakageList.size()>0) {
									Iterator<Leakage> leakitr = leakageList.iterator();
									while(leakitr.hasNext()) {
										try {

											Leakage leakage = leakitr.next();
											if(null != leakage) {
												if(null != leakage.getLeakageHrs()) {
													if(null != leakage.getLeakageMonth()) {
														if(IFactConstant.Month1.equalsIgnoreCase(leakage.getLeakageMonth())) {
															if(null != leakage.getLeakageReason() && IFactConstant.Leave.equalsIgnoreCase(leakage.getLeakageReason())) {
																pwbExObj.setMonth1Leave(leakage.getLeakageHrs());
																pwbExObj.setMonth1LeaveRevINR(leakage.getLeakageRevenue());
															}else if(null != leakage.getLeakageReason() && IFactConstant.RDwRepl.equalsIgnoreCase(leakage.getLeakageReason())) {
																pwbExObj.setMonth1RDwRepl(leakage.getLeakageHrs());
																pwbExObj.setMonth1RDwReplRevINR(leakage.getLeakageRevenue());
															}else if(null != leakage.getLeakageReason() && IFactConstant.RDwoRepl.equalsIgnoreCase(leakage.getLeakageReason())) {
																pwbExObj.setMonth1RDwoRepl(leakage.getLeakageHrs());
																pwbExObj.setMonth1RDwoReplRevINR(leakage.getLeakageRevenue());
															}else if(null != leakage.getLeakageReason() && IFactConstant.Parking.equalsIgnoreCase(leakage.getLeakageReason())) {
																pwbExObj.setMonth1Parking(leakage.getLeakageHrs());
																pwbExObj.setMonth1ParkingRevINR(leakage.getLeakageRevenue());
															}else if(null != leakage.getLeakageReason() && IFactConstant.Movement.equalsIgnoreCase(leakage.getLeakageReason())) {
																pwbExObj.setMonth1Movmt(leakage.getLeakageHrs());
																pwbExObj.setMonth1MovmtRevINR(leakage.getLeakageRevenue());
															}else if(null != leakage.getLeakageReason() && IFactConstant.Others.equalsIgnoreCase(leakage.getLeakageReason())) {
																pwbExObj.setMonth1Others(leakage.getLeakageHrs());
																pwbExObj.setMonth1OthersRevINR(leakage.getLeakageRevenue());
															}		
														}else if(IFactConstant.Month2.equalsIgnoreCase(leakage.getLeakageMonth())) {
															if(null != leakage.getLeakageReason() && IFactConstant.Leave.equalsIgnoreCase(leakage.getLeakageReason())) {
																pwbExObj.setMonth2Leave(leakage.getLeakageHrs());
																pwbExObj.setMonth2LeaveRevINR(leakage.getLeakageRevenue());
															}else if(null != leakage.getLeakageReason() && IFactConstant.RDwRepl.equalsIgnoreCase(leakage.getLeakageReason())) {
																pwbExObj.setMonth2RDwRepl(leakage.getLeakageHrs());
																pwbExObj.setMonth2RDwReplRevINR(leakage.getLeakageRevenue());
															}else if(null != leakage.getLeakageReason() && IFactConstant.RDwoRepl.equalsIgnoreCase(leakage.getLeakageReason())) {
																pwbExObj.setMonth2RDwoRepl(leakage.getLeakageHrs());
																pwbExObj.setMonth2RDwoReplRevINR(leakage.getLeakageRevenue());
															}else if(null != leakage.getLeakageReason() && IFactConstant.Parking.equalsIgnoreCase(leakage.getLeakageReason())) {
																pwbExObj.setMonth2Parking(leakage.getLeakageHrs());
																pwbExObj.setMonth2ParkingRevINR(leakage.getLeakageRevenue());
															}else if(null != leakage.getLeakageReason() && IFactConstant.Movement.equalsIgnoreCase(leakage.getLeakageReason())) {
																pwbExObj.setMonth2Movmt(leakage.getLeakageHrs());
																pwbExObj.setMonth2MovmtRevINR(leakage.getLeakageRevenue());
															}else if(null != leakage.getLeakageReason() && IFactConstant.Others.equalsIgnoreCase(leakage.getLeakageReason())) {
																pwbExObj.setMonth2Others(leakage.getLeakageHrs());
																pwbExObj.setMonth2OthersRevINR(leakage.getLeakageRevenue());
															}		
														}else if(IFactConstant.Month3.equalsIgnoreCase(leakage.getLeakageMonth())) {
															if(null != leakage.getLeakageReason() && IFactConstant.Leave.equalsIgnoreCase(leakage.getLeakageReason())) {
																pwbExObj.setMonth3Leave(leakage.getLeakageHrs());
																pwbExObj.setMonth3LeaveRevINR(leakage.getLeakageRevenue());
															}else if(null != leakage.getLeakageReason() && IFactConstant.RDwRepl.equalsIgnoreCase(leakage.getLeakageReason())) {
																pwbExObj.setMonth3RDwRepl(leakage.getLeakageHrs());
																pwbExObj.setMonth3RDwReplRevINR(leakage.getLeakageRevenue());
															}else if(null != leakage.getLeakageReason() && IFactConstant.RDwoRepl.equalsIgnoreCase(leakage.getLeakageReason())) {
																pwbExObj.setMonth3RDwoRepl(leakage.getLeakageHrs());
																pwbExObj.setMonth3RDwoReplRevINR(leakage.getLeakageRevenue());
															}else if(null != leakage.getLeakageReason() && IFactConstant.Parking.equalsIgnoreCase(leakage.getLeakageReason())) {
																pwbExObj.setMonth3Parking(leakage.getLeakageHrs());
																pwbExObj.setMonth3ParkingRevINR(leakage.getLeakageRevenue());
															}else if(null != leakage.getLeakageReason() && IFactConstant.Movement.equalsIgnoreCase(leakage.getLeakageReason())) {
																pwbExObj.setMonth3Movmt(leakage.getLeakageHrs());
																pwbExObj.setMonth3MovmtRevINR(leakage.getLeakageRevenue());
															}else if(null != leakage.getLeakageReason() && IFactConstant.Others.equalsIgnoreCase(leakage.getLeakageReason())) {
																pwbExObj.setMonth3Others(leakage.getLeakageHrs());
																pwbExObj.setMonth3OthersRevINR(leakage.getLeakageRevenue());
															}		
														}
													}
												}
											}
										}catch(Exception ex) {
											ex.printStackTrace();
											System.out.println("Error in constructPWBFullExcelObject in side Leakage Loop"+ex.getMessage());
										}
									}
								}
							}
							
							
							pwbExObj.setMonth1LeaveRev(IFactHelper.calculateRevInDoller(pwbExObj.getMonth1LeaveRevINR(),dollerRate));
							pwbExObj.setMonth1RDwReplRev(IFactHelper.calculateRevInDoller(pwbExObj.getMonth1RDwReplRevINR(),dollerRate));
							pwbExObj.setMonth1RDwoReplRev(IFactHelper.calculateRevInDoller(pwbExObj.getMonth1RDwoReplRevINR(),dollerRate));
							pwbExObj.setMonth1ParkingRev(IFactHelper.calculateRevInDoller(pwbExObj.getMonth1ParkingRevINR(),dollerRate));
							pwbExObj.setMonth1MovmtRev(IFactHelper.calculateRevInDoller(pwbExObj.getMonth1MovmtRevINR(),dollerRate));
							pwbExObj.setMonth1OthersRev(IFactHelper.calculateRevInDoller(pwbExObj.getMonth1OthersRevINR(),dollerRate));
							
							pwbExObj.setMonth2LeaveRev(IFactHelper.calculateRevInDoller(pwbExObj.getMonth2LeaveRevINR(),dollerRate));
							pwbExObj.setMonth2RDwReplRev(IFactHelper.calculateRevInDoller(pwbExObj.getMonth2RDwReplRevINR(),dollerRate));
							pwbExObj.setMonth2RDwoReplRev(IFactHelper.calculateRevInDoller(pwbExObj.getMonth2RDwoReplRevINR(),dollerRate));
							pwbExObj.setMonth2ParkingRev(IFactHelper.calculateRevInDoller(pwbExObj.getMonth2ParkingRevINR(),dollerRate));
							pwbExObj.setMonth2MovmtRev(IFactHelper.calculateRevInDoller(pwbExObj.getMonth2MovmtRevINR(),dollerRate));
							pwbExObj.setMonth2OthersRev(IFactHelper.calculateRevInDoller(pwbExObj.getMonth2OthersRevINR(),dollerRate));
							
							pwbExObj.setMonth3LeaveRev(IFactHelper.calculateRevInDoller(pwbExObj.getMonth2LeaveRevINR(),dollerRate));
							pwbExObj.setMonth3RDwReplRev(IFactHelper.calculateRevInDoller(pwbExObj.getMonth2RDwReplRevINR(),dollerRate));
							pwbExObj.setMonth3RDwoReplRev(IFactHelper.calculateRevInDoller(pwbExObj.getMonth2RDwoReplRevINR(),dollerRate));
							pwbExObj.setMonth3ParkingRev(IFactHelper.calculateRevInDoller(pwbExObj.getMonth2ParkingRevINR(),dollerRate));
							pwbExObj.setMonth3MovmtRev(IFactHelper.calculateRevInDoller(pwbExObj.getMonth2MovmtRevINR(),dollerRate));
							pwbExObj.setMonth3OthersRev(IFactHelper.calculateRevInDoller(pwbExObj.getMonth2OthersRevINR(),dollerRate));

							pwbExObj.setMonth1RevINR(IFactHelper.calculateMonthRev(pwbExObj.getMonth1LeaveRevINR(),pwbExObj.getMonth1RDwReplRevINR(),pwbExObj.getMonth1RDwoReplRevINR(),pwbExObj.getMonth1ParkingRevINR(),pwbExObj.getMonth1MovmtRevINR(),pwbExObj.getMonth1OthersRevINR()));
							pwbExObj.setMonth2RevINR(IFactHelper.calculateMonthRev(pwbExObj.getMonth2LeaveRevINR(),pwbExObj.getMonth2RDwReplRevINR(),pwbExObj.getMonth2RDwoReplRevINR(),pwbExObj.getMonth2ParkingRevINR(),pwbExObj.getMonth2MovmtRevINR(),pwbExObj.getMonth2OthersRevINR()));
							pwbExObj.setMonth3RevINR(IFactHelper.calculateMonthRev(pwbExObj.getMonth3LeaveRevINR(),pwbExObj.getMonth3RDwReplRevINR(),pwbExObj.getMonth3RDwoReplRevINR(),pwbExObj.getMonth3ParkingRevINR(),pwbExObj.getMonth3MovmtRevINR(),pwbExObj.getMonth3OthersRevINR()));
							pwbExObj.setQuaterTotalRevINR(IFactHelper.getQuarterTotal(pwbExObj.getMonth1RevINR(),pwbExObj.getMonth2RevINR(),pwbExObj.getMonth3RevINR()));
							
							pwbExObj.setMonth1Rev(IFactHelper.calculateRevInDoller(pwbExObj.getMonth1RevINR(),dollerRate));
							pwbExObj.setMonth2Rev(IFactHelper.calculateRevInDoller(pwbExObj.getMonth2RevINR(),dollerRate));
							pwbExObj.setMonth3Rev(IFactHelper.calculateRevInDoller(pwbExObj.getMonth3RevINR(),dollerRate));
							pwbExObj.setQuaterTotalRev(IFactHelper.getQuarterTotal(pwbExObj.getMonth1Rev(),pwbExObj.getMonth2Rev(),pwbExObj.getMonth3Rev()));
													
							pwbExObj.setMonth1ActRevINR(IFactHelper.calculateHrsRev(pwbExObj.getCurrentBillRate(), pwbExObj.getMonth1Act(), dollerRate));
							pwbExObj.setMonth2ActRevINR(IFactHelper.calculateHrsRev(pwbExObj.getCurrentBillRate(), pwbExObj.getMonth2Act(), dollerRate));
							pwbExObj.setMonth3ActRevINR(IFactHelper.calculateHrsRev(pwbExObj.getCurrentBillRate(), pwbExObj.getMonth3Act(), dollerRate));
							pwbExObj.setQuaterTotalActRevINR(IFactHelper.getQuarterTotal(pwbExObj.getMonth1ActRevINR(),pwbExObj.getMonth2ActRevINR(),pwbExObj.getMonth3ActRevINR()));
							
							pwbExObj.setMonth1ActRev(IFactHelper.calculateRevInDoller(pwbExObj.getMonth1ActRevINR(),dollerRate));
							pwbExObj.setMonth2ActRev(IFactHelper.calculateRevInDoller(pwbExObj.getMonth2ActRevINR(),dollerRate));
							pwbExObj.setMonth3ActRev(IFactHelper.calculateRevInDoller(pwbExObj.getMonth3ActRevINR(),dollerRate));
							pwbExObj.setQuaterTotalActRev(IFactHelper.getQuarterTotal(pwbExObj.getMonth1ActRev(),pwbExObj.getMonth2ActRev(),pwbExObj.getMonth3ActRev()));
							
							pwbExObj.setMonth1MaxRevINR(IFactHelper.calculateHrsRev(pwbExObj.getCurrentBillRate(), pwbExObj.getMonth1Max(), dollerRate));
							pwbExObj.setMonth2MaxRevINR(IFactHelper.calculateHrsRev(pwbExObj.getCurrentBillRate(), pwbExObj.getMonth2Max(), dollerRate));
							pwbExObj.setMonth3MaxRevINR(IFactHelper.calculateHrsRev(pwbExObj.getCurrentBillRate(), pwbExObj.getMonth3Max(), dollerRate));
							pwbExObj.setQuaterTotalMaxRevINR(IFactHelper.getQuarterTotal(pwbExObj.getMonth1MaxRevINR(),pwbExObj.getMonth2MaxRevINR(),pwbExObj.getMonth3MaxRevINR()));
							
							pwbExObj.setMonth1MaxRev(IFactHelper.calculateRevInDoller(pwbExObj.getMonth1MaxRevINR(),dollerRate));
							pwbExObj.setMonth2MaxRev(IFactHelper.calculateRevInDoller(pwbExObj.getMonth2MaxRevINR(),dollerRate));
							pwbExObj.setMonth3MaxRev(IFactHelper.calculateRevInDoller(pwbExObj.getMonth3MaxRevINR(),dollerRate));
							pwbExObj.setQuaterTotalMaxRev(IFactHelper.getQuarterTotal(pwbExObj.getMonth1MaxRev(),pwbExObj.getMonth2MaxRev(),pwbExObj.getMonth3MaxRev()));
							
							pwbExObj.setMonth1DiffRevINR(IFactHelper.calculateHrsRev(pwbExObj.getCurrentBillRate(), pwbExObj.getMonth1Diff(), dollerRate));
							pwbExObj.setMonth2DiffRevINR(IFactHelper.calculateHrsRev(pwbExObj.getCurrentBillRate(), pwbExObj.getMonth2Diff(), dollerRate));
							pwbExObj.setMonth3DiffRevINR(IFactHelper.calculateHrsRev(pwbExObj.getCurrentBillRate(), pwbExObj.getMonth3Diff(), dollerRate));
							pwbExObj.setQuaterTotalDiffRevINR(IFactHelper.getQuarterTotal(pwbExObj.getMonth1DiffRevINR(),pwbExObj.getMonth2DiffRevINR(),pwbExObj.getMonth3DiffRevINR()));
							
							pwbExObj.setMonth1DiffRev(IFactHelper.calculateRevInDoller(pwbExObj.getMonth1DiffRevINR(),dollerRate));
							pwbExObj.setMonth2DiffRev(IFactHelper.calculateRevInDoller(pwbExObj.getMonth2DiffRevINR(),dollerRate));
							pwbExObj.setMonth3DiffRev(IFactHelper.calculateRevInDoller(pwbExObj.getMonth3DiffRevINR(),dollerRate));
							pwbExObj.setQuaterTotalDiffRev(IFactHelper.getQuarterTotal(pwbExObj.getMonth1DiffRev(),pwbExObj.getMonth2DiffRev(),pwbExObj.getMonth3DiffRev()));
							
							
							constructRevObj(pwbRevBObj ,pwbExObj);
						}catch(Exception ex) {
							ex.printStackTrace();
							System.out.println("Error in constructPWBFullExcelObject in side pwb Loop"+ex.getMessage());
						}
					}
				}
				
				responseBObj.setError(false);
				responseBObj.setMessage("pwbRevBObj List Created");
				responseBObj.setResponseObject(pwbRevBObj);

			}
		}catch(Exception ex) {
			logger.error(ex);
			responseBObj.setError(true);
			responseBObj.setMessage("Error in pwbRevBObj  Creation" + ex.getMessage());
		}
		return responseBObj;
	}

	private void printDMDMPCP(PWBRevBObj pwbRevBObj) {
		if(null != pwbRevBObj && null != pwbRevBObj.getCpRevBObjList() && pwbRevBObj.getCpRevBObjList().size()>0) {
			ArrayList<CPRevBObj> cpList = pwbRevBObj.getCpRevBObjList();
			Iterator<CPRevBObj> cpItr = cpList.iterator();
			while(cpItr.hasNext()) {
				CPRevBObj cp = cpItr.next();
				if(null !=cp ) {
					if(null != cp.getDPRevBObjList() && cp.getDPRevBObjList().size()>0) {
						ArrayList<DPRevBObj> dpList = cp.getDPRevBObjList();
						Iterator<DPRevBObj> dpItr = dpList.iterator();
						while(dpItr.hasNext()) {
							DPRevBObj dp = dpItr.next();
							if(null != dp) {
								if(null != dp.getDMRevBObjList() && dp.getDMRevBObjList().size()>0) {
									ArrayList<DMRevBObj> dmList = dp.getDMRevBObjList();
									Iterator<DMRevBObj> dmItr = dmList.iterator();
									while(dmItr.hasNext()) {
										DMRevBObj dm = dmItr.next();
										if(null != dm) {
											logger.debug("CP Name:"+cp.getCPName()+":::"+"DP Name:"+dp.getDPName()+":::"+"DM Name:" + dm.getDMName() );
										}
									}
								}
							}
						}
					}
				}
				
			}
			
		}
	}

	private void constructRevObj(PWBRevBObj pwbRevBObj, PWBBObj pwbExObj) {

		boolean isCPExist = false;
		boolean isDPExist = false;
		boolean isDMExist = false;
		
		if(null != pwbExObj) {
			isCPExist = validateCPExist(pwbRevBObj,pwbExObj);
			isDPExist = validateDPExist(pwbRevBObj,pwbExObj);
			isDMExist = validateDMExist(pwbRevBObj,pwbExObj);
			if(null != pwbRevBObj && null != pwbRevBObj.getCpRevBObjList() && pwbRevBObj.getCpRevBObjList().size()>0 && isCPExist) {
				ArrayList<CPRevBObj> cpList = pwbRevBObj.getCpRevBObjList();
				Iterator<CPRevBObj> cpItr = cpList.iterator();
				while(cpItr.hasNext()) {
					CPRevBObj cp = cpItr.next();
					if(pwbExObj.getCP().equalsIgnoreCase(cp.getCPName())) {
						cp = mapCPObject(pwbExObj, cp);
						if(null != cp && null != cp.getDPRevBObjList() && cp.getDPRevBObjList().size()>0 && isDPExist) {
							ArrayList<DPRevBObj> dpList = cp.getDPRevBObjList();
							Iterator<DPRevBObj> dpItr = dpList.iterator();
							while(dpItr.hasNext()) {
								DPRevBObj dp = dpItr.next();
								if(pwbExObj.getDP().equalsIgnoreCase(dp.getDPName())) {
									dp = mapDPObject(pwbExObj, dp);
									if(null != dp && null != dp.getDMRevBObjList() && dp.getDMRevBObjList().size()>0 && isDMExist) {
										ArrayList<DMRevBObj> dmList = dp.getDMRevBObjList();
										Iterator<DMRevBObj> dmItr = dmList.iterator();
										while(dmItr.hasNext()) {
											DMRevBObj dm = dmItr.next();
											if(null != dm && pwbExObj.getDM().equalsIgnoreCase(dm.getDMName())) {
												dm = mapDMObject(pwbExObj,dm);
											}
										}
									}else {
										DMRevBObj dmBobj = new DMRevBObj();
										dmBobj.setDMName(pwbExObj.getDM());
										dmBobj = mapDMObject(pwbExObj,dmBobj);
										dp.addDMRevBObj(dmBobj);
									}
								}
								
							}
							
						}else {
							
							DMRevBObj dmBobj = new DMRevBObj();
							dmBobj.setDMName(pwbExObj.getDM());
							dmBobj = mapDMObject(pwbExObj,dmBobj);
											
							DPRevBObj dpBobj = new DPRevBObj();
							dpBobj.addDMRevBObj(dmBobj);
							dpBobj.setDPName(pwbExObj.getDP());
							dpBobj = mapDPObject(pwbExObj,dpBobj);			
							cp.addDPRevBObj(dpBobj);
							
						}
					}
				}
			}else {
				
				DMRevBObj dmBobj = new DMRevBObj();
				dmBobj.setDMName(pwbExObj.getDM());
				dmBobj = mapDMObject(pwbExObj,dmBobj);
								
				DPRevBObj dpBobj = new DPRevBObj();
				dpBobj.addDMRevBObj(dmBobj);
				dpBobj.setDPName(pwbExObj.getDP());
				dpBobj = mapDPObject(pwbExObj,dpBobj);	
				
				CPRevBObj cpBobj = new CPRevBObj();
				cpBobj.addDPRevBObj(dpBobj);
				cpBobj.setCPName(pwbExObj.getCP());
				cpBobj = mapCPObject(pwbExObj,cpBobj);
				pwbRevBObj.addCPRevBObj(cpBobj);
				
			
			}
		

		}

	}

	private boolean validateCPExist(PWBRevBObj pwbRevBObj, PWBBObj pwbExObj) {

		boolean flag = false;
		if(null != pwbRevBObj && null != pwbRevBObj.getCpRevBObjList() && pwbRevBObj.getCpRevBObjList().size()>0) {
			ArrayList<CPRevBObj> cpList = pwbRevBObj.getCpRevBObjList();
			Iterator<CPRevBObj> cpItr = cpList.iterator();
			while(cpItr.hasNext()) {
				CPRevBObj cp = cpItr.next();
				if(null !=cp && null != cp.getCPName() && cp.getCPName().equalsIgnoreCase(pwbExObj.getCP())) {
					flag = true;
				}
			}
		}
		return flag;
	}

	private boolean validateDMExist(PWBRevBObj pwbRevBObj, PWBBObj pwbExObj) {
		
		boolean flag = false;
		if(null != pwbRevBObj && null != pwbRevBObj.getCpRevBObjList() && pwbRevBObj.getCpRevBObjList().size()>0) {
			ArrayList<CPRevBObj> cpList = pwbRevBObj.getCpRevBObjList();
			Iterator<CPRevBObj> cpItr = cpList.iterator();
			while(cpItr.hasNext()) {
				CPRevBObj cp = cpItr.next();
				if(null !=cp && null != cp.getCPName() && cp.getCPName().equalsIgnoreCase(pwbExObj.getCP())) {
					if(null != cp.getDPRevBObjList() && cp.getDPRevBObjList().size()>0) {
						ArrayList<DPRevBObj> dpList = cp.getDPRevBObjList();
						Iterator<DPRevBObj> dpItr = dpList.iterator();
						while(dpItr.hasNext()) {
							DPRevBObj dp = dpItr.next();
							if(null != dp && null != dp.getDPName() && dp.getDPName().equalsIgnoreCase(pwbExObj.getDP())) {
								if(null != dp.getDMRevBObjList() && dp.getDMRevBObjList().size()>0) {
									ArrayList<DMRevBObj> dmList = dp.getDMRevBObjList();
									Iterator<DMRevBObj> dmItr = dmList.iterator();
									while(dmItr.hasNext()) {
										DMRevBObj dm = dmItr.next();
										if(null != dm && null !=dm.getDMName() && dm.getDMName().equalsIgnoreCase(pwbExObj.getDM())) {
											flag = true;
										}
									}
								}
							}
						}
					}
				}

			}

		}
		return flag;
	}

	private boolean validateDPExist(PWBRevBObj pwbRevBObj, PWBBObj pwbExObj) {

		boolean flag = false;
		if(null != pwbRevBObj && null != pwbRevBObj.getCpRevBObjList() && pwbRevBObj.getCpRevBObjList().size()>0) {
			ArrayList<CPRevBObj> cpList = pwbRevBObj.getCpRevBObjList();
			Iterator<CPRevBObj> cpItr = cpList.iterator();
			while(cpItr.hasNext()) {
				CPRevBObj cp = cpItr.next();
				if(null !=cp && null != cp.getCPName() && cp.getCPName().equalsIgnoreCase(pwbExObj.getCP())) {
					if(null != cp.getDPRevBObjList() && cp.getDPRevBObjList().size()>0) {
						ArrayList<DPRevBObj> dpList = cp.getDPRevBObjList();
						Iterator<DPRevBObj> dpItr = dpList.iterator();
						while(dpItr.hasNext()) {
							DPRevBObj dp = dpItr.next();
							if(null != dp && null != dp.getDPName() && dp.getDPName().equalsIgnoreCase(pwbExObj.getDP())) {
								flag = true;
							}
						}
					}
				}

			}

		}
		return flag;
	}
	
	private CPRevBObj mapCPObject(PWBBObj pwbExObj, CPRevBObj cpBobj) {
		
		cpBobj.setMonth1LeaveRevINR(IFactHelper.add2String(cpBobj.getMonth1LeaveRevINR(),pwbExObj.getMonth1LeaveRevINR()));
		cpBobj.setMonth1RDwReplRevINR(IFactHelper.add2String(cpBobj.getMonth1RDwReplRevINR(),pwbExObj.getMonth1RDwReplRevINR()));
		cpBobj.setMonth1RDwoReplRevINR(IFactHelper.add2String(cpBobj.getMonth1RDwoReplRevINR(),pwbExObj.getMonth1RDwoReplRevINR()));
		cpBobj.setMonth1ParkingRevINR(IFactHelper.add2String(cpBobj.getMonth1ParkingRevINR(),pwbExObj.getMonth1ParkingRevINR()));
		cpBobj.setMonth1MovmtRevINR(IFactHelper.add2String(cpBobj.getMonth1MovmtRevINR(),pwbExObj.getMonth1MovmtRevINR()));
		cpBobj.setMonth1OthersRevINR(IFactHelper.add2String(cpBobj.getMonth1OthersRevINR(),pwbExObj.getMonth1OthersRevINR()));
		
		cpBobj.setMonth1LeaveRev(IFactHelper.add2String(cpBobj.getMonth1LeaveRev(),pwbExObj.getMonth1LeaveRev()));
		cpBobj.setMonth1RDwReplRev(IFactHelper.add2String(cpBobj.getMonth1RDwReplRev(),pwbExObj.getMonth1RDwReplRev()));
		cpBobj.setMonth1RDwoReplRev(IFactHelper.add2String(cpBobj.getMonth1RDwoReplRev(),pwbExObj.getMonth1RDwoReplRev()));
		cpBobj.setMonth1ParkingRev(IFactHelper.add2String(cpBobj.getMonth1ParkingRev(),pwbExObj.getMonth1ParkingRev()));
		cpBobj.setMonth1MovmtRev(IFactHelper.add2String(cpBobj.getMonth1MovmtRev(),pwbExObj.getMonth1MovmtRev()));
		cpBobj.setMonth1OthersRev(IFactHelper.add2String(cpBobj.getMonth1OthersRev(),pwbExObj.getMonth1OthersRev()));
		
		cpBobj.setMonth2LeaveRevINR(IFactHelper.add2String(cpBobj.getMonth2LeaveRevINR(),pwbExObj.getMonth2LeaveRevINR()));
		cpBobj.setMonth2RDwReplRevINR(IFactHelper.add2String(cpBobj.getMonth2RDwReplRevINR(),pwbExObj.getMonth2RDwReplRevINR()));
		cpBobj.setMonth2RDwoReplRevINR(IFactHelper.add2String(cpBobj.getMonth2RDwoReplRevINR(),pwbExObj.getMonth2RDwoReplRevINR()));
		cpBobj.setMonth2ParkingRevINR(IFactHelper.add2String(cpBobj.getMonth2ParkingRevINR(),pwbExObj.getMonth2ParkingRevINR()));
		cpBobj.setMonth2MovmtRevINR(IFactHelper.add2String(cpBobj.getMonth2MovmtRevINR(),pwbExObj.getMonth2MovmtRevINR()));
		cpBobj.setMonth2OthersRevINR(IFactHelper.add2String(cpBobj.getMonth2OthersRevINR(),pwbExObj.getMonth2OthersRevINR()));
		
		cpBobj.setMonth2LeaveRev(IFactHelper.add2String(cpBobj.getMonth2LeaveRev(),pwbExObj.getMonth2LeaveRev()));
		cpBobj.setMonth2RDwReplRev(IFactHelper.add2String(cpBobj.getMonth2RDwReplRev(),pwbExObj.getMonth2RDwReplRev()));
		cpBobj.setMonth2RDwoReplRev(IFactHelper.add2String(cpBobj.getMonth2RDwoReplRev(),pwbExObj.getMonth2RDwoReplRev()));
		cpBobj.setMonth2ParkingRev(IFactHelper.add2String(cpBobj.getMonth2ParkingRev(),pwbExObj.getMonth2ParkingRev()));
		cpBobj.setMonth2MovmtRev(IFactHelper.add2String(cpBobj.getMonth2MovmtRev(),pwbExObj.getMonth2MovmtRev()));
		cpBobj.setMonth2OthersRev(IFactHelper.add2String(cpBobj.getMonth2OthersRev(),pwbExObj.getMonth2OthersRev()));
		
		cpBobj.setMonth3LeaveRevINR(IFactHelper.add2String(cpBobj.getMonth3LeaveRevINR(),pwbExObj.getMonth3LeaveRevINR()));
		cpBobj.setMonth3RDwReplRevINR(IFactHelper.add2String(cpBobj.getMonth3RDwReplRevINR(),pwbExObj.getMonth3RDwReplRevINR()));
		cpBobj.setMonth3RDwoReplRevINR(IFactHelper.add2String(cpBobj.getMonth3RDwoReplRevINR(),pwbExObj.getMonth3RDwoReplRevINR()));
		cpBobj.setMonth3ParkingRevINR(IFactHelper.add2String(cpBobj.getMonth3ParkingRevINR(),pwbExObj.getMonth3ParkingRevINR()));
		cpBobj.setMonth3MovmtRevINR(IFactHelper.add2String(cpBobj.getMonth3MovmtRevINR(),pwbExObj.getMonth3MovmtRevINR()));
		cpBobj.setMonth3OthersRevINR(IFactHelper.add2String(cpBobj.getMonth3OthersRevINR(),pwbExObj.getMonth3OthersRevINR()));
		
		cpBobj.setMonth3LeaveRev(IFactHelper.add2String(cpBobj.getMonth3LeaveRev(),pwbExObj.getMonth3LeaveRev()));
		cpBobj.setMonth3RDwReplRev(IFactHelper.add2String(cpBobj.getMonth3RDwReplRev(),pwbExObj.getMonth3RDwReplRev()));
		cpBobj.setMonth3RDwoReplRev(IFactHelper.add2String(cpBobj.getMonth3RDwoReplRev(),pwbExObj.getMonth3RDwoReplRev()));
		cpBobj.setMonth3ParkingRev(IFactHelper.add2String(cpBobj.getMonth3ParkingRev(),pwbExObj.getMonth3ParkingRev()));
		cpBobj.setMonth3MovmtRev(IFactHelper.add2String(cpBobj.getMonth3MovmtRev(),pwbExObj.getMonth3MovmtRev()));
		cpBobj.setMonth3OthersRev(IFactHelper.add2String(cpBobj.getMonth3OthersRev(),pwbExObj.getMonth3OthersRev()));
		
		cpBobj.setMonth1RevINR(IFactHelper.add2String(cpBobj.getMonth1RevINR(),pwbExObj.getMonth1RevINR()));
		cpBobj.setMonth2RevINR(IFactHelper.add2String(cpBobj.getMonth2RevINR(),pwbExObj.getMonth2RevINR()));
		cpBobj.setMonth3RevINR(IFactHelper.add2String(cpBobj.getMonth3RevINR(),pwbExObj.getMonth3RevINR()));
		cpBobj.setMonth1Rev(IFactHelper.add2String(cpBobj.getMonth1Rev(),pwbExObj.getMonth1Rev()));
		cpBobj.setMonth2Rev(IFactHelper.add2String(cpBobj.getMonth2Rev(),pwbExObj.getMonth2Rev()));
		cpBobj.setMonth3Rev(IFactHelper.add2String(cpBobj.getMonth3Rev(),pwbExObj.getMonth3Rev()));
		cpBobj.setQuaterTotalRevINR(IFactHelper.add2String(cpBobj.getQuaterTotalRevINR(),pwbExObj.getQuaterTotalRevINR()));
		cpBobj.setQuaterTotalRev(IFactHelper.add2String(cpBobj.getQuaterTotalRev(),pwbExObj.getQuaterTotalRev()));	
		
		cpBobj.setMonth1MaxRevINR(IFactHelper.add2String(cpBobj.getMonth1MaxRevINR(),pwbExObj.getMonth1MaxRevINR()));
		cpBobj.setMonth2MaxRevINR(IFactHelper.add2String(cpBobj.getMonth2MaxRevINR(),pwbExObj.getMonth2MaxRevINR()));
		cpBobj.setMonth3MaxRevINR(IFactHelper.add2String(cpBobj.getMonth3MaxRevINR(),pwbExObj.getMonth3MaxRevINR()));
		cpBobj.setMonth1MaxRev(IFactHelper.add2String(cpBobj.getMonth1MaxRev(),pwbExObj.getMonth1MaxRev()));
		cpBobj.setMonth2MaxRev(IFactHelper.add2String(cpBobj.getMonth2MaxRev(),pwbExObj.getMonth2MaxRev()));
		cpBobj.setMonth3MaxRev(IFactHelper.add2String(cpBobj.getMonth3MaxRev(),pwbExObj.getMonth3MaxRev()));
		cpBobj.setQuaterTotalMaxRevINR(IFactHelper.add2String(cpBobj.getQuaterTotalRevINR(),pwbExObj.getQuaterTotalRevINR()));
		cpBobj.setQuaterTotalMaxRev(IFactHelper.add2String(cpBobj.getQuaterTotalMaxRev(),pwbExObj.getQuaterTotalMaxRev()));	
		
		cpBobj.setMonth1ActRevINR(IFactHelper.add2String(cpBobj.getMonth1ActRevINR(),pwbExObj.getMonth1ActRevINR()));
		cpBobj.setMonth2ActRevINR(IFactHelper.add2String(cpBobj.getMonth2ActRevINR(),pwbExObj.getMonth2ActRevINR()));
		cpBobj.setMonth3ActRevINR(IFactHelper.add2String(cpBobj.getMonth3ActRevINR(),pwbExObj.getMonth3ActRevINR()));
		cpBobj.setMonth1ActRev(IFactHelper.add2String(cpBobj.getMonth1ActRev(),pwbExObj.getMonth1ActRev()));
		cpBobj.setMonth2ActRev(IFactHelper.add2String(cpBobj.getMonth2ActRev(),pwbExObj.getMonth2ActRev()));
		cpBobj.setMonth3ActRev(IFactHelper.add2String(cpBobj.getMonth3ActRev(),pwbExObj.getMonth3ActRev()));
		cpBobj.setQuaterTotalActRevINR(IFactHelper.add2String(cpBobj.getQuaterTotalActRevINR(),pwbExObj.getQuaterTotalActRevINR()));
		cpBobj.setQuaterTotalActRev(IFactHelper.add2String(cpBobj.getQuaterTotalActRev(),pwbExObj.getQuaterTotalActRev()));
		
		cpBobj.setMonth1DiffRevINR(IFactHelper.add2String(cpBobj.getMonth1DiffRevINR(),pwbExObj.getMonth1DiffRevINR()));
		cpBobj.setMonth2DiffRevINR(IFactHelper.add2String(cpBobj.getMonth2DiffRevINR(),pwbExObj.getMonth2DiffRevINR()));
		cpBobj.setMonth3DiffRevINR(IFactHelper.add2String(cpBobj.getMonth3DiffRevINR(),pwbExObj.getMonth3DiffRevINR()));
		cpBobj.setMonth1DiffRev(IFactHelper.add2String(cpBobj.getMonth1DiffRev(),pwbExObj.getMonth1DiffRev()));
		cpBobj.setMonth2DiffRev(IFactHelper.add2String(cpBobj.getMonth2DiffRev(),pwbExObj.getMonth2DiffRev()));
		cpBobj.setMonth3DiffRev(IFactHelper.add2String(cpBobj.getMonth3DiffRev(),pwbExObj.getMonth3DiffRev()));
		cpBobj.setQuaterTotalDiffRevINR(IFactHelper.add2String(cpBobj.getQuaterTotalDiffRevINR(),pwbExObj.getQuaterTotalDiffRevINR()));
		cpBobj.setQuaterTotalDiffRev(IFactHelper.add2String(cpBobj.getQuaterTotalDiffRev(),pwbExObj.getQuaterTotalDiffRev()));
		return cpBobj;	
		
	}

	private DPRevBObj mapDPObject(PWBBObj pwbExObj, DPRevBObj dpBobj) {
		
		dpBobj.setMonth1LeaveRevINR(IFactHelper.add2String(dpBobj.getMonth1LeaveRevINR(),pwbExObj.getMonth1LeaveRevINR()));
		dpBobj.setMonth1RDwReplRevINR(IFactHelper.add2String(dpBobj.getMonth1RDwReplRevINR(),pwbExObj.getMonth1RDwReplRevINR()));
		dpBobj.setMonth1RDwoReplRevINR(IFactHelper.add2String(dpBobj.getMonth1RDwoReplRevINR(),pwbExObj.getMonth1RDwoReplRevINR()));
		dpBobj.setMonth1ParkingRevINR(IFactHelper.add2String(dpBobj.getMonth1ParkingRevINR(),pwbExObj.getMonth1ParkingRevINR()));
		dpBobj.setMonth1MovmtRevINR(IFactHelper.add2String(dpBobj.getMonth1MovmtRevINR(),pwbExObj.getMonth1MovmtRevINR()));
		dpBobj.setMonth1OthersRevINR(IFactHelper.add2String(dpBobj.getMonth1OthersRevINR(),pwbExObj.getMonth1OthersRevINR()));
		
		dpBobj.setMonth1LeaveRev(IFactHelper.add2String(dpBobj.getMonth1LeaveRev(),pwbExObj.getMonth1LeaveRev()));
		dpBobj.setMonth1RDwReplRev(IFactHelper.add2String(dpBobj.getMonth1RDwReplRev(),pwbExObj.getMonth1RDwReplRev()));
		dpBobj.setMonth1RDwoReplRev(IFactHelper.add2String(dpBobj.getMonth1RDwoReplRev(),pwbExObj.getMonth1RDwoReplRev()));
		dpBobj.setMonth1ParkingRev(IFactHelper.add2String(dpBobj.getMonth1ParkingRev(),pwbExObj.getMonth1ParkingRev()));
		dpBobj.setMonth1MovmtRev(IFactHelper.add2String(dpBobj.getMonth1MovmtRev(),pwbExObj.getMonth1MovmtRev()));
		dpBobj.setMonth1OthersRev(IFactHelper.add2String(dpBobj.getMonth1OthersRev(),pwbExObj.getMonth1OthersRev()));
		
		dpBobj.setMonth2LeaveRevINR(IFactHelper.add2String(dpBobj.getMonth2LeaveRevINR(),pwbExObj.getMonth2LeaveRevINR()));
		dpBobj.setMonth2RDwReplRevINR(IFactHelper.add2String(dpBobj.getMonth2RDwReplRevINR(),pwbExObj.getMonth2RDwReplRevINR()));
		dpBobj.setMonth2RDwoReplRevINR(IFactHelper.add2String(dpBobj.getMonth2RDwoReplRevINR(),pwbExObj.getMonth2RDwoReplRevINR()));
		dpBobj.setMonth2ParkingRevINR(IFactHelper.add2String(dpBobj.getMonth2ParkingRevINR(),pwbExObj.getMonth2ParkingRevINR()));
		dpBobj.setMonth2MovmtRevINR(IFactHelper.add2String(dpBobj.getMonth2MovmtRevINR(),pwbExObj.getMonth2MovmtRevINR()));
		dpBobj.setMonth2OthersRevINR(IFactHelper.add2String(dpBobj.getMonth2OthersRevINR(),pwbExObj.getMonth2OthersRevINR()));
		
		dpBobj.setMonth2LeaveRev(IFactHelper.add2String(dpBobj.getMonth2LeaveRev(),pwbExObj.getMonth2LeaveRev()));
		dpBobj.setMonth2RDwReplRev(IFactHelper.add2String(dpBobj.getMonth2RDwReplRev(),pwbExObj.getMonth2RDwReplRev()));
		dpBobj.setMonth2RDwoReplRev(IFactHelper.add2String(dpBobj.getMonth2RDwoReplRev(),pwbExObj.getMonth2RDwoReplRev()));
		dpBobj.setMonth2ParkingRev(IFactHelper.add2String(dpBobj.getMonth2ParkingRev(),pwbExObj.getMonth2ParkingRev()));
		dpBobj.setMonth2MovmtRev(IFactHelper.add2String(dpBobj.getMonth2MovmtRev(),pwbExObj.getMonth2MovmtRev()));
		dpBobj.setMonth2OthersRev(IFactHelper.add2String(dpBobj.getMonth2OthersRev(),pwbExObj.getMonth2OthersRev()));
		
		dpBobj.setMonth3LeaveRevINR(IFactHelper.add2String(dpBobj.getMonth3LeaveRevINR(),pwbExObj.getMonth3LeaveRevINR()));
		dpBobj.setMonth3RDwReplRevINR(IFactHelper.add2String(dpBobj.getMonth3RDwReplRevINR(),pwbExObj.getMonth3RDwReplRevINR()));
		dpBobj.setMonth3RDwoReplRevINR(IFactHelper.add2String(dpBobj.getMonth3RDwoReplRevINR(),pwbExObj.getMonth3RDwoReplRevINR()));
		dpBobj.setMonth3ParkingRevINR(IFactHelper.add2String(dpBobj.getMonth3ParkingRevINR(),pwbExObj.getMonth3ParkingRevINR()));
		dpBobj.setMonth3MovmtRevINR(IFactHelper.add2String(dpBobj.getMonth3MovmtRevINR(),pwbExObj.getMonth3MovmtRevINR()));
		dpBobj.setMonth3OthersRevINR(IFactHelper.add2String(dpBobj.getMonth3OthersRevINR(),pwbExObj.getMonth3OthersRevINR()));
		
		dpBobj.setMonth3LeaveRev(IFactHelper.add2String(dpBobj.getMonth3LeaveRev(),pwbExObj.getMonth3LeaveRev()));
		dpBobj.setMonth3RDwReplRev(IFactHelper.add2String(dpBobj.getMonth3RDwReplRev(),pwbExObj.getMonth3RDwReplRev()));
		dpBobj.setMonth3RDwoReplRev(IFactHelper.add2String(dpBobj.getMonth3RDwoReplRev(),pwbExObj.getMonth3RDwoReplRev()));
		dpBobj.setMonth3ParkingRev(IFactHelper.add2String(dpBobj.getMonth3ParkingRev(),pwbExObj.getMonth3ParkingRev()));
		dpBobj.setMonth3MovmtRev(IFactHelper.add2String(dpBobj.getMonth3MovmtRev(),pwbExObj.getMonth3MovmtRev()));
		dpBobj.setMonth3OthersRev(IFactHelper.add2String(dpBobj.getMonth3OthersRev(),pwbExObj.getMonth3OthersRev()));
		
		dpBobj.setMonth1RevINR(IFactHelper.add2String(dpBobj.getMonth1RevINR(),pwbExObj.getMonth1RevINR()));
		dpBobj.setMonth2RevINR(IFactHelper.add2String(dpBobj.getMonth2RevINR(),pwbExObj.getMonth2RevINR()));
		dpBobj.setMonth3RevINR(IFactHelper.add2String(dpBobj.getMonth3RevINR(),pwbExObj.getMonth3RevINR()));
		dpBobj.setMonth1Rev(IFactHelper.add2String(dpBobj.getMonth1Rev(),pwbExObj.getMonth1Rev()));
		dpBobj.setMonth2Rev(IFactHelper.add2String(dpBobj.getMonth2Rev(),pwbExObj.getMonth2Rev()));
		dpBobj.setMonth3Rev(IFactHelper.add2String(dpBobj.getMonth3Rev(),pwbExObj.getMonth3Rev()));
		dpBobj.setQuaterTotalRevINR(IFactHelper.add2String(dpBobj.getQuaterTotalRevINR(),pwbExObj.getQuaterTotalRevINR()));
		dpBobj.setQuaterTotalRev(IFactHelper.add2String(dpBobj.getQuaterTotalRev(),pwbExObj.getQuaterTotalRev()));	
		
		dpBobj.setMonth1MaxRevINR(IFactHelper.add2String(dpBobj.getMonth1MaxRevINR(),pwbExObj.getMonth1MaxRevINR()));
		dpBobj.setMonth2MaxRevINR(IFactHelper.add2String(dpBobj.getMonth2MaxRevINR(),pwbExObj.getMonth2MaxRevINR()));
		dpBobj.setMonth3MaxRevINR(IFactHelper.add2String(dpBobj.getMonth3MaxRevINR(),pwbExObj.getMonth3MaxRevINR()));
		dpBobj.setMonth1MaxRev(IFactHelper.add2String(dpBobj.getMonth1MaxRev(),pwbExObj.getMonth1MaxRev()));
		dpBobj.setMonth2MaxRev(IFactHelper.add2String(dpBobj.getMonth2MaxRev(),pwbExObj.getMonth2MaxRev()));
		dpBobj.setMonth3MaxRev(IFactHelper.add2String(dpBobj.getMonth3MaxRev(),pwbExObj.getMonth3MaxRev()));
		dpBobj.setQuaterTotalMaxRevINR(IFactHelper.add2String(dpBobj.getQuaterTotalRevINR(),pwbExObj.getQuaterTotalRevINR()));
		dpBobj.setQuaterTotalMaxRev(IFactHelper.add2String(dpBobj.getQuaterTotalMaxRev(),pwbExObj.getQuaterTotalMaxRev()));	
		
		dpBobj.setMonth1ActRevINR(IFactHelper.add2String(dpBobj.getMonth1ActRevINR(),pwbExObj.getMonth1ActRevINR()));
		dpBobj.setMonth2ActRevINR(IFactHelper.add2String(dpBobj.getMonth2ActRevINR(),pwbExObj.getMonth2ActRevINR()));
		dpBobj.setMonth3ActRevINR(IFactHelper.add2String(dpBobj.getMonth3ActRevINR(),pwbExObj.getMonth3ActRevINR()));
		dpBobj.setMonth1ActRev(IFactHelper.add2String(dpBobj.getMonth1ActRev(),pwbExObj.getMonth1ActRev()));
		dpBobj.setMonth2ActRev(IFactHelper.add2String(dpBobj.getMonth2ActRev(),pwbExObj.getMonth2ActRev()));
		dpBobj.setMonth3ActRev(IFactHelper.add2String(dpBobj.getMonth3ActRev(),pwbExObj.getMonth3ActRev()));
		dpBobj.setQuaterTotalActRevINR(IFactHelper.add2String(dpBobj.getQuaterTotalActRevINR(),pwbExObj.getQuaterTotalActRevINR()));
		dpBobj.setQuaterTotalActRev(IFactHelper.add2String(dpBobj.getQuaterTotalActRev(),pwbExObj.getQuaterTotalActRev()));
		
		dpBobj.setMonth1DiffRevINR(IFactHelper.add2String(dpBobj.getMonth1DiffRevINR(),pwbExObj.getMonth1DiffRevINR()));
		dpBobj.setMonth2DiffRevINR(IFactHelper.add2String(dpBobj.getMonth2DiffRevINR(),pwbExObj.getMonth2DiffRevINR()));
		dpBobj.setMonth3DiffRevINR(IFactHelper.add2String(dpBobj.getMonth3DiffRevINR(),pwbExObj.getMonth3DiffRevINR()));
		dpBobj.setMonth1DiffRev(IFactHelper.add2String(dpBobj.getMonth1DiffRev(),pwbExObj.getMonth1DiffRev()));
		dpBobj.setMonth2DiffRev(IFactHelper.add2String(dpBobj.getMonth2DiffRev(),pwbExObj.getMonth2DiffRev()));
		dpBobj.setMonth3DiffRev(IFactHelper.add2String(dpBobj.getMonth3DiffRev(),pwbExObj.getMonth3DiffRev()));
		dpBobj.setQuaterTotalDiffRevINR(IFactHelper.add2String(dpBobj.getQuaterTotalDiffRevINR(),pwbExObj.getQuaterTotalDiffRevINR()));
		dpBobj.setQuaterTotalDiffRev(IFactHelper.add2String(dpBobj.getQuaterTotalDiffRev(),pwbExObj.getQuaterTotalDiffRev()));
		return dpBobj;	
		
	}

	private DMRevBObj mapDMObject(PWBBObj pwbExObj, DMRevBObj dmBobj) {
		
		dmBobj.addDMRevBObj(pwbExObj);
		
		dmBobj.setMonth1LeaveRevINR(IFactHelper.add2String(dmBobj.getMonth1LeaveRevINR(),pwbExObj.getMonth1LeaveRevINR()));
		dmBobj.setMonth1RDwReplRevINR(IFactHelper.add2String(dmBobj.getMonth1RDwReplRevINR(),pwbExObj.getMonth1RDwReplRevINR()));
		dmBobj.setMonth1RDwoReplRevINR(IFactHelper.add2String(dmBobj.getMonth1RDwoReplRevINR(),pwbExObj.getMonth1RDwoReplRevINR()));
		dmBobj.setMonth1ParkingRevINR(IFactHelper.add2String(dmBobj.getMonth1ParkingRevINR(),pwbExObj.getMonth1ParkingRevINR()));
		dmBobj.setMonth1MovmtRevINR(IFactHelper.add2String(dmBobj.getMonth1MovmtRevINR(),pwbExObj.getMonth1MovmtRevINR()));
		dmBobj.setMonth1OthersRevINR(IFactHelper.add2String(dmBobj.getMonth1OthersRevINR(),pwbExObj.getMonth1OthersRevINR()));
		
		dmBobj.setMonth1LeaveRev(IFactHelper.add2String(dmBobj.getMonth1LeaveRev(),pwbExObj.getMonth1LeaveRev()));
		dmBobj.setMonth1RDwReplRev(IFactHelper.add2String(dmBobj.getMonth1RDwReplRev(),pwbExObj.getMonth1RDwReplRev()));
		dmBobj.setMonth1RDwoReplRev(IFactHelper.add2String(dmBobj.getMonth1RDwoReplRev(),pwbExObj.getMonth1RDwoReplRev()));
		dmBobj.setMonth1ParkingRev(IFactHelper.add2String(dmBobj.getMonth1ParkingRev(),pwbExObj.getMonth1ParkingRev()));
		dmBobj.setMonth1MovmtRev(IFactHelper.add2String(dmBobj.getMonth1MovmtRev(),pwbExObj.getMonth1MovmtRev()));
		dmBobj.setMonth1OthersRev(IFactHelper.add2String(dmBobj.getMonth1OthersRev(),pwbExObj.getMonth1OthersRev()));
		
		dmBobj.setMonth2LeaveRevINR(IFactHelper.add2String(dmBobj.getMonth2LeaveRevINR(),pwbExObj.getMonth2LeaveRevINR()));
		dmBobj.setMonth2RDwReplRevINR(IFactHelper.add2String(dmBobj.getMonth2RDwReplRevINR(),pwbExObj.getMonth2RDwReplRevINR()));
		dmBobj.setMonth2RDwoReplRevINR(IFactHelper.add2String(dmBobj.getMonth2RDwoReplRevINR(),pwbExObj.getMonth2RDwoReplRevINR()));
		dmBobj.setMonth2ParkingRevINR(IFactHelper.add2String(dmBobj.getMonth2ParkingRevINR(),pwbExObj.getMonth2ParkingRevINR()));
		dmBobj.setMonth2MovmtRevINR(IFactHelper.add2String(dmBobj.getMonth2MovmtRevINR(),pwbExObj.getMonth2MovmtRevINR()));
		dmBobj.setMonth2OthersRevINR(IFactHelper.add2String(dmBobj.getMonth2OthersRevINR(),pwbExObj.getMonth2OthersRevINR()));
		
		dmBobj.setMonth2LeaveRev(IFactHelper.add2String(dmBobj.getMonth2LeaveRev(),pwbExObj.getMonth2LeaveRev()));
		dmBobj.setMonth2RDwReplRev(IFactHelper.add2String(dmBobj.getMonth2RDwReplRev(),pwbExObj.getMonth2RDwReplRev()));
		dmBobj.setMonth2RDwoReplRev(IFactHelper.add2String(dmBobj.getMonth2RDwoReplRev(),pwbExObj.getMonth2RDwoReplRev()));
		dmBobj.setMonth2ParkingRev(IFactHelper.add2String(dmBobj.getMonth2ParkingRev(),pwbExObj.getMonth2ParkingRev()));
		dmBobj.setMonth2MovmtRev(IFactHelper.add2String(dmBobj.getMonth2MovmtRev(),pwbExObj.getMonth2MovmtRev()));
		dmBobj.setMonth2OthersRev(IFactHelper.add2String(dmBobj.getMonth2OthersRev(),pwbExObj.getMonth2OthersRev()));
		
		dmBobj.setMonth3LeaveRevINR(IFactHelper.add2String(dmBobj.getMonth3LeaveRevINR(),pwbExObj.getMonth3LeaveRevINR()));
		dmBobj.setMonth3RDwReplRevINR(IFactHelper.add2String(dmBobj.getMonth3RDwReplRevINR(),pwbExObj.getMonth3RDwReplRevINR()));
		dmBobj.setMonth3RDwoReplRevINR(IFactHelper.add2String(dmBobj.getMonth3RDwoReplRevINR(),pwbExObj.getMonth3RDwoReplRevINR()));
		dmBobj.setMonth3ParkingRevINR(IFactHelper.add2String(dmBobj.getMonth3ParkingRevINR(),pwbExObj.getMonth3ParkingRevINR()));
		dmBobj.setMonth3MovmtRevINR(IFactHelper.add2String(dmBobj.getMonth3MovmtRevINR(),pwbExObj.getMonth3MovmtRevINR()));
		dmBobj.setMonth3OthersRevINR(IFactHelper.add2String(dmBobj.getMonth3OthersRevINR(),pwbExObj.getMonth3OthersRevINR()));
		
		dmBobj.setMonth3LeaveRev(IFactHelper.add2String(dmBobj.getMonth3LeaveRev(),pwbExObj.getMonth3LeaveRev()));
		dmBobj.setMonth3RDwReplRev(IFactHelper.add2String(dmBobj.getMonth3RDwReplRev(),pwbExObj.getMonth3RDwReplRev()));
		dmBobj.setMonth3RDwoReplRev(IFactHelper.add2String(dmBobj.getMonth3RDwoReplRev(),pwbExObj.getMonth3RDwoReplRev()));
		dmBobj.setMonth3ParkingRev(IFactHelper.add2String(dmBobj.getMonth3ParkingRev(),pwbExObj.getMonth3ParkingRev()));
		dmBobj.setMonth3MovmtRev(IFactHelper.add2String(dmBobj.getMonth3MovmtRev(),pwbExObj.getMonth3MovmtRev()));
		dmBobj.setMonth3OthersRev(IFactHelper.add2String(dmBobj.getMonth3OthersRev(),pwbExObj.getMonth3OthersRev()));
		
		dmBobj.setMonth1RevINR(IFactHelper.add2String(dmBobj.getMonth1RevINR(),pwbExObj.getMonth1RevINR()));
		dmBobj.setMonth2RevINR(IFactHelper.add2String(dmBobj.getMonth2RevINR(),pwbExObj.getMonth2RevINR()));
		dmBobj.setMonth3RevINR(IFactHelper.add2String(dmBobj.getMonth3RevINR(),pwbExObj.getMonth3RevINR()));
		dmBobj.setMonth1Rev(IFactHelper.add2String(dmBobj.getMonth1Rev(),pwbExObj.getMonth1Rev()));
		dmBobj.setMonth2Rev(IFactHelper.add2String(dmBobj.getMonth2Rev(),pwbExObj.getMonth2Rev()));
		dmBobj.setMonth3Rev(IFactHelper.add2String(dmBobj.getMonth3Rev(),pwbExObj.getMonth3Rev()));
		dmBobj.setQuaterTotalRevINR(IFactHelper.add2String(dmBobj.getQuaterTotalRevINR(),pwbExObj.getQuaterTotalRevINR()));
		dmBobj.setQuaterTotalRev(IFactHelper.add2String(dmBobj.getQuaterTotalRev(),pwbExObj.getQuaterTotalRev()));	
		
		dmBobj.setMonth1MaxRevINR(IFactHelper.add2String(dmBobj.getMonth1MaxRevINR(),pwbExObj.getMonth1MaxRevINR()));
		dmBobj.setMonth2MaxRevINR(IFactHelper.add2String(dmBobj.getMonth2MaxRevINR(),pwbExObj.getMonth2MaxRevINR()));
		dmBobj.setMonth3MaxRevINR(IFactHelper.add2String(dmBobj.getMonth3MaxRevINR(),pwbExObj.getMonth3MaxRevINR()));
		dmBobj.setMonth1MaxRev(IFactHelper.add2String(dmBobj.getMonth1MaxRev(),pwbExObj.getMonth1MaxRev()));
		dmBobj.setMonth2MaxRev(IFactHelper.add2String(dmBobj.getMonth2MaxRev(),pwbExObj.getMonth2MaxRev()));
		dmBobj.setMonth3MaxRev(IFactHelper.add2String(dmBobj.getMonth3MaxRev(),pwbExObj.getMonth3MaxRev()));
		dmBobj.setQuaterTotalMaxRevINR(IFactHelper.add2String(dmBobj.getQuaterTotalRevINR(),pwbExObj.getQuaterTotalRevINR()));
		dmBobj.setQuaterTotalMaxRev(IFactHelper.add2String(dmBobj.getQuaterTotalMaxRev(),pwbExObj.getQuaterTotalMaxRev()));	
		
		dmBobj.setMonth1ActRevINR(IFactHelper.add2String(dmBobj.getMonth1ActRevINR(),pwbExObj.getMonth1ActRevINR()));
		dmBobj.setMonth2ActRevINR(IFactHelper.add2String(dmBobj.getMonth2ActRevINR(),pwbExObj.getMonth2ActRevINR()));
		dmBobj.setMonth3ActRevINR(IFactHelper.add2String(dmBobj.getMonth3ActRevINR(),pwbExObj.getMonth3ActRevINR()));
		dmBobj.setMonth1ActRev(IFactHelper.add2String(dmBobj.getMonth1ActRev(),pwbExObj.getMonth1ActRev()));
		dmBobj.setMonth2ActRev(IFactHelper.add2String(dmBobj.getMonth2ActRev(),pwbExObj.getMonth2ActRev()));
		dmBobj.setMonth3ActRev(IFactHelper.add2String(dmBobj.getMonth3ActRev(),pwbExObj.getMonth3ActRev()));
		dmBobj.setQuaterTotalActRevINR(IFactHelper.add2String(dmBobj.getQuaterTotalActRevINR(),pwbExObj.getQuaterTotalActRevINR()));
		dmBobj.setQuaterTotalActRev(IFactHelper.add2String(dmBobj.getQuaterTotalActRev(),pwbExObj.getQuaterTotalActRev()));
		
		dmBobj.setMonth1DiffRevINR(IFactHelper.add2String(dmBobj.getMonth1DiffRevINR(),pwbExObj.getMonth1DiffRevINR()));
		dmBobj.setMonth2DiffRevINR(IFactHelper.add2String(dmBobj.getMonth2DiffRevINR(),pwbExObj.getMonth2DiffRevINR()));
		dmBobj.setMonth3DiffRevINR(IFactHelper.add2String(dmBobj.getMonth3DiffRevINR(),pwbExObj.getMonth3DiffRevINR()));
		dmBobj.setMonth1DiffRev(IFactHelper.add2String(dmBobj.getMonth1DiffRev(),pwbExObj.getMonth1DiffRev()));
		dmBobj.setMonth2DiffRev(IFactHelper.add2String(dmBobj.getMonth2DiffRev(),pwbExObj.getMonth2DiffRev()));
		dmBobj.setMonth3DiffRev(IFactHelper.add2String(dmBobj.getMonth3DiffRev(),pwbExObj.getMonth3DiffRev()));
		dmBobj.setQuaterTotalDiffRevINR(IFactHelper.add2String(dmBobj.getQuaterTotalDiffRevINR(),pwbExObj.getQuaterTotalDiffRevINR()));
		dmBobj.setQuaterTotalDiffRev(IFactHelper.add2String(dmBobj.getQuaterTotalDiffRev(),pwbExObj.getQuaterTotalDiffRev()));
		return dmBobj;	
		
	}
	
	public ResponseBObj getPWBFromUpdate(List<PWBUpdateBObj> pwbUpdate, String user) {
		ResponseBObj respObj = new ResponseBObj(); 
		try{
			
			if(null != pwbUpdate && pwbUpdate.size()>0) {
				ArrayList<PWBBObj> pWBBObjList = new ArrayList<PWBBObj>();
				HashMap<String,PWBBObj> pWBBObjMap = new HashMap<String,PWBBObj>();
				HashMap  ifactMap = new HashMap();
				String dollerRate = iFactDBHandler.getUtilValueByKey(IFactConstant.DollerRate);
				Iterator<PWBUpdateBObj> itr = pwbUpdate.iterator();
				while(itr.hasNext()) {
					PWBUpdateBObj upBObj = itr.next();
					if(null != upBObj) {
						PWBBObj pwbBObj = new PWBBObj();
						String currentBillRate = null;
						if(null != upBObj.getEmpID()) {
							pwbBObj.setEmpID(upBObj.getEmpID());
						}
						
						if(null != upBObj.getWorkerID()) {
							pwbBObj.setWorkerID(upBObj.getWorkerID());
						}
						
						if(null != upBObj.getDM()) {
							pwbBObj.setDM(upBObj.getDM());
						}
						
						if(null != upBObj.getDP()) {
							pwbBObj.setDP(upBObj.getDP());
						}
						
						if(null != upBObj.getCP()) {
							pwbBObj.setCP(upBObj.getCP());
						}
						
						if(null != upBObj.getCurrentBillRate()) {
							pwbBObj.setCurrentBillRate(upBObj.getCurrentBillRate());
							currentBillRate = upBObj.getCurrentBillRate();
						}
						
						if(null != upBObj.getMonth1Act()) {
							pwbBObj.setMonth1Act(upBObj.getMonth1Act());
						}
						
						if(null != upBObj.getMonth2Act()) {
							pwbBObj.setMonth2Act(upBObj.getMonth2Act());
						}
						
						if(null != upBObj.getMonth3Act()) {
							pwbBObj.setMonth3Act(upBObj.getMonth3Act());
						}
						
						if(null != upBObj.getMonth1Leave()) {
							pwbBObj.setMonth1Leave(upBObj.getMonth1Leave());
							pwbBObj.setMonth1LeaveRevINR(IFactHelper.calculateHrsRev(currentBillRate, upBObj.getMonth1Leave(), dollerRate));
						}
						
						if(null != upBObj.getMonth1RDwRepl()) {
							pwbBObj.setMonth1RDwRepl(upBObj.getMonth1RDwRepl());
							pwbBObj.setMonth1RDwReplRevINR(IFactHelper.calculateHrsRev(currentBillRate, upBObj.getMonth1RDwRepl(), dollerRate));
						}
						
						if(null != upBObj.getMonth1RDwoRepl()) {
							pwbBObj.setMonth1RDwoRepl(upBObj.getMonth1RDwoRepl());
							pwbBObj.setMonth1RDwoReplRevINR(IFactHelper.calculateHrsRev(currentBillRate, upBObj.getMonth1RDwoRepl(), dollerRate));
						}
						
						if(null != upBObj.getMonth1Parking()) {
							pwbBObj.setMonth1Parking(upBObj.getMonth1Parking());
							pwbBObj.setMonth1ParkingRevINR(IFactHelper.calculateHrsRev(currentBillRate, upBObj.getMonth1Parking(), dollerRate));
						}
						
						if(null != upBObj.getMonth1Movmt()) {
							pwbBObj.setMonth1Movmt(upBObj.getMonth1Movmt());
							pwbBObj.setMonth1MovmtRevINR(IFactHelper.calculateHrsRev(currentBillRate, upBObj.getMonth1Movmt(), dollerRate));
						}
						
						if(null != upBObj.getMonth1Others()) {
							pwbBObj.setMonth1Others(upBObj.getMonth1Others());
							pwbBObj.setMonth1OthersRevINR(IFactHelper.calculateHrsRev(currentBillRate, upBObj.getMonth1Others(), dollerRate));
						}
						
						if(null != upBObj.getMonth2Leave()) {
							pwbBObj.setMonth2Leave(upBObj.getMonth2Leave());
							pwbBObj.setMonth2LeaveRevINR(IFactHelper.calculateHrsRev(currentBillRate, upBObj.getMonth2Leave(), dollerRate));
						}
						
						if(null != upBObj.getMonth2RDwRepl()) {
							pwbBObj.setMonth2RDwRepl(upBObj.getMonth2RDwRepl());
							pwbBObj.setMonth2RDwReplRevINR(IFactHelper.calculateHrsRev(currentBillRate, upBObj.getMonth2RDwRepl(), dollerRate));
						}
						
						if(null != upBObj.getMonth2RDwoRepl()) {
							pwbBObj.setMonth2RDwoRepl(upBObj.getMonth2RDwoRepl());
							pwbBObj.setMonth2RDwoReplRevINR(IFactHelper.calculateHrsRev(currentBillRate, upBObj.getMonth2RDwoRepl(), dollerRate));
						}
						
						if(null != upBObj.getMonth2Parking()) {
							pwbBObj.setMonth2Parking(upBObj.getMonth2Parking());
							pwbBObj.setMonth2ParkingRevINR(IFactHelper.calculateHrsRev(currentBillRate, upBObj.getMonth2Parking(), dollerRate));
						}
						
						if(null != upBObj.getMonth2Movmt()) {
							pwbBObj.setMonth2Movmt(upBObj.getMonth2Movmt());
							pwbBObj.setMonth2MovmtRevINR(IFactHelper.calculateHrsRev(currentBillRate, upBObj.getMonth2Movmt(), dollerRate));
						}
						
						if(null != upBObj.getMonth2Others()) {
							pwbBObj.setMonth2Others(upBObj.getMonth2Others());
							pwbBObj.setMonth2OthersRevINR(IFactHelper.calculateHrsRev(currentBillRate, upBObj.getMonth2Others(), dollerRate));
						}
						
						if(null != upBObj.getMonth3Leave()) {
							pwbBObj.setMonth3Leave(upBObj.getMonth3Leave());
							pwbBObj.setMonth2LeaveRevINR(IFactHelper.calculateHrsRev(currentBillRate, upBObj.getMonth2Leave(), dollerRate));
						}
						
						if(null != upBObj.getMonth3RDwRepl()) {
							pwbBObj.setMonth3RDwRepl(upBObj.getMonth3RDwRepl());
							pwbBObj.setMonth3RDwReplRevINR(IFactHelper.calculateHrsRev(currentBillRate, upBObj.getMonth3RDwRepl(), dollerRate));
						}
						
						if(null != upBObj.getMonth3RDwoRepl()) {
							pwbBObj.setMonth3RDwoRepl(upBObj.getMonth3RDwoRepl());
							pwbBObj.setMonth3RDwoReplRevINR(IFactHelper.calculateHrsRev(currentBillRate, upBObj.getMonth3RDwoRepl(), dollerRate));
						}
						
						if(null != upBObj.getMonth3Parking()) {
							pwbBObj.setMonth3Parking(upBObj.getMonth3Parking());
							pwbBObj.setMonth3ParkingRevINR(IFactHelper.calculateHrsRev(currentBillRate, upBObj.getMonth3Parking(), dollerRate));
						}
						
						if(null != upBObj.getMonth3Movmt()) {
							pwbBObj.setMonth3Movmt(upBObj.getMonth3Movmt());
							pwbBObj.setMonth3MovmtRevINR(IFactHelper.calculateHrsRev(currentBillRate, upBObj.getMonth3Movmt(), dollerRate));
						}
						
						if(null != upBObj.getMonth3Others()) {
							pwbBObj.setMonth3Others(upBObj.getMonth3Others());
							pwbBObj.setMonth3OthersRevINR(IFactHelper.calculateHrsRev(currentBillRate, upBObj.getMonth3Others(), dollerRate));
						}
						
						pWBBObjList.add(pwbBObj);
						pWBBObjMap.put(pwbBObj.getWorkerID()+pwbBObj.getEmpID(), pwbBObj);
					}
					
				}
				ifactMap.put(IFactConstant.PWBLIST, pWBBObjList);
				ifactMap.put(IFactConstant.PWBMAP, pWBBObjMap);
				respObj.setResponseObject(ifactMap);
				respObj.setError(false);
				respObj.setMessage("PWB Object Created from Update Keying");
			}

		}catch(Exception e) {
			logger.error(e);
			System.out.println("Error in getPWBFromUpdate::"+e.getMessage());
			respObj.setError(true);
			respObj.setMessage(e.getMessage());

		}

		return respObj;
	}

	public ResponseBObj constructPWBPivotObject(ResponseBObj res, Object object) {
		ResponseBObj response = new ResponseBObj();
		
		
		try {
			ArrayList<Pivot> pivotList = new ArrayList<Pivot>();
			if(null != res && !res.isError() && null != res.getResponseObject() && res.getResponseObject() instanceof PWBRevBObj) {
				PWBRevBObj pwbRevBObj = (PWBRevBObj) res.getResponseObject();
				if(null != pwbRevBObj && null != pwbRevBObj.getCpRevBObjList() && pwbRevBObj.getCpRevBObjList().size()>0) {
					ArrayList<CPRevBObj> cpList = pwbRevBObj.getCpRevBObjList();
					Iterator<CPRevBObj> cpItr = cpList.iterator();
					while(cpItr.hasNext()) {
						CPRevBObj cp = cpItr.next();
						if(null !=cp ) {
							if(null != cp.getDPRevBObjList() && cp.getDPRevBObjList().size()>0) {
								ArrayList<DPRevBObj> dpList = cp.getDPRevBObjList();
								Iterator<DPRevBObj> dpItr = dpList.iterator();
								while(dpItr.hasNext()) {
									DPRevBObj dp = dpItr.next();
									if(null != dp) {
										if(null != dp.getDMRevBObjList() && dp.getDMRevBObjList().size()>0) {
											ArrayList<DMRevBObj> dmList = dp.getDMRevBObjList();
											Iterator<DMRevBObj> dmItr = dmList.iterator();
											while(dmItr.hasNext()) {
												DMRevBObj dm = dmItr.next();
												if(null != dm) {
													Pivot pivot = new Pivot();
													
													pivot.setCp(cp.getCPName());
													pivot.setDp(dp.getDPName());
													pivot.setDm(dm.getDMName());

													pivot.setMonth1ActRev(dm.getMonth1ActRev());
													pivot.setMonth2ActRev(dm.getMonth2ActRev());
													pivot.setMonth3ActRev(dm.getMonth3ActRev());
													pivot.setQuaterTotalActRev(dm.getQuaterTotalActRev());

													pivot.setMonth1ActRevINR(dm.getMonth1ActRevINR());
													pivot.setMonth2ActRevINR(dm.getMonth2ActRevINR());
													pivot.setMonth3ActRevINR(dm.getMonth3ActRevINR());
													pivot.setQuaterTotalActRevINR(dm.getQuaterTotalActRevINR());

													pivot.setMonth1DiffRev(dm.getMonth1DiffRev());
													pivot.setMonth2DiffRev(dm.getMonth2DiffRev());
													pivot.setMonth3DiffRev(dm.getMonth3DiffRev());
													pivot.setQuaterTotalDiffRev(dm.getQuaterTotalDiffRev());

													pivot.setMonth1DiffRevINR(dm.getMonth1DiffRevINR());
													pivot.setMonth2DiffRevINR(dm.getMonth2DiffRevINR());
													pivot.setMonth3DiffRevINR(dm.getMonth3DiffRevINR());
													pivot.setQuaterTotalDiffRev(dm.getQuaterTotalDiffRev());

													pivot.setMonth1LeaveRev(dm.getMonth1LeaveRev());
													pivot.setMonth1RDwReplRev(dm.getMonth1RDwReplRev());
													pivot.setMonth1RDwoReplRev(dm.getMonth1RDwoReplRev());
													pivot.setMonth1ParkingRev(dm.getMonth1ParkingRev());
													pivot.setMonth1MovmtRev(dm.getMonth1MovmtRev());
													pivot.setMonth1OthersRev(dm.getMonth1OthersRev());

													pivot.setMonth1LeaveRevINR(dm.getMonth1LeaveRevINR());
													pivot.setMonth1RDwReplRevINR(dm.getMonth1RDwReplRevINR());
													pivot.setMonth1RDwoReplRevINR(dm.getMonth1RDwoReplRevINR());
													pivot.setMonth1ParkingRevINR(dm.getMonth1ParkingRevINR());
													pivot.setMonth1MovmtRevINR(dm.getMonth1MovmtRevINR());
													pivot.setMonth1OthersRevINR(dm.getMonth1OthersRevINR());

													pivot.setMonth2LeaveRev(dm.getMonth2LeaveRev());
													pivot.setMonth2RDwReplRev(dm.getMonth2RDwReplRev());
													pivot.setMonth2RDwoReplRev(dm.getMonth2RDwoReplRev());
													pivot.setMonth2ParkingRev(dm.getMonth2ParkingRev());
													pivot.setMonth2MovmtRev(dm.getMonth2MovmtRev());
													pivot.setMonth2OthersRev(dm.getMonth2OthersRev());

													pivot.setMonth2LeaveRevINR(dm.getMonth2LeaveRevINR());
													pivot.setMonth2RDwReplRevINR(dm.getMonth2RDwReplRevINR());
													pivot.setMonth2RDwoReplRevINR(dm.getMonth2RDwoReplRevINR());
													pivot.setMonth2ParkingRevINR(dm.getMonth2ParkingRevINR());
													pivot.setMonth2MovmtRevINR(dm.getMonth2MovmtRevINR());
													pivot.setMonth2OthersRevINR(dm.getMonth2OthersRevINR());

													pivot.setMonth3LeaveRev(dm.getMonth3LeaveRev());
													pivot.setMonth3RDwReplRev(dm.getMonth3RDwReplRev());
													pivot.setMonth3RDwoReplRev(dm.getMonth3RDwoReplRev());
													pivot.setMonth3ParkingRev(dm.getMonth3ParkingRev());
													pivot.setMonth3MovmtRev(dm.getMonth3MovmtRev());
													pivot.setMonth3OthersRev(dm.getMonth3OthersRev());

													pivot.setMonth3LeaveRevINR(dm.getMonth3LeaveRevINR());
													pivot.setMonth3RDwReplRevINR(dm.getMonth3RDwReplRevINR());
													pivot.setMonth3RDwoReplRevINR(dm.getMonth3RDwoReplRevINR());
													pivot.setMonth3ParkingRevINR(dm.getMonth3ParkingRevINR());
													pivot.setMonth3MovmtRevINR(dm.getMonth3MovmtRevINR());
													pivot.setMonth3OthersRevINR(dm.getMonth3OthersRevINR());
													pivotList.add(pivot);
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
			response.setError(false);
			response.setResponseObject(pivotList);
			response.setMessage("constructPWBPivotObject Success");
		}catch (Exception ex) {
			response.setError(true);
			response.setMessage("constructPWBPivotObject Error");
		}
		return response;
	}

	public LoginBObj mapLoginObj(LoginBObj login, UserInfo userInfo) {
		
		if(null != userInfo) {
			login.setDateofjoining(userInfo.getDateofjoining());
			login.setEmail(userInfo.getEmail());
			login.setEnabled(userInfo.getEnabled());
			login.setFullName(userInfo.getFullName());
			login.setNickname(userInfo.getNickname());
			login.setProjectrole(userInfo.getProjectrole());
			login.setReportto(userInfo.getReportto());
			login.setRole(userInfo.getRole());
		}
		
		return login;	
		
	}

	public Object constructUtilObject(UtilBObj utilBObj) {
		Util util = new Util();
		ResponseBObj responseBObj = new ResponseBObj();
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


		try {
			if(null != utilBObj) {
				util.setUtilId(utilBObj.getUtilId());
				util.setUtilgroup(utilBObj.getUtilgroup());
				util.setUtilname(utilBObj.getUtilname());
				util.setUtilvalue(utilBObj.getUtilvalue());
			}
		}catch(Exception e) {
			logger.error(e);
			responseBObj.setError(true);
			responseBObj.setMessage(e.getMessage());
			return responseBObj;
		}
		return util;
	}
}
