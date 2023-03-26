package com.example.smartcode.dto.shape;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CreateShapeDto {

    @NotNull
    String type;

    @NotNull
    List<Double> parameters;

}
