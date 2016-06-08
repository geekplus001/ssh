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
	//����������Session ��Transaction ������Ϊ��Ա���������в�������

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
		//�ڲ�ѯû�������ʵ�����ʱ�� ʹ�õ����������Ӳ�ѯ�� һ����ѯ��������Ķ���
		//�����г�ʼ��
		Manager mgr = (Manager) session.get(Manager.class, 1);
		System.out.println(mgr.getMgrName());
		System.out.println(mgr.getDept().getDeptName());
	}
	@Test
	public void testGet()
	{
		//1. Ĭ������¶Թ�������ʹ��������
		Department department = (Department) session.get(Department.class, 1);
		System.out.println(department.getDeptName());
		
		Manager manager = department.getMgr();
		System.out.println(manager.getClass());
		
		System.out.println(manager.getMgrName());
		
		//2. ��ѯManager �������������Ӧ����dept.manager_id = mgr.manager_id
		//����Ӧ����dept.dept_id = mgr.manager_id
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
		
		//�趨������ϵ
		department.setMgr(manager);
		manager.setDept(department);
		
		//�������
		//�Ȳ�����һ������
		session.save(manager);
		session.save(department);
	}
}
