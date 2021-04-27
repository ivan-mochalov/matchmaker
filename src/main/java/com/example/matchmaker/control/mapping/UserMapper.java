package com.example.matchmaker.control.mapping;

import com.example.matchmaker.boundary.dto.UserDto;
import com.example.matchmaker.entity.User;

public interface UserMapper {

    User from(UserDto dto);
}
