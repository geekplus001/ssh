package com.ben.hibernate.entries;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.jdbc.Work;
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
//		System.out.println("init");
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
//		System.out.println("destroy");
		transaction.commit();
		session.close();
		sessionFactory.close();
	}
	@Test
	public void testComponent(){
		Worker worker = new Worker();
		Pay pay = new Pay();
		
		pay.setMonthlyPay(1000);
		pay.setYearPay(80000);
		pay.setVocationWithPay(100);
		
		worker.setName("ABCD");
		worker.setPay(pay);
		
		session.save(worker);
	}
	@Test
	public void testBlob() throws Exception
	{
//		News news = new News();
//		news.setTitle("CCC");
//		news.setAuthor("ccc");
//		news.setContent("Content");
//		news.setDate(new Date());
//		news.setDesc("desc");
//		
//		InputStream stream = new FileInputStream("QQͼƬ20160329220537.jpg");
//		Blob image = Hibernate.getLobCreator(session)
//							  .createBlob(stream,stream.available());
//		news.setImage(image);
//		session.save(news);
		
		News news = (News) session.get(News.class, 1);
		Blob image = news.getImage();
		
		InputStream in = image.getBinaryStream();
		System.out.println(in.available());
	}
	@Test
	public void testPropertyUpdate()
	{
		News news  = (News) session.get(News.class, 1);
		news.setTitle("aaa");
		
		System.out.println(news.getDesc());
	}
	@Test
	public void testDynamicUpdate()
	{
		News news = (News) session.get(News.class, 1);
		news.setAuthor("ABCD");
	}
	@Test
	public void testDoWork()
	{
		session.doWork(new Work() {
			
			@Override
			public void execute(Connection arg0) throws SQLException {
				// TODO Auto-generated method stub
				System.out.println(arg0);
				
				//���ô洢����
			}
		});
	}
	/*
	 * evict����session �����а�ָ���ĳ־û������Ƴ�
	 */
	@Test
	public void testEvict()
	{
		News news = (News) session.get(News.class, 1);
		News news2 = (News) session.get(News.class, 2);

		news.setAuthor("AA");
		news2.setAuthor("BB");
		
		session.evict(news2);
	}
	/*
	 * delete: ִ��ɾ�������� ֻҪOID �����ݱ���һ����¼��Ӧ�� �ͻ�׼��ִ��delete ����
	 * ��OID �����ݱ���û�ж�Ӧ�ļ�¼�� ���׳��쳣
	 * 
	 * ����ͨ������Hibernate �����ļ�Hibernate.use_identifier_rollback Ϊtrue
	 * ʹɾ������� ����OID ��Ϊnull���õĲ��ࣩ
	 */
	@Test
	public void testDelete()
	{
		News news = (News) session.get(News.class, 1);
		session.delete(news);
		
		System.out.println(news);
	}
	/*
	 * ע�⣺
	 * 1.��OID ��Ϊnull �� �����ݱ��л�û�����Ӧ��¼�� ���׳�һ���쳣
	 * 2.�˽⣺ OID ֵ����id ��unsaved-value ����ֵ�Ķ��� Ҳ����Ϊ��һ���������
	 */
	@Test
	public void testSaveOrUpdate()
	{
		News news = new News("FFF","fff", new Date());
		news.setId(11);
		
		session.saveOrUpdate(news);
	}
	/*
	 * Update:
	 * 1. ������һ���־û����� ����Ҫ��ʾ���� update ������ ��Ϊ�ڵ��� Transaction ��
	 * commit ����ʱ�� �������� session �� flush ����
	 * 2. ����һ��������� ��Ҫ��ʾ���� session �� update ������ ���԰�һ������Ķ����Ϊ
	 * �־û�����
	 * ע�⣺
	 * 1. ���¿��� session ����Ҫ���µ������������ݱ�ļ�¼�Ƿ�һ�£� ���ᷢ�� UPDATE ���
	 * ������� update ��������äĿ�Ĵ��� update ����أ�����ֹ���󴥷����������� .hbm.xml �ļ��� class �ڵ�����
	 * select-before-update=true��Ĭ��Ϊ false���� ��ͨ������Ҫ���ø����ԣ�û�д�������Ӱ�����ܣ�
	 * 2. �����ݱ���û�ж�Ӧ�ļ�¼�� ���������� update ������ ���׳��쳣
	 * 3. �� update���� ��������һ���������ʱ����� session �Ļ������Ѿ�������ͬ�� OID �ĳ־û����� ���׳��쳣��
	 * ��Ϊ��session �����в����� ����OID ��ͬ�Ķ���
	 *
	 */
	@Test
	public void testUpdate()
	{
		News news = (News) session.get(News.class, 1);
		 
		transaction.commit();
		session.close();
		
		session = sessionFactory.openSession();
		transaction = session.beginTransaction();
		
		news.setAuthor("SUN");
		
		News news2 = (News) session.get(News.class, 1);
		
		session.update(news);
	}
	/*
	 * get VS load��
	 * 1.ִ�� get �������������ض��󣬶�ִ�� load ������ ����ʹ�øö���
	 *  �򲻻�����ִ�в�ѯ������ ������һ���������
	 * ��get ������������ load ���ӳټ�����
	 * 
	 * 2.load �������ܻ��׳��������쳣�� ����Ҫ��ʼ���������֮ǰ�Ѿ��ر��� Session
	 * 
	 * 3.�����ݱ���û�ж�Ӧ�ļ�¼��Session Ҳû�йرգ� ͬʱ��Ҫʹ�ö���ʱ
	 *  get ���� null load �׳��쳣
	 */
	@Test
	public void testLoad() 
	{
		News news = (News) session.load(News.class, 1);
		
//		System.out.println(news.getClass().getName());
		System.out.println(news);
	}
	@Test
	public void testGet()
	{
		News news = (News) session.get(News.class, 1);
		System.out.println(news);
	}
	/*
	 * persist Ҳ��ִ�� INSERT ����
	 * 
	 * �� save ����������
	 * �� persist ����֮ǰ���������Ѿ��� id �ˣ��򲻻�ִ�� INSERT�����׳��쳣
	 */
	@Test
	public void testPersist(){
		News news = new News();
		news.setTitle("DD");
		news.setAuthor("dd");
		news.setDate(new Date());
		
		news.setId(200);
		
		session.persist(news);
	}
	
	/*
	 * 1.save��������
	 * ��1����һ����ʱ�����Ϊ�־û�����
	 * ��2��Ϊ������� ID
	 * ��3���� flush ����ʱ�ᷢ��һ�� INSERT ���
	 * ��4���� save ����֮ǰ�� ID ����Ч��
	 * ��5���־û������ ID �ǲ��ܱ��޸ĵ�
	 */
	public void testSave()
	{
		News news = new News();
		news.setTitle("AA");
		news.setAuthor("aa");
		news.setDate(new Date());
		
		System.out.println(news);
		
		session.save(news);
		
		System.out.println(news);
	}
	
	@Test
	public void testClear()
	{
		News news = (News) session.get(News.class, 1);
		
		session.clear();//�������
		
		News news2 = (News) session.get(News.class, 1);
	}
	/*
	 * refresh() ��ǿ�Ʒ��� SELECT ��䣬��ʹ Session �����еĶ����״̬�����ݱ��ж�Ӧ�ļ�¼����һ��
	 */
	@Test
	public void testRefresh()
	{
		News news = (News) session.get(News.class, 1);
		System.out.println(news);
		
		session.refresh(news);
		System.out.println(news);
	}
	
	/*
	 * flush�� ʹ���ݱ��еļ�¼�� Session �����еĶ����״̬����һ�£�Ϊ�˱���һ�£�����ܻᷢ�Ͷ�Ӧ�� SQL ���
	 * 1. �� Transaction �� commit���� �����У� �ȵ��� session ��flush ���������ύ����
	 * 2. flush���� �������ܻᷢ�� SQL ��䣬�������ύ����
	 * 3. ע�⣺��δ�ύ�������ʾ�ĵ��� session.flush() ����֮ǰ��Ҳ���ܻ���� flush����������
	 * ��1�� ִ�� HQL �� QBC ��ѯ�� ���Ƚ��� flush���� ������ �Եõ����ݱ����µļ�¼
	 * ��2�� ����¼�� ID ���ɵײ����ݿ�ʹ�������ķ�ʽ�����ģ� ���ڵ��� save���� ����ʱ���ͻ��������� INSERT ���  
	 * ��Ϊ save ������ ���뱣֤����� ID �Ǵ��ڵ�
	 */
	@Test
	public void testSessionFlush2(){
		News news = new News("111","111",new Date());
		session.save(news);
	}
	
	@Test
	public void testSessionFlush()
	{
		News news = (News) session.get(News.class, 1);
		news.setAuthor("ben");
				
//		session.flush();
//		System.out.println("flush");
		
		News new2 = (News) session.createCriteria(News.class).uniqueResult();
		System.out.println(new2);
	}
	
	@Test
	public void testSessionCache() {
//		System.out.println("test");
		//Hibernate һ������ Session ����
		
		//�� Session �ӿڵ�ʵ���а�����һϵ�е� Java ���ϣ���Щ���Ϲ����� Session ���棬
		//ֻҪ Session ʵ��û�н������������ڣ���û�������������������Ķ���Ҳ���������������

		//Session ����ɼ��� Hibernate Ӧ�ó���������ݿ��
		News news = (News) session.get(News.class, 1);
		System.out.println(news);
		
		News news2 = (News) session.get(News.class, 1);
		System.out.println(news2);
		
	}

}
