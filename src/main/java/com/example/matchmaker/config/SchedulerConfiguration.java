package com.example.matchmaker.config;

import com.example.matchmaker.service.MatchmakerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class SchedulerConfiguration {

    private static final int HALF_SECOND_DELAY = 500;
    private final MatchmakerService service;

    @Scheduled(fixedDelay = HALF_SECOND_DELAY)
    public void matchUsers() {
        log.debug("Scheduler started matching");
        service.matchUsers();
        log.debug("Scheduler finished matching");
    }
}
