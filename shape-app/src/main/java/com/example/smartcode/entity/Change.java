package com.example.smartcode.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Change {

    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;

    @CreatedDate
    @Setter(AccessLevel.NONE)
    @Column(nullable = false, updatable = false)
    private LocalDateTime changedDate;

    @CreatedBy
    @Setter(AccessLevel.NONE)
    @Column(nullable = false, updatable = false)
    private String changedBy;

    @Column(nullable = false, updatable = false)
    private String parameterName;

    @Column(nullable = false, updatable = false)
    private double parameterOldValue;

    @Column(nullable = false, updatable = false)
    private double parameterNewValue;

}
