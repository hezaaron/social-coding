package com.iplusplus.model;

public class Mark {

	public static final int MAX_MARK= 100;
	public static final int NUMBER_OF_QUESTION = 5;
	
	private Mark() {}
	
	public static float getMarkPerQuestion() {
		return (float) MAX_MARK / NUMBER_OF_QUESTION;
	}
}
