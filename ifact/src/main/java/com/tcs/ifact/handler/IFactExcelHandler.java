package com.tcs.ifact.handler;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

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
import org.springframework.stereotype.Component;

import com.tcs.ifact.bobj.ETSSBObj;
import com.tcs.ifact.bobj.FGBObj;
import com.tcs.ifact.bobj.PWBBObj;
import com.tcs.ifact.bobj.ResponseBObj;
import com.tcs.ifact.bobj.UxBObj;
import com.tcs.ifact.helper.IFactConstant;
import com.tcs.ifact.helper.IFactHelper;
import com.tcs.ifact.model.DBFile;
import com.tcs.ifact.model.Leakage;
import com.tcs.ifact.model.Pwb;

@Component
public class IFactExcelHandler {
	
	private static final Logger logger = LogManager.getLogger(IFactExcelHandler.class);

	@Autowired
	IFactDBHandler iFactDBHandler;

	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	public ResponseBObj getDataFromRawDataExcel(String user) {

		ResponseBObj respObj = new ResponseBObj(); 

		try {
			//FileInputStream rawdataFile = new FileInputStream(new File("C:\\Tools\\IFACT\\iFactRawData.xlsx"));
			String cuMonth1 = iFactDBHandler.getUtilValueByKey(IFactConstant.Month1);
			String cuMonth2 = iFactDBHandler.getUtilValueByKey(IFactConstant.Month2);
			String cuMonth3 = iFactDBHandler.getUtilValueByKey(IFactConstant.Month3);
			String cuYear = iFactDBHandler.getUtilValueByKey(IFactConstant.YEAR);
			String gift = iFactDBHandler.getUtilValueByKey(IFactConstant.GIFT);
			String i2i = iFactDBHandler.getUtilValueByKey(IFactConstant.I2I);
			String clp = iFactDBHandler.getUtilValueByKey(IFactConstant.CLP);
			String msp= iFactDBHandler.getUtilValueByKey(IFactConstant.MSP);
			
			ArrayList giftList = IFactHelper.stringToArray(gift);
			ArrayList i2iList = IFactHelper.stringToArray(i2i);
			ArrayList clpList = IFactHelper.stringToArray(clp);
			ArrayList mspList = IFactHelper.stringToArray(msp);
			
					
			if(null != user) {
				respObj = iFactDBHandler.getDBFileByUser(user);
				if(null != respObj && null != respObj.getResponseObject() && respObj.getResponseObject() instanceof DBFile) {
					DBFile dbfile = (DBFile) respObj.getResponseObject(); 
					if(null != dbfile & null != dbfile.getData()) {
						byte[] bdata=dbfile.getData();
						File excelFile = new File(user+"iFactRawData.xlsx");
						FileOutputStream fos = new FileOutputStream(excelFile);
						fos.write(bdata);
						fos.flush();
						fos.close();
						FileInputStream rawdataFile = new FileInputStream(excelFile);	
						if(null != rawdataFile) {

							Workbook workbook = WorkbookFactory.create(rawdataFile);

							ArrayList<UxBObj> uxBObjList = new ArrayList<UxBObj>();
							HashMap<String,UxBObj> uxMap = new HashMap<String,UxBObj>();

							ArrayList<FGBObj> fgBObjList = new ArrayList<FGBObj>();
							HashMap<String,FGBObj> fgMap = new HashMap<String,FGBObj>();

							ArrayList<ETSSBObj> etBObjList = new ArrayList<ETSSBObj>();
							HashMap<String,ETSSBObj> etMap = new HashMap<String,ETSSBObj>();

							ArrayList<Pwb> pwbList = new ArrayList<Pwb>();
							HashMap<String,Pwb> pwbMap = new HashMap<String,Pwb>();

							HashMap ifactMap = new HashMap<>();

							DataFormatter dataFormatter = new DataFormatter();
							//FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();

							

							Iterator<Sheet> sheetIterator = workbook.sheetIterator();
							while(sheetIterator.hasNext()) {
								Sheet sheet = sheetIterator.next();
								if("Ultimatix".equalsIgnoreCase(sheet.getSheetName())) {
									Iterator<Row> rowIterator = sheet.rowIterator();
									while(rowIterator.hasNext()) {
										Row row = rowIterator.next();
										if(row.getRowNum() ==0) {
											continue;
										}else if(row.getRowNum() >=0) {
											UxBObj ux = new UxBObj();
											Iterator<Cell> cellIterator = row.cellIterator();
											while(cellIterator.hasNext()) {
												Cell cell = cellIterator.next();
												if(null != cell) {
													String cellValue = null;
													if(0==cell.getColumnIndex()) {
														cellValue = dataFormatter.formatCellValue(cell);
														ux.setEmpID(cellValue);
													}else if(1==cell.getColumnIndex()) {
														cellValue = dataFormatter.formatCellValue(cell);
														ux.setEmpName(cellValue);
													}else if(2==cell.getColumnIndex()) {
														cellValue = dataFormatter.formatCellValue(cell);
														ux.setGrade(cellValue);
													}else if(3==cell.getColumnIndex()) {
														cellValue = dataFormatter.formatCellValue(cell);
														ux.setDeputeBranch(cellValue);
													}else if(4==cell.getColumnIndex()) {
														cellValue = dataFormatter.formatCellValue(cell);
														ux.setWorkCountry(cellValue);
													}else if(5==cell.getColumnIndex()) {
														cellValue = dataFormatter.formatCellValue(cell);
														ux.setWorkLocation(cellValue);
													}else if(6==cell.getColumnIndex()) {
														cellValue = dataFormatter.formatCellValue(cell);
														ux.setProjectNumber(cellValue);
													}else if(7==cell.getColumnIndex()) {
														cellValue = dataFormatter.formatCellValue(cell);
														ux.setProjectName(cellValue);
													}else if(8==cell.getColumnIndex()) {
														cellValue = dataFormatter.formatCellValue(cell);
														ux.setProjectType(cellValue);
													}else if(9==cell.getColumnIndex()) {
														cellValue = dataFormatter.formatCellValue(cell);
														ux.setAllocationStartDate(cellValue);
													}else if(10==cell.getColumnIndex()) {
														cellValue = dataFormatter.formatCellValue(cell);
														ux.setAllocationEndDate(cellValue);
													}else if(11==cell.getColumnIndex()) {
														cellValue = dataFormatter.formatCellValue(cell);
														ux.setDP(cellValue);
													}else if(12==cell.getColumnIndex()) {
														cellValue = dataFormatter.formatCellValue(cell);
														ux.setCP(cellValue);
													}
												}
											}
											uxBObjList.add(ux);
											uxMap.put(ux.getEmpID(), ux);
										}
									}
								}else if("FieldGlass".equalsIgnoreCase(sheet.getSheetName())){

									Iterator<Row> rowIterator = sheet.rowIterator();
									while(rowIterator.hasNext()) {
										Row row = rowIterator.next();
										if(row.getRowNum() ==0) {
											continue;
										}else if(row.getRowNum() >=1) {
											FGBObj fg = new FGBObj();
											Iterator<Cell> cellIterator = row.cellIterator();
											while(cellIterator.hasNext()) {
												Cell cell = cellIterator.next();
												if(null != cell) {
													String cellValue = null;
													if(0==cell.getColumnIndex()) {
														cellValue = dataFormatter.formatCellValue(cell);
														fg.setWorkerID(cellValue);
													}else if(1==cell.getColumnIndex()) {
														cellValue = dataFormatter.formatCellValue(cell);
														fg.setLoginID(cellValue);
													}else if(2==cell.getColumnIndex()) {
														cellValue = dataFormatter.formatCellValue(cell);
														fg.setGDCEID(cellValue);
													}else if(3==cell.getColumnIndex()) {
														cellValue = dataFormatter.formatCellValue(cell);
														fg.setFirstName(cellValue);
													}else if(4==cell.getColumnIndex()) {
														cellValue = dataFormatter.formatCellValue(cell);
														fg.setLastName(cellValue);
													}else if(5==cell.getColumnIndex()) {
														cellValue = dataFormatter.formatCellValue(cell);
														fg.setWorkerStatus(cellValue);
													}else if(6==cell.getColumnIndex()) {
														cellValue = dataFormatter.formatCellValue(cell);
														fg.setCurrentBillRate(cellValue);
													}else if(7==cell.getColumnIndex()) {
														cellValue = dataFormatter.formatCellValue(cell);
														fg.setSOWWorkerRole(cellValue);
													}else if(8==cell.getColumnIndex()) {
														cellValue = dataFormatter.formatCellValue(cell);
														fg.setWorkerStartDate(cellValue);
													}else if(9==cell.getColumnIndex()) {
														cellValue = dataFormatter.formatCellValue(cell);
														fg.setWorkerEndDate(cellValue);
													}
												}
											}
											fgBObjList.add(fg);
											fgMap.put(fg.getWorkerID(), fg);
										}
									}
								}else if("ETSS".equalsIgnoreCase(sheet.getSheetName())){

									Iterator<Row> rowIterator = sheet.rowIterator();
									while(rowIterator.hasNext()) {
										Row row = rowIterator.next();
										if(row.getRowNum() ==0) {
											continue;
										}else if(row.getRowNum() >=1) {
											ETSSBObj et = new ETSSBObj();
											Iterator<Cell> cellIterator = row.cellIterator();
											while(cellIterator.hasNext()) {
												Cell cell = cellIterator.next();
												if(null != cell) {
													String cellValue = null;
													if(0==cell.getColumnIndex()) {
														cellValue = dataFormatter.formatCellValue(cell);
														et.setEmpID(cellValue);
													}else if(1==cell.getColumnIndex()) {
														cellValue = dataFormatter.formatCellValue(cell);
														et.setWorkerID(cellValue);
													}else if(2==cell.getColumnIndex()) {
														cellValue = dataFormatter.formatCellValue(cell);
														et.setGDCEID(cellValue);
													}else if(3==cell.getColumnIndex()) {
														cellValue = dataFormatter.formatCellValue(cell);
														et.setFirstName(cellValue);
													}else if(4==cell.getColumnIndex()) {
														cellValue = dataFormatter.formatCellValue(cell);
														et.setLastName(cellValue);
													}else if(5==cell.getColumnIndex()) {
														cellValue = dataFormatter.formatCellValue(cell);
														et.setWorkLocation(cellValue);
													}else if(6==cell.getColumnIndex()) {
														cellValue = dataFormatter.formatCellValue(cell);
														et.setDM(cellValue);
													}else if(7==cell.getColumnIndex()) {
														cellValue = dataFormatter.formatCellValue(cell);
														et.setDP(cellValue);
													}else if(8==cell.getColumnIndex()) {
														cellValue = dataFormatter.formatCellValue(cell);
														et.setRM(cellValue);
													}
												}
											}
											etBObjList.add(et);
											etMap.put(et.getWorkerID(), et);
										}
									}
								}
							}

							if(null != fgBObjList) {
								Iterator<FGBObj> itr = fgBObjList.iterator();
								while(itr.hasNext()) {
									FGBObj fBObj = itr.next();
									Pwb pwb = new Pwb();
									pwb.setWorkerID(fBObj.getWorkerID());
									pwb.setFirstName(fBObj.getFirstName());
									pwb.setLastName(fBObj.getLastName());
									pwb.setGdceid(fBObj.getGDCEID());
									pwb.setSOWWorkerRole(fBObj.getSOWWorkerRole());
									pwb.setCurrentBillRate(fBObj.getCurrentBillRate());
									Date date1 = sdf.parse(fBObj.getWorkerStartDate());
									pwb.setWorkerStartDate(date1);
									Date date2 = sdf.parse(fBObj.getWorkerEndDate());
									pwb.setWorkerEndDate(date2);
									if(null != etMap && etMap.containsKey(pwb.getWorkerID())&& null != etMap.get(pwb.getWorkerID())) {
										ETSSBObj eBObj = etMap.get(pwb.getWorkerID());
										pwb.setEmpID(eBObj.getEmpID());
										pwb.setDm(eBObj.getDM());
										pwb.setDp(eBObj.getDP());
									}else {
										if(IFactHelper.isNumeric(fBObj.getLoginID())) {
											pwb.setEmpID(fBObj.getLoginID());
										}
									}
									if(null != uxMap && uxMap.containsKey(pwb.getEmpID())&&null!=uxMap.get(pwb.getEmpID())) {
										UxBObj uBObj = uxMap.get(pwb.getEmpID());
										pwb.setProjectType(uBObj.getProjectType());
										pwb.setWONName(uBObj.getProjectName());
										pwb.setWONNumber(uBObj.getProjectNumber());
										pwb.setWONType(uBObj.getProjectType());
										pwb.setWorkCountry(uBObj.getWorkCountry());
										pwb.setWorkLocation(uBObj.getWorkLocation());
										pwb.setCp(uBObj.getCP());
										pwb.setDp(uBObj.getDP());
										if(null == pwb.getDm()) {
											pwb.setDm(uBObj.getDP());
										}

									}

									pwb.setMonth1Max(constructMonthMax(pwb.getWorkCountry(),pwb.getWorkLocation(),cuYear,cuMonth1));
									pwb.setMonth2Max(constructMonthMax(pwb.getWorkCountry(),pwb.getWorkLocation(),cuYear,cuMonth2));
									pwb.setMonth3Max(constructMonthMax(pwb.getWorkCountry(),pwb.getWorkLocation(),cuYear,cuMonth3));

									Date now = new Date();
									pwb.setLastUpdatedDate(now);

									if(null != pwb.getWorkerID() && null != pwb.getEmpID() && IFactHelper.isNumeric(pwb.getEmpID())) {
										pwbList.add(pwb);
										pwbMap.put(pwb.getWorkerID()+pwb.getEmpID(), pwb);
									}
								}
							}

							ifactMap.put(IFactConstant.PWBLIST, pwbList);
							ifactMap.put(IFactConstant.PWBMAP, pwbMap);
							logger.debug("pwb Object created"+pwbList.size()+pwbMap.size());
							respObj.setResponseObject(ifactMap);
							respObj.setError(false);
							respObj.setMessage("pwb Data has been constructed::"+pwbList.size());

						}
					}
				}
			}


		}catch(Exception ex) {
			logger.error(ex);
			respObj.setError(true);
			respObj.setMessage("Error in getDataFromRawDataExcel::"+ex.getMessage());
		}


		return respObj;		
	}
	
	
	public  String constructMonthMax(String workCountry,String workLocation, String year, String month) {
		int yearNum = Integer.parseInt(year);
		int mothNum = Integer.parseInt(month);
		YearMonth ymObj= YearMonth.of(yearNum, mothNum);
		int daysInMonth = ymObj.lengthOfMonth();
		int weekends = 0;
		for(int day=1;day<=daysInMonth;day++) {
			if(ymObj.atDay(day).getDayOfWeek().equals(DayOfWeek.SATURDAY)||
					ymObj.atDay(day).getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
				weekends++;
			}
		}
		
		
		if(null != workCountry && !workCountry.isEmpty()) {
			if(workCountry.equalsIgnoreCase(IFactConstant.UnitedStates)) {
				workLocation = IFactConstant.USA;
			}else if(workCountry.equalsIgnoreCase(IFactConstant.UnitedKingdom)) {
				workLocation = IFactConstant.UK;
			}else if(workCountry.equalsIgnoreCase(IFactConstant.Singapore)) {
				workLocation = IFactConstant.Singapore;
			}
		}
		int leave = iFactDBHandler.getLeaveDays(workLocation,year,month);
		
		int maxHrs = (daysInMonth-weekends-leave)*8;
		return Integer.toString(maxHrs);
	}
	

/*	public ResponseBObj uploadExcel(MultipartFile file, String fileName) {
		ResponseBObj respObj = new ResponseBObj(); 
		if(!file.isEmpty()) {
			try {
				byte[] bytes = file.getBytes();
				File dir = new File("C:\\Tools\\IFACT");
				File serverFile = new File(dir.getAbsolutePath()+File.separator+file.getOriginalFilename());
				BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
				stream.write(bytes);
				stream.close();

				System.out.println("File Name:"+ file.getOriginalFilename());
				System.out.println("Server File Path:"+ serverFile.getAbsolutePath());
				respObj.setError(false);
				respObj.setMessage(file.getOriginalFilename() + "File Uploaded Successfully");

			}catch(Exception e) {
				e.printStackTrace();
				System.out.println("Failed to upload Excel"+ file.getOriginalFilename()+"=>"+e.getMessage());
				respObj.setError(true);
				respObj.setMessage(e.getMessage());
			}
		}
		return respObj;

	}*/

