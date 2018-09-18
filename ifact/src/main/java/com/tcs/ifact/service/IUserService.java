package com.tcs.ifact.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.multipart.MultipartFile;

import com.tcs.ifact.bobj.UserBObj;
import com.tcs.ifact.model.UserInfo;


public interface IUserService {

	ResponseEntity<String> addUser(UserBObj userBObj);
	ResponseEntity<String> updateUser(UserBObj userBObj);
	ResponseEntity<Object> getAllUser();
	ResponseEntity<Object> getUserByRole(String role);
	ResponseEntity<Object> getUserByProject(String project);
	ResponseEntity<Object> getUserByUserId(String user);
	ResponseEntity<String> deleteByUserId(String user);
	ResponseEntity<String> deleteAllUser();
	ResponseEntity<String> enableAndDisableUser(String user, String flag);
	
}
