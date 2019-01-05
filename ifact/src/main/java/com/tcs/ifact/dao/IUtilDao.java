package com.tcs.ifact.dao;

import java.util.List;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;

import com.tcs.ifact.model.Util;

@CacheConfig(cacheNames = "util")
public interface IUtilDao {

	@Cacheable
	List<Util> findByKey(final String key);
	
	void update(final Util entity);
	void create(final Util entity);
	List<Util> findAll();
	Util findById(final int utilId);
}
