package com.example.smartcode.dto.shape;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public abstract class GetShapeDto {

    private UUID id;

    private String type;

    private Long version;

    private String createdBy;

    private LocalDateTime createdAt;

    private LocalDateTime lastModifiedAt;

    private String lastModifiedBy;

    private double area;

    private double perimeter;

}
