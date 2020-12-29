package socialcoding.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import socialcoding.entity.QuizAttempt;

public interface QuizAttemptRepository extends JpaRepository<QuizAttempt, Long> {
}