package com.tcs.ifact.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.tcs.ifact.model.Pivot;

@Transactional
@Repository
public class PivotDaoImpl implements IPivotDao {
	private static final Logger logger = LogManager.getLogger(PivotDaoImpl.class);
	
	@PersistenceContext	
	private EntityManager entityManager;
	
	@Override
	public void create(Pivot entity) {
		entityManager.persist(entity);
	}

	@Override
	public void update(Pivot entity) {
		entityManager.merge(entity);

	}

	@Override
	public void delete(Pivot entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteAll() {
		String hql = "delete from Pivot";
		entityManager.createQuery(hql).executeUpdate();

	}

	@Override
	public List<Pivot> findAll() {
		return entityManager.createNamedQuery("Pivot.findAll").getResultList();
	}

	@Override
	public List<Pivot> findByCP(String cp) {
		String hql = "FROM Pivot as p WHERE p.cp = :cp";
		return entityManager.createQuery(hql).setParameter("cp", cp).getResultList();
	}

	@Override
	public List<Pivot> findByDP(String dp) {
		String hql = "FROM Pivot as p WHERE p.dp = :dp";
		return entityManager.createQuery(hql).setParameter("dp", dp).getResultList();
	}

	@Override
	public List<Pivot> findByDM(String dm) {
		String hql = "FROM Pivot as p WHERE p.dm = :dm";
		return entityManager.createQuery(hql).setParameter("dm", dm).getResultList();
	}

	@Override
	public List<Pivot> findByCPWeek(String cp, String week) {
		String hql = "FROM Pivot as p WHERE p.cp = :cp AND p.week = :week";
		return entityManager.createQuery(hql).setParameter("cp", cp).setParameter("week", week).getResultList();
	}

	@Override
	public List<Pivot> findByDPWeek(String dp, String week) {
		String hql = "FROM Pivot as p WHERE p.dp = :dp AND p.week = :week";
		return entityManager.createQuery(hql).setParameter("dp", dp).setParameter("week", week).getResultList();
	}

	@Override
	public List<Pivot> findByDMWeek(String dm, String week) {
		String hql = "FROM Pivot as p WHERE p.dm = :dm AND p.week = :week";
		return entityManager.createQuery(hql).setParameter("dm", dm).setParameter("week", week).getResultList();
	}

}
