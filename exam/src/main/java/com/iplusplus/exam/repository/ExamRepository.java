package com.iplusplus.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iplusplus.exam.entity.Exam;

public interface ExamRepository extends JpaRepository<Exam, Integer> {

}