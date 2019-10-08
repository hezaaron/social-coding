package com.iplusplus.coding.event;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class EventDispatcher {

    private RabbitTemplate rabbitTemplate;
    private String codingExchange;
    private String codingSolvedRoutingKey;
    
    @Autowired
    EventDispatcher(final RabbitTemplate rabbitTemplate, @Value("${coding.exchange}") final String codingExchange,
                    @Value("${coding.solved.key}") final String codingSolvedRoutingKey) {
        this.rabbitTemplate = rabbitTemplate;
        this.codingExchange = codingExchange;
        this.codingSolvedRoutingKey = codingSolvedRoutingKey;
    }

    public void send(final CodingSolvedEvent codingSolvedEvent) {
        rabbitTemplate.convertAndSend(codingExchange, codingSolvedRoutingKey, codingSolvedEvent);
    }
}