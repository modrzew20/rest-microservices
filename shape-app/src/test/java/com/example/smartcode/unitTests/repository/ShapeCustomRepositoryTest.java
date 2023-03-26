package com.example.smartcode.unitTests.repository;

import com.example.smartcode.entity.shape.Circle;
import com.example.smartcode.entity.shape.Rectangle;
import com.example.smartcode.entity.shape.Shape;
import com.example.smartcode.entity.shape.Square;
import com.example.smartcode.exception.InvalidParameterException;
import com.example.smartcode.repository.ShapeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase
class ShapeCustomRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ShapeRepository shapeCustomRepository;

    @BeforeEach
    void init() {
        for (int i = 0; i < 20; i++) {
            createCircle((double) (2 + i), LocalDateTime.now().minusDays(i));
            createSquare((double) (2 + i), LocalDateTime.now().minusDays(i));
            createRectangle((double) 2 + i, (double) 2 + i, LocalDateTime.now().minusDays(i));
        }

    }

    @ParameterizedTest
    @CsvSource({
            "type, circle, 20",
            "type, rectangle, 20",
            "type, square, 20",
            "createdBy, test-user, 20"
    })
    void findAllByKeyTest(String key, String value, int expectedValue) throws InvalidParameterException {
        List<Shape> shapes = shapeCustomRepository.findAll(Map.of(key, value));
        assertEquals(expectedValue, shapes.size());
    }

    @ParameterizedTest
    @CsvSource({
            "areaFrom, 10, areaTo, 100, 18",
            "perimeterFrom, 50, perimeterTo, 100, 26",
            "widthFrom, 2, widthTo, 10, 18",
            "lengthFrom, 2, lengthTo, 11, 10",
            "radiusFrom, 2, radiusTo, 11, 10",
    })
    void findAllByKeyFromAndKeyToTest(String keyFrom, String valueFrom, String keyTo, String valueTo, int expectedValue) throws InvalidParameterException {
        Map<String, String> filters = new HashMap<>();
        filters.put(keyFrom, valueFrom);
        filters.put(keyTo, valueTo);

        List<Shape> shapes = shapeCustomRepository.findAll(filters);
        assertEquals(expectedValue, shapes.size());
    }

    @Test
    void findAllByDateFromAndDateToTest() throws InvalidParameterException {
        Map<String, String> filters = new HashMap<>();
        filters.put("createdAtFrom", LocalDateTime.now().minusDays(10).toString());
        filters.put("createdAtTo", LocalDateTime.now().toString());


        List<Shape> shapes = shapeCustomRepository.findAll(filters);
        assertEquals(30, shapes.size());
    }


    private void createCircle(Double radius, LocalDateTime createdAt) {
        Circle circle = new Circle();
        circle.setType("circle");
        circle.setRadius(radius);
        circle.setCreatedAt(createdAt);
        circle.setCreatedBy("test-user");
        circle.setLastModifiedAt(createdAt);
        circle.setLastModifiedBy("test-user");

        entityManager.persist(circle);
        entityManager.flush();
    }

    private void createSquare(Double width, LocalDateTime createdAt) {
        Square square = new Square();
        square.setType("square");
        square.setWidth(width);
        square.setCreatedAt(createdAt);
        square.setCreatedBy("test-admin");
        square.setLastModifiedAt(createdAt);
        square.setLastModifiedBy("test-user");

        entityManager.persist(square);
        entityManager.flush();
    }


    private void createRectangle(Double width, Double length, LocalDateTime createdAt) {
        Rectangle square = new Rectangle();
        square.setType("rectangle");
        square.setWidth(width);
        square.setLength(length);
        square.setCreatedAt(createdAt);
        square.setCreatedBy("test-creator");
        square.setLastModifiedAt(createdAt);
        square.setLastModifiedBy("test-user");

        entityManager.persist(square);
        entityManager.flush();
    }


}
