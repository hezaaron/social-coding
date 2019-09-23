package com.iplusplus.exam.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iplusplus.exam.entity.Exam;
import com.iplusplus.exam.entity.Question;

public interface QuestionRepository extends JpaRepository<Question, Long> {

	List<Question> findByExamId(Integer examId);
    Question findFirstByExamAndIdNotIn(Exam exam, Set<Integer> ids);
    List<Integer> countByExamId(Integer examId);
	List<Question> findByExam(Exam exam);
}