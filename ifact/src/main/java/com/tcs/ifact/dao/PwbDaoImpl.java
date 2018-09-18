package com.tcs.ifact.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.tcs.ifact.model.Pwb;

@Transactional
@Repository
public class PwbDaoImpl implements IPwbDao {
	
	@PersistenceContext	
	private EntityManager entityManager;

	@Override
	public Pwb findById(String empID, String workerID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Pwb findByDP(String dp) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Pwb findByDM(String dm) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Pwb> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void create(Pwb entity) {
		entityManager.persist(entity);

	}

	@Override
	public void update(Pwb entity) {
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub

	}

}
