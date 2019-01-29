package com.iplusplus.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iplusplus.entity.Exam;

public interface ExamRepository extends JpaRepository<Exam, Integer> {

}
