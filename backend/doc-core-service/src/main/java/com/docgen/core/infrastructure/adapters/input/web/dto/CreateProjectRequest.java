package com.docgen.core.infrastructure.adapters.input.web.dto;

import lombok.Builder;

@Builder
public record CreateProjectRequest(
    String name,
    String description,
    String repositoryUrl,
    String ownerId
) {}
