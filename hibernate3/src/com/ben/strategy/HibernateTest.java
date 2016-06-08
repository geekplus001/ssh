package com.ben.strategy;

import java.util.List;

import org.hibernate.Hibernate;
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
	public void testMany2OneStrategy()
	{
//		Order order = (Order) session.get(Order.class, 1);
//		System.out.println(order.getCustomer().getCustomerName());
		
		List<Order> orders = session.createQuery("FROM Order o").list();
		for(Order o:orders)
		{
			if(o.getCustomer() != null)
			{
				System.out.println(o.getCustomer().getCustomerName());
			}
		}
		//1. lazy ȡֵΪproxy ��false �ֱ��������ӳټ�������������
		//2. fetch ȡֵΪjoin �� ��ʾʹ�������������ӵķ�ʽ��ʼ��n ������1 ��һ�˵����ԣ�orders��
		//����lazy ����
		//3. batch-size ��������Ҫ������1 ��һ�˵�class Ԫ���У�
		//<class name="Customer" table="CUSTOMERS" lazy="true" batch-size="5">
		//���ã� һ�γ�ʼ��1 ��һ�δ������ĸ���
	}
	@Test
	public void testSetFetch2()
	{
		Customer customer = (Customer) session.get(Customer.class,1);
		System.out.println(customer.getOrders().size());
	}
	@Test
	public void testSetFetch()
	{
		List<Customer> customers = session.createQuery("FROM Customer").list();
		
		System.out.println(customers.size());
		
		for(Customer c:customers)
		{
			if(c.getOrders() != null)
			{
				System.out.println(c.getOrders().size());
			}
		}
		//set ���ϵ� fetch ���ԣ�ȷ����ʼ��orders �Ĳ�ѯ������ʽ
		//1. Ĭ��ֵΪ select . ͨ�������ķ�ʽ����ʼ������
		//2. ����ȡֵΪ subselect �� ͨ���Ӳ�ѯ�ķ�ʽ����ʼ�����е� set ���ϣ� 
		//�Ӳ�ѯ��Ϊwhere �Ӿ��in ���������֣� �Ӳ�ѯ��ѯ����1  ��һ��ID ����ʱlazy ��Ч
		//��batch-size ��Ч
		//3. ��ȡֵΪjoin �� ��
		//3.1 �ڼ���1 ��һ�˵Ķ���ʱ�� ʹ�������������ӣ�ʹ���������ӽ��в�ѯ�� 
		//�ҰѼ������Խ��г�ʼ�����ķ�ʽ����n ��һ�˵ļ������� 
		//3.2 ����lazy ����
		//3.3 HQL ��ѯ����fetch=join ��ȡֵ
	}
	@Test
	public void testSetBatchSize()
	{
		List<Customer> customers = session.createQuery("FROM Customer").list();
		
		System.out.println(customers.size());
		
		for(Customer c:customers)
		{
			if(c.getOrders() != null)
			{
				System.out.println(c.getOrders().size());
			}
		}
		//set Ԫ�ص�batch-size ���Կ����趨һ�γ�ʼ��set ���ϵ�����
	}
	@Test
	public void testOne2ManyLevelStrategy()
	{
		Customer customer = (Customer) session.get(Customer.class, 1);
		System.out.println(customer.getCustomerName());
		
		System.out.println(customer.getOrders().size());
		
		Order order = new Order();
		order.setOrderId(1);
		System.out.println(customer.getOrders().contains(order));
		
		//�Լ��ϵĴ��������г�ʼ��
		Hibernate.initialize(customer.getOrders());
		
		//------------ set �� lazy ����-----------------
		//1. 1-n �� n-n �ļ�������Ĭ��ʹ�������ؼ�������
		//2. ����ͨ������set ��lazy �������޸�Ĭ�ϵļ������ԣ� Ĭ��Ϊtrue �� ������������Ϊfalse
		//3. lazy ����������Ϊ extra�� ��ǿ���ӳټ����� ��ȡֵ�ᾡ���ܵ��ӳټ��ϳ�ʼ����ʱ��
	}
	@Test
	public void testClassLevelStrategy()
	{
		Customer customer = (Customer) session.load(Customer.class, 1);
		System.out.println(customer.getClass());
		
		System.out.println(customer.getCustomerId());
		System.out.println(customer.getCustomerName());
	}
}
