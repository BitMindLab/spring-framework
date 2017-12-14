package com.tutorialspoint.chapter4.bean;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;

public class MainApp {
	public static void main(String[] args) {
			ApplicationContext context = new ClassPathXmlApplicationContext(
					"com/tutorialspoint/chapter4/bean/Beans.xml");
		
		HelloWorld obj = (HelloWorld) context.getBean("helloWorld");
		//HelloWorld obj = (HelloWorld) context.getBean(HelloWorld.class);
		obj.getMessage();
		

		// BeanFactory是beanfactory的超集，推荐用ApplicationContext
//		XmlBeanFactory factory = new XmlBeanFactory(new ClassPathResource(
//				"Beans.xml"));
//		HelloWorld obj2 = (HelloWorld) factory.getBean("helloWorld4");
//		obj2.getMessage();
	}
}