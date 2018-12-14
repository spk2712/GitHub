package com.tcs.ifact.dao;

import java.util.List;

import com.tcs.ifact.model.DBFile;

public interface IDBFileDao {

	List<DBFile> findByFileName(final String filename);
	DBFile findById(final String user);
	void create(final DBFile entity);
	void update(final DBFile entity);
	void delete(final DBFile entity);
	void deleteById(final String user);
	void deleteAll();
	List<DBFile> findByFileNameAndUser(final String user ,final String fileName);
}
