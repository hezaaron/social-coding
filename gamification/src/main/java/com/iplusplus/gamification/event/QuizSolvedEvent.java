package com.iplusplus.gamification.event;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class QuizSolvedEvent implements Serializable {

	private static final long serialVersionUID = -5633123394142170126L;
    private final String username;
    private final int score;
	
	public QuizSolvedEvent(@JsonProperty("username") String username,
						   @JsonProperty("score") int score) {
		this.username = username;
		this.score = score;
	}
}