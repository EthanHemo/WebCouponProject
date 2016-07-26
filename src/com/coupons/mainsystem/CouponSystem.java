package com.coupons.mainsystem;

import com.coupons.client.CouponClientFacade;
import com.coupons.client.LoginManager;
import com.coupons.dbdao.ConnectionPool;
import com.coupons.exceptions.ManagerSQLException;
import com.coupons.exceptions.ManagerThreadException;
import com.coupons.exceptions.UserNotFoundException;
import com.coupons.threads.ExpiredCouponDeamon;
/*
 * CouponSystem is a singleton that manage the coupon system.
 */
public class CouponSystem {
	private static CouponSystem instance;
	private ExpiredCouponDeamon expiredCouponsRunnable;
	private Thread deamon;
	private ConnectionPool pool;
	public static final String ADMIN = "admin";
	public static final String CUSTOMER = "customer";
	public static final String COMPANY = "company";

	/**
	 * This method return the only instance of the CouponSystem
	 * @return CouponSystem
	 * @throws ManagerSQLException
	 */
	public static CouponSystem getInstance() throws ManagerSQLException {
		if (instance == null) {
			instance = new CouponSystem();
		}

		return instance;
	}

	/*
	 * At the contractor of the CouponSystem the PoolConnection is been initialized and the thread
	 * who check the expiration of the coupons
	 */
	private CouponSystem() throws ManagerSQLException {
		expiredCouponsRunnable = new ExpiredCouponDeamon();
		deamon = new Thread(expiredCouponsRunnable);
		deamon.start();
		pool = ConnectionPool.getInstance();

	}
	
	/**
	 * This function authenticate the user and the password according to the type.
	 * @param name
	 * @param password
	 * @param clientType
	 * @return CouponClientFacade
	 * @throws UserNotFoundException
	 * @throws ManagerSQLException
	 * @throws ManagerThreadException
	 */
	public CouponClientFacade login(String name, String password,
			String clientType) throws UserNotFoundException, ManagerSQLException, ManagerThreadException {
		return LoginManager.login(name, password, clientType);
	}

	/**
	 * This function close all the open connections and stops the thread who check the experations
	 * @throws ManagerSQLException
	 */
	public void shutdown() throws ManagerSQLException {
		expiredCouponsRunnable.stopTask();
		pool.closeAllConnections();
	}

}
