package com.tcs.ifact.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the pwb database table.
 * 
 */
@Embeddable
public class PwbPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(unique=true, nullable=false, length=45)
	private String empID;

	@Column(unique=true, nullable=false, length=45)
	private String workerID;

	public PwbPK() {
	}
	public String getEmpID() {
		return this.empID;
	}
	public void setEmpID(String empID) {
		this.empID = empID;
	}
	public String getWorkerID() {
		return this.workerID;
	}
	public void setWorkerID(String workerID) {
		this.workerID = workerID;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof PwbPK)) {
			return false;
		}
		PwbPK castOther = (PwbPK)other;
		return 
			this.empID.equals(castOther.empID)
			&& this.workerID.equals(castOther.workerID);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.empID.hashCode();
		hash = hash * prime + this.workerID.hashCode();
		
		return hash;
	}
}