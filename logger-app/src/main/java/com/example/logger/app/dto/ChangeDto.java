package com.example.logger.app.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class ChangeDto {

    private String id;
    private UUID shapeId;
    private LocalDateTime changedDate;
    private String changedBy;
    private String parameterName;
    private double parameterOldValue;
    private double parameterNewValue;

}
