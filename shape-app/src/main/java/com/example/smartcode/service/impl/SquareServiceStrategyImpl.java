package com.example.smartcode.service.impl;

import com.example.smartcode.common.AbstractShapeService;
import com.example.smartcode.entity.Change;
import com.example.smartcode.entity.shape.Shape;
import com.example.smartcode.entity.shape.Square;
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
public class SquareServiceStrategyImpl extends AbstractShapeService implements ShapeServiceStrategy {

    private static final String SQUARE = "square";
    private static final String WIDTH = "width";

    private final ShapeRepository shapeRepository;
    private final ChangeRepository changeRepository;

    public Shape create(List<Double> parameters) throws InvalidValueOfParameterException, InvalidAmountOfParametersException {
        throwsIfInvalidAmountOfParameters(parameters, 1);
        throwsIfNegativeParameters(parameters);
        Square square = new Square();
        square.setWidth(parameters.get(0));
        return shapeRepository.save(square);
    }

    @Override
    public Shape update(Shape shape, List<Double> parameters) throws InvalidValueOfParameterException, InvalidAmountOfParametersException {
        throwsIfInvalidAmountOfParameters(parameters, 1);
        throwsIfNegativeParameters(parameters);
        Square square = (Square) shape;


        Change change = new Change();
        change.setParameterName(WIDTH);
        change.setParameterNewValue(parameters.get(0));
        change.setParameterOldValue(square.getWidth());

        square.setWidth(parameters.get(0));
        square.getChanges().add(changeRepository.save(change));
        return shapeRepository.save(square);
    }


    @Override
    public boolean supports(String delimiter) {
        return delimiter.equals(SQUARE);
    }
}
