package com.example.matchmaker.control.service.impl;

import com.example.matchmaker.boundary.dto.UserDto;
import com.example.matchmaker.control.mapping.UserMapper;
import com.example.matchmaker.control.repository.UserRepository;
import com.example.matchmaker.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static com.example.matchmaker.util.UserDtoFactory.createUserDto;
import static com.example.matchmaker.util.UserFactory.createUser;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@Execution(ExecutionMode.CONCURRENT)
class MatchmakerServiceImplTest {

    @Mock
    private UserMapper mapper;
    @Mock
    private UserRepository repository;
    @InjectMocks
    private MatchmakerServiceImpl service;

    @Test
    void givenUserDto_whenAccept_thenUserSavedToRepository() {
        UserDto userDto = createUserDto();
        when(mapper.from(userDto)).thenReturn(createUser());

        service.accept(userDto);

        verify(mapper, only()).from(any(UserDto.class));
        verify(repository, only()).save(any(User.class));
    }
}
