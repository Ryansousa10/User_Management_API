package com.compassuol.sp.msnotification.service;

import com.compassuol.sp.msnotification.model.Notification;
import com.compassuol.sp.msnotification.payload.NotificationPayload;
import com.compassuol.sp.msnotification.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;

    @Autowired
    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public void createNotification(NotificationPayload payload) {
        Notification notification = new Notification();
        notification.setEmail(payload.getEmail());
        notification.setEvent(payload.getEvent());
        notification.setDate(Instant.parse(payload.getDate()));

        notificationRepository.save(notification);
    }
}



