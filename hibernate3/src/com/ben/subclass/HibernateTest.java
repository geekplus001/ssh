package com.ben.subclass;

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
	/*
	 * ȱ�㣺
	 * 1. ʹ���˱������
	 * 2. ������е��ֶβ������ӷǿ�Լ��
	 * 3. ���̳й�ϵ��� �����ݱ��ֶ�Ҳ��϶�
	 */
	/*
	 * ��̬��ѯ��
	 * 1. ��ѯ�����¼�� ֻ��Ҫ��ѯһ�����ݱ�
	 * 2. ���������¼�� Ҳֻ��Ҫ��ѯһ�����ݱ�
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
	 * ���������
	 * 1. �����������ֻ��Ѽ�¼���뵽һ�����ݱ���
	 * 2. ���������Hibernate �Զ�ά��
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