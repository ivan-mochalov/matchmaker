package com.example.matchmaker.service;

import com.example.matchmaker.dto.UserDto;

public interface MatchmakerService {

    void accept(UserDto userDto);

    void matchUsers();
}
