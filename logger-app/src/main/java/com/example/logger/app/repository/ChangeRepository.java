package com.example.logger.app.repository;

import com.example.logger.app.document.ChangeDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.UUID;

public interface ChangeRepository extends MongoRepository<ChangeDocument, UUID> {

    List<ChangeDocument> findByShapeId(UUID shapeId);
}
