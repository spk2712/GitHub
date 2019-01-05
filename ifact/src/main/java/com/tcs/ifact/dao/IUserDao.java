package com.tcs.ifact.dao;

import java.util.Date;
import java.util.List;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;

import com.tcs.ifact.model.UserInfo;

//@CacheConfig(cacheNames = "util")
public interface IUserDao {

	//@Cacheable
	UserInfo findById(final String User);

	List<UserInfo> findByRole(final String role);

	List<UserInfo> findByProject(final String project);

	List<UserInfo> findAll();

	void create(final UserInfo entity);

	void update(final UserInfo entity);

	void delete(final UserInfo entity);

	void deleteById(final String User);

	void deleteAll();

	UserInfo getActiveUser(String userName);

	List<UserInfo> findByEmail(String email);

	List<UserInfo> getForPF(String user, String email, Date date);
}
