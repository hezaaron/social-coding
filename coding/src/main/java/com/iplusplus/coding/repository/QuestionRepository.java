package com.iplusplus.coding.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iplusplus.coding.entity.Question;
import com.iplusplus.coding.entity.Quiz;

public interface QuestionRepository extends JpaRepository<Question, Long> {

	List<Question> findByQuizId(Integer quizId);
	List<Question> findByQuiz(Quiz quiz);
}