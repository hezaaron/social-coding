package com.iplusplus.exam.service;

import java.time.Clock;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.iplusplus.exam.dto.ExamDTO;
import com.iplusplus.exam.entity.Answer;
import com.iplusplus.exam.entity.Exam;
import com.iplusplus.exam.entity.ExamProtocol;
import com.iplusplus.exam.entity.Question;
import com.iplusplus.exam.model.ExamProtocolFactory;
import com.iplusplus.exam.model.ExamTime;
import com.iplusplus.exam.model.ExamTimeFactory;
import com.iplusplus.exam.model.Mark;
import com.iplusplus.exam.model.RandomQuestion;
import com.iplusplus.exam.repository.AnswerRepository;
import com.iplusplus.exam.repository.ExamProtocolRepository;
import com.iplusplus.exam.repository.ExamRepository;
import com.iplusplus.exam.repository.QuestionRepository;

import lombok.RequiredArgsConstructor;

@Service @RequiredArgsConstructor
public class ExamServiceImpl implements ExamService{
	
	private final ExamRepository examRepository;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    private final ExamProtocolRepository protocolRepository;
    private final ExamProtocolFactory protocolFactory;
    private final ExamTimeFactory timeFactory;

    @Override
    public List<Exam> getAllExams() {
    	return examRepository.findAll();
    }
    
    @Override
    public Map<String, Object> getExamDetails(Integer examId, HttpServletRequest request){
    	final Exam exam = examRepository.getOne(examId);
    	ExamProtocol examProtocol = createExamProtocol(exam);
    	request.getSession().setAttribute("examStarted", examProtocol.getStartTime());
        final long protocolId = getExamProtocolId(examProtocol);
        final ExamDTO examDto = new ExamDTO(protocolId, examId);
        final ExamTime examTime = timeFactory.createInstance();
        
        final Map<String, Object> model = new HashMap<>();
        model.put("name", exam.getName());
        model.put("timer", examTime.getRemainingTime(request));
        model.put("result", examDto);
        return model;
    }
    
    private ExamProtocol createExamProtocol(Exam exam) {
    	final List<Question> questions = getQuestionsForExam(exam.getId());
    	return protocolFactory.createInstance(exam, new ExamTime(Clock.systemDefaultZone()).getTime(),
				   				questions.size());
    }
    
    @Transactional
    private Long getExamProtocolId(ExamProtocol protocol) {
    	protocolRepository.saveAndFlush(protocol);
        return protocol.getId();
    }
    
    @Override
    public List<Question> getQuestionsForExam(Integer examId) {
    	final List<Question> questions = questionRepository.findByExamId(examId);
    	final int questionSize = Mark.NUMBER_OF_QUESTION;
    	return new RandomQuestion(questions, questionSize).getList();
    }
    
    @Override
    public List<Answer> getAnswersForExam(Integer examId) {
    	return answerRepository.findByQuestionExamId(examId);
    }
}