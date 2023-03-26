package com.example.smartcode.repository;

import com.example.smartcode.entity.shape.Shape;
import com.example.smartcode.exception.InvalidParameterException;

import java.util.List;
import java.util.Map;

public interface ShapeCustomRepository {

    List<Shape> findAll(Map<String, String> params) throws InvalidParameterException;
}
