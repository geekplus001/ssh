package com.atguigu.struts2.model;

import java.util.Date;

public class Customer {

	private int age;

	public void setAge(int age) {
		this.age = age;
	}

	public int getAge() {
		return age;
	}

	private Date birth;

	public void setBirth(Date birth) {
		this.birth = birth;
	}

	public Date getBirth() {
		return birth;
	}
	
	@Override
	public String toString() {
		return "Customer [age=" + age + ", birth=" + birth + "]";
	}

	
}
