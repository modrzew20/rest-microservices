package com.example.smartcode.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class GetChangeDto {

    private UUID id;

    private LocalDateTime changedDate;

    private String changedBy;

    private String parameterName;

    private double parameterOldValue;

    private double parameterNewValue;
}
