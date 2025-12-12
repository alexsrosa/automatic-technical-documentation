package com.docgen.core.infrastructure.adapters.input.web.dto;

import lombok.Builder;
import java.time.LocalDateTime;

@Builder
public record ProjectResponse(
    String id,
    String name,
    String description,
    String repositoryUrl,
    String ownerId,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {}