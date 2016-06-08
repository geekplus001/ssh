package com.atguigu.struts2.model;

/**
 * 1. Department 是模型, 实际录入的 Department. deptName 可以直接写到
 * s:textfield 的 name 属性中. 那 mgr 属性如何处理呢 ?
 * 
 * struts2 表单标签的 name 值可以被赋为 属性的属性: name=mgr.name, name=mgr.birth
 * 
 * 2. mgr 中有一个 Date 类型的 birth 属性, Struts2 可以完成自动的类型转换吗 ?
 * 
 * 全局的类型转换器可以正常工作!
 *
 */

public class Department {
	
	private Integer id;
	private String deptName;
	
	private Manager mgr;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public Manager getMgr() {
		return mgr;
	}

	public void setMgr(Manager mgr) {
		this.mgr = mgr;
	}

	@Override
	public String toString() {
		return "Department [id=" + id + ", deptName=" + deptName + ", mgr="
				+ mgr + "]";
	}
	
	
	
}
