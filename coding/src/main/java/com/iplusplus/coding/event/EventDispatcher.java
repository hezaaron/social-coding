package com.iplusplus.coding.event;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class EventDispatcher {

    private RabbitTemplate rabbitTemplate;
    private String codingExchange;
    private String codingTakenRoutingKey;
    
    @Autowired
    EventDispatcher(final RabbitTemplate rabbitTemplate, @Value("${coding.exchange}") final String codingExchange,
                    @Value("${coding.taken.key}") final String codingTakenRoutingKey) {
        this.rabbitTemplate = rabbitTemplate;
        this.codingExchange = codingExchange;
        this.codingTakenRoutingKey = codingTakenRoutingKey;
    }

    public void send(final CodingTakenEvent codingTakenEvent) {
        rabbitTemplate.convertAndSend(codingExchange, codingTakenRoutingKey, codingTakenEvent);
    }
}