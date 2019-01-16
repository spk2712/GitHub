package com.tcs.ifact.dao;

import java.util.List;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;

import com.tcs.ifact.model.Leaveday;

@CacheConfig(cacheNames = "ifactCache")
public interface ILeaveDao {

	@Cacheable
	List<String> getLeaveDays(final String city,final String year,final String month);
	
	void update(final Leaveday entity);
	void create(final Leaveday entity);
	List<Leaveday> findAll();
	Leaveday findById(final int leaveId);
}
