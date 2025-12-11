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
public class Document {
    private String id;
    private String projectId;
    private String title;
    private String content; // Markdown or structured content
    private DocumentType type;
    private DocumentStatus status;
    private List<Requirement> requirements;
    private LocalDateTime generatedAt;
    
    public enum DocumentType {
        TECHNICAL_SPEC,
        API_DOCS,
        README,
        ARCHITECTURE_DIAGRAM
    }
    
    public enum DocumentStatus {
        PENDING,
        GENERATING,
        COMPLETED,
        FAILED
    }
}
