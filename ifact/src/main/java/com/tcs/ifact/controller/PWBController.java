package com.tcs.ifact.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.tcs.ifact.bobj.PWBUpdateBObj;
import com.tcs.ifact.bobj.ResponseBObj;
import com.tcs.ifact.service.IIFactService;
import com.tcs.ifact.service.IPWBService;

@RestController
@RequestMapping(path="/ifact/pwb")
public class PWBController {
	
	private static final Logger logger = LogManager.getLogger(PWBController.class);

	@Autowired
	IPWBService ipwbService;


	@RequestMapping(value="/pwbFromRawData",method=RequestMethod.PUT)
	//@Secured ({"ROLE_ADMIN"})
	public ResponseEntity<Object> pwbFromRawData(@RequestParam String user) {
		logger.debug("Welcome to pwbFromRawData");
		return ipwbService.pwbFromRawData(user);
	}
	

	@RequestMapping(value="/pwbWeekFreeze",method=RequestMethod.PUT)
	//@Secured ({"ROLE_ADMIN"})
	public ResponseEntity<Object> pwbWeekFreeze() {
		logger.debug("Welcome to pwbFromRawData");
		return ipwbService.pwbWeekFreeze();
	}
	
	@RequestMapping(value="/getAllPWBData",method=RequestMethod.GET)
	//@Secured ({"ROLE_ADMIN"})
	public ResponseEntity<Object> getAllPWBData() {
		logger.debug("Welcome to getAllPWBData");
		return ipwbService.getAllPWBData();
	}
	
	//@Secured ({"ROLE_ADMIN", "ROLE_DP","ROLE_DM","ROLE_PMO"})
	@RequestMapping(value="/pwbFromExcel",method=RequestMethod.PUT)
	public ResponseEntity<Object> pwbFromExcel(@RequestParam String user) {
		logger.debug("Welcome to pwbFromExcel");
		return ipwbService.pwbFromExcel(user);
	}
	
	
	@RequestMapping(value="/getPWBDataByUser",method=RequestMethod.GET)
	//@Secured ({"ROLE_ADMIN", "ROLE_DP","ROLE_DM","ROLE_PMO"})
	public ResponseEntity<Object> getPWBDataByUser(@RequestParam String user) {
		logger.debug("Welcome to getAllPWBData");
		return ipwbService.getPWBDataByUser(user);
	}
	
	
	@RequestMapping(value="/upadtePWBData",method=RequestMethod.GET)
	//@Secured ({"ROLE_ADMIN", "ROLE_DP","ROLE_DM"})
	public ResponseEntity<Object> upadtePWBData(@RequestBody List<PWBUpdateBObj> pwbUpdate,@RequestParam String user) {
		logger.debug("Welcome to getAllPWBData");
		return ipwbService.upadtePWBData(pwbUpdate,user);
	}
	
	/*@Secured ({"ROLE_ADMIN", "ROLE_DP","ROLE_DM"})
	@RequestMapping(value="/getPWBinExcel",method=RequestMethod.GET)
	public ResponseEntity<Object> getPWBinExcel() {
		System.out.println("Welcome to getPWBinExcel");
		return ipwbService.getPWBinExcel();
	}
*/
	/*@Secured ({"ROLE_ADMIN", "ROLE_DP","ROLE_DM"})
	@RequestMapping(value="/uploadPWBForDM",method=RequestMethod.PUT)
	public ResponseEntity<String> uploadPWBForDM() {
		String resp = "Welcome to uploadPWBForDM";
		return new ResponseEntity<String>(resp, HttpStatus.OK);
	}

	@Secured ({"ROLE_ADMIN", "ROLE_DP"})
	@RequestMapping(value="/uploadPWBForDP",method=RequestMethod.PUT)
	public ResponseEntity<String> uploadPWBForDP() {
		String resp = "Welcome to uploadPWBForDP";
		return new ResponseEntity<String>(resp, HttpStatus.OK);
	}

	@Secured ({"ROLE_ADMIN", "ROLE_DP"})
	@RequestMapping(value = "/getPWBForDP",method=RequestMethod.GET)
	public ResponseEntity<String> getPWBForDP() {
		String resp = "Welcome to getPWBForDP";
		return new ResponseEntity<String>(resp, HttpStatus.OK);
	}

	@Secured ({"ROLE_ADMIN", "ROLE_DP","ROLE_DM"})
	@RequestMapping(value = "/getPWBForDM",method=RequestMethod.GET)
	public ResponseEntity<String> getPWBForDM() {
		String resp = "Welcome to getPWBForDM";
		return new ResponseEntity<String>(resp, HttpStatus.OK);
	}*/

	/*@Secured ({"ROLE_ADMIN", "ROLE_DP","ROLE_DM"})
	@RequestMapping(value = "/uploadExcel",method = RequestMethod.POST)
	public ResponseEntity<String> uploadExcel(@RequestParam("file")MultipartFile file,@RequestParam("fileName")String fileName) {
		System.out.println("Welcome to uploadExcel");
		return ipwbService.uploadExcel(file,fileName);
	}*/



}
