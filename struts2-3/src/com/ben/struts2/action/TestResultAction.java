package com.ben.struts2.action;

import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

public class TestResultAction implements SessionAware{

	private int number;
	
	public void setNumber(int number) {
		this.number = number;
	}
	
	public String execute()
	{
//		number = (int) session.get("number");
		String result = null;
		//根据请求参数number 的值， 返回对应的JSP 页面
		//若number 是4倍数， 返回success。jsp
		if(number % 4 == 0)
			result = "success";
		//若number 除以4余数1， 返回login。jsp
		else if(number % 4 == 1)
			result = "login";
		//若number 除以4余数2， 返回index。jsp
		else if(number % 4 == 2)
			result = "index";
		else 
			result = "test";
		return result;
	}

	private Map<String, Object> session;
	@Override
	public void setSession(Map<String, Object> arg0) {
		// TODO Auto-generated method stub
		this.session = arg0;
	}
	
}
