package com.example.matchmaker.mapping;

import com.example.matchmaker.dto.UserDto;
import com.example.matchmaker.entity.User;

public interface UserMapper {

    User from(UserDto dto);
}
