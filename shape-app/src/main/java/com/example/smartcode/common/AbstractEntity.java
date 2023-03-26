package com.example.smartcode.common;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;
import lombok.Getter;

import java.util.UUID;

@Getter
@MappedSuperclass
public abstract class AbstractEntity implements Taggable {

    @Id
    @GeneratedValue
    private UUID id;

    @Version
    private Long version;

    @Override
    public String generateETagMessage() {
        return String.format("%s-%s-%s", id, version, getClass().getSimpleName());
    }
}
