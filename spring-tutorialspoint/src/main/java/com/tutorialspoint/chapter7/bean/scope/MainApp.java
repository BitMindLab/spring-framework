/**
 * 注意 Beans.xml中的scope字段，
 *
 * ## singleton 作用域 (默认)
 * 如果作用域设置为 singleton，那么 Spring IoC 容器刚好创建一个由该 bean 定义的对象的实例。
 * 该单一实例将存储在这种单例 bean 的高速缓存中，以及针对该 bean 的所有后续的请求和引用都返回缓存对象。
 *
 *
 * ## prototype 作用域
 *
 *
 */

package com.tutorialspoint.chapter7.bean.scope;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
public class MainApp {
   public static void main(String[] args) {
//      ApplicationContext context = new ClassPathXmlApplicationContext("com/tutorialspoint/chapter7/bean/scope/Beans.xml");
//      ApplicationContext context = new ClassPathXmlApplicationContext("file:src/main/java/com/tutorialspoint/chapter7/bean/scope/Beans.xml");
      ApplicationContext context = new ClassPathXmlApplicationContext("file:spring-tutorialspoint/src/main/java/com/tutorialspoint/chapter7/bean/scope/Beans.xml");
      HelloWorld objA = (HelloWorld) context.getBean("helloWorld");
      objA.setMessage("I'm object A");
      objA.getMessage();
      HelloWorld objB = (HelloWorld) context.getBean("helloWorld");
      objB.getMessage();
   }
}