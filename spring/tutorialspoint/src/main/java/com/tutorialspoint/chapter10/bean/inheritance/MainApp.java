/**
 * 注意区别 bean继承 和 类继承。
 * bean继承是针对对象，包括对象的属性
 * 类继承是针对模板，还没实例化。
 */

package com.tutorialspoint.chapter10.bean.inheritance;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainApp {
   public static void main(String[] args) {
//      ApplicationContext context = new ClassPathXmlApplicationContext("com/tutorialspoint/chapter10/Beans.xml");
      ApplicationContext context = new ClassPathXmlApplicationContext("file:spring/tutorialspoint/src/main/java/com/tutorialspoint/chapter10/bean/inheritance/Beans.xml");

      HelloWorld objA = (HelloWorld) context.getBean("helloWorld");

      objA.getMessage1();
      objA.getMessage2();

      HelloIndia objB = (HelloIndia) context.getBean("helloIndia");
      objB.getMessage1();
      objB.getMessage2();  // 注意这里
      objB.getMessage3();
   }
}