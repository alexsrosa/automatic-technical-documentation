package com.docgen.worker.infrastructure.messaging.dto;

public record RequirementMessage(
    String id,
    String description,
    String priority
) {}
