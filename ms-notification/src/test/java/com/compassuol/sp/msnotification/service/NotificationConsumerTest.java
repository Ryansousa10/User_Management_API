package com.compassuol.sp.msnotification.service;

import com.compassuol.sp.msnotification.payload.NotificationPayload;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;

public class NotificationConsumerTest {

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private NotificationConsumer notificationConsumer;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testReceiveNotification() throws IOException {
        String payload = "{\"message\":\"Test Message\"}";
        NotificationPayload notificationPayload = new ObjectMapper().readValue(payload, NotificationPayload.class);

        Mockito.doNothing().when(notificationService).createNotification(any(NotificationPayload.class));

        notificationConsumer.receiveNotification(payload);

        Mockito.verify(notificationService, Mockito.times(1)).createNotification(notificationPayload);
    }

    @Test
    public void testReceiveNotificationWithInvalidPayload() {
        String invalidPayload = "invalid_payload";

        Mockito.doNothing().when(notificationService).createNotification(any(NotificationPayload.class));

        notificationConsumer.receiveNotification(invalidPayload);

        Mockito.verify(notificationService, Mockito.never()).createNotification(any(NotificationPayload.class));
    }
}

