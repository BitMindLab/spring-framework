package com.tutorialspoint.chapter15.annotation.autowired.field;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
public class MainApp {
   public static void main(String[] args) {
      ApplicationContext context = new ClassPathXmlApplicationContext("com/tutorialspoint/chapter15/annotation/autowired/field/Beans.xml");
      TextEditor te = (TextEditor) context.getBean("textEditor");
      //TextEditor te = (TextEditor) context.getBean(TextEditor.class);
      te.spellCheck();
   }
}