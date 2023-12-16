package com.example.sns.service;

import com.example.sns.exception.SnsApplicationException;
import com.example.sns.fixture.UserEntityFixture;
import com.example.sns.model.entity.UserEntity;
import com.example.sns.repository.UserEntityRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserServiceTest {
    @Autowired
    private UserService userService;

    @MockBean
    private UserEntityRepository userEntityRepository;

    @Test
    void 회원가입이_정상적으로_동작하는경우() {
        String username = "username";
        String password = "password";

        when(userEntityRepository.findByUserName(username)).thenReturn(Optional.empty());
        when(userEntityRepository.save(any())).thenReturn(Optional.of(mock(UserEntity.class)));

        assertThatCode(() -> userService.join(username, password)).doesNotThrowAnyException();

    }

    @Test
    void 회원가입시_입력한_아이디로_이미_가입되어있는_유저가_있는경우() {
        String username = "username";
        String password = "password";

        when(userEntityRepository.findByUserName(any())).thenReturn(Optional.of(mock(UserEntity.class)));
        when(userEntityRepository.save(any())).thenReturn(Optional.of(mock(UserEntity.class)));

        assertThatCode(() -> userService.join(username, password)).isInstanceOf(SnsApplicationException.class);
    }
    @Test
    void 로그인이_정상적으로_동작하는경우() {
        String userName = "username";
        String password = "password";

        UserEntity userEntity = UserEntityFixture.get(userName, password);

        when(userEntityRepository.findByUserName(userName)).thenReturn(Optional.of(userEntity));

        assertThatCode(() -> userService.join(userName, password)).doesNotThrowAnyException();

    }

    @Test
    void 로그인시_패스워드가_틀린경우() {
        String userName = "username";
        String password = "password";
        String wrongPassword = "wrongPassword";

        UserEntity fixture = UserEntityFixture.get(userName, password);

        when(userEntityRepository.findByUserName(userName)).thenReturn(Optional.of(fixture));

        assertThatCode(() -> userService.login(userName, password)).isInstanceOf(SnsApplicationException.class);
    }
}
