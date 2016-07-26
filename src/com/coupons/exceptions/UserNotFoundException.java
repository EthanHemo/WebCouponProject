package com.coupons.exceptions;

/*
 * UserNotFoundException express exceptions regarding User not found
 */
public class UserNotFoundException extends Exception {

	private static final long serialVersionUID = 6033069562526966810L;
	
	public UserNotFoundException() {
		super();
	}
	
	public UserNotFoundException(String msg) {
		super(msg);
	}

}
