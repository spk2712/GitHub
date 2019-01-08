package com.tcs.ifact.model;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.Type;
//import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import java.math.BigInteger;
import java.util.Date;


/**
 * The persistent class for the users database table.
 * 
 */
@Entity
//@EnableAutoConfiguration
@Table(name="users")
@NamedQuery(name="UserInfo.findAll", query="SELECT u FROM UserInfo u")
public class UserInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(unique=true, nullable=false)
	private String user;

	@Column(length=45)
	private String enabled;

	@Column(name="fullname", length=45)
	private String fullName;

	@Column(length=250)
	private String password;

	@Column(length=45)
	private String projectrole;

	@Column(length=45)
	private String role;
	
	@Column(length=250)
	private String email;
	
	@Temporal(TemporalType.DATE)
	private Date dateofjoining;
	
	@Column(length=45)
	private String nickname;
	
	@Column(length=45)
	private String reportto;



	public UserInfo() {
	}

	public String getEnabled() {
		return this.enabled;
	}

	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}

	public String getFullName() {
		return this.fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getProjectrole() {
		return this.projectrole;
	}

	public void setProjectrole(String projectrole) {
		this.projectrole = projectrole;
	}

	public String getRole() {
		return this.role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getDateofjoining() {
		return dateofjoining;
	}

	public void setDateofjoining(Date dateofjoining) {
		this.dateofjoining = dateofjoining;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getReportto() {
		return reportto;
	}

	public void setReportto(String reportto) {
		this.reportto = reportto;
	}

	
}