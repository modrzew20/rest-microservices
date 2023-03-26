package com.example.smartcode.mapper;

import com.example.smartcode.dto.user.GetUserDto;
import com.example.smartcode.entity.User;
import jakarta.validation.constraints.NotNull;


public interface UserMapper {

    GetUserDto mapUserToGetUserDto(@NotNull User user);

}
