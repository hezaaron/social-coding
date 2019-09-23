package com.iplusplus.exam.service;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.iplusplus.exam.entity.Answer;
import com.iplusplus.exam.entity.ExamProtocol;
import com.iplusplus.exam.event.EventDispatcher;
import com.iplusplus.exam.event.ExamTakenEvent;
import com.iplusplus.exam.model.Grade;
import com.iplusplus.exam.model.Mark;
import com.iplusplus.exam.repository.AnswerRepository;
import com.iplusplus.exam.repository.ExamProtocolRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ResultServiceImpl implements ResultService {

	private final ExamProtocolRepository protocolRepository;
	private final AnswerRepository answerRepository;
    private final EventDispatcher eventDispatcher;
	
	@Override
	@Transactional
    public ExamProtocol updateExamProtocol(ExamProtocol protocol) {
    	boolean isPass = protocol.getGrade() >= Mark.PASS_MARK;
    	eventDispatcher.send(new ExamTakenEvent(protocol.getId(), protocol.getUser(), isPass));
        return protocolRepository.save(protocol);
    }

	@Override
	public void computeGrade(ExamProtocol protocol, Integer examId, List<Long> userAnswers, String user) {
		final List<Answer> correctAnswers = answerRepository.findByQuestionExamIdAndCorrect(examId, true);
        final List<Long> correctAnswerIds = correctAnswers.stream().map(Answer::getId)
	        														  .collect(Collectors.toList());
        new Grade(protocol, correctAnswerIds, userAnswers, user).computeGrade();
	}

	@Override
	public Map<String, Object> getExamStats(Long protocolId) {
		final ExamProtocol examProtocol = getExamProtocol(protocolId);
        final Map<String, Object> map = new HashMap<>();
        map.put("title", String.format("Your result for %s", examProtocol.getExam().getName()));
        map.put("startTime", getTimeFormat(examProtocol.getStartTime().toLocalTime()));
        map.put("finishTime", getTimeFormat(examProtocol.getFinishTime().toLocalTime()));
        map.put("questionCount", examProtocol.getQuestionCount());
        map.put("grade", examProtocol.getGrade());
        map.put("maxGrade", Mark.MAX_MARK);
        return map;
	}

	@Override
	public ExamProtocol getExamProtocol(Long protocolId) {
		return protocolRepository.getOne(protocolId);
	}

	private String getTimeFormat(LocalTime time) {
    	return DateTimeFormatter.ofPattern("HH:mm:ss", Locale.US).format(time);
    }
}
