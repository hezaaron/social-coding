package com.iplusplus.gamification.event;

import java.io.Serializable;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data @RequiredArgsConstructor
public class QuizSolvedEvent implements Serializable {

	private static final long serialVersionUID = -5633123394142170126L;
	private final Long resultId;
    private final String username;
    private final boolean solved;
}