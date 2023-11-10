package com.compassuol.sp.msnotification.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class NotificationPayload {
    @JsonProperty("email")
    private String email;
    @JsonProperty("event")
    private String event;
    @JsonProperty("date")
    private String date;
    private String message;

    @Override
    public String toString() {
        return "NotificationPayload{" +
                "event='" + event + '\'' +
                ", email='" + email + '\'' +
                ", date='" + date + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
