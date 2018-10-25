package com.iplusplus.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

	private static final Logger logger = LogManager.getLogger(ExamService.class);
	public static final double MAX_GRADE = 100;
	public static final int NUMBER_OF_QUESTIONS_PER_EXAM = 5;

    @Autowired
    private ExamRepository examRepository;
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private AnswerRepository answerRepository;
    @Autowired
    private ExamResultRepository resultRepository;

    
    public List<Exam> getAllExams() {
        return examRepository.findAll();
    }

    @Transactional
    public int insertExam(ExamResult result) {
    	resultRepository.saveAndFlush(result);
        return result.getId();
    }
    
    public Exam getExam(int examId) {
        return examRepository.getOne(examId);
    }

    public List<Question> getQuestionsForExam(int examId) {
    	final List<Question> questions = questionRepository.findByExamId(examId);
        return getRandomQuestions(questions);
    }

    public List<Answer> getAnswersForQuestion(int questionId) {
        return answerRepository.findByQuestionId(questionId);
    }
    
    @Transactional
    public ExamResult updateExamResult(ExamResult result) {
        return resultRepository.save(result);
    }

    public ExamResult getExamResult(int resultId) {
        return resultRepository.getOne(resultId);
    }
    
    public List<Object> getExamStats(final Integer resultId) {
    	final ExamResult examResult = getExamResult(resultId);
        final Exam exam = getExam(examResult.getExam().getId());
        return Arrays.asList(String.format("Your results for %s", exam.getName()), getTimeInstance(examResult.getStartTime()),
        		             getTimeInstance(examResult.getFinishTime()), examResult.getQuestionCount(), examResult.getGrade(), MAX_GRADE);
    }

    public void calculateGrade(ExamResult result, int examId, List<Integer> userAnswers) {
        if (result == null || userAnswers == null) {
            throw new IllegalArgumentException("Invalid parameters on GRADE call");
        }

        final List<Answer> correctAnswers = answerRepository.findByQuestionExamIdAndCorrect(examId, true);
        logger.info("number of correct answers for exam {} : {}", examId, correctAnswers.size());
        if (correctAnswers.isEmpty()) {
            throw new IllegalArgumentException("You must specify correct answers!");
        }
        
        calculateGrade(result, userAnswers, correctAnswers);
    }
    
    public List<Question> getRandomQuestions(List<Question> questions) {
    	final int minValue = questions.get(0).getId();
    	final int maxValue = questions.get(questions.size() - 1).getId();
    	final List<Question> questionsForExam = new ArrayList<>();
    	
    	for(int i = 1; i <= NUMBER_OF_QUESTIONS_PER_EXAM; i++) {
    		int id = new Random().nextInt((maxValue - minValue) + 1) + minValue;
    		questionsForExam.add(questionRepository.getOne(id));
    	}
        return questionsForExam;
    }
    
    private void calculateGrade(ExamResult result, List<Integer> userAnswers, List<Answer> correctAnswers) {
        final List<Integer> correctCount = new ArrayList<>();
        final List<Integer> correctAnswerIds = new ArrayList<>();
        
        for (final Answer answer : correctAnswers) {
        	correctAnswerIds.add(answer.getId());
        }
        
        float score = 0;
        final float step = (float) (MAX_GRADE / NUMBER_OF_QUESTIONS_PER_EXAM);
        
        for (final Integer id : userAnswers) {
            if (correctAnswerIds.contains(id)) {
            	score += step;
                correctCount.add(id);
            }
        }
        result.setCorrectAnswers(correctCount.size());
        result.setGrade(Math.round(score));
    }

    private String getTimeInstance(Date date) {
		return SimpleDateFormat.getTimeInstance().format(date);
    }
}
