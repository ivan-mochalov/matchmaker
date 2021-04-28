package com.example.matchmaker.util;

import com.example.matchmaker.entity.User;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.OffsetTime;
import java.util.ArrayList;
import java.util.List;

import static com.example.matchmaker.util.Constants.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserFactory {

    public static User createUser() {
        return User.builder().name(USER_NAME).skill(SKILL).latency(LATENCY).build();
    }

    public static User createDelayedUser() {
        return User.builder()
                .name(USER_NAME_TWO)
                .skill(SKILL)
                .latency(LATENCY)
                .acceptedAt(OffsetTime.now().minusSeconds(40))
                .build();
    }

    public static List<User> createTwoUsersWithOneDelayed() {
        return List.of(createUser(), createDelayedUser());
    }

    public static List<User> createUsers() {
        OffsetTime twentySecondsAgo = OffsetTime.now().minusSeconds(20);
        return new ArrayList<>(List.of(
                User.builder().name(ONE).latency(1.0).skill(1.0).acceptedAt(twentySecondsAgo).build(),
                User.builder().name(TWO).latency(1.0).skill(1.0).acceptedAt(twentySecondsAgo).build(),
                User.builder().name(THREE).latency(1.0).skill(1.0).acceptedAt(twentySecondsAgo).build()
        ));
    }

    public static List<User> createUsersWithWorstLatency() {
        return List.of(
                User.builder().name(FOUR).latency(999.0).skill(1.0).build(),
                User.builder().name(FIVE).latency(999.0).skill(1.0).build(),
                User.builder().name(SIX).latency(999.0).skill(1.0).build()
        );
    }

    public static List<User> createUsersWithWorstSkill() {
        return List.of(
                User.builder().name(FOUR).latency(1.0).skill(0.0).build(),
                User.builder().name(FIVE).latency(1.0).skill(0.0).build(),
                User.builder().name(SIX).latency(1.0).skill(0.0).build()
        );
    }

    public static List<User> createUsersWithMinDelay() {
        return List.of(
                User.builder().name(FOUR).latency(1.0).skill(0.0).build(),
                User.builder().name(FIVE).latency(1.0).skill(0.0).build(),
                User.builder().name(SIX).latency(1.0).skill(0.0).build()
        );
    }
}
