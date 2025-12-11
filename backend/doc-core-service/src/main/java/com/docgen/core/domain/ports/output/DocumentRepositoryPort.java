package com.docgen.core.domain.ports.output;

import com.docgen.core.domain.model.Document;
import java.util.Optional;
import java.util.List;

public interface DocumentRepositoryPort {
    Document save(Document document);
    Optional<Document> findById(String id);
    List<Document> findByProjectId(String projectId);
}
