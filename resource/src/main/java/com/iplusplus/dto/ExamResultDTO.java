package com.iplusplus.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@SuppressWarnings("unused")
public final class ExamDTO {

	private Integer id;
    private Integer examId;
    private List<Integer> answers;
    
    public ExamDTO(Integer id, Integer examId) {
        this.id = id;
        this.examId = examId;
    }
}
