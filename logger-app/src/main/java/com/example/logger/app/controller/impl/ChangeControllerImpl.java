package com.example.logger.app.controller.impl;

import com.example.logger.app.controller.ChangeController;
import com.example.logger.app.dto.ChangeDto;
import com.example.logger.app.mapper.ChangeDtoMapper;
import com.example.logger.app.model.Change;
import com.example.logger.app.service.ChangeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class ChangeControllerImpl implements ChangeController {

    private final ChangeService changeService;
    private final ChangeDtoMapper changeDtoMapper;

    @Override
    public ResponseEntity<List<ChangeDto>> getAll(UUID shapeId) {
        return ResponseEntity.ok()
                .body(changeService.getAll(shapeId)
                        .stream().map(changeDtoMapper::changeToChangeDto).toList());
    }

    @Override
    public ResponseEntity<ChangeDto> create(ChangeDto changeDto) {
        Change change = changeDtoMapper.changeDtoToChange(changeDto);
        return ResponseEntity.status(201)
                .body(changeDtoMapper.changeToChangeDto(changeService.create(change)));
    }

}
