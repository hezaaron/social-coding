package com.iplusplus.coding.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iplusplus.coding.entity.Quiz;

public interface QuizRepository extends JpaRepository<Quiz, Integer> {
}