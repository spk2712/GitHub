package com.tcs.ehCache.sql;

import java.util.List;


import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

public class HibernateSQL {


	public static List getEmpDetails(Session session) throws HibernateException {
		try {
			final Query query = session.createQuery("SELECT e FROM EmpDetail e");
			query.setCacheable(true);
			return query.list();
		}finally {
			//session.close();
		}
	}
}
