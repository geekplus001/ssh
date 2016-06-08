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
	public void testDelete()
	{
		// 在不设定级联关系的情况下， 且1 的一端有n 的对象在引用， 不能直接删除1 这一端的对象
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
		//1. 若只查询多的一端的对象， 则默认只查询多的一端的对象，
		//而没有查询关联的1的那一端的对象（延迟加载）
		Order order = (Order) session.get(Order.class, 1);
		System.out.println(order.getOrderName());
		//2. 在需要使用关联的对象时, 才发送对应的SQL 语句（懒加载）
		Customer customer = order.getCustomer();
		System.out.println(customer.getCustomerName());
		//3. 在查询Customer 对象时（由多的一端导航到1 的一端时）可能会发生懒加载异常（session 关闭）
		
		//4. 获取Order 对象时，默认情况下其关联的Customer 对象， 是一个代理对象
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
		
		//设定关联关系
		order1.setCustomer(customer);
		order2.setCustomer(customer);
		
		//先插入1段    再插入多段， 三条INSERT （推荐此方法）
//		session.save(customer);
//		session.save(order1);
//		session.save(order2);
		
		//先插入多端， 再插入1端，三条INSERT 两条UPDATE
		session.save(order1);
		session.save(order2);
		session.save(customer);
	}
}
