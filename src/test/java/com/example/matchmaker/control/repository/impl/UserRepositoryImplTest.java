package com.example.matchmaker.control.repository.impl;

import com.example.matchmaker.control.repository.UserRepository;
import com.example.matchmaker.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

import java.util.List;

import static com.example.matchmaker.util.Constants.USER_NAME;
import static com.example.matchmaker.util.UserFactory.createUser;
import static org.assertj.core.api.Assertions.assertThat;

@Execution(ExecutionMode.CONCURRENT)
class UserRepositoryImplTest {

    private UserRepository repository;

    @BeforeEach
    void setUp() {
        repository = new UserRepositoryImpl();
    }

    @Test
    void givenUserSaved_whenFindBy_thenReturnUser() {
        repository.save(createUser());

        User foundUser = repository.findBy(USER_NAME);

        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getName()).isEqualTo(USER_NAME);
    }

    @Test
    void givenUserSaved_whenFindAll_thenReturnUserInList() {
        repository.save(createUser());

        List<User> users = repository.findAll();

        assertThat(users).isNotEmpty();
        assertThat(users.size()).isEqualTo(1);
    }

    @Test
    void givenUserSaved_whenDeleteUser_thenDeleteUserAndReturnBack() {
        repository.save(createUser());

        User user = repository.deleteBy(USER_NAME);

        assertThat(user).isNotNull();
        assertThat(repository.findAll()).isEmpty();
    }
}