	public ResponseBObj getPWBFromExcel(String user) {
		ResponseBObj respObj = new ResponseBObj(); 
		try{

			if(null != user) {
				respObj = iFactDBHandler.getDBFileByUser(user);
				if(null != respObj && null != respObj.getResponseObject() && respObj.getResponseObject() instanceof DBFile) {
					DBFile dbfile = (DBFile) respObj.getResponseObject(); 
					if(null != dbfile & null != dbfile.getData()) {
						byte[] bdata=dbfile.getData();
						File excelFile = new File(user+"PWB.xlsx");
						FileOutputStream fos = new FileOutputStream(excelFile);
						fos.write(bdata);
						fos.flush();
						fos.close();
						FileInputStream rawdataFile = new FileInputStream(excelFile);	
						if(null != rawdataFile) {
							Workbook workbook = WorkbookFactory.create(rawdataFile);

							/*POIFSFileSystem fs = new POIFSFileSystem(file);
						HSSFWorkbook hssfWorkbook = new HSSFWorkbook(fs);*/

							DataFormatter dataFormatter = new DataFormatter();
							FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();

							String dollerRate = iFactDBHandler.getUtilValueByKey(IFactConstant.DollerRate);
							

							ArrayList<PWBBObj> pWBBObjList = new ArrayList<PWBBObj>();
							HashMap<String,PWBBObj> pWBBObjMap = new HashMap<String,PWBBObj>();
							HashMap  ifactMap = new HashMap();

							logger.info("Number of Sheets:"+workbook.getNumberOfSheets()+user);
							Iterator<Sheet> sheetIterator = workbook.sheetIterator();
							while (sheetIterator.hasNext()) {
								Sheet sheet = sheetIterator.next();
								logger.info("Sheet Name:" + sheet.getSheetName()+user);
								if(IFactConstant.PWBSHEET.equalsIgnoreCase(sheet.getSheetName())) {
									Iterator<Row> rowIterator = sheet.rowIterator();
									while (rowIterator.hasNext()) {
										Row row = rowIterator.next();
										String currentBillRate = null;
										if(row.getRowNum()==0) {
											continue;
										}else if(row.getRowNum()>= 1) {
											PWBBObj pwbBObj = new PWBBObj();
											Leakage leakage = null;
											Iterator<Cell> cellIterator = row.cellIterator();
											while (cellIterator.hasNext()) {
												Cell cell = cellIterator.next();
												if(null != cell) {
													String cellValue = null;
													if(0==cell.getColumnIndex()) {
														cellValue = dataFormatter.formatCellValue(cell);
														pwbBObj.setWorkerID(cellValue);
													}else if(1==cell.getColumnIndex()) {
														cellValue = dataFormatter.formatCellValue(cell);
														pwbBObj.setEmpID(cellValue);
													}/*else if(2==cell.getColumnIndex()) {
														cellValue = dataFormatter.formatCellValue(cell);
														pWB.setGdceid(cellValue);
													}else if(3==cell.getColumnIndex()) {
														cellValue = dataFormatter.formatCellValue(cell);
														pWB.setFirstName(cellValue);
													}else if(4==cell.getColumnIndex()) {
														cellValue = dataFormatter.formatCellValue(cell);
														pWB.setLastName(cellValue);
													}else if(5==cell.getColumnIndex()) {
														cellValue = dataFormatter.formatCellValue(cell);
														pWB.setWONNumber(cellValue);
													}else if(6==cell.getColumnIndex()) {
														cellValue = dataFormatter.formatCellValue(cell);
														pWB.setWONName(cellValue);
													}else if(7==cell.getColumnIndex()) {
														cellValue = dataFormatter.formatCellValue(cell);
														pWB.setWONType(cellValue);
													}else if(8==cell.getColumnIndex()) {
														cellValue = dataFormatter.formatCellValue(cell);
														pWB.setWorkCountry(cellValue);
													}else if(9==cell.getColumnIndex()) {
														cellValue = dataFormatter.formatCellValue(cell);
														pWB.setWorkLocation(cellValue);
													}else if(10==cell.getColumnIndex()) {
														cellValue = dataFormatter.formatCellValue(cell);
														pWB.setSOWWorkerRole(cellValue);
													}else if(11==cell.getColumnIndex()) {
														cellValue = dataFormatter.formatCellValue(cell);
														pWB.setProjectType(cellValue);
													}*/else if(12==cell.getColumnIndex()) {
														cellValue = dataFormatter.formatCellValue(cell);
														pwbBObj.setDM(cellValue);
													}else if(13==cell.getColumnIndex()) {
														cellValue = dataFormatter.formatCellValue(cell);
														pwbBObj.setDP(cellValue);
													}else if(14==cell.getColumnIndex()) {
														cellValue = dataFormatter.formatCellValue(cell);
														pwbBObj.setCP(cellValue);
													}/*else if(15==cell.getColumnIndex()) {
														cellValue = dataFormatter.formatCellValue(cell);
														Date date1 = sdf.parse(cellValue);
														pWB.setWorkerStartDate(date1);
													}else if(16==cell.getColumnIndex()) {
														cellValue = dataFormatter.formatCellValue(cell);
														Date date2 = sdf.parse(cellValue);
														pWB.setWorkerEndDate(date2);
													}*/else if(17==cell.getColumnIndex()) {
														cellValue = dataFormatter.formatCellValue(cell);
														pwbBObj.setCurrentBillRate(cellValue);
														currentBillRate = cellValue;
													}

													/*else if(18==cell.getColumnIndex()) {
														cellValue = dataFormatter.formatCellValue(cell);
														pWB.setMonth1Max(cellValue);
													}else if(19==cell.getColumnIndex()) {
														cellValue = dataFormatter.formatCellValue(cell);
														pWB.setMonth2Max(cellValue);
													}else if(20==cell.getColumnIndex()) {
														cellValue = dataFormatter.formatCellValue(cell);
														pWB.setMonth3Max(cellValue);
													}*/
													else if(21==cell.getColumnIndex()) {
														cellValue = dataFormatter.formatCellValue(cell);
														pwbBObj.setMonth1Act(cellValue);
													}else if(22==cell.getColumnIndex()) {
														cellValue = dataFormatter.formatCellValue(cell);
														pwbBObj.setMonth2Act(cellValue);
													}else if(23==cell.getColumnIndex()) {
														cellValue = dataFormatter.formatCellValue(cell);
														pwbBObj.setMonth3Act(cellValue);
													}


													if(24==cell.getColumnIndex()) {
														cellValue = dataFormatter.formatCellValue(cell);
														if(null != cellValue && !cellValue.trim().isEmpty()) {
															/*leakage = new Leakage();
																leakage.setLeakageReason(IFactConstant.Leave);
																leakage.setLeakageMonth(IFactConstant.Month1);
																leakage.setLeakageHrs(cellValue);
																leakage.setLeakageRevenue(IFactHelper.calculateHrsRev(currentBillRate, cellValue, dollerRate));
																pWB.addLeakage(leakage);*/
															pwbBObj.setMonth1Leave(cellValue);
															pwbBObj.setMonth1LeaveRevINR(IFactHelper.calculateHrsRev(currentBillRate, cellValue, dollerRate));

														}
													}else if(25==cell.getColumnIndex()) {
														cellValue = dataFormatter.formatCellValue(cell);
														if(null != cellValue && !cellValue.trim().isEmpty()) {
															/*leakage = new Leakage();
																leakage.setLeakageReason(IFactConstant.RDwRepl);
																leakage.setLeakageMonth(IFactConstant.Month1);
																leakage.setLeakageHrs(cellValue);
																leakage.setLeakageRevenue(IFactHelper.calculateHrsRev(currentBillRate, cellValue, dollerRate));
																pWB.addLeakage(leakage);*/
															pwbBObj.setMonth1RDwRepl(cellValue);
															pwbBObj.setMonth1RDwReplRevINR(IFactHelper.calculateHrsRev(currentBillRate, cellValue, dollerRate));

														}
													}else if(26==cell.getColumnIndex()) {
														cellValue = dataFormatter.formatCellValue(cell);
														if(null != cellValue && !cellValue.trim().isEmpty()) {
															/*leakage = new Leakage();
																leakage.setLeakageReason(IFactConstant.RDwoRepl);
																leakage.setLeakageMonth(IFactConstant.Month1);
																leakage.setLeakageHrs(cellValue);
																leakage.setLeakageRevenue(IFactHelper.calculateHrsRev(currentBillRate, cellValue, dollerRate));
																pWB.addLeakage(leakage);*/
															pwbBObj.setMonth1RDwoRepl(cellValue);
															pwbBObj.setMonth1RDwoReplRevINR(IFactHelper.calculateHrsRev(currentBillRate, cellValue, dollerRate));

														}
													}else if(27==cell.getColumnIndex()) {
														cellValue = dataFormatter.formatCellValue(cell);
														if(null != cellValue && !cellValue.trim().isEmpty()) {
															/*leakage = new Leakage();
																leakage.setLeakageReason(IFactConstant.Parking);
																leakage.setLeakageMonth(IFactConstant.Month1);
																leakage.setLeakageHrs(cellValue);
																leakage.setLeakageRevenue(IFactHelper.calculateHrsRev(currentBillRate, cellValue, dollerRate));
																pWB.addLeakage(leakage);*/
															pwbBObj.setMonth1Parking(cellValue);
															pwbBObj.setMonth1ParkingRevINR(IFactHelper.calculateHrsRev(currentBillRate, cellValue, dollerRate));

														}
													}else if(28==cell.getColumnIndex()) {
														cellValue = dataFormatter.formatCellValue(cell);
														if(null != cellValue && !cellValue.trim().isEmpty()) {
															/*leakage = new Leakage();
																leakage.setLeakageReason(IFactConstant.Movement);
																leakage.setLeakageMonth(IFactConstant.Month1);
																leakage.setLeakageHrs(cellValue);
																leakage.setLeakageRevenue(IFactHelper.calculateHrsRev(currentBillRate, cellValue, dollerRate));
																pWB.addLeakage(leakage);*/
															pwbBObj.setMonth1Movmt(cellValue);
															pwbBObj.setMonth1MovmtRevINR(IFactHelper.calculateHrsRev(currentBillRate, cellValue, dollerRate));

														}
													}else if(29==cell.getColumnIndex()) {
														cellValue = dataFormatter.formatCellValue(cell);
														if(null != cellValue && !cellValue.trim().isEmpty()) {
															/*leakage = new Leakage();
																leakage.setLeakageReason(IFactConstant.Others);
																leakage.setLeakageMonth(IFactConstant.Month1);
																leakage.setLeakageHrs(cellValue);
																leakage.setLeakageRevenue(IFactHelper.calculateHrsRev(currentBillRate, cellValue, dollerRate));
																pWB.addLeakage(leakage);*/
															pwbBObj.setMonth1Others(cellValue);
															pwbBObj.setMonth1OthersRevINR(IFactHelper.calculateHrsRev(currentBillRate, cellValue, dollerRate));

														}
													}else if(30==cell.getColumnIndex()) {
														cellValue = dataFormatter.formatCellValue(cell);
														if(null != cellValue && !cellValue.trim().isEmpty()) {
															/*leakage = new Leakage();
																leakage.setLeakageReason(IFactConstant.Leave);
																leakage.setLeakageMonth(IFactConstant.Month2);
																leakage.setLeakageHrs(cellValue);
																leakage.setLeakageRevenue(IFactHelper.calculateHrsRev(currentBillRate, cellValue, dollerRate));
																pWB.addLeakage(leakage);*/
															pwbBObj.setMonth2Leave(cellValue);
															pwbBObj.setMonth2LeaveRevINR(IFactHelper.calculateHrsRev(currentBillRate, cellValue, dollerRate));
														}
													}else if(31==cell.getColumnIndex()) {
														cellValue = dataFormatter.formatCellValue(cell);
														if(null != cellValue && !cellValue.trim().isEmpty()) {
															/*leakage = new Leakage();
																leakage.setLeakageReason(IFactConstant.RDwRepl);
																leakage.setLeakageMonth(IFactConstant.Month2);
																leakage.setLeakageHrs(cellValue);
																leakage.setLeakageRevenue(IFactHelper.calculateHrsRev(currentBillRate, cellValue, dollerRate));
																pWB.addLeakage(leakage);*/
															pwbBObj.setMonth2RDwRepl(cellValue);
															pwbBObj.setMonth2RDwReplRevINR(IFactHelper.calculateHrsRev(currentBillRate, cellValue, dollerRate));
														}
													}else if(32==cell.getColumnIndex()) {
														cellValue = dataFormatter.formatCellValue(cell);
														if(null != cellValue && !cellValue.trim().isEmpty()) {
															/*leakage = new Leakage();
																leakage.setLeakageReason(IFactConstant.RDwoRepl);
																leakage.setLeakageMonth(IFactConstant.Month2);
																leakage.setLeakageHrs(cellValue);
																leakage.setLeakageRevenue(IFactHelper.calculateHrsRev(currentBillRate, cellValue, dollerRate));
																pWB.addLeakage(leakage);*/
															pwbBObj.setMonth2RDwoRepl(cellValue);
															pwbBObj.setMonth2RDwoReplRevINR(IFactHelper.calculateHrsRev(currentBillRate, cellValue, dollerRate));
														}
													}else if(33==cell.getColumnIndex()) {
														cellValue = dataFormatter.formatCellValue(cell);
														if(null != cellValue && !cellValue.trim().isEmpty()) {
															/*leakage = new Leakage();
																leakage.setLeakageReason(IFactConstant.Parking);
																leakage.setLeakageMonth(IFactConstant.Month2);
																leakage.setLeakageHrs(cellValue);
																leakage.setLeakageRevenue(IFactHelper.calculateHrsRev(currentBillRate, cellValue, dollerRate));
																pWB.addLeakage(leakage);*/
															pwbBObj.setMonth2Parking(cellValue);
															pwbBObj.setMonth2ParkingRevINR(IFactHelper.calculateHrsRev(currentBillRate, cellValue, dollerRate));
														}
													}else if(34==cell.getColumnIndex()) {
														cellValue = dataFormatter.formatCellValue(cell);
														if(null != cellValue && !cellValue.trim().isEmpty()) {
															/*leakage = new Leakage();
																leakage.setLeakageReason(IFactConstant.Movement);
																leakage.setLeakageMonth(IFactConstant.Month2);
																leakage.setLeakageHrs(cellValue);
																leakage.setLeakageRevenue(IFactHelper.calculateHrsRev(currentBillRate, cellValue, dollerRate));
																pWB.addLeakage(leakage);*/
															pwbBObj.setMonth2Movmt(cellValue);
															pwbBObj.setMonth2MovmtRevINR(IFactHelper.calculateHrsRev(currentBillRate, cellValue, dollerRate));
														}
													}else if(35==cell.getColumnIndex()) {
														cellValue = dataFormatter.formatCellValue(cell);
														if(null != cellValue && !cellValue.trim().isEmpty()) {
															/*leakage = new Leakage();
																leakage.setLeakageReason(IFactConstant.Others);
																leakage.setLeakageMonth(IFactConstant.Month2);
																leakage.setLeakageHrs(cellValue);
																leakage.setLeakageRevenue(IFactHelper.calculateHrsRev(currentBillRate, cellValue, dollerRate));
																pWB.addLeakage(leakage);*/
															pwbBObj.setMonth2Others(cellValue);
															pwbBObj.setMonth2OthersRevINR(IFactHelper.calculateHrsRev(currentBillRate, cellValue, dollerRate));
														}
													}else if(36==cell.getColumnIndex()) {
														cellValue = dataFormatter.formatCellValue(cell);
														if(null != cellValue && !cellValue.trim().isEmpty()) {
															/*leakage = new Leakage();
																leakage.setLeakageReason(IFactConstant.Leave);
																leakage.setLeakageMonth(IFactConstant.Month3);
																leakage.setLeakageHrs(cellValue);
																leakage.setLeakageRevenue(IFactHelper.calculateHrsRev(currentBillRate, cellValue, dollerRate));
																pWB.addLeakage(leakage);*/
															pwbBObj.setMonth3Leave(cellValue);
															pwbBObj.setMonth3LeaveRevINR(IFactHelper.calculateHrsRev(currentBillRate, cellValue, dollerRate));
														}
													}else if(37==cell.getColumnIndex()) {
														cellValue = dataFormatter.formatCellValue(cell);
														if(null != cellValue && !cellValue.trim().isEmpty()) {
															/*leakage = new Leakage();
																leakage.setLeakageReason(IFactConstant.RDwRepl);
																leakage.setLeakageMonth(IFactConstant.Month3);
																leakage.setLeakageHrs(cellValue);
																leakage.setLeakageRevenue(IFactHelper.calculateHrsRev(currentBillRate, cellValue, dollerRate));
																pWB.addLeakage(leakage);*/
															pwbBObj.setMonth3RDwRepl(cellValue);
															pwbBObj.setMonth3RDwReplRevINR(IFactHelper.calculateHrsRev(currentBillRate, cellValue, dollerRate));
														}
													}else if(38==cell.getColumnIndex()) {
														cellValue = dataFormatter.formatCellValue(cell);
														if(null != cellValue && !cellValue.trim().isEmpty()) {
															/*	leakage = new Leakage();
																leakage.setLeakageReason(IFactConstant.RDwoRepl);
																leakage.setLeakageMonth(IFactConstant.Month3);
																leakage.setLeakageHrs(cellValue);
																leakage.setLeakageRevenue(IFactHelper.calculateHrsRev(currentBillRate, cellValue, dollerRate));
																pWB.addLeakage(leakage);*/
															pwbBObj.setMonth3RDwoRepl(cellValue);
															pwbBObj.setMonth3RDwoReplRevINR(IFactHelper.calculateHrsRev(currentBillRate, cellValue, dollerRate));
														}
													}else if(39==cell.getColumnIndex()) {
														cellValue = dataFormatter.formatCellValue(cell);
														if(null != cellValue && !cellValue.trim().isEmpty()) {
															/*leakage = new Leakage();
																leakage.setLeakageReason(IFactConstant.Parking);
																leakage.setLeakageMonth(IFactConstant.Month3);
																leakage.setLeakageHrs(cellValue);
																leakage.setLeakageRevenue(IFactHelper.calculateHrsRev(currentBillRate, cellValue, dollerRate));
																pWB.addLeakage(leakage);*/
															pwbBObj.setMonth3Parking(cellValue);
															pwbBObj.setMonth3ParkingRevINR(IFactHelper.calculateHrsRev(currentBillRate, cellValue, dollerRate));
														}
													}else if(40==cell.getColumnIndex()) {
														cellValue = dataFormatter.formatCellValue(cell);
														if(null != cellValue && !cellValue.trim().isEmpty()) {
															/*leakage = new Leakage();
																leakage.setLeakageReason(IFactConstant.Movement);
																leakage.setLeakageMonth(IFactConstant.Month3);
																leakage.setLeakageHrs(cellValue);
																leakage.setLeakageRevenue(IFactHelper.calculateHrsRev(currentBillRate, cellValue, dollerRate));
																pWB.addLeakage(leakage);*/
															pwbBObj.setMonth3Movmt(cellValue);
															pwbBObj.setMonth3MovmtRevINR(IFactHelper.calculateHrsRev(currentBillRate, cellValue, dollerRate));
														}
													}else if(41==cell.getColumnIndex()) {
														cellValue = dataFormatter.formatCellValue(cell);
														if(null != cellValue && !cellValue.trim().isEmpty()) {
															/*	leakage = new Leakage();
																leakage.setLeakageReason(IFactConstant.Others);
																leakage.setLeakageMonth(IFactConstant.Month3);
																leakage.setLeakageHrs(cellValue);
																leakage.setLeakageRevenue(IFactHelper.calculateHrsRev(currentBillRate, cellValue, dollerRate));
																pWB.addLeakage(leakage);*/
															pwbBObj.setMonth3Others(cellValue);
															pwbBObj.setMonth3OthersRevINR(IFactHelper.calculateHrsRev(currentBillRate, cellValue, dollerRate));
														}
													}
												}
											}
											pWBBObjList.add(pwbBObj);
											pWBBObjMap.put(pwbBObj.getWorkerID()+pwbBObj.getEmpID(), pwbBObj);
										}
									}
									ifactMap.put(IFactConstant.PWBLIST, pWBBObjList);
									ifactMap.put(IFactConstant.PWBMAP, pWBBObjMap);
									respObj.setResponseObject(ifactMap);
									respObj.setError(false);
									respObj.setMessage("PWB Object Created from Excel");
								}
							}

						}
					}
				}
			}

		}catch(Exception e) {

			logger.error(e);
			respObj.setError(true);
			respObj.setMessage(e.getMessage());

		}

		return respObj;
	}

