package com.iplusplus.dto;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public final class ExamResultDTO {

	private Integer id;
    private Integer examId;
    private List<Integer> answers;
    
    public ExamResultDTO(Integer id, Integer examId) {
        this.id = id;
        this.examId = examId;
    }
}
