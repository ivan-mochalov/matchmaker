package com.example.matchmaker.mapping.impl;

import com.example.matchmaker.dto.UserDto;
import com.example.matchmaker.mapping.UserMapper;
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
