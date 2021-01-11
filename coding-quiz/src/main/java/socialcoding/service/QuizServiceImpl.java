package socialcoding.service;

import java.time.Clock;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import socialcoding.dto.QuizDTO;
import socialcoding.entity.Answer;
import socialcoding.entity.Question;
import socialcoding.entity.Quiz;
import socialcoding.entity.QuizAttempt;
import socialcoding.model.QuizSetter;
import socialcoding.model.QuizTimer;
import socialcoding.model.RandomQuestion;
import socialcoding.repository.AnswerRepository;
import socialcoding.repository.QuestionRepository;
import socialcoding.repository.QuizAttemptRepository;
import socialcoding.repository.QuizRepository;

@Service
@RequiredArgsConstructor
public class QuizServiceImpl implements QuizService {
	
	private final QuizRepository quizRepository;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    private final QuizAttemptRepository quizAttemptRepository;
   
    @Override
    public List<Quiz> getAllQuiz() {
    	return quizRepository.findAll();
    }
    
    @Override
    public QuizSetter setQuiz(final Integer quizId, final HttpServletRequest request) {
    	final QuizAttempt quizAttempt = createQuizAttempt(quizId);
    	request.getSession().setAttribute("quizStarted", quizAttempt.getStartTime());
        final long quizAttemptId = getQuizAttemptId(quizAttempt);
        
        final Quiz quiz = quizAttempt.getQuiz();
        final int timer = new QuizTimer().getRemainingTime(request);
        final QuizDTO quizDto = new QuizDTO(quizAttemptId, quizId);
        
        return new QuizSetter(quiz.getName(), timer, quizDto);
    }
    
    private QuizAttempt createQuizAttempt(final Integer quizId) {
    	final Quiz quiz = getQuiz(quizId);
    	final List<Question> questions = getQuestions(quizId);
    	return new QuizAttempt(null, quiz, questions.size(), new QuizTimer(Clock.systemDefaultZone()).getTime());
    }
    
    @Override
    public Quiz getQuiz(final Integer id) {
    	return quizRepository.getOne(id);
    }
    
    @Transactional
    private Long getQuizAttemptId(final QuizAttempt quizAttempt) {
    	quizAttemptRepository.saveAndFlush(quizAttempt);
        return quizAttempt.getId();
    }
    
    @Override
    public List<Question> getQuestions(final Integer quizId) {
    	final List<Question> questions = questionRepository.findByQuizId(quizId);
    	final int questionSize = Mark.NUMBER_OF_QUESTION;
    	return new RandomQuestion(questions, questionSize).getList();
    }
    
    @Override
    public List<Answer> getAnswers(final Integer quizId) {
    	return answerRepository.findByQuestionQuizId(quizId);
    }
}