package com.example.smartcode.dto.shape;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GetRectangleDto extends GetShapeDto {

    private double length;

    private double width;

}
