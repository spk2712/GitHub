package com.tcs.ifact.bobj;

import java.util.List;

public class ResponseBObj {
	
	private String message;
	private boolean isError;
	private List<Object> objectList;
	private Object responseObject;
	
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public boolean isError() {
		return isError;
	}
	public void setError(boolean isError) {
		this.isError = isError;
	}
	public List<Object> getObjectList() {
		return objectList;
	}
	public void setObjectList(List<Object> objectList) {
		this.objectList = objectList;
	}
	public Object getResponseObject() {
		return responseObject;
	}
	public void setResponseObject(Object responseObject) {
		this.responseObject = responseObject;
	}
	
	


}
