package com.ben.hibernate.helloworld;

import static org.junit.Assert.*;

import java.sql.Date;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.junit.Test;


public class HibernateTest {

	@Test
	public void test() {
		
		//1. ����һ�� SessionFactory �����̰߳�ȫ��һ����Ŀ��һ��ֻ��һ��ʵ����
		SessionFactory sessionFactory = null;
		
		//(1) ���� Configuration ���� ��Ӧ hibernate �Ļ���������Ϣ�Ͷ����ϵӳ��
		Configuration configuration = new Configuration().configure();
		
		//4.0֮ǰ��������
//		sessionFactory = configuration.buildSessionFactory();
		
		//(2) ����һ�� ServiceRegister ���� hibernate 4.x ����ӵĶ���
		//hibernate ���κ�������Ϣ����Ҫ�ڸö�����ע��������Ч
		ServiceRegistry serviceRegistry = 
				new ServiceRegistryBuilder().applySettings(configuration.getProperties())
											.buildServiceRegistry();
		//(3) 
		sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		
		//2. ���� Configuration ����
		Session session = sessionFactory.openSession();
		
		//3. ��������
		Transaction transaction = session.beginTransaction();
		
		//4. ִ�б������
		News news = new News("Java","Ben",new Date(new java.util.Date().getTime()));
		session.save(news);
		
		//5. �ύ����
		transaction.commit();
		
		//6. �ر�����
		session.close();
		
		//7.�ر� SessionFactory ����
		sessionFactory.close();
	}

}
