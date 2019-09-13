package com.iplusplus.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iplusplus.exam.entity.ExamResult;

public interface ExamResultRepository extends JpaRepository<ExamResult, Integer> {
}