package com.tutorialspoint.chapter4.bean;

public class HelloWorld {
    private String message;

    public HelloWorld() {
        System.out.println("creating bean HelloWorld4");
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void getMessage() {
        System.out.println("Your Message : " + message);
    }
}