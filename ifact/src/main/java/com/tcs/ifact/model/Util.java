package com.tcs.ifact.model;

import java.io.Serializable;
import javax.persistence.*;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;


/**
 * The persistent class for the util database table.
 * 
 */
@Entity
//@EnableAutoConfiguration
@Table(name="util")
@NamedQuery(name="Util.findAll", query="SELECT u FROM Util u")
public class Util implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private int utilId;

	@Column(length=45)
	private String utilgroup;

	@Column(length=45)
	private String utilname;

	@Column(length=45)
	private String utilvalue;

	public Util() {
	}

	public int getUtilId() {
		return this.utilId;
	}

	public void setUtilId(int utilId) {
		this.utilId = utilId;
	}

	public String getUtilgroup() {
		return this.utilgroup;
	}

	public void setUtilgroup(String utilgroup) {
		this.utilgroup = utilgroup;
	}

	public String getUtilname() {
		return this.utilname;
	}

	public void setUtilname(String utilname) {
		this.utilname = utilname;
	}

	public String getUtilvalue() {
		return this.utilvalue;
	}

	public void setUtilvalue(String utilvalue) {
		this.utilvalue = utilvalue;
	}

}