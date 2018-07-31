package com.iplusplus.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iplusplus.domain.Exam;
import com.iplusplus.domain.Question;

public interface QuestionRepository extends JpaRepository<Question, Integer> {

	List<Question> findByExam(Exam exam);
    List<Question> findByExamId(Integer examId);
    Question findFirstByExamAndIdNotIn(Exam exam, Set<Integer> ids);
    int countByExam(Exam exam);
}
