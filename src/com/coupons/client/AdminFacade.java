package com.coupons.client;

import java.util.Collection;

import com.coupons.data.Company;
import com.coupons.data.Customer;
import com.coupons.dbdao.CompanyDBDAO;
import com.coupons.dbdao.CustomerDBDAO;
import com.coupons.exceptions.ManagerSQLException;
import com.coupons.exceptions.ManagerThreadException;
/*
 *  Admin Facade manage the coupon system administrator requests in the system.
 * 
 */
public class AdminFacade implements CouponClientFacade {
	
	private CompanyDBDAO companyDB;
	private CustomerDBDAO customerDB;
	
	public AdminFacade() throws ManagerSQLException {
		companyDB = new CompanyDBDAO();
		customerDB = new CustomerDBDAO();
		
	}
	
	/**
	 * This function takes a company and add it to the database. 
	 * in case the company name already exist there would be an ManagerSQLException.
	 * @param company
	 * @throws ManagerThreadException
	 * @throws ManagerSQLException
	 */
	public void createCompany(Company company) throws ManagerThreadException, ManagerSQLException{
		
		if(!companyDB.isCompanyNameExist(company))
			companyDB.createCompany(company);
		else
			throw new ManagerSQLException("Company already exist");
	
	}
	
	/**
	 * This function remove the given company in the database.
	 * If the company doesn't exits there would be ManagerSQLException.
	 * @param company
	 * @throws ManagerSQLException
	 * @throws ManagerThreadException
	 */
	public void removeCompany(Company company) throws ManagerSQLException, ManagerThreadException{
		companyDB.removeCompany(company);
	}
	
	/**
	 * This company updates a given company's except company name   
	 * @param company
	 * @throws ManagerSQLException
	 * @throws ManagerThreadException
	 */
	public void updateCompany(Company company) throws ManagerSQLException, ManagerThreadException{
		companyDB.updateCompany(company);
	}
	
	/**
	 * This function return the company that belong to the ID that was given
	 * if the id have no company associate, there would be a   ManagerSQLException.
	 * @param id
	 * @return Company
	 * @throws ManagerSQLException
	 * @throws ManagerThreadException
	 */
	public Company getCompany(long id) throws ManagerSQLException, ManagerThreadException{
		return companyDB.getCompany(id);
	}
	
	/**
	 * This function return a collection with all the companies in the system. 
	 * @return Collection<Company> 
	 * @throws ManagerSQLException
	 * @throws ManagerThreadException
	 */
	public Collection<Company> getAllCompanies() throws ManagerSQLException, ManagerThreadException{
		return companyDB.getAllCompanies();
	}
	
	/**
	 * This function create a new customer in the system.
	 * If the customer name already exist there would be a  ManagerSQLException.
	 * @param customer
	 * @throws ManagerSQLException
	 * @throws ManagerThreadException
	 */
	public void createCustomer(Customer customer) throws ManagerSQLException, ManagerThreadException{
		if(!customerDB.isCustomerNameExist(customer))
			customerDB.createCustomer(customer);
		else
			throw new ManagerSQLException("Customer name already exist");
	}
	
	/**
	 * This function removes a customer from the system.
	 * If that user isn't exist there would be a ManagerSQLException
	 * @param customer
	 * @throws ManagerSQLException
	 * @throws ManagerThreadException
	 */
	public void removeCustomer(Customer customer) throws ManagerSQLException, ManagerThreadException{
		customerDB.removeCustomer(customer);
	}

	/**
	 * This function updates a customer password.
	 * @param customer
	 * @throws ManagerSQLException
	 * @throws ManagerThreadException
	 */
	public void updateCustomer(Customer customer) throws ManagerSQLException, ManagerThreadException{
		customerDB.updateCustomer(customer);
	}
	
	/**
	 * This function return a customer that assosiated with the given ID.
	 * @param id
	 * @return Customer
	 * @throws ManagerSQLException
	 * @throws ManagerThreadException
	 */
	public Customer getCustomer(long id) throws ManagerSQLException, ManagerThreadException{
		return customerDB.getCustomer(id);
	}	
	
	/**
	 * This function returns a collection of Customer with all the customers in the system.
	 * @return Collection<Customer>
	 * @throws ManagerSQLException
	 * @throws ManagerThreadException
	 */
	public Collection<Customer> getAllCustomer() throws ManagerSQLException, ManagerThreadException{
		return customerDB.getAllCustomers();
	}
	
}
