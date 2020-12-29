package socialcoding.service;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import socialcoding.entity.Answer;
import socialcoding.entity.QuizAttempt;
import socialcoding.event.EventDispatcher;
import socialcoding.event.QuizSolvedEvent;
import socialcoding.model.QuizReport;
import socialcoding.repository.AnswerRepository;
import socialcoding.repository.QuizAttemptRepository;

@Service @Slf4j
@RequiredArgsConstructor
public class ResultServiceImpl implements ResultService {

	private final QuizAttemptRepository quizAttemptRepository;
	private final AnswerRepository answerRepository;
    private final EventDispatcher eventDispatcher;
	
    @Override
	public QuizAttempt getQuizAttempt(final Long id) {
		return quizAttemptRepository.getOne(id);
	}
    
    @Override
	@Transactional
    public QuizAttempt updateQuizAttempt(final QuizAttempt quizAttempt) {
    	log.info("Sending quiz solved event for user: {}", quizAttempt.getUser());
    	eventDispatcher.send(new QuizSolvedEvent(quizAttempt.getUser(), quizAttempt.getGrade()));
        return quizAttemptRepository.save(quizAttempt);
    }

	@Override
	public void computeGrade(final QuizAttempt quizAttempt, final Integer quizId, final List<Long> userAnswers, final String user) {
		final List<Answer> answers = answerRepository.findByQuestionQuizIdAndCorrect(quizId, true);
        final List<Long> answerIds = answers.stream()
                                            .map(Answer::getId)
                                            .collect(Collectors.toList());
        final Grade grade = new Mark(quizAttempt, answerIds, userAnswers, user);
        grade.compute();
	}

	@Override
	public QuizReport getResult(final Long quizAttemptId) {
		final QuizAttempt quizAttempt = getQuizAttempt(quizAttemptId);
		final String title = String.format("Your result for %s", quizAttempt.getQuiz().getName());
		final String startTime = getTimeFormat(quizAttempt.getStartTime().toLocalTime());
		final String finishTime = getTimeFormat(quizAttempt.getFinishTime().toLocalTime());
		final int questionCount = quizAttempt.getQuestionCount();
		final int grade = quizAttempt.getGrade();
		
        return new QuizReport(title, startTime, finishTime, questionCount, grade, Mark.MAX_MARK);
	}

	private String getTimeFormat(final LocalTime time) {
    	return DateTimeFormatter.ofPattern("HH:mm:ss", Locale.US).format(time);
    }
}
