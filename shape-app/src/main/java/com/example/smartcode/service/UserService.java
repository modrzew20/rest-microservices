package com.example.smartcode.service;

import com.example.smartcode.dto.user.CreateUserDto;
import com.example.smartcode.entity.User;
import com.example.smartcode.exception.LoginIsBusyException;
import com.example.smartcode.exception.RoleNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

    Page<User> getAll(Pageable pageable);

    User create(CreateUserDto entity) throws LoginIsBusyException, RoleNotFoundException;


}
