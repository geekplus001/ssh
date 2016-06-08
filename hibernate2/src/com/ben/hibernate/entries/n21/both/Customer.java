package com.ben.hibernate.entries.n21.both;

import java.util.HashSet;
import java.util.Set;

public class Customer {

	private Integer customerId;
	private String customerName;
	//1. ������������ʱ�� ��ʹ�ýӿ����ͣ� ��ΪHibernate �ڻ�ȡ��������ʱ��
	//���ص���Hibernate ���õļ������ͣ� ������JavaSE һ����׼�ļ���ʵ��
	//2. ��Ҫ�Ѽ��ϳ�ʼ���� ���Է�ֹ������ָ���쳣
	private Set<Order> orders = new HashSet<>();
	
	
	public Set<Order> getOrders() {
		return orders;
	}
	public void setOrders(Set<Order> orders) {
		this.orders = orders;
	}
	public Integer getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
	
}
