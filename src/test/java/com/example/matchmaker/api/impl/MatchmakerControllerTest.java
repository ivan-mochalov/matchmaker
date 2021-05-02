package com.example.matchmaker.api.impl;

import com.example.matchmaker.service.MatchmakerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static com.example.matchmaker.util.Constants.*;
import static com.example.matchmaker.util.UserDtoFactory.createInvalidUserDtoString;
import static com.example.matchmaker.util.UserDtoFactory.createUserDtoString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Execution(ExecutionMode.CONCURRENT)
@WebMvcTest(controllers = MatchmakerController.class)
class MatchmakerControllerTest {

    @Autowired
    private MockMvc controller;
    @MockBean
    private MatchmakerService service;

    @Test
    void givenValidRequest_whenPerformed_thenAccepted() throws Exception {
        controller.perform(post(USERS_URL)
                .content(createUserDtoString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted());
    }

    @Test
    void givenInvalidRequest_whenPerformed_thenErrorMessageReturns() throws Exception {
        controller.perform(post(USERS_URL)
                .content(createInvalidUserDtoString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(VALIDATION_ERROR_MESSAGE));
    }

    @Test
    void givenMalformedRequest_whenPerformed_thenErrorMessageReturns() throws Exception {
        controller.perform(post(USERS_URL)
                .content(MALFORMED_REQUEST_CONTENT)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(MALFORMED_REQUEST_ERROR_MESSAGE));
    }

    @Test
    void givenInternalException_whenPerformed_thenErrorMessageReturns() throws Exception {
        doThrow(TEST_EXCEPTION).when(service).accept(any());

        controller.perform(post(USERS_URL)
                .content(createUserDtoString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string(INTERNAL_ERROR_MESSAGE));
    }
}
