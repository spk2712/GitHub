package com.tcs.ifact.dao;

import java.util.List;

import com.tcs.ifact.model.Pivot;

public interface IPivotDao {

	void create(final Pivot entity);
	void update(final Pivot entity);
	void delete(final Pivot entity);
	void deleteAll();
	List<Pivot> findAll();
	List<Pivot> findByCP(final String cp);
	List<Pivot> findByDP(final String dp);
	List<Pivot> findByDM(final String dm);
	List<Pivot> findByCPWeek(final String cp,final String week);
	List<Pivot> findByDPWeek(final String dp,final String week);
	List<Pivot> findByDMWeek(final String dm,final String week);
}
