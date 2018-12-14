package com.tcs.ifact.dao;

import java.util.List;

import com.tcs.ifact.model.Util;

public interface IUtilDao {

	List<Util> findByKey(final String key);
	void update(final Util entity);
}
