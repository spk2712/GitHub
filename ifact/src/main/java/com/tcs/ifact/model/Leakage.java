package com.tcs.ifact.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the leakage database table.
 * 
 */
@Entity
@Table(name="leakage")
@NamedQuery(name="Leakage.findAll", query="SELECT l FROM Leakage l")
public class Leakage implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(unique=true, nullable=false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int leakageID;

	@Temporal(TemporalType.DATE)
	private Date lastUpdatedDate;

	@Column(length=45)
	private String lastUpdatedUser;

	@Column(length=45)
	private String leakageHrs;

	@Column(length=45)
	private String leakageReason;

	@Column(length=45)
	private String leakageRevenue;

	//bi-directional many-to-one association to Pwb
	@ManyToOne
	@JoinColumns({
		@JoinColumn(name="EmpID", referencedColumnName="EmpID", nullable=false),
		@JoinColumn(name="WorkerID", referencedColumnName="WorkerID", nullable=false)
		})
	private Pwb pwb;

	public Leakage() {
	}

	public int getLeakageID() {
		return this.leakageID;
	}

	public void setLeakageID(int leakageID) {
		this.leakageID = leakageID;
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

	public String getLeakageHrs() {
		return this.leakageHrs;
	}

	public void setLeakageHrs(String leakageHrs) {
		this.leakageHrs = leakageHrs;
	}

	public String getLeakageReason() {
		return this.leakageReason;
	}

	public void setLeakageReason(String leakageReason) {
		this.leakageReason = leakageReason;
	}

	public String getLeakageRevenue() {
		return this.leakageRevenue;
	}

	public void setLeakageRevenue(String leakageRevenue) {
		this.leakageRevenue = leakageRevenue;
	}

	public Pwb getPwb() {
		return this.pwb;
	}

	public void setPwb(Pwb pwb) {
		this.pwb = pwb;
	}

}