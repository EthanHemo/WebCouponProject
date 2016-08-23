package com.web.webservices.jaxb;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.coupons.client.CompanyFacade;
import com.coupons.data.Coupon;
import com.coupons.data.CouponType;
import com.coupons.exceptions.CouponException;
import com.coupons.exceptions.ManagerSQLException;
import com.coupons.exceptions.ManagerThreadException;

@Path("/company")
public class CompanyService {

	@Context
	HttpServletRequest request;
	
	private static final String FACADE_PARAMETER = "facade";

	@POST
	@Path("createCoupon")
	@Consumes(MediaType.APPLICATION_JSON)
	public void createCoupon(Coupon coupon) {
		try {
			CompanyFacade facade = (CompanyFacade) request.getSession().getAttribute(FACADE_PARAMETER);
			facade.createCoupon(coupon);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
	}

	@POST
	@Path("removeCoupon")
	@Consumes(MediaType.APPLICATION_JSON)
	public void removeCoupon(Coupon coupon) {
		try {
			CompanyFacade facade = (CompanyFacade) request.getSession().getAttribute(FACADE_PARAMETER);
			facade.removeCoupon(coupon);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}

	@POST
	@Path("updateCoupon")
	@Consumes(MediaType.APPLICATION_JSON)
	public void updateCoupon(Coupon coupon) {
		try {
			CompanyFacade facade = (CompanyFacade) request.getSession().getAttribute(FACADE_PARAMETER);
			facade.updateCoupon(coupon);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
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

	@GET
	@Path("getCouponByType")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Coupon> getCouponByType(@QueryParam("type") CouponType type) {
		try {
			CompanyFacade facade = (CompanyFacade) request.getSession().getAttribute("facade");
			return facade.getCouponByType(type);
		} catch (ManagerSQLException | ManagerThreadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@GET
	@Path("getCouponUntilPrice")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Coupon> getCouponUntilPrice(@QueryParam("price") float price) {
		try {
			CompanyFacade facade = (CompanyFacade) request.getSession().getAttribute("facade");
			return facade.getCouponUntilPrice(price);
		} catch (ManagerSQLException | ManagerThreadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@GET
	@Path("getCouponUntilDate")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Coupon> getCouponUntilDate(@QueryParam("date") String dateStr) {
		try {
			DateFormat df = new SimpleDateFormat("EEE MMM d yyyy");
			Date date = df.parse(dateStr);
			CompanyFacade facade = (CompanyFacade) request.getSession().getAttribute("facade");
			return facade.getCouponUntilDate(date);
		} catch (ManagerSQLException | ManagerThreadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
