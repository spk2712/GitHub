package com.tcs.ifact.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.tcs.ifact.service.IFileService;

@RestController
@RequestMapping(path="/ifact/file")

public class FileController {
	
	private static final Logger logger = LogManager.getLogger(FileController.class);
	
	@Autowired
	IFileService iFileService;
	
	//@Secured ({"ROLE_ADMIN", "ROLE_DP","ROLE_DM","ROLE_PMO"})
	 @PostMapping("/uploadFile")
	    public ResponseEntity<Object> uploadFile(@RequestParam("file") MultipartFile file,String user) {
		logger.debug("Welcome to uploadFile");
		 return iFileService.storeFile(user,file);
		 
	 }
	
	/*@Secured ({"ROLE_ADMIN", "ROLE_DP","ROLE_DM"})
	 @GetMapping("/downloadFile")
	    public ResponseEntity<Resource> downloadFile(@RequestParam String user) {
		 return iFileService.getFilebyUser(user);
	 }*/
	

}
