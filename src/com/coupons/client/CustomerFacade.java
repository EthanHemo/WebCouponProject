package com.coupons.client;

import java.util.Collection;

import com.coupons.data.Coupon;
import com.coupons.data.CouponType;
import com.coupons.data.Customer;
import com.coupons.dbdao.CouponDBDAO;
import com.coupons.dbdao.CustomerDBDAO;
import com.coupons.exceptions.CouponException;
import com.coupons.exceptions.ManagerSQLException;
import com.coupons.exceptions.ManagerThreadException;

/*
 *  Customer Facade manage requests of a customer in the system.
 *  The class need a customer to be initialized.
 * 
 */
public class CustomerFacade implements CouponClientFacade {

	private CustomerDBDAO customerDB;
	private CouponDBDAO couponDB;
	private final Customer customer;

	public CustomerFacade(Customer customer) throws ManagerSQLException {
		this.customer = customer;
		customerDB = new CustomerDBDAO();
		couponDB = new CouponDBDAO();
	}

	/**
	 * This functions associate the coupon to the customer logged in. if there
	 * no coupon available or the coupon already belong to the customer, there
	 * would be a CouponException.
	 * 
	 * @param coupon
	 * @throws ManagerThreadException
	 * @throws ManagerSQLException
	 * @throws CouponException
	 */
	public void purchaseCoupon(Coupon coupon) throws ManagerThreadException, ManagerSQLException, CouponException {

		if (coupon.getAmount() > 0 && !couponDB.isCouponBelongToCustomer(customer, coupon)) {
			customerDB.purchaseCoupon(customer, coupon);
			coupon.setAmount(coupon.getAmount() - 1);
			couponDB.updateAmountCoupon(coupon);
			customer.setCoupons(customerDB.getCoupons(customer));

		}else{
			throw new CouponException("Error purchusing coupon");
		}
		

	}

	/**
	 * This function returns all coupon the user logged in purchased.
	 * @return Collection<Coupon>
	 */
	public Collection<Coupon> getAllPurchasedCoupons() {
		return customer.getCoupons();

	}
	
	public Collection<Coupon> getAllAvailableCoupons() throws ManagerThreadException, ManagerSQLException{
		return couponDB.getAllAvailableCoupons(customer);
	}

	/**
	 * This function returns all coupon the user logged in purchased of the type given.
	 * @param type
	 * @return Collection<Coupon>
	 * @throws ManagerSQLException
	 * @throws ManagerThreadException
	 */
	public Collection<Coupon> getAllPurchasedCouponsByType(CouponType type)
			throws ManagerSQLException, ManagerThreadException {
		return couponDB.getCouponsByType(customer, type);

	}

	/**
	 * This function returns all coupon the user logged in purchased of the price given
	 * @param price
	 * @return
	 * @throws ManagerSQLException
	 * @throws ManagerThreadException
	 */
	public Collection<Coupon> getAllPurchasedCouponsUntilPrice(float price)
			throws ManagerSQLException, ManagerThreadException {
		return couponDB.getCouponsOfCustomerUntilPrice(customer, price);

	}

	/**
	 * When toString is called, the details of the customer logged in will returns.
	 * other details aren't relevant. 
	 */
	@Override
	public String toString() {
		return customer.toString();
	}

	public Customer getCustomer() {
		return customer;
	}

}
