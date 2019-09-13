package com.iplusplus.exam.model;

public class Mark {

	public static final int MAX_MARK= 100;
	public static final int NUMBER_OF_QUESTION = 5;
	public static final int PASS_MARK = 50;
	
	private Mark() {}
	
	public static float getMarkPerQuestion() {
		return (float) MAX_MARK / NUMBER_OF_QUESTION;
	}
}
