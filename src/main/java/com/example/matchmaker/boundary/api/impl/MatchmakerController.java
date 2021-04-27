package com.example.matchmaker.boundary.api.impl;

import com.example.matchmaker.boundary.api.MatchmakerApi;
import com.example.matchmaker.boundary.dto.UserDto;
import com.example.matchmaker.control.service.MatchmakerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MatchmakerController implements MatchmakerApi {

    private final MatchmakerService service;

    @Override
    public void acceptUser(final UserDto userDto) {
        service.accept(userDto);
    }
}
