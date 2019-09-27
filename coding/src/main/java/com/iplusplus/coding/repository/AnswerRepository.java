package com.iplusplus.coding.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iplusplus.coding.entity.Answer;

public interface AnswerRepository extends JpaRepository<Answer, Long> {

    List<Answer> findByQuestionId(Long questionId);
    List<Answer> findByQuestionQuizId(Integer quizId);
    List<Answer> findByQuestionQuizIdAndCorrect(Integer quizId, Boolean isCorrect);

}