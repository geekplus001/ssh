package com.atguigu.struts2.model;

import java.util.Date;

public class Manager {
	
	private String name;
	private Date birth;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getBirth() {
		return birth;
	}
	public void setBirth(Date birth) {
		this.birth = birth;
	}
	@Override
	public String toString() {
		return "Manager [name=" + name + ", birth=" + birth + "]";
	}
	
	
	
}
