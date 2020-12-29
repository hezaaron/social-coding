package socialcoding.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import socialcoding.entity.Answer;

public interface AnswerRepository extends JpaRepository<Answer, Long> {

    List<Answer> findByQuestionId(Long questionId);
    List<Answer> findByQuestionQuizId(Integer quizId);
    List<Answer> findByQuestionQuizIdAndCorrect(Integer quizId, Boolean isCorrect);

}