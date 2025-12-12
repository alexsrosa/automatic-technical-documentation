package com.docgen.core.infrastructure.adapters.output.persistence.entity;

import com.docgen.core.domain.model.Document.DocumentStatus;
import com.docgen.core.domain.model.Document.DocumentType;
import com.docgen.core.domain.model.Requirement;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "documents")
public class DocumentEntity {
    @Id
    private String id;
    private String projectId;
    private String title;
    private String content;
    private DocumentType type;
    private DocumentStatus status;
    private List<Requirement> requirements;
    private LocalDateTime generatedAt;
}
