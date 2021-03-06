package com.coupons.client;

import com.coupons.dbdao.CompanyDBDAO;
import com.coupons.dbdao.CustomerDBDAO;
import com.coupons.exceptions.ManagerSQLException;
import com.coupons.exceptions.ManagerThreadException;
import com.coupons.exceptions.UserNotFoundException;

/*
 * LoginManager authenticate the user and password according the given client type.
 */
public class LoginManager {

	private static final String ADMIN_USER_NAME = "admin";
	private static final String ADMIN_PASSWORD = "1234";

	/**
	 * Login is a static function that authenticate the user and password
	 * according the given client type. The function returns CouponClientFacade
	 * that contains the Facade needed. If the authentication failed there would
	 * be a UserNotFoundException. Casting is needed after receiving
	 * theCouponClientFacade.
	 * 
	 * @param name
	 * @param password
	 * @param clientType
	 * @return CouponClientFacade
	 * @throws UserNotFoundException
	 * @throws ManagerThreadException 
	 * @throws ManagerSQLException 
	 */
	public static CouponClientFacade login(String name, String password, String clientType)
			throws UserNotFoundException, ManagerSQLException, ManagerThreadException {
		CouponClientFacade client = null;

		switch (clientType) {
		case "customer":
			CustomerDBDAO customerDB = new CustomerDBDAO();
			if (customerDB.login(name, password)) {
				client = new CustomerFacade(customerDB.getCustomer(name, password));
			} else {
				throw new UserNotFoundException("User not found");
			}
			break;

		case "company":
			CompanyDBDAO companyDB = new CompanyDBDAO();
			if (companyDB.login(name, password)) {
				client = new CompanyFacade(companyDB.getCompany(name, password));
			} else {
				throw new UserNotFoundException("Company not found");
			}
			break;

		case "admin":
			if (name.equals(ADMIN_USER_NAME) && password.equals(ADMIN_PASSWORD)) {
				client = new AdminFacade();
			} else {
				throw new UserNotFoundException("User not found");
			}
			break;
		default:
			throw new UserNotFoundException("Type not found");

		}
		return client;
	}
}
