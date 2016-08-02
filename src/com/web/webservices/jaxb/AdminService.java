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
import com.coupons.data.Company;
import com.coupons.data.Customer;
import com.coupons.dbdao.CompanyDBDAO;
import com.coupons.exceptions.ManagerSQLException;
import com.coupons.exceptions.ManagerThreadException;

@Path("/admin")
public class AdminService {

	@Context
	HttpServletRequest request;

	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Company> sayPlainTextHello() {
		try {
			CompanyDBDAO companyDB = new CompanyDBDAO();
			if (request.getSession().getAttribute("Visit") == null) {
				request.getSession().setAttribute("Visit", "Yes");
				//return companyDB.getCompany(16);
				return companyDB.getAllCompanies();
			} else {
				//return companyDB.getCompany(17);
				return null;
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

	@Path("/createCompany")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void createCompany(Company company) {

		try {
			if (request.getSession().getAttribute("Facade") == null) {
				AdminFacade admin = (AdminFacade) request.getSession().getAttribute("Facade");
				admin.createCompany(company);
			} else {

			}
		} catch (ManagerThreadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ManagerSQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	@Path("/removeCompany")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void removeCompany(Company company) throws ManagerSQLException, ManagerThreadException{
		AdminFacade admin = (AdminFacade) request.getSession().getAttribute("Facade");
		admin.removeCompany(company);
	}
	
	
	@Path("/updateCompany")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void updateCompany(Company company){}
	
	
	@Path("/getCompany")
	@GET
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	public Company getCompany(@QueryParam("id") long id){
		return null;
	}
	
	
	@Path("/getAllCompanies")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Company> getAllCompanies(){
		System.out.println("We are in");
		try{
			if(request.getSession().getAttribute("facade")!=null){
				System.out.println("threr is a session");
				AdminFacade facade = (AdminFacade)request.getSession().getAttribute("facade");
				return facade.getAllCompanies();
			}
			else{
				System.out.println("threr is NOT session");
				AdminFacade facade = new AdminFacade();
				return facade.getAllCompanies();
			}
		} catch (ManagerSQLException e) {
			e.printStackTrace();
		} catch (ManagerThreadException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	
	@Path("/createCustomer")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void createCustomer(Customer customer){}
	
	
	@Path("/removeCustomer")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void removeCustomer(Customer customer){}
	
	
	@Path("/updateCustomer")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void updateCustomer(Customer customer){}
	
	
	@Path("/getCustomer")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Customer getCustomer(long id){
		return null;
	}
	
	
	@Path("/getAllCustomer")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Customer> getAllCustomer() throws Exception{
		throw new Exception("My exception");
	}

}
