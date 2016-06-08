package com.ben.hibernate.entries.n21;

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
	public void testDelete()
	{
		// �ڲ��趨������ϵ������£� ��1 ��һ����n �Ķ��������ã� ����ֱ��ɾ��1 ��һ�˵Ķ���
		Customer customer = (Customer) session.get(Customer.class, 1);
		session.delete(customer);
	}
	@Test
	public void testUpdate()
	{
		Order order = (Order) session.get(Order.class, 1);
		order.getCustomer().setCustomerName("AA");
	}
	@Test
	public void testMany2OneGet()
	{
		//1. ��ֻ��ѯ���һ�˵Ķ��� ��Ĭ��ֻ��ѯ���һ�˵Ķ���
		//��û�в�ѯ������1����һ�˵Ķ����ӳټ��أ�
		Order order = (Order) session.get(Order.class, 1);
		System.out.println(order.getOrderName());
		//2. ����Ҫʹ�ù����Ķ���ʱ, �ŷ��Ͷ�Ӧ��SQL ��䣨�����أ�
		Customer customer = order.getCustomer();
		System.out.println(customer.getCustomerName());
		//3. �ڲ�ѯCustomer ����ʱ���ɶ��һ�˵�����1 ��һ��ʱ�����ܻᷢ���������쳣��session �رգ�
		
		//4. ��ȡOrder ����ʱ��Ĭ��������������Customer ���� ��һ���������
	}
	@Test
	public void testMany2OneSave()
	{
		Customer customer = new Customer();
		customer.setCustomerName("CD");
		
		Order order1 = new Order();
		order1.setOrderName("ORDER-3");
		
		Order order2 = new Order();
		order2.setOrderName("ORDER-4");
		
		//�趨������ϵ
		order1.setCustomer(customer);
		order2.setCustomer(customer);
		
		//�Ȳ���1��    �ٲ����Σ� ����INSERT ���Ƽ��˷�����
//		session.save(customer);
//		session.save(order1);
//		session.save(order2);
		
		//�Ȳ����ˣ� �ٲ���1�ˣ�����INSERT ����UPDATE
		session.save(order1);
		session.save(order2);
		session.save(customer);
	}
}
