package com.ben.hibernate.entries;
/*
 * 实体类型 有ID
 */
public class Worker {
	private Integer id;
	private String name;
	
	private Pay pay;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Pay getPay() {
		return pay;
	}

	public void setPay(Pay pay) {
		this.pay = pay;
	}

	@Override
	public String toString() {
		return "Worker [id=" + id + ", name=" + name + ", pay=" + pay + "]";
	}
	
}
