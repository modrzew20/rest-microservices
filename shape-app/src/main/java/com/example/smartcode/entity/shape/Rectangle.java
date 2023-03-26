package com.example.smartcode.entity.shape;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Rectangle extends Shape {

    private double length;

    private double width;

    @Override
    protected double calculateArea() {
        return length * width;
    }

    @Override
    protected double calculatePerimeter() {
        return 2 * (length + width);
    }
}
