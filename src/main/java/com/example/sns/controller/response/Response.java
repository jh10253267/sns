package com.example.sns.controller.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Response<T> {
    private String resultCode;
    private T result;

    public static <T> Response<Void> error(String errorCode) {
            return new Response<>(errorCode, null);
    }
    public static <T> Response<T> success(T result) {
        return new Response<>("SUCCESS", result);
    }
}
