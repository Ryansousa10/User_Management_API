package com.compassuol.sp.msusers.service;

import com.compassuol.sp.msusers.exception.NotificationConversionException;
import com.compassuol.sp.msusers.exception.NotificationException;
import com.compassuol.sp.msusers.payload.NotificationPayload;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
public class NotificationProducer {

    private AmqpTemplate amqpTemplate;
    private ObjectMapper objectMapper;

    public void sendNotification(NotificationPayload payload) {
        try {
            String jsonPayload = objectMapper.writeValueAsString(payload);
            amqpTemplate.convertAndSend("notification_exchange", "notification_routing_key", jsonPayload);
        } catch (JsonProcessingException e) {
            throw new NotificationConversionException("Erro ao converter o payload para JSON", e);
        } catch (Exception e) {
            throw new NotificationException("Erro ao enviar notificação", e);
        }
    }
}