package com.docgen.core.infrastructure.adapters.output.persistence.repository;

import com.docgen.core.infrastructure.adapters.output.persistence.entity.DocumentEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpringDataDocumentRepository extends MongoRepository<DocumentEntity, String> {
    List<DocumentEntity> findByProjectId(String projectId);
}
