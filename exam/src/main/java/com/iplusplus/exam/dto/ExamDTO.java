package com.iplusplus.exam.dto;

import java.util.List;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@RequiredArgsConstructor
public final class ExamDTO {

	private final Long id;
    private final Integer examId;
    @Setter
    private List<Long> answers;
    @Setter
    private String username;
    
    public ExamDTO() {
        this(null, null);
    }
}