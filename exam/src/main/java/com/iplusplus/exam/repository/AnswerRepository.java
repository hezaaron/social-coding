package com.iplusplus.exam.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iplusplus.exam.entity.Answer;

public interface AnswerRepository extends JpaRepository<Answer, Long> {

    List<Answer> findByQuestionId(Long questionId);
    List<Answer> findByQuestionExamId(Integer examId);
    List<Answer> findByQuestionExamIdAndCorrect(Integer examId, Boolean isCorrect);

}