package com.coupons.exceptions;

/*
 * CouponException express exceptions regarding coupons
 */
public class CouponException extends Exception {
	private static final long serialVersionUID = 418724274189088226L;

	public CouponException() {
		super();
	}
	
	public CouponException(String msg) {
		super(msg);
	}
}
