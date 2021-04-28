package com.example.matchmaker.control.service.impl;

import com.example.matchmaker.control.service.UserGroupHandler;
import com.example.matchmaker.entity.User;
import com.example.matchmaker.entity.UserGroup;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.OffsetTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
@Execution(ExecutionMode.CONCURRENT)
class UserGroupFactoryImplTest {

    @Mock
    private UserGroupHandler handler;
    @InjectMocks
    private UserGroupFactoryImpl userGroupFactory;
    @Captor
    private ArgumentCaptor<UserGroup> userGroupCaptor;
    private List<User> users;


    @BeforeEach
    void setUp() {
        users = List.of(
                User.builder().name("First").skill(50.0).latency(50.0).acceptedAt(OffsetTime.now().minusSeconds(8)).build(),
                User.builder().name("Second").skill(45.0).latency(65.0).acceptedAt(OffsetTime.now().minusSeconds(16)).build(),
                User.builder().name("Third").skill(55.0).latency(35.0).acceptedAt(OffsetTime.now().minusSeconds(6)).build()
        );
    }

    @Test
    void givenUserList_whenCreateGroup_thenHandlerCalled() {
        userGroupFactory.createGroup(users);

        verify(handler, only()).handleUserGroup(any());
    }

    @Test
    void givenUserList_whenCreateGroup_thenGroupNumberIsPositiveValue() {
        userGroupFactory.createGroup(users);

        verify(handler).handleUserGroup(userGroupCaptor.capture());

        assertThat(userGroupCaptor.getValue().getGroupNumber()).isPositive();
    }

    @Test
    void givenUserList_whenCreateGroup_thenUserNamesPresented() {
        userGroupFactory.createGroup(users);

        verify(handler).handleUserGroup(userGroupCaptor.capture());

        assertThat(userGroupCaptor.getValue().getUserNames().size()).isEqualTo(3);
    }

    @Test
    void givenUserList_whenCreateGroup_thenMinSkillPresented() {
        userGroupFactory.createGroup(users);

        verify(handler).handleUserGroup(userGroupCaptor.capture());

        assertThat(userGroupCaptor.getValue().getMinSkill()).isEqualTo(45.0);
    }

    @Test
    void givenUserList_whenCreateGroup_thenMaxSkillPresented() {
        userGroupFactory.createGroup(users);

        verify(handler).handleUserGroup(userGroupCaptor.capture());

        assertThat(userGroupCaptor.getValue().getMaxSkill()).isEqualTo(55.0);
    }

    @Test
    void givenUserList_whenCreateGroup_thenAvgSkillPresented() {
        userGroupFactory.createGroup(users);

        verify(handler).handleUserGroup(userGroupCaptor.capture());

        assertThat(userGroupCaptor.getValue().getAvgSkill()).isEqualTo(50.0);
    }

    @Test
    void givenUserList_whenCreateGroup_thenMinLatencyPresented() {
        userGroupFactory.createGroup(users);

        verify(handler).handleUserGroup(userGroupCaptor.capture());

        assertThat(userGroupCaptor.getValue().getMinLatency()).isEqualTo(35.0);
    }

    @Test
    void givenUserList_whenCreateGroup_thenMaxLatencyPresented() {
        userGroupFactory.createGroup(users);

        verify(handler).handleUserGroup(userGroupCaptor.capture());

        assertThat(userGroupCaptor.getValue().getMaxLatency()).isEqualTo(65.0);
    }

    @Test
    void givenUserList_whenCreateGroup_thenAvgLatencyPresented() {
        userGroupFactory.createGroup(users);

        verify(handler).handleUserGroup(userGroupCaptor.capture());

        assertThat(userGroupCaptor.getValue().getAvgLatency()).isEqualTo(50.0);
    }

    @Test
    void givenUserList_whenCreateGroup_thenMinTimeSpentSeconds() {
        userGroupFactory.createGroup(users);

        verify(handler).handleUserGroup(userGroupCaptor.capture());

        assertThat(userGroupCaptor.getValue().getMinTimeSpentSeconds()).isEqualTo(6);
    }

    @Test
    void givenUserList_whenCreateGroup_thenMaxTimeSpentSeconds() {
        userGroupFactory.createGroup(users);

        verify(handler).handleUserGroup(userGroupCaptor.capture());

        assertThat(userGroupCaptor.getValue().getMaxTimeSpentSeconds()).isEqualTo(16);
    }

    @Test
    void givenUserList_whenCreateGroup_thenAvgTimeSpentSeconds() {
        userGroupFactory.createGroup(users);

        verify(handler).handleUserGroup(userGroupCaptor.capture());

        assertThat(userGroupCaptor.getValue().getAvgTimeSpentSeconds()).isEqualTo(10.0);
    }
}
