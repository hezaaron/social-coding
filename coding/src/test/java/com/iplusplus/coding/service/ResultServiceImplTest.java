package com.iplusplus.coding.service;

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

import com.iplusplus.coding.entity.Protocol;
import com.iplusplus.coding.event.EventDispatcher;
import com.iplusplus.coding.event.CodingSolvedEvent;
import com.iplusplus.coding.model.Mark;
import com.iplusplus.coding.repository.AnswerRepository;
import com.iplusplus.coding.repository.ProtocolRepository;
import com.iplusplus.coding.service.ResultServiceImpl;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;

public class ResultServiceImplTest {

	private ResultServiceImpl resultService;
	@Mock private ProtocolRepository protocolRepository;
	@Mock private AnswerRepository answerRepository;
    @Mock private EventDispatcher eventDispatcher;
	
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		FixtureFactoryLoader.loadTemplates("com.iplusplus.coding.template");
		resultService = new ResultServiceImpl(protocolRepository, answerRepository, eventDispatcher);
	}
	@Test
	void testUpdateExamProtocol() {
		Protocol fixtureProtocol = Fixture.from(Protocol.class).gimme("valid");
		boolean isPass = fixtureProtocol.getGrade() >= Mark.PASS_MARK;
		CodingSolvedEvent event = new CodingSolvedEvent(fixtureProtocol.getId(), fixtureProtocol.getUser(), isPass);
		
		given(protocolRepository.save(any())).willReturn(fixtureProtocol);
		Protocol protocol = resultService.updateProtocol(fixtureProtocol);
		
		assertAll("protocol",
				() -> assertNotNull(protocol),
				() -> assertNotNull(protocol.getQuiz()));
		verify(eventDispatcher).send(eq(event));
	}
	
	@Test
	void testGetQuizProtocol() {
		Protocol fixtureProtocol = Fixture.from(Protocol.class).gimme("valid");
		given(protocolRepository.getOne(anyLong())).willReturn(fixtureProtocol);
		Long protocolId = fixtureProtocol.getId();
		Protocol protocol = resultService.getProtocol(protocolId);
		
		assertAll("protocol",
				() -> assertNotNull(protocol),
				() -> assertEquals(5, (int)protocol.getQuestionCount()),
				() -> assertNotNull(protocol.getGrade()));
	}
	
	@Test
	void testGetQuizStats() {
		Protocol fixtureProtocol = Fixture.from(Protocol.class).gimme("valid");
		given(protocolRepository.getOne(anyLong())).willReturn(fixtureProtocol);
		Map<String, Object> stats = resultService.getQuizStats(fixtureProtocol.getId());
		
		assertAll("stats",
				() -> assertFalse(stats.isEmpty()),
				() -> assertNotNull(stats.get("title")),
				() -> assertEquals(5, stats.get("questionCount")));
	}
}
