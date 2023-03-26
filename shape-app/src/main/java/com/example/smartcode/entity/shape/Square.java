package com.example.smartcode.entity.shape;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Square extends Shape {

    private double width;

    @Override
    protected double calculateArea() {
        return width * width;
    }

    @Override
    protected double calculatePerimeter() {
        return 4 * width;
    }
}
