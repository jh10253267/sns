package com.example.sns.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

// TODO : implement
@AllArgsConstructor
@Getter
public class SnsApplicationException extends RuntimeException{
    private ErrorCode errorCode;
    private String message;

    @Override
    public String getMessage() {
        return String.format("%s, %s", errorCode.getMessage(), message);
    }
}
