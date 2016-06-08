package com.ben.spring.beans.collection;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

	public static void main(String[] args) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		Person person = (Person) ctx.getBean("person5");
		System.out.println(person);
		
		NewPerson newPerson = (NewPerson) ctx.getBean("newPerson");
		System.out.println(newPerson);
		
		DataSource dataSource = (DataSource) ctx.getBean("dataSource");
		System.out.println(dataSource);
	}
	
}
