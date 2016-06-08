package com.ben.struts2.action;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

public class TestServletActionContextAction {

	public String execute()
	{
		/*
		 * ServletActionContext： 中可获取到当前Action 对象需要的一切Servlet ＡＰＩ相关对象
		 */
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = ServletActionContext.getRequest().getSession();
		ServletContext servletContext = ServletActionContext.getServletContext();
		
		System.out.println("execute...");
		
		return "success";
	}
	
	
}
