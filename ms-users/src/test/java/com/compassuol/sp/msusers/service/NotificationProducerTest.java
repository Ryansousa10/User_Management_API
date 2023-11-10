package com.compassuol.sp.msusers.service;

import com.compassuol.sp.msusers.exception.NotificationConversionException;
import com.compassuol.sp.msusers.payload.NotificationPayload;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.amqp.core.AmqpTemplate;

import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class NotificationProducerTest {

    @Mock
    private AmqpTemplate amqpTemplate;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private NotificationProducer notificationProducer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void sendNotification_Successful() throws JsonProcessingException {
        NotificationPayload payload = new NotificationPayload("test@example.com", "some_event", "2023-01-01");
        when(objectMapper.writeValueAsString(any(NotificationPayload.class))).thenReturn("jsonPayload");

        notificationProducer.sendNotification(payload);

        verify(objectMapper, times(1)).writeValueAsString(payload);
        verify(amqpTemplate, times(1)).convertAndSend("notification_exchange", "notification_routing_key", "jsonPayload");
    }

    @Test
    void sendNotification_JsonProcessingException() throws JsonProcessingException {
        NotificationPayload payload = new NotificationPayload("test@example.com", "some_event", "2023-01-01");
        when(objectMapper.writeValueAsString(any(NotificationPayload.class))).thenThrow(JsonProcessingException.class);

        assertThrows(NotificationConversionException.class, () -> notificationProducer.sendNotification(payload));
    }
}
