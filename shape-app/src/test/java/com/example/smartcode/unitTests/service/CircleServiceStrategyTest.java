package com.example.smartcode.unitTests.service;

import com.example.smartcode.entity.Change;
import com.example.smartcode.entity.shape.Circle;
import com.example.smartcode.exception.InvalidAmountOfParametersException;
import com.example.smartcode.exception.InvalidValueOfParameterException;
import com.example.smartcode.repository.ChangeRepository;
import com.example.smartcode.repository.ShapeRepository;
import com.example.smartcode.service.impl.CircleServiceStrategyImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
class CircleServiceStrategyTest {

    @Mock
    ShapeRepository shapeRepository;

    @Mock
    ChangeRepository changeRepository;

    @InjectMocks
    CircleServiceStrategyImpl circleServiceStrategy;

    @Test
    void createTest() throws InvalidAmountOfParametersException, InvalidValueOfParameterException {
        Circle circle = new Circle();

        when(shapeRepository.save(any(Circle.class))).thenReturn(circle);
        Circle result = (Circle) circleServiceStrategy.create(List.of(2.2));

        assertEquals(circle, result);
        verify(shapeRepository, times(1)).save(any(Circle.class));
    }

    @Test
    void createNegativeParametersExceptionTest() {
        assertThrows(InvalidValueOfParameterException.class, () -> circleServiceStrategy.create(List.of(-2.2)));
    }

    @Test
    void createInvalidAmountOfParametersExceptionTest() {
        assertThrows(InvalidAmountOfParametersException.class, () -> circleServiceStrategy.create(List.of(2.2, 2.2)));
        assertThrows(InvalidAmountOfParametersException.class, () -> circleServiceStrategy.create(List.of()));
    }

    @Test
    void updateTest() throws InvalidAmountOfParametersException, InvalidValueOfParameterException {
        Circle circle = new Circle();
        Change change = new Change();

        when(changeRepository.save(any(Change.class))).thenReturn(change);
        when(shapeRepository.save(circle)).thenReturn(circle);

        Circle result = (Circle) circleServiceStrategy.update(circle, List.of(2.2));

        assertEquals(circle, result);
        verify(changeRepository, times(1)).save(any(Change.class));
        verify(shapeRepository, times(1)).save(any(Circle.class));
    }

}
