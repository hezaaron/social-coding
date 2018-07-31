package com.iplusplus.util;

import java.util.List;
import java.util.stream.Collectors;

import com.iplusplus.domain.Answer;
import com.iplusplus.domain.Question;
import com.iplusplus.dto.EntryDTO;
import com.iplusplus.dto.QuestionDTO;

public class Converter {

	public static List<QuestionDTO> questionsToDTO(List<Question> questions) {
        return questions.stream().map(q -> new QuestionDTO(q)).collect(Collectors.toList());
    }
    
    public static List<EntryDTO> answersToDTO(List<Answer> answers) {
        return answers.stream().map(q -> new EntryDTO(q.getId(), q.getName())).collect(Collectors.toList());
    }
}