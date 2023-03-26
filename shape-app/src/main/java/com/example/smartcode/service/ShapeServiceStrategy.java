package com.example.smartcode.service;

import com.example.smartcode.entity.shape.Shape;
import com.example.smartcode.exception.InvalidAmountOfParametersException;
import com.example.smartcode.exception.InvalidValueOfParameterException;
import org.springframework.plugin.core.Plugin;

import java.util.List;

public interface ShapeServiceStrategy extends Plugin<String> {

    Shape create(List<Double> parameters) throws InvalidValueOfParameterException, InvalidAmountOfParametersException;

    Shape update(Shape shape, List<Double> parameters) throws InvalidValueOfParameterException, InvalidAmountOfParametersException;
}
