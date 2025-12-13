package com.docgen.core.infrastructure.adapters.input.web.dto;

import com.docgen.core.domain.model.Requirement;
import lombok.Builder;

@Builder
public record RequirementDto(
    String id,
    String description,
    Requirement.Priority priority
) {}
