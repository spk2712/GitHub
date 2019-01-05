package com.tcs.ifact.model;

import java.io.Serializable;
import javax.persistence.*;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;


/**
 * The persistent class for the leave database table.
 * 
 */
@Entity
//@EnableAutoConfiguration
@Table(name="leave")
@NamedQuery(name="Leave.findAll", query="SELECT l FROM Leave l")
public class Leave implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private int leaveID;

	@Column(length=45)
	private String city;

	@Column(length=45)
	private String country;

	@Column(length=45)
	private String month;

	@Column(length=45)
	private String numberofleavedays;

	@Column(length=45)
	private String project;

	@Column(length=45)
	private String year;

	public Leave() {
	}

	public int getLeaveID() {
		return this.leaveID;
	}

	public void setLeaveID(int leaveID) {
		this.leaveID = leaveID;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return this.country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getMonth() {
		return this.month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getNumberofleavedays() {
		return this.numberofleavedays;
	}

	public void setNumberofleavedays(String numberofleavedays) {
		this.numberofleavedays = numberofleavedays;
	}

	public String getProject() {
		return this.project;
	}

	public void setProject(String project) {
		this.project = project;
	}

	public String getYear() {
		return this.year;
	}

	public void setYear(String year) {
		this.year = year;
	}

}