package com.tcs.ehCache;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.dom4j.dom.DOMNodeHelper.EmptyNodeList;
import org.hibernate.Cache;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.stat.Statistics;

import com.tcs.ehCache.model.EmpDetail;
import com.tcs.ehCache.sql.HibernateSQL;
import com.tcs.ehCache.util.HibernateUtil;

import net.sf.ehcache.CacheManager;




public class HibernateEHCacheMain {

	public static void main(String[] args) {
		
		System.out.println("Temp Dir:"+System.getProperty("java.io.tmpdir"));
		
		//Initialize Sessions
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Statistics stats = sessionFactory.getStatistics();
		System.out.println("Stats enabled="+stats.isStatisticsEnabled());
		//stats.setStatisticsEnabled(true);
		//System.out.println("Stats enabled="+stats.isStatisticsEnabled());
		
		Session session = sessionFactory.openSession();
		Session otherSession = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		Transaction otherTransaction = otherSession.beginTransaction();
		
		printStats(stats, 0);
		
		
		//EmpDetail emp = (EmpDetail) session.load(EmpDetail.class, 1);
		//printData(emp, stats, 1);
		
		List<EmpDetail> list = HibernateSQL.getEmpDetails(session);
		printData(list, stats, 1);
		
		//emp = (EmpDetail) session.load(EmpDetail.class, 1);
		//printData(emp, stats, 2);
		
		list = HibernateSQL.getEmpDetails(session);
		printData(list, stats, 2);
		
		
		Iterator<EmpDetail> itr = list.iterator();
		while(itr.hasNext()){
			EmpDetail emp = itr.next();
			System.out.println("Session Contains Employee with"+emp.getEmpid()+session.contains(emp));
			session.evict(emp);
			System.out.println("Session Contains Employee with"+emp.getEmpid()+session.contains(emp));
		}
		
		//System.out.println("Session Contains Employee with id=1?"+session.contains(list));
		//clear first level cache, so that second level cache is used
		//session.evict(emp);
		//System.out.println("Session Contains Employee with id=1?"+session.contains(list));
		
		//emp = (EmpDetail) session.load(EmpDetail.class, 1);
		//printData(emp, stats, 3);
		
		list = HibernateSQL.getEmpDetails(session);
		printData(list, stats, 3);
		
		//emp = (EmpDetail) session.load(EmpDetail.class, 1);
		//printData(emp, stats, 4);
		
		list = HibernateSQL.getEmpDetails(session);
		printData(list, stats, 4);
		
		//emp = (EmpDetail) otherSession.load(EmpDetail.class, 1);
		//printData(emp, stats, 5);
		
		list = HibernateSQL.getEmpDetails(otherSession);
		printData(list, stats, 5);
		
		/*Map cacheEntries = sessionFactory.getStatistics()
		        .getSecondLevelCacheStatistics("employee")
		        .getEntries();
		
		for (Object key : cacheEntries.keySet()) {
			System.out.println(key);
		}*/
		
		
		Cache cache = sessionFactory.getCache();
		
		
		if (cache != null /*&& cache.containsQuery("StandardQueryCache")*/ ) {
		   //cache.evictQueryRegion("StandardQueryCache"); 
			cache.evictAllRegions();
		   printStats(stats, 6);
		}
		
		list = HibernateSQL.getEmpDetails(otherSession);
		printData(list, stats, 7);
		
		//Release resources
		transaction.commit();
		otherTransaction.commit();
		sessionFactory.close();
	}

	private static void printStats(Statistics stats, int i) {
		System.out.println("Start***** " + i + " *****");
		System.out.println("Fetch Count="
				+ stats.getEntityFetchCount());
		System.out.println("Second Level Hit Count="
				+ stats.getSecondLevelCacheHitCount());
		System.out
				.println("Second Level Miss Count="
						+ stats
								.getSecondLevelCacheMissCount());
		System.out.println("Second Level Put Count="
				+ stats.getSecondLevelCachePutCount());
		
		String[] strArray = stats.getSecondLevelCacheRegionNames();
		int istrArry = strArray.length;
		String str="";
		for(int j=0;j<istrArry;j++) {
			str+=strArray[j]+" ";
			System.out.println("Second Level Region="+str);  
		}
		
		System.out.println("***** " + i + " *****End");
	
		
	}

	private static void printData(EmpDetail emp, Statistics stats, int count) {
		System.out.println(count+":: First Name="+emp.getFirstname()+", Last Name="+emp.getLastname());
		printStats(stats, count);
	}
	
	private static void printData(List lst, Statistics stats, int count) {
		System.out.println(count+":: List Size="+lst.size());
		printStats(stats, count);
	}

}
