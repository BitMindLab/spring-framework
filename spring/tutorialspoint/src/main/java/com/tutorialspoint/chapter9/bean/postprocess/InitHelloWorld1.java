package com.tutorialspoint.chapter9.bean.postprocess;

import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.BeansException;

public class InitHelloWorld1 implements BeanPostProcessor {
	public InitHelloWorld1() {
		System.out.println(this.getClass().getSimpleName() + " is constructing");
	}

	public Object postProcessBeforeInitialization(Object bean, String beanName)
			throws BeansException {
		System.out.println("[" + this.getClass().getSimpleName() + "]"
				+ "BeforeInitialization : " + beanName);
		return bean; // you can return any other object as well
	}

	public Object postProcessAfterInitialization(Object bean, String beanName)
			throws BeansException {
		System.out.println("[" + this.getClass().getSimpleName() + "]"
				+ "AfterInitialization : " + beanName);
		return bean; // you can return any other object as well
	}
}