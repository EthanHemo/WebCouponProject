package com.web.display;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.stream.events.StartDocument;

import com.coupons.client.CouponClientFacade;
import com.coupons.exceptions.ManagerSQLException;
import com.coupons.exceptions.ManagerThreadException;
import com.coupons.exceptions.UserNotFoundException;
import com.coupons.mainsystem.CouponSystem;

/**
 * Servlet implementation class TestWebPages
 */
@WebServlet("/TestWebPages")
public class TestWebPages extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TestWebPages() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().println("Got it");
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			System.out.println("Start of program");
			CouponSystem cs = CouponSystem.getInstance();
			CouponClientFacade clientFacade;
			switch(request.getParameter("clientType")){
			case "customer":
				System.out.println("in customer");
				clientFacade = cs.login(request.getParameter("userName"), request.getParameter("userPass"), CouponSystem.CUSTOMER);
				System.out.println("After login");
				response.getWriter().println("Succeses client");
				//response.getWriter().println("Hi customer " + request.getParameter("userName"));
				System.out.println("After print");
				break;
			case "company":
				System.out.println("in company");
				clientFacade = cs.login(request.getParameter("userName"), request.getParameter("userPass"), CouponSystem.COMPANY);
				response.getWriter().println("Hi Company " + request.getParameter("userName"));
				break;
			case "admin":
				System.out.println("in admin");
				clientFacade = cs.login(request.getParameter("userName"), request.getParameter("userPass"), CouponSystem.ADMIN);
				response.getWriter().println("YO ADMIN WHATSAPPP");
				break;
			default:
				System.out.println("damn..");
				response.getWriter().println("Type not found");
			}
			
		} catch (UserNotFoundException e) {
			response.getWriter().println("Error log in");
		} catch (ManagerSQLException e) {
			e.printStackTrace();
		
		} catch (ManagerThreadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

}
