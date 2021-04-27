package com.example.matchmaker.control.mapping.impl;

import com.example.matchmaker.boundary.dto.UserDto;
import com.example.matchmaker.control.mapping.UserMapper;
import com.example.matchmaker.entity.User;
import lombok.NonNull;
import org.springframework.stereotype.Component;

@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public User from(@NonNull UserDto dto) {
        return User.builder()
                .name(dto.getName())
                .skill(dto.getSkill())
                .latency(dto.getLatency())
                .build();
    }
}
