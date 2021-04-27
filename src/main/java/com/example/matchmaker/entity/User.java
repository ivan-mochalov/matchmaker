package com.example.matchmaker.entity;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;

import java.time.OffsetTime;

@Value
@Builder
public class User {

    String name;
    @EqualsAndHashCode.Exclude
    Double skill;
    @EqualsAndHashCode.Exclude
    Double latency;
    @EqualsAndHashCode.Exclude
    @Builder.Default
    OffsetTime acceptedAt = OffsetTime.now();
}
