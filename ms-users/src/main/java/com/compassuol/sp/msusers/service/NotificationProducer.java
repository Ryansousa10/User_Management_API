package com.compassuol.sp.msusers.service;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class NotificationProducer {
    @Autowired
    private AmqpTemplate amqpTemplate;

    public void sendNotification(String email, String event) {
        Map<String, Object> payload = new HashMap<>();
        payload.put("email", email);
        payload.put("event", event);
        payload.put("date", LocalDateTime.now().toString());

        amqpTemplate.convertAndSend("user_events", "", payload);
    }
}
