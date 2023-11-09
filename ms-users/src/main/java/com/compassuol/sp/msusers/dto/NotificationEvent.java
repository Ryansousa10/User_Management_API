package com.compassuol.sp.msusers.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NotificationEvent {
    private String userEmail;
    private String event;
}
