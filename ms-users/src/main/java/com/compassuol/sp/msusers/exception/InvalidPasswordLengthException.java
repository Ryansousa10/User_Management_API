package com.compassuol.sp.msusers.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidPasswordLengthException extends RuntimeException {
    public InvalidPasswordLengthException(String message) {
        super(message);
    }
}
