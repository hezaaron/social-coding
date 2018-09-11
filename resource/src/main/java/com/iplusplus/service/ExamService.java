package com.iplusplus.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iplusplus.domain.Answer;
import com.iplusplus.domain.Exam;
import com.iplusplus.domain.ExamResult;
import com.iplusplus.domain.Question;
import com.iplusplus.repository.AnswerRepository;
import com.iplusplus.repository.ExamRepository;
import com.iplusplus.repository.ExamResultRepository;
import com.iplusplus.repository.QuestionRepository;

@Service
public class ExamService {

	public static final double MAX_GRADE = 100;

    @Autowired
    private ExamRepository examRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private ExamResultRepository resultRepository;

    public Collection<Exam> getTestExams() {
        return examRepository.findAll()
        					 .stream()
        					 .sorted()
        					 .collect(Collectors.toList());
    }

    @SuppressWarnings("unused")
	@Transactional
    public int insertExam(ExamResult result) {
    	final ExamResult er = resultRepository.save(result);
    	resultRepository.flush();
        return result.getId();
    }
    
    public Exam getExam(int examId) {
        return examRepository.getOne(examId);
    }

    public List<Question> getQuestionsForExam(int examId) {
    	final List<Question> questions = questionRepository.findByExamId(examId);
    	final int minValue = questions.get(0).getId();
    	final int maxValue = questions.get(questions.size() - 1).getId();
    	
        return getRandomQuestions(minValue, maxValue);
    }

    public List<Answer> getAnswersForQuestion(int questionId) {
        return answerRepository.findByQuestionId(questionId);
    }

    public Question getNextQuestion(Exam exam, Set<Integer> ids) {
        final Set<Integer> identificators = new HashSet<>(ids);
        if (identificators.size() == 0) {
            identificators.add(0);
        }
        return questionRepository.findFirstByExamAndIdNotIn(exam, identificators);
    }
    
    @Transactional
    public ExamResult updateExamResult(ExamResult result) {
        return resultRepository.save(result);
    }

    public ExamResult getExamResult(int resultId) {
        return resultRepository.getOne(resultId);
    }
    
    public List<Object> stats(final Integer resultId) {
    	final ExamResult examResult = getExamResult(resultId);
        final Exam exam = getExam(examResult.getExam().getId());
        Map<String, Object> map = new HashMap<>();
        map.put("title", String.format("Your results for %s", exam.getName()));
        map.put("start", getTimeInstance(examResult.getStart()));
        map.put("finish", getTimeInstance(examResult.getFinish()));
        map.put("questionCount", examResult.getQuestionCount());
        map.put("grade", examResult.getGrade());
        map.put("maxGrade", ExamService.MAX_GRADE);
        return map.entrySet().stream().map(entry -> entry.getValue()).collect(Collectors.toList());
    }

    public void calcGrade(ExamResult result, int examId, List<Integer> userAnswers) {
        if (result == null || userAnswers == null) {
            throw new IllegalArgumentException("Invalid parameters on GRADE call");
        }

        final List<Answer> correctAnswers = answerRepository.findByQuestionExamIdAndCorrect(examId, true);
        if (correctAnswers.size() == 0) {
            throw new IllegalArgumentException("You must specify correct answers!");
        }

        final float step = (float) (MAX_GRADE / correctAnswers.size());
        final Set<Integer> correctCount = new HashSet<>();
        final Set<Integer> incorrectCount = new HashSet<>();

        final Set<Integer> answers = userAnswers.stream().filter(a -> a > 0).collect(Collectors.toSet());

        float grade = 0;
        for (final Answer answer : correctAnswers) {
            final Integer id = answer.getId();
            if (answers.contains(id)) {
                grade += step;
                correctCount.add(id);
            } else {
                incorrectCount.add(id);
            }
        }
        // fix for multi-answers questions
        correctCount.removeAll(incorrectCount);

        // grade
        result.setCorrectAnswers(correctCount.size());
        result.setGrade(Math.round(grade));
    }
    
    private List<Question> getRandomQuestions(int minValue, int maxValue) {
    	final List<Question> questionsForExam = new ArrayList<>();
    	for(int i = 1; i <= 5; i++) {
    		int id = new Random().nextInt((maxValue - minValue) + 1) + minValue;
    		System.out.print(id);
    		questionsForExam.add(questionRepository.getOne(id));
    	}
        return questionsForExam;
    }
    
    private String getTimeInstance(Date date) {
		return SimpleDateFormat.getTimeInstance().format(date);
    }

}
