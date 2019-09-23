package com.iplusplus.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iplusplus.exam.entity.ExamProtocol;

public interface ExamProtocolRepository extends JpaRepository<ExamProtocol, Long> {
}