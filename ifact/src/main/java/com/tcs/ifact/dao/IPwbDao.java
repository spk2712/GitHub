package com.tcs.ifact.dao;

import java.util.List;

import com.tcs.ifact.model.Pwb;

public interface IPwbDao {
	
	   Pwb findById(final int pk);
	   
	   List<Pwb> findByEmpIDAndWorkerID(final String empID,final String workerID);
	   
	   List<Pwb> findByEmpID(final String empID);
	   
	   List<Pwb> findByWorkerID(final String workerId);
	   
	   List<Pwb> findByDP(final String dp);
	   
	   List<Pwb> findByDM(final String dm);
	   
	   List<Pwb> findAll();
	 
	   void create(final Pwb entity);
	 
	   void update(final Pwb entity);
	 
	   void delete(final Pwb entity);
	 
	   void deleteById(final String empID,final String workerID);
	   
	   void deleteAll();

}
