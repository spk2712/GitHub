package com.tcs.ifact.handler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.tcs.ifact.bobj.ResponseBObj;
import com.tcs.ifact.dao.UtilDaoImpl;
import com.tcs.ifact.model.DBFile;

@Component
public class FileHandler {
	
	private static final Logger logger = LogManager.getLogger(FileHandler.class);
	
	@Autowired
	IFactDBHandler iFactDBHandler;
	
	@Autowired
	IFactExcelHandler iFactExcelHandler;
	
	@Autowired
	IFactObjectHandler iFactObjectHandler;

	public ResponseBObj storeFile(String user, MultipartFile file) {
		ResponseBObj resp = new ResponseBObj();
		String fileName = null;
		try {
		fileName = StringUtils.cleanPath(file.getOriginalFilename());
			// Check if the file's name contains invalid characters
			if(fileName.contains("..")) {
				resp.setError(true);
				resp.setMessage("Sorry! Filename contains invalid path sequence " + fileName);
			}else {
				DBFile dbFile = new DBFile(user,fileName, file.getContentType(), file.getBytes());
				resp = iFactDBHandler.persistDBFile(dbFile);
			}
		} catch (Exception ex) {
			logger.error(ex);
			resp.setError(true);
			resp.setMessage("Could not store file " + fileName + ". Please try again!::" + ex.getMessage());
		}
		return resp;
	}

	
	public ResponseBObj getFilebyUser(String user) {
		ResponseBObj resp = new ResponseBObj();
		String fileName = null;
		try {
			resp = iFactDBHandler.getDBFileByUser(user);
		} catch (Exception ex) {
			logger.error(ex);
			resp.setError(true);
			resp.setMessage("Could not store file " + fileName + ". Please try again!::" + ex.getMessage());
		}
		return resp;
	}

}
