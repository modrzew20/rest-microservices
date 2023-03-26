package com.example.smartcode.service.impl;

import com.example.smartcode.common.AbstractShapeService;
import com.example.smartcode.entity.Change;
import com.example.smartcode.entity.shape.Circle;
import com.example.smartcode.entity.shape.Shape;
import com.example.smartcode.exception.InvalidAmountOfParametersException;
import com.example.smartcode.exception.InvalidValueOfParameterException;
import com.example.smartcode.repository.ChangeRepository;
import com.example.smartcode.repository.ShapeRepository;
import com.example.smartcode.service.ShapeServiceStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(noRollbackFor = Exception.class)
public class CircleServiceStrategyImpl extends AbstractShapeService implements ShapeServiceStrategy {

    private static final String CIRCLE = "circle";
    private static final String RADIUS = "radius";


    private final ShapeRepository shapeRepository;
    private final ChangeRepository changeRepository;

    @Override
    public Shape create(List<Double> parameters) throws InvalidAmountOfParametersException, InvalidValueOfParameterException {
        throwsIfInvalidAmountOfParameters(parameters, 1);
        throwsIfNegativeParameters(parameters);

        Circle circle = new Circle();
        circle.setRadius(parameters.get(0));
        return shapeRepository.save(circle);
    }

    @Override
    public Shape update(Shape shape, List<Double> parameters) throws InvalidValueOfParameterException, InvalidAmountOfParametersException {
        throwsIfInvalidAmountOfParameters(parameters, 1);
        throwsIfNegativeParameters(parameters);
        Circle circle = (Circle) shape;

        Change change = new Change();
        change.setParameterName(RADIUS);
        change.setParameterNewValue(parameters.get(0));
        change.setParameterOldValue(circle.getRadius());

        circle.setRadius(parameters.get(0));
        circle.getChanges().add(changeRepository.save(change));
        return shapeRepository.save(circle);
    }

    @Override
    public boolean supports(String delimiter) {
        return delimiter.equals(CIRCLE);
    }
}
