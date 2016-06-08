package com.ben.spring.beans;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

	public static void main(String[] args) {
		
		//HelloWorld helloWorld = new HelloWorld();
	//	helloWorld.setName("ben");
		
		//1. 创建Spring 的IOC 容器对象
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		
		//2. 从IOC 容器中取Bean 的实例
//		HelloWorld helloWorld = (HelloWorld) ctx.getBean("helloWorld");
		//利用类型返回IOC 容器中的Bean 但要去IOC 容器中只能有一个
		HelloWorld helloWorld = ctx.getBean(HelloWorld.class);
		
		//3. 调用hello 方法
//		helloWorld.hello();
		
		Car car = (Car) ctx.getBean("car");
		System.out.println(car);
		
		Car car2 = (Car) ctx.getBean("car2");
		System.out.println(car2);
		
		Person person = (Person) ctx.getBean("person2");
		System.out.println(person);
		
	}
	
}
