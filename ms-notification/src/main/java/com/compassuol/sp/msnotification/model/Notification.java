package com.compassuol.sp.msnotification.model;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Data
@Document(collection = "db_notification")
@TypeAlias("notification")
public class Notification {

    @Id
    private String id;
    private String email;
    private String event;
    private Instant date;
    private String message;
}