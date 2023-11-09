package com.compassuol.sp.msusers.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class ErrorResponse {
    private final HttpStatus status;
    private final String message;
}

