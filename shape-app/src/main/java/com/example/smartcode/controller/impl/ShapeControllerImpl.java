package com.example.smartcode.controller.impl;

import com.example.smartcode.controller.ShapeController;
import com.example.smartcode.dto.GetChangeDto;
import com.example.smartcode.dto.shape.CreateShapeDto;
import com.example.smartcode.dto.shape.GetShapeDto;
import com.example.smartcode.dto.shape.PutShapeDto;
import com.example.smartcode.entity.shape.Shape;
import com.example.smartcode.exception.*;
import com.example.smartcode.mapper.ChangeMapper;
import com.example.smartcode.mapper.ShapeMapper;
import com.example.smartcode.service.ShapeService;
import com.example.smartcode.service.ShapeServiceStrategy;
import com.example.smartcode.utils.EtagGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.plugin.core.PluginRegistry;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;


@RestController
@RequiredArgsConstructor
public class ShapeControllerImpl implements ShapeController {

    private static final String SHAPE_TYPE_NOT_SUPPORTED = "Shape type not supported";

    private final ShapeService shapeService;
    private final PluginRegistry<ShapeServiceStrategy, String> pluginServiceRegistry;
    private final PluginRegistry<ShapeMapper, String> pluginMapperRegistry;
    private final ChangeMapper changeMapper;
    private final EtagGenerator etagGenerator;

    @Override
    public ResponseEntity<GetShapeDto> create(CreateShapeDto createShapeDto) {
        try {
            ShapeServiceStrategy shapeServiceStrategy = pluginServiceRegistry.getPluginFor(createShapeDto.getType().toLowerCase())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, SHAPE_TYPE_NOT_SUPPORTED));

            ShapeMapper shapeMapper = pluginMapperRegistry.getPluginFor(createShapeDto.getType().toLowerCase())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, SHAPE_TYPE_NOT_SUPPORTED));

            Shape shape = shapeServiceStrategy.create(createShapeDto.getParameters());

            return ResponseEntity.status(201)
                    .eTag(etagGenerator.generateETag(shape))
                    .body(shapeMapper.mapShapeToGetShapeDto(shape));

        } catch (InvalidValueOfParameterException | InvalidAmountOfParametersException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @Override
    public ResponseEntity<List<GetShapeDto>> getAll(Map<String, String> params) {
        try {
            List<Shape> shapes = shapeService.getAll(params);
            List<GetShapeDto> result = new ArrayList<>();
            ShapeMapper shapeMapper;
            for (Shape shape : shapes) {
                shapeMapper = pluginMapperRegistry.getPluginFor(shape.getClass().getSimpleName().toLowerCase())
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, SHAPE_TYPE_NOT_SUPPORTED));
                result.add(shapeMapper.mapShapeToGetShapeDto(shape));
            }
            return ResponseEntity.ok().body(result);
        } catch (InvalidParameterException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @Override
    public ResponseEntity<List<GetChangeDto>> getChanges(UUID id) {
        try {
            return ResponseEntity.ok(shapeService.get(id).getChanges().stream().map(changeMapper::mapChangeToGetChangesDto).toList());
        } catch (ShapeNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @Override
    public ResponseEntity<GetShapeDto> update(UUID id, PutShapeDto dto) {
        try {
            Shape shape = shapeService.update(id, dto.getParameters());
            ShapeMapper shapeMapper = pluginMapperRegistry.getPluginFor(shape.getType().toLowerCase())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, SHAPE_TYPE_NOT_SUPPORTED));
            return ResponseEntity.ok().body(shapeMapper.mapShapeToGetShapeDto(shape));
        } catch (ShapeNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (InvalidAmountOfParametersException | InvalidValueOfParameterException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (InvalidEtagException | InvalidShapeTypeException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }
}
