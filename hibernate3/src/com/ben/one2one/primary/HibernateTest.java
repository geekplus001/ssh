package com.ben.one2one.primary;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class HibernateTest {
	private SessionFactory sessionFactory;
	private Session session;
	private Transaction transaction;
	//生产环境中Session 和Transaction 不能作为成员变量，会有并发问题

	@Before
	public void init()
	{
		Configuration configuration = new Configuration().configure();
		ServiceRegistry serviceRegistry = 
				new ServiceRegistryBuilder().applySettings(configuration.getProperties())
											.buildServiceRegistry();
		sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		
		session = sessionFactory.openSession();
		transaction = session.beginTransaction();
	}
	
	@After
	public void destroy()
	{
		transaction.commit();
		session.close();
		sessionFactory.close();
	}
	@Test
	public void testGet2()
	{
		//在查询没有外键的实体对象时， 使用的是左外连接查询， 一并查询出其关联的对象
		//并进行初始化
		Manager mgr = (Manager) session.get(Manager.class, 1);
		System.out.println(mgr.getMgrName());
		System.out.println(mgr.getDept().getDeptName());
	}
	@Test
	public void testGet()
	{
		//1. 默认情况下对关联属性使用懒加载
		Department department = (Department) session.get(Department.class, 1);
		System.out.println(department.getDeptName());
		
		Manager manager = department.getMgr();
		System.out.println(manager.getClass());
		
		System.out.println(manager.getMgrName());
		
		//2. 查询Manager 对象的连接条件应该是dept.manager_id = mgr.manager_id
		//而不应该是dept.dept_id = mgr.manager_id
		Manager mgr = department.getMgr();
		System.out.println(mgr.getMgrName());
		
	}
	@Test
	public void testSave()
	{
		Department department = new Department();
		department.setDeptName("DEPT-BB");
	
		Manager manager = new Manager();
		manager.setMgrName("MGR-BB");
		
		//设定关联关系
		department.setMgr(manager);
		manager.setDept(department);
		
		//保存操作
		//先插入哪一个都行
		session.save(manager);
		session.save(department);
	}
}
