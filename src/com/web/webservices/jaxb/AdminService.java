package com.web.webservices.jaxb;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.coupons.data.Company;
import com.coupons.dbdao.CompanyDBDAO;
import com.coupons.exceptions.ManagerSQLException;
import com.coupons.exceptions.ManagerThreadException;

@Path("/admin")
public class AdminService {
	
	@Context HttpServletRequest request;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Company sayPlainTextHello() {
		try {
			CompanyDBDAO companyDB = new CompanyDBDAO();
			if(request.getSession().getAttribute("Visit") == null){
				request.getSession().setAttribute("Visit", "Yes");
				return companyDB.getCompany(16);
			}
			else{
				return companyDB.getCompany(17);
			}
			
		} catch (ManagerSQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ManagerThreadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
		
	}
	

	
	
}
