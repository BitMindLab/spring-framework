package com.tutorialspoint.chapter8.bean.lifecycle;

public class HelloWorld {
	private String message;
	public HelloWorld() {
		System.out.println("creating bean HelloWorld8");
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void getMessage() {
		System.out.println("Your Message : " + message);
	}

	public void init() {
		System.out.println("Bean8 is going through init.");
	}

	public void destroy() {
		System.out.println("Bean8 will destroy now.");
	}
}