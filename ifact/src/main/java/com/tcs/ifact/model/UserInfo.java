package com.tcs.ifact.model;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.Type;

import java.math.BigInteger;
import java.util.Date;


/**
 * The persistent class for the users database table.
 * 
 */
@Entity
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
	private String project;

	@Column(length=45)
	private String role;
	
	@Column(length=250)
	private String email;
	
	@Temporal(TemporalType.DATE)
	private Date dateofjoining;



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

	public String getProject() {
		return this.project;
	}

	public void setProject(String project) {
		this.project = project;
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

	
	/*	@Column(name="TOTAL_CONNECTIONS", nullable=false)
	private BigInteger totalConnections;*/

	/*@Column(length=32)
	private String user;*/
	
	/*@Column(name="CURRENT_CONNECTIONS", nullable=false)
	private BigInteger currentConnections;*/
	
	/*	public int getUsername() {
	return this.username;
}

public void setUsername(int username) {
	this.username = username;
}

public BigInteger getCurrentConnections() {
	return this.currentConnections;
}

public void setCurrentConnections(BigInteger currentConnections) {
	this.currentConnections = currentConnections;
}*/

/*	public BigInteger getTotalConnections() {
		return this.totalConnections;
	}

	public void setTotalConnections(BigInteger totalConnections) {
		this.totalConnections = totalConnections;
	}

	public String getUser() {
		return this.user;
	}

	public void setUser(String user) {
		this.user = user;
	}*/

}