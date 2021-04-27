package com.example.matchmaker.control.config;

import com.example.matchmaker.control.service.MatchmakerService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SchedulerConfiguration {

    private static final int FIVE_SECONDS_DELAY = 5000;
    private final MatchmakerService service;

    @Scheduled(fixedDelay = FIVE_SECONDS_DELAY)
    public void matchUsers() {
        service.matchUsers();
    }
}
