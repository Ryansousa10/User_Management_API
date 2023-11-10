package com.compassuol.sp.msusers.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class NotificationPayload {

    private String email;
    private String event;
    private String date;

}
