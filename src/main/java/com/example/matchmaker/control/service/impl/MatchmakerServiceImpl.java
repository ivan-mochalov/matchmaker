package com.example.matchmaker.control.service.impl;

import com.example.matchmaker.boundary.dto.UserDto;
import com.example.matchmaker.control.mapping.UserMapper;
import com.example.matchmaker.control.repository.UserRepository;
import com.example.matchmaker.control.service.MatchmakerService;
import com.example.matchmaker.control.service.UserGroupFactory;
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
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class MatchmakerServiceImpl implements MatchmakerService {

    private static final int LATENCY_MULTIPLIER = 3;
    private static final int SKILL_MULTIPLIER = 2;

    private final UserMapper mapper;
    private final UserRepository repository;
    private final UserGroupFactory userGroupFactory;
    @Value("${group.size:3}")
    private int groupSize;
    @Value("${max.delay.seconds:30}")
    private int maxDelayInSeconds;

    @Override
    public void accept(final UserDto userDto) {
        repository.save(mapper.from(userDto));
    }

    @Override
    public void matchUsers() {
        List<User> users = repository.findAll();

        if (!users.isEmpty()) {
            Optional<User> optionalExpiredUser = findUserWithExpiredDelay(users);
            int currentPoolSize = users.size();
            List<User> selectedUsers;

            if (optionalExpiredUser.isEmpty()) {
                selectedUsers = selectByMedianValues(users, currentPoolSize);
            } else {
                selectedUsers = selectByExpiredUser(users, optionalExpiredUser.get(), currentPoolSize);
            }

            createGroupFrom(selectedUsers);
        } else {
            log.debug("User pool is empty");
        }
    }

    private Optional<User> findUserWithExpiredDelay(List<User> users) {
        return users.stream()
                .filter(u -> Duration.between(u.getAcceptedAt(), OffsetTime.now()).getSeconds() > maxDelayInSeconds)
                .findAny();
    }

    private List<User> selectByMedianValues(final List<User> users, int currentPoolSize) {
        if (currentPoolSize >= groupSize * SKILL_MULTIPLIER) {
            log.debug("Matching users by median values");
            double latency = getMedianUserBy(Comparator.comparing(User::getLatency), users).getLatency();
            double skill = getMedianUserBy(Comparator.comparing(User::getSkill), users).getSkill();

            return selectCandidates(users, latency, skill, currentPoolSize);
        } else {
            log.debug("Not enough users to perform matching by median values");
            return Collections.emptyList();
        }
    }

    private List<User> selectByExpiredUser(final List<User> users, final User user, int currentPoolSize) {
        if (currentPoolSize > groupSize) {
            log.debug("Matching user by expired one");
            return selectCandidates(users, user.getLatency(), user.getSkill(), currentPoolSize);
        } else {
            log.debug("Not enough users for creating full group by expired one. Matching existing users.");
            return users;
        }
    }

    private User getMedianUserBy(final Comparator<User> comparator, final List<User> users) {
        int size = users.size();
        List<User> sortedUsers = users.stream().sorted(comparator).collect(Collectors.toList());
        return size % 2 == 0
                ? sortedUsers.get(size / 2)
                : sortedUsers.get(size / 2 + 1);
    }

    private List<User> selectCandidates(final List<User> users, double latency, double skill, int currentPoolSize) {
        int skillSubgroupSize = calculateSubGroupSize(SKILL_MULTIPLIER, currentPoolSize);
        int latencySubgroupSize = calculateSubGroupSize(LATENCY_MULTIPLIER, currentPoolSize);

        return sortBy(createDelayComparator(OffsetTime.now()).reversed(), this.groupSize,
                sortBy(createSkillComparator(skill), skillSubgroupSize,
                        sortBy(createLatencyComparator(latency), latencySubgroupSize, users)));
    }

    private int calculateSubGroupSize(int multiplier, int total) {
        return Math.min(total / groupSize, multiplier) * groupSize;
    }

    private Comparator<User> createLatencyComparator(double latency) {
        return Comparator.comparing(u -> Math.abs(u.getLatency() - latency));
    }

    private Comparator<User> createSkillComparator(double skill) {
        return Comparator.comparing(u -> Math.abs(u.getSkill() - skill));
    }

    private Comparator<User> createDelayComparator(OffsetTime time) {
        return Comparator.comparing(u -> (Duration.between(u.getAcceptedAt(), time).getSeconds()));
    }

    private List<User> sortBy(Comparator<User> comparator, int groupSize, List<User> users) {
        return users.stream()
                .sorted(comparator)
                .limit(groupSize)
                .collect(Collectors.toList());
    }

    private void createGroupFrom(List<User> selectedUsers) {
        if (!selectedUsers.isEmpty()) {
            List<User> matchedUsers = new ArrayList<>();
            selectedUsers.forEach(u -> matchedUsers.add(repository.deleteBy(u.getName())));
            userGroupFactory.createGroup(matchedUsers);

            matchUsers();
        }
    }
}
