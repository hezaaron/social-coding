package com.iplusplus.dto;

import java.io.Serializable;

import com.iplusplus.domain.Question;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@SuppressWarnings("unused")
public class QuestionDTO {

	private Integer id;
    private String name;
    private boolean multiAnswer = false;
    
    public QuestionDTO() {
    }

    public QuestionDTO(Question question) {
        this(question.getId(), question.getName(), question.isMultiAnswer());
    }

    public QuestionDTO(Integer id, String name, boolean multiAnswer) {
        this.id = id;
        this.name = name;
        this.multiAnswer = multiAnswer;
    }    
    
}
