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
import javax.xml.bind.JAXBElement;

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

	private static final String FACADE_PARAMETER = "facade";

	

	@Path("/createCompany")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void createCompany(Company company) {
		System.out.println(company.toString());
		try {
			if (request.getSession().getAttribute(FACADE_PARAMETER) != null) {
				AdminFacade admin = (AdminFacade) request.getSession()
						.getAttribute(FACADE_PARAMETER);
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
	public void removeCompany(Company company) {
		try {
			AdminFacade admin = (AdminFacade) request.getSession()
					.getAttribute(FACADE_PARAMETER);

			System.out.println("Company " + company.getCompanyName() + " ("
					+ company.getId() + ") is about to be deleted");
			admin.removeCompany(company);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Path("/updateCompany")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void updateCompany(Company company) {
		System.out.println(company.toString());
		try {
			if (request.getSession().getAttribute(FACADE_PARAMETER) != null) {
				AdminFacade admin = (AdminFacade) request.getSession()
						.getAttribute(FACADE_PARAMETER);
				admin.updateCompany(company);
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

	@Path("/getCompany")
	@GET
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	public Company getCompany(@QueryParam("id") long id) {
		return null;
	}

	@Path("/getAllCompanies")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Company> getAllCompanies() {
		try {
			if (request.getSession().getAttribute(FACADE_PARAMETER) != null) {
				AdminFacade facade = (AdminFacade) request.getSession()
						.getAttribute(FACADE_PARAMETER);
				return facade.getAllCompanies();
			} else {
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
	public void createCustomer(Customer customer) {
		try {
			AdminFacade admin = (AdminFacade) request.getSession()
					.getAttribute(FACADE_PARAMETER);
			admin.createCustomer(customer);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Path("/removeCustomer")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void removeCustomer(Customer customer) {
		try {
			AdminFacade admin = (AdminFacade) request.getSession()
					.getAttribute(FACADE_PARAMETER);
			admin.removeCustomer(customer);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Path("/updateCustomer")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void updateCustomer(Customer customer) {
		try {
			AdminFacade admin = (AdminFacade) request.getSession()
					.getAttribute(FACADE_PARAMETER);
			admin.updateCustomer(customer);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Path("/getCustomer")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Customer getCustomer(long id) {
		try {
			AdminFacade admin = (AdminFacade) request.getSession()
					.getAttribute(FACADE_PARAMETER);
			return admin.getCustomer(id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Path("/getAllCustomer")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Customer> getAllCustomer() throws Exception {
		try {
			if (request.getSession().getAttribute("facade") != null) {
				System.out.println("threr is a session");
				AdminFacade facade = (AdminFacade) request.getSession()
						.getAttribute("facade");
				return facade.getAllCustomer();
			} else {
				System.out.println("threr is NOT session");
				AdminFacade facade = new AdminFacade();
				return facade.getAllCustomer();
			}
		} catch (ManagerSQLException e) {
			e.printStackTrace();
		} catch (ManagerThreadException e) {
			e.printStackTrace();
		}

		return null;
	}

}
