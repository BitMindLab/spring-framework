package com.tutorialspoint.chapter15.annotation.autowired.field;

import org.springframework.beans.factory.annotation.Autowired;

public class TextEditor {
	@Autowired
	private SpellChecker spellChecker;

	private SpellChecker2 spellChecker2;
	private String message;

	public TextEditor() {
		System.out.println("Inside TextEditor constructor.");
	}
	
	public SpellChecker getSpellChecker() {
		return spellChecker;
	}

	/*
	 * public void setSpellChecker( SpellChecker spellChecker ){
	 * this.spellChecker = spellChecker; }
	 */
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public SpellChecker2 getSpellChecker2() {
		return spellChecker2;
	}

	public void setSpellChecker2(SpellChecker2 spellChecker2) {
		this.spellChecker2 = spellChecker2;
	}
	
	public void spellCheck() {
		spellChecker.checkSpelling();
	}
}