package com.ben.struts2.action;

import javax.servlet.ServletContext;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.util.ServletContextAware;


public class TestServletAwareAction 
	implements ServletRequestAware,ServletContextAware,ServletResponseAware {

	public String execute()
	{
		return "success";
	}
	private ServletResponse response;
	@Override
	public void setServletResponse(HttpServletResponse arg0) {
		// TODO Auto-generated method stub
		System.out.println(arg0);
		this.response = arg0;
	}

	@Override
	public void setServletContext(ServletContext arg0) {
		// TODO Auto-generated method stub
		System.out.println(arg0);
	}

	@Override
	public void setServletRequest(HttpServletRequest arg0) {
		// TODO Auto-generated method stub
		System.out.println(arg0);		
	}

}
