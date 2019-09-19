package com.iplusplus.exam.dto;

import java.util.List;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@RequiredArgsConstructor
public final class ExamResultDTO {

	private final Integer id;
    private final Integer examId;
    @Setter
    private List<Integer> answers;
    @Setter
    private String userName;
    
    public ExamResultDTO() {
        this(null, null);
    }
}