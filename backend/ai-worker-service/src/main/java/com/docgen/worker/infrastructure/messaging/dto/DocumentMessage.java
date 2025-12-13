package com.docgen.worker.infrastructure.messaging.dto;

import java.util.List;

public record DocumentMessage(
    String id,
    String projectId,
    String title,
    String type,
    String status,
    List<RequirementMessage> requirements
) {}
