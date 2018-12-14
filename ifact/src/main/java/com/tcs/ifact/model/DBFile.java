package com.tcs.ifact.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.math.BigInteger;


/**
 * The persistent class for the files database table.
 * 
 */
@Entity
@Table(name="files")
@NamedQuery(name="DBFile.findAll", query="SELECT d FROM DBFile d")
public class DBFile implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(unique=true, nullable=false, length=45)
	private String user;

	@Lob
	private byte[] data;

	@Column(length=45)
	private String filename;

	@Column(length=45)
	private String filetype;

	@Temporal(TemporalType.DATE)
	private Date lastUpdatedDate;

	@Column(length=45)
	private String lastUpdatedUser;

	public DBFile(String user, String fileName, String filetype, byte[] bs) {
		this.user = user;
		this.filename = fileName;
		this.filetype = filetype;
		this.data = bs;

	}

	public DBFile() {

	}

	public String getUser() {
		return this.user;
	}

	public void setUser(String user) {
		this.user = user;
	}


	public byte[] getData() {
		return this.data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}


	public String getFilename() {
		return this.filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getFiletype() {
		return this.filetype;
	}

	public void setFiletype(String filetype) {
		this.filetype = filetype;
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


}