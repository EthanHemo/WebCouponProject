package com.coupons.data;

import java.util.ArrayList;
import java.util.Collection;

/*
 * This class contains details of a company in the system
 */
public class Company {
	
	private final long id;
	private String email;
	private String companyName;
	private String password;
	private Collection<Coupon> coupons;

	public Company(long id) {
		this.id = id;
		coupons = new ArrayList<>();
	}

	public long getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Collection<Coupon> getCoupons() {
		return coupons;
	}

	public void setCoupons(Collection<Coupon> coupons) {
		this.coupons =coupons;
	}
	
	public void addCoupon(Coupon coupon){
		this.coupons.add(coupon);
	}
	
	public void removeCoupon(Coupon coupon){
		this.coupons.remove(coupon);
	}
	
	@Override
	public String toString() {
		String str = "ID: " + getId() + "\t|Company name: " + getCompanyName() + "\t|Password: " + getPassword() + "\t|Email: " + getEmail();
		for(Coupon coupon:getCoupons()){
			str+= "\n*" + coupon.toString();
		}
		return str;
	}
	
	
}
