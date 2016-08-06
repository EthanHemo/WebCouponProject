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








import org.json.JSONException;
import org.json.JSONObject;

import com.coupons.client.CompanyFacade;
import com.coupons.client.CouponClientFacade;
import com.coupons.client.CustomerFacade;
import com.coupons.exceptions.ManagerSQLException;
import com.coupons.exceptions.ManagerThreadException;
import com.coupons.exceptions.UserNotFoundException;
import com.coupons.mainsystem.CouponSystem;
import com.web.data.ClientData;

@Path("/login")
public class LoginService {

	@Context
	HttpServletRequest request;

	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public ClientData login(@FormParam("username") String username,
			@FormParam("password") String password,
			@FormParam("role") String role) {
		System.out.println("Username: " + username);
		System.out.println("Password: " + password);
		System.out.println("Role: " + role);
		JSONObject result = new JSONObject();
		ClientData clientData = new ClientData();
		

		CouponClientFacade facade;
		try {
			Thread.currentThread().sleep(50);

			CouponSystem couponSystem = CouponSystem.getInstance();
			

			switch (role) {
			case "admin":
				facade = couponSystem.login(username, password,	CouponSystem.ADMIN);				
				clientData.setUsername(username);
				clientData.setRole(role);
				break;
			case "company":
				facade = couponSystem.login(username, password,
						CouponSystem.COMPANY);
				CompanyFacade companyFacade = (CompanyFacade)facade;
				clientData.setUsername(companyFacade.getCompany().getCompanyName());
				break;
			case "customer":
				facade = couponSystem.login(username, password,
						CouponSystem.CUSTOMER);
				CustomerFacade customerFacade = (CustomerFacade)facade;
				clientData.setUsername(customerFacade.getCustomer().getCustName());
				break;
			default:
				throw new UserNotFoundException("No such type");
			}
			clientData.setRole(role);
			request.getSession().setAttribute("facade", facade);
			return clientData;
		} catch (Exception e1) {
			e1.printStackTrace();
		} 
		return clientData;
	}
	
	@GET
	@Path("loginTest")
	@Produces(MediaType.APPLICATION_JSON)
	public ClientData test(){
		ClientData data = new ClientData();
		data.setRole("admin");
		data.setUsername("Ethan");
		return data;
	}
	
	@GET
	@Path("isConnected")
	public String isConnected(){
		return String.valueOf(request.getSession().getAttribute("facade") != null);
	}
}
