package com.atguigu.struts2.app;

import java.util.Collection;

import com.atguigu.struts2.model.Manager;
import com.opensymphony.xwork2.ActionSupport;

public class TestCollectionAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Collection<Manager> mgrs = null;
	
	public Collection<Manager> getMgrs() {
		return mgrs;
	}

	public void setMgrs(Collection<Manager> mgrs) {
		this.mgrs = mgrs;
	}

	public String execute() throws Exception {
		System.out.println(mgrs);
		return SUCCESS;
	}
}
