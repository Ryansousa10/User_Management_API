package com.compassuol.sp.msnotification.service;

import com.compassuol.sp.msnotification.payload.NotificationPayload;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class NotificationConsumer {

    @Autowired
    private NotificationService notificationService;

    @RabbitListener(queues = "notification_queue")
    public void receiveNotification(String payload) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            NotificationPayload notificationPayload = objectMapper.readValue(payload, NotificationPayload.class);

            System.out.println("Recebido: " + notificationPayload);

            notificationService.createNotification(notificationPayload);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}