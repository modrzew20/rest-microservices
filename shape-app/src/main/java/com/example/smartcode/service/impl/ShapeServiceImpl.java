package com.example.smartcode.service.impl;

import com.example.smartcode.entity.shape.Shape;
import com.example.smartcode.exception.*;
import com.example.smartcode.repository.ShapeRepository;
import com.example.smartcode.service.ShapeService;
import com.example.smartcode.service.ShapeServiceStrategy;
import com.example.smartcode.utils.EtagGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.plugin.core.PluginRegistry;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(noRollbackFor = Exception.class)
public class ShapeServiceImpl implements ShapeService {

    private final PluginRegistry<ShapeServiceStrategy, String> pluginServiceRegistry;
    private final ShapeRepository shapeRepository;
    private final EtagGenerator etagGenerator;

    @Override
    public List<Shape> getAll(Map<String, String> params) throws InvalidParameterException {
        if (params.isEmpty()) {
            return shapeRepository.findAll();
        } else {
            return shapeRepository.findAll(params);
        }
    }

    @Override
    public Shape get(UUID id) throws ShapeNotFoundException {
        return shapeRepository.findById(id).orElseThrow(() -> new ShapeNotFoundException(id));
    }

    @Override
    public Shape update(UUID id, List<Double> parameters) throws ShapeNotFoundException, InvalidAmountOfParametersException, InvalidValueOfParameterException, InvalidEtagException, InvalidShapeTypeException {
        Shape shape = get(id);
        etagGenerator.verifyETag(shape);
        ShapeServiceStrategy shapeServiceStrategy = pluginServiceRegistry.getPluginFor(shape.getClass().getSimpleName().toLowerCase())
                .orElseThrow(() -> new InvalidShapeTypeException(shape.getClass().getSimpleName()));
        return shapeServiceStrategy.update(shape, parameters);
    }

}
