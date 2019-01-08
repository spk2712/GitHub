package com.tcs.ifact.model;

import java.io.Serializable;
import javax.persistence.*;

//import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the pwb database table.
 * 
 */
@Entity
//@EnableAutoConfiguration
@Table(name="pwb")
@NamedQuery(name="Pwb.findAll", query="SELECT p FROM Pwb p")
public class Pwb implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private int pwbID;

	@Column(length=45)
	private String cp;

	@Column(length=45)
	private String currentBillRate;

	@Column(length=45)
	private String dm;

	@Column(length=45)
	private String dp;

	@Column(nullable=false, length=45)
	private String empID;

	@Column(length=45)
	private String firstName;

	@Column(length=45)
	private String gdceid;

	@Column(length=45)
	private String lastName;

	@Temporal(TemporalType.DATE)
	private Date lastUpdatedDate;

	@Column(length=45)
	private String lastUpdatedUser;

	@Column(length=45)
	private String month1Act;

	@Column(length=45)
	private String month1Diff;

	@Column(length=45)
	private String month1Max;

	@Column(length=45)
	private String month2Act;

	@Column(length=45)
	private String month2Diff;

	@Column(length=45)
	private String month2Max;

	@Column(length=45)
	private String month3Act;

	@Column(length=45)
	private String month3Diff;

	@Column(length=45)
	private String month3Max;

	@Column(length=45)
	private String projectType;

	@Column(length=45)
	private String SOWWorkerRole;

	@Column(length=45)
	private String WONName;

	@Column(length=45)
	private String WONNumber;

	@Column(length=45)
	private String WONType;

	@Column(length=45)
	private String workCountry;

	@Temporal(TemporalType.DATE)
	private Date workerEndDate;

	@Column(nullable=false, length=45)
	private String workerID;

	@Temporal(TemporalType.DATE)
	private Date workerStartDate;

	@Column(length=45)
	private String workLocation;

	//bi-directional many-to-one association to Leakage
	@OneToMany(fetch=FetchType.EAGER,mappedBy="pwb",cascade = CascadeType.ALL)
	private List<Leakage> leakages;

	public Pwb() {
	}

	public int getPwbID() {
		return this.pwbID;
	}

	public void setPwbID(int pwbID) {
		this.pwbID = pwbID;
	}

	public String getCp() {
		return this.cp;
	}

	public void setCp(String cp) {
		this.cp = cp;
	}

	public String getCurrentBillRate() {
		return this.currentBillRate;
	}

	public void setCurrentBillRate(String currentBillRate) {
		this.currentBillRate = currentBillRate;
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

	public String getEmpID() {
		return this.empID;
	}

	public void setEmpID(String empID) {
		this.empID = empID;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getGdceid() {
		return this.gdceid;
	}

	public void setGdceid(String gdceid) {
		this.gdceid = gdceid;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getLastUpdatedDate() {
		return this.lastUpdatedDate;
	}

	public void setLastUpdatedDate(Date lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}

	public String getLastUpdatedUser() {
		return this.lastUpdatedUser;
	}

	public void setLastUpdatedUser(String lastUpdatedUser) {
		this.lastUpdatedUser = lastUpdatedUser;
	}

	public String getMonth1Act() {
		return this.month1Act;
	}

	public void setMonth1Act(String month1Act) {
		this.month1Act = month1Act;
	}

	public String getMonth1Diff() {
		return this.month1Diff;
	}

	public void setMonth1Diff(String month1Diff) {
		this.month1Diff = month1Diff;
	}

	public String getMonth1Max() {
		return this.month1Max;
	}

	public void setMonth1Max(String month1Max) {
		this.month1Max = month1Max;
	}

	public String getMonth2Act() {
		return this.month2Act;
	}

	public void setMonth2Act(String month2Act) {
		this.month2Act = month2Act;
	}

	public String getMonth2Diff() {
		return this.month2Diff;
	}

	public void setMonth2Diff(String month2Diff) {
		this.month2Diff = month2Diff;
	}

	public String getMonth2Max() {
		return this.month2Max;
	}

	public void setMonth2Max(String month2Max) {
		this.month2Max = month2Max;
	}

	public String getMonth3Act() {
		return this.month3Act;
	}

	public void setMonth3Act(String month3Act) {
		this.month3Act = month3Act;
	}

	public String getMonth3Diff() {
		return this.month3Diff;
	}

	public void setMonth3Diff(String month3Diff) {
		this.month3Diff = month3Diff;
	}

	public String getMonth3Max() {
		return this.month3Max;
	}

	public void setMonth3Max(String month3Max) {
		this.month3Max = month3Max;
	}

	public String getProjectType() {
		return this.projectType;
	}

	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}

	public String getSOWWorkerRole() {
		return this.SOWWorkerRole;
	}

	public void setSOWWorkerRole(String SOWWorkerRole) {
		this.SOWWorkerRole = SOWWorkerRole;
	}

	public String getWONName() {
		return this.WONName;
	}

	public void setWONName(String WONName) {
		this.WONName = WONName;
	}

	public String getWONNumber() {
		return this.WONNumber;
	}

	public void setWONNumber(String WONNumber) {
		this.WONNumber = WONNumber;
	}

	public String getWONType() {
		return this.WONType;
	}

	public void setWONType(String WONType) {
		this.WONType = WONType;
	}

	public String getWorkCountry() {
		return this.workCountry;
	}

	public void setWorkCountry(String workCountry) {
		this.workCountry = workCountry;
	}

	public Date getWorkerEndDate() {
		return this.workerEndDate;
	}

	public void setWorkerEndDate(Date workerEndDate) {
		this.workerEndDate = workerEndDate;
	}

	public String getWorkerID() {
		return this.workerID;
	}

	public void setWorkerID(String workerID) {
		this.workerID = workerID;
	}

	public Date getWorkerStartDate() {
		return this.workerStartDate;
	}

	public void setWorkerStartDate(Date workerStartDate) {
		this.workerStartDate = workerStartDate;
	}

	public String getWorkLocation() {
		return this.workLocation;
	}

	public void setWorkLocation(String workLocation) {
		this.workLocation = workLocation;
	}

	public List<Leakage> getLeakages() {
		return this.leakages;
	}

	public void setLeakages(List<Leakage> leakages) {
		this.leakages = leakages;
	}

	public Leakage addLeakage(Leakage leakage) {
		getLeakages().add(leakage);
		leakage.setPwb(this);

		return leakage;
	}

	public Leakage removeLeakage(Leakage leakage) {
		getLeakages().remove(leakage);
		leakage.setPwb(null);

		return leakage;
	}

}