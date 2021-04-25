package com.example.matchmaker.boundary.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.Validation;
import javax.validation.Validator;

import static com.example.matchmaker.util.Constants.*;
import static org.assertj.core.api.Assertions.assertThat;

public class UserDtoValidationTest {

    private Validator validator;
    private UserDto userDto;

    @BeforeEach
    void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    void givenValidUserDto_whenValidated_thenNoErrorsOccurred() {
        userDto = UserDto.builder().name(USER_NAME).skill(SKILL).latency(LATENCY).build();

        final var violations = validator.validate(userDto);
        assertThat(violations).isEmpty();
    }

    @Test
    void givenValidUserDtoWithZeroSkill_whenValidated_thenNoErrorsOccurred() {
        userDto = UserDto.builder().name(USER_NAME).skill(0.0).latency(LATENCY).build();

        final var violations = validator.validate(userDto);
        assertThat(violations).isEmpty();
    }

    @Test
    void givenDtoWithNullName_whenValidated_thenError() {
        userDto = UserDto.builder().skill(SKILL).latency(LATENCY).build();

        final var violations = validator.validate(userDto);
        assertThat(violations.size()).isEqualTo(1);
    }

    @Test
    void givenDtoWithEmptyName_whenValidated_thenError() {
        userDto = UserDto.builder().name("").skill(SKILL).latency(LATENCY).build();

        final var violations = validator.validate(userDto);
        assertThat(violations.size()).isEqualTo(1);
    }

    @Test
    void givenDtoWithNullSkill_whenValidated_thenError() {
        userDto = UserDto.builder().name(USER_NAME).latency(LATENCY).build();

        final var violations = validator.validate(userDto);
        assertThat(violations.size()).isEqualTo(1);
    }

    @Test
    void givenDtoWithNegativeSkill_whenValidated_thenError() {
        userDto = UserDto.builder().name(USER_NAME).skill(-1.0).latency(LATENCY).build();

        final var violations = validator.validate(userDto);
        assertThat(violations.size()).isEqualTo(1);
    }

    @Test
    void givenDtoWithNullLatency_whenValidated_thenError() {
        userDto = UserDto.builder().name(USER_NAME).skill(SKILL).build();

        final var violations = validator.validate(userDto);
        assertThat(violations.size()).isEqualTo(1);
    }

    @Test
    void givenDtoWithZeroLatency_whenValidated_thenError() {
        userDto = UserDto.builder().name(USER_NAME).skill(SKILL).latency(0.0).build();

        final var violations = validator.validate(userDto);
        assertThat(violations.size()).isEqualTo(1);
    }

    @Test
    void givenDtoWithNegativeLatency_whenValidated_thenError() {
        userDto = UserDto.builder().name(USER_NAME).skill(SKILL).latency(-1.0).build();

        final var violations = validator.validate(userDto);
        assertThat(violations.size()).isEqualTo(1);
    }
}
