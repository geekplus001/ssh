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
		//内部获取Session 对象
		//获取和当前线程绑定的Session 对象
		//1. 不需要从外部传入Session 对象
		//2. 多个DAO 方法也可以使用一个事务
		Session session = HibernateUtils.getInstance().getSession();
		System.out.println(session.hashCode());
		
		session.save(department);
	}
	/*
	 * 若需要传入一个Session 对象， 则意味着和上一层（Service）需要获取到Session 对象，
	 * 这说明上一层需要和Hibernate 的API 紧密耦合， 所以不推荐此种方式
	 */
	public void save(Session session,Department department)
	{
		session.save(department);
	}
}
