package com.atguigu.struts2.interceptors;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class MyInterceptor extends AbstractInterceptor {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		
		System.out.println("before invocation.invoke...");
		
		//String result = invocation.invoke();
		
		System.out.println("after invocation.invoke...");
		
		return "success";
	}

}
