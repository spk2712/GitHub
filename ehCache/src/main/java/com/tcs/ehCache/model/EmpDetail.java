package com.tcs.ehCache.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name="empdetail")
@NamedQuery(name="EmpDetail.findAll", query="SELECT e FROM EmpDetail e")
@Cache(usage=CacheConcurrencyStrategy.READ_ONLY/*, region="employee"*/)
public class EmpDetail {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private int empid;
	
	@Column(length=45)
	private String lastname;
	
	@Column(length=45)
	private String firstname;
	
	@Column(length=45)
	private String phonenumber;

	public EmpDetail() {
		
	}

	public EmpDetail(int empid, String lastname, String firstname, String phonenumber) {
		super();
		this.empid = empid;
		this.lastname = lastname;
		this.firstname = firstname;
		this.phonenumber = phonenumber;
	}

	public int getEmpid() {
		return empid;
	}

	public void setEmpid(int empid) {
		this.empid = empid;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getPhonenumber() {
		return phonenumber;
	}

	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}
	
	

}
