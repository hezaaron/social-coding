package com.iplusplus.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iplusplus.entity.Answer;
import com.iplusplus.entity.Question;

public interface AnswerRepository extends JpaRepository<Answer, Integer> {

	List<Answer> findByQuestion(Question question);
    List<Answer> findByQuestionId(Integer questionId);
    List<Answer> findByQuestionExamIdAndCorrect(Integer examId, Boolean isCorrect);
}
