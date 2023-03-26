package com.example.smartcode.unitTests.service;

import com.example.smartcode.entity.Change;
import com.example.smartcode.entity.shape.Square;
import com.example.smartcode.exception.InvalidAmountOfParametersException;
import com.example.smartcode.exception.InvalidValueOfParameterException;
import com.example.smartcode.repository.ChangeRepository;
import com.example.smartcode.repository.ShapeRepository;
import com.example.smartcode.service.impl.SquareServiceStrategyImpl;
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
class SquareServiceStrategyTest {

    @Mock
    ShapeRepository shapeRepository;

    @Mock
    ChangeRepository changeRepository;

    @InjectMocks
    SquareServiceStrategyImpl squareServiceStrategy;

    @Test
    void createTest() throws InvalidAmountOfParametersException, InvalidValueOfParameterException {

        Square square = new Square();
        square.setWidth(2.2);

        when(shapeRepository.save(any(Square.class))).thenReturn(square);
        Square result = (Square) squareServiceStrategy.create(List.of(2.2));

        assertEquals(square, result);
        verify(shapeRepository, times(1)).save(any(Square.class));
    }

    @Test
    void createNegativeParametersExceptionTest() {
        assertThrows(InvalidValueOfParameterException.class, () -> squareServiceStrategy.create(List.of(-2.2)));
    }

    @Test
    void createInvalidAmountOfParametersExceptionTest() {
        assertThrows(InvalidAmountOfParametersException.class, () -> squareServiceStrategy.create(List.of(2.2, 2.2)));
        assertThrows(InvalidAmountOfParametersException.class, () -> squareServiceStrategy.create(List.of()));
    }

    @Test
    void updateTest() throws InvalidAmountOfParametersException, InvalidValueOfParameterException {
        Square square = new Square();
        square.setWidth(2.2);
        Change change = new Change();

        when(changeRepository.save(any(Change.class))).thenReturn(change);
        when(shapeRepository.save(square)).thenReturn(square);

        Square result = (Square) squareServiceStrategy.update(square, List.of(2.2));

        assertEquals(square, result);
        verify(changeRepository, times(1)).save(any(Change.class));
        verify(shapeRepository, times(1)).save(any(Square.class));
    }

}
