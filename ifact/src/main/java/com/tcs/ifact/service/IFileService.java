package com.tcs.ifact.service;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;


public interface IFileService {

	ResponseEntity<Object> storeFile(String user, MultipartFile file);
	ResponseEntity<Resource> getFilebyUser(String user);

}
