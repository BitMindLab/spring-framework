package com.tutorialspoint.chapter15.annotation.autowired.field;

import org.springframework.beans.factory.annotation.Autowired;

public class SpellChecker {
	int q;	
	public SpellChecker() {
		System.out.println("Inside SpellChecker constructor.");
		q=6;
	}

	public void checkSpelling() {
		System.out.println("Inside checkSpelling.");
	}
}