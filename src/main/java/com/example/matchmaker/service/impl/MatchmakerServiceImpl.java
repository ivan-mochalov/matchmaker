package com.example.matchmaker.service.impl;

import com.example.matchmaker.dto.UserDto;
import com.example.matchmaker.mapping.UserMapper;
import com.example.matchmaker.repository.UserRepository;
import com.example.matchmaker.service.MatchmakerService;
import com.example.matchmaker.service.UserGroupFactory;
import com.example.matchmaker.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.OffsetTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class MatchmakerServiceImpl implements MatchmakerService {

    public static final int GROUP_SIZE_MULTIPLIER = 2;

    private final UserMapper mapper;
    private final UserRepository repository;
    private final UserGroupFactory userGroupFactory;

    @Value("${custom.group.size:5}")
    private int groupSize;
    @Value("${custom.delay.seconds:25}")
    private int maxDelayInSeconds;
    @Value("${custom.rate.step.skill:5}")
    private int skillRateStep;
    @Value("${custom.rate.step.latency:25}")
    private int latencyRateStep;

    @Override
    public void accept(final UserDto userDto) {
        repository.save(mapper.from(userDto));
    }

    @Override
    public void matchUsers() {
        List<User> users = repository.findAll();

        if (!users.isEmpty()) {
            Optional<User> optionalExpiredUser = findUserWithExpiredDelay(users);

            if (optionalExpiredUser.isEmpty()) {
                createGroupFrom(selectByRate(users));
            } else {
                createGroupFrom(selectByExpiredUser(users, optionalExpiredUser.get()));
            }
        } else {
            log.debug("User pool is empty");
        }
    }

    private Optional<User> findUserWithExpiredDelay(List<User> users) {
        return users.stream()
                .filter(u -> Duration.between(u.getAcceptedAt(), OffsetTime.now()).getSeconds() > maxDelayInSeconds)
                .findAny();
    }

    private List<User> selectByRate(final List<User> users) {
        if (users.size() >= groupSize * GROUP_SIZE_MULTIPLIER) {
            log.debug("Matching users by rate");
            Map<Integer, List<User>> groupCandidates = new HashMap<>();

            users.forEach(i -> {
                final List<User> candidate = selectGroup(users, i);

                groupCandidates.putIfAbsent(candidate.stream().mapToInt(User::getRate).sum(), candidate);
            });

            return groupCandidates.get(Collections.min(groupCandidates.keySet()));
        } else {
            log.debug("Not enough users to perform matching by rate");
            return Collections.emptyList();
        }
    }

    private List<User> selectGroup(List<User> users, User user) {
        final var latency = user.getLatency();
        final var skill = user.getSkill();

        users.forEach(u -> u.setRate(calculateRate(u, latency, skill)));

        return users.stream()
                .sorted(Comparator.comparing(User::getRate).thenComparing(User::getAcceptedAt))
                .limit(groupSize)
                .collect(Collectors.toList());
    }

    public int calculateRate(final User user, double latency, double skill) {
        return (int) (Math.round(Math.abs(user.getLatency() - latency) / latencyRateStep)
                + Math.round(Math.abs(user.getSkill() - skill) / skillRateStep));
    }

    private List<User> selectByExpiredUser(final List<User> users, final User user) {
        if (users.size() > groupSize) {
            log.debug("Matching users by expired one");
            return selectGroup(users, user);
        } else {
            log.debug("Not enough users for creating full group by expired one. Matching existing users.");
            return users;
        }
    }

    private void createGroupFrom(List<User> selectedUsers) {
        if (!selectedUsers.isEmpty()) {
            List<User> matchedUsers = new ArrayList<>();
            selectedUsers.forEach(u -> matchedUsers.add(repository.deleteBy(u.getName())));
            userGroupFactory.createGroup(matchedUsers);
        }
    }
}
