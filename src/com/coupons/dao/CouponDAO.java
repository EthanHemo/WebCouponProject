package com.coupons.dao;

import java.util.Collection;

import com.coupons.data.Coupon;
import com.coupons.data.CouponType;
import com.coupons.exceptions.ManagerSQLException;
import com.coupons.exceptions.ManagerThreadException;

/*
 * This interface describe the basic functions of Coupon DAO
 */
public interface CouponDAO {
	
	public void createCoupon(Coupon coupon) throws ManagerSQLException, ManagerThreadException;
	
	public void removeCoupon(Coupon coupon) throws ManagerSQLException, ManagerThreadException;	
	
	public void updateCoupon(Coupon coupon) throws ManagerSQLException, ManagerThreadException;
	
	public Coupon getCoupon(long id) throws ManagerSQLException, ManagerThreadException;
	
	public Collection<Coupon> getAllCoupons() throws ManagerSQLException, ManagerThreadException;
	
	public Collection<Coupon> getCouponsByType(CouponType type) throws ManagerSQLException, ManagerThreadException;
}
