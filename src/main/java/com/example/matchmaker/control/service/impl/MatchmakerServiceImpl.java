package com.example.matchmaker.control.service.impl;

import com.example.matchmaker.boundary.dto.UserDto;
import com.example.matchmaker.control.mapping.UserMapper;
import com.example.matchmaker.control.repository.UserRepository;
import com.example.matchmaker.control.service.MatchmakerService;
import com.example.matchmaker.control.service.UserGroupFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MatchmakerServiceImpl implements MatchmakerService {

    private final UserMapper mapper;
    private final UserRepository repository;
    private final UserGroupFactory userGroupFactory;

    @Value("${group.size:5}")
    private int groupSize;
    @Value("${max.delay.seconds:30}")
    private int maxDelayInSeconds;

    @Override
    public void accept(final UserDto userDto) {
        repository.save(mapper.from(userDto));
    }

    @Override
    public void matchUsers() {
        log.info("Current users pool: ");
        repository.findAll().forEach(u -> log.info(u.toString()));
    }
}
