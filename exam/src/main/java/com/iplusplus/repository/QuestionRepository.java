package com.iplusplus.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iplusplus.entity.Exam;
import com.iplusplus.entity.Question;

public interface QuestionRepository extends JpaRepository<Question, Integer> {

	List<Question> findByExamId(Integer examId);
    Question findFirstByExamAndIdNotIn(Exam exam, Set<Integer> ids);
    List<Integer> countByExamId(Integer examId);
	List<Question> findByExam(Exam exam);
}