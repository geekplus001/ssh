package com.ben.struts2;

public class DynamicMethodInvocation {

	public String save()
	{
		System.out.println("save...");
		return "success";
	}
	
	public String update()
	{
		System.out.println("update...");
		return "success";
	}
	
}