	/*public ResponseBObj getPWBDataInExcel(ResponseBObj res) {
		ResponseBObj respObj = new ResponseBObj(); 

		try {
			if(null != res && null != res.getResponseObject() && res.getResponseObject() instanceof List ) {
				ArrayList<PWBExcelBObj> pwbList =  (ArrayList<PWBExcelBObj>) res.getResponseObject();
				Workbook workbook = new XSSFWorkbook(); 
				CreationHelper createHelper = workbook.getCreationHelper();
				Sheet sheet = workbook.createSheet("PWB");

				String Month1 = iFactDBHandler.getUtilValueByKey(IFactConstant.Month1);
				String Month2 = iFactDBHandler.getUtilValueByKey(IFactConstant.Month2);
				String Month3 = iFactDBHandler.getUtilValueByKey(IFactConstant.Month3);

				Font headerFont = workbook.createFont();
				headerFont.setBold(true);
				headerFont.setFontHeightInPoints((short) 11);
				headerFont.setColor(IndexedColors.BLACK.getIndex());

				CellStyle headerCellStyle = workbook.createCellStyle();
				headerCellStyle.setFont(headerFont);

				headerCellStyle.setBorderBottom(BorderStyle.THIN);
				headerCellStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
				headerCellStyle.setBorderLeft(BorderStyle.THIN);
				headerCellStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
				headerCellStyle.setBorderRight(BorderStyle.THIN);
				headerCellStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
				headerCellStyle.setBorderTop(BorderStyle.THIN);
				headerCellStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
				headerCellStyle.setFillPattern(FillPatternType.LESS_DOTS); 
				headerCellStyle.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
				headerCellStyle.setFillBackgroundColor(IndexedColors.LIGHT_GREEN.getIndex());
				headerCellStyle.setLocked(true);

				Row headerRow = sheet.createRow(0);

				ArrayList<String> columns = getColumns(Month1,Month2,Month3);

				for(int i = 0; i < columns.size(); i++) {
					Cell cell = headerRow.createCell(i);
					cell.setCellValue(columns.get(i));
					cell.setCellStyle(headerCellStyle);
				}

					CellStyle dateCellStyle = workbook.createCellStyle();
				dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("yyyy-MM-dd"));

				CellStyle numberCellStyle = workbook.createCellStyle();
				numberCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("000.00"));

				//FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();


				if(null != pwbList) {
					int rowNum = 1;
					Iterator<PWBExcelBObj> itr = pwbList.iterator();
					while(itr.hasNext()){
						PWBExcelBObj pwb = itr.next();
						Row row = sheet.createRow(rowNum++);
						row.createCell(0).setCellValue(pwb.getWorkerID());
						row.createCell(1).setCellValue(pwb.getEmpID());
						row.createCell(2).setCellValue(pwb.getGDCEID());
						row.createCell(3).setCellValue(pwb.getFirstName());
						row.createCell(4).setCellValue(pwb.getLastName());
						row.createCell(5).setCellValue(pwb.getWONNumber());
						row.createCell(6).setCellValue(pwb.getWONName());
						row.createCell(7).setCellValue(pwb.getWONType());
						row.createCell(8).setCellValue(pwb.getWorkCountry());
						row.createCell(9).setCellValue(pwb.getWorkLocation());
						row.createCell(10).setCellValue(pwb.getSOWWorkerRole());
						row.createCell(11).setCellValue(pwb.getProjectType());
						row.createCell(12).setCellValue(pwb.getDM());
						row.createCell(13).setCellValue(pwb.getDP());
						row.createCell(14).setCellValue(pwb.getCP());

						row.createCell(15).setCellValue(pwb.getWorkerStartDate());
						//Cell workerStartDateCell = row.createCell(15);
						//workerStartDateCell.setCellValue(pwb.getWorkerStartDate());
						//workerStartDateCell.setCellStyle(dateCellStyle);
						//row.createCell(16).setCellValue(bObj.getWorkerStartDate());
						//row.createCell(16).setCellStyle(dateCellStyle);

						row.createCell(16).setCellValue(pwb.getWorkerEndDate());
						//Cell workerEndDateCell = row.createCell(16);
						//workerEndDateCell.setCellValue(pwb.getWorkerEndDate());
						//workerEndDateCell.setCellStyle(dateCellStyle);

						Cell currentBillRateCell = row.createCell(17);
						currentBillRateCell.setCellValue(pwb.getCurrentBillRate());
						currentBillRateCell.setCellStyle(numberCellStyle);
						currentBillRateCell.setCellType(CellType.NUMERIC);
						row.createCell(17).setCellValue(pwb.getCurrentBillRate());
						//row.createCell(15).setCellValue(bObj.getCurrentBillRate());

						//row.createCell(17).setCellValue(bObj.getWorkerEndDate());
						//row.createCell(17).setCellStyle(dateCellStyle);

						row.createCell(18).setCellValue(pwb.getMonth1Max());
						row.createCell(19).setCellValue(pwb.getMonth2Max());
						row.createCell(20).setCellValue(pwb.getMonth3Max());
						row.createCell(21).setCellValue(pwb.getMonth1Act());
						row.createCell(22).setCellValue(pwb.getMonth2Act());
						row.createCell(23).setCellValue(pwb.getMonth3Act());
						row.createCell(24).setCellValue(pwb.getMonth3Diff());
						row.createCell(25).setCellValue(pwb.getMonth3Diff());
						row.createCell(26).setCellValue(pwb.getMonth3Diff());

						row.createCell(27).setCellValue(pwb.getMonth1Leave());
						row.createCell(28).setCellValue(pwb.getMonth1RDwRepl());
						row.createCell(29).setCellValue(pwb.getMonth1RDwoRepl());
						row.createCell(30).setCellValue(pwb.getMonth1Parking());
						row.createCell(31).setCellValue(pwb.getMonth1Movmt());
						row.createCell(32).setCellValue(pwb.getMonth1Others());

						row.createCell(33).setCellValue(pwb.getMonth2Leave());
						row.createCell(34).setCellValue(pwb.getMonth2RDwRepl());
						row.createCell(35).setCellValue(pwb.getMonth2RDwoRepl());
						row.createCell(36).setCellValue(pwb.getMonth2Parking());
						row.createCell(37).setCellValue(pwb.getMonth2Movmt());
						row.createCell(38).setCellValue(pwb.getMonth2Others());

						row.createCell(39).setCellValue(pwb.getMonth3Leave());
						row.createCell(40).setCellValue(pwb.getMonth3RDwRepl());
						row.createCell(41).setCellValue(pwb.getMonth3RDwoRepl());
						row.createCell(42).setCellValue(pwb.getMonth3Parking());
						row.createCell(43).setCellValue(pwb.getMonth3Movmt());
						row.createCell(44).setCellValue(pwb.getMonth3Others());

						row.createCell(45).setCellValue(pwb.getMonth1LeaveRevINR());
						row.createCell(46).setCellValue(pwb.getMonth1RDwReplRevINR());
						row.createCell(47).setCellValue(pwb.getMonth1RDwoReplRevINR());
						row.createCell(48).setCellValue(pwb.getMonth1ParkingRevINR());
						row.createCell(49).setCellValue(pwb.getMonth1MovmtRevINR());
						row.createCell(50).setCellValue(pwb.getMonth1OthersRevINR());

						row.createCell(51).setCellValue(pwb.getMonth2LeaveRevINR());
						row.createCell(52).setCellValue(pwb.getMonth2RDwReplRevINR());
						row.createCell(53).setCellValue(pwb.getMonth2RDwoReplRevINR());
						row.createCell(54).setCellValue(pwb.getMonth2ParkingRevINR());
						row.createCell(55).setCellValue(pwb.getMonth2MovmtRevINR());
						row.createCell(56).setCellValue(pwb.getMonth2OthersRevINR());

						row.createCell(57).setCellValue(pwb.getMonth3LeaveRevINR());
						row.createCell(58).setCellValue(pwb.getMonth3RDwReplRevINR());
						row.createCell(59).setCellValue(pwb.getMonth3RDwoReplRevINR());
						row.createCell(60).setCellValue(pwb.getMonth3ParkingRevINR());
						row.createCell(61).setCellValue(pwb.getMonth3MovmtRevINR());
						row.createCell(62).setCellValue(pwb.getMonth3OthersRevINR());

						row.createCell(63).setCellValue(pwb.getMonth1RevINR());
						row.createCell(64).setCellValue(pwb.getMonth2RevINR());
						row.createCell(65).setCellValue(pwb.getMonth3RevINR());

						row.createCell(66).setCellValue(pwb.getMonth1Rev());
						row.createCell(67).setCellValue(pwb.getMonth2Rev());
						row.createCell(68).setCellValue(pwb.getMonth3Rev());

					}
				}
				for(int i = 0; i < columns.size(); i++) {
					sheet.autoSizeColumn(i);
				}

				sheet.createFreezePane(0,1);
				FileOutputStream fileOut = new FileOutputStream("C:\\Tools\\iFact\\PWBinExcel.xlsx");
				workbook.write(fileOut);
				fileOut.close();
				workbook.close();
				respObj.setError(false);
				respObj.setMessage("Excel Has been Created/Updated");
				//respObj.setResponseObject(fileOut);
				System.out.println("Excel Has been Created/Updated");
			}
		}catch(Exception ex) {
			ex.printStackTrace();
			System.out.println("Error in getPWBDataInExcel::"+ex.getMessage());
			respObj.setError(true);
			respObj.setMessage(ex.getMessage());
		}
		return respObj;
	}*/

