package com.atguigu.struts2.token.app;

import com.opensymphony.xwork2.ActionSupport;

public class TokenAction extends ActionSupport{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String username;
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getUsername() {
		return username;
	}
	
	@Override
	public String execute() throws Exception {
		Thread.sleep(2000); 
		System.out.println(username);
		return SUCCESS;
	}
	
}
