package com.docgen.core.infrastructure.adapters.input.web.dto;

import com.docgen.core.domain.model.Document.DocumentStatus;
import com.docgen.core.domain.model.Document.DocumentType;
import lombok.Builder;
import java.time.LocalDateTime;

@Builder
public record DocumentResponse(
    String id,
    String projectId,
    String title,
    String content,
    DocumentType type,
    DocumentStatus status,
    LocalDateTime generatedAt
) {}