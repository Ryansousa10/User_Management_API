package com.compassuol.sp.msnotification.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class NotificationPayload {
    @JsonProperty("email")
    private String email;
    @JsonProperty("event")
    private String event;
    @JsonProperty("date")
    private String date;

}
