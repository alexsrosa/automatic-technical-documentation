package com.docgen.core.domain.model;

import com.docgen.core.infrastructure.adapters.output.persistence.entity.DocumentEntity;
import com.docgen.core.infrastructure.adapters.output.persistence.entity.ProjectEntity;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ModelAndEntityTest {

    @Test
    void testProjectModel() {
        LocalDateTime now = LocalDateTime.now();
        Project project = Project.builder()
                .id("1")
                .name("Name")
                .description("Desc")
                .repositoryUrl("Url")
                .ownerId("Owner")
                .documents(List.of())
                .createdAt(now)
                .updatedAt(now)
                .build();

        assertEquals("1", project.getId());
        assertEquals("Name", project.getName());
        assertEquals("Desc", project.getDescription());
        assertEquals("Url", project.getRepositoryUrl());
        assertEquals("Owner", project.getOwnerId());
        assertEquals(0, project.getDocuments().size());
        assertEquals(now, project.getCreatedAt());
        assertEquals(now, project.getUpdatedAt());
        
        // Test NoArgs
        Project empty = new Project();
        assertNotNull(empty);
    }

    @Test
    void testDocumentModel() {
        Document document = Document.builder()
                .id("1")
                .projectId("p1")
                .title("Title")
                .type(Document.DocumentType.README)
                .status(Document.DocumentStatus.PENDING)
                .build();

        assertEquals("1", document.getId());
        assertEquals("p1", document.getProjectId());
        assertEquals("Title", document.getTitle());
        assertEquals(Document.DocumentType.README, document.getType());
        assertEquals(Document.DocumentStatus.PENDING, document.getStatus());
    }

    @Test
    void testRequirementModel() {
        Requirement req = Requirement.builder()
                .id("1")
                .description("Desc")
                .priority(Requirement.Priority.HIGH)
                .build();

        assertEquals("1", req.getId());
        assertEquals("Desc", req.getDescription());
        assertEquals(Requirement.Priority.HIGH, req.getPriority());
    }

    @Test
    void testProjectEntity() {
        ProjectEntity entity = ProjectEntity.builder()
                .id("1")
                .name("Name")
                .build();

        assertEquals("1", entity.getId());
        assertEquals("Name", entity.getName());
    }

    @Test
    void testDocumentEntity() {
        DocumentEntity entity = DocumentEntity.builder()
                .id("1")
                .title("Title")
                .build();

        assertEquals("1", entity.getId());
        assertEquals("Title", entity.getTitle());
    }
}
