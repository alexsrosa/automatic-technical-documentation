package com.docgen.core.infrastructure.adapters.input.web.dto;

import com.docgen.core.domain.model.Document;
import lombok.Builder;

@Builder
public record CreateDocumentRequest(
    String projectId,
    String title,
    Document.DocumentType type
) {}