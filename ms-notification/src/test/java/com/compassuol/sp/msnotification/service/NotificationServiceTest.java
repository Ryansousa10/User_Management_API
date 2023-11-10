package com.compassuol.sp.msnotification.service;

import com.compassuol.sp.msnotification.model.Notification;
import com.compassuol.sp.msnotification.payload.NotificationPayload;
import com.compassuol.sp.msnotification.repository.NotificationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.Instant;

import static org.mockito.Mockito.verify;

class NotificationServiceTest {

    @Mock
    private NotificationRepository notificationRepository;

    @InjectMocks
    private NotificationService notificationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateNotification() {
        NotificationPayload payload = new NotificationPayload();
        payload.setEvent("TestEvent");
        payload.setEmail("test@example.com");
        payload.setDate("2023-01-01T00:00:00Z");

        notificationService.createNotification(payload);

        verify(notificationRepository).save(Mockito.any(Notification.class));
    }
}