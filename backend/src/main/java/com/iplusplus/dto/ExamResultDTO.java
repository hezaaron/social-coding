package com.iplusplus.dto;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor(force = true)
public final class ExamResultDTO {

	private final Integer id;
    private final Integer examId;
    @Setter
    private List<Integer> answers;
    
    public ExamResultDTO(Integer id, Integer examId) {
        this.id = id;
        this.examId = examId;
    }
}