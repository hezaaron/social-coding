package com.iplusplus.exam.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.BDDMockito.given;

import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.iplusplus.exam.entity.ExamProtocol;
import com.iplusplus.exam.event.EventDispatcher;
import com.iplusplus.exam.event.ExamTakenEvent;
import com.iplusplus.exam.model.Mark;
import com.iplusplus.exam.repository.AnswerRepository;
import com.iplusplus.exam.repository.ExamProtocolRepository;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;

public class ResultServiceImplTest {

	private ResultServiceImpl resultService;
	@Mock private ExamProtocolRepository protocolRepository;
	@Mock private AnswerRepository answerRepository;
    @Mock private EventDispatcher eventDispatcher;
	
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		FixtureFactoryLoader.loadTemplates("com.iplusplus.exam.template");
		resultService = new ResultServiceImpl(protocolRepository, answerRepository, eventDispatcher);
	}
	@Test
	void testUpdateExamProtocol() {
		ExamProtocol fixtureProtocol = Fixture.from(ExamProtocol.class).gimme("valid");
		boolean isPass = fixtureProtocol.getGrade() >= Mark.PASS_MARK;
		ExamTakenEvent event = new ExamTakenEvent(fixtureProtocol.getId(), fixtureProtocol.getUser(), isPass);
		
		given(protocolRepository.save(any())).willReturn(fixtureProtocol);
		ExamProtocol protocol = resultService.updateExamProtocol(fixtureProtocol);
		
		assertAll("protocol",
				() -> assertNotNull(protocol),
				() -> assertNotNull(protocol.getExam()));
		verify(eventDispatcher).send(eq(event));
	}
	
	@Test
	void testGetExamProtocol() {
		ExamProtocol fixtureProtocol = Fixture.from(ExamProtocol.class).gimme("valid");
		given(protocolRepository.getOne(anyLong())).willReturn(fixtureProtocol);
		Long protocolId = fixtureProtocol.getId();
		ExamProtocol protocol = resultService.getExamProtocol(protocolId);
		
		assertAll("protocol",
				() -> assertNotNull(protocol),
				() -> assertEquals(5, (int)protocol.getQuestionCount()),
				() -> assertNotNull(protocol.getGrade()));
	}
	
	@Test
	void testGetExamStats() {
		ExamProtocol fixtureProtocol = Fixture.from(ExamProtocol.class).gimme("valid");
		given(protocolRepository.getOne(anyLong())).willReturn(fixtureProtocol);
		Map<String, Object> stats = resultService.getExamStats(fixtureProtocol.getId());
		
		assertAll("stats",
				() -> assertFalse(stats.isEmpty()),
				() -> assertNotNull(stats.get("title")),
				() -> assertEquals(5, stats.get("questionCount")));
	}
}
