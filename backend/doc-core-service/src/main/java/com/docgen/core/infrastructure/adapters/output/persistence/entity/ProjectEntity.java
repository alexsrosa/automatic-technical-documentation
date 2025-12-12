package com.docgen.core.infrastructure.adapters.output.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "projects")
public class ProjectEntity {
    @Id
    private String id;
    private String name;
    private String description;
    private String repositoryUrl;
    private String ownerId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
