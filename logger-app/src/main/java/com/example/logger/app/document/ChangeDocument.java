package com.example.logger.app.document;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.UUID;


@Document
@Getter
@Setter
public class ChangeDocument {

    @Id
    @Setter(AccessLevel.NONE)
    private String id;
    private UUID shapeId;
    @CreatedDate
    @Setter(AccessLevel.NONE)
    private LocalDateTime changedDate;
    private String changedBy;
    private String parameterName;
    private double parameterOldValue;
    private double parameterNewValue;

}
