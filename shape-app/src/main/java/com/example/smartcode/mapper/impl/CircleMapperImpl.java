package com.example.smartcode.mapper.impl;

import com.example.smartcode.common.AbstractShapeMapper;
import com.example.smartcode.dto.shape.GetCircleDto;
import com.example.smartcode.dto.shape.GetShapeDto;
import com.example.smartcode.entity.shape.Circle;
import com.example.smartcode.entity.shape.Shape;
import com.example.smartcode.mapper.ShapeMapper;
import org.springframework.stereotype.Component;

@Component
public class CircleMapperImpl extends AbstractShapeMapper implements ShapeMapper {

    @Override
    public GetShapeDto mapShapeToGetShapeDto(Shape shape) {
        GetCircleDto dto = new GetCircleDto();
        mapShape(shape, dto);
        dto.setRadius(((Circle) shape).getRadius());
        return dto;
    }

    @Override
    public boolean supports(String delimiter) {
        return delimiter.equals("circle");
    }

}
