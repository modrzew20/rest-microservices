package com.example.smartcode.controller;

import com.example.smartcode.dto.PaginationParam;
import com.example.smartcode.dto.user.CreateUserDto;
import com.example.smartcode.dto.user.GetUserDto;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/users")
public interface UserController {

    @GetMapping
    ResponseEntity<Page<GetUserDto>> getAll(PaginationParam paginationParam);

    @PostMapping
    ResponseEntity<GetUserDto> create(@RequestBody @Valid CreateUserDto createUserDto);

}
