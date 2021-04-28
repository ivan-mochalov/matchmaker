package com.example.matchmaker.control.service.impl;

import com.example.matchmaker.control.service.UserGroupFactory;
import com.example.matchmaker.control.service.UserGroupHandler;
import com.example.matchmaker.entity.User;
import com.example.matchmaker.entity.UserGroup;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.OffsetTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserGroupFactoryImpl implements UserGroupFactory {

    private static final double DEFAULT_DOUBLE = 0.0;
    private static final int DEFAULT_INT = 0;
    private static final AtomicInteger matchNumber = new AtomicInteger(0);

    private final UserGroupHandler handler;

    @Async
    @Override
    public void createGroup(List<User> users) {
        final var now = OffsetTime.now();

        handler.handleUserGroup(UserGroup.builder()
                .groupNumber(matchNumber.incrementAndGet())
                .userNames(createUserNames(users))
                .minSkill(calculateMinSkill(users))
                .maxSkill(calculateMaxSkill(users))
                .avgSkill(calculateAvgSkill(users))
                .minLatency(calculateMinLatency(users))
                .maxLatency(calculateMaxLatency(users))
                .avgLatency(calculateAvgLatency(users))
                .minTimeSpentSeconds(calculateMinTimeSpentSeconds(users, now))
                .maxTimeSpentSeconds(calculateMaxTimeSpentSeconds(users, now))
                .avgTimeSpentSeconds(calculateAvgTimeSpentSeconds(users, now))
                .build());
    }

    private double calculateMinSkill(List<User> users) {
        return users.stream()
                .mapToDouble(User::getSkill)
                .min()
                .orElse(DEFAULT_DOUBLE);
    }

    private double calculateMaxSkill(List<User> users) {
        return users.stream()
                .mapToDouble(User::getSkill)
                .max()
                .orElse(DEFAULT_DOUBLE);
    }

    private double calculateAvgSkill(List<User> users) {
        return Math.floor(users.stream()
                .mapToDouble(User::getSkill)
                .average()
                .orElse(DEFAULT_DOUBLE));
    }

    private double calculateMinLatency(List<User> users) {
        return users.stream()
                .mapToDouble(User::getLatency)
                .min()
                .orElse(DEFAULT_DOUBLE);
    }

    private double calculateMaxLatency(List<User> users) {
        return users.stream()
                .mapToDouble(User::getLatency)
                .max()
                .orElse(DEFAULT_DOUBLE);
    }

    private double calculateAvgLatency(List<User> users) {
        return Math.floor(users.stream()
                .mapToDouble(User::getLatency)
                .average()
                .orElse(DEFAULT_DOUBLE));
    }

    private int calculateMinTimeSpentSeconds(List<User> users, OffsetTime now) {
        return users.stream()
                .mapToInt(u -> (int) Duration.between(u.getAcceptedAt(), now).getSeconds())
                .min()
                .orElse(DEFAULT_INT);
    }

    private int calculateMaxTimeSpentSeconds(List<User> users, OffsetTime now) {
        return users.stream()
                .mapToInt(u -> (int) Duration.between(u.getAcceptedAt(), now).getSeconds())
                .max()
                .orElse(DEFAULT_INT);
    }

    private double calculateAvgTimeSpentSeconds(final List<User> users, OffsetTime now) {
        return Math.round(users.stream()
                .mapToLong(u -> Duration.between(u.getAcceptedAt(), now).getSeconds())
                .average()
                .orElse(DEFAULT_DOUBLE));
    }

    private List<String> createUserNames(final List<User> users) {
        return users.stream().map(User::getName).collect(Collectors.toList());
    }
}
