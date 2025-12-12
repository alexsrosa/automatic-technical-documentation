package com.docgen.core.infrastructure.adapters.output.persistence.repository;

import com.docgen.core.infrastructure.adapters.output.persistence.entity.ProjectEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpringDataProjectRepository extends MongoRepository<ProjectEntity, String> {
    List<ProjectEntity> findByOwnerId(String ownerId);
}
