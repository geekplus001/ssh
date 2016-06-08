package com.ben.hibernate.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.ben.hibernate.entities.Department;
import com.ben.hibernate.hibernate.HibernateUtils;

public class DepartmentDao {
	
	private SessionFactory sessionFactory;
	public void setSessionFactory(SessionFactory sessionFactory)
	{
		this.sessionFactory = sessionFactory;
	}
	
	public void save(Department department)
	{
		//�ڲ���ȡSession ����
		//��ȡ�͵�ǰ�̰߳󶨵�Session ����
		//1. ����Ҫ���ⲿ����Session ����
		//2. ���DAO ����Ҳ����ʹ��һ������
		Session session = HibernateUtils.getInstance().getSession();
		System.out.println(session.hashCode());
		
		session.save(department);
	}
	/*
	 * ����Ҫ����һ��Session ���� ����ζ�ź���һ�㣨Service����Ҫ��ȡ��Session ����
	 * ��˵����һ����Ҫ��Hibernate ��API ������ϣ� ���Բ��Ƽ����ַ�ʽ
	 */
	public void save(Session session,Department department)
	{
		session.save(department);
	}
}
