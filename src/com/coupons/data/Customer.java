package com.coupons.data;

import java.util.ArrayList;
import java.util.Collection;


/*
 * This class contains details of a customer in the system
 */
public class Customer {
	private final long id;
	private String custName;
	private String password;
	private ArrayList<Coupon> coupons;
	
	public Customer(long id) {
		this.id = id; 
		coupons = new ArrayList<Coupon>();
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public long getId() {
		return id;
	}
	
	public void setCoupons(Collection<Coupon> coupons) {
		this.coupons.addAll(coupons);
	}

	public ArrayList<Coupon> getCoupons() {
		return coupons;
	}

	public boolean isCouponInCustomer(Coupon coupon){
		for(Coupon currentCoupon:coupons){
			if(currentCoupon.getId() == coupon.getId())
				return true;
		}
		return false;
	}
	
	@Override
	public String toString() {
		String str = "ID: " + getId() + "\t|Customer name: " + getCustName() + "\t|Password: " + getPassword(); 
		for(Coupon coupon:getCoupons()){
			str+= "\n*" + coupon.toString();
		}
		return str;
	}
	

	
	
	
}
