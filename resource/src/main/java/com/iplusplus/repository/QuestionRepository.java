package com.iplusplus.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.iplusplus.domain.Exam;
import com.iplusplus.domain.Question;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer> {

	List<Question> findByExamId(Integer examId);
    Question findFirstByExamAndIdNotIn(Exam exam, Set<Integer> ids);
    List<Integer> countByExamId(Integer examId);
	List<Question> findByExam(Exam exam);
}
