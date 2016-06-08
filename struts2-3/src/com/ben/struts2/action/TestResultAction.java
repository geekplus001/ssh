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
		//�����������number ��ֵ�� ���ض�Ӧ��JSP ҳ��
		//��number ��4������ ����success��jsp
		if(number % 4 == 0)
			result = "success";
		//��number ����4����1�� ����login��jsp
		else if(number % 4 == 1)
			result = "login";
		//��number ����4����2�� ����index��jsp
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
