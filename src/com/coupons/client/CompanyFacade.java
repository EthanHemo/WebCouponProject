package com.coupons.client;

import java.util.Collection;
import java.util.Date;

import com.coupons.data.Company;
import com.coupons.data.Coupon;
import com.coupons.data.CouponType;
import com.coupons.dbdao.CompanyDBDAO;
import com.coupons.dbdao.CouponDBDAO;
import com.coupons.exceptions.CouponException;
import com.coupons.exceptions.ManagerSQLException;
import com.coupons.exceptions.ManagerThreadException;

/*
 *  Company Facade manage requests of a company in the system.
 *  The class need a company to be initialized.
 * 
 */
public class CompanyFacade implements CouponClientFacade {

	private CompanyDBDAO companyDB;
	private CouponDBDAO couponDB;
	private final Company company;

	public CompanyFacade(Company company) throws ManagerSQLException {

		companyDB = new CompanyDBDAO();
		couponDB = new CouponDBDAO();
		this.company = company;

	}

	/**
	 * This function creates a coupon and associate it to the company in the facade. 
	 * if the coupon title already exist in the system there would be a CouponException.
	 * @param coupon
	 * @throws CouponException
	 * @throws ManagerSQLException
	 * @throws ManagerThreadException
	 */
	public void createCoupon(Coupon coupon) throws CouponException,
			ManagerSQLException, ManagerThreadException {

		if (!couponDB.isCouponTitleExist(coupon)) {
			couponDB.createCoupon(coupon, company);
			company.setCoupons(companyDB.getCoupons(company));
		} else
			throw new CouponException("Coupon already exist");

	}

	/**
	 * This function remove the coupon from the system.
	 * If the coupon isn't belong to the company logged in there would be a CouponException.
	 * @param coupon
	 * @throws CouponException
	 * @throws ManagerSQLException
	 * @throws ManagerThreadException
	 */
	public void removeCoupon(Coupon coupon) throws CouponException,
			ManagerSQLException, ManagerThreadException {
		// Check if the coupon belong to the company

		if (couponDB.isCouponBelongToCompany(company, coupon)) {
			couponDB.removeCoupon(coupon);
			company.setCoupons(companyDB.getCoupons(company));

		} else {
			throw new CouponException("Coupon not exist in company list");
		}

	}

	/**
	 * This function updates coupon end_date and price.
	 * If the coupon isn't belong to the company logged in there would be a CouponException.
	 * @param coupon
	 * @throws CouponException
	 * @throws ManagerSQLException
	 * @throws ManagerThreadException
	 */
	public void updateCoupon(Coupon coupon) throws CouponException,
			ManagerSQLException, ManagerThreadException {
		if (couponDB.isCouponBelongToCompany(company, coupon)) {
			couponDB.updateCoupon(coupon);
			company.setCoupons(companyDB.getCoupons(company));
		} else {
			throw new CouponException("Coupon not exist in company list");
		}

	}

	/**
	 * This function return a Coupon of the given ID.
	 * if the Id isn't associate with any coupon there would be a ManagerSQLException.
	 * @param id
	 * @return Coupon
	 * @throws ManagerThreadException
	 * @throws ManagerSQLException
	 */
	public Coupon getCoupon(long id) throws ManagerThreadException,
			ManagerSQLException {
		return couponDB.getCoupon(id);
	}

	/**
	 * This function returns a collection of coupons of the company logged in.
	 * @return Collection<Coupon>
	 * @throws ManagerSQLException
	 * @throws ManagerThreadException
	 */
	public Collection<Coupon> getAllCoupon() throws ManagerSQLException,
			ManagerThreadException {
		return companyDB.getCoupons(company);
	}

	/**
	 * This function returns a collection of coupons of the company logged in of the type given.
	 * @param type
	 * @return Collection<Coupon>
	 * @throws ManagerSQLException
	 * @throws ManagerThreadException
	 */
	public Collection<Coupon> getCouponByType(CouponType type)
			throws ManagerSQLException, ManagerThreadException {

		return couponDB.getCouponsByType(company, type);

	}

	/**
	 * This function returns a collection of coupons of the company logged in 
	 * that less than the price given.
	 * @param price
	 * @return
	 * @throws ManagerSQLException
	 * @throws ManagerThreadException
	 */
	public Collection<Coupon> getCouponUntilPrice(float price)
			throws ManagerSQLException, ManagerThreadException {

		return couponDB.getCouponsOfCompanyUnilPrice(company, price);

	}

	/**
	 * This function returns a collection of coupons of the company logged in 
	 * that before the date given.
	 * @param date
	 * @return
	 * @throws ManagerSQLException
	 * @throws ManagerThreadException
	 */
	public Collection<Coupon> getCouponUntilDate(Date date)
			throws ManagerSQLException, ManagerThreadException {

		return couponDB.getCouponsOfCompanyUntilDate(company, date);

	}

	/**
	 * When toString is called, the details of the company logged in will returns.
	 * other details aren't relevant. 
	 */
	@Override
	public String toString() {
		return company.toString();
	}

	public Company getCompany() {
		// TODO Auto-generated method stub
		return company;
	}

}
