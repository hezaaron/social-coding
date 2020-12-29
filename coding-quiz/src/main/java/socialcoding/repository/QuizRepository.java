package socialcoding.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import socialcoding.entity.Quiz;

public interface QuizRepository extends JpaRepository<Quiz, Integer> {
}