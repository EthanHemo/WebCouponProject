package com.web.webservices.jaxb;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.coupons.client.AdminFacade;
import com.coupons.client.CustomerFacade;
import com.coupons.data.Coupon;
import com.coupons.data.CouponType;
import com.coupons.data.Customer;
import com.coupons.exceptions.ManagerSQLException;
import com.coupons.exceptions.ManagerThreadException;

@Path("/customer")
public class CustomerService {

	@Context
	HttpServletRequest request;

	private static final String FACADE_PARAMETER = "facade";

	@GET
	@Path("getCoupons")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Coupon> getCoupons() {
		try {
			CustomerFacade customer = (CustomerFacade) request.getSession()
					.getAttribute(FACADE_PARAMETER);

			return customer.getAllPurchasedCoupons();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return null;
	}
	
	
	@GET
	@Path("getCouponsByType")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Coupon> getAllPurchasedCouponsByType(@QueryParam("type") CouponType type) {
		try {
			CustomerFacade customer = (CustomerFacade) request.getSession()
					.getAttribute(FACADE_PARAMETER);

			return customer.getAllPurchasedCouponsByType(type);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return null;
	}
	
	@GET
	@Path("getCouponsByPrice")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Coupon> getCouponsByPrice(@QueryParam("price") float price) {
		try {
			CustomerFacade customer = (CustomerFacade) request.getSession()
					.getAttribute(FACADE_PARAMETER);

			return customer.getAllPurchasedCouponsUntilPrice(price);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return null;
	}	
	
	
	@GET
	@Path("getAvailableCoupons")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Coupon> getAvailableCoupons() {
		try {
			CustomerFacade customer = (CustomerFacade) request.getSession()
					.getAttribute(FACADE_PARAMETER);

			return customer.getAllAvailableCoupons();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return null;
	}	
	
	@POST
	@Path("purchaseCoupon")
	@Consumes(MediaType.APPLICATION_JSON)
	public void purchaseCoupon(Coupon coupon) {
		try {
			CustomerFacade customer = (CustomerFacade) request.getSession()
					.getAttribute(FACADE_PARAMETER);

			customer.purchaseCoupon(coupon);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}	
	

}
