package com.iplusplus.exam.event;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class EventDispatcher {

    private RabbitTemplate rabbitTemplate;
    private String examExchange;
    private String examTakenRoutingKey;
    
    @Autowired
    EventDispatcher(final RabbitTemplate rabbitTemplate, @Value("${exam.exchange}") final String examExchange,
                    @Value("${exam.taken.key}") final String examTakenRoutingKey) {
        this.rabbitTemplate = rabbitTemplate;
        this.examExchange = examExchange;
        this.examTakenRoutingKey = examTakenRoutingKey;
    }

    public void send(final ExamTakenEvent examTakenEvent) {
        rabbitTemplate.convertAndSend(examExchange, examTakenRoutingKey, examTakenEvent);
    }
}
