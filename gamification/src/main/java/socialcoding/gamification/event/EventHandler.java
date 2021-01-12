package socialcoding.gamification.event;

import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import socialcoding.gamification.service.GameService;

@Component @Slf4j
class EventHandler {

	private GameService gameService;
	
	EventHandler(final GameService gameService) {
		this.gameService = gameService;
	}
	
	@RabbitListener(queues = "${quiz.queue}")
	void handleQuizSolved(final QuizSolvedEvent event) {
		log.info("Quiz solved Event received for {}", event.getUsername());
		try {
			gameService.newAttemptForUser(event.getUsername(), event.getScore());
		}catch (final Exception e) {
			log.error("Error when trying to process QuizSolved Event", e);
			throw new AmqpRejectAndDontRequeueException(e);
		}
	}
}
