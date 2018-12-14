package com.tcs.ifact.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.tcs.ifact.model.Pwb;
import com.tcs.ifact.model.UserInfo;

@Transactional
@Repository
public class PwbDaoImpl implements IPwbDao {
	
	private static final Logger logger = LogManager.getLogger(PwbDaoImpl.class);
	
	@PersistenceContext	
	private EntityManager entityManager;

	@Override
	public Pwb findById(int pk) {
		return entityManager.find(Pwb.class, pk);
	}

	@Override
	public List<Pwb> findByDP(String dp) {
		String hql = "FROM Pwb as p WHERE p.dp like :dp";
		return entityManager.createQuery(hql).setParameter("dp", "%"+dp+"%").getResultList();
	}

	@Override
	public List<Pwb> findByDM(String dm) {
		String hql = "FROM Pwb as p WHERE p.dm = :dp";
		return entityManager.createQuery(hql).setParameter("dm", dm).getResultList();
	}

	@Override
	public List<Pwb> findAll() {
		return entityManager.createNamedQuery("Pwb.findAll").getResultList();
	}

	@Override
	public void create(Pwb entity) {
		entityManager.persist(entity);

	}

	@Override
	public void update(Pwb entity) {
		entityManager.merge(entity);
	}

	@Override
	public void delete(Pwb entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteById(String empID, String workerID) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteAll() {
		String hql = "delete from pwb";
		entityManager.createQuery(hql).executeUpdate();

	}

	@Override
	public List<Pwb> findByEmpIDAndWorkerID(String empID, String workerID) {
		String hql = "FROM Pwb as p WHERE p.empID = ? AND p.workerID = ?";
		return entityManager.createQuery(hql).setParameter(0, empID).setParameter(1, workerID).getResultList();
	}

	@Override
	public List<Pwb> findByEmpID(String empID) {
		String hql = "FROM Pwb as p WHERE p.empID = ?";
		return entityManager.createQuery(hql).setParameter(0, empID).getResultList();
	}

	@Override
	public List<Pwb> findByWorkerID(String workerId) {
		String hql = "FROM Pwb as p WHERE p.workerID = ?";
		return entityManager.createQuery(hql).setParameter(1, workerId).getResultList();
	}

}
