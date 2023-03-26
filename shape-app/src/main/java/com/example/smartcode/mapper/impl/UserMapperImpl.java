package com.example.smartcode.mapper.impl;

import com.example.smartcode.dto.user.GetUserDto;
import com.example.smartcode.entity.User;
import com.example.smartcode.mapper.UserMapper;
import org.springframework.stereotype.Component;

@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public GetUserDto mapUserToGetUserDto(User user) {
        GetUserDto dto = new GetUserDto();
        dto.setId(user.getId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setLogin(user.getLogin());
        dto.setRole(user.getRole().getName());
        dto.setAmountOfFigures(user.getFigures().size());
        return dto;
    }
}
