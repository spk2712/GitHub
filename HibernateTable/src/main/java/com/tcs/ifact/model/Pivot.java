package com.tcs.ifact.model;

import java.io.Serializable;
import javax.persistence.*;

//import org.springframework.boot.autoconfigure.EnableAutoConfiguration;


/**
 * The persistent class for the pivot database table.
 * 
 */
@Entity
//@EnableAutoConfiguration
@Table(name="pivot")
@NamedQuery(name="Pivot.findAll", query="SELECT p FROM Pivot p")
public class Pivot implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private int pivotID;

	@Column(length=45)
	private String cp;

	@Column(length=45)
	private String dm;

	@Column(length=45)
	private String dp;

	@Column(length=45)
	private String month1ActRev;

	@Column(length=45)
	private String month1ActRevINR;

	@Column(length=45)
	private String month1DiffRev;

	@Column(length=45)
	private String month1DiffRevINR;

	@Column(length=45)
	private String month1LeaveRev;

	@Column(length=45)
	private String month1LeaveRevINR;

	@Column(length=45)
	private String month1MovmtRev;

	@Column(length=45)
	private String month1MovmtRevINR;

	@Column(length=45)
	private String month1OthersRev;

	@Column(length=45)
	private String month1OthersRevINR;

	@Column(length=45)
	private String month1ParkingRev;

	@Column(length=45)
	private String month1ParkingRevINR;

	@Column(length=45)
	private String month1RDwoReplRev;

	@Column(length=45)
	private String month1RDwoReplRevINR;

	@Column(length=45)
	private String month1RDwReplRev;

	@Column(length=45)
	private String month1RDwReplRevINR;

	@Column(length=45)
	private String month2ActRev;

	@Column(length=45)
	private String month2ActRevINR;

	@Column(length=45)
	private String month2DiffRev;

	@Column(length=45)
	private String month2DiffRevINR;

	@Column(length=45)
	private String month2LeaveRev;

	@Column(length=45)
	private String month2LeaveRevINR;

	@Column(length=45)
	private String month2MovmtRev;

	@Column(length=45)
	private String month2MovmtRevINR;

	@Column(length=45)
	private String month2OthersRev;

	@Column(length=45)
	private String month2OthersRevINR;

	@Column(length=45)
	private String month2ParkingRev;

	@Column(length=45)
	private String month2ParkingRevINR;

	@Column(length=45)
	private String month2RDwoReplRev;

	@Column(length=45)
	private String month2RDwoReplRevINR;

	@Column(length=45)
	private String month2RDwReplRev;

	@Column(length=45)
	private String month2RDwReplRevINR;

	@Column(length=45)
	private String month3ActRev;

	@Column(length=45)
	private String month3ActRevINR;

	@Column(length=45)
	private String month3DiffRev;

	@Column(length=45)
	private String month3DiffRevINR;

	@Column(length=45)
	private String month3LeaveRev;

	@Column(length=45)
	private String month3LeaveRevINR;

	@Column(length=45)
	private String month3MovmtRev;

	@Column(length=45)
	private String month3MovmtRevINR;

	@Column(length=45)
	private String month3OthersRev;

	@Column(length=45)
	private String month3OthersRevINR;

	@Column(length=45)
	private String month3ParkingRev;

	@Column(length=45)
	private String month3ParkingRevINR;

	@Column(length=45)
	private String month3RDwoReplRev;

	@Column(length=45)
	private String month3RDwoReplRevINR;

	@Column(length=45)
	private String month3RDwReplRev;

	@Column(length=45)
	private String month3RDwReplRevINR;

	@Column(length=45)
	private String quaterTotalActRev;

	@Column(length=45)
	private String quaterTotalActRevINR;

	@Column(length=45)
	private String quaterTotalDiffRev;

	@Column(length=45)
	private String quaterTotalDiffRevINR;
	
	@Column(length=45)
	private String week;

	public Pivot() {
	}

	public int getPivotID() {
		return this.pivotID;
	}

	public void setPivotID(int pivotID) {
		this.pivotID = pivotID;
	}

	public String getCp() {
		return this.cp;
	}

	public void setCp(String cp) {
		this.cp = cp;
	}

	public String getDm() {
		return this.dm;
	}

	public void setDm(String dm) {
		this.dm = dm;
	}

	public String getDp() {
		return this.dp;
	}

	public void setDp(String dp) {
		this.dp = dp;
	}

	public String getMonth1ActRev() {
		return this.month1ActRev;
	}

	public void setMonth1ActRev(String month1ActRev) {
		this.month1ActRev = month1ActRev;
	}

	public String getMonth1ActRevINR() {
		return this.month1ActRevINR;
	}

	public void setMonth1ActRevINR(String month1ActRevINR) {
		this.month1ActRevINR = month1ActRevINR;
	}

	public String getMonth1DiffRev() {
		return this.month1DiffRev;
	}

	public void setMonth1DiffRev(String month1DiffRev) {
		this.month1DiffRev = month1DiffRev;
	}

	public String getMonth1DiffRevINR() {
		return this.month1DiffRevINR;
	}

	public void setMonth1DiffRevINR(String month1DiffRevINR) {
		this.month1DiffRevINR = month1DiffRevINR;
	}

	public String getMonth1LeaveRev() {
		return this.month1LeaveRev;
	}

	public void setMonth1LeaveRev(String month1LeaveRev) {
		this.month1LeaveRev = month1LeaveRev;
	}

	public String getMonth1LeaveRevINR() {
		return this.month1LeaveRevINR;
	}

	public void setMonth1LeaveRevINR(String month1LeaveRevINR) {
		this.month1LeaveRevINR = month1LeaveRevINR;
	}

	public String getMonth1MovmtRev() {
		return this.month1MovmtRev;
	}

	public void setMonth1MovmtRev(String month1MovmtRev) {
		this.month1MovmtRev = month1MovmtRev;
	}

	public String getMonth1MovmtRevINR() {
		return this.month1MovmtRevINR;
	}

	public void setMonth1MovmtRevINR(String month1MovmtRevINR) {
		this.month1MovmtRevINR = month1MovmtRevINR;
	}

	public String getMonth1OthersRev() {
		return this.month1OthersRev;
	}

	public void setMonth1OthersRev(String month1OthersRev) {
		this.month1OthersRev = month1OthersRev;
	}

	public String getMonth1OthersRevINR() {
		return this.month1OthersRevINR;
	}

	public void setMonth1OthersRevINR(String month1OthersRevINR) {
		this.month1OthersRevINR = month1OthersRevINR;
	}

	public String getMonth1ParkingRev() {
		return this.month1ParkingRev;
	}

	public void setMonth1ParkingRev(String month1ParkingRev) {
		this.month1ParkingRev = month1ParkingRev;
	}

	public String getMonth1ParkingRevINR() {
		return this.month1ParkingRevINR;
	}

	public void setMonth1ParkingRevINR(String month1ParkingRevINR) {
		this.month1ParkingRevINR = month1ParkingRevINR;
	}

	public String getMonth1RDwoReplRev() {
		return this.month1RDwoReplRev;
	}

	public void setMonth1RDwoReplRev(String month1RDwoReplRev) {
		this.month1RDwoReplRev = month1RDwoReplRev;
	}

	public String getMonth1RDwoReplRevINR() {
		return this.month1RDwoReplRevINR;
	}

	public void setMonth1RDwoReplRevINR(String month1RDwoReplRevINR) {
		this.month1RDwoReplRevINR = month1RDwoReplRevINR;
	}

	public String getMonth1RDwReplRev() {
		return this.month1RDwReplRev;
	}

	public void setMonth1RDwReplRev(String month1RDwReplRev) {
		this.month1RDwReplRev = month1RDwReplRev;
	}

	public String getMonth1RDwReplRevINR() {
		return this.month1RDwReplRevINR;
	}

	public void setMonth1RDwReplRevINR(String month1RDwReplRevINR) {
		this.month1RDwReplRevINR = month1RDwReplRevINR;
	}

	public String getMonth2ActRev() {
		return this.month2ActRev;
	}

	public void setMonth2ActRev(String month2ActRev) {
		this.month2ActRev = month2ActRev;
	}

	public String getMonth2ActRevINR() {
		return this.month2ActRevINR;
	}

	public void setMonth2ActRevINR(String month2ActRevINR) {
		this.month2ActRevINR = month2ActRevINR;
	}

	public String getMonth2DiffRev() {
		return this.month2DiffRev;
	}

	public void setMonth2DiffRev(String month2DiffRev) {
		this.month2DiffRev = month2DiffRev;
	}

	public String getMonth2DiffRevINR() {
		return this.month2DiffRevINR;
	}

	public void setMonth2DiffRevINR(String month2DiffRevINR) {
		this.month2DiffRevINR = month2DiffRevINR;
	}

	public String getMonth2LeaveRev() {
		return this.month2LeaveRev;
	}

	public void setMonth2LeaveRev(String month2LeaveRev) {
		this.month2LeaveRev = month2LeaveRev;
	}

	public String getMonth2LeaveRevINR() {
		return this.month2LeaveRevINR;
	}

	public void setMonth2LeaveRevINR(String month2LeaveRevINR) {
		this.month2LeaveRevINR = month2LeaveRevINR;
	}

	public String getMonth2MovmtRev() {
		return this.month2MovmtRev;
	}

	public void setMonth2MovmtRev(String month2MovmtRev) {
		this.month2MovmtRev = month2MovmtRev;
	}

	public String getMonth2MovmtRevINR() {
		return this.month2MovmtRevINR;
	}

	public void setMonth2MovmtRevINR(String month2MovmtRevINR) {
		this.month2MovmtRevINR = month2MovmtRevINR;
	}

	public String getMonth2OthersRev() {
		return this.month2OthersRev;
	}

	public void setMonth2OthersRev(String month2OthersRev) {
		this.month2OthersRev = month2OthersRev;
	}

	public String getMonth2OthersRevINR() {
		return this.month2OthersRevINR;
	}

	public void setMonth2OthersRevINR(String month2OthersRevINR) {
		this.month2OthersRevINR = month2OthersRevINR;
	}

	public String getMonth2ParkingRev() {
		return this.month2ParkingRev;
	}

	public void setMonth2ParkingRev(String month2ParkingRev) {
		this.month2ParkingRev = month2ParkingRev;
	}

	public String getMonth2ParkingRevINR() {
		return this.month2ParkingRevINR;
	}

	public void setMonth2ParkingRevINR(String month2ParkingRevINR) {
		this.month2ParkingRevINR = month2ParkingRevINR;
	}

	public String getMonth2RDwoReplRev() {
		return this.month2RDwoReplRev;
	}

	public void setMonth2RDwoReplRev(String month2RDwoReplRev) {
		this.month2RDwoReplRev = month2RDwoReplRev;
	}

	public String getMonth2RDwoReplRevINR() {
		return this.month2RDwoReplRevINR;
	}

	public void setMonth2RDwoReplRevINR(String month2RDwoReplRevINR) {
		this.month2RDwoReplRevINR = month2RDwoReplRevINR;
	}

	public String getMonth2RDwReplRev() {
		return this.month2RDwReplRev;
	}

	public void setMonth2RDwReplRev(String month2RDwReplRev) {
		this.month2RDwReplRev = month2RDwReplRev;
	}

	public String getMonth2RDwReplRevINR() {
		return this.month2RDwReplRevINR;
	}

	public void setMonth2RDwReplRevINR(String month2RDwReplRevINR) {
		this.month2RDwReplRevINR = month2RDwReplRevINR;
	}

	public String getMonth3ActRev() {
		return this.month3ActRev;
	}

	public void setMonth3ActRev(String month3ActRev) {
		this.month3ActRev = month3ActRev;
	}

	public String getMonth3ActRevINR() {
		return this.month3ActRevINR;
	}

	public void setMonth3ActRevINR(String month3ActRevINR) {
		this.month3ActRevINR = month3ActRevINR;
	}

	public String getMonth3DiffRev() {
		return this.month3DiffRev;
	}

	public void setMonth3DiffRev(String month3DiffRev) {
		this.month3DiffRev = month3DiffRev;
	}

	public String getMonth3DiffRevINR() {
		return this.month3DiffRevINR;
	}

	public void setMonth3DiffRevINR(String month3DiffRevINR) {
		this.month3DiffRevINR = month3DiffRevINR;
	}

	public String getMonth3LeaveRev() {
		return this.month3LeaveRev;
	}

	public void setMonth3LeaveRev(String month3LeaveRev) {
		this.month3LeaveRev = month3LeaveRev;
	}

	public String getMonth3LeaveRevINR() {
		return this.month3LeaveRevINR;
	}

	public void setMonth3LeaveRevINR(String month3LeaveRevINR) {
		this.month3LeaveRevINR = month3LeaveRevINR;
	}

	public String getMonth3MovmtRev() {
		return this.month3MovmtRev;
	}

	public void setMonth3MovmtRev(String month3MovmtRev) {
		this.month3MovmtRev = month3MovmtRev;
	}

	public String getMonth3MovmtRevINR() {
		return this.month3MovmtRevINR;
	}

	public void setMonth3MovmtRevINR(String month3MovmtRevINR) {
		this.month3MovmtRevINR = month3MovmtRevINR;
	}

	public String getMonth3OthersRev() {
		return this.month3OthersRev;
	}

	public void setMonth3OthersRev(String month3OthersRev) {
		this.month3OthersRev = month3OthersRev;
	}

	public String getMonth3OthersRevINR() {
		return this.month3OthersRevINR;
	}

	public void setMonth3OthersRevINR(String month3OthersRevINR) {
		this.month3OthersRevINR = month3OthersRevINR;
	}

	public String getMonth3ParkingRev() {
		return this.month3ParkingRev;
	}

	public void setMonth3ParkingRev(String month3ParkingRev) {
		this.month3ParkingRev = month3ParkingRev;
	}

	public String getMonth3ParkingRevINR() {
		return this.month3ParkingRevINR;
	}

	public void setMonth3ParkingRevINR(String month3ParkingRevINR) {
		this.month3ParkingRevINR = month3ParkingRevINR;
	}

	public String getMonth3RDwoReplRev() {
		return this.month3RDwoReplRev;
	}

	public void setMonth3RDwoReplRev(String month3RDwoReplRev) {
		this.month3RDwoReplRev = month3RDwoReplRev;
	}

	public String getMonth3RDwoReplRevINR() {
		return this.month3RDwoReplRevINR;
	}

	public void setMonth3RDwoReplRevINR(String month3RDwoReplRevINR) {
		this.month3RDwoReplRevINR = month3RDwoReplRevINR;
	}

	public String getMonth3RDwReplRev() {
		return this.month3RDwReplRev;
	}

	public void setMonth3RDwReplRev(String month3RDwReplRev) {
		this.month3RDwReplRev = month3RDwReplRev;
	}

	public String getMonth3RDwReplRevINR() {
		return this.month3RDwReplRevINR;
	}

	public void setMonth3RDwReplRevINR(String month3RDwReplRevINR) {
		this.month3RDwReplRevINR = month3RDwReplRevINR;
	}

	public String getQuaterTotalActRev() {
		return this.quaterTotalActRev;
	}

	public void setQuaterTotalActRev(String quaterTotalActRev) {
		this.quaterTotalActRev = quaterTotalActRev;
	}

	public String getQuaterTotalActRevINR() {
		return this.quaterTotalActRevINR;
	}

	public void setQuaterTotalActRevINR(String quaterTotalActRevINR) {
		this.quaterTotalActRevINR = quaterTotalActRevINR;
	}

	public String getQuaterTotalDiffRev() {
		return this.quaterTotalDiffRev;
	}

	public void setQuaterTotalDiffRev(String quaterTotalDiffRev) {
		this.quaterTotalDiffRev = quaterTotalDiffRev;
	}

	public String getQuaterTotalDiffRevINR() {
		return this.quaterTotalDiffRevINR;
	}

	public void setQuaterTotalDiffRevINR(String quaterTotalDiffRevINR) {
		this.quaterTotalDiffRevINR = quaterTotalDiffRevINR;
	}

	public String getWeek() {
		return week;
	}

	public void setWeek(String week) {
		this.week = week;
	}

}