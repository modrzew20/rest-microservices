package com.example.smartcode.service;

import com.example.smartcode.entity.shape.Shape;
import com.example.smartcode.exception.*;
import org.springframework.security.access.prepost.PostAuthorize;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface ShapeService {

    List<Shape> getAll(Map<String, String> params) throws InvalidParameterException;

    @PostAuthorize("(hasRole('ROLE_CREATOR') && returnObject.createdBy == authentication.name) || hasRole('ROLE_ADMIN')")
    Shape get(UUID id) throws ShapeNotFoundException;

    @PostAuthorize("(hasRole('ROLE_CREATOR') && returnObject.createdBy == authentication.name) || hasRole('ROLE_ADMIN')")
    Shape update(UUID id, List<Double> parameters) throws ShapeNotFoundException, InvalidAmountOfParametersException, InvalidValueOfParameterException, InvalidEtagException, InvalidShapeTypeException;
}
