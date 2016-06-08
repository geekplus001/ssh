package com.ben.struts2.action;

import java.util.Map;

import org.apache.struts2.dispatcher.SessionMap;

import com.opensymphony.xwork2.ActionContext;

public class TestActionContextAction {
	
	public String execute()
	{
		
		//0. 获取ActionContext 对象（Action上下文对象， 可从中获取Action 需要的一切信息）
		ActionContext actionContext = ActionContext.getContext();
		
		//1. 获取application 对应的Map ，并向其中一个添加一个属性
		Map<String, Object> applicationMap = actionContext.getApplication();
		//设置属性
		applicationMap.put("applicationKey", "applicationValue");
		//获取属性
		Object date = applicationMap.get("date");
		System.out.println("date " + date);
		
		//2. session 
		Map<String, Object> sessionMap = actionContext.getSession();
		sessionMap.put("sessionKey", "sessionValue");
		
		System.out.println(sessionMap.getClass());
		
		if(sessionMap instanceof SessionMap)
		{
			SessionMap sm = (SessionMap) sessionMap;
			sm.invalidate();
			System.out.println("session 失效了。");
		}
		
		//3. request
		//actionContext 中并没有提供getRequest 方法获取request 对应的Map
		//需要手工调用get（） 方法， 传入request 字符串来获取
		Map<String, Object> requestMap = (Map<String, Object>) actionContext.get("request");
		requestMap.put("requestKey", "requestValue");
		
		//4. 获取请求参数对应的Map ，并获取指定的参数值
		//键： 请求参数的名字， 值： 请求参数的值对应的字符串数组
		//注意： 1.getParameters 的返回值为在Map<String, Object>， 而不是Map<String,String[]>
		//	   2. parameters 这个Map 只能读， 不能写入数据， 如果写入， 不出错 ， 不过不起作用
		Map<String, Object> parameters = actionContext.getParameters();
		System.out.println(((String[])parameters.get("name"))[0]);
		parameters.put("age", 100);
		
		return "success";
	}
	
}
