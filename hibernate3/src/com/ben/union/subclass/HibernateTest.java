package com.ben.union.subclass;

import java.util.List;

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
	/*
	 * 优点：
	 * 1. 使用了辨别者列
	 * 2. 子类独有的字段不能添加非空约束
	 * 缺点：
	 * 1. 存在冗余字段
	 * 2. 若更新父表字段， 则更新效率低
	 */
	@Test
	public void testUpdate()
	{
		String hql = "UPDATE Person p SET p.age = 20";
		session.createQuery(hql).executeUpdate();
	}
	/*
	 * 多态查询：
	 * 1. 查询父类记录， 需把父表和子表记录汇总到一起再查询，性能稍差（子查询）
	 * 2. 对于子类记录， 只需要查询一张数据表
	 */
	@Test
	public void testQuery()
	{
		List<Person> persons = session.createQuery("FROM Person").list();
		System.out.println(persons.size());
		
		List<Student> students = session.createQuery("FROM Student").list();
		System.out.println(students.size());
	}
	/*
	 * 插入操作：
	 * 1. 对于子类对象只需把记录插入到一张数据表中
	 */
	@Test
	public void testSave()
	{
		Person person = new Person();
		person.setAge(11);
		person.setName("AA");
		
		session.save(person);
		
		Student student = new Student();
		student.setAge(22);
		student.setName("BB");
		student.setSchool("BEN");
		
		session.save(student);
	}
}