	/*private ArrayList<String> getColumns(String Month1, String Month2, String Month3) {
		ArrayList<String> col = new ArrayList<String>();
		String[] empData = {"WorkerID", "EmpID", "GDCEID", "FirstName","LastName", "WONNumber", 
				"WONName", "WONType","WorkCountry", "WorkLocation", "SOWWorkerRole", "ProjectType",
				"DM", "DP","CP",  "WorkerStartDate", "WorkerEndDate","CurrentBillRate"};

		String[] leakageMonth1 = {Month1+"-Leave",Month1+"-RD w Repl",Month1+"-RD wo Repl",Month1+"-Parking",Month1+"-Movmt",Month1+"-Others"};
		String[] leakageMonth2 = {Month2+"-Leave",Month2+"-RD w Repl",Month2+"-RD wo Repl",Month2+"-Parking",Month2+"-Movmt",Month2+"-Others"};
		String[] leakageMonth3 = {Month3+"-Leave",Month3+"-RD w Repl",Month3+"-RD wo Repl",Month3+"-Parking",Month3+"-Movmt",Month3+"-Others"};

		String[] leakageMonth1Rev = {Month1+"-Leave",Month1+"-RD w Repl",Month1+"-RD wo Repl",Month1+"-Parking",Month1+"-Movmt",Month1+"-Others"};
		String[] leakageMonth2Rev = {Month2+"-Leave",Month2+"-RD w Repl",Month2+"-RD wo Repl",Month2+"-Parking",Month2+"-Movmt",Month2+"-Others"};
		String[] leakageMonth3Rev = {Month3+"-Leave",Month3+"-RD w Repl",Month3+"-RD wo Repl",Month3+"-Parking",Month3+"-Movmt",Month3+"-Others"};

		String Month1Max  = Month1+"-Max";
		String Month2Max  = Month2+"-Max";
		String Month3Max  = Month3+"-Max";

		String Month1Act  = Month1+"-Act";
		String Month2Act  = Month2+"-Act";
		String Month3Act  = Month3+"-Act";

		String Month1Diff  = Month1+"-Diff";
		String Month2Diff  = Month2+"-Diff";
		String Month3Diff  = Month3+"-Diff";

		String Month1Rev  = Month1+"-Rev($)";
		String Month2Rev  = Month2+"-Rev($)";
		String Month3Rev  = Month3+"-Rev($)";

		String Month1RevINR  = Month1+"-Rev(INR)";
		String Month2RevINR  = Month2+"-Rev(INR)";
		String Month3RevINR  = Month3+"-Rev(INR)";

		List<String> empDataList = Arrays.asList(empData); 
		List<String> leakageMonth1List = Arrays.asList(leakageMonth1); 
		List<String> leakageMonth2List = Arrays.asList(leakageMonth2); 
		List<String> leakageMonth3List = Arrays.asList(leakageMonth3);
		List<String> leakageMonth1RevList = Arrays.asList(leakageMonth1Rev); 
		List<String> leakageMonth2RevList = Arrays.asList(leakageMonth2Rev); 
		List<String> leakageMonth3RevList = Arrays.asList(leakageMonth3Rev);

		col.addAll(empDataList);

		col.add(Month1Max);
		col.add(Month2Max);
		col.add(Month3Max);

		col.add(Month1Act);
		col.add(Month2Act);
		col.add(Month3Act);


		col.add(Month1Diff);
		col.add(Month2Diff);
		col.add(Month3Diff);

		col.addAll(leakageMonth1List);
		col.addAll(leakageMonth2List);
		col.addAll(leakageMonth3List);

		col.add(Month1Rev);
		col.add(Month2Rev);
		col.add(Month3Rev);

		col.add(Month1RevINR);
		col.add(Month2RevINR);
		col.add(Month3RevINR);

		col.addAll(leakageMonth1RevList);
		col.addAll(leakageMonth2RevList);
		col.addAll(leakageMonth3RevList);


		return col;
	}*/

}
