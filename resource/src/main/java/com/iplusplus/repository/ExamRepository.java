package com.iplusplus.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.iplusplus.domain.Exam;

@CrossOrigin(origins = "http://localhost:4200")
public interface ExamRepository extends JpaRepository<Exam, Integer> {

}
