package com.iplusplus.coding.service;

import java.time.Clock;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.iplusplus.coding.dto.QuizDTO;
import com.iplusplus.coding.entity.Answer;
import com.iplusplus.coding.entity.Quiz;
import com.iplusplus.coding.entity.Protocol;
import com.iplusplus.coding.entity.Question;
import com.iplusplus.coding.model.ProtocolFactory;
import com.iplusplus.coding.model.QuizTime;
import com.iplusplus.coding.model.QuizTimeFactory;
import com.iplusplus.coding.model.Mark;
import com.iplusplus.coding.model.RandomQuestion;
import com.iplusplus.coding.repository.AnswerRepository;
import com.iplusplus.coding.repository.ProtocolRepository;
import com.iplusplus.coding.repository.QuizRepository;
import com.iplusplus.coding.repository.QuestionRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service @Slf4j
@RequiredArgsConstructor
public class QuizServiceImpl implements QuizService{
	
	private final QuizRepository quizRepository;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    private final ProtocolRepository protocolRepository;
    private final ProtocolFactory protocolFactory;
    private final QuizTimeFactory timeFactory;

    @Override
    public List<Quiz> getAllQuizzes() {
    	return quizRepository.findAll();
    }
    
    @Override
    public Map<String, Object> getQuizDetails(Integer quizId, HttpServletRequest request){
    	final Quiz quiz = quizRepository.getOne(quizId);
    	Protocol protocol = createProtocol(quiz);
    	log.info("Protocol: {}", protocol.toString());
    	request.getSession().setAttribute("quizStarted", protocol.getStartTime());
        final long protocolId = getProtocolId(protocol);
        final QuizDTO quizDto = new QuizDTO(protocolId, quizId);
        final QuizTime quizTime = timeFactory.createInstance();
        
        final Map<String, Object> model = new HashMap<>();
        model.put("name", quiz.getName());
        model.put("timer", quizTime.getRemainingTime(request));
        model.put("result", quizDto);
        return model;
    }
    
    private Protocol createProtocol(Quiz quiz) {
    	final List<Question> questions = getQuestionsForQuiz(quiz.getId());
    	return protocolFactory.createInstance(quiz, new QuizTime(Clock.systemDefaultZone()).getTime(),
				   				questions.size());
    }
    
    @Transactional
    private Long getProtocolId(Protocol protocol) {
    	protocolRepository.saveAndFlush(protocol);
        return protocol.getId();
    }
    
    @Override
    public List<Question> getQuestionsForQuiz(Integer quizId) {
    	final List<Question> questions = questionRepository.findByQuizId(quizId);
    	final int questionSize = Mark.NUMBER_OF_QUESTION;
    	return new RandomQuestion(questions, questionSize).getList();
    }
    
    @Override
    public List<Answer> getAnswersForQuiz(Integer quizId) {
    	return answerRepository.findByQuestionQuizId(quizId);
    }
}