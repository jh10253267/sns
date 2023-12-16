package com.example.sns.controller.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@AllArgsConstructor
public class UserJoinRequest {
    private String userName;
    private String password;
}
