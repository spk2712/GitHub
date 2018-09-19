package com.tcs.ifact.handler;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.web.multipart.MultipartFile;

import com.tcs.ifact.bobj.ETSSBObj;
import com.tcs.ifact.bobj.FGBObj;
import com.tcs.ifact.bobj.ResponseBObj;
import com.tcs.ifact.bobj.UxBObj;
import com.tcs.ifact.model.Pwb;
import com.tcs.ifact.model.PwbPK;

public class IFactExcelHandler {

	public ResponseBObj getDataFromExcel() {
		ResponseBObj respObj = new ResponseBObj(); 
		try {
			FileInputStream rawdataFile = new FileInputStream(new File("C:\\Tools\\IFACT\\iFactRawData.xlsx"));
			Workbook workbook = WorkbookFactory.create(rawdataFile);

			ArrayList<UxBObj> uxBObjList = new ArrayList<UxBObj>();
			HashMap<String,UxBObj> uxMap = new HashMap<String,UxBObj>();

			ArrayList<FGBObj> fgBObjList = new ArrayList<FGBObj>();
			HashMap<String,FGBObj> fgMap = new HashMap<String,FGBObj>();

			ArrayList<ETSSBObj> etBObjList = new ArrayList<ETSSBObj>();
			HashMap<String,ETSSBObj> etMap = new HashMap<String,ETSSBObj>();
			
			ArrayList<Pwb> pwbList = new ArrayList<Pwb>();
			HashMap<String,Pwb> pwbMap = new HashMap<String,Pwb>();

			DataFormatter dataFormatter = new DataFormatter();
			FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
			
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

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
					PwbPK pk = new PwbPK();
					Pwb pwb = new Pwb();
					pk.setWorkerID(fBObj.getWorkerID());
					pwb.setFirstName(fBObj.getFirstName());
					pwb.setLastName(fBObj.getLastName());
					pwb.setGdceid(fBObj.getGDCEID());
					pwb.setSOWWorkerRole(fBObj.getSOWWorkerRole());
					pwb.setCurrentBillRate(fBObj.getCurrentBillRate());
					Date date1 = sdf.parse(fBObj.getWorkerStartDate());
					pwb.setWorkerStartDate(date1);
					Date date2 = sdf.parse(fBObj.getWorkerEndDate());
					pwb.setWorkerEndDate(date2);
					if(null != etMap && etMap.containsKey(pk.getWorkerID())&& null != etMap.get(pk.getWorkerID())) {
						ETSSBObj eBObj = etMap.get(pk.getWorkerID());
						pk.setEmpID(eBObj.getEmpID());
						pwb.setDm(eBObj.getDM());
					}else {
						if(IFactHelper.isNumeric(fBObj.getLoginID())) {
							pk.setEmpID(fBObj.getLoginID());
						}
					}
					if(null != uxMap && uxMap.containsKey(pk.getEmpID())&&null!=uxMap.get(pk.getEmpID())) {
						UxBObj uBObj = uxMap.get(pk.getEmpID());
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
					
					pwb.setMonth1Max(IFactHelper.constructMonthMax(pwb.getWorkLocation(),"Year","Month"));
					pwb.setMonth2Max(IFactHelper.constructMonthMax(pwb.getWorkLocation(),"Year","Month"));
					pwb.setMonth3Max(IFactHelper.constructMonthMax(pwb.getWorkLocation(),"Year","Month"));
					
					Date now = new Date();
					pwb.setLastUpdatedDate(now);

					if(null != pk.getWorkerID() && null != pk.getEmpID() && IFactHelper.isNumeric(pk.getEmpID())) {
						pwb.setId(pk);
						pwbList.add(pwb);
						pwbMap.put(pk.getWorkerID(), pwb);
					}
				}
			}
		}catch(Exception e) {

		}


		return respObj;		
	}

	public ResponseBObj uploadExcel(MultipartFile file, String fileName) {
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
		
	}

}
