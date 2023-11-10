package com.compassuol.sp.msnotification.model;

import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class NotificationTest {

    @Test
    void testCreateNotificationWithAllFields() {
        String id = "1";
        String email = "test@example.com";
        String event = "TestEvent";
        Instant date = Instant.parse("2023-01-01T00:00:00Z");
        String message = "Test Message";

        Notification notification = new Notification();
        notification.setId(id);
        notification.setEmail(email);
        notification.setEvent(event);
        notification.setDate(date);
        notification.setMessage(message);

        assertNotNull(notification);
        assertEquals(id, notification.getId());
        assertEquals(email, notification.getEmail());
        assertEquals(event, notification.getEvent());
        assertEquals(date, notification.getDate());
        assertEquals(message, notification.getMessage());
    }

    @Test
    void testCreateNotificationWithoutMessage() {
        String id = "1";
        String email = "test@example.com";
        String event = "TestEvent";
        Instant date = Instant.parse("2023-01-01T00:00:00Z");

        Notification notification = new Notification();
        notification.setId(id);
        notification.setEmail(email);
        notification.setEvent(event);
        notification.setDate(date);

        assertNotNull(notification);
        assertEquals(id, notification.getId());
        assertEquals(email, notification.getEmail());
        assertEquals(event, notification.getEvent());
        assertEquals(date, notification.getDate());
        assertEquals(null, notification.getMessage());
    }
}

