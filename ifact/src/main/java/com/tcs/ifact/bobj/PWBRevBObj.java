package com.tcs.ifact.bobj;

import java.util.ArrayList;

public class PWBRevBObj {

	private ArrayList<CPRevBObj> cpRevBObjList;
	
	
	public ArrayList<CPRevBObj> getCpRevBObjList() {
		return cpRevBObjList;
	}
	public void setDpRevBObjList(ArrayList<CPRevBObj> cpRevBObjList) {
		this.cpRevBObjList = cpRevBObjList;
	}
	
	public void addCPRevBObj(CPRevBObj cpRevBObj) {
		if(null != getCpRevBObjList()) {
			getCpRevBObjList().add(cpRevBObj);
		}else {
			this.cpRevBObjList = new ArrayList<CPRevBObj>();
			getCpRevBObjList().add(cpRevBObj);
		}
	}

	public void removeCPRevBObj(CPRevBObj cpRevBObj) {
		if(null != getCpRevBObjList()) {
			getCpRevBObjList().remove(cpRevBObj);
		}
	}
	
	
	
}
