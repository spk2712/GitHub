package com.tcs.ifact.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.tcs.ifact.controller.UserController;
import com.tcs.ifact.model.DBFile;


@Transactional
@Repository
public class DBFileDaoImpl implements IDBFileDao {
	
	private static final Logger logger = LogManager.getLogger(DBFileDaoImpl.class);
	
	@PersistenceContext	
	private EntityManager entityManager;

	@Override
	public List<DBFile> findByFileName(String filename) {
		String hql = "FROM DBFile as d WHERE d.filename = ?";
		return entityManager.createQuery(hql).setParameter(0, filename).getResultList();
	}


	@Override
	public DBFile findById(String user) {
		return entityManager.find(DBFile.class, user);
	}

	@Override
	public void create(DBFile entity) {
		entityManager.persist(entity);

	}

	@Override
	public void update(DBFile entity) {
		entityManager.merge(entity);

	}

	@Override
	public void delete(DBFile entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteById(String user) {
		entityManager.remove(findById(user));

	}

	@Override
	public void deleteAll() {
		String hql = "delete from DBFile";
		entityManager.createQuery(hql).executeUpdate();
	}

	@Override
	public List<DBFile> findByFileNameAndUser(String user ,String fileName) {
		String hql = "FROM DBFile as d WHERE d.filename = ? and d.user = ?";
		return entityManager.createQuery(hql).setParameter(0, user).setParameter(1, fileName).getResultList();
	}

}
