package com.coupons.dao;


import java.util.Collection;

import com.coupons.data.Company;
import com.coupons.data.Coupon;
import com.coupons.exceptions.ManagerSQLException;
import com.coupons.exceptions.ManagerThreadException;

/*
 * This interface describe the basic functions of Company DAO
 */
public interface CompanyDAO {


	public void createCompany(Company company) throws ManagerSQLException, ManagerThreadException;
	
	public void removeCompany(Company company) throws ManagerSQLException, ManagerThreadException;	
	
	public void updateCompany(Company company) throws ManagerSQLException, ManagerThreadException;
	
	public Company getCompany(long id) throws ManagerSQLException, ManagerThreadException;
	
	public Collection<Company> getAllCompanies() throws ManagerSQLException, ManagerThreadException;
	
	public Collection<Coupon> getCoupons(Company company) throws ManagerSQLException, ManagerThreadException;
	
	public boolean login(String companyName, String password) throws ManagerSQLException, ManagerThreadException;
	
	
	
	
	

}
