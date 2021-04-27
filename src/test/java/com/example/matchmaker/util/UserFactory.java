package com.example.matchmaker.util;

import com.example.matchmaker.entity.User;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static com.example.matchmaker.util.Constants.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserFactory {

    public static User createUser() {
        return User.builder().name(USER_NAME).skill(SKILL).latency(LATENCY).build();
    }
}
