package socialcoding.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import socialcoding.entity.Answer;
import socialcoding.entity.Question;
import socialcoding.entity.Quiz;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class RepositoryIntegrationTest {

    
    @Autowired private QuizRepository quizRepository;
    @Autowired private QuizAttemptRepository quizAttemptRepository;
    @Autowired private QuestionRepository questionRepository;
    @Autowired private AnswerRepository answerRepository;
    private Quiz quiz;
    
    @BeforeEach
    void setUp() throws Exception {
        FixtureFactoryLoader.loadTemplates("socialcoding.template");
        quiz = Fixture.from(Quiz.class).gimme("valid");
    }
    
    @AfterEach
    void tearDown() throws Exception {
        quizAttemptRepository.deleteAll();
        answerRepository.deleteAll();
        questionRepository.deleteAll();
        quizRepository.deleteAll();
    }
    
    @Test
    void shouldSaveAndFetchQuiz() {
        quizRepository.save(quiz);
        List<Quiz> quizzes = quizRepository.findAll();
        
        assertAll("quizzes", () -> assertFalse(quizzes.isEmpty()),
                             () -> assertTrue(quizzes.contains(quiz)),
                             () -> assertEquals(1, quizzes.size()));
    }
    
    @Test
    void shouldSaveAndFetchQuestion() {
        quizRepository.save(quiz);
        Question question = Fixture.from(Question.class).gimme("valid");
        
        questionRepository.save(question);
        List<Question> questions = questionRepository.findAll();
        
        assertAll("questions", () -> assertFalse(questions.isEmpty()),
                               () -> assertTrue(questions.get(0) instanceof Question),
                               () -> assertEquals(1, questions.size()));
    }
    
    @Test
    void shouldSaveAndFetchAnswer() {
        quizRepository.save(quiz);
        Question question = Fixture.from(Question.class).gimme("valid");
        questionRepository.save(question);
        Answer answer = Fixture.from(Answer.class).gimme("valid");
        long questionId = answer.getQuestion().getId();
        
        answerRepository.save(answer);
        List<Answer> answers = answerRepository.findByQuestionId(questionId);
        
        assertAll("answers", () -> assertFalse(answers.isEmpty()),
                             () -> assertTrue(answers.get(0) instanceof Answer),
                             () -> assertEquals(1, answers.size()));
    }
}
