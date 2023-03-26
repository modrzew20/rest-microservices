package com.example.smartcode.unitTests.service;

import com.example.smartcode.entity.Change;
import com.example.smartcode.entity.shape.Rectangle;
import com.example.smartcode.exception.InvalidAmountOfParametersException;
import com.example.smartcode.exception.InvalidValueOfParameterException;
import com.example.smartcode.repository.ChangeRepository;
import com.example.smartcode.repository.ShapeRepository;
import com.example.smartcode.service.impl.RectangleServiceStrategyImpl;
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
class RectangleServiceStrategyTest {


    @Mock
    ShapeRepository shapeRepository;

    @Mock
    ChangeRepository changeRepository;

    @InjectMocks
    RectangleServiceStrategyImpl rectangleServiceStrategy;

    @Test
    void createTest() throws InvalidAmountOfParametersException, InvalidValueOfParameterException {

        Rectangle rectangle = new Rectangle();

        when(shapeRepository.save(any(Rectangle.class))).thenReturn(rectangle);
        Rectangle result = (Rectangle) rectangleServiceStrategy.create(List.of(2.2, 2.4));

        assertEquals(rectangle, result);
        verify(shapeRepository, times(1)).save(any(Rectangle.class));
    }

    @Test
    void createNegativeParametersExceptionTest() {
        assertThrows(InvalidValueOfParameterException.class, () -> rectangleServiceStrategy.create(List.of(-2.2, 5.0)));
        assertThrows(InvalidValueOfParameterException.class, () -> rectangleServiceStrategy.create(List.of(2.2, -5.0)));
        assertThrows(InvalidValueOfParameterException.class, () -> rectangleServiceStrategy.create(List.of(-2.2, -5.0)));
    }

    @Test
    void createInvalidAmountOfParametersExceptionTest() {
        assertThrows(InvalidAmountOfParametersException.class, () -> rectangleServiceStrategy.create(List.of(2.2)));
        assertThrows(InvalidAmountOfParametersException.class, () -> rectangleServiceStrategy.create(List.of(2.2, 2.4, 2.4)));
    }

    @Test
    void updateTest() throws InvalidAmountOfParametersException, InvalidValueOfParameterException {
        Rectangle rectangle = new Rectangle();
        Change change = new Change();

        when(changeRepository.save(any(Change.class))).thenReturn(change);
        when(shapeRepository.save(rectangle)).thenReturn(rectangle);

        Rectangle result = (Rectangle) rectangleServiceStrategy.update(rectangle, List.of(2.2, 2.5));

        assertEquals(rectangle, result);
        verify(changeRepository, times(2)).save(any(Change.class));
        verify(shapeRepository, times(1)).save(any(Rectangle.class));
    }

}