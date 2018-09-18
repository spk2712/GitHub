package com.tcs.ifact.dao;

import java.util.List;

import com.tcs.ifact.model.Pwb;

public interface IPwbDao {
	
	   Pwb findById(final String empID,final String workerID);
	   
	   Pwb findByDP(final String dp);
	   
	   Pwb findByDM(final String dm);
	   
	   List<Pwb> findAll();
	 
	   void create(final Pwb entity);
	 
	   void update(final Pwb entity);
	 
	   void delete(final Pwb entity);
	 
	   void deleteById(final String empID,final String workerID);
	   
	   void deleteAll();

}
