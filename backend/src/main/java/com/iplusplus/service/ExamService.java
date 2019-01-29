package com.iplusplus.service;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.iplusplus.entity.Answer;
import com.iplusplus.entity.Exam;
import com.iplusplus.entity.ExamResult;
import com.iplusplus.entity.Question;
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

    public List<Answer> getAnswersForQuestion(int questionId) {
        return answerRepository.findByQuestionId(questionId);
    }
    
    @Transactional
    public int createExamResult(ExamResult result) {
    	resultRepository.saveAndFlush(result);
        return result.getId();
    }
    
    public void computeGrade(ExamResult result, int examId, List<Integer> userAnswers) {
        final List<Answer> correctAnswers = answerRepository.findByQuestionExamIdAndCorrect(examId, true);
        final List<Integer> correctIds = new ArrayList<>();
        correctAnswers.forEach(answer -> correctIds.add(answer.getId()));
        final Grade grade = new Grade(result, correctIds, userAnswers);
        grade.computeGrade();
    }
    
    @Transactional
    public ExamResult updateExamResult(ExamResult result) {
        return resultRepository.save(result);
    }
    
    public ExamResult getExamResult(int resultId) {
        return resultRepository.getOne(resultId);
    }
    
    public Map<String,Object> getExamStats(final Integer resultId) {
    	final ExamResult examResult = resultRepository.getOne(resultId);
        final Map<String, Object> map = new HashMap<>();
        map.put("title", String.format("Your result for %s", examResult.getExam().getName()));
        map.put("startTime", getTimeFormat(examResult.getStartTime().toLocalTime()));
        map.put("finishTime", getTimeFormat(examResult.getFinishTime().toLocalTime()));
        map.put("questionCount", examResult.getQuestionCount());
        map.put("grade", examResult.getGrade());
        map.put("maxGrade", Mark.MAX_MARK);
        return map;
    }
    
    private String getTimeFormat(LocalTime time) {
    	return DateTimeFormatter.ofPattern("HH:mm:ss", Locale.US).format(time);
    }
}