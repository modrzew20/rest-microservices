package com.example.smartcode.repository;

import com.example.smartcode.entity.shape.Shape;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ShapeRepository extends JpaRepository<Shape, UUID>, ShapeCustomRepository {

}
