package com.docgen.core.domain.ports.input;

import com.docgen.core.domain.model.Document;
import java.util.List;
import java.util.Optional;

public interface GetDocumentUseCase {
    List<Document> getDocumentsByProject(String projectId);
    Optional<Document> getDocumentById(String id);
}