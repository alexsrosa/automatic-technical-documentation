package com.docgen.core.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Project {
    private String id;
    private String name;
    private String description;
    private String repositoryUrl;
    private String ownerId;
    private List<Document> documents;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
