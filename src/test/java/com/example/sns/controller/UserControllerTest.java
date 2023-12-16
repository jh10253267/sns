package com.example.sns.controller;

import com.example.sns.controller.request.UserJoinRequest;
import com.example.sns.controller.request.UserLoginRequest;
import com.example.sns.exception.ErrorCode;
import com.example.sns.exception.SnsApplicationException;
import com.example.sns.model.User;
import com.example.sns.service.UserService;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private UserService userService;

    @Test
    void 회원가입() throws Exception {
        String username = "username";
        String password = "password";

        // TODO : mocking
        when(userService.join(username, password)).thenReturn(mock(User.class));

        mockMvc.perform(post("/api/v1/users/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        // TODO : add Request body
                        .content(objectMapper.writeValueAsBytes(new UserJoinRequest(username, password)))
                ).andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void 회원가입_이미_회원가입된_유저네임으로_회원가입을_하는경우_에러반환() throws Exception {
        String username = "username";
        String password = "password";

        when(userService.join(username, password)).thenThrow(new SnsApplicationException(ErrorCode.DUPLICATED_USER_NAME, ""));

        mockMvc.perform(post("/api/v1/users/join")
                // TODO : add request body
                .content(objectMapper.writeValueAsBytes(new UserJoinRequest(username, password)))
        ).andDo(print())
                .andExpect(status().isConflict());
    }

    @Test
    void 로그인() throws Exception {
        String username = "username";
        String password = "password";

        when(userService.login(username, password)).thenReturn("test_token");

        mockMvc.perform(post("/api/v1/users/login")
                        // TODO : add request body
                        .content(objectMapper.writeValueAsBytes(new UserLoginRequest(username, password)))
                ).andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void 로그인시_회원가입이_안된_아이디를_입력할경우_에러반환() throws Exception {
        String username = "username";
        String password = "password";

        when(userService.join(username, password)).thenThrow(new SnsApplicationException(ErrorCode.DUPLICATED_USER_NAME, ""));

        mockMvc.perform(post("/api/v1/users/join")
                        // TODO : add request body
                        .content(objectMapper.writeValueAsBytes(new UserJoinRequest(username, password)))
                ).andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void 로그인시_틀린_패스워드를_입력할경우_에러반환() throws Exception {
        String username = "username";
        String password = "password";

        when(userService.login(username, password)).thenThrow(new SnsApplicationException(ErrorCode.DUPLICATED_USER_NAME, ""));

        mockMvc.perform(post("/api/v1/users/login")
                        // TODO : add request body
                        .content(objectMapper.writeValueAsBytes(new UserLoginRequest(username, password)))
                ).andDo(print())
                .andExpect(status().isOk());
    }
}
