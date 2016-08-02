package com.web.webservices.jaxb;


import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.coupons.client.CouponClientFacade;
import com.coupons.exceptions.ManagerSQLException;
import com.coupons.exceptions.ManagerThreadException;
import com.coupons.exceptions.UserNotFoundException;
import com.coupons.mainsystem.CouponSystem;

@Path("/Login")
public class LoginService {
	
	@Context
	HttpServletRequest request;
	
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String login(@FormParam("username") String username,
            @FormParam("password") String password,
            @FormParam("role") String role){
		System.out.println("Username: " + username);
		System.out.println("Password: " + password);
		System.out.println("Role: " + role);
		try {
			Thread.currentThread().sleep(2000);
			
			CouponSystem couponSystem = CouponSystem.getInstance();
			CouponClientFacade facade;
			switch(role){
				case "Admin":
					facade = couponSystem.login(username, password, CouponSystem.ADMIN);
					break;
				case "Company":
					facade = couponSystem.login(username, password, CouponSystem.COMPANY);
					break;
				case "Customer":
					facade = couponSystem.login(username, password, CouponSystem.CUSTOMER);
					break;
				default:
					throw new UserNotFoundException("No such type");
			}
			
			request.getSession().setAttribute("facade", facade);
			return "true";
		} catch (ManagerSQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UserNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ManagerThreadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return "false";
	}
}
