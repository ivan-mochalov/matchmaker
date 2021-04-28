package com.example.matchmaker.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Constants {

    public static final String USER_NAME = "ImbaJoeDoe";
    public static final String USER_NAME_TWO = "ImbaJaneDoe";
    public static final double SKILL = 99.99;
    public static final double LATENCY = 123.45;

    public static final String USERS_URL = "/users";
    public static final String MALFORMED_REQUEST_CONTENT = "{";
    public static final String VALIDATION_ERROR_MESSAGE = "Validation failed: [name : must not be blank]";
    public static final String MALFORMED_REQUEST_ERROR_MESSAGE = "Received malformed json request";
    public static final String INTERNAL_ERROR_MESSAGE = "Internal server error: [test]";

    public static final Exception TEST_EXCEPTION = new RuntimeException("test");

    public static final String ONE = "one";
    public static final String TWO = "two";
    public static final String THREE = "three";
    public static final String FOUR = "four";
    public static final String FIVE = "five";
    public static final String SIX = "six";
}
