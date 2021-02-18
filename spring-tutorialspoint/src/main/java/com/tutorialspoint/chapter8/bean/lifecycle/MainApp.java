package com.tutorialspoint.chapter8.bean.lifecycle;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainApp {
   public static void main(String[] args) {
      AbstractApplicationContext context = 
    		  new ClassPathXmlApplicationContext("com/tutorialspoint/chapter8/bean/lifecycle/Beans.xml");
      System.out.println(context.getBeanDefinitionNames()[0]);
      HelloWorld obj = (HelloWorld) context.getBean("helloWorld");
      obj.getMessage();
      context.registerShutdownHook();
   }
}