package com.tutorialspoint.chapter7.bean.scope;

public class HelloWorld {
	private String message;
	
	public HelloWorld() {
		System.out.println("creating bean HelloWorld7");
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void getMessage() {
		System.out.println("Your Message : " + message);
	}
}