package com.iplusplus.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iplusplus.entity.Answer;

public interface AnswerRepository extends JpaRepository<Answer, Integer> {

    List<Answer> findByQuestionId(Integer questionId);
    List<Answer> findByQuestionExamId(Integer examId);
    List<Answer> findByQuestionExamIdAndCorrect(Integer examId, Boolean isCorrect);

}