package com.tcs.ifact.dao;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.tcs.ifact.model.UserInfo;

@Transactional
@Repository
public class UserDaoImpl implements IUserDao {

	@PersistenceContext	
	private EntityManager entityManager;
	
	@Override
	public UserInfo findById(String user) {
		return entityManager.find(UserInfo.class, user);
	}

	@Override
	public List<UserInfo> findByRole(String role) {
		String hql = "FROM UserInfo as u WHERE u.role = ?";
		return entityManager.createQuery(hql).setParameter(0, role).getResultList();
	}

	@Override
	public List<UserInfo> findByProject(String project) {
		String hql = "FROM UserInfo as u WHERE u.project = ?";
		return entityManager.createQuery(hql).setParameter(0, project).getResultList();
	}
	
	@Override
	public List<UserInfo> findByEmail(String email) {
		String hql = "FROM UserInfo as u WHERE u.email = ?";
		return entityManager.createQuery(hql).setParameter(0, email).getResultList();
	}
	
	@Override
	public List<UserInfo> findByToken(String token) {
		String hql = "FROM UserInfo as u WHERE u.token = ?";
		return entityManager.createQuery(hql).setParameter(0, token).getResultList();
	}

	@Override
	public List<UserInfo> findAll() {
		return entityManager.createNamedQuery("UserInfo.findAll").getResultList();
	}
	
	@Override
	public UserInfo getActiveUser(String userName) {
		UserInfo activeUser = new UserInfo();
		String enabled = "Y";
		String hql = "FROM UserInfo u WHERE user=? and enabled=?";
		List<?> list = entityManager.createQuery(hql).setParameter(0, userName).setParameter(1, enabled).getResultList();
		if(!list.isEmpty()) {
			activeUser = (UserInfo)list.get(0);
		}
		return activeUser;
	}

	@Override
	public void create(UserInfo entity) {
		entityManager.persist(entity);

	}

	@Override
	public void update(UserInfo entity) {
		entityManager.merge(entity);

	}

	@Override
	public void delete(UserInfo entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteById(String user) {
		entityManager.remove(findById(user));
		/*String hql = "delete from User where user = ?";
		entityManager.createQuery(hql).setParameter(0, user);*/
		

	}

	@Override
	public void deleteAll() {
		String hql = "delete from UserInfo";
		entityManager.createQuery(hql).executeUpdate();
	}

	@Override
	public List<UserInfo> getForPF(String user, String email, Date dateOfJoining) {
		String hql = "FROM UserInfo as u WHERE u.user = ? and u.email=? and dateofjoining=?";
		return entityManager.createQuery(hql).setParameter(0, user).setParameter(1, email).setParameter(2, dateOfJoining).getResultList();
	}

}
