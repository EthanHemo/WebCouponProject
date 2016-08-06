package com.web.webservices.jaxb;

import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.coupons.client.CompanyFacade;
import com.coupons.data.Coupon;
import com.coupons.data.CouponType;
import com.coupons.exceptions.ManagerSQLException;
import com.coupons.exceptions.ManagerThreadException;

@Path("/company")
public class CompanyService {

	@Context
	HttpServletRequest request;

	public void createCoupon(Coupon coupon) {
	}

	public void removeCoupon(Coupon coupon) {
	}

	public void updateCoupon(Coupon coupon) {
	}

	public Coupon getCoupon(long id) {
		return null;
	}

	@GET
	@Path("getAllCoupon")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Coupon> getAllCoupon() {
		try {

			if (request.getSession().getAttribute("facade") != null) {
				CompanyFacade facade = (CompanyFacade) request.getSession().getAttribute("facade");
				return facade.getAllCoupon();
			}
		} catch (ManagerSQLException | ManagerThreadException e) {
			e.printStackTrace();
		}

		return null;
	}

	public Collection<Coupon> getCouponByType(CouponType type) {
		return null;
	}

	public Collection<Coupon> getCouponUntilPrice(float price) {
		return null;
	}

	public Collection<Coupon> getCouponUntilDate(Date date) {
		return null;
	}
}
