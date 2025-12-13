package com.docgen.core.infrastructure.adapters.input.web.dto;

import com.docgen.core.domain.model.Document;
import lombok.Builder;
import java.util.List;

@Builder
public record UpdateDocumentRequest(
    String title,
    Document.DocumentType type,
    List<RequirementDto> requirements,
    String content,
    Document.DocumentStatus status
) {}
