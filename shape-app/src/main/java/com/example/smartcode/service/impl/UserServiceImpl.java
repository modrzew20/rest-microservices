package com.example.smartcode.service.impl;

import com.example.smartcode.dto.user.CreateUserDto;
import com.example.smartcode.entity.Role;
import com.example.smartcode.entity.User;
import com.example.smartcode.exception.LoginIsBusyException;
import com.example.smartcode.exception.RoleNotFoundException;
import com.example.smartcode.repository.RoleRepository;
import com.example.smartcode.repository.UserRepository;
import com.example.smartcode.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(noRollbackFor = Exception.class)
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public Page<User> getAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public User create(CreateUserDto entity) throws LoginIsBusyException, RoleNotFoundException {
        throwIfUserExists(entity.getLogin());
        Role role = roleRepository.findById(entity.getRole()).orElseThrow(() -> new RoleNotFoundException(entity.getRole()));
        return userRepository.save(createUser(entity, role));
    }

    private User createUser(CreateUserDto entity, Role role) {
        User user = new User();
        user.setLogin(entity.getLogin());
        user.setPassword(passwordEncoder.encode(entity.getPassword()));
        user.setRole(role);
        user.setFirstName(entity.getFirstName());
        user.setLastName(entity.getLastName());
        return user;
    }

    private void throwIfUserExists(String login) throws LoginIsBusyException {
        if (userRepository.existsByLogin(login)) {
            throw new LoginIsBusyException(login);
        }
    }
}
