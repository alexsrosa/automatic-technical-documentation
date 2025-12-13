package com.docgen.worker.infrastructure.client.dto;

import java.util.List;

public record UpdateDocumentRequest(
    String title,
    String type,
    List<RequirementDto> requirements,
    String content,
    String status
) {}
