package com.iplusplus.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iplusplus.domain.ExamResult;

public interface ExamResultRepository extends JpaRepository<ExamResult, Integer> {

}
