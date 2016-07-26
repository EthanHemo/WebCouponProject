package com.coupons.dao;

import java.util.Collection;



import com.coupons.data.Coupon;
import com.coupons.data.Customer;
import com.coupons.exceptions.ManagerSQLException;
import com.coupons.exceptions.ManagerThreadException;

/*
 * This interface describe the basic functions of Customer DAO
 */
public interface CustomerDAO {
	
	public void createCustomer(Customer customer) throws ManagerSQLException, ManagerThreadException;
	
	public void removeCustomer(Customer customer) throws ManagerSQLException, ManagerThreadException;	
	
	public void updateCustomer(Customer customer) throws ManagerSQLException, ManagerThreadException;
	
	public Customer getCustomer(long id) throws ManagerSQLException, ManagerThreadException;
	
	public Collection<Customer> getAllCustomers() throws ManagerSQLException, ManagerThreadException;
	
	public Collection<Coupon> getCoupons(Customer customer) throws ManagerSQLException, ManagerThreadException;
	
	public boolean login(String custName, String password) throws ManagerSQLException, ManagerThreadException;
	
}
