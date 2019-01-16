package com.tcs.ifact.dao.Impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.tcs.ifact.dao.ILeaveDao;
import com.tcs.ifact.model.Leaveday;

@Transactional
@Repository
public class LeaveDaoImpl implements ILeaveDao {
	
	private static final Logger logger = LogManager.getLogger(LeaveDaoImpl.class);

	@PersistenceContext	
	private EntityManager entityManager;

	@Override
	public List<String> getLeaveDays(String leavecity,String leaveyear,String leavemonth) {
		String hql = "select numberofleavedays FROM Leaveday as l WHERE l.leavecity = :leavecity AND l.leaveyear = :leaveyear AND l.leavemonth = :leavemonth";
		return entityManager.createQuery(hql).setParameter("leavecity", leavecity).setParameter("leaveyear", leaveyear).setParameter("leavemonth", leavemonth).getResultList();
	}

	@Override
	public void update(Leaveday entity) {
		entityManager.merge(entity);	
	}
	
	@Override
	public List<Leaveday> findAll() {
		return entityManager.createNamedQuery("Leave.findAll").getResultList();
	}

	@Override
	public void create(Leaveday entity) {
		entityManager.persist(entity);
		
	}

	@Override
	public Leaveday findById(int leaveId) {
		return entityManager.find(Leaveday.class, leaveId);
	}
}
