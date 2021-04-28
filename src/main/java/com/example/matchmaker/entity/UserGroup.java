package com.example.matchmaker.entity;

import lombok.Builder;
import lombok.Value;

import java.util.ArrayList;
import java.util.List;

@Value
@Builder
public class UserGroup {

    int groupNumber;
    @Builder.Default
    List<String> userNames = new ArrayList<>();
    double minSkill;
    double maxSkill;
    double avgSkill;
    double minLatency;
    double maxLatency;
    double avgLatency;
    int minTimeSpentSeconds;
    int maxTimeSpentSeconds;
    double avgTimeSpentSeconds;

    @Override
    public String toString() {
        return "The group number " + groupNumber +
                " consists of [" + String.join(",", userNames) +
                "] " +
                "skill [min: " + minSkill +
                "] [max: " + maxSkill +
                "] [avg: " + avgSkill +
                "] " +
                "latency [min: " + minLatency +
                "] [max: " + maxLatency +
                "] [avg: " + avgLatency +
                "] " +
                "time spent in queue [min: " + minTimeSpentSeconds +
                "] [max: " + maxTimeSpentSeconds +
                "] [avg: " + avgTimeSpentSeconds +
                "]";
    }
}
