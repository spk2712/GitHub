package com.tcs.ifact.handler;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;

import org.springframework.web.multipart.MultipartFile;

import com.tcs.ifact.bobj.ResponseBObj;

public class IFactExcelHandler {

	public ResponseBObj getDataFromExcel() {
		ResponseBObj respObj = new ResponseBObj(); 
		
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
				System.out.println("Failed to upload Excel"+ file.getOriginalFilename()+"=>"+e.getMessage());
				respObj.setError(true);
				respObj.setMessage(e.getMessage());
			}
		}
		return respObj;
		
	}

}
