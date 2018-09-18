package com.tcs.ifact.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.tcs.ifact.service.IIFactService;
import com.tcs.ifact.service.IPWBService;

@RestController
@RequestMapping(path="/ifact/pwb")

public class PWBController {

	@Autowired
	IPWBService ipwbService;


	@RequestMapping(value="/pwbFromRawData",method=RequestMethod.PUT)
	@Secured ({"ROLE_ADMIN"})
	public ResponseEntity<String> pwbFromRawData() {
		String resp = "Welcome to pwbFromiFact";
		ipwbService.pwbFromRawData();
		return new ResponseEntity<String>(resp, HttpStatus.OK);
	}
	@Secured ({"ROLE_ADMIN", "ROLE_DP","ROLE_DM"})
	@RequestMapping(value="/pwbFromExcel",method=RequestMethod.PUT)
	public ResponseEntity<String> pwbFromExcel() {
		String resp = "Welcome to pwbFromExcel";
		return new ResponseEntity<String>(resp, HttpStatus.OK);
	}

	@Secured ({"ROLE_ADMIN", "ROLE_DP","ROLE_DM"})
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
	}

	@Secured ({"ROLE_ADMIN", "ROLE_DP","ROLE_DM"})
	@RequestMapping(value = "/uploadExcel",method = RequestMethod.POST)
	public ResponseEntity<String> uploadExcel(@RequestParam("file")MultipartFile file,@RequestParam("fileName")String fileName) {
		System.out.println("Welcome to uploadExcel");
		return ipwbService.uploadExcel(file,fileName);
	}



}
