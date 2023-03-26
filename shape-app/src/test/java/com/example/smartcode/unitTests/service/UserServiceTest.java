package com.example.smartcode.unitTests.service;

import com.example.smartcode.dto.user.CreateUserDto;
import com.example.smartcode.entity.Role;
import com.example.smartcode.entity.User;
import com.example.smartcode.exception.LoginIsBusyException;
import com.example.smartcode.exception.RoleNotFoundException;
import com.example.smartcode.repository.RoleRepository;
import com.example.smartcode.repository.UserRepository;
import com.example.smartcode.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @Mock
    RoleRepository roleRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @InjectMocks
    UserServiceImpl userService;

    private static final String firstName = "firstName";
    private static final String lastName = "lastName";
    private static final String login = "login";
    private static final String password = "password";

    CreateUserDto createUserDto;
    User user;
    Role roleCreator;
    Role roleAdmin;
    Pageable pageable = PageRequest.of(0, 10);

    @BeforeEach
    void init() {
        user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setLogin(login);
        user.setPassword(password);

        roleCreator = new Role();
        roleCreator.setName(Role.CREATOR);

        createUserDto = new CreateUserDto();
        createUserDto.setFirstName(firstName);
        createUserDto.setLastName(lastName);
        createUserDto.setLogin(login);
        createUserDto.setPassword(password);
        createUserDto.setRole(roleCreator.getName());

    }

    @Test
    void getAllTest() {
        List<User> users = List.of(new User(), new User(), new User());
        when(userRepository.findAll(pageable)).thenReturn(new PageImpl<>(users));
        Page<User> result = userService.getAll(pageable);

        assertEquals(users.size(), result.getTotalElements());
    }

    @Test
    void createUserTest() throws LoginIsBusyException, RoleNotFoundException {

        when(roleRepository.findById(Role.CREATOR)).thenReturn(java.util.Optional.of(roleCreator));
        when(userRepository.existsByLogin(login)).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(passwordEncoder.encode(password)).thenReturn(password);

        User result = userService.create(createUserDto);

        assertEquals(user, result);
    }

    @Test
    void createUserWithBusyLoginTest() {
        when(userRepository.existsByLogin(login)).thenReturn(true);
        assertThrows(LoginIsBusyException.class, () -> userService.create(createUserDto));
    }


}
