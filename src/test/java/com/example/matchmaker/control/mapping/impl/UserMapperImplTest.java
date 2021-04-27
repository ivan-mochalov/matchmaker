package com.example.matchmaker.control.mapping.impl;

import com.example.matchmaker.boundary.dto.UserDto;
import com.example.matchmaker.control.mapping.UserMapper;
import com.example.matchmaker.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

import java.time.OffsetTime;

import static com.example.matchmaker.util.UserDtoFactory.createUserDto;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Execution(ExecutionMode.CONCURRENT)
class UserMapperImplTest {

    private UserMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new UserMapperImpl();
    }

    @Test
    void givenUserDto_whenMapping_thenUserCreated() {
        UserDto userDto = createUserDto();

        User user = mapper.from(userDto);

        assertThat(user).isNotNull();
        assertThat(user.getAcceptedAt()).isBefore(OffsetTime.now());
        assertThat(userDto.getName()).isEqualTo(user.getName());
        assertThat(userDto.getLatency()).isEqualTo(user.getLatency());
        assertThat(userDto.getSkill()).isEqualTo(user.getSkill());
    }

    @Test
    void givenNullUserDto_whenMapping_thenIllegalArgumentExceptionOccurs() {
        assertThrows(IllegalArgumentException.class, () -> mapper.from(null));
    }
}
