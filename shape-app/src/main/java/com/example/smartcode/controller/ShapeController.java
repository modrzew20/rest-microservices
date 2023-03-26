package com.example.smartcode.controller;

import com.example.smartcode.dto.GetChangeDto;
import com.example.smartcode.dto.shape.CreateShapeDto;
import com.example.smartcode.dto.shape.GetShapeDto;
import com.example.smartcode.dto.shape.PutShapeDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RequestMapping("/shapes")
public interface ShapeController {

    @PostMapping
    @Secured("ROLE_CREATOR")
    ResponseEntity<GetShapeDto> create(@RequestBody @Valid CreateShapeDto dto);

    @GetMapping
    ResponseEntity<List<GetShapeDto>> getAll(@RequestParam Map<String, String> params);

    @GetMapping("/{id}/changes")
    @Secured({"ROLE_CREATOR", "ROLE_ADMIN"})
    ResponseEntity<List<GetChangeDto>> getChanges(@PathVariable("id") UUID id);

    @PutMapping("/{id}")
    @Secured({"ROLE_CREATOR", "ROLE_ADMIN"})
    ResponseEntity<GetShapeDto> update(@PathVariable("id") UUID id, @RequestBody @Valid PutShapeDto dto);
}
