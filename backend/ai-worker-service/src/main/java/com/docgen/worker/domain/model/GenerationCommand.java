package com.docgen.worker.domain.model;

import java.util.List;

public record GenerationCommand(
    String documentId,
    String projectId,
    String title,
    String type,
    List<Requirement> requirements
) {}
