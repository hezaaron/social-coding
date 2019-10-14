package com.iplusplus.gamification.event;

import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.iplusplus.gamification.service.GameService;

import lombok.extern.slf4j.Slf4j;

@Component @Slf4j
class EventHandler {

	private GameService gameService;
	
	EventHandler(final GameService gameService) {
		this.gameService = gameService;
	}
	
	@RabbitListener(queues = "${coding.queue}")
	void handleCodingSolved(final CodingSolvedEvent event) {
		log.info("Coding solved Event received: {}", event.getResultId());
		try {
			gameService.newAttemptForUser(event.getUsername(), event.getResultId(), event.isSolved());
		}catch (final Exception e) {
			log.error("Error when trying to process CodingSolved Event", e);
			throw new AmqpRejectAndDontRequeueException(e);
		}
	}
	
	
}
