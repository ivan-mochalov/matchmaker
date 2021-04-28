package com.example.matchmaker.boundary.api.impl;

import com.example.matchmaker.boundary.api.MatchmakerApi;
import com.example.matchmaker.boundary.dto.UserDto;
import com.example.matchmaker.control.service.MatchmakerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MatchmakerController implements MatchmakerApi {

    private final MatchmakerService service;

    @Override
    public void acceptUser(final UserDto userDto) {
        log.debug("Received user: [{}]", userDto.getName());
        service.accept(userDto);
    }
}
