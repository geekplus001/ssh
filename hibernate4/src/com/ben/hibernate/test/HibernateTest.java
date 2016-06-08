package com.ben.hibernate.test;


import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.jdbc.Work;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.ben.hibernate.dao.DepartmentDao;
import com.ben.hibernate.entities.Department;
import com.ben.hibernate.entities.Employee;
import com.ben.hibernate.hibernate.HibernateUtils;


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
	//批量操作
	@Test
	public void testBatch()
	{
		session.doWork(new Work() {
			
			@Override
			public void execute(Connection arg0) throws SQLException {
				//使用原生JDBC API 操作效率最高
				
			}
		});
		
	}
	@Test
	public void testManageSession()
	{
		//获取Session
		//开启事务
		Session session = HibernateUtils.getInstance().getSession();
		System.out.println("-->" + session.hashCode());
		Transaction transaction = session.beginTransaction();
		
		DepartmentDao departmentDao = new DepartmentDao();
		
		Department department = new Department();
		department.setName("ben");
		departmentDao.save(department);
		departmentDao.save(department);
		departmentDao.save(department);
		
		//若Session 是由thread 管理的， 则在提交或回滚事务时， 已经关闭Session
		transaction.commit();
		System.out.println(session.isOpen());
	}
	@Test
	public void testQueryIterate()
	{
		Department department = (Department) session.get(Department.class, 80);
		System.out.println(department.getName());
		System.out.println(department.getEmps().size());
		
		Query query = session.createQuery("FROM Employee e WHERE e.dept.id = 80");
//		List<Employee> employees = query.list();
//		System.out.println(employees.size());
		
		Iterator<Employee> eIterator = query.iterate();
		while(eIterator.hasNext())
		{
			System.out.println(eIterator.next().getName());
		}
	}
	//更新时间戳缓存
	@Test
	public void testUpdateTimeStampCache()
	{
		Query query = session.createQuery("FROM Employee");
		query.setCacheable(true);
		
		List<Employee> employees = query.list();
		System.out.println(employees.size());
		
		Employee employee = (Employee) session.get(Employee.class, 100);
		employee.setSalary(30000);
		
		employees = query.list();
		System.out.println(employees.size());
	}
	//查询缓存
	@Test
	public void testQueryCache()
	{
		Query query = session.createQuery("FROM Employee");
		query.setCacheable(true);
		
		List<Employee> employees = query.list();
		System.out.println(employees.size());
		
		employees = query.list();
		System.out.println(employees.size());
		
		Criteria criteria = session.createCriteria(Employee.class);
		criteria.setCacheable(true);
	}
	//集合级别的二级缓存
	public void testCollectionSecondLevelCache()
	{
		Department department = (Department) session.get(Department.class, 80);
		System.out.println(department.getName());
		System.out.println(department.getEmps().size());
		
		session.close();
		sessionFactory.close();
		
		session = sessionFactory.openSession();
		transaction = session.beginTransaction();
		
		
		Department department2 = (Department) session.get(Department.class, 80);
		System.out.println(department2.getName());
		System.out.println(department2.getEmps().size());
	}
	//类级别的二级缓存
	@Test
	public void testHibernateSecondLevelCache()
	{
		Employee employee = (Employee) session.get(Employee.class, 100);
		System.out.println(employee.getName());
		
		session.close();
		sessionFactory.close();
		
		session = sessionFactory.openSession();
		transaction = session.beginTransaction();
		
		Employee employee2 = (Employee) session.get(Employee.class, 100);
		System.out.println(employee2.getName());
	}
	@Test
	public void testNativeSQL()
	{
		String sql = "INSERT INTO department VALUES(?,?)";
		Query query = session.createSQLQuery(sql);
		
		query.setInteger(0, 80)
			 .setString(1, "geekplus")
			 .executeUpdate();
	}
	@Test
	public void testQBCUpdate()
	{
		String hql = "DELETE FROM Department d WHERE d.id = :id";
		
		session.createQuery(hql)
		       .setInteger("id", 80)
		       .executeUpdate();
	}
	@Test
	public void testQBC4()
	{
		Criteria criteria = session.createCriteria(Employee.class);
		
		//1. 添加排序
		criteria.addOrder(Order.asc("salary"));
		criteria.addOrder(Order.desc("email"));
		
		//2. 添加反野方法
		int pageSize = 5;
		int pageNum = 3;
		criteria.setFirstResult(0)//(pageNum-1)*pageSize
				.setMaxResults(pageSize)
				.list();
		
	}
	@Test
	public void testQBC3()
	{
		Criteria criteria = session.createCriteria(Employee.class);
		
		//统计查询： 使用Projection 来表示： 可以由Projections 的静态方法得到
		criteria.setProjection(Projections.max("salary"));
		
		System.out.println(criteria.uniqueResult());
	}
	@Test
	public void testQBC2()
	{
		Criteria criteria = session.createCriteria(Employee.class);
		
		//1. AND 使用Conjunction 标准
		//Conjunction 本身就是一个Criteria 对象
		//且其中还可以添加Criteria 对象
		Conjunction conjunction = Restrictions.conjunction();
		conjunction.add(Restrictions.like("name", "a",MatchMode.ANYWHERE));
		Department department = new Department();
		department.setId(80);
		conjunction.add(Restrictions.eq("dept", department));
		System.out.println(conjunction);
		
		//2. OR
		Disjunction disjunction = Restrictions.disjunction();
		disjunction.add(Restrictions.ge("salary", 6000F));
		disjunction.add(Restrictions.isNull("email"));
		
		criteria.add(conjunction);
		criteria.add(disjunction);
		
		criteria.list();
	}
	//QBC查询
	@Test
	public void testQBC()
	{
		//1. 创建一个Criteria 对象
		Criteria criteria = session.createCriteria(Employee.class);
		
		//2. 添加查询条件： 在ＱＢＣ　中查询条件使用Ｃｒｉｔｅｒｉｏｎ 来表示
		//Criterion 可以通过Restrictions 的静态方法得到
		criteria.add(Restrictions.eq("email", "geekplus"));
		criteria.add(Restrictions.gt("salary", 5000F));
		
		//3. 执行查询
		Employee employee = (Employee) criteria.uniqueResult();
		System.out.println(employee);
	}
	//左外链接
	@Test
	public void testLeftJoin()
	{
		String hql = "SELECT DISTINCT d FROM Department d LEFT JOIN d.emps";
		Query query = session.createQuery(hql);
		
		List<Department> departments = query.list();
		System.out.println(departments.size());
		
		for(Department department: departments)
		{
			System.out.println(department.getName() + ", " + department.getEmps().size());
		}
		
//		List<Object[]> result = query.list();
//		result = new ArrayList<>(new LinkedHashSet<>(result));
//		System.out.println(result);
//		for(Object[] objects:result)
//		{
//			System.out.println(Arrays.asList(objects));
//		}
	}
	//迫切左外链接
	@Test
	public void testLeftJoinFetch()
	{
//		String hql = "SELECT DISTINCT d FROM Department d LEFT JOIN FETCH d.emps";//去重方法一
		String hql = "FROM Department d LEFT JOIN FETCH d.emps";
		Query query = session.createQuery(hql);
		
		List<Department> depts = query.list();
		depts = new ArrayList<>(new LinkedHashSet<>(depts));//去重方法二
		System.out.println(depts.size());
		
		for(Department department:depts)
		{
			System.out.println(department.getName() + "-" + department.getEmps().size());
		}
	}
	//报表查询
	@Test
	public void testGroupBy()
	{
		String hql = "SELECT min(e.salary), max(e.salary)"
				+ "FROM Employee e"
				+ "GROUP BY e.dept"
				+ "HAVING min(salary) > :minSal";//聚集函数
		
		Query query = session.createQuery(hql)
							  .setFloat("minSal", 5000);
		List<Object[]> objs = query.list();
		for(Object[] objects:objs)
		{
			System.out.println(Arrays.asList(objects));
		}
	}
	public void testFieldQuery2()
	{
		String hql = "SELECT new Employee(e.email, e.salary, e.dept)"
				+ " FROM Employee e "
				+ "WHERE e.dept = :dept";
		Query query = session.createQuery(hql);
		
		Department department = new Department();
		department.setId(80);
		List<Employee> result = query.setEntity("dept", department)
								     .list();
		
		for(Employee employee: result)
		{
			System.out.println(employee.getId() + ", " + employee.getEmail() + 
					", " + employee.getSalary() + ", " + employee.getDept());
		}
	}
	//投影查询
	public void testFieldQuery()
	{
		String hql = "SELECT e.email, e.salary, e.dept FROM Employee e WHERE e.dept = :dept";
		Query query = session.createQuery(hql);
		
		Department department = new Department();
		department.setId(80);
		List<Object[]> result = query.setEntity("dept", department)
								     .list();
		
		for(Object[] objects: result)
		{
			System.out.println(Arrays.asList(objects));
		}
	}
	//命名查询:可放在hibernate 映射文件中
	@Test
	public void testNamedQuery()
	{
		Query query = session.getNamedQuery("salaryEmps");
		
		List<Employee> emps = query.setFloat("minSal", 5000)
								   .setFloat("maxSal", 10000)
								   .list();
		
		System.out.println(emps);
	}
	@Test
	public void testPageQuery()
	{
		String hql = "FROM Employee";
		Query query = session.createQuery(hql);
		
		int pageNum = 3;
		int pageSize = 5;
		
		List<Employee> emps = query.setFirstResult(0)
								   .setMaxResults(pageSize)
								   .list();
		System.out.println(emps);
	}
	@Test
	public void testHQLNamedParameter()
	{
		//1. 创建Query 对象
		//基于命名的参数
		String hql = "FROM Employee e WHERE e.salary > :sal AND e.email LIKE :email";
		Query query = session.createQuery(hql);
		
		//2. 绑定参数
		query.setFloat("sal", 6000)
			 .setString("email", "%%");
		
		//3. 执行查询
		List<Employee> emps = query.list();
		System.out.println(emps.size());
	}
	@Test
	public void testHQL()
	{
		//1. 创建Query 对象
		//基于位置的参数
		String hql = "FROM Employee e WHERE e.salary > ?"
				+ " AND e.email LIKE ? AND e.dept = ? ORDER BY e,salary";
		Query query = session.createQuery(hql);
		
		//2. 绑定参数
		//query 对象调用setXxx 方法支持方法链的编程风格
		Department department = new Department();
		department.setId(80);
		query.setFloat(0, 6000)
			 .setString(1, "%%")
			 .setEntity(2, department);
		
		//3. 执行查询
		List<Employee> emps = query.list();
		System.out.println(emps.size());
	}
}
