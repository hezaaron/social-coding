package socialcoding.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import socialcoding.entity.Question;
import socialcoding.entity.Quiz;

public interface QuestionRepository extends JpaRepository<Question, Long> {

	List<Question> findByQuizId(Integer quizId);
	List<Question> findByQuiz(Quiz quiz);
}