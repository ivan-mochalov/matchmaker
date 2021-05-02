package com.example.matchmaker.service.impl;

import com.example.matchmaker.dto.UserDto;
import com.example.matchmaker.mapping.UserMapper;
import com.example.matchmaker.repository.UserRepository;
import com.example.matchmaker.service.UserGroupFactory;
import com.example.matchmaker.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Collections;
import java.util.List;

import static com.example.matchmaker.util.Constants.*;
import static com.example.matchmaker.util.UserDtoFactory.createUserDto;
import static com.example.matchmaker.util.UserFactory.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@Execution(ExecutionMode.CONCURRENT)
class MatchmakerServiceImplTest {

    private static final int GROUP_SIZE = 3;
    private static final int MAX_DELAY = 30;

    @Mock
    private UserMapper mapper;
    @Mock
    private UserRepository repository;
    @Mock
    private UserGroupFactory userGroupFactory;
    private MatchmakerServiceImpl service;

    @BeforeEach
    void setUp() {
        service = new MatchmakerServiceImpl(mapper, repository, userGroupFactory);
        ReflectionTestUtils.setField(service, "groupSize", GROUP_SIZE);
        ReflectionTestUtils.setField(service, "maxDelayInSeconds", MAX_DELAY);
    }

    @Test
    void givenUserDto_whenAccept_thenUserSavedToRepository() {
        UserDto userDto = createUserDto();
        when(mapper.from(userDto)).thenReturn(createUser());

        service.accept(userDto);

        verify(mapper, only()).from(any(UserDto.class));
        verify(repository, only()).save(any(User.class));
    }

    @Test
    void givenEmptyRepository_whenMatchUsers_thenRepositoryCalledOnly() {
        when(repository.findAll()).thenReturn(Collections.emptyList());

        service.matchUsers();

        verify(repository, only()).findAll();
        verify(userGroupFactory, never()).createGroup(any());
    }

    @Test
    void givenUserWithExpiredDelay_whenNotEnoughUserToGroup_thenCreateOne() {
        when(repository.findAll()).thenReturn(createTwoUsersWithOneDelayed());
        when(repository.deleteBy(any())).thenReturn(createUser());
        doThrow(TEST_EXCEPTION).when(userGroupFactory).createGroup(any());

        try {
            service.matchUsers();
        } catch (Exception e) {
            //expected
        }

        verify(repository, times(2)).deleteBy(any());
    }

    @Test
    void givenUsers_whenEnoughUserToGroup_thenFilteredOutWithWorstLatency() {
        final var users = createUsers();
        List<User> usersWithWorstLatency = createUsersWithWorstLatency();
        users.addAll(usersWithWorstLatency);
        when(repository.findAll()).thenReturn(users);
        when(repository.deleteBy(any())).thenReturn(createUser());
        doThrow(TEST_EXCEPTION).when(userGroupFactory).createGroup(any());

        try {
            service.matchUsers();
        } catch (Exception e) {
            //expected
        }

        verify(repository, times(1)).deleteBy(ONE);
        verify(repository, times(1)).deleteBy(TWO);
        verify(repository, times(1)).deleteBy(THREE);
        verify(repository, times(0)).deleteBy(FOUR);
        verify(repository, times(0)).deleteBy(FIVE);
        verify(repository, times(0)).deleteBy(SIX);
    }

    @Test
    void givenUsers_whenEnoughUserToGroup_thenFilteredOutWithWorstSkill() {
        final var users = createUsers();
        List<User> usersWithWorstSkill = createUsersWithWorstSkill();
        users.addAll(usersWithWorstSkill);
        when(repository.findAll()).thenReturn(users);
        when(repository.deleteBy(any())).thenReturn(createUser());
        doThrow(TEST_EXCEPTION).when(userGroupFactory).createGroup(any());

        try {
            service.matchUsers();
        } catch (Exception e) {
            //expected
        }

        verify(repository, times(1)).deleteBy(ONE);
        verify(repository, times(1)).deleteBy(TWO);
        verify(repository, times(1)).deleteBy(THREE);
        verify(repository, times(0)).deleteBy(FOUR);
        verify(repository, times(0)).deleteBy(FIVE);
        verify(repository, times(0)).deleteBy(SIX);;
    }

    @Test
    void givenUsers_whenEnoughUserToGroup_thenFilteredOutWithWorstDelay() {
        final var users = createUsers();
        List<User> usersWithMinDelay = createUsersWithMinDelay();
        users.addAll(usersWithMinDelay);
        when(repository.findAll()).thenReturn(users);
        when(repository.deleteBy(any())).thenReturn(createUser());
        doThrow(TEST_EXCEPTION).when(userGroupFactory).createGroup(any());

        try {
            service.matchUsers();
        } catch (Exception e) {
            //expected
        }

        verify(repository, times(1)).deleteBy(ONE);
        verify(repository, times(1)).deleteBy(TWO);
        verify(repository, times(1)).deleteBy(THREE);
        verify(repository, times(0)).deleteBy(FOUR);
        verify(repository, times(0)).deleteBy(FIVE);
        verify(repository, times(0)).deleteBy(SIX);;
    }
}
