package com.iplusplus.service;

import java.time.Clock;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.iplusplus.entity.Answer;
import com.iplusplus.entity.Exam;
import com.iplusplus.entity.ExamResult;
import com.iplusplus.entity.Question;
import com.iplusplus.model.ExamTime;
import com.iplusplus.model.Grade;
import com.iplusplus.model.Mark;
import com.iplusplus.model.RandomQuestion;
import com.iplusplus.repository.AnswerRepository;
import com.iplusplus.repository.ExamRepository;
import com.iplusplus.repository.ExamResultRepository;
import com.iplusplus.repository.QuestionRepository;

import lombok.RequiredArgsConstructor;

@Service @RequiredArgsConstructor
public class ExamService {
	
	private final ExamRepository examRepository;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    private final ExamResultRepository resultRepository;

    
    public List<Exam> getAllExams() {
    	return examRepository.findAll();
    }
    
    public Exam getExam(int examId) {
        return examRepository.getOne(examId);
    }

    public List<Question> getQuestionsForExam(int examId) {
    	final List<Question> questions = questionRepository.findByExamId(examId);
    	final int questionSize = Mark.NUMBER_OF_QUESTION;
    	return new RandomQuestion(questions, questionSize).getList();
    }
    
    public List<Answer> getAnswersForExam(int examId) {
    	return answerRepository.findByQuestionExamId(examId);
    }

    public List<Answer> getAnswersForQuestion(int questionId) {
        return answerRepository.findByQuestionId(questionId);
    }
    
    public ExamResult createExamResult(Exam exam) {
    	final List<Question> questions = getQuestionsForExam(exam.getId());
    	final ExamResult examResult = new ExamResult();
    	examResult.setExam(exam);
    	examResult.setStartTime(new ExamTime(Clock.systemDefaultZone()).getTime());
    	examResult.setQuestionCount(questions.size());
    	return examResult;
    }
    
    @Transactional
    public int createExamResultId(ExamResult result) {
    	resultRepository.saveAndFlush(result);
        return result.getId();
    }
    
    @Transactional
    public ExamResult updateExamResult(ExamResult result) {
        return resultRepository.save(result);
    }
    
    public void computeGrade(ExamResult result, Integer examId, List<Integer> userAnswers) {
        final List<Answer> correctAnswers = answerRepository.findByQuestionExamIdAndCorrect(examId, true);
        final List<Integer> correctAnswerIds = correctAnswers.stream()
	        												 .map(Answer::getId)
	        												 .collect(Collectors.toList());
        new Grade(result, correctAnswerIds, userAnswers).computeGrade();
    }
    
    public Map<String,Object> getExamStats(final int resultId) {
    	final ExamResult examResult = getExamResult(resultId);
        final Map<String, Object> map = new HashMap<>();
        map.put("title", String.format("Your result for %s", examResult.getExam().getName()));
        map.put("startTime", getTimeFormat(examResult.getStartTime().toLocalTime()));
        map.put("finishTime", getTimeFormat(examResult.getFinishTime().toLocalTime()));
        map.put("questionCount", examResult.getQuestionCount());
        map.put("grade", examResult.getGrade());
        map.put("maxGrade", Mark.MAX_MARK);
        return map;
    }
    
    public ExamResult getExamResult(int resultId) {
        return resultRepository.getOne(resultId);
    }
    
    private String getTimeFormat(LocalTime time) {
    	return DateTimeFormatter.ofPattern("HH:mm:ss", Locale.US).format(time);
    }
}