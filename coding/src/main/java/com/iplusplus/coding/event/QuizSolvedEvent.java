package com.iplusplus.coding.event;

import java.io.Serializable;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class QuizSolvedEvent implements Serializable {

	private static final long serialVersionUID = 4419489739197516400L;
	private final Long quizResultId;
    private final String username;
    private final boolean solved;
}