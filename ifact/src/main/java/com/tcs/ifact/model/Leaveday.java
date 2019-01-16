package com.tcs.ifact.model;

import java.io.Serializable;
import javax.persistence.*;

//import org.springframework.boot.autoconfigure.EnableAutoConfiguration;


/**
 * The persistent class for the leave database table.
 * 
 */
@Entity
//@EnableAutoConfiguration
@Table(name="leaveday")
@NamedQuery(name="Leaveday.findAll", query="SELECT l FROM Leaveday l")
public class Leaveday implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private int leavedayID;

	@Column(length=45)
	private String leavecity;

	@Column(length=45)
	private String leavecountry;

	@Column(length=45)
	private String leavemonth;

	@Column(length=45)
	private String numberofleavedays;

	@Column(length=45)
	private String leaveproject;

	@Column(length=45)
	private String leaveyear;

	public Leaveday() {
	}

	public int getLeavedayID() {
		return leavedayID;
	}

	public void setLeavedayID(int leavedayID) {
		this.leavedayID = leavedayID;
	}

	public String getLeavecity() {
		return leavecity;
	}

	public void setLeavecity(String leavecity) {
		this.leavecity = leavecity;
	}

	public String getLeavecountry() {
		return leavecountry;
	}

	public void setLeavecountry(String leavecountry) {
		this.leavecountry = leavecountry;
	}

	public String getLeavemonth() {
		return leavemonth;
	}

	public void setLeavemonth(String leavemonth) {
		this.leavemonth = leavemonth;
	}

	public String getNumberofleavedays() {
		return numberofleavedays;
	}

	public void setNumberofleavedays(String numberofleavedays) {
		this.numberofleavedays = numberofleavedays;
	}

	public String getLeaveproject() {
		return leaveproject;
	}

	public void setLeaveproject(String leaveproject) {
		this.leaveproject = leaveproject;
	}

	public String getLeaveyear() {
		return leaveyear;
	}

	public void setLeaveyear(String leaveyear) {
		this.leaveyear = leaveyear;
	}

	

}