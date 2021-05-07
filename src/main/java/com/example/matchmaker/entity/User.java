package com.example.matchmaker.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;

import java.time.OffsetTime;

@Data
@Builder
@Setter(AccessLevel.NONE)
public class User {

    String name;
    @EqualsAndHashCode.Exclude
    Double skill;
    @EqualsAndHashCode.Exclude
    Double latency;
    @EqualsAndHashCode.Exclude
    @Builder.Default
    OffsetTime acceptedAt = OffsetTime.now();
    @EqualsAndHashCode.Exclude
    @Setter
    int rate;
}
