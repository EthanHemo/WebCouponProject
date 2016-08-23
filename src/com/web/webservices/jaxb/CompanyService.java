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
	public void createCoupon(Coupon coupon) throws CouponException,
			ManagerSQLException, ManagerThreadException {
		CompanyFacade facade = (CompanyFacade) request.getSession()
				.getAttribute(FACADE_PARAMETER);
		facade.createCoupon(coupon);

	}

	@POST
	@Path("removeCoupon")
	@Consumes(MediaType.APPLICATION_JSON)
	public void removeCoupon(Coupon coupon) throws CouponException,
			ManagerSQLException, ManagerThreadException {
		CompanyFacade facade = (CompanyFacade) request.getSession()
				.getAttribute(FACADE_PARAMETER);
		facade.removeCoupon(coupon);
	}

	@POST
	@Path("updateCoupon")
	@Consumes(MediaType.APPLICATION_JSON)
	public void updateCoupon(Coupon coupon) throws CouponException,
			ManagerSQLException, ManagerThreadException {
		CompanyFacade facade = (CompanyFacade) request.getSession()
				.getAttribute(FACADE_PARAMETER);
		facade.updateCoupon(coupon);
	}

	@GET
	@Path("getCouponById")
	public Coupon getCoupon(@QueryParam("id") long id)
			throws ManagerThreadException, ManagerSQLException {
		CompanyFacade facade = (CompanyFacade) request.getSession()
				.getAttribute(FACADE_PARAMETER);
		return facade.getCoupon(id);
	}

	@GET
	@Path("getAllCoupon")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Coupon> getAllCoupon() throws ManagerSQLException,
			ManagerThreadException {
		CompanyFacade facade = (CompanyFacade) request.getSession()
				.getAttribute("facade");
		return facade.getAllCoupon();
	}

	@GET
	@Path("getCouponByType")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Coupon> getCouponByType(
			@QueryParam("type") CouponType type) throws ManagerSQLException,
			ManagerThreadException {
		CompanyFacade facade = (CompanyFacade) request.getSession()
				.getAttribute("facade");
		return facade.getCouponByType(type);
	}

	@GET
	@Path("getCouponUntilPrice")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Coupon> getCouponUntilPrice(
			@QueryParam("price") float price) throws ManagerSQLException,
			ManagerThreadException {
		CompanyFacade facade = (CompanyFacade) request.getSession()
				.getAttribute("facade");
		return facade.getCouponUntilPrice(price);
	}

	@GET
	@Path("getCouponUntilDate")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Coupon> getCouponUntilDate(
			@QueryParam("date") String dateStr) throws ParseException,
			ManagerSQLException, ManagerThreadException {
		DateFormat df = new SimpleDateFormat("EEE MMM d yyyy");
		Date date = df.parse(dateStr);
		CompanyFacade facade = (CompanyFacade) request.getSession()
				.getAttribute("facade");
		return facade.getCouponUntilDate(date);
	}
}
