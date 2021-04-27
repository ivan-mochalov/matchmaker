package com.example.matchmaker.control.service;

import com.example.matchmaker.boundary.dto.UserDto;

public interface MatchmakerService {

    void accept(UserDto userDto);

    void matchUsers();
}
