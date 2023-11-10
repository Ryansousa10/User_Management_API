package com.compassuol.sp.msusers.payload;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NotificationPayloadTest {

    @Test
    void testNotificationPayload() {
        // Arrange
        String email = "test@example.com";
        String event = "user_created";
        String date = "2023-11-10";

        // Act
        NotificationPayload payload = new NotificationPayload(email, event, date);

        // Assert
        assertEquals(email, payload.getEmail());
        assertEquals(event, payload.getEvent());
        assertEquals(date, payload.getDate());
    }
}

