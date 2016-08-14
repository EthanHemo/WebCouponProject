package com.web.webservices;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;

/**
 * Servlet Filter implementation class LoginFilter
 */
@WebFilter("/rest/*")
public class LoginFilter implements Filter {
	
	private static final String FACADE_PARAMETER = "facade";

    /**
     * Default constructor. 
     */
    public LoginFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		String url = httpRequest.getRequestURL().toString();
		System.out.println(url);
		
		if((httpRequest.getSession()== null || httpRequest.getSession().getAttribute(FACADE_PARAMETER) == null)&& url.indexOf("/login")== -1 && url.indexOf("/test")== -1 ){
			Response.status(401).build();
		}
		else{
			System.out.println(url);
			chain.doFilter(request, response);
		}
			

		// pass the request along the filter chain
		
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
