package com.iplusplus.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iplusplus.controller.ExamController;
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
	public static final int QUESTION_COUNT_PER_EXAM = 5;

    @Autowired
    private ExamRepository examRepository;
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private AnswerRepository answerRepository;
    @Autowired
    private ExamResultRepository resultRepository;

    public List<Exam> getTestExams() {
        return examRepository.findAll();
    }

    @Transactional
    @SuppressWarnings("unused")
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

    public void calculateGrade(ExamResult result, int examId, List<Integer> userAnswers) {
        if (result == null || userAnswers == null) {
            throw new IllegalArgumentException("Invalid parameters on GRADE call");
        }

        final List<Answer> correctAnswers = answerRepository.findByQuestionExamIdAndCorrect(examId, true);
        logger.info("number of correct answers for exam {} : {}", examId, correctAnswers.size());
        if (correctAnswers.size() == 0) {
            throw new IllegalArgumentException("You must specify correct answers!");
        }

        final float step = (float) (MAX_GRADE / QUESTION_COUNT_PER_EXAM);
        final List<Integer> correctCount = new ArrayList<>();
        final List<Integer> correctAnswerIds = new ArrayList<>();

        for (final Answer answer : correctAnswers) {
        	correctAnswerIds.add(answer.getId());
        }
        
        float score = 0;
        for (final Integer id : userAnswers) {
            if (correctAnswerIds.contains(id)) {
            	score += step;
                correctCount.add(id);
            }
        }
        
        result.setCorrectAnswers(correctCount.size());
        result.setGrade(Math.round(score));
    }
    
    public List<Question> getRandomQuestions(List<Question> questions) {
    	final int minValue = questions.get(0).getId();
    	final int maxValue = questions.get(questions.size() - 1).getId();
    	final List<Question> questionsForExam = new ArrayList<>();
    	
    	for(int i = 1; i <= QUESTION_COUNT_PER_EXAM; i++) {
    		int id = new Random().nextInt((maxValue - minValue) + 1) + minValue;
    		questionsForExam.add(questionRepository.getOne(id));
    	}
        return questionsForExam;
    }
    
    private String getTimeInstance(Date date) {
		return SimpleDateFormat.getTimeInstance().format(date);
    }

}
