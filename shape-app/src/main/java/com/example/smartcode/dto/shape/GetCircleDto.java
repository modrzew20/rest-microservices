package com.example.smartcode.dto.shape;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GetCircleDto extends GetShapeDto {

    private double radius;
}
