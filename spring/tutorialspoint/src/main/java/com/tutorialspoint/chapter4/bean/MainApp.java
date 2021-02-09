package com.tutorialspoint.chapter4.bean;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;

public class MainApp {
    public static void main(String[] args) throws FileNotFoundException {
//        ApplicationContext context = new ClassPathXmlApplicationContext("spring/tutorialspoint/src/main/java/com/tutorialspoint/chapter4/bean/Beans.xml");
//        ApplicationContext context = new ClassPathXmlApplicationContext("file:spring/tutorialspoint/src/main/java/com/tutorialspoint/chapter4/bean/Beans.xml"); // 方式二
        ApplicationContext context = new FileSystemXmlApplicationContext("spring/tutorialspoint/src/main/java/com/tutorialspoint/chapter4/bean/Beans.xml"); // 等价于方式二

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