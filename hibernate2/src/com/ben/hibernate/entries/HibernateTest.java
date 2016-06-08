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
	//生产环境中Session 和Transaction 不能作为成员变量，会有并发问题

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
//		InputStream stream = new FileInputStream("QQ图片20160329220537.jpg");
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
				
				//调用存储过程
			}
		});
	}
	/*
	 * evict：从session 缓存中把指定的持久化对象移除
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
	 * delete: 执行删除操作， 只要OID 和数据表中一条记录对应， 就会准备执行delete 操作
	 * 若OID 在数据表中没有对应的记录， 则抛出异常
	 * 
	 * 可以通过设置Hibernate 配置文件Hibernate.use_identifier_rollback 为true
	 * 使删除对象后， 把其OID 设为null（用的不多）
	 */
	@Test
	public void testDelete()
	{
		News news = (News) session.get(News.class, 1);
		session.delete(news);
		
		System.out.println(news);
	}
	/*
	 * 注意：
	 * 1.若OID 不为null ， 但数据表中还没有其对应记录， 会抛出一个异常
	 * 2.了解： OID 值等于id 的unsaved-value 属性值的对象， 也被认为是一个游离对象
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
	 * 1. 若更新一个持久化对象， 不需要显示调用 update 方法， 因为在调用 Transaction 的
	 * commit 方法时， 会先自行 session 的 flush 方法
	 * 2. 更新一个游离对象， 需要显示调用 session 的 update 方法， 可以把一个游离的对象变为
	 * 持久化对象
	 * 注意：
	 * 1. 重新开的 session 无论要更新的游离对象和数据表的记录是否一致， 都会发送 UPDATE 语句
	 * 如何能让 update 方法不再盲目的触发 update 语句呢？（防止错误触发触发器）在 .hbm.xml 文件的 class 节点设置
	 * select-before-update=true（默认为 false）， 但通常不需要设置该属性（没有触发器会影响性能）
	 * 2. 若数据表中没有对应的记录， 但还调用了 update 方法， 会抛出异常
	 * 3. 当 update（） 方法关联一个游离对象时，如果 session 的缓存中已经存在相同的 OID 的持久化对象， 会抛出异常，
	 * 因为在session 缓存中不能有 两个OID 相同的对象
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
	 * get VS load：
	 * 1.执行 get 方法会立即加载对象，而执行 load 方法， 若不使用该对象，
	 *  则不会立即执行查询操作， 而返回一个代理对象
	 * （get 是立即检索， load 是延迟检索）
	 * 
	 * 2.load 方法可能会抛出懒加载异常： 在需要初始化代理对象之前已经关闭了 Session
	 * 
	 * 3.若数据表中没有对应的记录，Session 也没有关闭， 同时需要使用对象时
	 *  get 返回 null load 抛出异常
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
	 * persist 也会执行 INSERT 操作
	 * 
	 * 和 save 方法的区别：
	 * 在 persist 方法之前，若对象已经有 id 了，则不会执行 INSERT，而抛出异常
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
	 * 1.save（）方法
	 * （1）是一个临时对象变为持久化对象
	 * （2）为对象分配 ID
	 * （3）在 flush 缓存时会发送一条 INSERT 语句
	 * （4）在 save 方法之前的 ID 是无效的
	 * （5）持久化对象的 ID 是不能被修改的
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
		
		session.clear();//清除缓存
		
		News news2 = (News) session.get(News.class, 1);
	}
	/*
	 * refresh() 会强制发送 SELECT 语句，以使 Session 缓存中的对象的状态和数据表中对应的记录保持一致
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
	 * flush： 使数据表中的记录和 Session 缓存中的对象的状态保持一致，为了保持一致，则可能会发送对应的 SQL 语句
	 * 1. 在 Transaction 的 commit（） 方法中： 先调用 session 的flush 方法，再提交事务
	 * 2. flush（） 方法可能会发送 SQL 语句，但不会提交事务
	 * 3. 注意：在未提交事务或显示的调用 session.flush() 方法之前，也可能会进行 flush（）操作，
	 * （1） 执行 HQL 或 QBC 查询， 会先进行 flush（） 操作， 以得到数据报最新的记录
	 * （2） 若记录的 ID 是由底层数据库使用自增的方式生产的， 则在调用 save（） 方法时，就会立即发送 INSERT 语句  
	 * 因为 save 方法后， 必须保证对象的 ID 是存在的
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
		//Hibernate 一级缓存 Session 级别
		
		//在 Session 接口的实现中包含了一系列的 Java 集合，这些集合构成了 Session 缓存，
		//只要 Session 实例没有结束生命中周期，且没有清理缓存则放在它缓存的对象也不会结束生命周期

		//Session 缓存可减少 Hibernate 应用程序访问数据库的
		News news = (News) session.get(News.class, 1);
		System.out.println(news);
		
		News news2 = (News) session.get(News.class, 1);
		System.out.println(news2);
		
	}

}
