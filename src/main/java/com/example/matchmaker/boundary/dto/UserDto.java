package com.example.matchmaker.boundary.dto;

import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@Value
@Builder
public class UserDto {

    @NotBlank
    String name;
    @NotNull
    @PositiveOrZero
    Double skill;
    @NotNull
    @Positive
    Double latency;
}
