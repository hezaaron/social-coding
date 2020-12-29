package socialcoding.event;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class EventDispatcher {
	
    private final RabbitTemplate rabbitTemplate;
    private final String quizExchange;
    private final String quizSolvedRoutingKey;
    
    @Autowired
    EventDispatcher(final RabbitTemplate rabbitTemplate, @Value("${quiz.exchange}") final String quizExchange,
                    @Value("${quiz.solved.key}") final String quizSolvedRoutingKey) {
        this.rabbitTemplate = rabbitTemplate;
        this.quizExchange = quizExchange;
        this.quizSolvedRoutingKey = quizSolvedRoutingKey;
    }

    public void send(final QuizSolvedEvent quizSolvedEvent) {
        rabbitTemplate.convertAndSend(quizExchange, quizSolvedRoutingKey, quizSolvedEvent);
    }
}