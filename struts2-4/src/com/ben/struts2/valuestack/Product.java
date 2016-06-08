package com.ben.struts2.valuestack;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.util.ValueStack;

public class Product implements RequestAware,SessionAware{

	private Integer productId;
	private String productName;
	private String productDesc;
	
	private double productPrice;
	
	private List<Person> persons = new ArrayList<>();
	

	public List<Person> getPersons() {
		return persons;
	}

	public void setPersons(List<Person> persons) {
		this.persons = persons;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductDesc() {
		return productDesc;
	}

	public void setProductDesc(String productDesc) {
		this.productDesc = productDesc;
	}

	public double getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(double productPrice) {
		this.productPrice = productPrice;
	}


	@Override
	public String toString() {
		return "Product [productId=" + productId + ", productName=" + productName + ", productDesc=" + productDesc
				+ ", productPrice=" + productPrice + "]";
	}
	
	public String save()
	{
		System.out.println("save: " + this);
		
		//1. 獲取值棧
		ValueStack valueStack = ActionContext.getContext().getValueStack();
		
		//2. 創建Test 對象，并为其属性赋值
		Test test = new Test();
		test.setProductDesc("abcd");
		test.setProductName("ABCD");
		
		//3. 把Test 对象压入到值栈的栈顶
		valueStack.push(test);
		
		
		sessionMap.put("product", this);
		requestMap.put("test", test);
		
		
		int i = 10/0;
		
		return "success";
	}
	
	public String test()
	{
		System.out.println("test");
		return "success";
	}

	private Map<String, Object> sessionMap;
	@Override
	public void setSession(Map<String, Object> arg0) {
		this.sessionMap = arg0;
	}

	private Map<String, Object> requestMap;
	@Override
	public void setRequest(Map<String, Object> arg0) {
		this.requestMap = arg0;
	}
	
	public String testTag()
	{
		this.productId = 1001;
		this.productDesc = "desc";
		this.productName = "productName";
		this.productPrice = 1000;

		persons.add(new Person("AAA",10));
		persons.add(new Person("BBB",20));
		persons.add(new Person("CCC",30));
		persons.add(new Person("DDD",40));
		persons.add(new Person("EEE",50));
		
		return "success";
	}
}
