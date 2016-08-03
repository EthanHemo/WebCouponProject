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

import com.coupons.client.CouponClientFacade;
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
			Thread.currentThread().sleep(2000);

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
				break;
			case "customer":
				facade = couponSystem.login(username, password,
						CouponSystem.CUSTOMER);
				result.put("username", "Admin");
				result.put("userId", "0");
				result.put("userRole", "Admin");
				break;
			default:
				throw new UserNotFoundException("No such type");
			}

			request.getSession().setAttribute("facade", facade);
			return clientData;
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
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return clientData;
	}
}
