package com.example.matchmaker.util;

import com.example.matchmaker.boundary.dto.UserDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static com.example.matchmaker.util.Constants.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserDtoFactory {

    public static String createUserDtoString() throws JsonProcessingException {
        return new ObjectMapper().writer().writeValueAsString(UserDto.builder()
                .name(USER_NAME)
                .skill(SKILL)
                .latency(LATENCY)
                .build());
    }

    public static String createInvalidUserDtoString() throws JsonProcessingException {
        return new ObjectMapper().writer().writeValueAsString(UserDto.builder()
                .skill(SKILL)
                .latency(LATENCY)
                .build());
    }
}
