package com.coupons.exceptions;

/*
 * ManagerSQLException express exceptions regarding SQL
 */
public class ManagerSQLException extends Exception {

	private static final long serialVersionUID = -7035840055397513063L;

	public ManagerSQLException() {
		super();
	}
	
	public ManagerSQLException(String msg) {
		super("Manager SQL error: " + msg);
	}

}
