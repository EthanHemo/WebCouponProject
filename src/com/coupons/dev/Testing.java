package com.coupons.dev;

//import java.sql.SQLException;
//import java.util.ArrayList;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import com.coupons.client.AdminFacade;
import com.coupons.client.CompanyFacade;
import com.coupons.client.CouponClientFacade;
import com.coupons.client.CustomerFacade;
import com.coupons.data.Company;
import com.coupons.data.Coupon;
import com.coupons.data.CouponType;
import com.coupons.data.Customer;
import com.coupons.dbdao.CouponDBDAO;
import com.coupons.exceptions.CouponException;
import com.coupons.exceptions.ManagerSQLException;
import com.coupons.exceptions.ManagerThreadException;
import com.coupons.exceptions.UserNotFoundException;
import com.coupons.mainsystem.CouponSystem;

public class Testing {
	private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

	// This function display a list of companies
	private static void printListCompanies(Collection<Company> list) {
		for (Company c : list) {
			System.out.println(c.toString());
		}
	}

	// This function display a list of companies
	private static void printListCustomers(Collection<Customer> list) {
		for (Customer c : list) {
			System.out.println(c.toString());
		}
	}

	// This function check AdminFacade functionality
	private static void checkAdminFacade() {
		try {
			CouponSystem sc = CouponSystem.getInstance();
			CouponClientFacade client = sc.login("admin", "1234", CouponSystem.ADMIN);
			AdminFacade admin = (AdminFacade) client;

			System.out.println("\n************ Start company check **************");
			printListCompanies(admin.getAllCompanies());

			// Check company functionality
			// Check create company
			Company company = new Company(0);
			company.setCompanyName("Cisco1");
			company.setPassword("123456");
			company.setEmail("a@cisco.com");

			admin.createCompany(company);

			System.out.println("\n************ After create Company **************");
			printListCompanies(admin.getAllCompanies());

			// Check update company
			Collection<Company> companies = admin.getAllCompanies();
			// Get last company
			Iterator<Company> iterator = companies.iterator();
			while (iterator.hasNext())
				company = iterator.next();

			company.setEmail("some@test");
			admin.updateCompany(company);

			System.out.println("\n************ After update Company " + company.getId() + "**************");
			printListCompanies(admin.getAllCompanies());

			// Check remove company
			companies = admin.getAllCompanies();
			iterator = companies.iterator();
			while (iterator.hasNext())
				company = iterator.next();

			admin.removeCompany(company);
			System.out.println("\n************ After remove Company " + company.getId() + "**************");
			printListCompanies(admin.getAllCompanies());

			// Check company functionality
			System.out.println("\n************ Start customer check **************");
			printListCustomers(admin.getAllCustomer());

			// Check create customer
			Customer customer = new Customer(0);
			customer.setCustName("Bill Gates2");
			customer.setPassword("123456");
			admin.createCustomer(customer);

			System.out.println("\n************ After create Customer **************");
			printListCustomers(admin.getAllCustomer());

			// Check customer update
			Collection<Customer> customers = admin.getAllCustomer();
			Iterator<Customer> iteratorCust = customers.iterator();
			while (iteratorCust.hasNext())
				customer = iteratorCust.next();

			customer.setPassword("*******");
			admin.updateCustomer(customer);

			System.out.println("\n************ After update Customer **************");
			printListCustomers(admin.getAllCustomer());

			// Check remove customer
			customers = admin.getAllCustomer();
			iteratorCust = customers.iterator();
			while (iteratorCust.hasNext())
				customer = iteratorCust.next();

			admin.removeCustomer(customer);

			System.out.println("\n************ After remove Customer **************");
			printListCustomers(admin.getAllCustomer());

		} catch (UserNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ManagerSQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ManagerThreadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// This function chrck CompanyFacade functionality
	private static void checkCompanyFacade() {
		try {
			CouponSystem sc = CouponSystem.getInstance();
			CouponClientFacade client = sc.login("Matrix", "Matrix", CouponSystem.COMPANY);
			CompanyFacade company = (CompanyFacade) client;

			System.out.println("\n************ Start Company Facade check **************");
			System.out.println(company.toString());

			// Check create a company
			DateFormat df = new SimpleDateFormat(DATE_FORMAT);
			Date startDate = df.parse("2017-01-24 00:00:00");
			Date endDate = df.parse("2017-02-24 00:00:00");

			Coupon coupon = new Coupon(0);
			coupon.setTitle("Pokemon GO Exploring");
			coupon.setAmount(30);
			coupon.setEndDate(endDate);
			coupon.setStartDate(startDate);
			coupon.setImage("Pikatchu.png");
			coupon.setMessage("Explore Pokemon GO");
			coupon.setPrice(2752.17);
			coupon.setType(CouponType.SPORTS);
			company.createCoupon(coupon);

			System.out.println("\n************ After create coupon **************");
			System.out.println(company.toString());

			// Check update a company
			Collection<Coupon> coupons = company.getAllCoupon();
			Iterator<Coupon> iterator = coupons.iterator();
			while (iterator.hasNext()) {
				coupon = iterator.next();
			}

			coupon.setPrice(88858.4);
			company.updateCoupon(coupon);

			System.out.println("\n************ After update coupon " + coupon.getId() + "**************");
			System.out.println(company.toString());

			coupons = company.getAllCoupon();
			iterator = coupons.iterator();
			while (iterator.hasNext()) {
				coupon = iterator.next();
			}

			company.removeCoupon(coupon);
			System.out.println("\n************ After Delete coupon " + coupon.getId() + "**************");
			System.out.println(company.toString());

		} catch (UserNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CouponException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ManagerSQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ManagerThreadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// This function chrck CustomerFacade functionality
	private static void checkCustomerFacade() {

		try {
			CouponDBDAO couponDB = new CouponDBDAO();
			CouponSystem sc = CouponSystem.getInstance();
			CouponClientFacade client = sc.login("ethanh", "123456", CouponSystem.CUSTOMER);
			CustomerFacade customer = (CustomerFacade) client;

			System.out.println("\n************ Start Customer Facade check **************");
			System.out.println(customer.toString());

			Coupon coupon = new Coupon();
			Collection<Coupon> coupons = couponDB.getAllCoupons();
			Iterator<Coupon> iteratorComp = coupons.iterator();
			while (iteratorComp.hasNext())
				coupon = iteratorComp.next();

			customer.purchaseCoupon(coupon);

			System.out.println("\n************ After purchase coupon id 12**************");
			System.out.println(customer.toString());

		} catch (UserNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ManagerSQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ManagerThreadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CouponException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// This function check ExpiredCouponDeamon
	public static void checkThreadActivity() {
		try {
			CouponSystem sc = CouponSystem.getInstance();
			Thread.sleep(300000);
		} catch (ManagerSQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void testConnectionPoolThreading() {
		PoolThread t1 = new PoolThread();
		PoolThread t2 = new PoolThread();
		PoolThread t3 = new PoolThread();
		PoolThread t4 = new PoolThread();
		PoolThread t5 = new PoolThread();
		t1.start();
		t2.start();
		t3.start();
		t4.start();
		t5.start();

	}

	public static void main(String[] args) {

		 checkAdminFacade();
		 //checkCompanyFacade();
		 //checkCustomerFacade();
		 //checkThreadActivity();
		 //testConnectionPoolThreading();

		
	}

}
