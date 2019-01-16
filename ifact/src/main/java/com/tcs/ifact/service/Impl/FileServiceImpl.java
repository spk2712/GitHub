package com.tcs.ifact.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.tcs.ifact.bobj.ResponseBObj;
import com.tcs.ifact.bobj.UploadFileBObj;
import com.tcs.ifact.handler.FileHandler;
import com.tcs.ifact.model.DBFile;
import com.tcs.ifact.service.IFileService;

@Service
public class FileServiceImpl implements IFileService {
	
@Autowired
FileHandler fileHandler;
	@Override
	public ResponseEntity<Object> storeFile(String user,MultipartFile file) {
		ResponseBObj res = fileHandler.storeFile(user,file);

		if(null != res) {
			if(!res.isError()) {
				if(null != res && null != res.getResponseObject() && res.getResponseObject() instanceof DBFile) {
					DBFile dbfile = (DBFile) res.getResponseObject(); 
					if(null != dbfile & null != dbfile.getData()) {
						/*String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
								.path("/ifact/file/downloadFile?user="+dbfile.getUser())
								.toUriString();*/
						String fileDownloadUri = null;
						UploadFileBObj payload = new UploadFileBObj(dbfile.getFilename(), fileDownloadUri,
								file.getContentType(), file.getSize());
						return new ResponseEntity<Object>(payload,HttpStatus.EXPECTATION_FAILED);
					}
				}
			}else {
				return new ResponseEntity<Object>(res,HttpStatus.EXPECTATION_FAILED);
			}
		}
		return new ResponseEntity<Object>("Default Message",HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Resource> getFilebyUser(String user) {
		ResponseBObj res = new ResponseBObj();
		res.setError(true);
		res.setMessage("Error getUserByRole");
		res  = fileHandler.getFilebyUser(user);
		if(null != res) {
			if(res.isError()) {
				return new ResponseEntity<Resource>(HttpStatus.EXPECTATION_FAILED);
			}else {
				if(null != res.getResponseObject() && res.getResponseObject() instanceof DBFile) {
					DBFile dbFile = (DBFile)res.getResponseObject();
				       return ResponseEntity.ok()
				                .contentType(MediaType.parseMediaType(dbFile.getFiletype()))
				                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + dbFile.getFilename() + "\"")
				                .body(new ByteArrayResource(dbFile.getData()));
				}
			}
		}
		return new ResponseEntity<Resource>(HttpStatus.EXPECTATION_FAILED);
	}

}
