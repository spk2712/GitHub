package com.tcs.ifact.service;


import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;



public interface IPWBService {
	
	ResponseEntity<String> pwbFromRawData();
	ResponseEntity<String> pwbFromExcel();
	ResponseEntity<String> uploadPWBForDM();
	ResponseEntity<String> uploadPWBForDP();
	ResponseEntity<String> getPWBForDP();
	ResponseEntity<String> getPWBForDM();
	ResponseEntity<String> uploadExcel(MultipartFile file, String fileName);

}
