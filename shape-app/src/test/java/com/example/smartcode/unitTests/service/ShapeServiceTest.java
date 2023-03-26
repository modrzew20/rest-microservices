package com.example.smartcode.unitTests.service;

import com.example.smartcode.entity.shape.Circle;
import com.example.smartcode.entity.shape.Rectangle;
import com.example.smartcode.entity.shape.Shape;
import com.example.smartcode.entity.shape.Square;
import com.example.smartcode.exception.*;
import com.example.smartcode.repository.ShapeRepository;
import com.example.smartcode.service.ShapeServiceStrategy;
import com.example.smartcode.service.impl.CircleServiceStrategyImpl;
import com.example.smartcode.service.impl.RectangleServiceStrategyImpl;
import com.example.smartcode.service.impl.ShapeServiceImpl;
import com.example.smartcode.service.impl.SquareServiceStrategyImpl;
import com.example.smartcode.utils.EtagGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.plugin.core.PluginRegistry;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
class ShapeServiceTest {

    @Mock
    PluginRegistry<ShapeServiceStrategy, String> pluginRegistry;

    @Mock
    ShapeRepository shapeRepository;

    @Mock
    CircleServiceStrategyImpl circleServiceStrategy;

    @Mock
    SquareServiceStrategyImpl squareServiceStrategy;

    @Mock
    RectangleServiceStrategyImpl rectangleServiceStrategy;

    @Mock
    EtagGenerator etagGenerator;

    @InjectMocks
    ShapeServiceImpl shapeService;

    @Test
    void getAllWithoutParametersTest() throws InvalidParameterException {
        List<Shape> shapeList = List.of(new Square(), new Circle(), new Rectangle());

        when(shapeRepository.findAll()).thenReturn(shapeList);
        List<Shape> result = shapeService.getAll(Map.of());

        assertEquals(result.size(), shapeList.size());
        verify(shapeRepository, times(1)).findAll();
    }

    @Test
    void getAllWithParametersTest() throws InvalidParameterException {
        Map<String, String> params = Map.of("type", "square");
        List<Shape> shapeList = List.of(new Square(), new Square(), new Square());

        when(shapeRepository.findAll(params)).thenReturn(shapeList);
        List<Shape> result = shapeService.getAll(params);

        assertEquals(result.size(), shapeList.size());
        verify(shapeRepository, times(1)).findAll(params);
    }

    @Test
    void getTest() throws ShapeNotFoundException {
        Shape shape = new Square();
        when(shapeRepository.findById(any(UUID.class))).thenReturn(Optional.of(shape));
        Shape result = shapeService.get(UUID.randomUUID());

        assertEquals(result, shape);
        verify(shapeRepository, times(1)).findById(any(UUID.class));
    }

    @Test
    void updateCircle() throws InvalidEtagException, InvalidAmountOfParametersException, InvalidValueOfParameterException, ShapeNotFoundException, InvalidShapeTypeException {
        Shape shape = new Circle();

        when(shapeRepository.findById(any(UUID.class))).thenReturn(Optional.of(shape));
        when(pluginRegistry.getPluginFor(shape.getClass().getSimpleName().toLowerCase())).thenReturn(Optional.ofNullable(circleServiceStrategy));
        when(circleServiceStrategy.update(any(Circle.class), any(List.class))).thenReturn(shape);
        doNothing().when(etagGenerator).verifyETag(any(Shape.class));

        Shape result = shapeService.update(UUID.randomUUID(), List.of(1.0));

        assertEquals(result, shape);
        verify(shapeRepository, times(1)).findById(any(UUID.class));
        verify(pluginRegistry, times(1)).getPluginFor(shape.getClass().getSimpleName().toLowerCase());
        verify(circleServiceStrategy, times(1)).update(any(Circle.class), any(List.class));
        verify(etagGenerator, times(1)).verifyETag(any(Shape.class));
    }

    @Test
    void updateSquare() throws InvalidEtagException, InvalidAmountOfParametersException, InvalidValueOfParameterException, ShapeNotFoundException, InvalidShapeTypeException {
        Shape shape = new Square();

        when(shapeRepository.findById(any(UUID.class))).thenReturn(Optional.of(shape));
        when(pluginRegistry.getPluginFor(shape.getClass().getSimpleName().toLowerCase())).thenReturn(Optional.ofNullable(squareServiceStrategy));
        when(squareServiceStrategy.update(any(Square.class), any(List.class))).thenReturn(shape);
        doNothing().when(etagGenerator).verifyETag(any(Shape.class));

        Shape result = shapeService.update(UUID.randomUUID(), List.of(1.0));

        assertEquals(result, shape);
        verify(shapeRepository, times(1)).findById(any(UUID.class));
        verify(pluginRegistry, times(1)).getPluginFor(shape.getClass().getSimpleName().toLowerCase());
        verify(squareServiceStrategy, times(1)).update(any(Square.class), any(List.class));
        verify(etagGenerator, times(1)).verifyETag(any(Shape.class));
    }

    @Test
    void updateRectangle() throws InvalidEtagException, InvalidAmountOfParametersException, InvalidValueOfParameterException, ShapeNotFoundException, InvalidShapeTypeException {
        Shape shape = new Rectangle();

        when(shapeRepository.findById(any(UUID.class))).thenReturn(Optional.of(shape));
        when(pluginRegistry.getPluginFor(shape.getClass().getSimpleName().toLowerCase())).thenReturn(Optional.ofNullable(rectangleServiceStrategy));
        when(rectangleServiceStrategy.update(any(Rectangle.class), any(List.class))).thenReturn(shape);
        doNothing().when(etagGenerator).verifyETag(any(Shape.class));

        Shape result = shapeService.update(UUID.randomUUID(), List.of(1.0));

        assertEquals(result, shape);
        verify(shapeRepository, times(1)).findById(any(UUID.class));
        verify(pluginRegistry, times(1)).getPluginFor(shape.getClass().getSimpleName().toLowerCase());
        verify(rectangleServiceStrategy, times(1)).update(any(Rectangle.class), any(List.class));
        verify(etagGenerator, times(1)).verifyETag(any(Shape.class));
    }
}
