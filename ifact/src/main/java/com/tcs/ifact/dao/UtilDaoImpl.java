package com.tcs.ifact.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.tcs.ifact.model.Util;

@Transactional
@Repository
public class UtilDaoImpl implements IUtilDao {
	
	private static final Logger logger = LogManager.getLogger(UtilDaoImpl.class);

	@PersistenceContext	
	private EntityManager entityManager;

	@Override
	public List<Util> findByKey(String key) {
		String hql = "FROM Util as u WHERE u.key = :key";
		return entityManager.createQuery(hql).setParameter("key", key).getResultList();
	}

	@Override
	public void update(Util entity) {
		entityManager.merge(entity);
		
	}
}
