package com.example.smartcode.common;

import com.example.smartcode.dto.shape.GetShapeDto;
import com.example.smartcode.entity.shape.Shape;

public abstract class AbstractShapeMapper {

    protected void mapShape(Shape shape, GetShapeDto dto) {
        dto.setArea(shape.getArea());
        dto.setPerimeter(shape.getPerimeter());
        dto.setId(shape.getId());
        dto.setVersion(shape.getVersion());
        dto.setCreatedBy(shape.getCreatedBy());
        dto.setCreatedAt(shape.getCreatedAt());
        dto.setLastModifiedAt(shape.getLastModifiedAt());
        dto.setLastModifiedBy(shape.getLastModifiedBy());
        dto.setType(shape.getType());
    }
}
