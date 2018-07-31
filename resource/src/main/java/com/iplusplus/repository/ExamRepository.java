package com.iplusplus.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iplusplus.domain.Exam;

public interface ExamRepository extends JpaRepository<Exam, Integer> {

}
